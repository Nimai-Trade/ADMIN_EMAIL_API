package com.nimai.lc.service;

import java.text.ParseException;
import java.util.Date;
import java.util.List;

import org.springframework.http.ResponseEntity;

import com.nimai.lc.bean.CustomerTransactionBean;
import com.nimai.lc.bean.NewRequestBean;
import com.nimai.lc.bean.NimaiCustomerBean;
import com.nimai.lc.bean.NimaiLCBean;
import com.nimai.lc.bean.NimaiLCMasterBean;
import com.nimai.lc.entity.NimaiLCPort;
import com.nimai.lc.entity.NimaiSubscriptionDetails;
import com.nimai.lc.entity.Countrycurrency;
import com.nimai.lc.entity.Goods;
import com.nimai.lc.entity.NewRequestEntity;
import com.nimai.lc.entity.NimaiClient;
import com.nimai.lc.entity.NewRequestEntity;
import com.nimai.lc.entity.NimaiLC;
import com.nimai.lc.entity.NimaiLCMaster;

public interface LCService {

	public void saveLCdetails(NimaiLCBean nimailcbean, String tid);

	public List<Countrycurrency> getCountry();

	public List<NimaiLCMaster> getAllTransactionDetails();

	//public List<NimaiLCMaster> getAllTransactionForBank(String userid);
	public List<NewRequestEntity> getAllTransactionForBank(String userid);
	
	public NimaiLCMaster getSpecificTransactionDetail(String transactionId);

	public List<NimaiLCMaster> getAllTransactionDetailsByStatus(String status);

	public List<NimaiLCMaster> getTransactionDetailByUserId(String userId, String branchEmailId);

	public List<NimaiLCMaster> getTransactionDetailByUserIdAndStatus(String userId, String status,
			String branchEmailId);

	public String generateSerialNo();

	public String generateYear();

	public String generateCountryCode(String userid);

	public String generateSubscriberType(String userid);

	public String generateTransactionType(String userid);

	public String confirmLCDet(String transId, String userId);

	public void cloneLCDetail(String oldTransId, String newTransId);

	public NimaiLCMaster checkTransaction(String transId);

	public NimaiLC findByTransactionIdToConfirm(String transId);

	public NimaiLC findByTransactionUserIdToConfirm(String transId, String userId);

	public void moveToHistory(String transId, String userId);

	public void saveLCMasterdetails(NimaiLCMasterBean nimailcbean, String tid);

	public List<NimaiLC> getAllDraftTransactionDetails(String userId, String branchEmailId);

	public void updateDraftLCdetails(NimaiLCBean nimailcbean, String newtid);

	public NimaiLC getSpecificDraftTransactionDetail(String transactionId);

	public Integer getLcCount(String userId);

	public Integer getUtilizedLcCount(String userId);

	public NimaiLCMaster getTransactionForAcceptCheck(String transId);

	public void updateTransactionStatusToActive(String transactionId, String userId);

	public String checkMasterForSubsidiary(String userId);

	public Integer getNoOfBanksAgainstCountry(String countryName);

	public void updateTransactionForClosed(String transactionId, String userId, String reason);

	public List<NimaiLC> getDraftTransactionDetails(String transactionId);

	public void deleteDraftTransaction(String transactionId);

	void getAlleligibleBAnksEmail(String userId, String transactionId, int quoteId, String bankEmailEvent, String custEmailEvent);

	public List<CustomerTransactionBean> getTransactionForCustomerByUserIdAndStatus(String userId, String status,String branchEmailId) throws ParseException;

	public int getSpecificDraftTransactionDetailForDuplicate(String userId,String transactionId);

	public void updateQuotationReceivedForValidityDateExp(String userId);

	public String getLCIssuingCountryByTransId(String transId);

	public Double getAvgAmountForCountryFromAdmin(String lcCountry,String lcCurrency);

	public String getLCCurrencyByTransId(String transId);

	public List<NimaiLCPort> getPortListByCountry(String countryName);

	public NimaiLCMaster getAcceptedorExpiredTransaction(String transactionId, String userId);

	public void updateTransactionForCancel(String transactionId, String userId);

	public List<NimaiCustomerBean> getCreditTxnForCustomerByUserId(String userId) throws ParseException;

	public Date getValidityDate(String transId, String userId) throws ParseException;

	public List<NimaiCustomerBean> getCreditTxnForCustomerByUserId(String userId, Date fromDate) throws ParseException;

	public List<NimaiCustomerBean> getCreditTxnForCustomerByUserId(String userId, Date fromDate, Date toDate) throws ParseException;

	List<NimaiCustomerBean> getCreditTxnForCustomerByBankUserId(String userId) throws ParseException;

	public List<Goods> getGoods();

	public String getAccountType(String userid);

	public String getAccountSource(String userid);

	public List<NimaiCustomerBean> getCreditTxnForCustomerByUserId(String userId, String subsidiaryName) throws ParseException;

	public List<NimaiCustomerBean> getCreditTxnForCustomerByUserId(String userId, Date fromDate, String subsidiaryName)
			throws ParseException;

	public List<NimaiCustomerBean> getCreditTxnForCustomerByUserId(String userId, Date fromDate, Date toDate,
			String subsidiaryName) throws ParseException;

	public void updateReopenCounter(String transactionId);

	public Integer getReopenCounter(String transactionId);

	public void updateLCUtilized(String userId);

	public Integer getLCTenorDays(String transId);

	public Double getAnnualAssetValue(String lcCountry, String lcCurrency);

	public Double getNetRevenueLC(String lcCountry, String lcCurrency);

	//public Double getAvgSpreadForCountry(String lcCountry, String lcCurrency);

	public Double getLCValue(String transId);

	public void insertDataForSavingInput(String lcCountry, String lcCurrency);

	public void updateLCUtilizedReopen4Times(String userId);

	public List<NimaiCustomerBean> getCreditTxnForCustomerByUserId(String userId, String subsidiaryName,
			String passcodeUser) throws ParseException;

	public List<NimaiCustomerBean> getCreditTxnForCustomerByUserId(String userId, Date fromDate, String subsidiaryName,
			String passcodeUser) throws ParseException;

	public List<NimaiCustomerBean> getCreditTxnForCustomerByUserId(String userId, Date fromDate, Date toDate,
			String subsidiaryName, String passcodeUser) throws ParseException;

	List<NimaiCustomerBean> getCreditTxnForCustomerByBankUserId(String userId, Date fromDate) throws ParseException;

	List<NimaiCustomerBean> getCreditTxnForCustomerByBankUserId(String userId, Date fromDate, Date toDate)
			throws ParseException;

	public NimaiClient checkMasterSubsidiary(String accountType, String userId,NimaiClient userDetails);

	public List<ResponseEntity<Object>> saveTempLc(NimaiClient obtainUserId, NimaiLCBean nimailc);

	
}
