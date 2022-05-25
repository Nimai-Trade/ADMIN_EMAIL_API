package com.nimai.email.service;

import com.nimai.email.api.GenericResponse;
import com.nimai.email.bean.AdminBean;
import com.nimai.email.bean.BankDetailsBean;
import com.nimai.email.bean.BranchUserPassCodeBean;
import com.nimai.email.bean.BranchUserRequest;
import com.nimai.email.bean.InvoiceBeanResponse;
import com.nimai.email.bean.ResetPassBean;
import com.nimai.email.bean.SubsidiaryBean;
import com.nimai.email.bean.UserIdentificationBean;
import com.nimai.email.bean.UserRegistrationBean;
import com.nimai.email.dao.EmailConfigurationdaoImpl;
import com.nimai.email.dao.EmailProcessImpl;
import com.nimai.email.dao.UserServiceDao;
import com.nimai.email.entity.EmailComponentMaster;
import com.nimai.email.entity.NimaiClient;
import com.nimai.email.entity.NimaiEmailScheduler;
import com.nimai.email.entity.NimaiFSubsidiaries;
import com.nimai.email.entity.NimaiLC;
import com.nimai.email.entity.NimaiMBranch;
import com.nimai.email.entity.NimaiMLogin;
import com.nimai.email.entity.NimaiMRefer;
import com.nimai.email.entity.NimaiSubscriptionDetails;
import com.nimai.email.entity.NimaiSubscriptionVas;
import com.nimai.email.entity.NimaiSystemConfig;
import com.nimai.email.entity.NimaiToken;
import com.nimai.email.entity.OnlinePayment;
import com.nimai.email.repository.NimaiEmailSchedulerRepo;
import com.nimai.email.repository.NimaiMBranchRepository;
import com.nimai.email.repository.NimaiMCustomerRepo;
import com.nimai.email.repository.NimaiTokenRepository;
import com.nimai.email.repository.OnlinePaymentRepository;
import com.nimai.email.repository.SubscriptionDetailsRepository;
import com.nimai.email.repository.SubscriptionVasRepository;
import com.nimai.email.repository.nimaiSystemConfigRepository;
import com.nimai.email.service.UserService;
import com.nimai.email.utility.EmaiInsert;
import com.nimai.email.utility.EmailErrorCode;
import com.nimai.email.utility.EmailSend;
import com.nimai.email.utility.ErrorDescription;
import com.nimai.email.utility.ExcelUtility;
import com.nimai.email.utility.InvoiceTemplate;
import com.nimai.email.utility.ResetUserValidation;
import com.nimai.email.utility.Utils;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import javax.persistence.EntityManagerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class UserServiceImpl implements UserService {
  private static Logger logger = LoggerFactory.getLogger(com.nimai.email.service.UserServiceImpl.class);
  
  @Autowired
  JdbcTemplate jdbcTemplate;
  
  @Autowired
  EmailConfigurationdaoImpl emailConfigurationDAO;
  
  @Autowired
  OnlinePaymentRepository payRepo;
  
  @Autowired
  UserServiceDao userDao;
  
  @Autowired
  EntityManagerFactory em;
  
  @Autowired
  private EmaiInsert emailInsert;
  
  @Autowired
  EmailProcessImpl emailProcessorImpl;
  
  @Autowired
  EmailSend emailSend;
  
  @Autowired
  private Utils utility;
  
  @Autowired
  EmailConfigurationdaoImpl emailConfigurationDAOImpl;
  
  @Autowired
  NimaiEmailSchedulerRepo schRepo;
  
  @Autowired
  SubscriptionVasRepository vasRepo;
  
  @Autowired
  SubscriptionDetailsRepository sPlanRepo;
  
  @Autowired
  nimaiSystemConfigRepository systemConfig;
  
  @Autowired
  GenericResponse response;
  
  @Autowired
  ResetUserValidation resetUserValidator;
  
  @Value("${accountActivation.url}")
  private String accountActivationLink;
  
  @Value("${forgotPassword.url}")
  private String forgorPasswordLink;
  
  @Value("${subsidiaryLink.url}")
  private String subAccountActivationLink;
  
  @Value("${branchUserLink.url}")
  private String branchUserActivationLink;
  
  @Value("${referUserLink.url}")
  private String referAccountActivationLink;
  
  @Value("${adminForgotPassLink.url}")
  private String adminForgotPassLink;
  
  @Autowired
  NimaiMBranchRepository brRepo;
  
  @Autowired
  NimaiMCustomerRepo custRepo;
  
  @Autowired
  NimaiTokenRepository tokenRepo;
  
  public boolean checkUserId(String userId) {
    return false;
  }
  
  public boolean checkEmailId(String emailAddress) {
    return false;
  }
  
  public ResponseEntity<Object> sendEmail(UserRegistrationBean userRegistratinBean) throws Exception {
    GenericResponse response = new GenericResponse();
    NimaiMLogin nimaiLogin = null;
    logger.info("=======Inside sendEmail method=========" + userRegistratinBean.toString());
    String errorString = this.resetUserValidator.Validation(userRegistratinBean);
    if (errorString.equalsIgnoreCase("success")) {
      try {
        NimaiClient clientUseId = this.userDao.getcliDetailsByEmailId(userRegistratinBean.getEmail());
        nimaiLogin = this.userDao.getCustomerDetailsByUserID(clientUseId.getUserid());
        if (nimaiLogin != null && clientUseId != null) {
          
          java.util.Date currentDate =new java.util.Date();
          String forgotTokenKey = "";
          String tokenKey = Utils.generatePasswordResetToken();
          if (userRegistratinBean.getEvent().equalsIgnoreCase("FORGOT_PASSWORD")) {
        	  java.util.Date  ForgottokenExpiry = this.utility.getForgotPassExpiryLink();
            forgotTokenKey = "FO_".concat(Utils.generatePasswordResetToken());
            nimaiLogin.setToken(forgotTokenKey);
            nimaiLogin.setTokenExpiryDate(ForgottokenExpiry);
          } else {
            String email = this.systemConfig.findByLinkDays();
            java.util.Date tokenExpiry = this.utility.getLinkExpiry(email);
            nimaiLogin.setTokenExpiryDate(tokenExpiry);
            nimaiLogin.setToken(tokenKey);
          } 
          nimaiLogin.setInsertedDate(currentDate);
          this.userDao.update(nimaiLogin);
          try {
            String parentEmailId = "";
            if (clientUseId.getAccountType().equalsIgnoreCase("BANKUSER")) {
              NimaiClient parentClient = this.userDao.getClientDetailsbyUserId(clientUseId.getAccountSource());
              parentEmailId = parentClient.getEmailAddress();
            } 
            String aCLlink = this.accountActivationLink + tokenKey;
            String fPlink = this.forgorPasswordLink + forgotTokenKey;
            NimaiClient nimaiClientdetails = this.userDao.getClientDetailsbyUserId(clientUseId.getUserid());
            if (userRegistratinBean.getEvent().equalsIgnoreCase("ACCOUNT_ACTIVATE")) {
              if (clientUseId.getAccountSource().equalsIgnoreCase("RE32221") || clientUseId.getAccountSource().equalsIgnoreCase("RE10099")) {
                NimaiClient parentReferDetails = this.userDao.getClientDetailsbyUserId(clientUseId.getAccountSource());
                NimaiEmailScheduler schedulerData = new NimaiEmailScheduler();
                schedulerData.setUserid(clientUseId.getUserid());
                schedulerData.setUserName(clientUseId.getFirstName());
                Calendar cal1 = Calendar.getInstance();
                java.util.Date addReferdate = cal1.getTime();
                schedulerData.setEmailStatus("Pending");
                schedulerData.setEvent("ADD_REFER_ALERT_TO_PARENT");
                schedulerData.setInsertedDate(addReferdate);
                schedulerData.setEmailId(parentReferDetails.getEmailAddress());
                try {
                  this.userDao.saveSubDetails(schedulerData);
                } catch (Exception e) {
                  e.printStackTrace();
                  response.setMessage("Exception occure  saving data in schedulerTable");
                  return new ResponseEntity(response, HttpStatus.OK);
                } 
              } 
              this.emailInsert.resetPasswordEmail(aCLlink, userRegistratinBean, nimaiLogin, nimaiClientdetails, parentEmailId);
            } else if (userRegistratinBean.getEvent().equalsIgnoreCase("FORGOT_PASSWORD")) {
              this.emailInsert.resetForgorPasswordEmail(fPlink, userRegistratinBean, userRegistratinBean
                  .getEmail(), clientUseId);
            } 
            response.setErrCode("ASA002");
            response.setMessage(ErrorDescription.getDescription("ASA002"));
            return new ResponseEntity(response, HttpStatus.OK);
          } catch (Exception e) {
            e.printStackTrace();
            if (e instanceof NullPointerException) {
              response.setMessage("No email address provided for User");
              EmailErrorCode emailError = new EmailErrorCode("EmailNull", Integer.valueOf(409));
              response.setData(emailError);
              return new ResponseEntity(response, HttpStatus.CONFLICT);
            } 
          } 
        } 
      } catch (Exception e) {
        e.printStackTrace();
      } 
    } else {
      response.setErrCode("EXE000");
      response.setMessage(ErrorDescription.getDescription("EXE000") + errorString.toString());
      return new ResponseEntity(response, HttpStatus.BAD_REQUEST);
    } 
    response.setErrCode("ASA005");
    response.setMessage(ErrorDescription.getDescription("ASA005"));
    return new ResponseEntity(response, HttpStatus.BAD_REQUEST);
  }
  
  @Scheduled(fixedDelay = 30000L)
  @Transactional(propagation = Propagation.NESTED)
  public void sendAccountEmail() throws Exception {
    String emailStatus = "";
    List<NimaiEmailScheduler> emailDetailsScheduled = this.userDao.getSchedulerDetails();
    for (NimaiEmailScheduler schdulerData : emailDetailsScheduled) {
      if (schdulerData.getEvent().equalsIgnoreCase("Cust_Splan_email")) {
        try {
          NimaiClient clientUseId = this.userDao.getClientDetailsbyUserId(schdulerData.getUserid());
          logger.info("============Inside Cust_Splan_email condition==========" + schdulerData.getUserid());
          if (clientUseId != null) {
            NimaiSubscriptionDetails subDetails = this.userDao.getSplanDetails(schdulerData.getSubscriptionId(), schdulerData
                .getUserid());
            logger.info("===========CUST_SPLAN_EVENT:" + subDetails.getSubscriptionId());
            logger.info("===========CUST_SPLAN_EVENT:" + subDetails.toString());
            if (subDetails.getPaymentMode().equalsIgnoreCase("Credit")) {
              String status = "Success";
              String aStatus = "Approved";
              OnlinePayment paymentDetails = this.payRepo.findByuserId(schdulerData.getUserid(), status, aStatus);
              if (paymentDetails == null) {
                String planFailureName = "SPLAN_FAILURE";
                this.emailInsert.sendPaymentFailureEmail(schdulerData, clientUseId, subDetails, planFailureName);
              } else {
                NimaiSystemConfig configDetails = null;
                configDetails = this.systemConfig.findBySystemId(1);
                NimaiSystemConfig configDetail = null;
                try {
                  configDetail = (NimaiSystemConfig)this.systemConfig.getOne(Integer.valueOf(14));
                  System.out
                    .println("configDetail image value" + configDetail.getSystemEntityValue());
                  this.emailInsert.sendCustSPlanEmail(schdulerData, clientUseId, subDetails, paymentDetails, configDetails, configDetail
                      .getSystemEntityValue());
                } catch (Exception e) {
                  e.printStackTrace();
                } 
              } 
            } 
            try {
              this.userDao.updateEmailStatus(schdulerData.getAccountSchedulerId());
              logger.info("===========CUST_SPLAN_EVENT  updated id is:" + schdulerData
                  .getAccountSchedulerId());
            } catch (Exception e) {
              e.printStackTrace();
              logger.info("===========CUST_SPLAN_EVENT:" + e.toString());
              logger.info("===========CUST_SPLAN_EVENT not updated id is:" + schdulerData
                  .getAccountSchedulerId());
            } 
          } else {
            logger.info("Inside Cust_Splan_email condition user id not found");
            emailStatus = "UserId_NOT_Registered(Cust_Splan_email)";
            this.userDao.updateInvalidIdEmailFlag(schdulerData.getAccountSchedulerId(), emailStatus);
            this.response.setMessage("Details not found");
          } 
        } catch (Exception e) {
          if (e instanceof NullPointerException) {
            EmailErrorCode emailError = new EmailErrorCode("EmailNull", Integer.valueOf(409));
            logger.info("Customer Splan catch block" + emailError);
          } 
        } 
      } else if (schdulerData.getEvent().equalsIgnoreCase("Cust_Splan_email_Wire")) {
        try {
          NimaiClient clientUseId = this.userDao.getClientDetailsbyUserId(schdulerData.getUserid());
          logger.info("============Inside Cust_Splan_email condition==========" + schdulerData.getUserid());
          if (clientUseId != null && clientUseId.getPaymentStatus().equalsIgnoreCase("Approved")) {
            NimaiSubscriptionDetails subDetails = this.userDao.getSplanDetails(schdulerData.getSubscriptionId(), schdulerData
                .getUserid());
            logger.info("===========CUST_SPLAN_EVENT:" + subDetails.getSubscriptionId());
            logger.info("===========CUST_SPLAN_EVENT:" + subDetails.toString());
            OnlinePayment paymentDetails = new OnlinePayment();
            NimaiSystemConfig configDetails = null;
            configDetails = this.systemConfig.findBySystemId(1);
            NimaiSystemConfig configDetail = null;
            configDetail = (NimaiSystemConfig)this.systemConfig.getOne(Integer.valueOf(14));
            this.emailInsert.sendCustSPlanEmail(schdulerData, clientUseId, subDetails, paymentDetails, configDetails, configDetail
                .getSystemEntityValue());
            try {
              this.userDao.updateEmailStatus(schdulerData.getAccountSchedulerId());
              logger.info("===========CUST_SPLAN_EVENT  updated id is:" + schdulerData
                  .getAccountSchedulerId());
            } catch (Exception e) {
              e.printStackTrace();
              logger.info("===========CUST_SPLAN_EVENT:" + e.toString());
              logger.info("===========CUST_SPLAN_EVENT not updated id is:" + schdulerData
                  .getAccountSchedulerId());
            } 
          } else {
            logger.info("Inside Cust_Splan_email condition user id not found");
            emailStatus = "UserId_NOT_Registered(Cust_Splan_email)";
            this.userDao.updateInvalidIdEmailFlag(schdulerData.getAccountSchedulerId(), emailStatus);
            this.response.setMessage("Details not found");
          } 
        } catch (Exception e) {
          if (e instanceof NullPointerException) {
            EmailErrorCode emailError = new EmailErrorCode("EmailNull", Integer.valueOf(409));
            logger.info("Custpmer Splan catch block" + emailError);
          } 
        } 
      } else if (schdulerData.getEvent().equalsIgnoreCase("Cust_Splan_email_Wire_Rejected")) {
        try {
          NimaiClient clientUseId = this.userDao.getClientDetailsbyUserId(schdulerData.getUserid());
          logger.info("============Inside Cust_Splan_email condition==========" + schdulerData.getUserid());
          if (clientUseId != null) {
            NimaiSubscriptionDetails subDetails = this.userDao.getSplanDetails(schdulerData.getSubscriptionId(), schdulerData
                .getUserid());
            logger.info("===========CUST_SPLAN_EVENT:" + subDetails.getSubscriptionId());
            logger.info("===========CUST_SPLAN_EVENT:" + subDetails.toString());
            OnlinePayment paymentDetails = new OnlinePayment();
            NimaiSystemConfig configDetails = null;
            configDetails = this.systemConfig.findBySystemId(1);
            NimaiSystemConfig configDetail = null;
            configDetail = (NimaiSystemConfig)this.systemConfig.getOne(Integer.valueOf(14));
            this.emailInsert.sendCustSPlanEmail(schdulerData, clientUseId, subDetails, paymentDetails, configDetails, configDetail
                .getSystemEntityValue());
            try {
              this.userDao.updateEmailStatus(schdulerData.getAccountSchedulerId());
              logger.info("===========CUST_SPLAN_EVENT  updated id is:" + schdulerData
                  .getAccountSchedulerId());
            } catch (Exception e) {
              e.printStackTrace();
              logger.info("===========CUST_SPLAN_EVENT:" + e.toString());
              logger.info("===========CUST_SPLAN_EVENT not updated id is:" + schdulerData
                  .getAccountSchedulerId());
            } 
          } else {
            logger.info("Inside Cust_Splan_email condition user id not found");
            emailStatus = "UserId_NOT_Registered(Cust_Splan_email)";
            this.userDao.updateInvalidIdEmailFlag(schdulerData.getAccountSchedulerId(), emailStatus);
            this.response.setMessage("Details not found");
          } 
        } catch (Exception e) {
          if (e instanceof NullPointerException) {
            EmailErrorCode emailError = new EmailErrorCode("EmailNull", Integer.valueOf(409));
            logger.info("Custpmer Splan catch block" + emailError);
          } 
        } 
      } else if (schdulerData.getEvent().equalsIgnoreCase("ADD_REFER_ALERT_TO_PARENT")) {
        try {
          if (schdulerData.getUserName() != null && schdulerData.getEmailId() != null) {
            this.emailInsert.sendReferEmailToParentUser(schdulerData);
            try {
              this.userDao.updateEmailStatus(schdulerData.getAccountSchedulerId());
            } catch (Exception e) {
              e.printStackTrace();
              continue;
            } 
          } else {
            logger.info("Inside ADD_REFER_TO_PARENT condition user id not found");
            emailStatus = "ParentId_Not_Save";
            this.userDao.updateInvalidIdEmailFlag(schdulerData.getAccountSchedulerId(), emailStatus);
          } 
        } catch (Exception e) {
          e.printStackTrace();
          continue;
        } 
      } else if (schdulerData.getEvent().equalsIgnoreCase("VAS_ADDED")) {
        try {
          NimaiClient clientUseId = this.userDao.getClientDetailsbyUserId(schdulerData.getUserid());
          logger.info("============Inside VAS_ADDED condition==========");
          logger.info("====================================UserId Id:-" + schdulerData.getUserid() + "====================================");
          if (clientUseId != null) {
            NimaiSubscriptionVas vasDetails = this.vasRepo.getVasDetails(clientUseId.getUserid(), schdulerData
                .getSubscriptionName());
            logger.info("====================vasdetails" + vasDetails.getVasId());
            NimaiSubscriptionDetails subDetails = this.userDao.getSplanDetails(vasDetails.getSubscriptionId(), schdulerData
                .getUserid());
            logger.info("==========================subdetails" + subDetails.getsPlSerialNUmber());
            if (!vasDetails.getMode().equalsIgnoreCase("Wire")) {
              String status = "Success";
              String aStatus = "Approved";
              try {
                OnlinePayment paymentDetails = this.payRepo.findByuserId(schdulerData.getUserid(), status, aStatus);
                if (paymentDetails == null) {
                  logger.info("==================null paymentdetails");
                  logger.info("=============Inside paymet failure mode===============");
                  String planFailureName = "VAS_FAILURE";
                  this.emailInsert.sendPaymentFailureEmail(schdulerData, clientUseId, subDetails, planFailureName);
                } else {
                  logger.info("================" + paymentDetails.toString());
                  logger.info("==================paymentdetails" + paymentDetails.getId());
                  logger.info("==============Inside paymet success==============");
                  NimaiSystemConfig configDetails = null;
                  configDetails = this.systemConfig.findBySystemId(1);
                  NimaiSystemConfig configDetail = null;
                  configDetail = (NimaiSystemConfig)this.systemConfig.getOne(Integer.valueOf(14));
                  this.emailInsert.sendVasEmail(schdulerData, clientUseId, vasDetails, subDetails, paymentDetails, configDetails, configDetail
                      .getSystemEntityValue());
                } 
                System.out.println("payment dettails outside the codition" + paymentDetails.toString());
                this.userDao.updateEmailStatus(schdulerData.getAccountSchedulerId());
              } catch (Exception e) {
                e.printStackTrace();
              } 
            } 
          } else {
            logger.info("Inside Cust_Splan_email condition user id not found");
            emailStatus = "UserId_NOT_Registered(Cust_Splan_email)";
            this.userDao.updateInvalidIdEmailFlag(schdulerData.getAccountSchedulerId(), emailStatus);
            this.response.setMessage("Details not found");
          } 
        } catch (Exception e) {
          if (e instanceof NullPointerException) {
            EmailErrorCode emailError = new EmailErrorCode("EmailNull", Integer.valueOf(409));
            logger.info("Vas added catch block" + emailError);
            continue;
          } 
        } 
      } else if (schdulerData.getEvent().equalsIgnoreCase("VAS_PLAN_WIRE_APPROVED")) {
        try {
          NimaiClient clientUseId = this.userDao.getClientDetailsbyUserId(schdulerData.getUserid());
          logger.info("============Inside VAS_ADDED condition==========");
          logger.info("====================================UserId Id:-" + schdulerData.getUserid() + "====================================");
          if (clientUseId != null) {
            NimaiSubscriptionVas vasDetails = this.userDao.getVasDetails(schdulerData.getSubscriptionName(), clientUseId
                .getUserid());
            logger.info("====================vasdetails" + vasDetails.getVasId());
            NimaiSubscriptionDetails subDetails = this.userDao.getSplanDetails(vasDetails.getSubscriptionId(), schdulerData
                .getUserid());
            logger.info("==========================subdetails" + subDetails.getsPlSerialNUmber());
            String status = "Success";
            String aStatus = "Approved";
            try {
              OnlinePayment paymentDetails = new OnlinePayment();
              logger.info("================" + paymentDetails.toString());
              logger.info("==================paymentdetails" + paymentDetails.getId());
              logger.info("==============Inside paymet success==============");
              NimaiSystemConfig configDetails = null;
              configDetails = this.systemConfig.findBySystemId(1);
              NimaiSystemConfig configDetail = null;
              configDetail = (NimaiSystemConfig)this.systemConfig.getOne(Integer.valueOf(14));
              this.emailInsert.sendVasEmail(schdulerData, clientUseId, vasDetails, subDetails, paymentDetails, configDetails, configDetail
                  .getSystemEntityValue());
              System.out.println("payment details outside the codition" + paymentDetails.toString());
              this.userDao.updateEmailStatus(schdulerData.getAccountSchedulerId());
            } catch (Exception e) {
              e.printStackTrace();
            } 
          } else {
            logger.info("Inside Cust_Splan_email condition user id not found");
            emailStatus = "UserId_NOT_Registered(Cust_Splan_email)";
            this.userDao.updateInvalidIdEmailFlag(schdulerData.getAccountSchedulerId(), emailStatus);
            this.response.setMessage("Details not found");
          } 
        } catch (Exception e) {
          if (e instanceof NullPointerException) {
            EmailErrorCode emailError = new EmailErrorCode("EmailNull", Integer.valueOf(409));
            logger.info("Vas added catch block" + emailError);
            continue;
          } 
        } 
      } else if (schdulerData.getEvent().equalsIgnoreCase("VAS_PLAN_WIRE_REJECTED")) {
        try {
          this.emailInsert.sendVasRejectedEmail(schdulerData);
          this.userDao.updateEmailStatus(schdulerData.getAccountSchedulerId());
        } catch (Exception e) {
          if (e instanceof NullPointerException) {
            EmailErrorCode emailError = new EmailErrorCode("EmailNull", Integer.valueOf(409));
            logger.info("Vas added catch block" + emailError);
            continue;
          } 
        } 
      } else if (schdulerData.getEvent().equalsIgnoreCase("SUBSIDIARY_ADDEDED")) {
        try {
          NimaiClient clientUseId = this.userDao.getClientDetailsbyUserId(schdulerData.getUserid());
          NimaiClient subDetails = this.userDao.getClientDetailsbyUserId(schdulerData.getSubUserId());
          logger.info("============Inside SUBSIDIARY_ADDEDED condition==========");
          logger.info("====================================SUBSIDIARY_ADDEDED:-" + schdulerData.getUserid() + "====================================");
          if (clientUseId != null && subDetails != null) {
            this.emailInsert.sendSubEmailToParentUser(schdulerData, clientUseId, clientUseId.getFirstName(), subDetails);
            this.userDao.updateEmailStatus(schdulerData.getAccountSchedulerId());
          } else {
            logger.info("Inside SUBSIDIARY_ADDEDED condition user id not found");
            emailStatus = "UserId_NOT_Registered(SUBSIDIARY_ADDEDED)";
            this.userDao.updateInvalidIdEmailFlag(schdulerData.getAccountSchedulerId(), emailStatus);
          } 
        } catch (Exception e) {
          if (e instanceof NullPointerException) {
            this.response.setMessage("Email Sending failed");
            EmailErrorCode emailError = new EmailErrorCode("EmailNull", Integer.valueOf(409));
            logger.info("Subsidiary added catch block" + emailError);
            continue;
          } 
        } 
      } else if (schdulerData.getEvent().equalsIgnoreCase("SUBSIDIARY_ACTIVATION_ALERT") || schdulerData
        .getEvent().equalsIgnoreCase("ASSOCIATE_ACTIVATION_ALERT")) {
        try {
          NimaiClient subsidiaryDetails = this.userDao.getClientDetailsbyUserId(schdulerData.getSubUserId());
          NimaiClient client = this.userDao.getClientDetailsbyUserId(schdulerData.getUserid());
          logger.info("============Inside SUBSIDIARY_ACTIVATION_ALERT condition==========");
          System.out.println("====================================SUBSIDIARY_ACTIVATION_ALERT:-" + schdulerData
              .getUserid() + "====================================");
          if (subsidiaryDetails != null && client != null) {
            this.emailInsert.sendSubEmailToParentUser(schdulerData, client, client.getFirstName(), subsidiaryDetails);
            this.userDao.updateEmailStatus(schdulerData.getAccountSchedulerId());
          } else {
            logger.info("Inside SUBSIDIARY_ACTIVATION_ALERT condition user id not found");
            emailStatus = "UserId_NOT_Registered(SUBSIDIARY_ACTIVATION_ALERT)";
            this.userDao.updateInvalidIdEmailFlag(schdulerData.getAccountSchedulerId(), emailStatus);
          } 
        } catch (Exception e) {
          if (e instanceof NullPointerException) {
            this.response.setMessage("Email Sending failed");
            EmailErrorCode emailError = new EmailErrorCode("EmailNull", Integer.valueOf(409));
            logger.info("Subsidiary activation catch block" + emailError);
            continue;
          } 
        } 
      } else if (schdulerData.getEvent().equalsIgnoreCase("ASSIGN_NOTIFICATION_TO_RM")) {
        logger.info("============Inside ASSIGN_NOTIFICATION_TO_RM condition==========");
        logger.info("====================================ASSIGN_NOTIFICATION_TO_RM:-" + schdulerData.toString() + "====================================");
        try {
          if (schdulerData.getUserid().substring(0, 2).equalsIgnoreCase("CU"))
            this.emailInsert.sendEmailToRm(schdulerData); 
          this.userDao.updateEmailStatus(schdulerData.getAccountSchedulerId());
        } catch (Exception e) {
          if (e instanceof NullPointerException) {
            this.response.setMessage("Email Sending failed");
            EmailErrorCode emailError = new EmailErrorCode("EmailNull", Integer.valueOf(409));
            logger.info("ASSIGN_NOTI_TORM catch block" + emailError);
            continue;
          } 
        } 
      } else if (schdulerData.getEvent().equalsIgnoreCase("ASSIGN_NOTIFICATION_TO_RM_RE")) {
        logger.info("============Inside ASSIGN_NOTIFICATION_TO_RM_RE condition==========");
        logger.info("====================================ASSIGN_NOTIFICATION_TO_RM_RE:-" + schdulerData
            .toString() + "====================================");
        try {
          this.emailInsert.sendEmailToRmRE(schdulerData);
          this.userDao.updateEmailStatus(schdulerData.getAccountSchedulerId());
        } catch (Exception e) {
          if (e instanceof NullPointerException) {
            this.response.setMessage("Email Sending failed");
            EmailErrorCode emailError = new EmailErrorCode("EmailNull", Integer.valueOf(409));
            logger.info("ASSIGN_NOTIFICATION_TO_RM_RE catch block" + emailError);
          } 
        } 
      } else if (schdulerData.getEvent().equalsIgnoreCase("ASSIGN_NOTIFICATION_TO_RM_BC")) {
        logger.info("============Inside ASSIGN_NOTIFICATION_TO_RM_BC condition==========");
        logger.info("====================================ASSIGN_NOTIFICATION_TO_RM_BC:-" + schdulerData
            .toString() + "====================================");
        try {
          this.emailInsert.sendEmailToRmBC(schdulerData);
          this.userDao.updateEmailStatus(schdulerData.getAccountSchedulerId());
        } catch (Exception e) {
          if (e instanceof NullPointerException) {
            this.response.setMessage("Email Sending failed");
            EmailErrorCode emailError = new EmailErrorCode("EmailNull", Integer.valueOf(409));
            logger.info("ASSIGN_NOTIFICATION_TO_RM_BC catch block" + emailError);
            continue;
          } 
        } 
      } else if (schdulerData.getEvent().equalsIgnoreCase("ASSIGN_NOTIFICATION_TO_RM_BA")) {
        logger.info("============Inside ASSIGN_NOTIFICATION_TO_RM_BA condition==========");
        logger.info("====================================ASSIGN_NOTIFICATION_TO_RM_BA:-" + schdulerData
            .toString() + "====================================");
        try {
          this.emailInsert.sendEmailToRmBA(schdulerData);
          this.userDao.updateEmailStatus(schdulerData.getAccountSchedulerId());
        } catch (Exception e) {
          if (e instanceof NullPointerException) {
            logger.info("============Inside ASSIGN_NOTIFICATION_TO_RM_BA condition==========");
            logger.info("====================================ASSIGN_NOTIFICATION_TO_RM_BA:-" + schdulerData
                .toString() + "====================================");
            EmailErrorCode emailError = new EmailErrorCode("EmailNull", Integer.valueOf(409));
            logger.info("ASSIGN_NOTIFICATION_TO_RM_BA catch block" + emailError);
          } 
        } 
      } else if (schdulerData.getEvent().equalsIgnoreCase("CU_SPLAN_NOTIFICATON_TORM")) {
        logger.info("============Inside CU_SPLAN_NOTIFICATON_TORM condition==========");
        logger.info("========================CU_SPLAN_NOTIFICATON_TORM:-" + schdulerData.toString() + "========================");
        try {
          this.emailInsert.sendCuSplanEmailToRm(schdulerData);
          this.userDao.updateEmailStatus(schdulerData.getAccountSchedulerId());
        } catch (Exception e) {
          if (e instanceof NullPointerException) {
            logger.info("============Inside CU_SPLAN_NOTIFICATON_TORM condition==========");
            logger.info("========================CU_SPLAN_NOTIFICATON_TORM:-" + schdulerData.toString() + "========================");
            EmailErrorCode emailError = new EmailErrorCode("EmailNull", Integer.valueOf(409));
            logger.info("CU_SPLAN_NOTIFICATON_TORM catch block" + emailError);
          } 
        } 
      } else if (schdulerData.getEvent().equalsIgnoreCase("BC_SPLAN_NOTIFICATON_TORM")) {
        logger.info("============Inside BC_SPLAN_NOTIFICATON_TORM condition==========");
        logger.info("====================================BC_SPLAN_NOTIFICATON_TORM:-" + schdulerData.toString() + "====================================");
        try {
          this.emailInsert.sendBCSplanEmailToRm(schdulerData);
          this.userDao.updateEmailStatus(schdulerData.getAccountSchedulerId());
        } catch (Exception e) {
          if (e instanceof NullPointerException) {
            logger.info("============Inside BC_SPLAN_NOTIFICATON_TORM condition==========");
            logger.info("========================#BC_SPLAN_NOTIFICATON_TORM:-" + schdulerData.toString() + "========================");
            EmailErrorCode emailError = new EmailErrorCode("EmailNull", Integer.valueOf(409));
            logger.info("BC_SPLAN_NOTIFICATON_TORM catch block" + emailError);
          } 
        } 
      } else if (schdulerData.getEvent().equalsIgnoreCase("BAU_SPLAN_NOTIFICATON_TORM")) {
        logger.info("============Inside BAU_SPLAN_NOTIFICATON_TORM condition==========");
        logger.info("========================BAU_SPLAN_NOTIFICATON_TORM:-" + schdulerData.toString() + "========================");
        try {
          this.emailInsert.sendBAUSplanEmailToRm(schdulerData);
          this.userDao.updateEmailStatus(schdulerData.getAccountSchedulerId());
        } catch (Exception e) {
          if (e instanceof NullPointerException) {
            logger.info("============Inside BAU_SPLAN_NOTIFICATON_TORM condition==========");
            logger.info("========================BAU_SPLAN_NOTIFICATON_TORM:-" + schdulerData.toString() + "========================");
            EmailErrorCode emailError = new EmailErrorCode("EmailNull", Integer.valueOf(409));
            logger.info("BAU_SPLAN_NOTIFICATON_TORM catch block" + emailError);
          } 
        } 
      } else if (schdulerData.getEvent().equalsIgnoreCase("FIXED_COUPON_CODE_CREATED_ALERT")) {
        logger.info("============Inside FIXED_COUPON_CODE_CREATED_ALERT condition==========");
        logger.info("====================================FIXED_COUPON_CODE_CREATED_ALERT:-" + schdulerData
            .toString() + "====================================");
        try {
          this.emailInsert.sendFixedCDAlertEmailToRm(schdulerData);
          this.userDao.updateEmailStatus(schdulerData.getAccountSchedulerId());
        } catch (Exception e) {
          logger.info("============Inside FIXED_COUPON_CODE_CREATED_ALERT condition==========");
          logger.info("====================================FIXED_COUPON_CODE_CREATED_ALERT:-" + schdulerData
              .toString() + "====================================");
          if (e instanceof NullPointerException) {
            EmailErrorCode emailError = new EmailErrorCode("EmailNull", Integer.valueOf(409));
            logger.info("FIXED_COUPON_ALERT catch block" + emailError);
          } 
        } 
      } else if (schdulerData.getEvent().equalsIgnoreCase("Percent_COUPON_CODE_CREATED_ALERT")) {
        logger.info("============Inside Percent_COUPON_CODE_CREATED_ALERT condition==========");
        logger.info("====================================Percent_COUPON_CODE_CREATED_ALERT:-" + schdulerData
            .toString() + "====================================");
        try {
          this.emailInsert.sendPercentCDAlertEmailToRm(schdulerData);
          this.userDao.updateEmailStatus(schdulerData.getAccountSchedulerId());
        } catch (Exception e) {
          logger.info("============Inside Percent_COUPON_CODE_CREATED_ALERT condition==========");
          System.out.println("====================================Percent_COUPON_CODE_CREATED_ALERT:-" + schdulerData
              .toString() + "====================================");
          if (e instanceof NullPointerException) {
            EmailErrorCode emailError = new EmailErrorCode("EmailNull", Integer.valueOf(409));
            logger.info("PERCENT_COUPON_ALERT catch block" + emailError);
          } 
          continue;
        } 
      } else if (schdulerData.getEvent().equalsIgnoreCase("CU_VAS_NOTIFICATION_TORM")) {
        logger.info("============Inside CU_VAS_NOTIFICATION_TORM condition==========");
        logger.info("====================================CU_VAS_NOTIFICATION_TORM:-" + schdulerData.toString() + "====================================");
        try {
          this.emailInsert.sendCUVASAlertEmailToRm(schdulerData);
          this.userDao.updateEmailStatus(schdulerData.getAccountSchedulerId());
        } catch (Exception e) {
          logger.info("============Inside CU_VAS_NOTIFICATION_TORM condition==========");
          logger.info("====================================CU_VAS_NOTIFICATION_TORM:-" + schdulerData
              .toString() + "====================================");
          if (e instanceof NullPointerException) {
            EmailErrorCode emailError = new EmailErrorCode("EmailNull", Integer.valueOf(409));
            logger.info("CU_VAS_TO_RM catch block" + emailError);
          } 
        } 
      } else if (schdulerData.getEvent().equalsIgnoreCase("BC_VAS_NOTIFICATION_TORM")) {
        logger.info("============Inside BC_VAS_NOTIFICATION_TORM condition==========");
        logger.info("====================================BC_VAS_NOTIFICATION_TORM:-" + schdulerData.toString() + "====================================");
        try {
          this.emailInsert.sendBCVASAlertEmailToRm(schdulerData);
          this.userDao.updateEmailStatus(schdulerData.getAccountSchedulerId());
        } catch (Exception e) {
          logger.info("============Inside BC_VAS_NOTIFICATION_TORM condition==========");
          logger.info("====================================BC_VAS_NOTIFICATION_TORM:-" + schdulerData
              .toString() + "====================================");
          if (e instanceof NullPointerException) {
            EmailErrorCode emailError = new EmailErrorCode("EmailNull", Integer.valueOf(409));
            logger.info("BC_VAS_NOTIFICATION_TORM condition" + emailError);
          } 
          continue;
        } 
      } else if (schdulerData.getEvent().equalsIgnoreCase("BAU_VAS_NOTIFICATION_TORM")) {
        logger.info("============Inside BAU_VAS_NOTIFICATION_TORM condition==========");
        logger.info("====================================BAU_VAS_NOTIFICATION_TORM-" + schdulerData.toString() + "====================================");
        try {
          this.emailInsert.sendBAUAlertEmailToRm(schdulerData);
          this.userDao.updateEmailStatus(schdulerData.getAccountSchedulerId());
        } catch (Exception e) {
          if (e instanceof NullPointerException) {
            EmailErrorCode emailError = new EmailErrorCode("EmailNull", Integer.valueOf(409));
            logger.info("BAU_VAS_NOTIFICATION_TORM condition" + emailError);
          } 
          continue;
        } 
      } else if (schdulerData.getEvent().equalsIgnoreCase("KYC_APPROVAL_FROMRMTO_BANk")) {
        logger.info("============Inside KYC_APPROVAL_FROMRMTO_CUSTOMER condition==========");
        logger.info("====================================KYC_APPROVAL_FROMRMTO_CUSTOMER:-" + schdulerData
            .toString() + "====================================");
        try {
          this.emailInsert.sendApprovalEmailToBank(schdulerData);
          this.userDao.updateEmailStatus(schdulerData.getAccountSchedulerId());
        } catch (Exception e) {
          logger.info("============Inside KYC_APPROVAL_FROMRMTO_CUSTOMER condition==========");
          System.out.println("====================================KYC_APPROVAL_FROMRMTO_CUSTOMER:-" + schdulerData
              .toString() + "====================================");
          if (e instanceof NullPointerException) {
            EmailErrorCode emailError = new EmailErrorCode("EmailNull", Integer.valueOf(409));
            logger.info("Inside KYC_APPROVAL_FROMRMTO_CUSTOMER condition=" + emailError);
          } 
        } 
      } else if (schdulerData.getEvent().equalsIgnoreCase("KYC_APPROVAL_FROMRMTO_CUSTOMER") || schdulerData
        .getEvent().equalsIgnoreCase("KYC_APPROVAL_FROMRMTO_CUSTOMER_PARENT")) {
        logger.info("============Inside KYC_APPROVAL_FROMRMTO_CUSTOMER condition==========");
        logger.info("====================================KYC_APPROVAL_FROMRMTO_CUSTOMER:-" + schdulerData
            .toString() + "====================================");
        try {
          this.emailInsert.sendApprovalEmailToCU(schdulerData);
          this.userDao.updateEmailStatus(schdulerData.getAccountSchedulerId());
        } catch (Exception e) {
          logger.info("============Inside KYC_APPROVAL_FROMRMTO_CUSTOMER condition==========");
          System.out.println("====================================KYC_APPROVAL_FROMRMTO_CUSTOMER:-" + schdulerData
              .toString() + "====================================");
          if (e instanceof NullPointerException) {
            EmailErrorCode emailError = new EmailErrorCode("EmailNull", Integer.valueOf(409));
            logger.info("Inside KYC_APPROVAL_FROMRMTO_CUSTOMER condition=" + emailError);
          } 
        } 
      } else if (schdulerData.getEvent().equalsIgnoreCase("KYC_APPROVAL_FROMRMTO_REFERRER")) {
        logger.info("============Inside KYC_APPROVAL_FROMRMTO_CUSTOMER condition==========");
        logger.info("====================================KYC_APPROVAL_FROMRMTO_CUSTOMER:-" + schdulerData
            .toString() + "====================================");
        try {
          this.emailInsert.sendApprovalEmailToRE(schdulerData);
          this.userDao.updateEmailStatus(schdulerData.getAccountSchedulerId());
        } catch (Exception e) {
          logger.info("============Inside KYC_APPROVAL_FROMRMTO_CUSTOMER condition==========");
          logger.info("====================================KYC_APPROVAL_FROMRMTO_CUSTOMER:-" + schdulerData
              .toString() + "====================================");
          if (e instanceof NullPointerException) {
            EmailErrorCode emailError = new EmailErrorCode("EmailNull", Integer.valueOf(409));
            logger.info("Inside KYC_APPROVAL_FROMRMTO_CUSTOMER condition=" + emailError);
          } 
          continue;
        } 
      } else if (schdulerData.getEvent().equalsIgnoreCase("KYC_REJECTION_FROMRMTO_REFERRER_Support")) {
        logger.info("============Inside KYC_REJ_FROMRM_TO_RE_Support condition==========");
        logger.info("====================================KYC_REJ_FROMRM_TO_RE_Support:-" + schdulerData
            .toString() + "====================================");
        try {
          String CNumber = this.systemConfig.findByNumber();
          String email = this.systemConfig.findByEmail();
          this.emailInsert.sendRejectionEmailFromRmTORe(schdulerData, CNumber, email);
          this.userDao.updateEmailStatus(schdulerData.getAccountSchedulerId());
        } catch (Exception e) {
          logger.info("============Inside KYC_REJ_FROMRM_TO_RE_Support condition==========");
          logger.info("====================================KYC_APPROVAL_FROMRMTO_CUSTOMER:-" + schdulerData
              .toString() + "====================================");
          if (e instanceof NullPointerException) {
            EmailErrorCode emailError = new EmailErrorCode("EmailNull", Integer.valueOf(409));
            logger.info("Inside KYC_REJ_FROMRM_TO_RE_Support condition=" + emailError);
          } 
          continue;
        } 
      } else if (schdulerData.getEvent().equalsIgnoreCase("KYC_REJECTION_FROMRMTO_CUSTOMER_Support") || schdulerData
        .getEvent().equalsIgnoreCase("KYC_REJECTION_FROMRMTO_CUSTOMER_PARENT") || schdulerData
        .getEvent().equalsIgnoreCase("KYC_REJECTION_FROMRMTO_PA_CUSTOMER_Support") || schdulerData
        .getEvent().equalsIgnoreCase("Associate_added")) {
        logger.info("============Inside KYC_APPROVAL_FROMRMTO_CUSTOMER condition==========");
        logger.info("====================================KYC_APPROVAL_FROMRMTO_CUSTOMER:-" + schdulerData
            .toString() + "====================================");
        try {
          String CNumber = this.systemConfig.findByNumber();
          String email = this.systemConfig.findByEmail();
          this.emailInsert.sendRejectionEmailFromRmTOCU(schdulerData, CNumber, email);
          this.userDao.updateEmailStatus(schdulerData.getAccountSchedulerId());
        } catch (Exception e) {
          logger.info("============Inside KYC_REJ_FROMRM_TO_CU_Support condition==========");
          logger.info("====================================KYC_REJ_FROMRM_TO_CU_Support:-" + schdulerData
              .toString() + "====================================");
          if (e instanceof NullPointerException) {
            EmailErrorCode emailError = new EmailErrorCode("EmailNull", Integer.valueOf(409));
            logger.info("Inside KYC_APPROVAL_FROMRMTO_CUSTOMER condition=" + emailError);
          } 
          continue;
        } 
      } else if (schdulerData.getEvent().equalsIgnoreCase("KYC_REJECTION_FROMRMTO_BANk_Support")) {
        logger.info("============Inside KYC_REJ_FROMRM_TO_BA_Support condition==========");
        logger.info("====================================KYC_REJ_FROMRM_TO_BA_Support:-" + schdulerData
            .toString() + "====================================");
        try {
          String CNumber = this.systemConfig.findByNumber();
          String email = this.systemConfig.findByEmail();
          this.emailInsert.sendRejectionEmailFromRmTOBA(schdulerData, CNumber, email);
          this.userDao.updateEmailStatus(schdulerData.getAccountSchedulerId());
        } catch (Exception e) {
          logger.info("============Inside KYC_REJ_FROMRM_TO_BA_Support condition==========");
          logger.info("====================================KYC_REJ_FROMRM_TO_BA_Support:-" + schdulerData
              .toString() + "====================================");
          if (e instanceof NullPointerException) {
            EmailErrorCode emailError = new EmailErrorCode("EmailNull", Integer.valueOf(409));
            logger.info("Inside KYC_REJ_FROMRM_TO_BA_Support condition=" + emailError);
          } 
          continue;
        } 
      } else if (schdulerData.getEvent().equalsIgnoreCase("CUSTOMER_ACCOUNT_REFERRED")) {
        logger.info("============Inside ACCOUNT_REFER condition==========");
        logger.info("====================================ACCOUNT_REFER:-" + schdulerData.toString() + "====================================");
        try {
          String referPercentage = this.systemConfig.findReferrerEarning();
          this.emailInsert.sendCustAccountReferredEmailToSource(schdulerData, referPercentage);
          this.userDao.updateEmailStatus(schdulerData.getAccountSchedulerId());
        } catch (Exception e) {
          if (e instanceof NullPointerException) {
            EmailErrorCode emailError = new EmailErrorCode("EmailNull", Integer.valueOf(409));
            logger.info("Inside ACCOUNT_REFER condition" + emailError);
          } 
          continue;
        } 
      } else if (schdulerData.getEvent().equalsIgnoreCase("KYC_REJECTION_FROMRMTO_CUSTOMER")) {
        logger.info("============Inside KYC_REJECTION_FROMRMTO_CUSTOMER condition==========");
        logger.info("====================================KYC_REJECTION_FROMRMTO_CUSTOMER:-" + schdulerData
            .toString() + "====================================");
        try {
          String CNumber = this.systemConfig.findByNumber();
          String email = this.systemConfig.findByEmail();
          this.emailInsert.sendRejectionEmailFromRmTOCU(schdulerData, CNumber, email);
          this.userDao.updateEmailStatus(schdulerData.getAccountSchedulerId());
        } catch (Exception e) {
          if (e instanceof NullPointerException) {
            EmailErrorCode emailError = new EmailErrorCode("EmailNull", Integer.valueOf(409));
            logger.info("Inside KYC_REJECTION_FROMRMTO_CUSTOMER condition" + emailError);
          } 
        } 
      } else if (schdulerData.getEvent().equalsIgnoreCase("KYC_REJECTION_FROMRMTO_REFERRER")) {
        logger.info("============Inside KYC_REJECTION_FROMRMTO_CUSTOMER condition==========");
        logger.info("====================================KYC_REJECTION_FROMRMTO_CUSTOMER:-" + schdulerData
            .toString() + "====================================");
        try {
          String CNumber = this.systemConfig.findByNumber();
          String email = this.systemConfig.findByEmail();
          this.emailInsert.sendRejectionEmailFromRmTORe(schdulerData, CNumber, email);
          this.userDao.updateEmailStatus(schdulerData.getAccountSchedulerId());
        } catch (Exception e) {
          if (e instanceof NullPointerException) {
            EmailErrorCode emailError = new EmailErrorCode("EmailNull", Integer.valueOf(409));
            logger.info("Inside KYC_REJECTION_FROMRMTO_CUSTOMER condition" + emailError);
          } 
          continue;
        } 
      } else if (schdulerData.getEvent().equalsIgnoreCase("KYC_REJECTION_FROMRMTO_BANk")) {
        logger.info("============Inside KYC_REJ_FROMRM_TO_BA condition==========");
        logger.info("====================================KYC_REJ_FROMRM_TO_BA:-" + schdulerData.toString() + "====================================");
        try {
          String CNumber = this.systemConfig.findByNumber();
          String email = this.systemConfig.findByEmail();
          this.emailInsert.sendRejectionEmailFromRmTOBA(schdulerData, CNumber, email);
          this.userDao.updateEmailStatus(schdulerData.getAccountSchedulerId());
        } catch (Exception e) {
          if (e instanceof NullPointerException) {
            EmailErrorCode emailError = new EmailErrorCode("EmailNull", Integer.valueOf(409));
            logger.info("Inside KYC_REJ_FROMRM_TO_BA condition" + emailError);
          } 
          continue;
        } 
      } else if (schdulerData.getEvent().equalsIgnoreCase("KYC_UPLOAD") || schdulerData
        .getEvent().equalsIgnoreCase("KYC_UPLOAD_ASSOCI_PARENT")) {
        NimaiClient clientUserId = this.userDao.getClientDetailsbyUserId(schdulerData.getUserid());
        logger.info("============Inside" + schdulerData.getEvent() + "condition==========");
        logger.info("====================================" + schdulerData.getEvent() + ":-" + schdulerData
            .toString() + "====================================");
        if (clientUserId != null) {
          if (schdulerData.getrMemailId() != null) {
            try {
              this.emailInsert.sendKycEmail(schdulerData.getEvent(), clientUserId, schdulerData);
              try {
                this.emailInsert.sendEmailToRm(schdulerData.getEvent(), clientUserId, schdulerData);
              } catch (Exception e) {
                if (e instanceof NullPointerException) {
                  EmailErrorCode emailError = new EmailErrorCode("EmailNull", Integer.valueOf(409));
                  logger.info("Inside KYC_UPLOAD" + emailError);
                } 
              } 
              this.userDao.updateEmailStatus(schdulerData.getAccountSchedulerId());
              this.response.setMessage(ErrorDescription.getDescription("ASA002"));
            } catch (Exception e) {
              if (e instanceof NullPointerException) {
                EmailErrorCode emailError = new EmailErrorCode("EmailNull", Integer.valueOf(409));
                logger.info("Inside KYC_UPLOAD" + emailError);
              } 
              continue;
            } 
          } else if (schdulerData.getrMemailId() == null) {
            try {
              this.emailInsert.sendKycEmail(schdulerData.getEvent(), clientUserId, schdulerData);
              int scedulerid = schdulerData.getAccountSchedulerId();
              this.userDao.updateEmailStatus(scedulerid);
            } catch (Exception e) {
              if (e instanceof NullPointerException) {
                EmailErrorCode emailError = new EmailErrorCode("EmailNull", Integer.valueOf(409));
                logger.info("Inside KYC_UPLOAD" + emailError);
              } 
            } 
          } 
        } else {
          logger.info("Inside" + schdulerData.getEvent() + "condition user id not found");
          emailStatus = "UserId_NOT_Registered" + schdulerData.getEvent();
          this.userDao.updateInvalidIdEmailFlag(schdulerData.getAccountSchedulerId(), emailStatus);
        } 
      } 
      try {
        InetAddress ip = InetAddress.getLocalHost();
        System.out
          .println("=============================Current IP address========== : " + ip.getHostAddress());
      } catch (UnknownHostException e) {
        e.printStackTrace();
      } 
    } 
  }
  
  public ResponseEntity<Object> validateResetPasswordLink(String tokenKey) {
    logger.info("======validateResetPasswordLink method invoked=====:" + tokenKey);
    GenericResponse response = new GenericResponse();
    String tokenInitial = tokenKey.substring(0, 2);
    System.out.println(tokenInitial);
    if (tokenInitial.equalsIgnoreCase("RE")) {
      SubsidiaryBean beanResponse = new SubsidiaryBean();
      java.util.Date currentDate = new java.util.Date();
      NimaiMRefer nimaiRefer = this.userDao.getReferDetailsByToken(tokenKey);
      System.out.println("nimaiRefer" + nimaiRefer);
      if (nimaiRefer != null && nimaiRefer.getToken().equals(tokenKey)) {
    	  java.util.Date referExpirydate = nimaiRefer.getTokenExpiryTime();
        System.out.println("referExpirydate" + referExpirydate);
        if (currentDate.before(referExpirydate)) {
          beanResponse.setEmailId(nimaiRefer.getEmailAddress());
          beanResponse.setUserId(nimaiRefer.getUserid().getUserid());
          response.setData(beanResponse);
          response.setMessage(tokenKey);
          response.setFlag(1);
          return new ResponseEntity(response, HttpStatus.OK);
        } 
        response.setFlag(0);
        response.setErrCode("ASA008");
        response.setMessage(ErrorDescription.getDescription("ASA008"));
        return new ResponseEntity(response, HttpStatus.BAD_REQUEST);
      } 
      response.setMessage("Invalid Token");
      return new ResponseEntity(response, HttpStatus.BAD_REQUEST);
    } 
    if (tokenInitial.equalsIgnoreCase("FO"))
      try {
        NimaiMLogin nimaiLogin = this.userDao.getUserDetailsByTokenKey(tokenKey);
        java.util.Date dnow = new java.util.Date();
        if (nimaiLogin != null && nimaiLogin.getToken().equals(tokenKey)) {
          if (dnow.after(nimaiLogin.getTokenExpiryDate())) {
            response.setFlag(0);
            response.setErrCode("ASA015");
            response.setMessage(ErrorDescription.getDescription("ASA015"));
            return new ResponseEntity(response, HttpStatus.BAD_REQUEST);
          } 
          response.setMessage(tokenKey);
          response.setFlag(1);
          return new ResponseEntity(response, HttpStatus.OK);
        } 
        response.setFlag(0);
        response.setErrCode("ASA016");
        response.setMessage(ErrorDescription.getDescription("ASA016"));
        return new ResponseEntity(response, HttpStatus.BAD_REQUEST);
      } catch (Exception e) {
        response.setFlag(0);
        response.setErrCode("ASA017");
        response.setMessage(ErrorDescription.getDescription("ASA017"));
        return new ResponseEntity(response, HttpStatus.BAD_REQUEST);
      }  
    try {
      NimaiMLogin nimaiLogin = this.userDao.getUserDetailsByTokenKey(tokenKey);
      java.util.Date dnow = new java.util.Date();
      java.util.Date expirydate = nimaiLogin.getTokenExpiryDate();
      if (nimaiLogin != null && nimaiLogin.getToken().equals(tokenKey)) {
        if (dnow.before(expirydate)) {
          if (tokenKey.substring(0, 2).equalsIgnoreCase("CU")) {
            NimaiEmailScheduler schData = new NimaiEmailScheduler();
            schData.setSubUserId(nimaiLogin.getUserid().getUserid());
            schData.setUserid(nimaiLogin.getUserid().getAccountSource());
            schData.setEvent("SUBSIDIARY_ACTIVATION_ALERT");
            schData.setEmailStatus("Sent");
            this.userDao.saveSubDetails(schData);
          } 
          UserIdentificationBean beanResponse = new UserIdentificationBean();
          Integer leadId = this.custRepo.findLeadIdByUserId(nimaiLogin.getUserid().getUserid());
          if (leadId == null || leadId.intValue() == 0) {
            if (nimaiLogin.getUserid().getAccountType().equalsIgnoreCase("MASTER")) {
              beanResponse.setUserIdentification(nimaiLogin.getUserid().getAccountType());
              response.setData(beanResponse);
            } 
          } else if (nimaiLogin.getUserid().getAccountType().equalsIgnoreCase("MASTER") || leadId.intValue() > 0) {
            beanResponse.setUserIdentification("MASTER");
            response.setData(beanResponse);
          } 
          response.setMessage(tokenKey);
          response.setFlag(1);
          return new ResponseEntity(response, HttpStatus.OK);
        } 
        response.setFlag(0);
        response.setErrCode("ASA004");
        response.setMessage(ErrorDescription.getDescription("ASA004"));
        return new ResponseEntity(response, HttpStatus.BAD_REQUEST);
      } 
    } catch (Exception e) {
      e.printStackTrace();
    } 
    response.setMessage("Invalid Token");
    return new ResponseEntity(response, HttpStatus.BAD_REQUEST);
  }
  
  public ResponseEntity<?> sendSubsidiaryEmail(SubsidiaryBean subsidiaryBean) {
    logger.info("=========sendSubsidiaryEmail method invoked======:" + subsidiaryBean.toString());
    GenericResponse response = new GenericResponse();
    String errorString = this.resetUserValidator.subsidiaryValidation(subsidiaryBean);
    if (errorString.equalsIgnoreCase("success"))
      try {
        NimaiClient clientUseId = this.userDao.getClientDetailsbyUserId(subsidiaryBean.getUserId());
        NimaiClient subsUserDetails = this.userDao.getcliDetailsByEmailId(subsidiaryBean.getEmailId());
        if (clientUseId != null) {
          String userId = clientUseId.getUserid();
          String tokenKey = userId.concat("_").concat(Utils.generatePasswordResetToken());
          String link = "";
          String clientDomainName = this.utility.getEmailDomain(clientUseId.getEmailAddress());
          NimaiFSubsidiaries subsidiary = new NimaiFSubsidiaries();
          NimaiEmailScheduler schData = new NimaiEmailScheduler();
          String token = tokenKey.substring(tokenKey.indexOf("_") + 1);
          Calendar cal = Calendar.getInstance();
          java.util.Date insertedDate = cal.getTime();
          java.util.Date tokenExpiry = this.utility.getLinkExpiryDate();
          subsidiary.setTokenExpiryDate(tokenExpiry);
          subsidiary.setSubsidiaryToken(tokenKey);
          subsidiary.setUserid(clientUseId);
          subsidiary.setInsertedDate(insertedDate);
          subsidiary.setSubUserId(subsUserDetails.getUserid());
          subsidiary.setSubsidiaryEmail(subsidiaryBean.getEmailId());
          schData.setUserid(clientUseId.getUserid());
          schData.setSubUserId(subsUserDetails.getUserid());
          schData.setUserName(clientUseId.getFirstName());
          schData.setSubFirstName(subsUserDetails.getFirstName());
          schData.setSubLandLine(subsUserDetails.getLandline());
          schData.setSubLastName(subsUserDetails.getLastName());
          schData.setSubMobile(subsUserDetails.getMobileNumber());
          schData.setSubCountry(subsUserDetails.getCountryName());
          schData.setSubOfficailEmail(subsUserDetails.getEmailAddress());
          schData.setEvent("SUBSIDIARY_ADDEDED");
          schData.setEmailStatus("Pending");
          this.userDao.saveSubsidiaryDetails(subsidiary);
          response.setFlag(0);
          link = this.subAccountActivationLink + tokenKey;
          NimaiClient subDet = this.userDao.getClientDetailsBySubsidiaryId(subsidiary.getSubsidiaryEmail());
          this.userDao.updateSubsidiaryTokenDetails(tokenExpiry, tokenKey, subDet.getUserid());
          try {
            this.emailInsert.sendSubAccAcivationLink(link, subsidiaryBean, subsUserDetails);
          } catch (Exception e) {
            e.printStackTrace();
            if (e instanceof NullPointerException) {
              response.setMessage("Email Sending failed");
              EmailErrorCode emailError = new EmailErrorCode("EmailNull", Integer.valueOf(409));
              response.setData(emailError);
              return new ResponseEntity(response, HttpStatus.CONFLICT);
            } 
          } 
          this.userDao.saveSubDetails(schData);
          response.setMessage(ErrorDescription.getDescription("ASA002"));
          return new ResponseEntity(response, HttpStatus.OK);
        } 
        response.setErrCode("ASA001");
        response.setMessage(ErrorDescription.getDescription("ASA001"));
        return new ResponseEntity(response, HttpStatus.BAD_REQUEST);
      } catch (Exception e) {
        e.printStackTrace();
        response.setErrCode("ASA001");
        response.setMessage(ErrorDescription.getDescription("ASA001"));
        return new ResponseEntity(response, HttpStatus.BAD_REQUEST);
      }  
    response.setErrCode("EXE000");
    response.setMessage(ErrorDescription.getDescription("EXE000") + errorString.toString());
    return new ResponseEntity(response, HttpStatus.BAD_REQUEST);
  }
  
  public ResponseEntity<?> sendReferEmail(SubsidiaryBean subsidiaryBean) {
    logger.info("=========sendReferEmail method invoked======:" + subsidiaryBean.toString());
    GenericResponse response = new GenericResponse();
    String errorString = this.resetUserValidator.subsidiaryValidation(subsidiaryBean);
    if (errorString.equalsIgnoreCase("success"))
      try {
        NimaiClient clientUseId = this.userDao.getClientDetailsbyUserId(subsidiaryBean.getUserId());
        NimaiClient referUserDetails = this.userDao.getcliDetailsByEmailId(subsidiaryBean.getEmailId());
        if (clientUseId != null && referUserDetails != null) {
          String userId = clientUseId.getUserid();
          String tokenKey = userId.concat("_").concat(Utils.generatePasswordResetToken());
          if (subsidiaryBean.getEvent().equalsIgnoreCase("ADD_REFER")) {
            NimaiMRefer referDetails = this.userDao.getreferDetails(subsidiaryBean.getReferenceId());
            if (referDetails != null) {
              String refertokenKey = "RE".concat(userId.concat("_").concat(Utils.generatePasswordResetToken()));
              System.out.println(refertokenKey);
              NimaiMRefer referUser = new NimaiMRefer();
              NimaiEmailScheduler schedulerData = new NimaiEmailScheduler();
              schedulerData.setUserid(subsidiaryBean.getUserId());
              schedulerData.setUserName(clientUseId.getFirstName());
              Calendar cal1 = Calendar.getInstance();
              java.util.Date addReferdate = cal1.getTime();
              schedulerData.setEmailStatus("Pending");
              schedulerData.setEvent("ADD_REFER_ALERT_TO_PARENT");
              schedulerData.setInsertedDate(addReferdate);
              schedulerData.setEmailId(clientUseId.getEmailAddress());
              try {
                this.userDao.saveSubDetails(schedulerData);
              } catch (Exception e) {
                e.printStackTrace();
                response.setMessage("Exception occure  saving data in schedulerTable");
                return new ResponseEntity(response, HttpStatus.OK);
              } 
              String token = tokenKey.substring(tokenKey.indexOf("_") + 1);
              Calendar cal = Calendar.getInstance();
              java.util.Date insertedDate = cal.getTime();
              java.util.Date tokenExpiry = this.utility.getLinkExDate();
              try {
                this.userDao.updateReferTokenDetails(tokenExpiry, refertokenKey, clientUseId, insertedDate, subsidiaryBean
                    .getEmailId(), subsidiaryBean.getReferenceId());
              } catch (Exception e) {
                e.printStackTrace();
                response.setMessage("Exception occure while updating referTokenDetails in nimai_m_refer");
                return new ResponseEntity(response, HttpStatus.OK);
              } 
              try {
                System.out.println("Refer User Id: " + referUserDetails.getUserid());
                this.userDao.updateReTokenLoginTable(refertokenKey, insertedDate, tokenExpiry, referUserDetails
                    .getUserid());
              } catch (Exception e) {
                e.printStackTrace();
                response.setMessage("Exception occure while updating referTokenDetails in nimai_m_login");
                return new ResponseEntity(response, HttpStatus.OK);
              } 
              response.setFlag(1);
              String referLink = this.referAccountActivationLink + refertokenKey;
              System.out.println("Activation link:::" + referLink);
              try {
                this.emailInsert.sendReferAccAcivationLink(referLink, subsidiaryBean, referUserDetails);
              } catch (Exception e) {
                e.printStackTrace();
                if (e instanceof NullPointerException) {
                  response.setMessage("Email Sending failed");
                  EmailErrorCode emailError = new EmailErrorCode("EmailNull", Integer.valueOf(409));
                  response.setData(emailError);
                  return new ResponseEntity(response, HttpStatus.CONFLICT);
                } 
              } 
            } 
          } else {
            response.setMessage("Refer Id Entry not Present");
            return new ResponseEntity(response, HttpStatus.OK);
          } 
          response.setErrCode("ASA002");
          response.setMessage(ErrorDescription.getDescription("ASA002"));
          return new ResponseEntity(response, HttpStatus.OK);
        } 
        response.setErrCode("ASA001");
        response.setMessage(ErrorDescription.getDescription("ASA001"));
        return new ResponseEntity(response, HttpStatus.BAD_REQUEST);
      } catch (Exception e) {
        e.printStackTrace();
        response.setErrCode("ASA001");
        response.setMessage(ErrorDescription.getDescription("ASA001"));
        return new ResponseEntity(response, HttpStatus.BAD_REQUEST);
      }  
    response.setErrCode("EXE000");
    response.setMessage(ErrorDescription.getDescription("EXE000") + errorString.toString());
    return new ResponseEntity(response, HttpStatus.BAD_REQUEST);
  }
  
  public ResponseEntity<?> validateSubsidiaryLink(String token) {
    logger.info("=======validateSubsidiaryLink method invoked======:" + token);
    GenericResponse response = new GenericResponse();
    String tokenInitial = token.substring(0, 2);
    System.out.println(tokenInitial);
    try {
      SubsidiaryBean beanResponse = new SubsidiaryBean();
      java.util.Date dnow = new java.util.Date();
      if (tokenInitial.equalsIgnoreCase("RE")) {
        NimaiMRefer nimaiRefer = this.userDao.getReferDetailsByToken(token);
        java.util.Date referExpirydate = nimaiRefer.getTokenExpiryTime();
        if (nimaiRefer != null && nimaiRefer.getToken().equals(token)) {
          if (dnow.before(referExpirydate)) {
            beanResponse.setEmailId(nimaiRefer.getEmailAddress());
            beanResponse.setUserId(nimaiRefer.getUserid().getUserid());
            response.setData(beanResponse);
            response.setMessage(token);
            response.setFlag(1);
            return new ResponseEntity(response, HttpStatus.OK);
          } 
          response.setFlag(0);
          response.setErrCode("ASA008");
          response.setMessage(ErrorDescription.getDescription("ASA008"));
          return new ResponseEntity(response, HttpStatus.BAD_REQUEST);
        } 
      } else if (tokenInitial.equalsIgnoreCase("CU") || tokenInitial.equalsIgnoreCase("BA")) {
        NimaiFSubsidiaries nimaiFSub = this.userDao.getSubsidiaryDetailsByToken(token);
        java.util.Date subExpirydate = nimaiFSub.getTokenExpiryDate();
        if (nimaiFSub != null && nimaiFSub.getSubsidiaryToken().equals(token)) {
          if (dnow.before(subExpirydate)) {
            beanResponse.setEmailId(nimaiFSub.getSubsidiaryEmail());
            beanResponse.setUserId(nimaiFSub.getUserid().getUserid());
            response.setData(beanResponse);
            response.setMessage(token);
            response.setFlag(1);
            return new ResponseEntity(response, HttpStatus.OK);
          } 
          response.setFlag(0);
          response.setErrCode("ASA007");
          response.setMessage(ErrorDescription.getDescription("ASA007"));
          return new ResponseEntity(response, HttpStatus.BAD_REQUEST);
        } 
      } else {
        String passcode = token.substring(11, 16);
        String branchToken = token.substring(0, 10);
        NimaiMBranch nimaiMbranch = this.userDao.getbranchDetailsByToken(branchToken);
        java.util.Date subExpirydate = nimaiMbranch.getExpryTime();
        if (nimaiMbranch != null && nimaiMbranch.getToken().equals(branchToken)) {
          if (passcode.equals(nimaiMbranch.getPasscodeValue())) {
            if (dnow.before(subExpirydate)) {
              beanResponse.setEmailId(nimaiMbranch.getEmailId());
              beanResponse.setUserId(nimaiMbranch.getUserid());
              response.setData(beanResponse);
              response.setMessage(token);
              response.setFlag(1);
              return new ResponseEntity(response, HttpStatus.OK);
            } 
            response.setFlag(0);
            response.setErrCode("ASA009");
            response.setMessage(ErrorDescription.getDescription("ASA009"));
            return new ResponseEntity(response, HttpStatus.BAD_REQUEST);
          } 
          response.setMessage("Passcode does not match");
          return new ResponseEntity(response, HttpStatus.BAD_REQUEST);
        } 
      } 
    } catch (Exception e) {
      e.printStackTrace();
    } 
    response.setMessage("Invalid Token");
    return new ResponseEntity(response, HttpStatus.BAD_REQUEST);
  }
  
  public ResponseEntity<?> sendbranchUserLink(BranchUserRequest branchUserLink) {
    logger.info("=====sendbranchUserLink=========:" + branchUserLink.toString());
    String errorString = this.resetUserValidator.buPassCodeValidator(branchUserLink);
    if (errorString.equalsIgnoreCase("success")) {
    	java.util.Date currentDateTime = new java.util.Date();
      NimaiMBranch branchUser = new NimaiMBranch();
      NimaiClient clientUseId = (NimaiClient)this.custRepo.getOne(branchUserLink.getUserId());
      if (clientUseId.getKycStatus() == null)
        return new ResponseEntity("KYC status not maintain", HttpStatus.OK); 
      if (clientUseId.getKycStatus().equalsIgnoreCase("Approved")) {
        branchUser = (NimaiMBranch)this.brRepo.getOne(Integer.valueOf(Integer.parseInt(branchUserLink.getBranchId())));
        if (branchUser.getPasscodeCount() == null)
          try {
            int count = 0;
            branchUser.setInsertTime(currentDateTime);
            branchUser.setPasscodeCount(Integer.valueOf(count));
            branchUser.setBrId(branchUser.getBrId());
            this.brRepo.save(branchUser);
          } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity("Passcode Count is null", HttpStatus.OK);
          }  
        if (branchUser.getPasscodeCount().intValue() < 3)
          try {
            String urlToken = sendBranchUserLink(branchUserLink, clientUseId, this.response, branchUser);
            if (urlToken.equalsIgnoreCase("Email sending failed")) {
              this.response.setErrCode("ASA014");
            } else {
              this.response.setErrCode("ASA002");
              this.response.setData(urlToken);
              this.response.setId(Integer.parseInt(branchUserLink.getBranchId()));
            } 
            this.response.setMessage(ErrorDescription.getDescription("ASA002"));
            return new ResponseEntity(this.response, HttpStatus.OK);
          } catch (Exception e) {
            logger.info("=========Catch block of send branch User Link (branchUser.getPasscodeCount() < 3)======:");
            e.printStackTrace();
            this.response.setMessage("Something went wrong");
            return new ResponseEntity(this.response, HttpStatus.OK);
          }  
        if (branchUser.getPasscodeCount().intValue() >= 3) {
          long diff = currentDateTime.getTime() - branchUser.getInsertTime().getTime();
          long differenceMinutes = diff / 60000L % 60L;
          logger.info("=============currentTime" + differenceMinutes);
          logger.info("=============currentTime" + branchUser.getInsertTime().getTime());
          Calendar calendar = Calendar.getInstance();
          System.out.println("=============difference in minutes" + differenceMinutes);
          logger.info("=============difference in minutes" + differenceMinutes);
          if (differenceMinutes <= 10L) {
            this.response.setMessage("You have excedded maximum attempts to login into system,Yoour account is temprorarily blocked,try again after 10 minutes.");
            this.response.setErrCode("EX003");
            this.response.setId(Integer.parseInt(branchUserLink.getBranchId()));
            return new ResponseEntity(this.response, HttpStatus.OK);
          } 
          if (differenceMinutes > 10L)
            try {
              String urlToken = sendBranchUserLink(branchUserLink, clientUseId, this.response, branchUser);
              int passcount = 0;
              this.userDao.updateBranchUserDetails(branchUser.getToken(), currentDateTime, passcount);
              if (urlToken.equalsIgnoreCase("Email sending failed")) {
                this.response.setErrCode("ASA014");
              } else {
                this.response.setErrCode("ASA002");
                this.response.setData(urlToken);
                this.response.setId(Integer.parseInt(branchUserLink.getBranchId()));
              } 
              this.response.setMessage(ErrorDescription.getDescription("ASA002"));
              return new ResponseEntity(this.response, HttpStatus.OK);
            } catch (Exception e) {
              logger.info("=========Catch block of send branch User Link else if (differenceMinutes > 10) method======:");
              e.printStackTrace();
              this.response.setMessage("Something went wrong");
              return new ResponseEntity(this.response, HttpStatus.BAD_REQUEST);
            }  
        } 
      } else {
        this.response.setErrCode("ASA013");
        this.response.setMessage(ErrorDescription.getDescription("ASA013") + errorString.toString());
        return new ResponseEntity(this.response, HttpStatus.BAD_REQUEST);
      } 
    } else {
      this.response.setErrCode("EX000");
      this.response.setMessage(ErrorDescription.getDescription("EXE000") + errorString.toString());
      return new ResponseEntity(this.response, HttpStatus.BAD_REQUEST);
    } 
    return new ResponseEntity("Somethig went wrong", HttpStatus.BAD_REQUEST);
  }
  
  public String sendBranchUserLink(BranchUserRequest branchUserLink, NimaiClient clientUseId, GenericResponse response, NimaiMBranch branchUser) {
    NimaiMBranch branchUserDetails = new NimaiMBranch();
    String errorMsg = "Email sending failed";
    try {
      int id = Integer.parseInt(branchUserLink.getBranchId());
      logger.info("=====================branchId:" + id);
      String link = "";
      String passcode = "";
      String bUlink = "";
      String urltoken = "";
      String token = "";
      String employeeId = branchUser.getEmployeeId();
      if (employeeId == null || employeeId.isEmpty()) {
        employeeId = "null";
      } else {
        employeeId = branchUser.getEmployeeId();
      } 
      if (branchUser != null && branchUser.getToken() != null) {
    	  java.util.Date dnow = new java.util.Date();
        System.out.println(dnow);
        logger.info("=====================String sendBranchUserLink:" + dnow);
        token = Utils.generatePasswordResetToken();
        if (branchUser.getExpryTime() == null || branchUser.getToken() == null) {
          passcode = Utils.passcodeValue();
          String userId = branchUserLink.getUserId();
          String tokenKey = this.utility.generateLinkToken(clientUseId.getSubscriberType(), branchUserLink
              .getUserId());
          java.util.Date tokenExpiry = this.utility.getLinkExpiryDate();
          Calendar cal = Calendar.getInstance();
          java.util.Date insertedDate = cal.getTime();
          branchUserDetails.setEmailId(branchUserLink.getEmailId());
          try {
            branchUserDetails.setPasscodeValue(passcode);
            branchUserDetails.setToken(tokenKey);
            branchUserDetails.setUserid(branchUserLink.getUserId());
            branchUserDetails.setEmployeeId(branchUser.getEmployeeId());
            branchUserDetails.setInsertTime(insertedDate);
            branchUserDetails.setEmailId(branchUserLink.getEmailId());
            branchUserDetails.setEmployeeName(branchUserLink.getEmployeeName());
            branchUserDetails.setBrId(id);
            branchUserDetails.setExpryTime(tokenExpiry);
            this.brRepo.save(branchUserDetails);
          } catch (Exception e) {
            e.printStackTrace();
            response.setMessage("Email Sending failed");
            EmailErrorCode emailError = new EmailErrorCode("EmailNull", Integer.valueOf(409));
            response.setData(emailError);
            return errorMsg;
          } 
          urltoken = tokenKey.concat("_").concat(passcode);
          link = this.subAccountActivationLink + urltoken;
          try {
            this.emailInsert.sendBranchUserActivationLink(link, branchUserLink, passcode, clientUseId);
            return urltoken;
          } catch (Exception e) {
            if (e instanceof NullPointerException) {
              response.setMessage("Email Sending failed");
              EmailErrorCode emailError = new EmailErrorCode("EmailNull", Integer.valueOf(409));
              response.setData(emailError);
              return errorMsg;
            } 
          } 
        } 
        if (token.equalsIgnoreCase(branchUser.getToken()))
          token = this.utility.generateLinkToken(clientUseId.getSubscriberType(), branchUserLink.getUserId()); 
        if (dnow.before(branchUser.getExpryTime())) {
          String passcodeValue = Utils.passcodeValue();
          try {
        	  java.util.Date tokExpiry = this.utility.getLinkExpiryDate();
            branchUserDetails.setEmailId(branchUserLink.getEmailId());
            branchUserDetails.setInsertTime(dnow);
            branchUserDetails.setPasscodeValue(passcodeValue);
            branchUserDetails.setUserid(branchUserLink.getUserId());
            branchUserDetails.setEmployeeId(branchUser.getEmployeeId());
            branchUserDetails.setEmployeeName(branchUserLink.getEmployeeName());
            branchUserDetails.setToken(token);
            branchUserDetails.setExpryTime(tokExpiry);
            branchUserDetails.setBrId(id);
            this.brRepo.save(branchUserDetails);
          } catch (Exception e) {
            e.printStackTrace();
            response.setMessage("Email Sending failed");
            EmailErrorCode emailError = new EmailErrorCode("EmailNull", Integer.valueOf(409));
            response.setData(emailError);
            return errorMsg;
          } 
          urltoken = token.concat("_").concat(passcodeValue);
          bUlink = this.subAccountActivationLink + urltoken;
          try {
            this.emailInsert.sendBranchUserActivationLink(bUlink, branchUserLink, passcodeValue, clientUseId);
            return urltoken;
          } catch (Exception e) {
            if (e instanceof NullPointerException) {
              response.setMessage("Email Sending failed");
              EmailErrorCode emailError = new EmailErrorCode("EmailNull", Integer.valueOf(409));
              response.setData(emailError);
              return errorMsg;
            } 
          } 
        } else {
          passcode = Utils.passcodeValue();
          String userId = branchUserLink.getUserId();
          try {
            String tokenKey = this.utility.generateLinkToken(clientUseId.getSubscriberType(), branchUserLink
                .getUserId());
            java.util.Date tokenExpiry = this.utility.getLinkExpiryDate();
            Calendar cal = Calendar.getInstance();
            java.util.Date insertedDate = cal.getTime();
            try {
              branchUserDetails.setPasscodeValue(passcode);
              branchUserDetails.setToken(tokenKey);
              branchUserDetails.setInsertTime(insertedDate);
              branchUserDetails.setEmailId(branchUserLink.getEmailId());
              branchUserDetails.setUserid(branchUserLink.getUserId());
              branchUserDetails.setEmployeeId(branchUser.getEmployeeId());
              branchUserDetails.setEmployeeName(branchUserLink.getEmployeeName());
              branchUserDetails.setExpryTime(tokenExpiry);
              branchUserDetails.setBrId(id);
              this.brRepo.save(branchUserDetails);
            } catch (Exception e) {
              e.printStackTrace();
              response.setMessage("Email Sending failed");
              EmailErrorCode emailError = new EmailErrorCode("EmailNull", Integer.valueOf(409));
              response.setData(emailError);
              return errorMsg;
            } 
            urltoken = tokenKey.concat("_").concat(passcode);
            bUlink = this.subAccountActivationLink + urltoken;
            try {
              this.emailInsert.sendBranchUserActivationLink(bUlink, branchUserLink, passcode, clientUseId);
              return urltoken;
            } catch (Exception e) {
              if (e instanceof NullPointerException) {
                response.setMessage("Email Sending failed");
                EmailErrorCode emailError = new EmailErrorCode("EmailNull", Integer.valueOf(409));
                response.setData(emailError);
                return errorMsg;
              } 
            } 
          } catch (Exception e) {
            e.printStackTrace();
          } 
        } 
      } else {
        passcode = Utils.passcodeValue();
        String userId = branchUserLink.getUserId();
        String tokenKey = Utils.generatePasswordResetToken();
        java.util.Date tokenExpiry = this.utility.getLinkExpiryDate();
        Calendar cal = Calendar.getInstance();
        java.util.Date insertedDate = cal.getTime();
        branchUserDetails.setEmailId(branchUserLink.getEmailId());
        try {
          branchUserDetails.setPasscodeValue(passcode);
          branchUserDetails.setToken(tokenKey);
          branchUserDetails.setInsertTime(insertedDate);
          branchUserDetails.setEmailId(branchUserLink.getEmailId());
          branchUserDetails.setUserid(branchUserLink.getUserId());
          branchUserDetails.setEmployeeId(branchUser.getEmployeeId());
          branchUserDetails.setEmployeeName(branchUserLink.getEmployeeName());
          branchUserDetails.setBrId(id);
          branchUserDetails.setExpryTime(tokenExpiry);
          this.brRepo.save(branchUserDetails);
        } catch (Exception e) {
          e.printStackTrace();
          response.setMessage("Email Sending failed");
          EmailErrorCode emailError = new EmailErrorCode("EmailNull", Integer.valueOf(409));
          response.setData(emailError);
          return errorMsg;
        } 
        urltoken = tokenKey.concat("_").concat(passcode);
        link = this.subAccountActivationLink + urltoken;
        try {
          this.emailInsert.sendBranchUserActivationLink(link, branchUserLink, passcode, clientUseId);
          return urltoken;
        } catch (Exception e) {
          if (e instanceof NullPointerException) {
            response.setMessage("Email Sending failed");
            EmailErrorCode emailError = new EmailErrorCode("EmailNull", Integer.valueOf(409));
            response.setData(emailError);
            return errorMsg;
          } 
        } 
      } 
    } catch (Exception e) {
      e.printStackTrace();
    } 
    return errorMsg;
  }
  
  public ResponseEntity<?> validatePassCodeValue(BranchUserPassCodeBean passCodeBean) {
    logger.info("=========validatePassCodeValue method invoked========");
    String errorString = this.resetUserValidator.passcodeValidation(passCodeBean);
    logger.info("pascodevalue before logic" + passCodeBean.getPasscodeValue());
    logger.info("token before logic" + passCodeBean.getToken());
    if (errorString.equalsIgnoreCase("success"))
      try {
        NimaiMBranch branchDetails = (NimaiMBranch)this.brRepo.getOne(Integer.valueOf(passCodeBean.getId()));
        String employeeId = branchDetails.getEmployeeId();
        if (employeeId == null || employeeId.isEmpty()) {
          employeeId = "null";
        } else {
          employeeId = branchDetails.getEmployeeId();
        } 
        logger.info("============passCodeBean.getId():" + passCodeBean.getId());
        NimaiToken token = (NimaiToken)this.tokenRepo.getOne(passCodeBean.getUserId());
        String flag = "";
        java.util.Date dnow = new java.util.Date();
        if (branchDetails != null) {
        	java.util.Date subExpirydate = branchDetails.getExpryTime();
          if (branchDetails.getToken().equals(passCodeBean.getToken())) {
            if (passCodeBean.getPasscodeValue().equals(branchDetails.getPasscodeValue())) {
              if (dnow.before(subExpirydate)) {
                SubsidiaryBean beanResponse = new SubsidiaryBean();
                if (employeeId.substring(0, 2).equalsIgnoreCase("BA") || employeeId
                  .substring(0, 2).equalsIgnoreCase("BC") || employeeId
                  .substring(0, 2).equalsIgnoreCase("CU") || employeeId
                  .substring(0, 2).equalsIgnoreCase("RE")) {
                  beanResponse.setUserId(branchDetails.getEmployeeId());
                } else {
                  beanResponse.setUserId(branchDetails.getUserid());
                } 
                int passcodecount = 0;
                branchDetails.setPasscodeCount(Integer.valueOf(passcodecount));
                branchDetails.setInsertTime(dnow);
                branchDetails.setBrId(branchDetails.getBrId());
                this.brRepo.save(branchDetails);
                this.response.setData(beanResponse);
                this.response.setMessage(passCodeBean.getToken());
                this.response.setFlag(1);
                this.response.setErrCode("ASA001");
                return new ResponseEntity(this.response, HttpStatus.OK);
              } 
              this.response.setFlag(0);
              this.response.setErrCode("ASA009");
              this.response.setMessage(ErrorDescription.getDescription("ASA009"));
              return new ResponseEntity(this.response, HttpStatus.BAD_REQUEST);
            } 
            if (branchDetails.getPasscodeCount().intValue() == 3) {
              int passcodecount = 3;
              branchDetails.setPasscodeCount(Integer.valueOf(passcodecount));
              branchDetails.setInsertTime(dnow);
              branchDetails.setBrId(branchDetails.getBrId());
              this.brRepo.save(branchDetails);
              this.response.setMessage("Attempt excedded");
              try {
                flag = "attempt_true";
                token.setIsInvalidCaptcha(flag);
                this.tokenRepo.save(token);
                System.out.println("Inside true comdition for invalidCaptchaFlag" + flag);
              } catch (Exception e) {
                e.printStackTrace();
                this.response.setErrCode("EX001");
                this.response.setMessage("Error while updating invalid captcha details");
              } 
              return new ResponseEntity(this.response, HttpStatus.OK);
            } 
            int passcodeCOunt = branchDetails.getPasscodeCount().intValue() + 1;
            branchDetails.setPasscodeCount(Integer.valueOf(passcodeCOunt));
            branchDetails.setInsertTime(dnow);
            branchDetails.setBrId(branchDetails.getBrId());
            this.brRepo.save(branchDetails);
            this.response.setMessage("Passcode does not match");
            try {
              flag = "passcode_true";
              token.setIsInvalidCaptcha(flag);
              this.tokenRepo.save(token);
              System.out.println("Inside true condition for invalid passcode condition" + flag);
            } catch (Exception e) {
              e.printStackTrace();
              this.response.setErrCode("EX001");
              this.response.setMessage("Error while updating invalid captcha details");
            } 
            return new ResponseEntity(this.response, HttpStatus.OK);
          } 
          this.response.setErrCode("EX001");
          this.response.setMessage("Token not found");
          try {
            flag = "token_true";
            token.setIsInvalidCaptcha(flag);
            this.tokenRepo.save(token);
            System.out.println("Inside true comdition for invalidToken condition" + flag);
          } catch (Exception e) {
            e.printStackTrace();
            this.response.setErrCode("EX001");
            this.response.setMessage("Error while updating invalid captcha details");
          } 
          return new ResponseEntity(this.response, HttpStatus.OK);
        } 
      } catch (Exception e) {
        e.printStackTrace();
        this.response.setMessage("Branch Id not found");
        return new ResponseEntity(this.response, HttpStatus.BAD_REQUEST);
      }  
    this.response.setErrCode("EXE000");
    this.response.setMessage(ErrorDescription.getDescription("EXE000") + errorString.toString());
    return new ResponseEntity(this.response, HttpStatus.BAD_REQUEST);
  }
  
  public ResponseEntity<Object> sendAdminEmail(AdminBean userRegistratinBean) throws Exception {
    logger.info("=======Inside sendAdminEmail method====" + userRegistratinBean.toString());
    NimaiMLogin mLoginId = this.userDao.existsByEmpCode(userRegistratinBean.getEmpCode());
    try {
      if (mLoginId == null) {
        System.out.println("==========================EMpDetaila:" + mLoginId.toString());
        this.response.setErrCode("ASA010");
        this.response.setMessage(ErrorDescription.getDescription("ASA010"));
        return new ResponseEntity(this.response, HttpStatus.OK);
      } 
      String link = this.adminForgotPassLink + mLoginId.getEmpCode().getEmpCode() + "&token=" + mLoginId.getToken();
      System.out.println("==========================EMpDetaila:" + mLoginId.toString());
      if (userRegistratinBean.getEvent().equalsIgnoreCase("FORGOT_PASSWORD")) {
        logger.info("=================link" + link);
        try {
          this.emailInsert.AdminForgotPassEmail(userRegistratinBean, mLoginId, link);
        } catch (Exception e) {
          e.printStackTrace();
          this.response.setErrCode("ASA018");
          this.response.setMessage(ErrorDescription.getDescription("ASA018"));
          return new ResponseEntity(this.response, HttpStatus.OK);
        } 
      } else if (userRegistratinBean.getEvent().equalsIgnoreCase("ACCOUNT_ACTIVATE")) {
        logger.info("=================link" + link);
        try {
          this.emailInsert.AdminEmail(userRegistratinBean, mLoginId, link);
        } catch (Exception e) {
          e.printStackTrace();
          this.response.setErrCode("ASA018");
          this.response.setMessage(ErrorDescription.getDescription("ASA018"));
          return new ResponseEntity(this.response, HttpStatus.OK);
        } 
      } 
      this.response.setErrCode("ASA002");
      this.response.setMessage(ErrorDescription.getDescription("ASA002"));
      return new ResponseEntity(this.response, HttpStatus.OK);
    } catch (Exception e) {
      e.printStackTrace();
      this.response.setErrCode("ASA011");
      this.response.setMessage(ErrorDescription.getDescription("ASA011"));
      return new ResponseEntity(this.response, HttpStatus.OK);
    } 
  }
  
  public void AdminEmail(AdminBean resetpassword) throws Exception {
    EmailComponentMaster emailconfigurationBean = null;
    try {
      emailconfigurationBean = this.emailConfigurationDAOImpl.findEventConfiguration(resetpassword.getEvent());
      System.out.println(" Fetching Configuration for Reset Password Policy " + emailconfigurationBean);
      Map<String, String> subject = new HashMap<>();
      Map<String, String> body = new HashMap<>();
      subject.put("Subject", emailconfigurationBean.getSubject());
      body.put("link", resetpassword.getLink());
      body.put("username", resetpassword.getUserName());
      body.put("userId", resetpassword.getUserId());
      String toMail = resetpassword.getEmail();
      System.out.println(" Fetching Configuration for Reset Password Policy " + emailconfigurationBean
          .getSubject() + " :: " + emailconfigurationBean.getEventId());
      ArrayList details = new ArrayList();
      details.add(emailconfigurationBean.getEventId());
      details.add(toMail);
      details.add(subject);
      details.add(body);
      System.out.println("details" + details);
      this.emailProcessorImpl.saveEmail(details, emailconfigurationBean.getEventId().intValue());
      this.emailSend.getDetailsEmail();
    } catch (Exception e) {
      System.out.println("Exception" + e);
      e.printStackTrace();
    } 
  }
  
  public ByteArrayInputStream generateEodReport() {
    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
    java.util.Date date = new java.util.Date();
    java.util.Date todaysdate = new Date((new java.util.Date()).getTime());
    System.out.println(todaysdate);
    List<NimaiLC> customerList = this.userDao.getCustTransactionList(todaysdate);
    System.out.println(customerList.toString());
    Map<String, List<NimaiLC>> groupByUserId = (Map<String, List<NimaiLC>>)customerList.stream().collect(Collectors.groupingBy(NimaiLC::getUserId));
    System.out.println("New generated methods inside the generate Eod Report" + groupByUserId.toString());
    ArrayList<String> fileLocationArrayList = new ArrayList<>();
    for (Map.Entry<String, List<NimaiLC>> entry : groupByUserId.entrySet()) {
      try {
        String fileLocation = ExcelUtility.generateReportToExcel(entry.getKey(), entry
            .getValue());
        System.out.println("filelocation name in the middle" + fileLocation);
        for (String filePresent : fileLocationArrayList) {
          if (!filePresent.equalsIgnoreCase(fileLocation) && filePresent != null && !filePresent.isEmpty())
            try {
              boolean emailStatus = this.emailInsert.sendEodReport(fileLocation, entry.getKey());
              if (emailStatus) {
                System.out.println("false conditions");
                fileLocationArrayList.add(fileLocation);
              } 
            } catch (Exception exception) {} 
        } 
      } catch (IOException e) {
        e.printStackTrace();
      } 
    } 
    return null;
  }
  
  public ResponseEntity<?> sendDmmyAccEmail(UserRegistrationBean userBean) {
    NimaiClient nimaiClientdetails = this.userDao.getClientDetailsbyUserId(userBean.getUserId());
    this.emailInsert.sendAccEmail(userBean, nimaiClientdetails);
    return null;
  }
  
  public ResponseEntity<?> sendbranchUserPasscode(BranchUserRequest branchUserLink) {
    logger.info("=====sendbranchUserLink=========:" + branchUserLink.toString());
    GenericResponse response = new GenericResponse();
    NimaiMBranch branchUserDetails = new NimaiMBranch();
    java.util.Date currentTime = new java.util.Date();
    java.util.Date currentDateTime = new java.util.Date();
    String errorString = this.resetUserValidator.buPassCodeValidator(branchUserLink);
    if (errorString.equalsIgnoreCase("success")) {
      try {
        NimaiClient clientUseId = (NimaiClient)this.custRepo.getOne(branchUserLink.getUserId());
        if (clientUseId != null) {
          NimaiMBranch branchUser = this.userDao.getBranchUserDetailsByEMpId(clientUseId.getEmailAddress(), clientUseId
              .getUserid());
          if (branchUser == null) {
            String urlToken = sendbrPaasFor1Time(clientUseId, branchUserLink);
            int index = urlToken.indexOf("@");
            int BrID = Integer.parseInt(urlToken.substring(index + 1, urlToken.length()));
            if (urlToken.equalsIgnoreCase("Email sending failed")) {
              response.setErrCode("ASA014");
              response.setMessage(ErrorDescription.getDescription("ASA014"));
            } else {
              response.setErrCode("ASA002");
              response.setMessage(ErrorDescription.getDescription("ASA002"));
              response.setId(BrID);
              response.setData(urlToken);
            } 
            return new ResponseEntity(response, HttpStatus.OK);
          } 
          if (branchUser.getPasscodeCount() == null)
            try {
              int count = 0;
              branchUserDetails.setPasscodeCount(Integer.valueOf(count));
              branchUserDetails.setInsertTime(currentDateTime);
              branchUserDetails.setBrId(branchUser.getBrId());
              this.brRepo.save(branchUserDetails);
            } catch (Exception e) {
              e.printStackTrace();
              return new ResponseEntity("Passcode Count is null", HttpStatus.CONFLICT);
            }  
          if (branchUser.getPasscodeCount().intValue() < 3) {
            String urlToken = sendPasscodeMethod(branchUserLink, clientUseId, response, branchUser);
            if (urlToken.equalsIgnoreCase("Email sending failed")) {
              response.setErrCode("ASA014");
              response.setMessage(ErrorDescription.getDescription("ASA014"));
            } else {
              response.setErrCode("ASA002");
              response.setMessage(ErrorDescription.getDescription("ASA002"));
              response.setId(branchUser.getBrId());
              response.setData(urlToken);
            } 
            return new ResponseEntity(response, HttpStatus.OK);
          } 
          if (branchUser.getPasscodeCount().intValue() >= 3) {
            long diff = currentTime.getTime() - branchUser.getInsertTime().getTime();
            long differenceMinutes = diff / 60000L % 60L;
            System.out.println("=============currentTime" + differenceMinutes);
            logger.info("=============currentTime" + differenceMinutes);
            System.out.println("=============currentTime" + branchUser.getInsertTime().getTime());
            logger.info("=============currentTime" + branchUser.getInsertTime().getTime());
            Calendar calendar = Calendar.getInstance();
            System.out.println("=============difference in minutes" + differenceMinutes);
            if (differenceMinutes <= 10L) {
              response.setMessage("You have excedded maximum attempts to login into system,Yoour account is temprorarily blocked,try again after 10 minutes.");
              response.setErrCode("EX003");
              response.setId(branchUser.getBrId());
              return new ResponseEntity(response, HttpStatus.OK);
            } 
            if (differenceMinutes > 10L) {
              String urlToken = sendPasscodeMethod(branchUserLink, clientUseId, response, branchUser);
              int count = 0;
              branchUserDetails.setToken(branchUser.getToken());
              branchUserDetails.setInsertTime(currentDateTime);
              branchUserDetails.setPasscodeCount(Integer.valueOf(count));
              branchUserDetails.setBrId(branchUser.getBrId());
              this.brRepo.save(branchUserDetails);
              if (urlToken.equalsIgnoreCase("Email sending failed")) {
                response.setErrCode("ASA014");
                response.setMessage(ErrorDescription.getDescription("ASA014"));
              } else {
                response.setErrCode("ASA002");
                response.setMessage(ErrorDescription.getDescription("ASA002"));
                response.setId(branchUser.getBrId());
                response.setData(urlToken);
              } 
              return new ResponseEntity(response, HttpStatus.OK);
            } 
          } 
        } else {
          response.setMessage("User Not registred");
          return new ResponseEntity(response, HttpStatus.BAD_REQUEST);
        } 
      } catch (Exception e) {
        e.printStackTrace();
      } 
    } else {
      response.setErrCode("EX000");
      response.setMessage(ErrorDescription.getDescription("EXE000") + errorString.toString());
      return new ResponseEntity(response, HttpStatus.BAD_REQUEST);
    } 
    response.setMessage("Branch Id not found");
    return new ResponseEntity(response, HttpStatus.BAD_REQUEST);
  }
  
  private String sendbrPaasFor1Time(NimaiClient clientUseId, BranchUserRequest branchUserLink) {
    NimaiMBranch branchUserDetails = new NimaiMBranch();
    String link = "";
    String passcode = "";
    String bUlink = "";
    String urltoken = "";
    String errorMsg = "Email Sending failed";
    passcode = Utils.passcodeValue();
    String userId = branchUserLink.getUserId();
    String tokenKey = this.utility.generateLinkToken(clientUseId.getUserid(), branchUserLink.getUserId());
    java.util.Date tokenExpiry = this.utility.getLinkExpiryDate();
    Calendar cal = Calendar.getInstance();
    java.util.Date insertedDate = cal.getTime();
    branchUserDetails.setEmailId(clientUseId.getEmailAddress());
    branchUserDetails.setPasscodeValue(passcode);
    branchUserDetails.setToken(tokenKey);
    branchUserDetails.setInsertTime(insertedDate);
    branchUserDetails.setEmployeeId(clientUseId.getUserid());
    branchUserDetails.setEmployeeName(clientUseId.getFirstName());
    branchUserDetails.setExpryTime(tokenExpiry);
    branchUserDetails.setPasscodeCount(Integer.valueOf(0));
    this.brRepo.save(branchUserDetails);
    urltoken = tokenKey.concat("_").concat(passcode);
    link = this.subAccountActivationLink + urltoken;
    try {
      this.emailInsert.sendBranchUserPasscodeLink(link, branchUserLink, clientUseId, passcode);
    } catch (Exception e) {
      if (e instanceof NullPointerException)
        return errorMsg; 
    } 
    return urltoken.concat("@").concat(String.valueOf(branchUserDetails.getBrId()));
  }
  
  public String sendPasscodeMethod(BranchUserRequest branchUserLink, NimaiClient clientUseId, GenericResponse response, NimaiMBranch branchUser) {
    NimaiMBranch branchUserDetails = (NimaiMBranch)this.brRepo.getOne(Integer.valueOf(branchUser.getBrId()));
    String link = "";
    String passcode = "";
    String bUlink = "";
    java.util.Date dnow = new java.util.Date();
    String urltoken = "";
    String errorMsg = "Email Sending failed";
    logger.debug("===============sendPasscodeMethod===========:");
    String token = Utils.generatePasswordResetToken();
    java.util.Date expirydate = branchUser.getExpryTime();
    if (dnow.before(expirydate)) {
      String passcodeValue = Utils.passcodeValue();
      try {
        branchUserDetails.setEmailId(clientUseId.getEmailAddress());
        branchUserDetails.setInsertTime(dnow);
        branchUserDetails.setPasscodeValue(passcodeValue);
        branchUserDetails.setToken(token);
        branchUserDetails.setBrId(branchUser.getBrId());
        this.brRepo.save(branchUserDetails);
      } catch (Exception e) {
        e.printStackTrace();
        response.setMessage("Email Sending failed");
        EmailErrorCode emailError = new EmailErrorCode("EmailNull", Integer.valueOf(409));
        response.setData(emailError);
        return errorMsg;
      } 
      urltoken = token.concat("_").concat(passcodeValue);
      bUlink = this.subAccountActivationLink + urltoken;
      try {
        this.emailInsert.sendBranchUserPasscodeLink(bUlink, branchUserLink, clientUseId, passcodeValue);
        return urltoken;
      } catch (Exception e) {
        if (e instanceof NullPointerException) {
          response.setMessage("Email Sending failed");
          EmailErrorCode emailError = new EmailErrorCode("EmailNull", Integer.valueOf(409));
          response.setData(emailError);
          return errorMsg;
        } 
      } 
    } else {
      passcode = Utils.passcodeValue();
      String userId = branchUserLink.getUserId();
      String tokenKey = Utils.generatePasswordResetToken();
      java.util.Date tokenExpiry = this.utility.getLinkExpiryDate();
      try {
        branchUserDetails.setEmailId(clientUseId.getEmailAddress());
        branchUserDetails.setInsertTime(dnow);
        branchUserDetails.setPasscodeValue(passcode);
        branchUserDetails.setToken(tokenKey);
        branchUserDetails.setExpryTime(tokenExpiry);
        branchUserDetails.setBrId(branchUser.getBrId());
        this.brRepo.save(branchUserDetails);
      } catch (Exception e) {
        e.printStackTrace();
        response.setMessage("Email Sending failed");
        EmailErrorCode emailError = new EmailErrorCode("EmailNull", Integer.valueOf(409));
        response.setData(emailError);
        return errorMsg;
      } 
      urltoken = tokenKey.concat("_").concat(passcode);
      bUlink = this.subAccountActivationLink + urltoken;
      try {
        this.emailInsert.sendBranchUserPasscodeLink(bUlink, branchUserLink, clientUseId, passcode);
        return urltoken;
      } catch (Exception e) {
        if (e instanceof NullPointerException) {
          response.setMessage("Email Sending failed");
          EmailErrorCode emailError = new EmailErrorCode("EmailNull", Integer.valueOf(409));
          response.setData(emailError);
          return errorMsg;
        } 
      } 
    } 
    return errorMsg;
  }
  
  public ResponseEntity<?> reSendReferEmail(SubsidiaryBean registerLink) {
    logger.info("=========sendReferEmail method invoked======:" + registerLink.toString());
    GenericResponse response = new GenericResponse();
    String errorString = this.resetUserValidator.reSendValidation(registerLink);
    if (errorString.equalsIgnoreCase("success"))
      try {
        NimaiClient clientUseId = this.userDao.getClientDetailsbyUserId(registerLink.getUserId());
        NimaiMRefer referDetails = this.userDao.getreferDetailsByUserDetails(clientUseId.getEmailAddress());
        if (clientUseId != null && referDetails != null) {
          String userId = clientUseId.getUserid();
          String tokenKey = userId.concat("_").concat(Utils.generatePasswordResetToken());
          if (registerLink.getEvent().equalsIgnoreCase("ADD_REFER")) {
            String refertokenKey = "RE".concat(userId.concat("_").concat(Utils.generatePasswordResetToken()));
            System.out.println(refertokenKey);
            NimaiMRefer referUser = new NimaiMRefer();
            String token = tokenKey.substring(tokenKey.indexOf("_") + 1);
            Calendar cal = Calendar.getInstance();
            java.util.Date insertedDate = cal.getTime();
            java.util.Date tokenExpiry = this.utility.getLinkExDate();
            this.userDao.updateReferTokenDetails(tokenExpiry, refertokenKey, clientUseId, insertedDate, clientUseId
                .getEmailAddress(), referDetails.getId());
            System.out.println("Refer User Id: " + referDetails.getId());
            this.userDao.updateReTokenLoginTable(refertokenKey, insertedDate, tokenExpiry, clientUseId
                .getUserid());
            response.setFlag(1);
            String referLink = this.referAccountActivationLink + refertokenKey;
            System.out.println("Activation link:::" + referLink);
            try {
              this.emailInsert.reSendReferAccAcivationLink(referLink, registerLink, clientUseId);
            } catch (Exception e) {
              e.printStackTrace();
              if (e instanceof NullPointerException) {
                response.setMessage("Email Sending failed");
                EmailErrorCode emailError = new EmailErrorCode("EmailNull", Integer.valueOf(409));
                response.setData(emailError);
                return new ResponseEntity(response, HttpStatus.CONFLICT);
              } 
            } 
          } 
          response.setErrCode("ASA002");
          response.setMessage(ErrorDescription.getDescription("ASA002"));
          return new ResponseEntity(response, HttpStatus.OK);
        } 
        response.setErrCode("ASA001");
        response.setMessage(ErrorDescription.getDescription("ASA001"));
        return new ResponseEntity(response, HttpStatus.BAD_REQUEST);
      } catch (Exception e) {
        e.printStackTrace();
        response.setErrCode("ASA001");
        response.setMessage(ErrorDescription.getDescription("ASA001"));
        return new ResponseEntity(response, HttpStatus.BAD_REQUEST);
      }  
    response.setErrCode("ASA002");
    response.setMessage(ErrorDescription.getDescription("ASA002"));
    return new ResponseEntity(response, HttpStatus.OK);
  }
  
  public ResponseEntity<?> restSuccessEmail(ResetPassBean resetBean) {
    GenericResponse response = new GenericResponse();
    logger.info("=======Inside restSuccessEmail method====" + resetBean.toString());
    if (resetBean.getEvent().equalsIgnoreCase("RESET_SUCCESS")) {
      NimaiClient clientUseId = this.userDao.getClientDetailsbyUserId(resetBean.getUserId());
      try {
        this.emailInsert.resetSuccessEmail(resetBean, clientUseId);
      } catch (Exception e) {
        e.printStackTrace();
        response.setErrCode("ASA011");
        response.setMessage(ErrorDescription.getDescription("ASA011"));
        return new ResponseEntity(response, HttpStatus.OK);
      } 
    } else {
      response.setErrCode("ASA012");
      response.setMessage(ErrorDescription.getDescription("ASA012"));
      return new ResponseEntity(response, HttpStatus.OK);
    } 
    response.setErrCode("ASA002");
    response.setMessage(ErrorDescription.getDescription("ASA002"));
    return new ResponseEntity(response, HttpStatus.OK);
  }
  
  public boolean validateToken(String userId, String token) {
    NimaiToken nt = this.tokenRepo.isTokenExists(userId, token);
    if (nt != null)
      return true; 
    return false;
  }
  
  public ResponseEntity<?> sendBankDetails(BankDetailsBean bdBean) {
    GenericResponse response = new GenericResponse();
    NimaiClient clientUseId = this.userDao.getClientDetailsbyUserId(bdBean.getUserId());
    logger.info("=======Inside sendBankDetails method====" + bdBean.toString());
    try {
      if (clientUseId != null) {
        try {
          this.emailInsert.BankDetailstoCustomer(bdBean, clientUseId);
        } catch (Exception e) {
          e.printStackTrace();
          response.setErrCode("ASA018");
          response.setMessage(ErrorDescription.getDescription("ASA018"));
          return new ResponseEntity(response, HttpStatus.OK);
        } 
      } else {
        logger.info("=======Inside sendBankDetails method====" + bdBean.toString());
        response.setErrCode("ASA010");
        response.setMessage(ErrorDescription.getDescription("ASA010"));
        return new ResponseEntity(response, HttpStatus.OK);
      } 
      if (bdBean.getWireTransfer().equalsIgnoreCase("wire_CLickHere")) {
        response.setErrCode("wire_ClickHere");
      } else {
        response.setErrCode("wire_SendRequest");
      } 
      response.setMessage(ErrorDescription.getDescription("Email Send Succefully"));
      return new ResponseEntity(response, HttpStatus.OK);
    } catch (Exception e) {
      e.printStackTrace();
      response.setErrCode("ASA011");
      response.setMessage(ErrorDescription.getDescription("ASA011"));
      return new ResponseEntity(response, HttpStatus.OK);
    } 
  }
  
  public void saveInvalidCaptchaFlag(BranchUserPassCodeBean passCodeBean, String flag) {
    this.userDao.updateInvalidCaptcha(passCodeBean, flag);
  }
  
  public String InvalidCaptchaStatus(BranchUserPassCodeBean passCodeBean) {
    NimaiMBranch branchDetails = this.userDao.getbranchDetailsById(passCodeBean.getId());
    if (branchDetails != null) {
      if (branchDetails.getInvalidCaptcha() == null)
        return "String null"; 
      if (branchDetails.getInvalidCaptcha().equalsIgnoreCase("true"))
        return "true"; 
      return "false";
    } 
    return "object null";
  }
  
  public ResponseEntity<?> validateCaptcha(String userId) {
    GenericResponse response = new GenericResponse();
    String tokenUserEmail = "";
    try {
      NimaiToken nt;
      if (userId.toString().substring(0, 2).equalsIgnoreCase("CU") || userId
        .toString().substring(0, 2).equalsIgnoreCase("BC") || userId
        .toString().substring(0, 2).equalsIgnoreCase("RE")) {
        NimaiClient client = this.userDao.getClientDetailsbyUserId(userId);
        tokenUserEmail = userId + "-" + client.getEmailAddress();
        nt = (NimaiToken)this.tokenRepo.getOne(tokenUserEmail);
      } else {
        nt = this.userDao.isTokenExists(userId);
        tokenUserEmail = userId;
        nt = (NimaiToken)this.tokenRepo.getOne(tokenUserEmail);
      } 
      String captchFlag = nt.getIsInvalidCaptcha();
      if (captchFlag == null) {
        response.setMessage("Authorise Access");
      } else if (captchFlag.equalsIgnoreCase("false")) {
        response.setMessage("Authorise Access");
      } else if (captchFlag.equalsIgnoreCase("passcode_true")) {
        response.setMessage("Passcode does not match");
        String flag = "false";
        try {
          this.userDao.updateInvalCaptcha(tokenUserEmail, flag);
        } catch (Exception e) {
          e.printStackTrace();
          response.setErrCode("EX001");
          response.setMessage("Exception while updating flag");
        } 
      } else if (captchFlag.equalsIgnoreCase("token_true")) {
        response.setMessage("Token not found");
        String flag = "false";
        try {
          this.userDao.updateInvalCaptcha(tokenUserEmail, flag);
        } catch (Exception e) {
          e.printStackTrace();
          response.setErrCode("EX001");
          response.setMessage("Exception while updating flag");
        } 
      } else if (captchFlag.equalsIgnoreCase("attempt_true")) {
        response.setMessage("Attempt exceedded");
        String flag = "false";
        try {
          this.userDao.updateInvalCaptcha(tokenUserEmail, flag);
        } catch (Exception e) {
          e.printStackTrace();
          response.setErrCode("EX001");
          response.setMessage("Exception while updating flag");
        } 
      } else if (captchFlag.equalsIgnoreCase("true")) {
        response.setErrCode("EX001");
        String flag = "false";
        try {
          this.userDao.updateInvalCaptcha(tokenUserEmail, flag);
        } catch (Exception e) {
          e.printStackTrace();
          response.setErrCode("EX001");
          response.setMessage("Exception while updating flag");
        } 
        response.setMessage("Unauthorise Access");
      } 
      return new ResponseEntity(response, HttpStatus.OK);
    } catch (Exception e) {
      e.printStackTrace();
      response.setErrCode("EX001");
      response.setMessage("Unauthorise Access");
      return new ResponseEntity(response, HttpStatus.OK);
    } 
  }
  
  public InvoiceBeanResponse getSplanInvoiceString(String userId, String invoiceId) {
    String path = null;
    InvoiceBeanResponse file = null;
    NimaiSubscriptionVas vasDetails = null;
    try {
      NimaiClient clientUseId = this.userDao.getClientDetailsbyUserId(userId);
      logger.info("============Inside Cust_Splan_email condition==========" + userId);
      if (clientUseId != null) {
        NimaiSubscriptionDetails subDetails = this.sPlanRepo.getSplanDetails(userId, invoiceId);
        System.out.println("sub details for the nimaisubscriptiondetails" + subDetails.toString());
        logger.info("===========CUST_SPLAN_EVENT:" + subDetails.getSubscriptionId());
        logger.info("===========CUST_SPLAN_EVENT:" + subDetails.toString());
        if (subDetails.getPaymentMode().equalsIgnoreCase("Credit")) {
          String status = "Success";
          String aStatus = "Approved";
          OnlinePayment paymentDetails = this.payRepo.findByuserId(userId, status, aStatus);
          if (paymentDetails == null) {
            String planFailureName = "SPLAN_FAILURE";
            logger.info("===========SPLAN_FAILURE:" + subDetails.toString());
          } else {
            NimaiSystemConfig configDetails = null;
            configDetails = this.systemConfig.findBySystemId(1);
            NimaiSystemConfig configDetail = null;
            try {
              configDetail = (NimaiSystemConfig)this.systemConfig.getOne(Integer.valueOf(14));
              System.out.println("configDetail image value" + configDetail.getSystemEntityValue());
              InvoiceTemplate link = new InvoiceTemplate();
              vasDetails = this.vasRepo.getVasDetailsBySerialNumber(subDetails.getUserid().getUserid(), subDetails.getsPlSerialNUmber());
              if (subDetails.getPaymentMode().equalsIgnoreCase("Wire") && 
                !clientUseId.getPaymentStatus().equalsIgnoreCase("Rejected")) {
                System.out.println("Inside Wire mode");
                file = link.genStringSplanInvoiceTemplatePdf(subDetails, paymentDetails, configDetails, configDetail
                    .getSystemEntityValue(), vasDetails);
              } else if (subDetails.getPaymentMode().equalsIgnoreCase("Credit")) {
                System.out.println("Inside credit mode");
                file = link.genStringSplanInvoiceTemplatePdf(subDetails, paymentDetails, configDetails, configDetail
                    .getSystemEntityValue(), vasDetails);
              } 
            } catch (Exception e) {
              e.printStackTrace();
            } 
          } 
        } else {
          String status = "Success";
          String aStatus = "Approved";
          OnlinePayment paymentDetails = this.payRepo.findByuserId(userId, status, aStatus);
          NimaiSystemConfig configDetails = null;
          configDetails = this.systemConfig.findBySystemId(1);
          NimaiSystemConfig configDetail = null;
          try {
            configDetail = (NimaiSystemConfig)this.systemConfig.getOne(Integer.valueOf(14));
            System.out.println("configDetail image value" + configDetail.getSystemEntityValue());
            InvoiceTemplate link = new InvoiceTemplate();
            vasDetails = this.vasRepo.getVasDetailsBySerialNumber(subDetails.getUserid().getUserid(), subDetails.getsPlSerialNUmber());
            if (subDetails.getPaymentMode().equalsIgnoreCase("Wire") && 
              !clientUseId.getPaymentStatus().equalsIgnoreCase("Rejected")) {
              System.out.println("Inside Wire mode");
              file = link.genStringSplanInvoiceTemplatePdf(subDetails, paymentDetails, configDetails, configDetail
                  .getSystemEntityValue(), vasDetails);
            } else if (subDetails.getPaymentMode().equalsIgnoreCase("Credit")) {
              System.out.println("Inside credit mode");
              file = link.genStringSplanInvoiceTemplatePdf(subDetails, paymentDetails, configDetails, configDetail
                  .getSystemEntityValue(), vasDetails);
            } 
          } catch (Exception e) {
            e.printStackTrace();
          } 
        } 
      } else {
        logger.info("Inside Cust_Splan_email condition user id not found");
        this.response.setMessage("Details not found");
      } 
    } catch (Exception e) {
      if (e instanceof NullPointerException) {
        EmailErrorCode emailError = new EmailErrorCode("EmailNull", Integer.valueOf(409));
        logger.info("Customer Splan catch block" + emailError);
      } 
    } 
    return file;
  }
  
  public void saveConfigEntity(NimaiSystemConfig config) {
    System.out.println("Inside saveConfigEntity");
    this.systemConfig.save(config);
  }
  
  public InvoiceBeanResponse getVasplanInvoiceString(String userId, String invoiceId) {
    InvoiceTemplate link = new InvoiceTemplate();
    InvoiceBeanResponse response = null;
    try {
      NimaiClient clientUseId = this.userDao.getClientDetailsbyUserId(userId);
      logger.info("============Inside VAS_ADDED condition==========");
      logger.info("====================================UserId Id:-" + userId + "====================================");
      if (clientUseId != null) {
        NimaiSubscriptionDetails subDetails;
        NimaiSubscriptionVas vasDetails = this.vasRepo.getVasDetailsByInvoiceId(clientUseId.getUserid(), invoiceId);
        logger.info("====================vasdetails" + vasDetails.getVasId());
        if (vasDetails.getStatus().equalsIgnoreCase("Inactive")) {
          subDetails = (NimaiSubscriptionDetails)this.sPlanRepo.getOne(Integer.valueOf(vasDetails.getsPLanSerialNUmber()));
        } else {
          subDetails = this.userDao.getSplanDetails(vasDetails.getSubscriptionId(), userId);
        } 
        logger.info("==========================subdetails" + subDetails.getsPlSerialNUmber());
        if (!vasDetails.getMode().equalsIgnoreCase("Wire")) {
          String status = "Success";
          String aStatus = "Approved";
          try {
            OnlinePayment paymentDetails = this.payRepo.findByuserId(userId, status, aStatus);
            if (paymentDetails == null) {
              logger.info("==================null paymentdetails");
              logger.info("=============Inside paymet failure mode===============");
              logger.info("==============Inside paymet failure wire==============");
            } else {
              logger.info("================" + paymentDetails.toString());
              logger.info("==================paymentdetails" + paymentDetails.getId());
              logger.info("==============Inside paymet success==============");
              NimaiSystemConfig configDetails = null;
              configDetails = this.systemConfig.findBySystemId(1);
              NimaiSystemConfig configDetail = null;
              configDetail = (NimaiSystemConfig)this.systemConfig.getOne(Integer.valueOf(14));
              response = link.generateVasInvoiceResponse(vasDetails, subDetails, paymentDetails, configDetails);
            } 
          } catch (Exception e) {
            e.printStackTrace();
          } 
        } else {
          try {
            NimaiSubscriptionDetails subDetail;
            logger.info("============Inside VAS_ADDED condition==========");
            logger.info("====================================UserId Id:-" + userId + "====================================");
            NimaiSubscriptionVas vasDetail = this.vasRepo.getVasDetailsByInvoiceId(clientUseId.getUserid(), invoiceId);
            logger.info("====================vasdetails" + vasDetails.getVasId());
            if (vasDetails.getStatus().equalsIgnoreCase("Inactive")) {
              subDetail = (NimaiSubscriptionDetails)this.sPlanRepo.getOne(Integer.valueOf(vasDetails.getsPLanSerialNUmber()));
            } else {
              subDetail = this.userDao.getSplanDetails(vasDetails.getSubscriptionId(), userId);
            } 
            logger.info("====================vasdetails" + vasDetails.getVasId());
            logger.info("==========================subdetails" + subDetails.getsPlSerialNUmber());
            String status = "Success";
            String aStatus = "Approved";
            try {
              OnlinePayment paymentDetail = new OnlinePayment();
              logger.info("================" + paymentDetail.toString());
              logger.info("==================paymentdetails" + paymentDetail.getId());
              logger.info("==============Inside paymet success==============");
              NimaiSystemConfig configDetails = null;
              configDetails = this.systemConfig.findBySystemId(1);
              NimaiSystemConfig configDetail = null;
              configDetail = (NimaiSystemConfig)this.systemConfig.getOne(Integer.valueOf(14));
              response = link.generateVasInvoiceResponse(vasDetail, subDetail, paymentDetail, configDetails);
              System.out.println("payment details outside the codition" + paymentDetail.toString());
            } catch (Exception e) {
              e.printStackTrace();
            } 
          } catch (Exception e) {
            if (e instanceof NullPointerException) {
              EmailErrorCode emailError = new EmailErrorCode("EmailNull", Integer.valueOf(409));
              logger.info("Vas added catch block" + emailError);
            } 
          } 
        } 
      } else {
        logger.info("Inside Cust_Splan_email condition user id not found");
      } 
    } catch (Exception e) {
      if (e instanceof NullPointerException) {
        EmailErrorCode emailError = new EmailErrorCode("EmailNull", Integer.valueOf(409));
        logger.info("Vas added catch block" + emailError);
      } 
    } 
    return response;
  }
  
  public int chkForInvoiceId(String userId, String invoiceId) {
    try {
      NimaiClient clientUseId = this.userDao.getClientDetailsbyUserId(userId);
      if (clientUseId == null)
        return 2; 
      try {
        NimaiSubscriptionDetails subDetails = this.sPlanRepo.getSplanDetails(userId, invoiceId);
        if (subDetails == null)
          return 0; 
        return 1;
      } catch (Exception e) {
        logger.info("Exception occurs in userserviceImpl chkForInvoiceId method");
        e.printStackTrace();
        return 2;
      } 
    } catch (Exception e) {
      logger.info("Exception occurs in userserviceImpl chkForInvoiceId method");
      e.printStackTrace();
      return 2;
    } 
  }
  
  public byte[] getSplanInvoice(String userId) {
    return null;
  }
  
  public String getSplanInvoicePath(String userId) {
    return null;
  }
  
  public NimaiSubscriptionDetails getSubDetails(int splanSerialNumber) {
    NimaiSubscriptionDetails details = this.userDao.getsPlanDetailsBySerialNumber(splanSerialNumber);
    return details;
  }
}
