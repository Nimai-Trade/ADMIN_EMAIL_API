package com.nimai.lc.service;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.Period;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.concurrent.TimeUnit;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.ParameterMode;
import javax.persistence.StoredProcedureQuery;

import org.modelmapper.ModelMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nimai.lc.bean.BankDetailsBean;
import com.nimai.lc.bean.QuotationBean;
import com.nimai.lc.bean.QuotationMasterBean;
import com.nimai.lc.bean.TransactionQuotationBean;
import com.nimai.lc.entity.NimaiClient;
import com.nimai.lc.entity.NimaiEmailSchedulerAlertToBanks;
import com.nimai.lc.entity.NimaiLCMaster;
import com.nimai.lc.entity.Quotation;
import com.nimai.lc.entity.QuotationMaster;
import com.nimai.lc.entity.SavingInput;
import com.nimai.lc.payload.GenericResponse;
import com.nimai.lc.repository.LCMasterRepository;
import com.nimai.lc.repository.NimaiEmailSchedulerAlertToBanksRepository;
import com.nimai.lc.repository.QuotationMasterRepository;
import com.nimai.lc.repository.QuotationRepository;
import com.nimai.lc.repository.SavingInpRepo;
import com.nimai.lc.repository.TransactionSavingRepo;
import com.nimai.lc.utility.ModelMapperUtil;

@Service
public class QuotationServiceImpl implements QuotationService {

	@Autowired
	NimaiEmailSchedulerAlertToBanksRepository userDao;
	
	@Autowired
	QuotationRepository quotationRepo;
	
	@Autowired
	LCMasterRepository lcMasterRepo;

	@Autowired
	SavingInpRepo savingRepo;
	
	@Autowired
	TransactionSavingRepo trSavingRepo;
	
	@Autowired
	QuotationMasterRepository quotationMasterRepo;

	@Autowired
	LCService lcservice;
	
	@Autowired
	EntityManagerFactory em;
	
	
	
	@Autowired
    private ModelMapper modelMapper;
	
	@Override
	public Integer saveQuotationdetails(QuotationBean quotationbean) {
		// TODO Auto-generated method stub
		Quotation quote = new Quotation();
		quote.setQuotationId(quotationbean.getQuotationId());
		quote.setTransactionId(quotationbean.getTransactionId());
		quote.setUserId(quotationbean.getUserId());
		quote.setBankUserId(quotationbean.getBankUserId());
		quote.setConfirmationCharges(quotationbean.getConfirmationCharges());
		quote.setConfChgsIssuanceToNegot(quotationbean.getConfChgsIssuanceToNegot());
		quote.setConfChgsIssuanceToexp(quotationbean.getConfChgsIssuanceToexp());
		quote.setConfChgsIssuanceToMatur(quotationbean.getConfChgsIssuanceToMatur());
		quote.setDiscountingCharges(quotationbean.getDiscountingCharges());
		quote.setBankAcceptCharges(quotationbean.getBankAcceptCharges());
		quote.setRefinancingCharges(quotationbean.getRefinancingCharges());
		quote.setApplicableBenchmark(quotationbean.getApplicableBenchmark());
		quote.setCommentsBenchmark(quotationbean.getCommentsBenchmark());
		quote.setNegotiationChargesFixed(quotationbean.getNegotiationChargesFixed());
		quote.setNegotiationChargesPerct(quotationbean.getNegotiationChargesPerct());
		quote.setDocHandlingCharges(quotationbean.getDocHandlingCharges());
		quote.setOtherCharges(quotationbean.getOtherCharges());
		quote.setChargesType(quotationbean.getChargesType());
		quote.setMinTransactionCharges(quotationbean.getMinTransactionCharges());
		quote.setInsertedBy(quotationbean.getInsertedBy());
		quote.setInsertedDate(quotationbean.getInsertedDate());
		quote.setModifiedBy(quotationbean.getModifiedBy());
		quote.setModifiedDate(quotationbean.getModifiedDate());
		quote.setValidityDate(quotationbean.getValidityDate());
		quote.setTermConditionComments(quotationbean.getTermConditionComments());
		
		HashMap<String,String> bankDet=getBankDetailsByBankUserId(quotationbean.getBankUserId()); 
		quote.setBankName(bankDet.get("bankname"));
		quote.setBranchName(bankDet.get("branchname"));
		quote.setSwiftCode(bankDet.get("swiftcode"));
		quote.setCountryName(bankDet.get("countryname"));
		quote.setEmailAddress(bankDet.get("emailaddress"));
		quote.setTelephone(bankDet.get("telephone"));
		quote.setMobileNumber(bankDet.get("mobileno"));
		quote.setFirstName(bankDet.get("firstname"));
		quote.setLastName(bankDet.get("lastname"));
		
		quotationRepo.save(quote);
		
		return quote.getQuotationId();
	}

	@Override
	public HashMap<String, Integer> calculateQuote(Integer quotationId,String transactionId) 
	{
		
		System.out.println("Calculating Ouotation Id: "+quotationId+" for transaction id: "+transactionId);
		HashMap<String, Integer> getData=generateQuote(quotationId,transactionId,"Draft");
		//HashMap<String, Integer> getData=new HashMap<String, Integer>();
		//getData.put("Value", 1222);
		return getData;
	}
		
	@Override
	public void confirmQuotationdetails(QuotationBean quotationbean) {
		Integer quotationId=quotationbean.getQuotationId();
		String transId = quotationbean.getTransactionId();
		String userId = quotationbean.getUserId();
		EntityManager entityManager = em.createEntityManager();
		try {
		StoredProcedureQuery storedProcedureQuery = entityManager
				.createStoredProcedureQuery("move_to_quotation_master", Quotation.class);
		storedProcedureQuery.registerStoredProcedureParameter("inp_quotation_id", Integer.class, ParameterMode.IN);
		storedProcedureQuery.registerStoredProcedureParameter("inp_transaction_id", String.class, ParameterMode.IN);
		storedProcedureQuery.registerStoredProcedureParameter("inp_userid", String.class, ParameterMode.IN);
		storedProcedureQuery.setParameter("inp_quotation_id", quotationId);
		storedProcedureQuery.setParameter("inp_transaction_id", transId);
		storedProcedureQuery.setParameter("inp_userid", userId);

		storedProcedureQuery.execute();
		} catch (Exception e) {
			System.out.println(e);

		} finally {
			entityManager.close();

		}
		
	}

	@Override
	public HashMap<String, Integer> calculateQuote(Integer quotationId,String transId, String tableType) {
		// TODO Auto-generated method stub
		// String transactionId="4028870370f1880f0170f1899aec0001";
		EntityManager entityManager = em.createEntityManager();
		try {
		StoredProcedureQuery storedProcedure = entityManager.createStoredProcedureQuery("quote_calculation",
				NimaiLCMaster.class);
		// set parameters
		System.out.println("Calculating Quote for: "+quotationId);
		storedProcedure.registerStoredProcedureParameter("inp_quotation_id", Integer.class, ParameterMode.IN);
		storedProcedure.registerStoredProcedureParameter("inp_transaction_id", String.class, ParameterMode.IN);
		storedProcedure.registerStoredProcedureParameter("inp_table_type", String.class, ParameterMode.IN);
		storedProcedure.registerStoredProcedureParameter("negoDays", Integer.class, ParameterMode.OUT);
		storedProcedure.registerStoredProcedureParameter("expDays", Integer.class, ParameterMode.OUT);
		storedProcedure.registerStoredProcedureParameter("matDays", Integer.class, ParameterMode.OUT);
		storedProcedure.registerStoredProcedureParameter("confChgsNegot", Float.class, ParameterMode.OUT);
		storedProcedure.registerStoredProcedureParameter("confChgsMatur", Float.class, ParameterMode.OUT);
		storedProcedure.registerStoredProcedureParameter("sumOfQuote", Integer.class, ParameterMode.OUT);
		storedProcedure.registerStoredProcedureParameter("totalQuote", Integer.class, ParameterMode.OUT);
		storedProcedure.setParameter("inp_quotation_id", quotationId);
		storedProcedure.setParameter("inp_transaction_id", transId);
		storedProcedure.setParameter("inp_table_type", tableType);

		storedProcedure.execute();

		int negoDays = (int) storedProcedure.getOutputParameterValue("negoDays");
		int expDays = (int) storedProcedure.getOutputParameterValue("expDays");
		int matDays = (int) storedProcedure.getOutputParameterValue("matDays");
		float confChgsNegot = (float) storedProcedure.getOutputParameterValue("confChgsNegot");
		float confChgsMatur = (float) storedProcedure.getOutputParameterValue("confChgsMatur");
		int sumOfQuote = (int) storedProcedure.getOutputParameterValue("sumOfQuote");
		int totalQuote = (int) storedProcedure.getOutputParameterValue("totalQuote");

		System.out.println(negoDays + " " + expDays + " " + matDays + " " + sumOfQuote + " " + totalQuote);
		HashMap outputData = new HashMap();

		outputData.put("negotiationDays", negoDays);
		outputData.put("expiryDays", expDays);
		outputData.put("maturityDays", matDays);
		outputData.put("confChgsNegot", confChgsNegot);
		outputData.put("confChgsMatur", confChgsMatur);
		outputData.put("sumOfQuote", sumOfQuote);
		outputData.put("TotalQuote", totalQuote);
		

		return outputData;
	} catch (Exception e) {
		System.out.println(e);

	} finally {
		entityManager.close();

	}
	return null;

	}

	@Override
	public void quotePlace(String transId) {
		// TODO Auto-generated method stub
		quotationRepo.updateQuotationPlaced(transId);
	}

	@Override
	public int getQuotationdetailsToCount(String transId) {
		// TODO Auto-generated method stub
		return quotationRepo.getQuotationCount(transId);
	}

	@Override
	public List<Quotation> getAllDraftQuotationDetails(String userId) {
		// TODO Auto-generated method stub
		return quotationRepo.findAllDraftQuotation(userId);
	}
	
	

	@Override
	public List<QuotationMaster> getAllQuotationDetails(String userId) {
		// TODO Auto-generated method stub
		return quotationMasterRepo.findAllQuotationByUserId(userId);
	}

	@Override
	public List<QuotationMaster> getQuotationDetailByUserIdAndStatus(String userId, String status) {
		// TODO Auto-generated method stub
		return quotationMasterRepo.findAllQuotation(userId, status);
	}

	@Override
	public void updateDraftQuotationDet(QuotationBean quotationbean) {
		Integer qid = quotationbean.getQuotationId();
		Quotation quote = quotationRepo.getOne(qid);
		System.out.println("Quotation id= " + qid);
		System.out.println("ConfirmationCharges= " + quote.getConfirmationCharges());
		quote.setUserId(quotationbean.getUserId());
		quote.setBankUserId(quotationbean.getBankUserId());
		quote.setTransactionId(quotationbean.getTransactionId());
		quote.setConfirmationCharges(quotationbean.getConfirmationCharges());
		quote.setConfChgsIssuanceToNegot(quotationbean.getConfChgsIssuanceToNegot());
		quote.setConfChgsIssuanceToexp(quotationbean.getConfChgsIssuanceToexp());
		quote.setConfChgsIssuanceToMatur(quotationbean.getConfChgsIssuanceToMatur());
		quote.setDiscountingCharges(quotationbean.getDiscountingCharges());
		quote.setBankAcceptCharges(quotationbean.getBankAcceptCharges());
		quote.setRefinancingCharges(quotationbean.getRefinancingCharges());
		quote.setApplicableBenchmark(quotationbean.getApplicableBenchmark());
		quote.setCommentsBenchmark(quotationbean.getCommentsBenchmark());
		quote.setNegotiationChargesFixed(quotationbean.getNegotiationChargesFixed());
		quote.setNegotiationChargesPerct(quotationbean.getNegotiationChargesPerct());
		Date now = new Date();
		quote.setDocHandlingCharges(quotationbean.getDocHandlingCharges());
		quote.setOtherCharges(quotationbean.getOtherCharges());
		quote.setChargesType(quotationbean.getChargesType());
		quote.setMinTransactionCharges(quotationbean.getMinTransactionCharges());
		quote.setInsertedBy(quotationbean.getInsertedBy());
		quote.setInsertedDate(quotationbean.getInsertedDate());
		quote.setModifiedBy(quotationbean.getModifiedBy());
		quote.setModifiedDate(now);
		quote.setTermConditionComments(quotationbean.getTermConditionComments());
		HashMap<String,String> bankDet=getBankDetailsByBankUserId(quotationbean.getBankUserId()); 
		quote.setBankName(bankDet.get("bankname"));
		quote.setBranchName(bankDet.get("branchname"));
		quote.setSwiftCode(bankDet.get("swiftcode"));
		quote.setCountryName(bankDet.get("countryname"));
		quote.setEmailAddress(bankDet.get("emailaddress"));
		quote.setTelephone(bankDet.get("telephone"));
		quote.setMobileNumber(bankDet.get("mobileno"));
		quote.setFirstName(bankDet.get("firstname"));
		quote.setLastName(bankDet.get("lastname"));
		quote.setIsDeleted(null);
		quote.setValidityDate(quotationbean.getValidityDate());
		quotationRepo.save(quote);
	}

	@Override
	public void moveQuoteToHistory(Integer quotationId,String transId, String userId) {
		// TODO Auto-generated method stub
		// lcrepo.insertIntoMaster(transId, userId);
		EntityManager entityManager = em.createEntityManager();
		try {
		StoredProcedureQuery storedProcedureQuery = entityManager
				.createStoredProcedureQuery("move_quotation_to_historytbl", QuotationMaster.class);
		storedProcedureQuery.registerStoredProcedureParameter("inp_quotation_id", Integer.class, ParameterMode.IN);
		storedProcedureQuery.registerStoredProcedureParameter("inp_transaction_id", String.class, ParameterMode.IN);
		storedProcedureQuery.registerStoredProcedureParameter("inp_userid", String.class, ParameterMode.IN);
		storedProcedureQuery.setParameter("inp_quotation_id", quotationId);
		storedProcedureQuery.setParameter("inp_transaction_id", transId);
		storedProcedureQuery.setParameter("inp_userid", userId);

		storedProcedureQuery.execute();
		} catch (Exception e) {
			System.out.println(e);

		} finally {
			entityManager.close();

		}
	}

	@Override
	public void updateQuotationMasterDetails(QuotationMasterBean quotationbean) throws ParseException {
		Integer qid = quotationbean.getQuotationId();
		HashMap<String,String> bankDet=getBankDetailsByBankUserId(quotationbean.getBankUserId()); 
		QuotationMaster quote = quotationMasterRepo.getById(qid);
		System.out.println("Quotation id= " + qid);
		//System.out.println(" >>>> "+quotationbean.getMinTransactionCharges());
		quote.setUserId(quotationbean.getUserId());
		quote.setBankUserId(quotationbean.getBankUserId());
		quote.setTransactionId(quotationbean.getTransactionId());
		quote.setConfirmationCharges(quotationbean.getConfirmationCharges());
		quote.setConfChgsIssuanceToNegot(quotationbean.getConfChgsIssuanceToNegot());
		quote.setConfChgsIssuanceToexp(quotationbean.getConfChgsIssuanceToexp());
		quote.setConfChgsIssuanceToMatur(quotationbean.getConfChgsIssuanceToMatur());
		quote.setDiscountingCharges(quotationbean.getDiscountingCharges());
		quote.setBankAcceptCharges(quotationbean.getBankAcceptCharges());
		quote.setRefinancingCharges(quotationbean.getRefinancingCharges());
		quote.setApplicableBenchmark(quotationbean.getApplicableBenchmark());
		quote.setCommentsBenchmark(quotationbean.getCommentsBenchmark());
		quote.setNegotiationChargesFixed(quotationbean.getNegotiationChargesFixed());
		quote.setNegotiationChargesPerct(quotationbean.getNegotiationChargesPerct());
		Date now = new Date();
		quote.setDocHandlingCharges(quotationbean.getDocHandlingCharges());
		quote.setOtherCharges(quotationbean.getOtherCharges());
		quote.setChargesType(quotationbean.getChargesType());
		quote.setMinTransactionCharges(quotationbean.getMinTransactionCharges());
		quote.setTotalQuoteValue(quotationbean.getTotalQuoteValue());
		quote.setValidityDate(quotationbean.getValidityDate());
		quote.setInsertedBy(quotationbean.getInsertedBy());
		quote.setInsertedDate(quotationbean.getInsertedDate());
		quote.setModifiedBy(quotationbean.getModifiedBy());
		quote.setModifiedDate(now);
		quote.setTermConditionComments(quotationbean.getTermConditionComments());		
		quote.setBankName(bankDet.get("bankname"));
		quote.setBranchName(bankDet.get("branchname"));
		quote.setSwiftCode(bankDet.get("swiftcode"));
		quote.setCountryName(bankDet.get("countryname"));
		quote.setEmailAddress(bankDet.get("emailaddress"));
		quote.setTelephone(bankDet.get("telephone"));
		quote.setMobileNumber(bankDet.get("mobileno"));
		quote.setFirstName(bankDet.get("firstname"));
		quote.setLastName(bankDet.get("lastname"));
		
        final String qStatus = this.quotationMasterRepo.getStatusAfterReopen(qid);
        //final DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        final LocalDateTime nowForUpdate = LocalDateTime.now();
        final Date updateTodaydate = Date.from(nowForUpdate.atZone(ZoneId.systemDefault()).toInstant());
        System.out.println("Current Date: " + updateTodaydate);
        final String previousDate = this.quotationMasterRepo.getValidityDate(qid);
        final Date previousConvDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(previousDate);
        System.out.println("Previous Validity of Quote: " + previousConvDate);
        final Date validity = quotationbean.getValidityDate();
        System.out.println("Updated Validity of Quote: " + validity);
        final LocalDateTime ldt = LocalDateTime.ofInstant(validity.toInstant(), ZoneId.systemDefault()).plusDays(1L);
        final Date convValidity = Date.from(ldt.atZone(ZoneId.systemDefault()).toInstant());
        if ("ExpPlaced".equals(qStatus) && (updateTodaydate.before(convValidity) || updateTodaydate.compareTo(convValidity) != 0)) {
            this.lcMasterRepo.incrementQuotationReceived(quotationbean.getTransactionId(), quotationbean.getUserId());
            this.quotationMasterRepo.updateQuotationToActiveByQid(qid, quotationbean.getTransactionId());
        }
		
		quotationMasterRepo.save(quote);
	}

	@Override
	public Quotation getSpecificDraftQuotationDetail(Integer quotationId) {
		// TODO Auto-generated method stub
		return quotationRepo.findSpecificDraftQuotation(quotationId);
	}

	@Override
	public List<QuotationMasterBean> getQuotationDetailByUserIdAndTransactionId(String userId, String transactionId) {
		// TODO Auto-generated method stub
		//return quotationMasterRepo.findAllQuotationByUserIdAndTransactionId(userId, transactionId);
		List<QuotationMasterBean> qmList = new ArrayList<>();
		List<QuotationMaster> listOfQuotations=quotationMasterRepo.findAllQuotationByUserIdAndTransactionId(userId, transactionId);
		ModelMapperUtil modelMapper = new ModelMapperUtil();
		for(QuotationMaster qm:listOfQuotations)
		{
			QuotationMasterBean qmb=modelMapper.map(qm, QuotationMasterBean.class);
			qmb.setNoOfQuotesByBank(quotationMasterRepo.getQuotesCount(qm.getBankUserId()));
			qmb.setGoods(quotationMasterRepo.getGoodsByTransactionId(qm.getTransactionId()));
			qmb.setNoOfGoodsByBank(quotationMasterRepo.getGoodsCount(qmb.getGoods(), qm.getBankUserId()));
			qmList.add(qmb);
		}
		return qmList;
	}
	
	@Override
	public List<QuotationMaster> getQuotationDetailByUserIdAndTransactionIdStatus(String userId, String transactionId,String status) {
		// TODO Auto-generated method stub
		return quotationMasterRepo.findQuotationByUserIdAndTransactionIdStatus(userId, transactionId, status);
	}

	@Override
	public List<QuotationMaster> getQuotationDetailByQuotationId(Integer quotationId) {
		// TODO Auto-generated method stub
		return quotationMasterRepo.findAllQuotationByQuotationId(quotationId);
	}

	@Override
	public void updateQuotationForReject(Integer quotationId,String userId,String statusReason) {
		// TODO Auto-generated method stub
		//String quoteStatus
		GenericResponse response = new GenericResponse<>();
		Integer lcCount=0,utilizedLcCount=0;
		if(userId.substring(0, 2).equalsIgnoreCase("BA") == true)
		{
			quotationMasterRepo.updateQuotationStatusToReject(quotationId,"BANK",statusReason);
			quotationMasterRepo.refundCredit(userId);
			String custUserId=quotationMasterRepo.getUserIdByQid(quotationId);
			quotationMasterRepo.refundCredit(custUserId);
		}
		else
		{
			String tid=quotationMasterRepo.findTransactionIdByQid(quotationId);
			Integer ctr=lcservice.getReopenCounter(tid);
			if(ctr<=3)
			{
				String obtainUserId=lcservice.checkMasterForSubsidiary(userId);
				quotationMasterRepo.refundCredit(obtainUserId);
			}
			String bankUserId=quotationMasterRepo.getBankUserId(quotationId);
			quotationMasterRepo.refundCredit(bankUserId);
			quotationMasterRepo.updateQuotationStatusToReject(quotationId,"CUSTOMER",statusReason);
		}
		String tid=quotationMasterRepo.findTransactionIdByQid(quotationId);
		System.out.println("Quotation Rejected for trans: "+tid);
		lcMasterRepo.updateTransactionStatusToReject(tid,statusReason);
		trSavingRepo.deleteSavingsByTxnId(tid);
	}

	
	@Override
	public void updateQuotationForAccept(Integer quotationId, String transId) {
		// TODO Auto-generated method stub
		Integer qid=getRejectedQuotationByTransactionId(transId);
		System.out.println("Rejected QuotationId: "+qid);
		String quoteStatus=quotationMasterRepo.getStatus(qid);
		if(qid!=null)
		{
				quotationMasterRepo.updateQuotationStatusToExpiredExceptRejectedStatus(transId,qid);
				quotationMasterRepo.updateQuotationStatusToAccept(quotationId);
			
		}
		else
		{
				quotationMasterRepo.updateQuotationStatusToAccept(quotationId);
				quotationMasterRepo.updateQuotationStatusToExpired(transId,quotationId);
			
		}
		lcMasterRepo.updateTransactionStatusToAccept(transId);
	}

	@Override
	public List<QuotationMaster> getQuotationDetailByBankUserId(String bankUserId) {
		// TODO Auto-generated method stub
		return quotationMasterRepo.findAllQuotationBybankUserId(bankUserId);
	}
	
	
	@Override
	public List<String> getSavingsByUserId(String bankUserId) {
		// TODO Auto-generated method stub
		return quotationMasterRepo.findSavingsByUserId(bankUserId);
	}
	@Override
	public List<String> getTotalSavings(String bankUserId) {
		// TODO Auto-generated method stub
		return quotationMasterRepo.findTotalSavings(bankUserId);
	}
	@Override
	public List<String> getTotalSavingsUserId(String ccy,String bankUserId) {
		// TODO Auto-generated method stub
		return quotationMasterRepo.findTotalSavingsByUserId(ccy,bankUserId);
	}
	@Override
	public List<Quotation> getAllDraftQuotationDetailsByBankUserId(String bankUserId) {
		// TODO Auto-generated method stub
		return quotationRepo.findAllDraftQuotationByBankUserId(bankUserId);
	}

	@Override
	public List<TransactionQuotationBean> getTransactionQuotationDetailByBankUserIdAndStatus(String bankUserId,String quotationStatus) throws NumberFormatException,ParseException {
		
	//public List<TransactionQuotationBean> getTransactionQuotationDetailByBankUserIdAndStatus(String bankUserId,String quotationPlaced,String transactionStatus) throws NumberFormatException,ParseException {
		// TODO Auto-generated method stub
		List<NimaiClient> listOfAdditionalUser;
		if(bankUserId.substring(0, 2).equalsIgnoreCase("Al") == true)
			listOfAdditionalUser=quotationMasterRepo.getAdditionalUserList(bankUserId.replaceFirst("All", ""));
		else
			listOfAdditionalUser=quotationMasterRepo.getAdditionalUserList(bankUserId);
		System.out.println("List of additional user: "+listOfAdditionalUser);
		List<String> additionalList = new ArrayList<String>();
		for(NimaiClient nc:listOfAdditionalUser)
		{
			String user=nc.getUserid();
			additionalList.add(user);
		}
		//List<TransactionQuotationBean> details = quotationMasterRepo.findTransQuotationBybankUserIdAndStatus(bankUserId,quotationPlaced,transactionStatus);
		List<TransactionQuotationBean> details = quotationMasterRepo.findTransQuoteDetByBankUserIdListAndStatus(additionalList,quotationStatus);
		//List<TransactionQuotationBean> details = quotationMasterRepo.findTransQuotationBybankUserIdAndStatus(bankUserId,quotationStatus);
		List<TransactionQuotationBean> finalList=mapListToResponseBean(details);
		
		return finalList;
	}	
	
	@Override
	public List<TransactionQuotationBean> getTransactionQuotationDetailByQId(int qId) throws NumberFormatException,ParseException {
		
	//public List<TransactionQuotationBean> getTransactionQuotationDetailByBankUserIdAndStatus(String bankUserId,String quotationPlaced,String transactionStatus) throws NumberFormatException,ParseException {
		// TODO Auto-generated method stub
		
		//List<TransactionQuotationBean> details = quotationMasterRepo.findTransQuotationBybankUserIdAndStatus(bankUserId,quotationPlaced,transactionStatus);
		List<TransactionQuotationBean> details = quotationMasterRepo.findTransQuotationByqId(qId);
		List<TransactionQuotationBean> finalList=mapListToResponseBean(details);
		
		return finalList;
	}	
	
	@Override
	public List<TransactionQuotationBean> getAllDraftTransQuotationDetailsByBankUserId(String bankUserId) throws NumberFormatException, ParseException {
		// TODO Auto-generated method stub
		List<TransactionQuotationBean> details = quotationRepo.findDraftTransQuotationBybankUser(bankUserId);
		List<TransactionQuotationBean> finalList=mapListToResponseBean(details);
		
		return finalList;
	}
	
	
	private List<TransactionQuotationBean> mapListToResponseBean(List<TransactionQuotationBean> details) throws NumberFormatException,ParseException{
		// TODO Auto-generated method stub
		List<TransactionQuotationBean> list1 = new ArrayList<TransactionQuotationBean>();
		
		DateFormat simpleDateFormat=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		for(Object objA:details) 
		{
			
			TransactionQuotationBean responseBean=new TransactionQuotationBean();
			responseBean.setTransactionId(((Object[])objA)[0]==null?"null":((Object[])objA)[0].toString());
			responseBean.setUserId(((Object[])objA)[1]==null?"null":((Object[])objA)[1].toString());
			responseBean.setRequirementType(((Object[])objA)[2]==null?"null":((Object[])objA)[2].toString());
			responseBean.setlCIssuanceBank(((Object[])objA)[3]==null?"null":((Object[])objA)[3].toString());
			
			responseBean.setlCIssuanceBranch(((Object[])objA)[4]==null?"null":((Object[])objA)[4].toString());
			responseBean.setlCSwiftCode(((Object[])objA)[5]==null?"null":((Object[])objA)[5].toString());
			responseBean.setlCIssuanceCountry(((Object[])objA)[6]==null?"null":((Object[])objA)[6].toString());
			responseBean.setlCValue(((Object[])objA)[7]==null?0:Double.valueOf(((Object[])objA)[7].toString()));
			responseBean.setlCCurrency(((Object[])objA)[8]==null?"null":((Object[])objA)[8].toString());
			responseBean.setlCIssuingDate(((Object[])objA)[9]==null?new Date(0):(Date)simpleDateFormat.parse(((Object[])objA)[9].toString()));
			responseBean.setLastShipmentDate(((Object[])objA)[10]==null?new Date(0):(Date)simpleDateFormat.parse(((Object[])objA)[10].toString()));
			responseBean.setGoodsType(((Object[])objA)[11]==null?"null":((Object[])objA)[11].toString());
			responseBean.setNegotiationDate(((Object[])objA)[12]==null?new Date(0):(Date)simpleDateFormat.parse(((Object[])objA)[12].toString()));
			responseBean.setlCExpiryDate(((Object[])objA)[13]==null?new Date(0):(Date)simpleDateFormat.parse(((Object[])objA)[13].toString()));
			responseBean.setUsanceDays(((Object[])objA)[14]==null?0:Integer.valueOf(((Object[])objA)[14].toString()));
			responseBean.setPaymentTerms(((Object[])objA)[15]==null?"null":((Object[])objA)[15].toString());
			responseBean.setStartDate(((Object[])objA)[16]==null?new Date(0):(Date)simpleDateFormat.parse(((Object[])objA)[16].toString()));
			responseBean.setEndDate(((Object[])objA)[17]==null?new Date(0):(Date)simpleDateFormat.parse(((Object[])objA)[17].toString()));
			responseBean.setOriginalTenorDays(((Object[])objA)[18]==null?0:Integer.valueOf(((Object[])objA)[18].toString()));
			responseBean.setRefinancingPeriod(((Object[])objA)[19]==null?"null":((Object[])objA)[19].toString());
			responseBean.setlCMaturityDate(((Object[])objA)[20]==null?new Date(0):(Date)simpleDateFormat.parse(((Object[])objA)[20].toString()));
			responseBean.setlCNumber(((Object[])objA)[21]==null?"null":((Object[])objA)[21].toString());
			responseBean.setLastBeneBank(((Object[])objA)[22]==null?"null":((Object[])objA)[22].toString());
			responseBean.setLastBeneSwiftCode(((Object[])objA)[23]==null?"null":((Object[])objA)[23].toString());
			responseBean.setLastBankCountry(((Object[])objA)[24]==null?"null":((Object[])objA)[24].toString());
			responseBean.setRemarks(((Object[])objA)[25]==null?"null":((Object[])objA)[25].toString());
			responseBean.setDiscountingPeriod(((Object[])objA)[26]==null?"null":((Object[])objA)[26].toString());
			responseBean.setConfirmationPeriod(((Object[])objA)[27]==null?"null":((Object[])objA)[27].toString());
			responseBean.setFinancingPeriod(((Object[])objA)[28]==null?"null":((Object[])objA)[28].toString());
			responseBean.setUserType(((Object[])objA)[29]==null?"null":((Object[])objA)[29].toString());
			responseBean.setApplicantName(((Object[])objA)[30]==null?"null":((Object[])objA)[30].toString());
			responseBean.setApplicantCountry(((Object[])objA)[31]==null?"null":((Object[])objA)[31].toString());
			responseBean.setApplicantContactPerson(((Object[])objA)[32]==null?"null":((Object[])objA)[32].toString());
			responseBean.setApplicantContactPersonEmail(((Object[])objA)[33]==null?"null":((Object[])objA)[33].toString());
			responseBean.setBeneName(((Object[])objA)[34]==null?"null":((Object[])objA)[34].toString());
			responseBean.setBeneCountry(((Object[])objA)[35]==null?"null":((Object[])objA)[35].toString());
			responseBean.setBeneContactPerson(((Object[])objA)[36]==null?"null":((Object[])objA)[36].toString());
			responseBean.setBeneContactPersonEmail(((Object[])objA)[37]==null?"null":((Object[])objA)[37].toString());
			responseBean.setBeneBankName(((Object[])objA)[38]==null?"null":((Object[])objA)[38].toString());
			responseBean.setBeneSwiftCode(((Object[])objA)[39]==null?"null":((Object[])objA)[39].toString());
			responseBean.setBeneBankCountry(((Object[])objA)[40]==null?"null":((Object[])objA)[40].toString());
			responseBean.setLoadingCountry(((Object[])objA)[41]==null?"null":((Object[])objA)[41].toString());
			responseBean.setLoadingPort(((Object[])objA)[42]==null?"null":((Object[])objA)[42].toString());
			responseBean.setDischargeCountry(((Object[])objA)[43]==null?"null":((Object[])objA)[43].toString());
			responseBean.setDischargePort(((Object[])objA)[44]==null?"null":((Object[])objA)[44].toString());
			responseBean.setLcChargesType(((Object[])objA)[45]==null?"null":((Object[])objA)[45].toString());
			responseBean.setValidity(((Object[])objA)[46]==null?new Date(0):(Date)simpleDateFormat.parse(((Object[])objA)[46].toString()));
			responseBean.setLcProforma(((Object[])objA)[47]==null?"null":((Object[])objA)[47].toString());
			responseBean.setPaymentPeriod(((Object[])objA)[48]==null?"null":((Object[])objA)[48].toString());
			
			responseBean.setQuotationPlaced(((Object[])objA)[49]==null?"null":((Object[])objA)[49].toString());
			responseBean.setTransactionStatus(((Object[])objA)[50]==null?"null":((Object[])objA)[50].toString());
			responseBean.setAcceptedOn(((Object[])objA)[51]==null?new Date(0):(Date)simpleDateFormat.parse(((Object[])objA)[51].toString()));
			
			responseBean.setQuotationId(((Object[])objA)[52]==null?0:Integer.valueOf(((Object[])objA)[52].toString()));
			responseBean.setBankUserId(((Object[])objA)[53]==null?"null":((Object[])objA)[53].toString());
			responseBean.setConfirmationCharges(((Object[])objA)[54]==null?0:Float.valueOf(((Object[])objA)[54].toString()));
			responseBean.setConfChgsIssuanceToNegot(((Object[])objA)[55]==null?"null":((Object[])objA)[55].toString());
			responseBean.setConfChgsIssuanceToExp(((Object[])objA)[56]==null?"null":((Object[])objA)[56].toString());
			responseBean.setConfChgsIssuanceToMatur(((Object[])objA)[57]==null?"null":((Object[])objA)[57].toString());
			responseBean.setDiscountingCharges(((Object[])objA)[58]==null?0:Float.valueOf(((Object[])objA)[58].toString()));
			responseBean.setRefinancingCharges(((Object[])objA)[59]==null?0:Float.valueOf(((Object[])objA)[59].toString()));
			responseBean.setBankerAcceptCharges(((Object[])objA)[60]==null?0:Float.valueOf(((Object[])objA)[60].toString()));
			responseBean.setApplicableBenchmark(((Object[])objA)[61]==null?0:Float.valueOf(((Object[])objA)[61].toString()));
			responseBean.setCommentsBenchmark(((Object[])objA)[62]==null?"null":((Object[])objA)[62].toString());
			responseBean.setNegotiationChargesFixed(((Object[])objA)[63]==null?0:Float.valueOf(((Object[])objA)[63].toString()));
			responseBean.setNegotiationChargesPerct(((Object[])objA)[64]==null?0:Float.valueOf(((Object[])objA)[64].toString()));
			responseBean.setDocHandlingCharges(((Object[])objA)[65]==null?0:Float.valueOf(((Object[])objA)[65].toString()));
			responseBean.setOtherCharges(((Object[])objA)[66]==null?0:Float.valueOf(((Object[])objA)[66].toString()));
			responseBean.setChargesType(((Object[])objA)[67]==null?"null":((Object[])objA)[67].toString());
			responseBean.setMinTransactionCharges(((Object[])objA)[68]==null?0:Float.valueOf(((Object[])objA)[68].toString()));
			
			responseBean.setTotalQuoteValue(((Object[])objA)[69]==null?0:Float.valueOf(((Object[])objA)[69].toString()));
			responseBean.setValidityDate(((Object[])objA)[70]==null?new Date(0):(Date)simpleDateFormat.parse(((Object[])objA)[70].toString()));
			responseBean.setQuotationStatus(((Object[])objA)[71]==null?"null":((Object[])objA)[71].toString());
			responseBean.setQuoteRank(((Object[])objA)[72]==null?0:Integer.valueOf(((Object[])objA)[72].toString()));
			responseBean.setInsertedDate(((Object[])objA)[73]==null?new Date(0):(Date)simpleDateFormat.parse(((Object[])objA)[73].toString()));
			responseBean.setModifiedDate(((Object[])objA)[74]==null?new Date(0):(Date)simpleDateFormat.parse(((Object[])objA)[74].toString()));
			responseBean.setRejectedBy(((Object[])objA)[75]==null?"null":((Object[])objA)[75].toString());
			responseBean.setTermConditionComments(((Object[])objA)[76]==null?"null":((Object[])objA)[76].toString());
			Float acceptedQuoteValue=quotationMasterRepo.getAcceptedQuoteValue(responseBean.getTransactionId());
			responseBean.setAcceptedQuoteValue(acceptedQuoteValue);
			responseBean.setRejectedOn(((Object[])objA)[77]==null?new Date(0):(Date)simpleDateFormat.parse(((Object[])objA)[77].toString()));
			
			Date transValidityDate=responseBean.getValidity();
			Date quoteValidityDate=responseBean.getValidityDate();
			Date currentDate=new Date();
			
			LocalDateTime ldtForCurrentDate = LocalDateTime.ofInstant(currentDate.toInstant(), ZoneId.systemDefault()).plusDays(1);
			LocalDateTime ldtForTrans = LocalDateTime.ofInstant(transValidityDate.toInstant(), ZoneId.systemDefault()).plusDays(1);
			LocalDateTime ldtForQuote = LocalDateTime.ofInstant(quoteValidityDate.toInstant(), ZoneId.systemDefault()).plusDays(1);
			
			Date convCurrentDate = Date.from(ldtForCurrentDate.atZone(ZoneId.systemDefault()).toInstant());
			Date convTransValidity = Date.from(ldtForTrans.atZone(ZoneId.systemDefault()).toInstant());
			Date convQuoteValidity = Date.from(ldtForQuote.atZone(ZoneId.systemDefault()).toInstant());
			
		    
			System.out.println("Current Date: "+convCurrentDate);
			System.out.println("Transaction Validity Date: "+convTransValidity);
			System.out.println("Quotation Validity Date: "+convQuoteValidity);
			
			SimpleDateFormat objSDF = new SimpleDateFormat("yyyy-MM-dd");
			String s1=objSDF.format(convCurrentDate);
			String s2=objSDF.format(convTransValidity);
			String s3=objSDF.format(convQuoteValidity);
			Date dt_1 = objSDF.parse(s1);
			Date dt_2 = objSDF.parse(s2);
			Date dt_3 = objSDF.parse(s3);
			
			int noOfDaysForTransaction=dt_2.compareTo(dt_1);
			int noOfDaysForQuotation=dt_3.compareTo(dt_1);
			System.out.println("No of Days of trans: "+noOfDaysForTransaction);
			int days;
			System.out.println("Actual No of Days of trans: "+dt_2.compareTo(dt_1));
			if(noOfDaysForTransaction==0)
				responseBean.setTransactionDaysCountdown("Last Day");
			else
				if(noOfDaysForTransaction>0)
				{
					days=(int) ((convTransValidity.getTime() - currentDate.getTime()) / (1000 * 60 * 60 * 24));
					responseBean.setTransactionDaysCountdown(days+" Days to go");
				}
				else
					responseBean.setTransactionDaysCountdown("Expired");
			if(noOfDaysForQuotation==0)
				responseBean.setQuoteDaysCountdown("Last Day");
			else
				if(noOfDaysForQuotation>0)
				{
					days=(int) ((convQuoteValidity.getTime() - currentDate.getTime()) / (1000 * 60 * 60 * 24));
					responseBean.setQuoteDaysCountdown(days+" Days to go");
				}
				else
					responseBean.setQuoteDaysCountdown("Expired");
			
			responseBean.setExpiredOn(((Object[])objA)[78]==null?new Date(0):(Date)simpleDateFormat.parse(((Object[])objA)[78].toString()));
			responseBean.setRejectedReason(((Object[])objA)[79]==null?"null":((Object[])objA)[79].toString());
			responseBean.setTenorFile(((Object[])objA)[80]==null?"null":((Object[])objA)[80].toString());
			
			list1.add(responseBean);
		}
		return list1;
	}

	
		
		
		/*
		 * return details;
		 * 
		 * 
		 * 
		 * int key=1; for(Object o: details) { JSONArray array = new JSONArray();
		 * for(int i = 0; i < 15; i++) { try {
		 * array.put(listKey[i]+":"+((Object[])o)[i].toString());
		 * //jsonObject.put(""+key++, ((Object[])o)[i].toString());
		 * list1.add(listKey[i]); list2.add(((Object[])o)[i].toString());
		 * 
		 * //System.out.println(""+((Object[])o)[i].toString()); }
		 * catch(NullPointerException npe) { array.put(listKey[i]+":"+"null");
		 * list1.add(listKey[i]); list2.add("null"); //System.out.println("null"); }
		 * 
		 * } jsonObject.put("transaction "+key++,array);
		 * 
		 * }
		 * 
		 * System.out.println(""+jsonObject.toString());
		 * 
		 * for(int i=0;i<list1.size();i++) { for } return jsonObject.toString();
		 */

		


	private HashMap<String, Integer> generateQuote(Integer quotationId,String transId, String tableType) 
	{
		// TODO Auto-generated method stub
		HashMap<String, Integer> outputFields=calculateQuote(quotationId,transId,tableType);
		return outputFields;
	}

	@Override
	public String findBankUserIdByQuotationId(Integer quotationId) {
		// TODO Auto-generated method stub
		return quotationMasterRepo.findBankUserIdByQuotationId(quotationId);
		
	}

	@Override
	public HashMap<String,String> getBankDetailsByBankUserId(String bankUserId) 
	{
		// TODO Auto-generated method stub
		HashMap<String,String> bankDetMap=new HashMap<String, String>();
		List bankDetails=quotationMasterRepo.findBankDetailsBybankUserId(bankUserId);
		for(Object det:bankDetails) 
		{
			bankDetMap.put("firstname",((Object[])det)[0]==null?null:((Object[])det)[0].toString());
			bankDetMap.put("lastname",((Object[])det)[1]==null?null:((Object[])det)[1].toString());
			bankDetMap.put("emailaddress",((Object[])det)[2]==null?null:((Object[])det)[2].toString());
			bankDetMap.put("mobileno",((Object[])det)[3]==null?null:((Object[])det)[3].toString());
			bankDetMap.put("countryname",((Object[])det)[4]==null?null:((Object[])det)[4].toString());
			bankDetMap.put("bankname",((Object[])det)[5]==null?null:((Object[])det)[5].toString());
			bankDetMap.put("branchname",((Object[])det)[6]==null?null:((Object[])det)[6].toString());
			bankDetMap.put("swiftcode",((Object[])det)[7]==null?null:((Object[])det)[7].toString());
			bankDetMap.put("telephone",((Object[])det)[8]==null?null:((Object[])det)[8].toString());
		}
		
		return bankDetMap;
	}

	@Override
	public List<BankDetailsBean> getBankDet(String bankUserId) 
	{
		List<BankDetailsBean> list1 = new ArrayList<BankDetailsBean>();
		// TODO Auto-generated method stub
		List<BankDetailsBean> bankDetails=quotationMasterRepo.findBankDetailsBybankUserId(bankUserId);
		for(Object det:bankDetails) 
		{
			BankDetailsBean bDet=new BankDetailsBean();
			bDet.setBankUserId(bankUserId);
			bDet.setFirstName(((Object[])det)[0]==null?null:((Object[])det)[0].toString());
			bDet.setLastName(((Object[])det)[1]==null?null:((Object[])det)[1].toString());
			bDet.setEmailAddress(((Object[])det)[2]==null?null:((Object[])det)[2].toString());
			bDet.setMobileNumber(((Object[])det)[3]==null?null:((Object[])det)[3].toString());
			bDet.setCountryName(((Object[])det)[4]==null?null:((Object[])det)[4].toString());
			bDet.setBankName(((Object[])det)[5]==null?null:((Object[])det)[5].toString());
			bDet.setBranchName(((Object[])det)[6]==null?null:((Object[])det)[6].toString());
			bDet.setSwiftCode(((Object[])det)[7]==null?null:((Object[])det)[7].toString());
			bDet.setTelephone(((Object[])det)[8]==null?null:((Object[])det)[8].toString());
			
			list1.add(bDet);
		}
		
		return list1;
	}

	@Override
	public Quotation findDraftQuotation(String transId, String userId, String bankUserId) {
		// TODO Auto-generated method stub
		return quotationRepo.findQuotationDetails(transId,userId,bankUserId);
	}

	@Override
	public int findQuotationId(String transId, String userId, String bankUserId) {
		// TODO Auto-generated method stub
		return quotationRepo.getQuotationId(transId,userId,bankUserId);
	}

	@Override
	public QuotationMaster getDetailsOfAcceptedTrans(String transId, String userId) {
		// TODO Auto-generated method stub
		return quotationMasterRepo.findAcceptedTransByTransIdUserId(transId,userId);
	}
	
	@Override
	public QuotationMaster getDetailsOfAcceptedTrans(String transId) {
		// TODO Auto-generated method stub
		return quotationMasterRepo.findAcceptedTransByTransId(transId);
	}

	@Override
	public Integer getRejectedQuotationByTransactionId(String transactionId) {
		// TODO Auto-generated method stub
		return quotationMasterRepo.findRejectedQuotationByTransId(transactionId);
	}

	@Override
	public void updateQuotationStatusForReopenToRePlaced(Integer qid,String transId) throws ParseException {
		// TODO Auto-generated method stub
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");  
		LocalDateTime now = LocalDateTime.now();  
		Date todaydate = Date.from(now.atZone(ZoneId.systemDefault()).toInstant());
		List<QuotationMaster> listOfQuotation=quotationMasterRepo.findValidityDateAndQidByTransId(transId);
		NimaiLCMaster nlm=lcMasterRepo.findSpecificTransactionById(transId);
		//System.out.println("List: "+listOfQuotation);
		System.out.println("Current Date: "+todaydate);
		for(QuotationMaster qmb:listOfQuotation)
		{
			System.out.println("Original validity: "+nlm.getValidity());
			Date validity=qmb.getValidityDate();
			LocalDateTime ldt = LocalDateTime.ofInstant(validity.toInstant(), ZoneId.systemDefault()).plusDays(1);
			Date convValidity = Date.from(ldt.atZone(ZoneId.systemDefault()).toInstant());
			//if(!todaydate.before(convValidity))
			System.out.println("Validity(Active) of transaction: "+transId+" is "+convValidity);
			quotationMasterRepo.updateQuotationToRePlacedByQid(qmb.getQuotationId(), transId);
			if(todaydate.compareTo(convValidity)>0)
			{
				lcMasterRepo.updateQuotationReceivedCount1(transId,nlm.getUserId());
				quotationMasterRepo.updateQuotationStatusToExpPlacedForQid(qmb.getQuotationId());
			}
			
			/*
			 if(todaydate.compareTo(convValidity)>=0)
			
			{	//NewRequest
				System.out.println("Check: "+todaydate.after(convValidity));
				System.out.println("Validity(New R) of transaction: "+transId+" is "+convValidity);
				quotationMasterRepo.updateQuotationForNewRequestByQid(qmb.getQuotationId(),transId);
				
			}*/
			//else
			//{
				
			//}
			//System.out.println("Quotation Id: "+qmb.getQuotationId()+" Validity Date: "+qmb.getValidityDate());
		}
		trSavingRepo.deleteSavingsByTxnId(transId);
		//NewRequest
		//quotationMasterRepo.updateQuotationToActiveByQid(qid,transId);
	}

	@Override
	public QuotationMaster getQuotationOfAcceptedQuote(String transactionId) {
		// TODO Auto-generated method stub
		try
		{
			return quotationMasterRepo.getAcceptedQuoteByTransactionId(transactionId);
		}
		catch(Exception e)
		{
			System.out.println("No Data found: "+e);
			return null;
		}
		
	}

	@Override
	public void sendMailToBank(QuotationBean quotationBean, String bankEmailEvent) {
		// TODO Auto-generated method stub
		NimaiEmailSchedulerAlertToBanks schedulerEntity = new NimaiEmailSchedulerAlertToBanks();
		int quotationId = quotationBean.getQuotationId();
		QuotationMaster custTransactionDetails = quotationMasterRepo.getDetailsByQuoteId(quotationId);
		/*NimaiClient bankUserId = userDao.getCustDetailsByUserId(custTransactionDetails.getBankUserId());
		NimaiClient customerDetails = userDao.getCustDetailsByUserId(custTransactionDetails.getUserId());
		if (custTransactionDetails != null && bankUserId != null) {
			Calendar cal = Calendar.getInstance();
			Date insertedDate = cal.getTime();
			schedulerEntity.setInsertedDate(insertedDate);
			schedulerEntity.setQuotationId(quotationId);
			schedulerEntity.setTransactionid(custTransactionDetails.getTransactionId());
			schedulerEntity.setBankUserid(custTransactionDetails.getBankUserId());
			schedulerEntity.setBankUserName(bankUserId.getFirstName());
			schedulerEntity.setBanksEmailID(alertBanksBean.getBankEmail());
			schedulerEntity.setEmailFlag(alertBanksBean.getEmailFlag());
			schedulerEntity.setCustomerid(custTransactionDetails.getUserId());
			schedulerEntity.setCustomerEmail(customerDetails.getEmailAddress());
			schedulerEntity.setEmailEvent(bankEmailEvent);
			userDao.saveSchdulerData(schedulerEntity);
		} else {
			response.setErrCode("EX000");
			response.setMessage("Quotation Id Or Bank User Id not Found");
			return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
		}*/
	}

	@Override
	public Quotation getDraftQuotationDetails(Integer quotationId) {
		// TODO Auto-generated method stub
		return quotationRepo.findDraftQuotationByQuotationId(quotationId);
	}

	@Override
	public void deleteDraftQuotation(Integer quotationId) {
		// TODO Auto-generated method stub
		quotationRepo.deleteDraftQuotation(quotationId);
	}

	@Override
	public String getTransactionId(Integer quotationId) {
		// TODO Auto-generated method stub
		return quotationMasterRepo.getTransactionId(quotationId);
	}
	
	@Override
	public String getUserId(Integer quotationId) {
		// TODO Auto-generated method stub
		return quotationMasterRepo.getUserIdByQid(quotationId);
	}

	@Override
	public Double getQuoteValueByQid(Integer quotationId) {
		// TODO Auto-generated method stub
		return quotationMasterRepo.getQuoteAmount(quotationId);
	}

	@Override
	public void updateQuotationStatusForFreezeToPlaced(String transactionId,String bankUserId) {
		// TODO Auto-generated method stub
		quotationMasterRepo.updateQuotationToPlaced(transactionId,bankUserId);
	}

	@Override
	public Integer getQuotationIdByTransIdUserId(String transactionId, String userId, String status) {
		// TODO Auto-generated method stub
		return quotationMasterRepo.getQuotationId(transactionId,userId,status);
	}

	@Override
	public String getBankUserIdByQId(Integer quotationId) {
		// TODO Auto-generated method stub
		return quotationRepo.getBankUserId(quotationId);
	}

	@Override
	public boolean withdrawQuoteByQid(QuotationBean quotationbean) {
		// TODO Auto-generated method stub
		Integer qid=quotationbean.getQuotationId();
		System.out.println("Withdrawing quote: "+qid);
		try
		{
			quotationMasterRepo.updateStatusToWithdrawn(qid);
			String bankuserId=quotationMasterRepo.getBankUserId(qid);
			String custuserId=quotationMasterRepo.getUserIdByQid(qid);
			quotationMasterRepo.refundCredit(bankuserId);
			//quotationMasterRepo.refundCredit(custuserId);
			lcMasterRepo.updateQuotationReceivedCount1(quotationbean.getTransactionId(), quotationbean.getUserId());
			return true;
		}
		catch(Exception e)
		{
			return false;
		}
	}

	@Override
	public String calculateSavingPercent(String transId, Integer quotationId) {
		// TODO Auto-generated method stub
		Double annualAssetValue,netRevenue,avgSpread;
		String lcCountry=lcservice.getLCIssuingCountryByTransId(transId);
		String lcCurrency=lcservice.getLCCurrencyByTransId(transId);
		Integer tenorDays=lcservice.getLCTenorDays(transId);
		Double lcValue=lcservice.getLCValue(transId);
		Double t=Double.valueOf(tenorDays)/360;
		System.out.println("LC Issuing Country: "+lcCountry);
		System.out.println("LC Currency: "+lcCurrency);
		System.out.println("LC Tenor Days: "+tenorDays);
		try
		{
		//avgAmount=lcservice.getAvgAmountForCountryFromAdmin(lcCountry,lcCurrency);
		//Code on 13 Jan 2021
			annualAssetValue=lcservice.getAnnualAssetValue(lcCountry,lcCurrency);
			netRevenue=lcservice.getNetRevenueLC(lcCountry, lcCurrency);
			avgSpread=netRevenue/annualAssetValue;
		}
		catch(Exception ne)
		{
			annualAssetValue=0.0;
			netRevenue=0.0;
			avgSpread=0.0;
		}
		System.out.println("Annual Asset Value: "+annualAssetValue);
		System.out.println("Net Revenue: "+netRevenue);
		System.out.println("Avg Spread: "+avgSpread);
		Double quoteValue=quotationMasterRepo.getQuoteAmount(quotationId);//getQuoteValueByQid(quotationId);
		System.out.println("Quote Amount: "+quoteValue);
		
		System.out.println("Divided value: "+t);
		Double calculatedAnnualAssetValue=lcValue*t;
		Double cost=(quoteValue/lcValue)*(360/tenorDays);
		//Double avgSpreadUpd=avgSpread/100;
		//System.out.println("Avg Spread/100: "+avgSpreadUpd);
		Double savingPercent=avgSpread-cost;
		System.out.println("Calculated Annual Asset Value: "+calculatedAnnualAssetValue);
		System.out.println("Cost: "+cost);
		System.out.println("Saving Percent: "+savingPercent);
		Double savingValue=savingPercent*lcValue*t;
		System.out.println("Saving Value: "+ savingValue);
		Double updatedAnnualAssetValue=annualAssetValue+calculatedAnnualAssetValue;
		Double updatedNetRevenue=quoteValue+netRevenue;
		//quotationMasterRepo.saveDetailsToSavingInput(lcCountry,lcCurrency,updatedAnnualAssetValue,updatedNetRevenue);
		SavingInput si=new SavingInput();
		si.setCountryName(lcCountry);
		si.setCurrency(lcCurrency);
		si.setAnnualAssetValue(updatedAnnualAssetValue);
		si.setNetRevenue(updatedNetRevenue);
		savingRepo.save(si);
		return savingPercent+","+savingValue;
	}

	@Override
	public boolean checkDataForSaving(String lcCountry, String lcCurrency) {
		// TODO Auto-generated method stub
		List<SavingInput> data=savingRepo.checkForSavingData(lcCountry,lcCurrency);
		System.out.println("Data from saving input: "+data);
		if(data==null)
			return false;
		else
			return true;
	}

	@Override
	public List<QuotationMaster> checkQuotationPlacedOrNot(String transactionId, String bankUserId) {
		// TODO Auto-generated method stub
		return quotationMasterRepo.getDataToCheckQuotationPlaced(transactionId,bankUserId);
	}

	@Override
	public QuotationMaster getQuotationDetailByAcceptedQuotationId(Integer quotationId) {
		// TODO Auto-generated method stub
		return quotationMasterRepo.findQuotationByAcceptedQuotationId(quotationId);
	}

	
	
}
