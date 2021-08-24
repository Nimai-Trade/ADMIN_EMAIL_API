package com.nimai.lc.service;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.ParameterMode;
import javax.persistence.StoredProcedureQuery;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.nimai.lc.bean.CustomerTransactionBean;
import com.nimai.lc.bean.EligibleEmailBeanResponse;
import com.nimai.lc.bean.EligibleEmailList;
import com.nimai.lc.bean.NewRequestBean;
import com.nimai.lc.bean.NimaiCustomerBean;
import com.nimai.lc.bean.NimaiLCBean;
import com.nimai.lc.bean.NimaiLCMasterBean;
import com.nimai.lc.bean.QuotationMasterBean;
import com.nimai.lc.bean.TransactionQuotationBean;
import com.nimai.lc.entity.Countrycurrency;
import com.nimai.lc.entity.Goods;
import com.nimai.lc.entity.NewRequestEntity;
import com.nimai.lc.entity.NimaiClient;
import com.nimai.lc.entity.NimaiEmailSchedulerAlertToBanks;
import com.nimai.lc.entity.NewRequestEntity;
import com.nimai.lc.entity.NimaiLC;
import com.nimai.lc.entity.NimaiLCMaster;
import com.nimai.lc.entity.NimaiLCPort;
import com.nimai.lc.entity.NimaiSubscriptionDetails;
import com.nimai.lc.entity.QuotationMaster;
import com.nimai.lc.payload.GenericResponse;
import com.nimai.lc.repository.CountryRepository;
import com.nimai.lc.repository.CountrycurrencyRepository;
import com.nimai.lc.repository.GoodsRepository;
import com.nimai.lc.repository.LCCountryRepository;
import com.nimai.lc.repository.LCGoodsRepository;
import com.nimai.lc.repository.LCMasterRepository;
import com.nimai.lc.repository.LCPortRepository;
import com.nimai.lc.repository.LCRepository;
import com.nimai.lc.repository.NimaiClientRepository;
import com.nimai.lc.repository.NimaiEmailSchedulerAlertToBanksRepository;
import com.nimai.lc.repository.QuotationMasterRepository;
import com.nimai.lc.repository.QuotationRepository;
import com.nimai.lc.repository.TransactionSavingRepo;
import com.nimai.lc.utility.ErrorDescription;
import com.nimai.lc.utility.ModelMapperUtil;

@Service
public class LCServiceImpl implements LCService {

	@Autowired
	NimaiEmailSchedulerAlertToBanksRepository userDao;
	
	@Autowired
	LCRepository lcrepo;

	@Autowired
	LCMasterRepository lcmasterrepo;
	
	@Autowired
	QuotationRepository quoterepo;
	
	@Autowired
	QuotationMasterRepository quotemasterrepo;

	@Autowired
	LCCountryRepository lccountryrepo;

	@Autowired
	LCGoodsRepository lcgoodsrepo;

	@Autowired
	LCPortRepository lcportrepo;
	
	@Autowired
	CountrycurrencyRepository countryrepo;

	@Autowired
	CountryRepository countryRepo;
	
	@Autowired
	GoodsRepository goodsRepo;
	
	@Autowired
	NimaiClientRepository customerRepo;
	
	@Autowired
	TransactionSavingRepo trSavingRepo;
	
	@Autowired
	QuotationService quotationService;
	
	@Autowired
	EntityManagerFactory em;

	@Override
	public void saveLCdetails(NimaiLCBean nimailcbean, String tid) {

		NimaiLC nimailc = new NimaiLC();
		System.out.println("transaction id= " + tid);
		nimailc.setTransactionId(tid);
		nimailc.setUserId(nimailcbean.getUserId());
		nimailc.setRequirementType(nimailcbean.getRequirementType());
		nimailc.setlCIssuanceBank(nimailcbean.getlCIssuanceBank());
		nimailc.setlCIssuanceBranch(nimailcbean.getlCIssuanceBranch());
		nimailc.setSwiftCode(nimailcbean.getSwiftCode());
		nimailc.setlCIssuanceCountry(nimailcbean.getlCIssuanceCountry());
		nimailc.setlCIssuingDate(nimailcbean.getlCIssuingDate());
		nimailc.setlCExpiryDate(nimailcbean.getlCExpiryDate());
		nimailc.setlCValue(nimailcbean.getlCValue());
		nimailc.setlCCurrency(nimailcbean.getlCCurrency());
		nimailc.setLastShipmentDate(nimailcbean.getLastShipmentDate());
		nimailc.setNegotiationDate(nimailcbean.getNegotiationDate());
		nimailc.setPaymentPeriod(nimailcbean.getPaymentPeriod());
		nimailc.setPaymentTerms(nimailcbean.getPaymentTerms());
		nimailc.setTenorEndDate(nimailcbean.getTenorEndDate());
		nimailc.setUserType(nimailcbean.getUserType());
		nimailc.setApplicantName(nimailcbean.getApplicantName());
		nimailc.setApplicantCountry(nimailcbean.getApplicantCountry());
		nimailc.setApplicantContactPerson(nimailcbean.getApplicantContactPerson());
		nimailc.setApplicantContactPersonEmail(nimailcbean.getApplicantContactPersonEmail());
		nimailc.setBeneName(nimailcbean.getBeneName());
		nimailc.setBeneBankCountry(nimailcbean.getBeneBankCountry());
		nimailc.setBeneContactPerson(nimailcbean.getBeneContactPerson());
		nimailc.setBeneContactPersonEmail(nimailcbean.getBeneContactPersonEmail());
		nimailc.setBeneBankName(nimailcbean.getBeneBankName());
		nimailc.setBeneSwiftCode(nimailcbean.getBeneSwiftCode());
		nimailc.setBeneCountry(nimailcbean.getBeneCountry());
		nimailc.setLoadingCountry(nimailcbean.getLoadingCountry());
		nimailc.setLoadingPort(nimailcbean.getLoadingPort());
		nimailc.setDischargeCountry(nimailcbean.getDischargeCountry());
		nimailc.setDischargePort(nimailcbean.getDischargePort());
		nimailc.setChargesType(nimailcbean.getChargesType());
		nimailc.setValidity(nimailcbean.getValidity());
		nimailc.setInsertedDate(nimailcbean.getInsertedDate());
		nimailc.setInsertedBy(nimailcbean.getInsertedBy());
		nimailc.setModifiedDate(nimailcbean.getModifiedDate());
		nimailc.setModifiedBy(nimailcbean.getModifiedBy());
		nimailc.setTransactionflag(nimailcbean.getTransactionFlag());
		nimailc.setTransactionStatus(nimailcbean.getTransactionStatus());
		nimailc.setBranchUserId(nimailcbean.getBranchUserId());
		nimailc.setBranchUserEmail(nimailcbean.getBranchUserEmail());
		nimailc.setGoodsType(nimailcbean.getGoodsType());
		nimailc.setUsanceDays(nimailcbean.getUsanceDays());
		nimailc.setStartDate(nimailcbean.getStartDate());
		nimailc.setEndDate(nimailcbean.getEndDate());
		nimailc.setOriginalTenorDays(nimailcbean.getOriginalTenorDays());
		nimailc.setRefinancingPeriod(nimailcbean.getRefinancingPeriod());
		nimailc.setLcMaturityDate(nimailcbean.getLcMaturityDate());
		nimailc.setLcNumber(nimailcbean.getLcNumber());
		nimailc.setLastBeneBank(nimailcbean.getLastBeneBank());
		nimailc.setLastBeneSwiftCode(nimailcbean.getLastBeneSwiftCode());
		nimailc.setLastBankCountry(nimailcbean.getLastBankCountry());
		nimailc.setRemarks(nimailcbean.getRemarks());
		nimailc.setDiscountingPeriod(nimailcbean.getDiscountingPeriod());
		nimailc.setConfirmationPeriod(nimailcbean.getConfirmationPeriod());
		nimailc.setFinancingPeriod(nimailcbean.getFinancingPeriod());
		nimailc.setLcProForma(nimailcbean.getLcProForma());
		nimailc.setTenorFile(nimailcbean.getTenorFile());

		lcrepo.save(nimailc);

	}

	@Override
	public List<Countrycurrency> getCountry() {
		List<Countrycurrency> list=null;
		try
		{
			list = (List<Countrycurrency>) countryrepo.findAll();
			//return list;
		}
		catch(Exception e)
		{
			System.out.println(e);
		}
		return list;
	}

	@Override
	public List<NimaiLC> getAllDraftTransactionDetails(String userId, String branchEmailId) {
		// TODO Auto-generated method stub
		if (userId.substring(0, 2).equalsIgnoreCase("BC") == true) {
			return lcrepo.findAllDraftTransactionByBranchEmailId(branchEmailId);
			/*
			if (branchEmailId.equals(lcmasterrepo.getEmailAddress(userId))) {
				System.out.println("Bank as a customer - Master");
				return lcrepo.findAllDraftTransactionByUserId(userId);
			} else {
				return lcrepo.findAllDraftTransactionByUserIdAndBranchEmailId(userId, branchEmailId);
			}*/
		} else {
			//if(branchEmailId.equalsIgnoreCase(""))
			//	return lcrepo.findAllDraftTransactionByUserId(userId);
			//else
				return lcrepo.findAllDraftTransactionByUserIdBranchEmailId(userId,branchEmailId);
		}
	}

	@Override
	public List<NimaiLCMaster> getAllTransactionDetails() {
		List<NimaiLCMaster> allTransactionList = lcmasterrepo.findAllTransaction();

		return allTransactionList;
	}

	@Override
	public NimaiLCMaster getSpecificTransactionDetail(String transactionId) {
		// TODO Auto-generated method stub
		return lcmasterrepo.findSpecificTransactionById(transactionId);
	}

	@Override
	public NimaiLC getSpecificDraftTransactionDetail(String transactionId) {
		// TODO Auto-generated method stub
		return lcrepo.findSpecificDraftTransaction(transactionId);
	}

	@Override
	public List<NimaiLCMaster> getAllTransactionDetailsByStatus(String status) {
		// TODO Auto-generated method stub
		return lcmasterrepo.findAllTransactionByStatus(status);
		// return lcmasterrepo.findAllActiveTransaction();
	}

	@Override
	public List<NimaiLCMaster> getTransactionDetailByUserId(String userId, String branchEmailId) {
		// TODO Auto-generated method stub
		if (userId.substring(0, 2).equalsIgnoreCase("BC") == true) {
			if (branchEmailId.equals(lcmasterrepo.getEmailAddress(userId))) {
				System.out.println("Bank as a customer");
				return lcmasterrepo.findByTransactionByUserId(userId);
			} else {
				return lcmasterrepo.findByTransactionByUserIdAndBranchEmail(userId, branchEmailId);
			}
		} else {
			return lcmasterrepo.findByTransactionByUserId(userId);
		}
	}

	@Override
	public List<NimaiLCMaster> getTransactionDetailByUserIdAndStatus(String userId, String status, String branchEmailId) {
		// TODO Auto-generated method stub
		if(status.equalsIgnoreCase("Accepted"))
		{
			if (userId.substring(0, 2).equalsIgnoreCase("BC") == true) {
				if (branchEmailId.equals(lcmasterrepo.getEmailAddress(userId))) {
					System.out.println("Bank as a customer");
					return lcmasterrepo.findTransactionByUserIdAndAcceptedClosedStatus(userId);
				} else {
					return lcmasterrepo.findTransactionByUserIdAndAcceptedClosedStatusBranchEmail(userId, branchEmailId);
				}
			} else {
				return lcmasterrepo.findTransactionByUserIdAndAcceptedClosedStatus(userId);
			}
		}
		else
		{
			if (userId.substring(0, 2).equalsIgnoreCase("BC") == true) {
				if (branchEmailId.equals(lcmasterrepo.getEmailAddress(userId))) {
					System.out.println("Bank as a customer");
					return lcmasterrepo.findByTransactionByUserIdAndStatus(userId, status);
				} else {
					return lcmasterrepo.findByTransactionByUserIdStatusBranchEmail(userId, status, branchEmailId);
				}
			} else {
				return lcmasterrepo.findByTransactionByUserIdAndStatus(userId, status);
			}
		}
	}

	/*@Override
	public List<NimaiLCMaster> getAllTransactionForBank(String userid) {
		// TODO Auto-generated method stub
		EntityManager entityManager = em.createEntityManager();
		try {
			StoredProcedureQuery storedProcedureQuery = entityManager
					.createStoredProcedureQuery("get_transaction_for_bank", NimaiLCMaster.class);
			storedProcedureQuery.registerStoredProcedureParameter("user_id", String.class, ParameterMode.IN);
			storedProcedureQuery.setParameter("user_id", userid);
			storedProcedureQuery.execute();
			List<NimaiLCMaster> list = storedProcedureQuery.getResultList();
			return list;
		} catch (Exception e) {
			// TODO: handle exception

		} finally {
			entityManager.close();

		}
		return null;
	}*/
	
	@Override
	public List<NewRequestEntity> getAllTransactionForBank(String userid) {
		// TODO Auto-generated method stub
		//lcmasterrepo.clearTransactionForBank();
		EntityManager entityManager = em.createEntityManager();
		try {
			StoredProcedureQuery storedProcedureQuery = entityManager
					.createStoredProcedureQuery("get_transaction_for_bank", NewRequestEntity.class);
			storedProcedureQuery.registerStoredProcedureParameter("user_id", String.class, ParameterMode.IN);
			storedProcedureQuery.setParameter("user_id", userid);
			storedProcedureQuery.execute();
			List<NewRequestEntity> list = storedProcedureQuery.getResultList();
			
			return list;
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println(e);
		} finally {
			entityManager.close();

		}
		return null;
	}

	@Override
	public String generateSerialNo() {
		// TODO Auto-generated method stub

		/*
		 * Random randomNo=new Random(System.currentTimeMillis()); StringBuilder
		 * builder=new StringBuilder(); int length=6; for(int i=1;i<=length;i++) { int
		 * digit=randomNo.nextInt(6); builder.append(digit); } String
		 * no=builder.toString(); return no;
		 */

		Random rand = new Random();
		int ranInt = rand.nextInt(1000) + 1000;

		return String.valueOf(ranInt);
	}

	@Override
	public String generateYear() {
		// TODO Auto-generated method stub
		DateFormat df = new SimpleDateFormat("YY");
		StringBuilder yearbuilder = new StringBuilder();
		yearbuilder.append(
				Calendar.getInstance().get(Calendar.DATE) < 10 ? ("0" + (Calendar.getInstance().get(Calendar.DATE)))
						: (Calendar.getInstance().get(Calendar.DATE)));
		yearbuilder.append((Calendar.getInstance().get(Calendar.MONTH) + 1) < 10
				? ("0" + (Calendar.getInstance().get(Calendar.MONTH) + 1))
				: (Calendar.getInstance().get(Calendar.MONTH) + 1));
		yearbuilder.append(df.format(Calendar.getInstance().getTime()));
		String year = yearbuilder.toString();
		return year;
	}

	@Override
	public String generateCountryCode(String countryName) {
		// TODO Auto-generated method stub
		return countryRepo.getCountryCode(countryName);
	}

	@Override
	public String generateSubscriberType(String userid) {
		// TODO Auto-generated method stub
		return lcrepo.getSubscriberType(userid);
	}

	@Override
	public String generateTransactionType(String transType) {
		// TODO Auto-generated method stub
		String str = "";
		switch (transType) {
		case "Confirmation":
			str = "CONF";
			break;
		case "ConfirmAndDiscount":
			str = "CODI";
			break;
		case "Discounting":
			str = "DISC";
			break;
		case "Refinance":
			str = "REFI";
			break;
		case "Refinancing":
			str = "REFI";
			break;
		case "Banker":
			str = "BAAC";
			break;
		}
		return str;
	}

	@Override
	public String confirmLCDet(String transId, String userId) {
		// TODO Auto-generated method stub
		// lcrepo.insertIntoMaster(transId, userId);
		EntityManager entityManager = em.createEntityManager();
		try {
			StoredProcedureQuery storedProcedureQuery = entityManager.createStoredProcedureQuery("move_to_master",
					NimaiLC.class);

			storedProcedureQuery.registerStoredProcedureParameter("inp_transaction_id", String.class, ParameterMode.IN);
			storedProcedureQuery.registerStoredProcedureParameter("inp_userid", String.class, ParameterMode.IN);
			storedProcedureQuery.registerStoredProcedureParameter("validation_message", String.class,
					ParameterMode.OUT);
			storedProcedureQuery.setParameter("inp_transaction_id", transId);
			storedProcedureQuery.setParameter("inp_userid", userId);

			storedProcedureQuery.execute();
			String message = (String) storedProcedureQuery.getOutputParameterValue("validation_message");
			System.out.println("Status: " + message);
			return message;
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();

		} finally {
			entityManager.close();

		}
		return null;
	}

	@Override
	public void cloneLCDetail(String oldTransId, String newTransId) {
		// TODO Auto-generated method stub
		EntityManager entityManager = em.createEntityManager();
		try {
			StoredProcedureQuery storedProcedureQuery = entityManager.createStoredProcedureQuery("clone_transaction",
					NimaiLC.class);

			storedProcedureQuery.registerStoredProcedureParameter("inp_transaction_id", String.class, ParameterMode.IN);
			storedProcedureQuery.registerStoredProcedureParameter("updated_transaction_id", String.class,
					ParameterMode.IN);
			storedProcedureQuery.setParameter("inp_transaction_id", oldTransId);
			storedProcedureQuery.setParameter("updated_transaction_id", newTransId);

			storedProcedureQuery.execute();
		} catch (Exception e) {
			// TODO: handle exception

		} finally {
			entityManager.close();

		}
	}

	@Override
	public NimaiLCMaster checkTransaction(String transId) {
		// TODO Auto-generated method stub
		return lcmasterrepo.findSpecificTransactionById(transId);
	}

	@Override
	public NimaiLC findByTransactionIdToConfirm(String transId) {
		// TODO Auto-generated method stub
		return lcrepo.findTransactionIdToConfirm(transId);
	}

	@Override
	public NimaiLC findByTransactionUserIdToConfirm(String transId, String userId) {
		// TODO Auto-generated method stub
		return lcrepo.findTransactionUserIdToConfirm(transId, userId);
	}

	@Override
	public void moveToHistory(String transId, String userId) {
		// TODO Auto-generated method stub
		// lcrepo.insertIntoMaster(transId, userId);
		EntityManager entityManager = em.createEntityManager();
		try {
			StoredProcedureQuery storedProcedureQuery = entityManager.createStoredProcedureQuery("move_to_historytbl",
					NimaiLCMaster.class);

			storedProcedureQuery.registerStoredProcedureParameter("inp_transaction_id", String.class, ParameterMode.IN);
			storedProcedureQuery.registerStoredProcedureParameter("inp_userid", String.class, ParameterMode.IN);
			storedProcedureQuery.setParameter("inp_transaction_id", transId);
			storedProcedureQuery.setParameter("inp_userid", userId);

			storedProcedureQuery.execute();
		} catch (Exception e) {
			// TODO: handle exception

		} finally {
			entityManager.close();

		}
	}

	@Override
	public void saveLCMasterdetails(NimaiLCMasterBean nimailcbean, String tid) {
		int quoteReceived=lcmasterrepo.getTotalQuoteReceived(tid);
		System.out.println("Quote Received: "+quoteReceived);
		if(quoteReceived>=0)
		{
			quotemasterrepo.updateQuotationToFreezePlaced(tid);
		}
		NimaiLCMaster nimailc = new NimaiLCMaster();
		System.out.println("transaction id= " + tid);
		nimailc.setTransactionId(tid);
		nimailc.setUserId(nimailcbean.getUserId());
		nimailc.setRequirementType(nimailcbean.getRequirementType());
		nimailc.setlCIssuanceBank(nimailcbean.getlCIssuanceBank());
		nimailc.setlCIssuanceBranch(nimailcbean.getlCIssuanceBranch());
		nimailc.setSwiftCode(nimailcbean.getSwiftCode());
		nimailc.setlCIssuanceCountry(nimailcbean.getlCIssuanceCountry());
		nimailc.setlCIssuingDate(nimailcbean.getlCIssuingDate());
		nimailc.setlCExpiryDate(nimailcbean.getlCExpiryDate());
		nimailc.setlCValue(nimailcbean.getlCValue());
		nimailc.setlCCurrency(nimailcbean.getlCCurrency());
		nimailc.setLastShipmentDate(nimailcbean.getLastShipmentDate());
		nimailc.setNegotiationDate(nimailcbean.getNegotiationDate());
		nimailc.setPaymentPeriod(nimailcbean.getPaymentPeriod());
		nimailc.setPaymentTerms(nimailcbean.getPaymentTerms());
		nimailc.setTenorEndDate(nimailcbean.getTenorEndDate());
		nimailc.setUserType(nimailcbean.getUserType());
		nimailc.setApplicantName(nimailcbean.getApplicantName());
		nimailc.setApplicantCountry(nimailcbean.getApplicantCountry());
		nimailc.setApplicantContactPerson(nimailcbean.getApplicantContactPerson());
		nimailc.setApplicantContactPersonEmail(nimailcbean.getApplicantContactPersonEmail());
		nimailc.setBeneName(nimailcbean.getBeneName());
		nimailc.setBeneBankCountry(nimailcbean.getBeneBankCountry());
		nimailc.setBeneContactPerson(nimailcbean.getBeneContactPerson());
		nimailc.setBeneContactPersonEmail(nimailcbean.getBeneContactPersonEmail());
		nimailc.setBeneBankName(nimailcbean.getBeneBankName());
		nimailc.setBeneSwiftCode(nimailcbean.getBeneSwiftCode());
		nimailc.setBeneCountry(nimailcbean.getBeneCountry());
		nimailc.setLoadingCountry(nimailcbean.getLoadingCountry());
		nimailc.setLoadingPort(nimailcbean.getLoadingPort());
		nimailc.setDischargeCountry(nimailcbean.getDischargeCountry());
		nimailc.setDischargePort(nimailcbean.getDischargePort());
		nimailc.setChargesType(nimailcbean.getChargesType());
		nimailc.setValidity(nimailcbean.getValidity());
		Date now = new Date();
		nimailc.setInsertedDate(now);
		nimailc.setInsertedBy(nimailcbean.getInsertedBy());
		nimailc.setModifiedDate(nimailcbean.getModifiedDate());
		nimailc.setModifiedBy(nimailcbean.getModifiedBy());
		nimailc.setTransactionflag(nimailcbean.getTransactionFlag());
		nimailc.setTransactionStatus(nimailcbean.getTransactionStatus());
		nimailc.setBranchUserId(nimailcbean.getBranchUserId());
		nimailc.setBranchUserEmail(nimailcbean.getBranchUserEmail());
		nimailc.setGoodsType(nimailcbean.getGoodsType());
		nimailc.setUsanceDays(nimailcbean.getUsanceDays());
		nimailc.setStartDate(nimailcbean.getStartDate());
		nimailc.setEndDate(nimailcbean.getEndDate());
		nimailc.setOriginalTenorDays(nimailcbean.getOriginalTenorDays());
		nimailc.setRefinancingPeriod(nimailcbean.getRefinancingPeriod());
		nimailc.setLcMaturityDate(nimailcbean.getLcMaturityDate());
		nimailc.setLcNumber(nimailcbean.getLcNumber());
		nimailc.setLastBeneBank(nimailcbean.getLastBeneBank());
		nimailc.setLastBeneSwiftCode(nimailcbean.getLastBeneSwiftCode());
		nimailc.setLastBankCountry(nimailcbean.getLastBankCountry());
		nimailc.setRemarks(nimailcbean.getRemarks());
		nimailc.setDiscountingPeriod(nimailcbean.getDiscountingPeriod());
		nimailc.setConfirmationPeriod(nimailcbean.getConfirmationPeriod());
		nimailc.setFinancingPeriod(nimailcbean.getFinancingPeriod());
		nimailc.setLcProForma(nimailcbean.getLcProForma());
		nimailc.setTenorFile(nimailcbean.getTenorFile());
		nimailc.setQuotationReceived(nimailcbean.getQuotationReceived());

		lcmasterrepo.save(nimailc);
		
		quoterepo.deleteQuoteByTrasanctionId(nimailc.getTransactionId(),nimailc.getUserId());
	}

	@Override
	public void updateDraftLCdetails(NimaiLCBean nimailcbean, String newtid) {
		String tid = nimailcbean.getTransactionId();
		NimaiLC nimailc = lcrepo.getOne(tid);
		System.out.println("transaction id= " + tid);
		//nimailc.setOldTransactionId(tid);
		nimailc.setUserId(nimailcbean.getUserId());
		nimailc.setRequirementType(nimailcbean.getRequirementType());
		nimailc.setlCIssuanceBank(nimailcbean.getlCIssuanceBank());
		nimailc.setlCIssuanceBranch(nimailcbean.getlCIssuanceBranch());
		nimailc.setSwiftCode(nimailcbean.getSwiftCode());
		nimailc.setlCIssuanceCountry(nimailcbean.getlCIssuanceCountry());
		nimailc.setlCIssuingDate(nimailcbean.getlCIssuingDate());
		nimailc.setlCExpiryDate(nimailcbean.getlCExpiryDate());
		nimailc.setlCValue(nimailcbean.getlCValue());
		nimailc.setlCCurrency(nimailcbean.getlCCurrency());
		nimailc.setLastShipmentDate(nimailcbean.getLastShipmentDate());
		nimailc.setNegotiationDate(nimailcbean.getNegotiationDate());
		nimailc.setPaymentPeriod(nimailcbean.getPaymentPeriod());
		nimailc.setPaymentTerms(nimailcbean.getPaymentTerms());
		nimailc.setTenorEndDate(nimailcbean.getTenorEndDate());
		nimailc.setUserType(nimailcbean.getUserType());
		nimailc.setApplicantName(nimailcbean.getApplicantName());
		nimailc.setApplicantCountry(nimailcbean.getApplicantCountry());
		nimailc.setApplicantContactPerson(nimailcbean.getApplicantContactPerson());
		nimailc.setApplicantContactPersonEmail(nimailcbean.getApplicantContactPersonEmail());
		nimailc.setBeneName(nimailcbean.getBeneName());
		nimailc.setBeneBankCountry(nimailcbean.getBeneBankCountry());
		nimailc.setBeneContactPerson(nimailcbean.getBeneContactPerson());
		nimailc.setBeneContactPersonEmail(nimailcbean.getBeneContactPersonEmail());
		nimailc.setBeneBankName(nimailcbean.getBeneBankName());
		nimailc.setBeneSwiftCode(nimailcbean.getBeneSwiftCode());
		nimailc.setBeneCountry(nimailcbean.getBeneCountry());
		nimailc.setLoadingCountry(nimailcbean.getLoadingCountry());
		nimailc.setLoadingPort(nimailcbean.getLoadingPort());
		nimailc.setDischargeCountry(nimailcbean.getDischargeCountry());
		nimailc.setDischargePort(nimailcbean.getDischargePort());
		nimailc.setChargesType(nimailcbean.getChargesType());
		nimailc.setValidity(nimailcbean.getValidity());
		Date now = new Date();
		nimailc.setInsertedDate(nimailcbean.getInsertedDate());
		nimailc.setInsertedBy(nimailcbean.getInsertedBy());
		nimailc.setModifiedDate(now);
		nimailc.setModifiedBy(nimailcbean.getModifiedBy());
		nimailc.setTransactionflag(nimailcbean.getTransactionFlag());
		nimailc.setTransactionStatus(nimailcbean.getTransactionStatus());
		nimailc.setBranchUserId(nimailcbean.getBranchUserId());
		nimailc.setBranchUserEmail(nimailcbean.getBranchUserEmail());
		nimailc.setGoodsType(nimailcbean.getGoodsType());
		nimailc.setUsanceDays(nimailcbean.getUsanceDays());
		nimailc.setStartDate(nimailcbean.getStartDate());
		nimailc.setEndDate(nimailcbean.getEndDate());
		nimailc.setOriginalTenorDays(nimailcbean.getOriginalTenorDays());
		nimailc.setRefinancingPeriod(nimailcbean.getRefinancingPeriod());
		nimailc.setLcMaturityDate(nimailcbean.getLcMaturityDate());
		nimailc.setLcNumber(nimailcbean.getLcNumber());
		nimailc.setLastBeneBank(nimailcbean.getLastBeneBank());
		nimailc.setLastBeneSwiftCode(nimailcbean.getLastBeneSwiftCode());
		nimailc.setLastBankCountry(nimailcbean.getLastBankCountry());
		nimailc.setRemarks(nimailcbean.getRemarks());
		nimailc.setDiscountingPeriod(nimailcbean.getDiscountingPeriod());
		nimailc.setConfirmationPeriod(nimailcbean.getConfirmationPeriod());
		nimailc.setFinancingPeriod(nimailcbean.getFinancingPeriod());
		nimailc.setLcProForma(nimailcbean.getLcProForma());
		nimailc.setTenorFile(nimailcbean.getTenorFile());

		lcrepo.save(nimailc);

		
		lcrepo.updateTransactionIdByNew(nimailc.getTransactionId(),newtid);
	}

	@Override
	public Integer getLcCount(String userId) {
		// TODO Auto-generated method stub
		Integer lccount;
		try {
			lccount = lcrepo.findLCCount(userId);
		} catch (NullPointerException ne) {
			lccount = 0;
		}
		return lccount;
	}

	@Override
	public Integer getUtilizedLcCount(String userId) {
		// TODO Auto-generated method stub
		Integer lcutilized;
		try {
			lcutilized = lcrepo.findUtilzedLCCount(userId);
		} catch (NullPointerException ne) {
			lcutilized = 0;
		}
		return lcutilized;
	}

	@Override
	public NimaiLCMaster getTransactionForAcceptCheck(String transId) {
		// TODO Auto-generated method stub
		return lcmasterrepo.getTransactionByTransIdTrStatusAndQuoteStatus(transId);
	}

	@Override
	public void updateTransactionStatusToActive(String transactionId, String userId) {
		// TODO Auto-generated method stub
		lcmasterrepo.updateTransactionStatusToActive(transactionId, userId);
	}

	@Override
	public String checkMasterForSubsidiary(String userId) 
	{
		// TODO Auto-generated method stub
		
		String checkForSubsidiary="";
		String checkForAdditionalUser="";
		if(userId.substring(0, 2).equalsIgnoreCase("CU"))
		{
			checkForSubsidiary=lcmasterrepo.getAccountType(userId);
		}
		else
		{
			checkForAdditionalUser=lcmasterrepo.getAccountType(userId);
		}
		if(checkForSubsidiary.equalsIgnoreCase("subsidiary") || checkForAdditionalUser.equalsIgnoreCase("bankuser"))
		{
			System.out.println("===== Getting Master User ====");
			String masterUserId=lcmasterrepo.findMasterForSubsidiary(userId);
			System.out.println("User is Subsidiary of Master User: "+masterUserId);
			return masterUserId;
		}
		else
		{
			System.out.println(userId+" is Master User");
			return userId;
		}
	}

	@Override
	public Integer getNoOfBanksAgainstCountry(String countryName) {
		// TODO Auto-generated method stub
		return lcrepo.getBanksCountForCountry(countryName);
	}

	@Override
	public void updateTransactionForClosed(String transactionId, String userId, String reason) 
	{
		// TODO Auto-generated method stub
		lcmasterrepo.updateTransactionStatusToClosed(transactionId, userId, reason);
	}

	@Override
	public List<NimaiLC> getDraftTransactionDetails(String transactionId) {
		// TODO Auto-generated method stub
		return lcrepo.findDraftTransactionByTransactionId(transactionId);
	}

	@Override
	public void deleteDraftTransaction(String transactionId) {
		// TODO Auto-generated method stub
		lcrepo.deleteDraftTransaction(transactionId);
	}
	
	@Override
	public void getAlleligibleBAnksEmail(String userId,String transactionId,int quoteId,String bankEmailEvent,String custEmailEvent)
	{
		if("LC_REOPENING_ALERT_ToBanks".equals(bankEmailEvent))
		{
			List<QuotationMaster> qmList=quotemasterrepo.findAllReplacedQuotationByUserIdAndTransactionId(userId, transactionId);
			for(QuotationMaster qm:qmList)
			{
				
				NimaiClient bankUserData = userDao.getCustDetailsByUserId(qm.getBankUserId());
				NimaiEmailSchedulerAlertToBanks schedulerEntity = new NimaiEmailSchedulerAlertToBanks();
				Calendar cal = Calendar.getInstance();
				Date insertedDate = cal.getTime();
				schedulerEntity.setInsertedDate(insertedDate);
				schedulerEntity.setQuotationId(qm.getQuotationId());
				schedulerEntity.setTransactionid(transactionId);
				schedulerEntity.setBankUserid(qm.getBankUserId());
				schedulerEntity.setBankUserName(bankUserData.getFirstName());
				schedulerEntity.setBanksEmailID(bankUserData.getEmailAddress());
				schedulerEntity.setEmailFlag("Pending");
				//schedulerEntity.setCustomerid(custTransactionDetails.getUserId());
				//schedulerEntity.setCustomerEmail(customerDetails.getEmailAddress());
				schedulerEntity.setEmailEvent(bankEmailEvent);
				
				schedulerEntity.setCustomerid(userId);
				schedulerEntity.setTransactionid(transactionId);
				//schedulerEntity.setEmailEvent(custEmailEvent);
				userDao.save(schedulerEntity);
			}
		}
		else if("QUOTE_REJECTION".equals(bankEmailEvent))
		{
			String bankUserId=quotemasterrepo.getBankUserId(quoteId);
			NimaiClient bankUserData = userDao.getCustDetailsByUserId(bankUserId);
			NimaiEmailSchedulerAlertToBanks schedulerEntity = new NimaiEmailSchedulerAlertToBanks();
			Calendar cal = Calendar.getInstance();
			Date insertedDate = cal.getTime();
			schedulerEntity.setInsertedDate(insertedDate);
			schedulerEntity.setQuotationId(quoteId);
			schedulerEntity.setTransactionid(transactionId);
			schedulerEntity.setBankUserid(bankUserId);
			schedulerEntity.setBankUserName(bankUserData.getFirstName());
			schedulerEntity.setBanksEmailID(bankUserData.getEmailAddress());
			schedulerEntity.setEmailFlag("Pending");
			//schedulerEntity.setCustomerid(custTransactionDetails.getUserId());
			//schedulerEntity.setCustomerEmail(customerDetails.getEmailAddress());
			schedulerEntity.setEmailEvent(bankEmailEvent);
			String custUserName=lcmasterrepo.getCustomerName(userId);
			String custEmailId=lcmasterrepo.getCustomerEmailId(userId);
			schedulerEntity.setQuotationId(quoteId);
			schedulerEntity.setCustomerid(userId);
			schedulerEntity.setCustomerUserName(custUserName==null?"":custUserName);
			schedulerEntity.setCustomerEmail(custEmailId==null?"":custEmailId);
			schedulerEntity.setTransactionid(transactionId);
			//schedulerEntity.setEmailEvent(custEmailEvent);
			userDao.save(schedulerEntity);
			NimaiEmailSchedulerAlertToBanks schedulerEntityCu = new NimaiEmailSchedulerAlertToBanks();
			
			schedulerEntityCu.setInsertedDate(insertedDate);
			schedulerEntityCu.setQuotationId(quoteId);
			schedulerEntityCu.setTransactionid(transactionId);
			schedulerEntityCu.setBankUserid(bankUserId);
			schedulerEntityCu.setBankUserName(bankUserData.getFirstName());
			schedulerEntityCu.setBanksEmailID(bankUserData.getEmailAddress());
			schedulerEntityCu.setEmailFlag("Pending");
			//schedulerEntity.setCustomerid(custTransactionDetails.getUserId());
			//schedulerEntity.setCustomerEmail(customerDetails.getEmailAddress());
			schedulerEntityCu.setEmailEvent("QUOTE_REJECTION_CUSTOMER");
			schedulerEntityCu.setQuotationId(quoteId);
			schedulerEntityCu.setCustomerid(userId);
			schedulerEntityCu.setCustomerUserName(custUserName==null?"":custUserName);
			schedulerEntityCu.setCustomerEmail(custEmailId==null?"":custEmailId);
			schedulerEntityCu.setTransactionid(transactionId);
			//schedulerEntity.setEmailEvent(custEmailEvent);
			userDao.save(schedulerEntityCu);
		}
		else if("QUOTE_ACCEPT".equals(bankEmailEvent))
		{
			String bankUserId=quotemasterrepo.getBankUserId(quoteId);
			NimaiClient bankUserData = userDao.getCustDetailsByUserId(bankUserId);
			NimaiEmailSchedulerAlertToBanks schedulerEntity = new NimaiEmailSchedulerAlertToBanks();
			Calendar cal = Calendar.getInstance();
			Date insertedDate = cal.getTime();
			schedulerEntity.setInsertedDate(insertedDate);
			schedulerEntity.setQuotationId(quoteId);
			schedulerEntity.setTransactionid(transactionId);
			schedulerEntity.setBankUserid(bankUserId);
			schedulerEntity.setBankUserName(bankUserData.getFirstName());
			schedulerEntity.setBanksEmailID(bankUserData.getEmailAddress());
			schedulerEntity.setEmailFlag("Pending");
			//schedulerEntity.setCustomerid(custTransactionDetails.getUserId());
			//schedulerEntity.setCustomerEmail(customerDetails.getEmailAddress());
			schedulerEntity.setEmailEvent(bankEmailEvent);
			String custUserName=lcmasterrepo.getCustomerName(userId);
			String custEmailId=lcmasterrepo.getCustomerEmailId(userId);
			//schedulerEntity.setQuotationId(quoteId);
			schedulerEntity.setCustomerid(userId);
			schedulerEntity.setCustomerUserName(custUserName==null?"":custUserName);
			schedulerEntity.setCustomerEmail(custEmailId==null?"":custEmailId);
			schedulerEntity.setTransactionid(transactionId);
			//schedulerEntity.setEmailEvent(custEmailEvent);
			userDao.save(schedulerEntity);
			NimaiEmailSchedulerAlertToBanks schedulerEntityCUst = new NimaiEmailSchedulerAlertToBanks();
			
			schedulerEntityCUst.setInsertedDate(insertedDate);
			schedulerEntityCUst.setQuotationId(quoteId);
			schedulerEntityCUst.setTransactionid(transactionId);
			schedulerEntityCUst.setBankUserid(bankUserId);
			schedulerEntityCUst.setBankUserName(bankUserData.getFirstName());
			schedulerEntityCUst.setBanksEmailID(bankUserData.getEmailAddress());
			schedulerEntityCUst.setEmailFlag("Pending");
			schedulerEntityCUst.setEmailEvent("QUOTE_ACCEPT_CUSTOMER");
			schedulerEntityCUst.setCustomerid(userId);
			schedulerEntityCUst.setCustomerUserName(custUserName==null?"":custUserName);
			schedulerEntityCUst.setCustomerEmail(custEmailId==null?"":custEmailId);
			schedulerEntityCUst.setTransactionid(transactionId);
			userDao.save(schedulerEntityCUst);

			NimaiEmailSchedulerAlertToBanks winningQuote = new NimaiEmailSchedulerAlertToBanks();
			winningQuote.setTransactionid(transactionId);
			winningQuote.setInsertedDate(insertedDate);
			winningQuote.setQuotationId(quoteId);
			winningQuote.setTransactionid(transactionId);
			winningQuote.setBankUserid(bankUserId);
			winningQuote.setBankUserName(bankUserData.getFirstName());
			winningQuote.setBanksEmailID(bankUserData.getEmailAddress());
			winningQuote.setCustomerid(userId);
			winningQuote.setCustomerUserName(custUserName==null?"":custUserName);
			winningQuote.setCustomerEmail(custEmailId==null?"":custEmailId);
			winningQuote.setEmailEvent("Winning_Quote_Data");
			winningQuote.setEmailFlag("Pending");
			userDao.save(winningQuote);
			
		}
		else if("QUOTE_PLACE_ALERT_ToBanks".equals(bankEmailEvent) || "QUOTE_ACCEPT".equals(bankEmailEvent))
		{
			String bankUserId=quotemasterrepo.getBankUserId(quoteId);
			NimaiClient bankUserData = userDao.getCustDetailsByUserId(bankUserId);
			NimaiEmailSchedulerAlertToBanks schedulerEntity = new NimaiEmailSchedulerAlertToBanks();
			Calendar cal = Calendar.getInstance();
			Date insertedDate = cal.getTime();
			schedulerEntity.setInsertedDate(insertedDate);
			schedulerEntity.setQuotationId(quoteId);
			schedulerEntity.setTransactionid(transactionId);
			schedulerEntity.setCustomerid(userId);
			schedulerEntity.setBankUserid(bankUserId);
			schedulerEntity.setBankUserName(bankUserData.getFirstName());
			schedulerEntity.setBanksEmailID(bankUserData.getEmailAddress());
			schedulerEntity.setEmailFlag("Pending");
			//schedulerEntity.setCustomerid(custTransactionDetails.getUserId());
			//schedulerEntity.setCustomerEmail(customerDetails.getEmailAddress());
			schedulerEntity.setEmailEvent(bankEmailEvent);
			userDao.save(schedulerEntity);
			NimaiEmailSchedulerAlertToBanks schedulerEntityCust = new NimaiEmailSchedulerAlertToBanks();
			
			String custUserName=lcmasterrepo.getCustomerName(userId);
			String custEmailId=lcmasterrepo.getCustomerEmailId(userId);
			schedulerEntityCust.setInsertedDate(insertedDate);
			schedulerEntityCust.setQuotationId(quoteId);
			schedulerEntityCust.setCustomerid(userId);
			schedulerEntityCust.setCustomerUserName(custUserName==null?"":custUserName);
			schedulerEntityCust.setCustomerEmail(custEmailId==null?"":custEmailId);
			schedulerEntityCust.setTransactionid(transactionId);
			schedulerEntityCust.setEmailEvent(custEmailEvent);
		
			schedulerEntityCust.setEmailFlag("Pending");
			userDao.save(schedulerEntityCust);
		}
		else
		{
		System.out.println("======== Getting eligible bank ========");
		EntityManager entityManager = em.createEntityManager();
		try 
		{
			
			StoredProcedureQuery getBAnksEmail = entityManager
							.createStoredProcedureQuery("get_eligible_banks", NimaiClient.class);
			getBAnksEmail.registerStoredProcedureParameter("inp_customer_userID", String.class,
							ParameterMode.IN);
			getBAnksEmail.registerStoredProcedureParameter("inp_transaction_ID", String.class,
							ParameterMode.IN);

			getBAnksEmail.setParameter("inp_customer_userID", userId);
			getBAnksEmail.setParameter("inp_transaction_ID", transactionId);
			getBAnksEmail.execute();
			ModelMapperUtil modelMapper = new ModelMapperUtil();
			List<NimaiClient> nimaiCust = getBAnksEmail.getResultList();
			
			EligibleEmailBeanResponse responseBean = new EligibleEmailBeanResponse();
			//String custEmailId="";
			List<EligibleEmailList> emailId = nimaiCust.stream().map(obj -> {
			EligibleEmailList data = new EligibleEmailList();
			NimaiEmailSchedulerAlertToBanks schedulerEntity = new NimaiEmailSchedulerAlertToBanks();
			Calendar cal = Calendar.getInstance();
			Date insertedDate = cal.getTime();
			schedulerEntity.setInsertedDate(insertedDate);
			schedulerEntity.setCustomerid(userId);
			System.out.println("Userid:"+userId);
			schedulerEntity.setTransactionid(transactionId);
			schedulerEntity.setEmailEvent(bankEmailEvent);
			/* while updating set event as */
			//schedulerEntity.setEmailEvent("LC_UPDATE_ALERT_ToBanks");
			schedulerEntity.setBanksEmailID(obj.getEmailAddress());
			schedulerEntity.setBankUserid(obj.getUserid());
			schedulerEntity.setBankUserName(obj.getFirstName());
			schedulerEntity.setEmailFlag("Pending");
			userDao.save(schedulerEntity);
			data.setEmailList(obj.getEmailAddress());
			return data;
			}).collect(Collectors.toList());

			if(nimaiCust.isEmpty())
			{
				System.out.println("No Banks Eligible");
				
			}
			
			Calendar cal = Calendar.getInstance();
			Date insertedDate = cal.getTime();
			NimaiEmailSchedulerAlertToBanks schedulerEntityCust = new NimaiEmailSchedulerAlertToBanks();
			NimaiLCMaster passcodeDetails=lcmasterrepo.findSpecificTransactionById(transactionId);
			
			NimaiClient custDetails=lcmasterrepo.getCustomerDetais(passcodeDetails.getBranchUserEmail());
			//if branch userEmail consist parent user email or passcode userEmail
			if(custDetails==null  ) {
				schedulerEntityCust.setPasscodeuserEmail(passcodeDetails.getBranchUserEmail());
				
			}
			String custUserName=lcmasterrepo.getCustomerName(userId);
			String custEmailId=lcmasterrepo.getCustomerEmailId(userId);		
			schedulerEntityCust.setInsertedDate(insertedDate);
			schedulerEntityCust.setQuotationId(quoteId);
			schedulerEntityCust.setCustomerid(userId);
			schedulerEntityCust.setCustomerUserName(custUserName==null?"":custUserName);
			schedulerEntityCust.setCustomerEmail(custEmailId==null?"":custEmailId);
			schedulerEntityCust.setTransactionid(transactionId);
			schedulerEntityCust.setEmailEvent(custEmailEvent);
			if(nimaiCust.isEmpty())
			{
				System.out.println("No Banks Eligible");
				schedulerEntityCust.setTransactionEmailStatusToBanks("Pending");
			}
			
			schedulerEntityCust.setEmailFlag("Pending");
			userDao.save(schedulerEntityCust);
					
			
		} 
		catch (Exception e) 
		{
			System.out.println(""+e.getMessage());
		}
		finally 
		{
			entityManager.close();

		}
		}
	}

	@Override
	public List<CustomerTransactionBean> getTransactionForCustomerByUserIdAndStatus(String userId, String status, String branchEmailId) throws ParseException {
		// TODO Auto-generated method stub
		if(status.equalsIgnoreCase("Accepted"))
		{
			if(userId==null || userId=="")
			{
				System.out.println("UserId: "+userId);
				List<CustomerTransactionBean> details = lcmasterrepo.findTransactionForCustByUserIdAndAcceptedClosedStatusBranchEmailOnly(branchEmailId);
				List<CustomerTransactionBean> finalList=mapListToCustomerTransactionBean(details);
				return finalList;
			}
			if (userId.substring(0, 2).equalsIgnoreCase("BC") == true) {
				if (branchEmailId.equals(lcmasterrepo.getEmailAddress(userId))) {
					System.out.println("Bank as a customer");
					List<CustomerTransactionBean> details = lcmasterrepo.findTransactionForCustByUserIdAndAcceptedClosedStatus(userId);
					List<CustomerTransactionBean> finalList=mapListToCustomerTransactionBean(details);
					return finalList;
				} else {
					List<CustomerTransactionBean> details = lcmasterrepo.findTransactionForCustByUserIdAndAcceptedClosedStatusBranchEmail(userId, branchEmailId);
					List<CustomerTransactionBean> finalList=mapListToCustomerTransactionBean(details);
					return finalList;
					}
			} else {
				if(userId!=null || userId!="")
				{
					if(userId.substring(0, 2).equalsIgnoreCase("Al"))
					{
						System.out.println("Removing All from userid: "+userId.replaceFirst("All", ""));
						List<CustomerTransactionBean> details = lcmasterrepo.findTransactionForCustByUserIdSubsIdAndAcceptedClosedStatus(userId.replaceFirst("All", ""));
						List<CustomerTransactionBean> finalList=mapListToCustomerTransactionBean(details);
						return finalList;
					}
					else
					{
						List<CustomerTransactionBean> details = lcmasterrepo.findTransactionForCustByUserIdAndAcceptedClosedStatus(userId);
						List<CustomerTransactionBean> finalList=mapListToCustomerTransactionBean(details);
						return finalList;
					}
				}
				else
				{
					List<CustomerTransactionBean> details = lcmasterrepo.findTransactionForCustByUserIdAndAcceptedClosedStatusBranchEmailOnly(branchEmailId);
					List<CustomerTransactionBean> finalList=mapListToCustomerTransactionBean(details);
					return finalList;
				}
			}
		}
		else
		{
			if(userId==null || userId=="")
			{
				System.out.println("UserId: "+userId);
				List<CustomerTransactionBean> details = lcmasterrepo.findTransactionForCustByStatusBranchEmail(status,branchEmailId);
				List<CustomerTransactionBean> finalList=mapListToCustomerTransactionBean(details);
				return finalList;
			}
			if (userId.substring(0, 2).equalsIgnoreCase("BC") == true) {
				if (branchEmailId.equals(lcmasterrepo.getEmailAddress(userId)) || branchEmailId.equalsIgnoreCase("All")) {
					System.out.println("Bank as a customer");
					List<CustomerTransactionBean> details = lcmasterrepo.findTransactionForCustByUserIdAndStatus(userId, status);
					List<CustomerTransactionBean> finalList=mapListToCustomerTransactionBean(details);
					return finalList;
				} else {
					List<CustomerTransactionBean> details = lcmasterrepo.findTransactionForCustByUserIdStatusBranchEmail(userId, status, branchEmailId);
					List<CustomerTransactionBean> finalList=mapListToCustomerTransactionBean(details);
					return finalList;
				}
			} else {
				if(!userId.substring(0, 2).equalsIgnoreCase("Al"))
				{
					System.out.println("Removing All from userid: "+userId.replaceFirst("All", ""));
					List<CustomerTransactionBean> details = lcmasterrepo.findTransactionForCustByUserIdAndStatusExpAll(userId.replaceFirst("All", ""), status);
					List<CustomerTransactionBean> finalList=mapListToCustomerTransactionBean(details);
					return finalList;
				}
				if(userId.substring(0, 2).equalsIgnoreCase("Al"))
				{
					System.out.println("Removing All from userid: "+userId.replaceFirst("All", ""));
					List<CustomerTransactionBean> details = lcmasterrepo.findTransactionForCustByUserIdAndStatus(userId.replaceFirst("All", ""), status);
					List<CustomerTransactionBean> finalList=mapListToCustomerTransactionBean(details);
					return finalList;
				}
				try
				{
				List<NimaiClient> listOfSubsidiaries=lcmasterrepo.getSubsidiaryList(userId.replaceFirst("All", ""));
				List<String> subsidiaryList = new ArrayList<String>();
				System.out.println("List Of Subsidiaries: ");
				List<CustomerTransactionBean> details=null;
				List<CustomerTransactionBean> finalDetails=null;
				for(NimaiClient nc:listOfSubsidiaries)
				{
					String user=nc.getUserid();
					subsidiaryList.add(user);
				}
				details = lcmasterrepo.findTransactionForCustByUserIdListAndStatus(subsidiaryList, status);
				List<CustomerTransactionBean> finalList=mapListToCustomerTransactionBean(details);
				System.out.println(""+finalList);
				return finalList;
				}
				catch(Exception e)
				{
					System.out.println("No Subsdiary Selected");
					return null;
				}
				/*if(listOfSubsidiaries==null || listOfSubsidiaries.isEmpty())
				{
					details = lcmasterrepo.findTransactionForCustByUserIdAndStatus(userId, status);
					List<CustomerTransactionBean> finalList=mapListToCustomerTransactionBean(details);
					return finalList;
				}
				else
				{
					List<CustomerTransactionBean> finalList=null;
					
					for(CustomerTransactionBean ctb:finalDetails)
					{
						finalList=mapListToCustomerTransactionBean(details);
						
					}
					return finalList;
				}*/
				
			}
			
		}
		//return null;
	}
	
	@Override
	public int getSpecificDraftTransactionDetailForDuplicate(String userId,String transactionId) {
		// TODO Auto-generated method stub
		int conditionMatch;
		try
			{
			NimaiLC draftLC = lcrepo.findSpecificDraftTransaction(transactionId);
			String applicantName=draftLC.getApplicantName();
			Double lcValue=draftLC.getlCValue();
			String lcCurrency=draftLC.getlCCurrency();
			String issuanceBank=draftLC.getlCIssuanceBank();
			String confirmationPeriod=draftLC.getConfirmationPeriod();
			String goodsType=draftLC.getGoodsType();
			String requirementType=draftLC.getRequirementType();
			
			System.out.println(""+applicantName+lcValue+lcCurrency+issuanceBank+confirmationPeriod+goodsType+requirementType);
			conditionMatch=lcmasterrepo.getConditionValue(userId,applicantName,lcValue,lcCurrency,issuanceBank,confirmationPeriod,goodsType,requirementType);
			System.out.println("Condition Matches atleast: "+conditionMatch);
		}
		catch(Exception e)
		{
			conditionMatch=0;
			return 0;
		}
		return conditionMatch;
	}
	
	private List<CustomerTransactionBean> mapListToCustomerTransactionBean(List<CustomerTransactionBean> details) throws ParseException {
		// TODO Auto-generated method stub
		List<CustomerTransactionBean> list1 = new ArrayList<CustomerTransactionBean>();
		
		DateFormat simpleDateFormat=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		for(Object objA:details) 
		{
			
			CustomerTransactionBean responseBean=new CustomerTransactionBean();
			responseBean.setTransactionId(((Object[])objA)[0]==null?"null":((Object[])objA)[0].toString());
			responseBean.setUserId(((Object[])objA)[1]==null?"null":((Object[])objA)[1].toString());
			responseBean.setRequirementType(((Object[])objA)[2]==null?"null":((Object[])objA)[2].toString());
			responseBean.setlCIssuanceBank(((Object[])objA)[3]==null?"null":((Object[])objA)[3].toString());
			
			responseBean.setlCValue(((Object[])objA)[4]==null?0:Double.valueOf(((Object[])objA)[4].toString()));
			responseBean.setGoodsType(((Object[])objA)[5]==null?"null":((Object[])objA)[5].toString());
			responseBean.setApplicantName(((Object[])objA)[6]==null?"null":((Object[])objA)[6].toString());
			responseBean.setBeneName(((Object[])objA)[7]==null?"null":((Object[])objA)[7].toString());
			responseBean.setQuotationReceived(((Object[])objA)[8]==null?0:Integer.valueOf(((Object[])objA)[8].toString()));
			responseBean.setInsertedDate(((Object[])objA)[9]==null?new Date(0):(Date)simpleDateFormat.parse(((Object[])objA)[9].toString()));
			responseBean.setValidity(((Object[])objA)[10]==null?new Date(0):(Date)simpleDateFormat.parse(((Object[])objA)[10].toString()));
			responseBean.setAcceptedOn(((Object[])objA)[11]==null?new Date(0):(Date)simpleDateFormat.parse(((Object[])objA)[11].toString()));
			responseBean.setTransactionStatus(((Object[])objA)[12]==null?"null":((Object[])objA)[12].toString());
			responseBean.setRejectedOn(((Object[])objA)[13]==null?new Date(0):(Date)simpleDateFormat.parse(((Object[])objA)[13].toString()));
			responseBean.setlCCurrency(((Object[])objA)[14]==null?"null":((Object[])objA)[14].toString());
			responseBean.setStatusReason(((Object[])objA)[15]==null?"null":((Object[])objA)[15].toString());
			
			list1.add(responseBean);
		}
		return list1;
	}

	@Override
	public void updateQuotationReceivedForValidityDateExp(String userId) {
		// TODO Auto-generated method stub
		lcmasterrepo.updateQuotationReceivedCountForQuoteExpValidity(userId);
	}


	@Override
	public String getLCIssuingCountryByTransId(String transId) {
		// TODO Auto-generated method stub
		return lcmasterrepo.getIssuingCountry(transId);
	}
	
	@Override
	public String getLCCurrencyByTransId(String transId) {
		// TODO Auto-generated method stub
		return lcmasterrepo.getCurrency(transId);
	}
	
	@Override
	public Integer getLCTenorDays(String transId) {
		// TODO Auto-generated method stub
		Integer tenor=0;
		try
		{
			String productType=lcmasterrepo.getProductTypeByTransId(transId);
			System.out.println("Product Type: "+productType);
			switch(productType)
			{
			case "Confirmation":
				tenor=Integer.valueOf(lcmasterrepo.getConfirmationPeriod(transId));
				break;
			case "ConfirmAndDiscount":
				tenor=Integer.valueOf(lcmasterrepo.getConfirmationPeriod(transId));
				break;
			case "Discounting":
				tenor=Integer.valueOf(lcmasterrepo.getDiscountingPeriod(transId));
				break;
			case "Banker":
				tenor=Integer.valueOf(lcmasterrepo.getDiscountingPeriod(transId));
				break;
			case "Refinance":
				tenor=Integer.valueOf(lcmasterrepo.getRefinancingPeriod(transId));
				break;
			}
		}
		catch(Exception e)
		{
			System.out.println("Exception while getting tenor days: "+e);
			tenor=0;
		}
		return tenor;
	}
	
	@Override
	public Double getLCValue(String transId) {
		// TODO Auto-generated method stub
		Double lcValue=0.0;
		try
		{
			lcValue=lcmasterrepo.getLCValueByTransId(transId);
		}
		catch(Exception e)
		{
			System.out.println("Exception while getting lcValue: "+e);
			lcValue=0.0;
		}
		return lcValue;
	}

	@Override
	public Double getAvgAmountForCountryFromAdmin(String lcCountry,String lcCurrency) {
		// TODO Auto-generated method stub
		return lcmasterrepo.getAvgAmouunt(lcCountry,lcCurrency);
	}
	
	@Override
	public Double getAnnualAssetValue(String lcCountry,String lcCurrency) {
		// TODO Auto-generated method stub
		return lcmasterrepo.getAnnualAsset(lcCountry,lcCurrency);
	}
	
	@Override
	public Double getNetRevenueLC(String lcCountry,String lcCurrency) {
		// TODO Auto-generated method stub
		return lcmasterrepo.getNetRevenue(lcCountry,lcCurrency);
	}
	
	/*@Override
	public Double getAvgSpreadForCountry(String lcCountry,String lcCurrency) {
		// TODO Auto-generated method stub
		return lcmasterrepo.getAvgSpread(lcCountry,lcCurrency);
	}*/

	@Override
	public List<NimaiLCPort> getPortListByCountry(String countryName) {
		// TODO Auto-generated method stub
		
		return lcportrepo.getPort(countryName);
	}

	@Override
	public NimaiLCMaster getAcceptedorExpiredTransaction(String transactionId, String userId) {
		// TODO Auto-generated method stub
		return lcmasterrepo.getAcceptedORExpiredTrans(transactionId,userId);
	}

	@Override
	public void updateTransactionForCancel(String transactionId, String userId) {
		// TODO Auto-generated method stub
		lcmasterrepo.updateTransactionStatusToCancel(transactionId, userId);
		quotemasterrepo.updateQuotationStatusForCancelToExpired(transactionId, userId);
	}

	@Override
	public List<NimaiCustomerBean> getCreditTxnForCustomerByUserId(String userId) throws ParseException {
		// TODO Auto-generated method stub
		List<NimaiClient> custData=null;
		if(userId.substring(0, 3).equalsIgnoreCase("All"))
			custData=customerRepo.findCreditTransactionByUserId(userId.replaceFirst("All", ""));
		else
			custData=customerRepo.findCreditTransactionByOnlyUserId(userId);
		List<NimaiCustomerBean> custTxnList = new ArrayList<>();
		List<NimaiCustomerBean> custFinalList = new ArrayList<>();
		ModelMapperUtil modelMapper = new ModelMapperUtil();
		Double avgAmount;
		DateFormat simpleDateFormat=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		int i=0;
		String companyName=customerRepo.findCompanyNameByUserId(userId);
		for(NimaiClient nc:custData)
			{
			List nlm=lcmasterrepo.findTransactionByUserId(nc.getUserid());
			/*if(nlm.isEmpty())
			{
				NimaiCustomerBean ncb=modelMapper.map(nc, NimaiCustomerBean.class);
				ncb.setCompanyName(companyName);
				custFinalList.add(ncb);
			}*/
				for(Object txnDet:nlm)
				{
				NimaiCustomerBean ncb=modelMapper.map(nc, NimaiCustomerBean.class);
				//ncb.setCompanyName(companyName);
				ncb.setTransactionId(((Object[])txnDet)[0]==null?null:((Object[])txnDet)[0].toString());
				ncb.setTransactionType(((Object[])txnDet)[1]==null?null:((Object[])txnDet)[1].toString());
				ncb.setCcy(((Object[])txnDet)[2]==null?null:((Object[])txnDet)[2].toString());
				ncb.setTxnInsertedDate(((Object[])txnDet)[3]==null?new Date(0):(Date)simpleDateFormat.parse(((Object[])txnDet)[3].toString()));
				ncb.setTxnDate(((Object[])txnDet)[4]==null?new Date(0):(Date)simpleDateFormat.parse(((Object[])txnDet)[4].toString()));
				ncb.setTransactionStatus(((Object[])txnDet)[5]==null?null:((Object[])txnDet)[5].toString());
				String lcCountry=((Object[])txnDet)[6]==null?null:((Object[])txnDet)[6].toString();
				ncb.setEmailAddress(((Object[])txnDet)[7]==null?null:((Object[])txnDet)[7].toString());
				
				if(ncb.getTransactionStatus().equalsIgnoreCase("Active") || ncb.getTransactionStatus().equalsIgnoreCase("Closed") || ncb.getTransactionStatus().equalsIgnoreCase("Accepted") || ncb.getTransactionStatus().equalsIgnoreCase("Expired"))
				{
					ncb.setCreditUsed(1);
				}
				if(ncb.getTransactionStatus().equalsIgnoreCase("Rejected"))
				{
					String rejectionCount="";
					try
					{
						rejectionCount=lcmasterrepo.findRejectionCount(ncb.getTransactionId());
						if(Integer.valueOf(rejectionCount)<=3)
						{
							ncb.setCreditUsed(0);
						}
						else
						{
							ncb.setCreditUsed(1);
						}
					}
					catch(Exception e)
					{
						ncb.setCreditUsed(0);
					}
					
				}
				
				System.out.println("Getting quote value and saving for: "+ncb.getUserid()+" "+ncb.getTransactionId());
				try
				{
					Double saving=trSavingRepo.getSavingsByTransId(ncb.getTransactionId());
					System.out.println("Saving: "+saving);
					//float quoteValue=quotationService.getQuoteValueByQid(quotationId);
					ncb.setSavings(saving);
				}
				catch(Exception e)
				{
					ncb.setSavings(0.0);
				}
				custFinalList.add(ncb);
				}
			//NimaiCustomerBean ncb=modelMapper.map(nc, NimaiCustomerBean.class);
			/*ncb.setTxnDate(nlm.getModifiedDate());
			ncb.setTransactionId(nlm.getTransactionId());
			ncb.setTransactionType(nlm.getRequirementType());
			ncb.setTransactionStatus(nlm.getTransactionStatus());
			ncb.setCcy(nlm.getlCCurrency());
			*/
		}
		return custFinalList;
	}

	@Override
	public Date getValidityDate(String transId, String userId) throws ParseException {
		// TODO Auto-generated method stub
		Date vd= new Date();//lcrepo.getValidityDateByTransIdUserId(transId,userId);
		NimaiLC draftDet=lcrepo.findByTransactionIdUserId(transId,userId);
		System.out.println("Validity Date: "+draftDet.getValidity());
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");   
		return draftDet.getValidity();
	}

	@Override
	public List<NimaiCustomerBean> getCreditTxnForCustomerByUserId(String userId, Date fromDate) throws ParseException {
		// TODO Auto-generated method stub
		List<NimaiClient> custData=null;
		if(userId.substring(0, 3).equalsIgnoreCase("All"))
			custData=customerRepo.findCreditTransactionByUserId(userId.replaceFirst("All", ""));
		else
			custData=customerRepo.findCreditTransactionByOnlyUserId(userId);
		List<NimaiCustomerBean> custFinalList = new ArrayList<>();
		ModelMapperUtil modelMapper = new ModelMapperUtil();
		Double avgAmount;
		DateFormat simpleDateFormat=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		int i=0;
		String companyName=customerRepo.findCompanyNameByUserId(userId);
		for(NimaiClient nc:custData)
			{
			List nlm=lcmasterrepo.findTransactionByUserIdStartDate(nc.getUserid(),fromDate);
			/*if(nlm.isEmpty())
			{
				NimaiCustomerBean ncb=modelMapper.map(nc, NimaiCustomerBean.class);
				custFinalList.add(ncb);
			}*/
				for(Object txnDet:nlm)
				{
				NimaiCustomerBean ncb=modelMapper.map(nc, NimaiCustomerBean.class);
				//ncb.setCompanyName(companyName);
				ncb.setTransactionId(((Object[])txnDet)[0]==null?null:((Object[])txnDet)[0].toString());
				ncb.setTransactionType(((Object[])txnDet)[1]==null?null:((Object[])txnDet)[1].toString());
				ncb.setCcy(((Object[])txnDet)[2]==null?null:((Object[])txnDet)[2].toString());
				ncb.setTxnInsertedDate(((Object[])txnDet)[3]==null?new Date(0):(Date)simpleDateFormat.parse(((Object[])txnDet)[3].toString()));
				ncb.setTxnDate(((Object[])txnDet)[4]==null?new Date(0):(Date)simpleDateFormat.parse(((Object[])txnDet)[4].toString()));
				ncb.setTransactionStatus(((Object[])txnDet)[5]==null?null:((Object[])txnDet)[5].toString());
				String lcCountry=((Object[])txnDet)[6]==null?null:((Object[])txnDet)[6].toString();
				ncb.setEmailAddress(((Object[])txnDet)[7]==null?null:((Object[])txnDet)[7].toString());
				
				if(ncb.getTransactionStatus().equalsIgnoreCase("Active") || ncb.getTransactionStatus().equalsIgnoreCase("Closed") || ncb.getTransactionStatus().equalsIgnoreCase("Accepted") || ncb.getTransactionStatus().equalsIgnoreCase("Expired"))
				{
					ncb.setCreditUsed(1);
				}
				if(ncb.getTransactionStatus().equalsIgnoreCase("Rejected") )
				{
					String rejectionCount="";
					try
					{
						rejectionCount=lcmasterrepo.findRejectionCount(ncb.getTransactionId());
						if(Integer.valueOf(rejectionCount)<=3)
						{
							ncb.setCreditUsed(0);
						}
						else
						{
							ncb.setCreditUsed(1);
						}
					}
					catch(Exception e)
					{
						ncb.setCreditUsed(0);
					}
				}
				try
				{
					Double saving=trSavingRepo.getSavingsByTransId(ncb.getTransactionId());
					System.out.println("Saving: "+saving);
					//float quoteValue=quotationService.getQuoteValueByQid(quotationId);
					ncb.setSavings(saving);
				}
				catch(Exception e)
				{
					ncb.setSavings(0.0);
				}
				custFinalList.add(ncb);
				}
			//NimaiCustomerBean ncb=modelMapper.map(nc, NimaiCustomerBean.class);
			/*ncb.setTxnDate(nlm.getModifiedDate());
			ncb.setTransactionId(nlm.getTransactionId());
			ncb.setTransactionType(nlm.getRequirementType());
			ncb.setTransactionStatus(nlm.getTransactionStatus());
			ncb.setCcy(nlm.getlCCurrency());
			*/
		}
		return custFinalList;
	}

	@Override
	public List<NimaiCustomerBean> getCreditTxnForCustomerByUserId(String userId, Date fromDate, String subsidiaryName) throws ParseException {
		// TODO Auto-generated method stub
		List<NimaiClient> custData;
		if(subsidiaryName.equalsIgnoreCase("All"))
			custData=customerRepo.findCreditTransactionByUserId(userId);
		else
			custData=customerRepo.findCreditTransactionByUserIdSubsidiary(userId,subsidiaryName);
		
		List<NimaiCustomerBean> custFinalList = new ArrayList<>();
		ModelMapperUtil modelMapper = new ModelMapperUtil();
		Double avgAmount;
		DateFormat simpleDateFormat=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		int i=0;
		String companyName=customerRepo.findCompanyNameByUserId(userId);
		for(NimaiClient nc:custData)
			{
			List nlm=lcmasterrepo.findTransactionByUserIdStartDate(nc.getUserid(),fromDate);
			
				for(Object txnDet:nlm)
				{
				NimaiCustomerBean ncb=modelMapper.map(nc, NimaiCustomerBean.class);
				ncb.setCompanyName(companyName);
				ncb.setTransactionId(((Object[])txnDet)[0]==null?null:((Object[])txnDet)[0].toString());
				ncb.setTransactionType(((Object[])txnDet)[1]==null?null:((Object[])txnDet)[1].toString());
				ncb.setCcy(((Object[])txnDet)[2]==null?null:((Object[])txnDet)[2].toString());
				ncb.setTxnInsertedDate(((Object[])txnDet)[3]==null?new Date(0):(Date)simpleDateFormat.parse(((Object[])txnDet)[3].toString()));
				ncb.setTxnDate(((Object[])txnDet)[4]==null?new Date(0):(Date)simpleDateFormat.parse(((Object[])txnDet)[4].toString()));
				ncb.setTransactionStatus(((Object[])txnDet)[5]==null?null:((Object[])txnDet)[5].toString());
				String lcCountry=((Object[])txnDet)[6]==null?null:((Object[])txnDet)[6].toString();
				ncb.setEmailAddress(((Object[])txnDet)[7]==null?null:((Object[])txnDet)[7].toString());
				
				
				if(ncb.getTransactionStatus().equalsIgnoreCase("Active") || ncb.getTransactionStatus().equalsIgnoreCase("Closed") || ncb.getTransactionStatus().equalsIgnoreCase("Accepted") || ncb.getTransactionStatus().equalsIgnoreCase("Expired"))
				{
					ncb.setCreditUsed(1);
				}
				if(ncb.getTransactionStatus().equalsIgnoreCase("Rejected") )
				{
					String rejectionCount="";
					try
					{
						rejectionCount=lcmasterrepo.findRejectionCount(ncb.getTransactionId());
						if(Integer.valueOf(rejectionCount)<=3)
						{
							ncb.setCreditUsed(0);
						}
						else
						{
							ncb.setCreditUsed(1);
						}
					}
					catch(Exception e)
					{
						ncb.setCreditUsed(0);
					}
				}
				try
				{
					Double saving=trSavingRepo.getSavingsByTransId(ncb.getTransactionId());
					System.out.println("Saving: "+saving);
					//float quoteValue=quotationService.getQuoteValueByQid(quotationId);
					ncb.setSavings(saving);
				}
				catch(Exception e)
				{
					ncb.setSavings(0.0);
				}
				custFinalList.add(ncb);
				}
			//NimaiCustomerBean ncb=modelMapper.map(nc, NimaiCustomerBean.class);
			/*ncb.setTxnDate(nlm.getModifiedDate());
			ncb.setTransactionId(nlm.getTransactionId());
			ncb.setTransactionType(nlm.getRequirementType());
			ncb.setTransactionStatus(nlm.getTransactionStatus());
			ncb.setCcy(nlm.getlCCurrency());
			*/
		}
		return custFinalList;
	}

	
	
	@Override
	public List<NimaiCustomerBean> getCreditTxnForCustomerByUserId(String userId, Date fromDate, Date toDate) throws ParseException {
		// TODO Auto-generated method stub
		List<NimaiClient> custData=null;
		if(userId.substring(0, 3).equalsIgnoreCase("All"))
			custData=customerRepo.findCreditTransactionByUserId(userId.replaceFirst("All", ""));
		else
			custData=customerRepo.findCreditTransactionByOnlyUserId(userId);
		List<NimaiCustomerBean> custFinalList = new ArrayList<>();
		ModelMapperUtil modelMapper = new ModelMapperUtil();
		Double avgAmount;
		DateFormat simpleDateFormat=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		int i=0;
		String companyName=customerRepo.findCompanyNameByUserId(userId);
		for(NimaiClient nc:custData)
			{
			List nlm=lcmasterrepo.findTransactionByUserIdStartDateEndDate(nc.getUserid(),fromDate,toDate);
			/*if(nlm.isEmpty())
			{
				NimaiCustomerBean ncb=modelMapper.map(nc, NimaiCustomerBean.class);
				custFinalList.add(ncb);
			}*/	
			for(Object txnDet:nlm)
				{
				NimaiCustomerBean ncb=modelMapper.map(nc, NimaiCustomerBean.class);
				//ncb.setCompanyName(companyName);
				ncb.setTransactionId(((Object[])txnDet)[0]==null?null:((Object[])txnDet)[0].toString());
				ncb.setTransactionType(((Object[])txnDet)[1]==null?null:((Object[])txnDet)[1].toString());
				ncb.setCcy(((Object[])txnDet)[2]==null?null:((Object[])txnDet)[2].toString());
				ncb.setTxnInsertedDate(((Object[])txnDet)[3]==null?new Date(0):(Date)simpleDateFormat.parse(((Object[])txnDet)[3].toString()));
				ncb.setTxnDate(((Object[])txnDet)[4]==null?new Date(0):(Date)simpleDateFormat.parse(((Object[])txnDet)[4].toString()));
				ncb.setTransactionStatus(((Object[])txnDet)[5]==null?null:((Object[])txnDet)[5].toString());
				String lcCountry=((Object[])txnDet)[6]==null?null:((Object[])txnDet)[6].toString();
				ncb.setEmailAddress(((Object[])txnDet)[7]==null?null:((Object[])txnDet)[7].toString());
				
				if(ncb.getTransactionStatus().equalsIgnoreCase("Active") || ncb.getTransactionStatus().equalsIgnoreCase("Closed") || ncb.getTransactionStatus().equalsIgnoreCase("Accepted") || ncb.getTransactionStatus().equalsIgnoreCase("Expired"))
				{
					ncb.setCreditUsed(1);
				}
				if(ncb.getTransactionStatus().equalsIgnoreCase("Rejected") )
				{
					String rejectionCount="";
					try
					{
						rejectionCount=lcmasterrepo.findRejectionCount(ncb.getTransactionId());
						if(Integer.valueOf(rejectionCount)<=3)
						{
							ncb.setCreditUsed(0);
						}
						else
						{
							ncb.setCreditUsed(1);
						}
					}
					catch(Exception e)
					{
						ncb.setCreditUsed(0);
					}
				}
				try
				{
					Double saving=trSavingRepo.getSavingsByTransId(ncb.getTransactionId());
					System.out.println("Saving: "+saving);
					//float quoteValue=quotationService.getQuoteValueByQid(quotationId);
					ncb.setSavings(saving);
				}
				catch(Exception e)
				{
					ncb.setSavings(0.0);
				}
				custFinalList.add(ncb);
				}
			//NimaiCustomerBean ncb=modelMapper.map(nc, NimaiCustomerBean.class);
			/*ncb.setTxnDate(nlm.getModifiedDate());
			ncb.setTransactionId(nlm.getTransactionId());
			ncb.setTransactionType(nlm.getRequirementType());
			ncb.setTransactionStatus(nlm.getTransactionStatus());
			ncb.setCcy(nlm.getlCCurrency());
			*/
		}
		return custFinalList;
	}
	
	

	@Override
	public List<NimaiCustomerBean> getCreditTxnForCustomerByBankUserId(String userId) throws ParseException {
		// TODO Auto-generated method stub
		List<NimaiClient> custData=null;
		if(userId.substring(0, 3).equalsIgnoreCase("All"))
			custData=customerRepo.findCreditTransactionByBankUserId(userId.replaceFirst("All", ""));
		else
			custData=customerRepo.findCreditTransactionByOnlyUserId(userId);
		//List<NimaiClient> custData=customerRepo.findCreditTransactionByBankUserId(userId);
		List<NimaiCustomerBean> custFinalList = new ArrayList<>();
		ModelMapperUtil modelMapper = new ModelMapperUtil();
		Double avgAmount;
		DateFormat simpleDateFormat=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		int i=0;
		String companyName=customerRepo.findCompanyNameByUserId(userId);
		for(NimaiClient nc:custData)
		{
			List nlm=lcmasterrepo.findQuotationByBankUserId(nc.getUserid());
			for(Object txnDet:nlm)
			{
			NimaiCustomerBean ncb=modelMapper.map(nc, NimaiCustomerBean.class);
			//ncb.setCompanyName(companyName);
			ncb.setTransactionId(((Object[])txnDet)[0]==null?null:((Object[])txnDet)[0].toString());
			ncb.setTransactionType(lcmasterrepo.getProductTypeByTransId(ncb.getTransactionId()));
			ncb.setCcy(lcmasterrepo.getCurrency(ncb.getTransactionId()));
			ncb.setTxnInsertedDate(((Object[])txnDet)[1]==null?new Date(0):(Date)simpleDateFormat.parse(((Object[])txnDet)[1].toString()));
			ncb.setTxnDate(((Object[])txnDet)[2]==null?new Date(0):(Date)simpleDateFormat.parse(((Object[])txnDet)[2].toString()));
		
			if(((Object[])txnDet)[3].toString().equalsIgnoreCase("Placed") || ((Object[])txnDet)[3].toString().equalsIgnoreCase("RePlaced") || ((Object[])txnDet)[3].toString().equalsIgnoreCase("ExpPlaced") || ((Object[])txnDet)[3].toString().equalsIgnoreCase("FreezePlaced"))
				ncb.setTransactionStatus("Placed");
			else
				ncb.setTransactionStatus(((Object[])txnDet)[3]==null?null:((Object[])txnDet)[3].toString());
			//String lcCountry=((Object[])txnDet)[6]==null?null:((Object[])txnDet)[6].toString();
			if(ncb.getTransactionStatus().equalsIgnoreCase("Placed") || ncb.getTransactionStatus().equalsIgnoreCase("Closed") || ncb.getTransactionStatus().equalsIgnoreCase("RePlaced") || ncb.getTransactionStatus().equalsIgnoreCase("ExpPlaced") || ncb.getTransactionStatus().equalsIgnoreCase("Accepted") || ncb.getTransactionStatus().equalsIgnoreCase("Expired"))
			{
				ncb.setCreditUsed(1);
			}
			if(ncb.getTransactionStatus().equalsIgnoreCase("Rejected") || ncb.getTransactionStatus().equalsIgnoreCase("Withdrawn"))
			{
				ncb.setCreditUsed(0);
			}
			
			System.out.println("Getting quote value and saving for: "+ncb.getUserid()+" "+ncb.getTransactionId());
			try
			{
				Double saving=trSavingRepo.getSavingsByTransId(ncb.getTransactionId());
				System.out.println("Saving: "+saving);
				//float quoteValue=quotationService.getQuoteValueByQid(quotationId);
				ncb.setSavings(saving);
			}
			catch(Exception e)
			{
				ncb.setSavings(0.0);
			}
			custFinalList.add(ncb);
			}
			//NimaiCustomerBean ncb=modelMapper.map(nc, NimaiCustomerBean.class);
			/*ncb.setTxnDate(nlm.getModifiedDate());
			ncb.setTransactionId(nlm.getTransactionId());
			ncb.setTransactionType(nlm.getRequirementType());
			ncb.setTransactionStatus(nlm.getTransactionStatus());
			ncb.setCcy(nlm.getlCCurrency());
			*/
		}
			
		return custFinalList;
	}

	@Override
	public List<NimaiCustomerBean> getCreditTxnForCustomerByBankUserId(String userId, Date fromDate) throws ParseException {
		// TODO Auto-generated method stub
		List<NimaiClient> custData=customerRepo.findCreditTransactionByBankUserId(userId);
		List<NimaiCustomerBean> custFinalList = new ArrayList<>();
		ModelMapperUtil modelMapper = new ModelMapperUtil();
		Double avgAmount;
		DateFormat simpleDateFormat=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		int i=0;
		String companyName=customerRepo.findCompanyNameByUserId(userId);
		for(NimaiClient nc:custData)
		{
			List nlm=lcmasterrepo.findQuotationByBankUserIdStartDate(nc.getUserid(),fromDate);
			for(Object txnDet:nlm)
			{
			NimaiCustomerBean ncb=modelMapper.map(nc, NimaiCustomerBean.class);
			//ncb.setCompanyName(companyName);
			ncb.setTransactionId(((Object[])txnDet)[0]==null?null:((Object[])txnDet)[0].toString());
			ncb.setTransactionType(lcmasterrepo.getProductTypeByTransId(ncb.getTransactionId()));
			ncb.setCcy(lcmasterrepo.getCurrency(ncb.getTransactionId()));
			ncb.setTxnInsertedDate(((Object[])txnDet)[1]==null?new Date(0):(Date)simpleDateFormat.parse(((Object[])txnDet)[1].toString()));
			ncb.setTxnDate(((Object[])txnDet)[2]==null?new Date(0):(Date)simpleDateFormat.parse(((Object[])txnDet)[2].toString()));
			if(((Object[])txnDet)[3].toString().equalsIgnoreCase("Placed") || ((Object[])txnDet)[3].toString().equalsIgnoreCase("RePlaced") || ((Object[])txnDet)[3].toString().equalsIgnoreCase("ExpPlaced") || ((Object[])txnDet)[3].toString().equalsIgnoreCase("FreezePlaced"))
				ncb.setTransactionStatus("Placed");
			else
				ncb.setTransactionStatus(((Object[])txnDet)[3]==null?null:((Object[])txnDet)[3].toString());
			//String lcCountry=((Object[])txnDet)[6]==null?null:((Object[])txnDet)[6].toString();
			if(ncb.getTransactionStatus().equalsIgnoreCase("Placed") || ncb.getTransactionStatus().equalsIgnoreCase("Closed") || ncb.getTransactionStatus().equalsIgnoreCase("RePlaced") || ncb.getTransactionStatus().equalsIgnoreCase("ExpPlaced") || ncb.getTransactionStatus().equalsIgnoreCase("Accepted") || ncb.getTransactionStatus().equalsIgnoreCase("Expired"))
			{
				ncb.setCreditUsed(1);
			}
			if(ncb.getTransactionStatus().equalsIgnoreCase("Rejected") )
			{
				ncb.setCreditUsed(0);
			}
			
			System.out.println("Getting quote value and saving for: "+ncb.getUserid()+" "+ncb.getTransactionId());
			try
			{
				Double saving=trSavingRepo.getSavingsByTransId(ncb.getTransactionId());
				System.out.println("Saving: "+saving);
				//float quoteValue=quotationService.getQuoteValueByQid(quotationId);
				ncb.setSavings(saving);
			}
			catch(Exception e)
			{
				ncb.setSavings(0.0);
			}
			custFinalList.add(ncb);
			}
			//NimaiCustomerBean ncb=modelMapper.map(nc, NimaiCustomerBean.class);
			/*ncb.setTxnDate(nlm.getModifiedDate());
			ncb.setTransactionId(nlm.getTransactionId());
			ncb.setTransactionType(nlm.getRequirementType());
			ncb.setTransactionStatus(nlm.getTransactionStatus());
			ncb.setCcy(nlm.getlCCurrency());
			*/
		}
			
		return custFinalList;
	}

	@Override
	public List<NimaiCustomerBean> getCreditTxnForCustomerByBankUserId(String userId, Date fromDate, Date toDate) throws ParseException {
		// TODO Auto-generated method stub
		List<NimaiClient> custData=customerRepo.findCreditTransactionByBankUserId(userId);
		List<NimaiCustomerBean> custFinalList = new ArrayList<>();
		ModelMapperUtil modelMapper = new ModelMapperUtil();
		Double avgAmount;
		DateFormat simpleDateFormat=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		int i=0;
		String companyName=customerRepo.findCompanyNameByUserId(userId);
		for(NimaiClient nc:custData)
		{
			List nlm=lcmasterrepo.findQuotationByBankUserIdStartDateEndDate(nc.getUserid(),fromDate,toDate);
			for(Object txnDet:nlm)
			{
			NimaiCustomerBean ncb=modelMapper.map(nc, NimaiCustomerBean.class);
			//ncb.setCompanyName(companyName);
			ncb.setTransactionId(((Object[])txnDet)[0]==null?null:((Object[])txnDet)[0].toString());
			ncb.setTransactionType(lcmasterrepo.getProductTypeByTransId(ncb.getTransactionId()));
			ncb.setCcy(lcmasterrepo.getCurrency(ncb.getTransactionId()));
			ncb.setTxnInsertedDate(((Object[])txnDet)[1]==null?new Date(0):(Date)simpleDateFormat.parse(((Object[])txnDet)[1].toString()));
			ncb.setTxnDate(((Object[])txnDet)[2]==null?new Date(0):(Date)simpleDateFormat.parse(((Object[])txnDet)[2].toString()));
			
			if(((Object[])txnDet)[3].toString().equalsIgnoreCase("Placed") || ((Object[])txnDet)[3].toString().equalsIgnoreCase("RePlaced") || ((Object[])txnDet)[3].toString().equalsIgnoreCase("ExpPlaced") || ((Object[])txnDet)[3].toString().equalsIgnoreCase("FreezePlaced"))
				ncb.setTransactionStatus("Placed");
			else
				ncb.setTransactionStatus(((Object[])txnDet)[3]==null?null:((Object[])txnDet)[3].toString());
			//String lcCountry=((Object[])txnDet)[6]==null?null:((Object[])txnDet)[6].toString();
			if(ncb.getTransactionStatus().equalsIgnoreCase("Placed") || ncb.getTransactionStatus().equalsIgnoreCase("Closed") || ncb.getTransactionStatus().equalsIgnoreCase("RePlaced") || ncb.getTransactionStatus().equalsIgnoreCase("ExpPlaced") || ncb.getTransactionStatus().equalsIgnoreCase("Accepted") || ncb.getTransactionStatus().equalsIgnoreCase("Expired"))
			{
				ncb.setCreditUsed(1);
			}
			if(ncb.getTransactionStatus().equalsIgnoreCase("Rejected") )
			{
				ncb.setCreditUsed(0);
			}
			
			System.out.println("Getting quote value and saving for: "+ncb.getUserid()+" "+ncb.getTransactionId());
			try
			{
				Double saving=trSavingRepo.getSavingsByTransId(ncb.getTransactionId());
				System.out.println("Saving: "+saving);
				//float quoteValue=quotationService.getQuoteValueByQid(quotationId);
				ncb.setSavings(saving);
			}
			catch(Exception e)
			{
				ncb.setSavings(0.0);
			}
			custFinalList.add(ncb);
			}
			//NimaiCustomerBean ncb=modelMapper.map(nc, NimaiCustomerBean.class);
			/*ncb.setTxnDate(nlm.getModifiedDate());
			ncb.setTransactionId(nlm.getTransactionId());
			ncb.setTransactionType(nlm.getRequirementType());
			ncb.setTransactionStatus(nlm.getTransactionStatus());
			ncb.setCcy(nlm.getlCCurrency());
			*/
		}
			
		return custFinalList;
	}
	
	@Override
	public List<Goods> getGoods() {
		// TODO Auto-generated method stub
		List<Goods> list=null;
		try
		{
			list = (List<Goods>) goodsRepo.findAll();
			//return list;
		}
		catch(Exception e)
		{
			System.out.println(e);
		}
		return list;
	}

	@Override
	public String getAccountType(String userid) {
		// TODO Auto-generated method stub
		return lcmasterrepo.getAccountTypeByUserId(userid);
	}

	@Override
	public String getAccountSource(String userid) {
		// TODO Auto-generated method stub
		return lcmasterrepo.getAccountSourceByUserId(userid);
	}

	@Override
	public List<NimaiCustomerBean> getCreditTxnForCustomerByUserId(String userId, String subsidiaryName) throws ParseException {
		// TODO Auto-generated method stub
		List<NimaiClient> custData;
		if(subsidiaryName.equalsIgnoreCase("All"))
			custData=customerRepo.findCreditTransactionByUserId(userId);
		else
			custData=customerRepo.findCreditTransactionByUserIdSubsidiary(userId,subsidiaryName);
		List<NimaiCustomerBean> custFinalList = new ArrayList<>();
		ModelMapperUtil modelMapper = new ModelMapperUtil();
		Double avgAmount;
		DateFormat simpleDateFormat=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		int i=0;
		String companyName=customerRepo.findCompanyNameByUserId(userId);
		for(NimaiClient nc:custData)
			{
			List nlm=lcmasterrepo.findTransactionByUserId(nc.getUserid());
				for(Object txnDet:nlm)
				{
				NimaiCustomerBean ncb=modelMapper.map(nc, NimaiCustomerBean.class);
				ncb.setCompanyName(companyName);
				ncb.setTransactionId(((Object[])txnDet)[0]==null?null:((Object[])txnDet)[0].toString());
				ncb.setTransactionType(((Object[])txnDet)[1]==null?null:((Object[])txnDet)[1].toString());
				ncb.setCcy(((Object[])txnDet)[2]==null?null:((Object[])txnDet)[2].toString());
				ncb.setTxnInsertedDate(((Object[])txnDet)[3]==null?new Date(0):(Date)simpleDateFormat.parse(((Object[])txnDet)[3].toString()));
				ncb.setTxnDate(((Object[])txnDet)[4]==null?new Date(0):(Date)simpleDateFormat.parse(((Object[])txnDet)[4].toString()));
				ncb.setTransactionStatus(((Object[])txnDet)[5]==null?null:((Object[])txnDet)[5].toString());
				String lcCountry=((Object[])txnDet)[6]==null?null:((Object[])txnDet)[6].toString();
				if(ncb.getTransactionStatus().equalsIgnoreCase("Active") || ncb.getTransactionStatus().equalsIgnoreCase("Closed") || ncb.getTransactionStatus().equalsIgnoreCase("Accepted") || ncb.getTransactionStatus().equalsIgnoreCase("Expired"))
				{
					ncb.setCreditUsed(1);
				}
				if(ncb.getTransactionStatus().equalsIgnoreCase("Rejected") )
				{
					String rejectionCount="";
					try
					{
						rejectionCount=lcmasterrepo.findRejectionCount(ncb.getTransactionId());
						if(Integer.valueOf(rejectionCount)<=3)
						{
							ncb.setCreditUsed(0);
						}
						else
						{
							ncb.setCreditUsed(1);
						}
					}
					catch(Exception e)
					{
						ncb.setCreditUsed(0);
					}				}
				try
				{
					Double saving=trSavingRepo.getSavingsByTransId(ncb.getTransactionId());
					System.out.println("Saving: "+saving);
					//float quoteValue=quotationService.getQuoteValueByQid(quotationId);
					ncb.setSavings(saving);
				}
				catch(Exception e)
				{
					ncb.setSavings(0.0);
				}
				custFinalList.add(ncb);
				}
			//NimaiCustomerBean ncb=modelMapper.map(nc, NimaiCustomerBean.class);
			/*ncb.setTxnDate(nlm.getModifiedDate());
			ncb.setTransactionId(nlm.getTransactionId());
			ncb.setTransactionType(nlm.getRequirementType());
			ncb.setTransactionStatus(nlm.getTransactionStatus());
			ncb.setCcy(nlm.getlCCurrency());
			*/
		}
		return custFinalList;
	}

	@Override
	public List<NimaiCustomerBean> getCreditTxnForCustomerByUserId(String userId, Date fromDate, Date toDate,
			String subsidiaryName) throws ParseException {
		// TODO Auto-generated method stub
		// TODO Auto-generated method stub
		List<NimaiClient> custData;
		if(subsidiaryName.equalsIgnoreCase("All"))
			custData=customerRepo.findCreditTransactionByUserId(userId);
		else
			custData=customerRepo.findCreditTransactionByUserIdSubsidiary(userId,subsidiaryName);
		
		String companyName=customerRepo.findCompanyNameByUserId(userId);		
		List<NimaiCustomerBean> custFinalList = new ArrayList<>();
		ModelMapperUtil modelMapper = new ModelMapperUtil();
		Double avgAmount;
		DateFormat simpleDateFormat=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		int i=0;
		for(NimaiClient nc:custData)
		{
			List nlm=lcmasterrepo.findTransactionByUserIdStartDateEndDate(nc.getUserid(),fromDate,toDate);
			for(Object txnDet:nlm)
			{
				NimaiCustomerBean ncb=modelMapper.map(nc, NimaiCustomerBean.class);
				ncb.setCompanyName(companyName);
				ncb.setTransactionId(((Object[])txnDet)[0]==null?null:((Object[])txnDet)[0].toString());
				ncb.setTransactionType(((Object[])txnDet)[1]==null?null:((Object[])txnDet)[1].toString());
				ncb.setCcy(((Object[])txnDet)[2]==null?null:((Object[])txnDet)[2].toString());
				ncb.setTxnInsertedDate(((Object[])txnDet)[3]==null?new Date(0):(Date)simpleDateFormat.parse(((Object[])txnDet)[3].toString()));
				ncb.setTxnDate(((Object[])txnDet)[4]==null?new Date(0):(Date)simpleDateFormat.parse(((Object[])txnDet)[4].toString()));
				ncb.setTransactionStatus(((Object[])txnDet)[5]==null?null:((Object[])txnDet)[5].toString());
				String lcCountry=((Object[])txnDet)[6]==null?null:((Object[])txnDet)[6].toString();
				if(ncb.getTransactionStatus().equalsIgnoreCase("Active") || ncb.getTransactionStatus().equalsIgnoreCase("Accepted") || ncb.getTransactionStatus().equalsIgnoreCase("Expired"))
				{
					ncb.setCreditUsed(1);
				}
				if(ncb.getTransactionStatus().equalsIgnoreCase("Rejected") )
				{
					ncb.setCreditUsed(0);
				}
				try
				{
					Double saving=trSavingRepo.getSavingsByTransId(ncb.getTransactionId());
					System.out.println("Saving: "+saving);
					//float quoteValue=quotationService.getQuoteValueByQid(quotationId);
					ncb.setSavings(saving);
				}
				catch(Exception e)
				{
					ncb.setSavings(0.0);
				}
				custFinalList.add(ncb);
			}
					//NimaiCustomerBean ncb=modelMapper.map(nc, NimaiCustomerBean.class);
					/*ncb.setTxnDate(nlm.getModifiedDate());
					ncb.setTransactionId(nlm.getTransactionId());
					ncb.setTransactionType(nlm.getRequirementType());
					ncb.setTransactionStatus(nlm.getTransactionStatus());
					ncb.setCcy(nlm.getlCCurrency());
					*/
		}
		return custFinalList;
	}

	@Override
	public void updateReopenCounter(String transactionId) {
		// TODO Auto-generated method stub
		lcmasterrepo.updateCounterAfterReopen(transactionId);
	}

	@Override
	public Integer getReopenCounter(String transactionId) {
		// TODO Auto-generated method stub
		try
		{
			Integer ctr=Integer.valueOf(lcmasterrepo.getReopenCtr(transactionId));
			if(ctr==null || ctr==0)
				return 0;
			else
				return ctr;
		}
		catch(Exception e)
		{
			System.out.println(e);
			return 0;
		}
	}

	@Override
	public void updateLCUtilized(String userId) {
		// TODO Auto-generated method stub
		quotemasterrepo.updateLCUtilizedByUserId(userId);
	}

	@Override
	public void insertDataForSavingInput(String lcCountry, String lcCurrency) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateLCUtilizedReopen4Times(String userId) {
		// TODO Auto-generated method stub
		quotemasterrepo.updateLCUtilizedByUserIdAfter4Reopen(userId);
	}

	@Override
	public List<NimaiCustomerBean> getCreditTxnForCustomerByUserId(String userId, String subsidiaryName,
			String passcodeUser) throws ParseException {
		List<NimaiClient> custData;
		if((subsidiaryName.equalsIgnoreCase("All") || subsidiaryName.equalsIgnoreCase("")) && 
				(passcodeUser.equalsIgnoreCase("All") || passcodeUser.equalsIgnoreCase("")))
			custData=customerRepo.findCreditTransactionByUserId(userId);
		else if(!passcodeUser.equalsIgnoreCase("") && !passcodeUser.equalsIgnoreCase("All"))
			custData=null;
		else
			custData=customerRepo.findCreditTransactionByUserIdSubsidiary(userId,subsidiaryName);
		List<NimaiCustomerBean> custFinalList = new ArrayList<>();
		ModelMapperUtil modelMapper = new ModelMapperUtil();
		Double avgAmount;
		DateFormat simpleDateFormat=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		int i=0;
		String companyName=customerRepo.findCompanyNameByUserId(userId);
		if(custData==null)
		{
			NimaiClient nc=customerRepo.findCreditTransactionByUserIdForPasscode(userId);
			List nlm;
			nlm=lcmasterrepo.findTransactionByBranchEmailId(passcodeUser);
			for(Object txnDet:nlm)
			{
			NimaiCustomerBean ncb=modelMapper.map(nc, NimaiCustomerBean.class);
			ncb.setCompanyName(companyName);
			ncb.setTransactionId(((Object[])txnDet)[0]==null?null:((Object[])txnDet)[0].toString());
			ncb.setTransactionType(((Object[])txnDet)[1]==null?null:((Object[])txnDet)[1].toString());
			ncb.setCcy(((Object[])txnDet)[2]==null?null:((Object[])txnDet)[2].toString());
			ncb.setTxnInsertedDate(((Object[])txnDet)[3]==null?new Date(0):(Date)simpleDateFormat.parse(((Object[])txnDet)[3].toString()));
			ncb.setTxnDate(((Object[])txnDet)[4]==null?new Date(0):(Date)simpleDateFormat.parse(((Object[])txnDet)[4].toString()));
			ncb.setTransactionStatus(((Object[])txnDet)[5]==null?null:((Object[])txnDet)[5].toString());
			ncb.setPasscodeUser(((Object[])txnDet)[7]==null?null:((Object[])txnDet)[7].toString());
			if(ncb.getTransactionStatus().equalsIgnoreCase("Active") || ncb.getTransactionStatus().equalsIgnoreCase("Accepted") || ncb.getTransactionStatus().equalsIgnoreCase("Expired"))
			{
				ncb.setCreditUsed(1);
			}
			if(ncb.getTransactionStatus().equalsIgnoreCase("Rejected") )
			{
				ncb.setCreditUsed(0);
			}
			try
			{
				Double saving=trSavingRepo.getSavingsByTransId(ncb.getTransactionId());
				System.out.println("Saving: "+saving);
				//float quoteValue=quotationService.getQuoteValueByQid(quotationId);
				ncb.setSavings(saving);
			}
			catch(Exception e)
			{
				ncb.setSavings(0.0);
			}
			custFinalList.add(ncb);
			}
			return custFinalList;
		}
		else
		{
		for(NimaiClient nc:custData)
			{
			List nlm;
			if(passcodeUser.equalsIgnoreCase("all"))
				nlm=lcmasterrepo.findTransactionByUserId(nc.getUserid());
			else
				nlm=lcmasterrepo.findTransactionByBranchEmailId(passcodeUser);
				for(Object txnDet:nlm)
				{
				NimaiCustomerBean ncb=modelMapper.map(nc, NimaiCustomerBean.class);
				ncb.setCompanyName(companyName);
				ncb.setTransactionId(((Object[])txnDet)[0]==null?null:((Object[])txnDet)[0].toString());
				ncb.setTransactionType(((Object[])txnDet)[1]==null?null:((Object[])txnDet)[1].toString());
				ncb.setCcy(((Object[])txnDet)[2]==null?null:((Object[])txnDet)[2].toString());
				ncb.setTxnInsertedDate(((Object[])txnDet)[3]==null?new Date(0):(Date)simpleDateFormat.parse(((Object[])txnDet)[3].toString()));
				ncb.setTxnDate(((Object[])txnDet)[4]==null?new Date(0):(Date)simpleDateFormat.parse(((Object[])txnDet)[4].toString()));
				ncb.setTransactionStatus(((Object[])txnDet)[5]==null?null:((Object[])txnDet)[5].toString());
				ncb.setPasscodeUser(((Object[])txnDet)[7]==null?null:((Object[])txnDet)[7].toString());
				if(ncb.getTransactionStatus().equalsIgnoreCase("Active") || ncb.getTransactionStatus().equalsIgnoreCase("Accepted") || ncb.getTransactionStatus().equalsIgnoreCase("Expired"))
				{
					ncb.setCreditUsed(1);
				}
				if(ncb.getTransactionStatus().equalsIgnoreCase("Rejected") )
				{
					ncb.setCreditUsed(0);
				}
				try
				{
					Double saving=trSavingRepo.getSavingsByTransId(ncb.getTransactionId());
					System.out.println("Saving: "+saving);
					//float quoteValue=quotationService.getQuoteValueByQid(quotationId);
					ncb.setSavings(saving);
				}
				catch(Exception e)
				{
					ncb.setSavings(0.0);
				}
				custFinalList.add(ncb);
				}
			//NimaiCustomerBean ncb=modelMapper.map(nc, NimaiCustomerBean.class);
			/*ncb.setTxnDate(nlm.getModifiedDate());
			ncb.setTransactionId(nlm.getTransactionId());
			ncb.setTransactionType(nlm.getRequirementType());
			ncb.setTransactionStatus(nlm.getTransactionStatus());
			ncb.setCcy(nlm.getlCCurrency());
			*/
		}
		return custFinalList;
		}
	}

	@Override
	public List<NimaiCustomerBean> getCreditTxnForCustomerByUserId(String userId, Date fromDate, String subsidiaryName,
			String passcodeUser) throws ParseException {
		// TODO Auto-generated method stub
		List<NimaiClient> custData;
		if((subsidiaryName.equalsIgnoreCase("All") || subsidiaryName.equalsIgnoreCase("")) && 
				(passcodeUser.equalsIgnoreCase("All") || passcodeUser.equalsIgnoreCase("")))
			custData=customerRepo.findCreditTransactionByUserId(userId);
		else if(!passcodeUser.equalsIgnoreCase("") && !passcodeUser.equalsIgnoreCase("All"))
			custData=null;
		else
			custData=customerRepo.findCreditTransactionByUserIdSubsidiary(userId,subsidiaryName);
		List<NimaiCustomerBean> custFinalList = new ArrayList<>();
		ModelMapperUtil modelMapper = new ModelMapperUtil();
		Double avgAmount;
		DateFormat simpleDateFormat=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		int i=0;
		List nlm;
		String companyName=customerRepo.findCompanyNameByUserId(userId);
		if(custData==null)
		{
			NimaiClient nc=customerRepo.findCreditTransactionByUserIdForPasscode(userId);
			nlm=lcmasterrepo.findTransactionByBranchEmailIdStartDate(nc.getUserid(),passcodeUser,fromDate);
      
			for(Object txnDet:nlm)
			{
			NimaiCustomerBean ncb=modelMapper.map(nc, NimaiCustomerBean.class);
			ncb.setCompanyName(companyName);
			ncb.setTransactionId(((Object[])txnDet)[0]==null?null:((Object[])txnDet)[0].toString());
			ncb.setTransactionType(((Object[])txnDet)[1]==null?null:((Object[])txnDet)[1].toString());
			ncb.setCcy(((Object[])txnDet)[2]==null?null:((Object[])txnDet)[2].toString());
			ncb.setTxnInsertedDate(((Object[])txnDet)[3]==null?new Date(0):(Date)simpleDateFormat.parse(((Object[])txnDet)[3].toString()));
			ncb.setTxnDate(((Object[])txnDet)[4]==null?new Date(0):(Date)simpleDateFormat.parse(((Object[])txnDet)[4].toString()));
			ncb.setTransactionStatus(((Object[])txnDet)[5]==null?null:((Object[])txnDet)[5].toString());
			ncb.setPasscodeUser(((Object[])txnDet)[7]==null?null:((Object[])txnDet)[7].toString());
			if(ncb.getTransactionStatus().equalsIgnoreCase("Active") || ncb.getTransactionStatus().equalsIgnoreCase("Accepted") || ncb.getTransactionStatus().equalsIgnoreCase("Expired"))
			{
				ncb.setCreditUsed(1);
			}
			if(ncb.getTransactionStatus().equalsIgnoreCase("Rejected") )
			{
				ncb.setCreditUsed(0);
			}
			try
			{
				Double saving=trSavingRepo.getSavingsByTransId(ncb.getTransactionId());
				System.out.println("Saving: "+saving);
				//float quoteValue=quotationService.getQuoteValueByQid(quotationId);
				ncb.setSavings(saving);
			}
			catch(Exception e)
			{
				ncb.setSavings(0.0);
			}
			custFinalList.add(ncb);
			}
			return custFinalList;
		}
		else
		{
		for(NimaiClient nc:custData)
			{
			if(passcodeUser.equalsIgnoreCase("all"))
				nlm=lcmasterrepo.findTransactionByUserIdStartDate(nc.getUserid(), fromDate);
			else
				nlm=lcmasterrepo.findTransactionByBranchEmailIdStartDate(nc.getUserid(),passcodeUser,fromDate);
			//List nlm=lcmasterrepo.findTransactionByUserIdStartDate(nc.getUserid(),fromDate);
			
				for(Object txnDet:nlm)
				{
				NimaiCustomerBean ncb=modelMapper.map(nc, NimaiCustomerBean.class);
				ncb.setCompanyName(companyName);
				ncb.setTransactionId(((Object[])txnDet)[0]==null?null:((Object[])txnDet)[0].toString());
				ncb.setTransactionType(((Object[])txnDet)[1]==null?null:((Object[])txnDet)[1].toString());
				ncb.setCcy(((Object[])txnDet)[2]==null?null:((Object[])txnDet)[2].toString());
				ncb.setTxnInsertedDate(((Object[])txnDet)[3]==null?new Date(0):(Date)simpleDateFormat.parse(((Object[])txnDet)[3].toString()));
				ncb.setTxnDate(((Object[])txnDet)[4]==null?new Date(0):(Date)simpleDateFormat.parse(((Object[])txnDet)[4].toString()));
				ncb.setTransactionStatus(((Object[])txnDet)[5]==null?null:((Object[])txnDet)[5].toString());
				String lcCountry=((Object[])txnDet)[6]==null?null:((Object[])txnDet)[6].toString();
				if(ncb.getTransactionStatus().equalsIgnoreCase("Active") || ncb.getTransactionStatus().equalsIgnoreCase("Accepted") || ncb.getTransactionStatus().equalsIgnoreCase("Expired"))
				{
					ncb.setCreditUsed(1);
				}
				if(ncb.getTransactionStatus().equalsIgnoreCase("Rejected") )
				{
					ncb.setCreditUsed(0);
				}
				try
				{
					Double saving=trSavingRepo.getSavingsByTransId(ncb.getTransactionId());
					System.out.println("Saving: "+saving);
					//float quoteValue=quotationService.getQuoteValueByQid(quotationId);
					ncb.setSavings(saving);
				}
				catch(Exception e)
				{
					ncb.setSavings(0.0);
				}
				custFinalList.add(ncb);
				}
			//NimaiCustomerBean ncb=modelMapper.map(nc, NimaiCustomerBean.class);
			/*ncb.setTxnDate(nlm.getModifiedDate());
			ncb.setTransactionId(nlm.getTransactionId());
			ncb.setTransactionType(nlm.getRequirementType());
			ncb.setTransactionStatus(nlm.getTransactionStatus());
			ncb.setCcy(nlm.getlCCurrency());
			*/
		}
		return custFinalList;
    }
	}

	@Override
	public List<NimaiCustomerBean> getCreditTxnForCustomerByUserId(String userId, Date fromDate, Date toDate,
			String subsidiaryName, String passcodeUser) throws ParseException {
		// TODO Auto-generated method stub
		List<NimaiClient> custData;
		if((subsidiaryName.equalsIgnoreCase("All") || subsidiaryName.equalsIgnoreCase("")) && 
				(passcodeUser.equalsIgnoreCase("All") || passcodeUser.equalsIgnoreCase("")))
			custData=customerRepo.findCreditTransactionByUserId(userId);
		else if(!passcodeUser.equalsIgnoreCase("") && !passcodeUser.equalsIgnoreCase("All"))
			custData=null;
		else
			custData=customerRepo.findCreditTransactionByUserIdSubsidiary(userId,subsidiaryName);
		
		String companyName=customerRepo.findCompanyNameByUserId(userId);		
		List<NimaiCustomerBean> custFinalList = new ArrayList<>();
		ModelMapperUtil modelMapper = new ModelMapperUtil();
		Double avgAmount;
		
		DateFormat simpleDateFormat=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		int i=0;
		if(custData==null)
		{
			List nlm;
			NimaiClient nc=customerRepo.findCreditTransactionByUserIdForPasscode(userId);
			nlm=lcmasterrepo.findTransactionByBranchEmailIdStartDateEndDate(nc.getUserid(),passcodeUser,fromDate,toDate);
			
			for(Object txnDet:nlm)
			{
			NimaiCustomerBean ncb=modelMapper.map(nc, NimaiCustomerBean.class);
			ncb.setCompanyName(companyName);
			ncb.setTransactionId(((Object[])txnDet)[0]==null?null:((Object[])txnDet)[0].toString());
			ncb.setTransactionType(((Object[])txnDet)[1]==null?null:((Object[])txnDet)[1].toString());
			ncb.setCcy(((Object[])txnDet)[2]==null?null:((Object[])txnDet)[2].toString());
			ncb.setTxnInsertedDate(((Object[])txnDet)[3]==null?new Date(0):(Date)simpleDateFormat.parse(((Object[])txnDet)[3].toString()));
			ncb.setTxnDate(((Object[])txnDet)[4]==null?new Date(0):(Date)simpleDateFormat.parse(((Object[])txnDet)[4].toString()));
			ncb.setTransactionStatus(((Object[])txnDet)[5]==null?null:((Object[])txnDet)[5].toString());
			ncb.setPasscodeUser(((Object[])txnDet)[7]==null?null:((Object[])txnDet)[7].toString());
			if(ncb.getTransactionStatus().equalsIgnoreCase("Active") || ncb.getTransactionStatus().equalsIgnoreCase("Accepted") || ncb.getTransactionStatus().equalsIgnoreCase("Expired"))
			{
				ncb.setCreditUsed(1);
			}
			if(ncb.getTransactionStatus().equalsIgnoreCase("Rejected") )
			{
				ncb.setCreditUsed(0);
			}
			try
			{
				Double saving=trSavingRepo.getSavingsByTransId(ncb.getTransactionId());
				System.out.println("Saving: "+saving);
				//float quoteValue=quotationService.getQuoteValueByQid(quotationId);
				ncb.setSavings(saving);
			}
			catch(Exception e)
			{
				ncb.setSavings(0.0);
			}
			custFinalList.add(ncb);
			}
			return custFinalList;
		}
		else
		{
		for(NimaiClient nc:custData)
		{
			List nlm;
			if(passcodeUser.equalsIgnoreCase("all"))
				nlm=lcmasterrepo.findTransactionByUserIdStartDateEndDate(nc.getUserid(), fromDate,toDate);
			else
				nlm=lcmasterrepo.findTransactionByBranchEmailIdStartDateEndDate(nc.getUserid(),passcodeUser,fromDate,toDate);
			
			//List nlm=lcmasterrepo.findTransactionByUserIdStartDateEndDate(nc.getUserid(),fromDate,toDate);
			for(Object txnDet:nlm)
			{
				NimaiCustomerBean ncb=modelMapper.map(nc, NimaiCustomerBean.class);
				ncb.setCompanyName(companyName);
				ncb.setTransactionId(((Object[])txnDet)[0]==null?null:((Object[])txnDet)[0].toString());
				ncb.setTransactionType(((Object[])txnDet)[1]==null?null:((Object[])txnDet)[1].toString());
				ncb.setCcy(((Object[])txnDet)[2]==null?null:((Object[])txnDet)[2].toString());
				ncb.setTxnInsertedDate(((Object[])txnDet)[3]==null?new Date(0):(Date)simpleDateFormat.parse(((Object[])txnDet)[3].toString()));
				ncb.setTxnDate(((Object[])txnDet)[4]==null?new Date(0):(Date)simpleDateFormat.parse(((Object[])txnDet)[4].toString()));
				ncb.setTransactionStatus(((Object[])txnDet)[5]==null?null:((Object[])txnDet)[5].toString());
				String lcCountry=((Object[])txnDet)[6]==null?null:((Object[])txnDet)[6].toString();
				if(ncb.getTransactionStatus().equalsIgnoreCase("Active") || ncb.getTransactionStatus().equalsIgnoreCase("Accepted") || ncb.getTransactionStatus().equalsIgnoreCase("Expired"))
				{
					ncb.setCreditUsed(1);
				}
				if(ncb.getTransactionStatus().equalsIgnoreCase("Rejected") )
				{
					ncb.setCreditUsed(0);
				}
				try
				{
					Double saving=trSavingRepo.getSavingsByTransId(ncb.getTransactionId());
					System.out.println("Saving: "+saving);
					//float quoteValue=quotationService.getQuoteValueByQid(quotationId);
					ncb.setSavings(saving);
				}
				catch(Exception e)
				{
					ncb.setSavings(0.0);
				}
				custFinalList.add(ncb);
			}
					
		}
		return custFinalList;
	}
	}



	@Override
	public NimaiClient checkMasterSubsidiary(String accountType, String userId, NimaiClient userDetails) {
		String checkForSubsidiary="";
		String checkForAdditionalUser="";
//		if(userId.substring(0, 2).equalsIgnoreCase("CU"))
//		{
//			checkForSubsidiary=accountType;
//	}
//		else
//		{
//			checkForAdditionalUser=accountType;
//		}
		//if(checkForSubsidiary.equalsIgnoreCase("subsidiary") || checkForAdditionalUser.equalsIgnoreCase("bankuser"))
		if(userDetails.getAccountType().equalsIgnoreCase("subsidiary")||
				userDetails.getAccountType().equalsIgnoreCase("bankuser"))
		
		{
			System.out.println("===== Getting Master User ====");
		//	String masterUserId=lcmasterrepo.findMasterForSubsidiary(userId);
			NimaiClient masterUserId=customerRepo.getOne(userDetails.getAccountSource());
			System.out.println("User is Subsidiary of Master User: "+masterUserId);
			return masterUserId;
		}
		else
		{
			System.out.println(userId+" is Master User");
			return userDetails;
		}
	}

	@Override
	public List<ResponseEntity<Object>> saveTempLc(NimaiClient subscriptionDettails,NimaiLCBean nimailc) {
		GenericResponse response = new GenericResponse<>();
		
		return	subscriptionDettails.getSubscriptionDettails().stream().filter(t -> t.getStatus().equalsIgnoreCase("ACTIVE")).map(request->{
			
			Integer lcCount=Integer.valueOf(request.getlCount());
			Integer utilizedLcCount=request.getLcUtilizedCount();
		
			if (lcCount > utilizedLcCount) 
			{
				try 
				{
					String transId = nimailc.getTransactionId();
					String userId = nimailc.getUserId();
					System.out.println(transId + " " + userId);
					//Date validityDate=lcservice.getValidityDate(transId,userId);
					NimaiLC draftDet=lcrepo.findByTransactionIdUserId(transId,userId);
					
					Date today=new Date();
					Calendar cal1 = Calendar.getInstance();
					Calendar cal2 = Calendar.getInstance();
					cal1.setTime(draftDet.getValidity());
					cal1.add(Calendar.DATE, 1);
					cal2.setTime(today);
					System.out.println("Validity Date: "+cal1);
					System.out.println("Today Date: "+cal2);
					if(cal1.get(Calendar.DAY_OF_YEAR) < cal2.get(Calendar.DAY_OF_YEAR) ||
							cal1.get(Calendar.MONTH) < cal2.get(Calendar.MONTH) ||
							cal1.get(Calendar.YEAR) < cal2.get(Calendar.YEAR))
					{
						response.setStatus("Failure");
						response.setErrMessage("Please select correct transaction validity date");
						return new ResponseEntity<Object>(response, HttpStatus.OK);
			
					}
					
					String sts = confirmLCDet(transId, userId);
					
					
					if (sts.equals("Validation Success")) 
					{
						/*String lcCountry=lcservice.getLCIssuingCountryByTransId(transId);
						String lcCurrency=lcservice.getLCCurrencyByTransId(transId);
						Integer tenorDays=lcservice.getLCTenorDays(transId);
						Double lcValue=lcservice.getLCValue(transId);
						lcservice.insertDataForSavingInput(lcCountry,lcCurrency,lcValue,);*/
					
						
						
						getAlleligibleBAnksEmail(userId, transId,0,"LC_UPLOAD_ALERT_ToBanks","LC_UPLOAD(DATA)");
						response.setStatus("Success");
						response.setData(sts);
						return new ResponseEntity<Object>(response, HttpStatus.OK);
					}
						
					
					else 
					{
						response.setStatus("Failure");
						response.setErrMessage(sts);
						return new ResponseEntity<Object>(response, HttpStatus.OK);
					}
				} 
				catch (Exception e) 
				{
					response.setStatus("Failure");
					response.setErrCode("EXE000");
					response.setErrMessage(ErrorDescription.getDescription("EXE000") + e);
					return new ResponseEntity<Object>(response, HttpStatus.BAD_REQUEST);
				}
			} 
			else 
			{
				response.setStatus("Failure");
				response.setErrMessage("You had reached maximum LC Count!");
				return new ResponseEntity<Object>(response, HttpStatus.OK);
			}
		}).collect(Collectors.toList());
//	}catch(Exception e) {
//		e.printStackTrace();
//		response.setStatus("Failure");
//		System.out.println("You are not Subscribe to a Plan. Please Subscribe");
//		response.setErrMessage("You are not Subscribe to a Plan. Please Subscribe");
//		return (List<ResponseEntity<Object>>) new ResponseEntity<Object>(response, HttpStatus.OK);
//	}
	}


	
	
}
