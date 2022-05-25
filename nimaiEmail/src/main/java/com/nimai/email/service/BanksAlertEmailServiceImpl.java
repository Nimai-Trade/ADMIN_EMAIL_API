package com.nimai.email.service;

import com.nimai.email.api.GenericResponse;
import com.nimai.email.bean.AlertToBanksBean;
import com.nimai.email.bean.EligibleEmailBeanResponse;
import com.nimai.email.bean.EligibleEmailList;
import com.nimai.email.bean.EmailSendingDetails;
import com.nimai.email.bean.QuotationAlertRequest;
import com.nimai.email.controller.BanksAlertEmailController;
import com.nimai.email.dao.BanksAlertDao;
import com.nimai.email.entity.NimaiClient;
import com.nimai.email.entity.NimaiEmailSchedulerAlertToBanks;
import com.nimai.email.entity.NimaiLC;
import com.nimai.email.entity.NimaiMBranch;
import com.nimai.email.entity.QuotationMaster;
import com.nimai.email.entity.TransactionSaving;
import com.nimai.email.service.BanksALertEmailService;
import com.nimai.email.utility.EmaiInsert;
import com.nimai.email.utility.EmailErrorCode;
import com.nimai.email.utility.ErrorDescription;
import com.nimai.email.utility.ModelMapper;
import com.nimai.email.utility.ResetUserValidation;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;
import javax.persistence.EntityManagerFactory;
import javax.persistence.ParameterMode;
import javax.persistence.StoredProcedureQuery;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class BanksAlertEmailServiceImpl implements BanksALertEmailService {
  private static Logger logger = LoggerFactory.getLogger(BanksAlertEmailController.class);
  
  @Autowired
  BanksAlertDao userDao;
  
  @Autowired
  private EmaiInsert emailInsert;
  
  @Autowired
  EntityManagerFactory em;
  
  @Autowired
  ResetUserValidation resetUserValidator;
  
  public void setTransactionEmailInSchTable() {
    logger.info("============Inside scheduler method of setTransactionEmailInSchTable==============");
    List<NimaiEmailSchedulerAlertToBanks> emailDetailsScheduled = this.userDao.getTransactionDetailByTrEmailStatus();
    Iterator<NimaiEmailSchedulerAlertToBanks> itr = emailDetailsScheduled.iterator();
    while (itr.hasNext()) {
      NimaiEmailSchedulerAlertToBanks schdulerData = itr.next();
      if (schdulerData.getTransactionEmailStatusToBanks().equalsIgnoreCase("pending") && schdulerData
        .getCustomerid().substring(0, 2).equalsIgnoreCase("CU") && schdulerData
        .getEmailEvent().equalsIgnoreCase("LC_UPLOAD(DATA)"))
        try {
          StoredProcedureQuery getBAnksEmail = this.em.createEntityManager().createStoredProcedureQuery("get_eligible_banks", new Class[] { NimaiClient.class });
          getBAnksEmail.registerStoredProcedureParameter("inp_customer_userID", String.class, ParameterMode.IN);
          getBAnksEmail.registerStoredProcedureParameter("inp_transaction_ID", String.class, ParameterMode.IN);
          getBAnksEmail.setParameter("inp_customer_userID", schdulerData.getCustomerid());
          getBAnksEmail.setParameter("inp_transaction_ID", schdulerData.getTransactionid());
          getBAnksEmail.execute();
          ModelMapper modelMapper = new ModelMapper();
          List<NimaiClient> nimaiCust = getBAnksEmail.getResultList();
          EligibleEmailBeanResponse responseBean = new EligibleEmailBeanResponse();
          List<EligibleEmailList> emailIdList = new ArrayList<>();
          List<EligibleEmailList> emailId = (List<EligibleEmailList>)nimaiCust.stream().map(obj -> {
                EligibleEmailList data = new EligibleEmailList();
                NimaiEmailSchedulerAlertToBanks schedulerEntity = new NimaiEmailSchedulerAlertToBanks();
                Calendar cal = Calendar.getInstance();
                Date insertedDate = cal.getTime();
                schedulerEntity.setInsertedDate(insertedDate);
                schedulerEntity.setCustomerid(schdulerData.getCustomerid());
                schedulerEntity.setTransactionid(schdulerData.getTransactionid());
                schedulerEntity.setEmailEvent("LC_UPLOAD_ALERT_ToBanks");
                schedulerEntity.setBanksEmailID(obj.getEmailAddress());
                schedulerEntity.setBankUserid(obj.getUserid());
                schedulerEntity.setBankUserName(obj.getFirstName());
                int i = schdulerData.getScedulerid();
                String trScheduledId = Integer.toString(i);
                schedulerEntity.setTrScheduledId(trScheduledId);
                schedulerEntity.setEmailFlag("pending");
                schedulerEntity.setTransactionEmailStatusToBanks("pending");
                this.userDao.saveSchdulerData(schedulerEntity);
                data.setEmailList(obj.getEmailAddress());
                return data;
              }).collect(Collectors.toList());
          int scedulerid = schdulerData.getScedulerid();
          this.userDao.updateTREmailStatus(scedulerid);
          logger.info("Customer critria(minLc,blGoods,country) matching banks are not available to send the transaction upload alert");
        } catch (Exception e) {
          logger.info("Customer critria(minLc,blGoods,country) matching banks are not available to send the customer transaction upload alert");
        }  
    } 
  }
  
  @Scheduled(fixedDelay = 50000L)
  @Transactional(propagation = Propagation.NESTED)
  public void sendTransactionStatusToBanksByScheduled() {
    logger.info("=====InsidesendTransactionStatusToBanksByScheduled method========= ");
    GenericResponse response = new GenericResponse();
    String emailStatus = "";
    List<NimaiEmailSchedulerAlertToBanks> emailDetailsScheduled = this.userDao.getTransactionDetail();
    for (NimaiEmailSchedulerAlertToBanks schdulerData : emailDetailsScheduled) {
      if (schdulerData.getEmailEvent().equalsIgnoreCase("QUOTE_ACCEPT") || schdulerData
        .getEmailEvent().equalsIgnoreCase("QUOTE_REJECTION")) {
        logger.info("============Inside QUOTE_ACCEPT & QUOTE_REJECTION condition==========");
        try {
          NimaiClient customerDetails;
          NimaiLC custTransactionDetails = this.userDao.getTransactioDetailsByTransId(schdulerData.getTransactionid());
          logger.info("============Inside QUOTE_ACCEPT & QUOTE_REJECTION condition==========" + custTransactionDetails
              .toString());
          QuotationMaster bnakQuotationDetails = this.userDao.getDetailsByQuoteId(schdulerData.getQuotationId().intValue());
          logger.info("============Inside QUOTE_ACCEPT & QUOTE_REJECTION condition==========" + bnakQuotationDetails
              .toString());
          if (schdulerData.getCustomerEmail() == null || schdulerData.getCustomerEmail().isEmpty()) {
            customerDetails = this.userDao.getCustDetailsByUserId(schdulerData.getCustomerid());
            NimaiClient acnttSrceDtsByAssoUser = this.userDao.getCustDetailsByUserId(customerDetails.getAccountSource());
            logger.info("============Inside QUOTE_ACCEPT & QUOTE_REJECTION condition11==========" + customerDetails
                .toString());
          } else {
            customerDetails = this.userDao.getCustDetailsByUserId(schdulerData.getCustomerid());
            logger.info("============Inside QUOTE_ACCEPT & QUOTE_REJECTION condition 12==========" + customerDetails
                .toString());
          } 
          NimaiClient bankDetails = this.userDao.getCustDetailsByUserId(schdulerData.getBankUserid());
          logger.info("============Inside QUOTE_ACCEPT & QUOTE_REJECTION condition==========" + bankDetails
              .toString());
          if (custTransactionDetails != null && bnakQuotationDetails != null) {
            this.emailInsert.sendQuotationStatusEmail(schdulerData.getEmailEvent(), schdulerData, schdulerData
                .getBanksEmailID(), custTransactionDetails, bnakQuotationDetails, bankDetails, customerDetails);
            try {
              logger.info("============Inside QUOTE_ACCEPT & QUOTE_REJECTION condition schedulerId:==========" + schdulerData
                  
                  .getScedulerid());
              this.userDao.updateEmailFlag(schdulerData.getScedulerid());
            } catch (Exception e) {
              e.printStackTrace();
              emailStatus = "Email_Sent";
              this.userDao.updateInvalidIdEmailFlag(schdulerData.getScedulerid(), emailStatus);
            } 
            continue;
          } 
          logger.info("=====Inside QUOTE_ACCEPT or QUOTE_REJECTION quotation id not found======");
          emailStatus = "Quote_Id_NOT_Register";
          try {
            this.userDao.updateInvalidIdEmailFlag(schdulerData.getScedulerid(), emailStatus);
          } catch (Exception e) {
            e.printStackTrace();
            logger.info("=======Inside QUOTE_ACCEPT & QUOTE_REJECTION condition in updateInvalidflag method======");
          } 
        } catch (Exception e) {
          if (e instanceof NullPointerException) {
            response.setMessage("Email Sending failed");
            EmailErrorCode emailError = new EmailErrorCode("EmailNull", Integer.valueOf(409));
            logger.info("============email sending failed sendQuotationStatusEmail method of QUOTE_ACCEPT & QUOTE_REJECTION condition catch block========");
          } 
        } 
        continue;
      } 
      if (schdulerData.getEmailEvent().equalsIgnoreCase("QUOTE_ACCEPT_CUSTOMER") || schdulerData
        .getEmailEvent().equalsIgnoreCase("QUOTE_REJECTION_CUSTOMER")) {
        logger.info("============Inside QUOTE_ACCEPT_CUSTOMER & QUOTE_REJECTION_CUSTOMER condition==========1");
        try {
          NimaiClient customerDetails;
          NimaiLC custTransactionDetails = this.userDao.getTransactioDetailsByTransId(schdulerData.getTransactionid());
          logger.info("============Inside QUOTE_ACCEPT & QUOTE_REJECTION condition==========12" + custTransactionDetails
              .toString());
          QuotationMaster bnakQuotationDetails = this.userDao.getDetailsByQuoteId(schdulerData.getQuotationId().intValue());
          logger.info("============Inside QUOTE_ACCEPT & QUOTE_REJECTION condition==========13" + bnakQuotationDetails
              .toString());
          if (schdulerData.getCustomerEmail() == null || schdulerData.getCustomerEmail().isEmpty()) {
            NimaiClient acnttSrceDtsByAssoUser = this.userDao.getCustDetailsByUserId(schdulerData.getCustomerid());
            customerDetails = this.userDao.getCustDetailsByUserId(acnttSrceDtsByAssoUser.getAccountSource());
            logger.info("============Inside QUOTE_ACCEPT & QUOTE_REJECTION condition 14==========" + customerDetails
                .toString());
          } else {
            logger.info("============Inside QUOTE_ACCEPT & QUOTE_REJECTION condition 15==========");
            customerDetails = this.userDao.getCustDetailsByUserId(schdulerData.getCustomerid());
          } 
          NimaiClient bankDetails = this.userDao.getCustDetailsByUserId(schdulerData.getBankUserid());
          logger.info("============Inside QUOTE_ACCEPT & QUOTE_REJECTION condition==========16" + bankDetails
              .toString());
          if (custTransactionDetails != null && bnakQuotationDetails != null) {
            try {
              logger.info("##########################method for sending the email to customer" + schdulerData
                  .getQuotationId() + "########################");
              QuotationMaster bankQuotationDetails = this.userDao.getDetailsByQuoteId(schdulerData.getQuotationId().intValue());
              if (bankQuotationDetails != null) {
                String savingsDetails = "";
                TransactionSaving trSavingDetails = this.userDao.getSavingDetails(schdulerData.getTransactionid());
                if (trSavingDetails == null) {
                  savingsDetails = "0";
                } else {
                  savingsDetails = String.valueOf(trSavingDetails.getSavings());
                } 
                logger.info("============Inside QUOTE_ACCEPT & QUOTE_REJECTION condition savings:==========" + bankDetails
                    
                    .toString());
                if (schdulerData.getCustomerEmail() == null || schdulerData.getCustomerEmail().isEmpty()) {
                  this.emailInsert.sendQuotationStatusEmailToCust(schdulerData.getEmailEvent(), schdulerData, customerDetails
                      .getEmailAddress(), bankQuotationDetails, custTransactionDetails, bankDetails, savingsDetails, customerDetails);
                } else {
                  NimaiClient custDetails = this.userDao.getcuDetailsByEmail(custTransactionDetails.getBranchUserEmail());
                  if (custDetails == null) {
                    String passcodeUserEmail = custTransactionDetails.getBranchUserEmail();
                    NimaiMBranch branchDetails = this.userDao.getBrDetailsByEmail(passcodeUserEmail);
                    this.emailInsert.sendQuotationStatusEmailToPassCodeCust(schdulerData.getEmailEvent(), schdulerData, passcodeUserEmail, bankQuotationDetails, custTransactionDetails, bankDetails, savingsDetails, branchDetails);
                  } else {
                    this.emailInsert.sendQuotationStatusEmailToCust(schdulerData.getEmailEvent(), schdulerData, schdulerData
                        .getCustomerEmail(), bankQuotationDetails, custTransactionDetails, bankDetails, savingsDetails, customerDetails);
                  } 
                } 
                try {
                  logger.info("============Inside QUOTE_ACCEPT & QUOTE_REJECTION condition schedulerId:==========" + schdulerData
                      
                      .getScedulerid());
                  this.userDao.updateEmailFlag(schdulerData.getScedulerid());
                } catch (Exception e) {
                  e.printStackTrace();
                  continue;
                } 
              } else {
                logger.info("Inside sendTransactionStatusToBanksByScheduled quotation id not found");
                try {
                  emailStatus = "Quote_Id_NOT_Register";
                  this.userDao.updateInvalidIdEmailFlag(schdulerData.getScedulerid(), emailStatus);
                  response.setMessage("Details not found");
                } catch (Exception e) {
                  e.printStackTrace();
                  continue;
                } 
              } 
            } catch (Exception e) {
              if (e instanceof NullPointerException) {
                response.setMessage("Email Sending failed");
                EmailErrorCode emailError = new EmailErrorCode("EmailNull", Integer.valueOf(409));
                continue;
              } 
            } 
            logger.info("============Inside QUOTE_ACCEPT & QUOTE_REJECTION condition schdulerData.getScedulerid():==========" + schdulerData
                
                .getScedulerid());
            continue;
          } 
          logger.info("=====Inside QUOTE_ACCEPT or QUOTE_REJECTION quotation id not found======");
          emailStatus = "Quote_Id_NOT_Register";
          try {
            this.userDao.updateInvalidIdEmailFlag(schdulerData.getScedulerid(), emailStatus);
          } catch (Exception e) {
            e.printStackTrace();
            logger.info("=======Inside QUOTE_ACCEPT & QUOTE_REJECTION condition in updateInvalidflag method======");
          } 
        } catch (Exception e) {
          if (e instanceof NullPointerException) {
            response.setMessage("Email Sending failed");
            EmailErrorCode emailError = new EmailErrorCode("EmailNull", Integer.valueOf(409));
            logger.info("============email sending failed sendQuotationStatusEmail method of QUOTE_ACCEPT & QUOTE_REJECTION condition catch block========");
          } 
        } 
        continue;
      } 
      if (schdulerData.getEmailEvent().equalsIgnoreCase("Winning_Quote_Data")) {
        NimaiLC custTransactionDetails = this.userDao.getTransactioDetailsByTransId(schdulerData.getTransactionid());
        logger.info("=====Inside Winning_Quote_Data transaction id not found" + schdulerData
            .getTransactionid());
        System.out.println("===========Winning_Quote_Data trId:" + schdulerData.getTransactionid());
        if (custTransactionDetails != null) {
          List<QuotationMaster> rejectedBankDetails = this.userDao.getBankQuoteList(schdulerData.getTransactionid());
          System.out.println("rejected details" + rejectedBankDetails.toString());
          NimaiClient customerDetails = this.userDao.getCustDetailsByUserId(schdulerData.getCustomerid());
          for (QuotationMaster details : rejectedBankDetails) {
            System.out.println("particular data" + details.toString());
            NimaiEmailSchedulerAlertToBanks rejectBankDetails = new NimaiEmailSchedulerAlertToBanks();
            NimaiClient bankDetails = this.userDao.getCustDetailsByUserId(details.getBankUserId());
            rejectBankDetails.setBankUserName(bankDetails.getFirstName());
            rejectBankDetails.setTransactionid(schdulerData.getTransactionid());
            rejectBankDetails.setCustomerCompanyName(customerDetails.getCompanyName());
            rejectBankDetails.setAmount(String.valueOf(custTransactionDetails.getlCValue()));
            rejectBankDetails.setCurrency(custTransactionDetails.getlCCurrency());
            rejectBankDetails.setBanksEmailID(bankDetails.getEmailAddress());
            rejectBankDetails.setEmailEvent("Winning_Quote_Alert_toBanks");
            rejectBankDetails.setEmailFlag("Pending");
            this.userDao.saveBankSchData(rejectBankDetails);
          } 
          logger.info("============Inside Winning_Quote_Data & Winning_Quote_Data condition schdulerData.getScedulerid():==========" + schdulerData
              
              .getScedulerid());
          try {
            this.userDao.updateEmailFlag(schdulerData.getScedulerid());
          } catch (Exception e) {
            e.printStackTrace();
          } 
          continue;
        } 
        logger.info("Inside Winning_Quote_Data transaction id not found");
        response.setMessage("Details not found");
        emailStatus = "Tr_Id_NOT_Register";
        try {
          this.userDao.updateInvalidIdEmailFlag(schdulerData.getScedulerid(), emailStatus);
        } catch (Exception e) {
          e.printStackTrace();
        } 
        continue;
      } 
      if (schdulerData.getEmailEvent().equalsIgnoreCase("Winning_Quote_Alert_toBanks")) {
        try {
          this.emailInsert.sendWinningQuoteToAlertBank(schdulerData.getEmailEvent(), schdulerData);
          logger.info("============Inside Winning_Quote_Alert_toBanks condition schdulerData.getScedulerid():==========" + schdulerData
              
              .getScedulerid());
          this.userDao.updateEmailFlag(schdulerData.getScedulerid());
          try {
            this.userDao.updateEmailFlag(schdulerData.getScedulerid());
          } catch (Exception e) {
            e.printStackTrace();
          } 
        } catch (Exception e) {
          e.printStackTrace();
          logger.info("============Inside catch block Winning_Quote_Alert_toBanks condition schdulerData.getScedulerid():==========" + schdulerData
              
              .getScedulerid());
        } 
        continue;
      } 
      if (schdulerData.getEmailEvent().equalsIgnoreCase("LC_REOPENING_ALERT_ToBanks")) {
        try {
          int isAssociatedUser;
          NimaiLC custTransactionDetails = this.userDao.getTransactioDetailsByTransId(schdulerData.getTransactionid());
          NimaiClient customerDetails = this.userDao.getCustDetailsByUserId(custTransactionDetails.getUserId());
          if (customerDetails.getIsAssociated() == 1) {
            isAssociatedUser = 1;
          } else {
            isAssociatedUser = 0;
            customerDetails = this.userDao.getCustDetailsByUserId(schdulerData.getCustomerid());
          } 
          logger.info("================LC_REOPENING_ALERT_ToBanks Customer:" + customerDetails.getUserid());
          NimaiClient bankDetails = this.userDao.getCustDetailsByUserId(schdulerData.getBankUserid());
          logger.info("================LC_REOPENING_ALERT_ToBanks Bank:" + bankDetails.getUserid());
          if (custTransactionDetails != null && customerDetails != null) {
            if (isAssociatedUser == 1) {
              this.emailInsert.sendLcReopeningToAlertBankNew(schdulerData.getEmailEvent(), schdulerData, schdulerData
                  .getBanksEmailID(), custTransactionDetails, bankDetails, customerDetails);
            } else {
              this.emailInsert.sendLcReopeningToAlertBankNew(schdulerData.getEmailEvent(), schdulerData, schdulerData
                  .getBanksEmailID(), custTransactionDetails, bankDetails, customerDetails);
            } 
          } else {
            logger.info("Inside LC_REOPENING_ALERT_ToBanks quotation id not found");
            response.setMessage("Details not found");
            emailStatus = "Quote_Id_NOT_Register";
            try {
              this.userDao.updateInvalidIdEmailFlag(schdulerData.getScedulerid(), emailStatus);
            } catch (Exception e) {
              e.printStackTrace();
              continue;
            } 
          } 
          logger.info("============Inside LC_REOPENING_ALERT_ToBanks Customer condition schdulerData.getScedulerid():==========" + schdulerData
              
              .getScedulerid());
          try {
            this.userDao.updateEmailFlag(schdulerData.getScedulerid());
          } catch (Exception e) {
            e.printStackTrace();
          } 
        } catch (Exception e) {
          if (e instanceof NullPointerException) {
            response.setMessage("Email Sending failed");
            EmailErrorCode emailError = new EmailErrorCode("EmailNull", Integer.valueOf(409));
          } 
        } 
        continue;
      } 
      if (schdulerData.getEmailEvent().equalsIgnoreCase("Bank_Details_tocustomer")) {
        logger.info("============Inside Bank_Details_tocustomer condition==========");
        QuotationMaster bnakQuotationDetails = this.userDao.getDetailsByQuoteId(schdulerData.getQuotationId().intValue());
        if (bnakQuotationDetails != null) {
          try {
            this.emailInsert.sendBankDetailstoCustomer(schdulerData.getEmailEvent(), schdulerData, schdulerData
                .getCustomerEmail(), bnakQuotationDetails);
            logger.info("============Inside Bank_Details_tocustomer Customer condition schdulerData.getScedulerid():==========" + schdulerData
                
                .getScedulerid());
            try {
              this.userDao.updateEmailFlag(schdulerData.getScedulerid());
            } catch (Exception e) {
              e.printStackTrace();
            } 
          } catch (Exception e) {
            if (e instanceof NullPointerException) {
              response.setMessage("Email Sending failed");
              EmailErrorCode emailError = new EmailErrorCode("EmailNull", Integer.valueOf(409));
            } 
          } 
          continue;
        } 
        logger.info("Inside sendTransactionStatusToBanksByScheduled quotation id not found");
        response.setMessage("Details not found");
        emailStatus = "Quote_Id_NOT_Register";
        try {
          this.userDao.updateInvalidIdEmailFlag(schdulerData.getScedulerid(), emailStatus);
        } catch (Exception e) {
          e.printStackTrace();
        } 
        continue;
      } 
      if (schdulerData.getEmailEvent().equalsIgnoreCase("QUOTE_PLACE_ALERT_ToBanks")) {
        logger.info("============Inside QUOTE_PLACE_ALERT_ToBanks condition==========");
        logger.info("@@@@@@@@@@@@@@@@##########################Quotatio Id:-" + schdulerData.getQuotationId() + "@@@@@@@@@@@@@@@@@@@@########################");
        QuotationMaster bnakQuotationDetails = this.userDao.getDetailsByQuoteId(schdulerData.getQuotationId().intValue());
        if (bnakQuotationDetails != null) {
          try {
            NimaiLC custTransactionDetails = this.userDao.getTransactioDetailsByTransId(schdulerData.getTransactionid());
            NimaiClient customerDetails = this.userDao.getCustDetailsByUserId(bnakQuotationDetails.getUserId());
            this.emailInsert.sendQuotePlaceEmailToBanks(schdulerData.getEmailEvent(), schdulerData, schdulerData
                .getBanksEmailID(), bnakQuotationDetails, customerDetails, custTransactionDetails);
            logger.info("============Inside QUOTE_PLACE_ALERT_ToBanks Customer condition schdulerData.getScedulerid():==========" + schdulerData
                
                .getScedulerid());
            try {
              this.userDao.updateEmailFlag(schdulerData.getScedulerid());
            } catch (Exception e) {
              e.printStackTrace();
              logger.info("============Inside QUOTE_PLACE_ALERT_ToBanks Customer condition schdulerData.getScedulerid():==========" + schdulerData
                  
                  .getScedulerid());
            } 
          } catch (Exception e) {
            if (e instanceof NullPointerException) {
              response.setMessage("Email Sending failed");
              EmailErrorCode emailError = new EmailErrorCode("EmailNull", Integer.valueOf(409));
              response.setData(emailError);
            } 
          } 
          continue;
        } 
        logger.info("Inside sendTransactionStatusToBanksByScheduled quotation id not found");
        emailStatus = "Quote_Id_NOT_Register";
        try {
          this.userDao.updateInvalidIdEmailFlag(schdulerData.getScedulerid(), emailStatus);
        } catch (Exception e) {
          e.printStackTrace();
        } 
        continue;
      } 
      if (schdulerData.getEmailEvent().equalsIgnoreCase("BId_ALERT_ToCustomer")) {
        try {
          String event = "BId_ALERT_ToCustomer";
          NimaiLC custTransactionDetails = this.userDao.getTransactioDetailsByTransId(schdulerData.getTransactionid());
          NimaiClient custDetailsByUserId = this.userDao.getCustDetailsByUserId(custTransactionDetails.getUserId());
          if (schdulerData.getCustomerEmail() == null || schdulerData
            .getCustomerEmail().isEmpty()) {
            System.out.println("INside branch user details null condition 1");
            NimaiClient custDetailsbyUserId = this.userDao.getCustDetailsByUserId(custTransactionDetails.getUserId());
            if (custDetailsbyUserId.getIsAssociated() == 1) {
              NimaiClient masterDetailsbyUserId = this.userDao.getCustDetailsByUserId(custDetailsbyUserId.getAccountSource());
              this.emailInsert.sendBidRecivedEmailToCust(event, schdulerData, schdulerData.getCustomerEmail(), masterDetailsbyUserId, custDetailsbyUserId.getIsAssociated(), custDetailsByUserId);
            } 
          } else {
            NimaiClient custDetails = this.userDao.getcuDetailsByEmail(custTransactionDetails.getBranchUserEmail());
            System.out.println("INside branch user details null condition 2");
            if (custDetails == null) {
              System.out.println("INside branch user details null condition 3");
              String passcodeUserEmail = custTransactionDetails.getBranchUserEmail();
              NimaiMBranch branchDetails = this.userDao.getBrDetailsByEmail(passcodeUserEmail);
              this.emailInsert.sendBidRecivedEmailToPassCodeCust(event, schdulerData, passcodeUserEmail, branchDetails);
            } else {
              System.out.println("INside branch user details null condition 4");
              System.out.println("INside branch user details null condition 4 NimaiClient custDetails" + custDetails.getUserid());
              System.out.println("INside branch user details null condition 4 NimaiClient custDetailsByUserId" + custDetailsByUserId.getUserid());
              this.emailInsert.sendBidRecivedEmailToCust(event, schdulerData, schdulerData.getCustomerEmail(), custDetails, custDetails.getIsAssociated(), custDetailsByUserId);
            } 
          } 
          logger.info("============Inside BId_ALERT_ToCustomer Customer condition schdulerData.getScedulerid():==========" + schdulerData
              
              .getScedulerid());
          try {
            this.userDao.updateEmailFlag(schdulerData.getScedulerid());
          } catch (Exception e) {
            e.printStackTrace();
          } 
        } catch (Exception e) {
          if (e instanceof NullPointerException) {
            e.printStackTrace();
            response.setMessage("Email Sending failed");
            EmailErrorCode emailError = new EmailErrorCode("EmailNull", Integer.valueOf(409));
          } 
        } 
        continue;
      } 
      if (schdulerData.getEmailEvent().equalsIgnoreCase("LC_UPLOAD(DATA)") || schdulerData
        .getEmailEvent().equalsIgnoreCase("LC_UPDATE(DATA)")) {
        logger.info("============Inside LC_UPLOAD(DATA) & LC_UPLOAD(DATA) condition==========" + schdulerData
            .getTransactionid());
        NimaiLC custTransactionDetails = this.userDao.getTransactioDetailsByTransId(schdulerData.getTransactionid());
        NimaiClient custDetails = this.userDao.getCustDetailsByUserId(schdulerData.getCustomerid());
        try {
          if (custTransactionDetails != null && custDetails != null) {
            if (schdulerData.getPasscodeuserEmail() == null || schdulerData
              .getPasscodeuserEmail().isEmpty()) {
              System.out.println("Inside the pascodeUser condition 1");
              if (custDetails.getIsAssociated() == 1) {
                System.out.println("Inside the pascodeUser condition 2");
                System.out.println("Inside the if condition pascodeUser condition");
                NimaiClient accountSourceDetails = this.userDao.getCustDetailsByUserId(custDetails.getAccountSource());
                System.out.println("Inside the pascodeUser condition 2" + custDetails.getUserid());
                System.out.println("Inside the if condition pascodeUser condition" + accountSourceDetails.getUserid());
                this.emailInsert.sendLcStatusEmailData(schdulerData, custTransactionDetails, custDetails, accountSourceDetails);
              } else {
                System.out.println("Inside the pascodeUser condition 3");
                NimaiClient accountSourcecustDetails = null;
                if (custDetails.getAccountType().equalsIgnoreCase("Subsidiary")) {
                  System.out.println("Inside the pascodeUser condition 8");
                  accountSourcecustDetails = this.userDao.getCustDetailsByUserId(custDetails.getAccountSource());
                  System.out.println("Inside the pascodeUser condition 801" + accountSourcecustDetails.getUserid());
                } 
                System.out.println("Inside the else condition pascodeUser condition 4");
                this.emailInsert.sendLcStatusEmailData(schdulerData, custTransactionDetails, custDetails, accountSourcecustDetails);
              } 
            } else {
              System.out.println("Inside the else condition pascodeUser custTransactionDetails condition 5");
              if (custDetails.getIsAssociated() == 1) {
                System.out.println("Inside the pascodeUser condition 6");
                System.out.println("Inside the else condition pascodeUser condition");
                NimaiClient accountSourceDetails = this.userDao.getCustDetailsByUserId(custDetails.getAccountSource());
                this.emailInsert.sendLcStatusEmailData(schdulerData, custTransactionDetails, custDetails, accountSourceDetails);
              } else {
                System.out.println("Inside the pascodeUser condition 7");
                NimaiMBranch branchDetails = this.userDao.getBrDetailsByEmail(schdulerData.getPasscodeuserEmail());
                this.emailInsert.sendLcStatusEmailDataToPaUser(schdulerData, custTransactionDetails, custDetails, branchDetails);
              } 
            } 
            logger.info("============Inside LC_UPLOAD Customer condition schdulerData.getScedulerid():==========" + schdulerData
                
                .getScedulerid());
            try {
              this.userDao.updateEmailFlag(schdulerData.getScedulerid());
            } catch (Exception e) {
              logger.info("============Inside LC_UPLOAD Customer condition schdulerData.getScedulerid():==========" + schdulerData
                  
                  .getScedulerid());
              e.printStackTrace();
            } 
            continue;
          } 
          logger.info("Inside sendTransactionStatusToBanksByScheduled transaction id not found");
          emailStatus = "Tr_Id_Not_Register";
          this.userDao.updateInvalidIdEmailFlag(schdulerData.getScedulerid(), emailStatus);
        } catch (Exception e) {
          if (e instanceof NullPointerException) {
            e.printStackTrace();
            response.setMessage("Email Sending failed");
            EmailErrorCode emailError = new EmailErrorCode("EmailNull", Integer.valueOf(409));
          } 
        } 
        continue;
      } 
      if (schdulerData.getEmailEvent().equalsIgnoreCase("LC_UPLOAD_ALERT_ToBanks") || schdulerData
        .getEmailEvent().equalsIgnoreCase("LC_UPDATE_ALERT_ToBanks")) {
        logger.info("============Inside LC_UPLOAD_ALERT_ToBanks & LC_UPDATE_ALERT_ToBankscondition==========" + schdulerData
            .getTransactionid());
        NimaiLC custTransactionDetails = this.userDao.getTransactioDetailsByTransId(schdulerData.getTransactionid());
        NimaiClient custDetails = this.userDao.getCustDetailsByUserId(schdulerData.getCustomerid());
        if (custTransactionDetails != null && custDetails != null) {
          if (schdulerData.getTransactionEmailStatusToBanks() == null) {
            try {
              if (custDetails.getIsAssociated() == 1) {
                NimaiClient accountSourceDetails = this.userDao.getCustDetailsByUserId(custDetails.getAccountSource());
                this.emailInsert.sendAssTransactionStatusToBanks(schdulerData, custTransactionDetails, accountSourceDetails, custDetails);
              } else {
                this.emailInsert.sendTransactionStatusToBanks(schdulerData, custTransactionDetails, custDetails);
              } 
              logger.info("============Inside LC_UPDATE_ALERT_ToBankscondition Customer condition schdulerData.getScedulerid():==========" + schdulerData
                  
                  .getScedulerid());
              try {
                this.userDao.updateEmailFlag(schdulerData.getScedulerid());
              } catch (Exception e) {
                e.printStackTrace();
                continue;
              } 
            } catch (Exception e) {
              if (e instanceof NullPointerException) {
                e.printStackTrace();
                response.setMessage("Email Sending failed");
                EmailErrorCode emailError = new EmailErrorCode("EmailNull", Integer.valueOf(409));
                continue;
              } 
            } 
          } else {
            try {
              if (custDetails.getIsAssociated() == 1) {
                NimaiClient accountSourceDetails = this.userDao.getCustDetailsByUserId(custDetails.getAccountSource());
                this.emailInsert.sendAssTransactionStatusToBanks(schdulerData, custTransactionDetails, accountSourceDetails, custDetails);
              } else {
                this.emailInsert.sendTransactionStatusToBanks(schdulerData, custTransactionDetails, custDetails);
              } 
              try {
                this.userDao.updateTrStatusEmailFlag(Integer.parseInt(schdulerData.getTrScheduledId()));
                logger.info("============Inside LC_UPDATE_ALERT_ToBankscondition Customer condition schdulerData.getScedulerid():==========" + schdulerData
                    
                    .getScedulerid());
              } catch (Exception e) {
                e.printStackTrace();
                continue;
              } 
              try {
                this.userDao.updateBankEmailFlag(schdulerData.getScedulerid());
              } catch (Exception e) {
                e.printStackTrace();
                continue;
              } 
            } catch (Exception e) {
              if (e instanceof NullPointerException) {
                response.setMessage("Email Sending failed");
                EmailErrorCode emailError = new EmailErrorCode("EmailNull", Integer.valueOf(409));
                continue;
              } 
            } 
          } 
        } else {
          logger.info("Inside sendTransactionStatusToBanksByScheduled transaction id not found");
          emailStatus = "Tr_Id_not_Register";
          try {
            logger.info("============Inside LC_UPDATE_ALERT_ToBankscondition Customer condition transaction id not found schdulerData.getScedulerid():==========" + schdulerData
                
                .getScedulerid());
            this.userDao.updateInvalidIdEmailFlag(schdulerData.getScedulerid(), emailStatus);
          } catch (Exception e) {
            e.printStackTrace();
            continue;
          } 
        } 
        try {
          InetAddress ip = InetAddress.getLocalHost();
          System.out.println("=============================Current IP address========== : " + ip.getHostAddress());
        } catch (UnknownHostException e) {
          e.printStackTrace();
        } 
      } 
    } 
  }
  
  public ResponseEntity<?> sendTransactionStatusToBanks(AlertToBanksBean alertBanksBean) {
    logger.info("=======sendTransactionStatusToBanks method invoked=======");
    GenericResponse response = new GenericResponse();
    List<EmailSendingDetails> emailList = alertBanksBean.getBankEmails();
    for (EmailSendingDetails emailIds : emailList) {
      String errorString = this.resetUserValidator.banksEmailValidation(alertBanksBean, emailIds.getEmailId());
      if (errorString.equalsIgnoreCase("Success")) {
        try {
          this.emailInsert.sendTransactionStatusToBanks(alertBanksBean.getEvent(), alertBanksBean, emailIds
              .getEmailId());
        } catch (Exception e) {
          if (e instanceof NullPointerException) {
            response.setMessage("Email Sending failed");
            EmailErrorCode emailError = new EmailErrorCode("EmailNull", Integer.valueOf(409));
            response.setData(emailError);
            return new ResponseEntity(response, HttpStatus.CONFLICT);
          } 
        } 
        continue;
      } 
      response.setErrCode("EX000");
      response.setMessage(ErrorDescription.getDescription("EX000") + errorString.toString());
      return new ResponseEntity(response, HttpStatus.BAD_REQUEST);
    } 
    response.setMessage(ErrorDescription.getDescription("ASA002"));
    return new ResponseEntity(response, HttpStatus.OK);
  }
  
  public ResponseEntity<?> sendQuotationStatusToBanks(QuotationAlertRequest quotationReq) {
    logger.info("==========sendQuotationStatusToBanks method invoked=========");
    GenericResponse response = new GenericResponse();
    String errorString = this.resetUserValidator.quotationAlertValidation(quotationReq);
    if (errorString.equalsIgnoreCase("Success"))
      try {
        this.emailInsert.sendQuotationStatusEmail(quotationReq.getEvent(), quotationReq, quotationReq
            .getBankEmailId());
        response.setMessage(ErrorDescription.getDescription("ASA002"));
        return new ResponseEntity(response, HttpStatus.OK);
      } catch (Exception e) {
        if (e instanceof NullPointerException) {
          response.setMessage("Email Sending failed");
          EmailErrorCode emailError = new EmailErrorCode("EmailNull", Integer.valueOf(409));
          response.setData(emailError);
          return new ResponseEntity(response, HttpStatus.CONFLICT);
        } 
        return new ResponseEntity(response, HttpStatus.BAD_REQUEST);
      }  
    response.setErrCode("EX000");
    response.setMessage(ErrorDescription.getDescription("EX000") + errorString.toString());
    return new ResponseEntity(response, HttpStatus.BAD_REQUEST);
  }
}
