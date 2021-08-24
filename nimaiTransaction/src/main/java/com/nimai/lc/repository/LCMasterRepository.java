package com.nimai.lc.repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;

import com.nimai.lc.bean.CustomerTransactionBean;
import com.nimai.lc.entity.NimaiClient;
import com.nimai.lc.entity.NimaiLCMaster;

@Transactional
public interface LCMasterRepository extends JpaRepository<NimaiLCMaster, String> {
	
	@Query(value = "SELECT * from get_all_transaction", nativeQuery = true)
	List<NimaiLCMaster> findAllTransaction();

	@Query(value = "SELECT * from get_all_transaction where transaction_status=(:status)", nativeQuery = true)
	List<NimaiLCMaster> findAllTransactionByStatus(@Param("status") String status);

	// @Query(value="SELECT * from get_all_active_transaction alltr", nativeQuery =
	// true )
	// List<NimaiLCMaster> findAllActiveTransaction();

	@Query(value = "SELECT * from get_all_transaction where transaction_id=(:transid)", nativeQuery = true)
	NimaiLCMaster findSpecificTransactionById(@Param("transid") String transid);

	@Query(value = "SELECT * from get_all_transaction where user_id=(:userid)", nativeQuery = true)
	List<NimaiLCMaster> findByTransactionByUserId(@Param("userid") String userid);
	
	@Query(value = "SELECT distinct transaction_id,requirement_type,lc_currency,inserted_date,modified_date,transaction_status,lc_issuance_country,branch_user_email from get_all_transaction where user_id=(:userid) order by inserted_date desc", nativeQuery = true)
	List findTransactionByUserId(@Param("userid") String userid);
	
	@Query(value = "SELECT distinct transaction_id,inserted_date,modified_date,quotation_status from get_all_quotation where bank_userid=(:userid) order by inserted_date desc", nativeQuery = true)
	List findQuotationByBankUserId(@Param("userid") String userid);
	
	@Query(value = "SELECT distinct transaction_id,inserted_date,modified_date,quotation_status from get_all_quotation where bank_userid=(:userid) and inserted_date>=(:fromDate) order by inserted_date desc", nativeQuery = true)
	List findQuotationByBankUserIdStartDate(@Param("userid") String userid,@Param("fromDate") Date fromDate);
	
	@Query(value = "SELECT distinct transaction_id,inserted_date,modified_date,quotation_status from get_all_quotation where bank_userid=(:userid) and inserted_date>=(:fromDate) and inserted_date<=(:toDate) order by inserted_date desc", nativeQuery = true)
	List findQuotationByBankUserIdStartDateEndDate(@Param("userid") String userid,@Param("fromDate") Date fromDate,@Param("toDate") Date toDate);
	
	@Query(value = "SELECT transaction_id,requirement_type,lc_currency,inserted_date,modified_date,transaction_status,lc_issuance_country,branch_user_email from get_all_transaction where branch_user_email=(:emailid) order by inserted_date desc", nativeQuery = true)
	List findTransactionByBranchEmailId(@Param("emailid") String emailid);
	
	@Query(value = "SELECT transaction_id,requirement_type,lc_currency,inserted_date,modified_date,transaction_status,lc_issuance_country,branch_user_email from get_all_transaction where user_id=(:userid) and inserted_date>=(:fromDate) order by inserted_date desc", nativeQuery = true)
	List findTransactionByUserIdStartDate(@Param("userid") String userid,@Param("fromDate") Date fromDate);

	@Query(value = "SELECT transaction_id,requirement_type,lc_currency,inserted_date,modified_date,transaction_status,lc_issuance_country,branch_user_email from get_all_transaction where user_id=(:userid) and inserted_date>=(:fromDate) and inserted_date<=(:toDate) order by inserted_date desc", nativeQuery = true)
	List findTransactionByUserIdStartDateEndDate(@Param("userid") String userid,@Param("fromDate") Date fromDate,@Param("toDate") Date toDate);

	@Query(value = "SELECT * from get_all_transaction where user_id=(:userid) and transaction_status=(:status)", nativeQuery = true)
	List<NimaiLCMaster> findByTransactionByUserIdAndStatus(@Param("userid") String userid,
			@Param("status") String status);

	@Procedure("quote_calculation")
	public void getQuote(@Param("inp_transaction_id") String transId);

	@Procedure(name = "move_to_historytbl")
	public void insertIntoHistory(@Param("inp_transaction_id") String transId, @Param("inp_userid") String userId);

	@Modifying
	@Query(value = "update nimai_mm_transaction set modified_date=now(), transaction_status='Accepted', quotation_accepted='Yes', accepted_on=now(), status_reason='Transaction Accepted' where transaction_id=(:transId)", nativeQuery = true)
	void updateTransactionStatusToAccept(String transId);

	@Modifying
	@Query(value = "update nimai_mm_transaction set modified_date=now(), transaction_status='Rejected', status_reason=(:statusReason), rejected_on=now(), quotation_received=ifnull(quotation_received,0)-1 where transaction_id=(:transId)", nativeQuery = true)
	void updateTransactionStatusToReject(String transId, @Param("statusReason") String statusReason);

	@Query(value = "SELECT email_address from nimai_m_customer where userid=(:userid)", nativeQuery = true)
	String getEmailAddress(@Param("userid") String userid);

	@Query(value = "SELECT * from get_all_transaction where user_id=(:userid) and branch_user_email=(:branchEmailId)", nativeQuery = true)
	List<NimaiLCMaster> findByTransactionByUserIdAndBranchEmail(@Param("userid") String userId,
			@Param("branchEmailId") String branchEmailId);

	@Query(value = "SELECT * from get_all_transaction where user_id=(:userid) and transaction_status=(:status) and branch_user_email=(:branchEmailId)", nativeQuery = true)
	List<NimaiLCMaster> findByTransactionByUserIdStatusBranchEmail(@Param("userid") String userId,
			@Param("status") String status, @Param("branchEmailId") String branchEmailId);

	@Query(value = "select * from nimai_mm_transaction where transaction_status='Accepted' and quotation_accepted='Yes'and transaction_id=(:transId)", nativeQuery = true)
	NimaiLCMaster getTransactionByTransIdTrStatusAndQuoteStatus(String transId);

	@Modifying
	@Query(value = "update nimai_mm_transaction set modified_date=now(), transaction_status='Active', status_reason='Active after Reopen' where transaction_id=(:transactionId) and user_id=(:userId)", nativeQuery = true)
	void updateTransactionStatusToActive(String transactionId, String userId);

	@Query(value = "select account_type from nimai_m_customer where userid=(:userId)", nativeQuery = true)
	String getAccountType(String userId);

	@Query(value = "select account_source from nimai_m_customer where userid=(:userId)", nativeQuery = true)
	String findMasterForSubsidiary(String userId);

	@Modifying
	@Query(value = "update nimai_mm_transaction set modified_date=now(), transaction_status='Closed', status_reason=(:reason) where transaction_id=(:transactionId) and user_id=(:userId)", nativeQuery = true)
	void updateTransactionStatusToClosed(String transactionId, String userId, String reason);

	@Query(value = "SELECT * from get_all_transaction where user_id=(:userId) and transaction_status IN ('Accepted','Closed') ", nativeQuery = true)
	List<NimaiLCMaster> findTransactionByUserIdAndAcceptedClosedStatus(String userId);

	@Query(value = "SELECT * from get_all_transaction where user_id=(:userId) and transaction_status IN ('Accepted','Closed') and branch_user_email=(:branchEmailId)", nativeQuery = true)
	List<NimaiLCMaster> findTransactionByUserIdAndAcceptedClosedStatusBranchEmail(String userId, String branchEmailId);

	@Query(value = "select email_address from nimai_m_customer where userid=(:userId)", nativeQuery = true)
	String getCustomerEmailId(String userId);
	
	@Query(value = "select first_name from nimai_m_customer where userid=(:userId)", nativeQuery = true)
	String getCustomerName(String userId);
	
	@Query(value = "select * from nimai_m_customer where EMAIL_ADDRESS=(:userId)", nativeQuery = true)
	NimaiClient getCustomerDetais(String userId);

	//New 11/09/2020
	@Query(value = "SELECT transaction_id,user_id,requirement_type,lc_issuance_bank,lc_value,goods_type,applicant_name,bene_name,quotation_received,inserted_date,validity,accepted_on,transaction_status,rejected_on,lc_currency,status_reason from get_all_transaction where user_id=(:userId) and transaction_status IN ('Accepted','Closed') ", nativeQuery = true)
	List findTransactionForCustByUserIdAndAcceptedClosedStatus(String userId);
	
	@Query(value = "SELECT transaction_id,user_id,requirement_type,lc_issuance_bank,lc_value,goods_type,applicant_name,bene_name,quotation_received,inserted_date,validity,accepted_on,transaction_status,rejected_on,lc_currency,status_reason from get_all_transaction where (user_id=(:userId) or user_id in (select nmc.userid from nimai_m_customer nmc where nmc.account_source=(:userId))) and transaction_status IN ('Accepted','Closed') ", nativeQuery = true)
	List findTransactionForCustByUserIdSubsIdAndAcceptedClosedStatus(String userId);
	
	@Query(value = "SELECT transaction_id,user_id,requirement_type,lc_issuance_bank,lc_value,goods_type,applicant_name,bene_name,quotation_received,inserted_date,validity,accepted_on,transaction_status,rejected_on,lc_currency,status_reason from get_all_transaction where user_id=(:userId) and transaction_status IN ('Accepted','Closed') and branch_user_email=(:branchEmailId)", nativeQuery = true)
	List findTransactionForCustByUserIdAndAcceptedClosedStatusBranchEmail(String userId, String branchEmailId);

	@Query(value = "SELECT transaction_id,user_id,requirement_type,lc_issuance_bank,lc_value,goods_type,applicant_name,bene_name,quotation_received,inserted_date,validity,accepted_on,transaction_status,rejected_on,lc_currency,status_reason from get_all_transaction where transaction_status IN ('Accepted','Closed') and branch_user_email=(:branchEmailId)", nativeQuery = true)
	List findTransactionForCustByUserIdAndAcceptedClosedStatusBranchEmailOnly(String branchEmailId);

	 @Query(value = "SELECT transaction_id,user_id,requirement_type,lc_issuance_bank,lc_value,goods_type,applicant_name,bene_name,quotation_received,inserted_date,validity,accepted_on,transaction_status,rejected_on,lc_currency,status_reason from get_all_transaction where (user_id=(:userId) or user_id in (select nmc.userid from nimai_m_customer nmc \r\n" + 
			"where nmc.account_source=(:userId))) and transaction_status=(:status)", nativeQuery = true)
	List findTransactionForCustByUserIdAndStatus(@Param("userId") String userid,
			@Param("status") String status);
	
	
	@Query(value = "SELECT transaction_id,user_id,requirement_type,lc_issuance_bank,lc_value,goods_type,applicant_name,bene_name,quotation_received,inserted_date,validity,accepted_on,transaction_status,rejected_on,lc_currency,status_reason from get_all_transaction where user_id=(:userId) and transaction_status=(:status)", nativeQuery = true)
	List findTransactionForCustByUserIdAndStatusExpAll(@Param("userId") String userid,
			@Param("status") String status);
	
	@Query(value = "SELECT transaction_id,user_id,requirement_type,lc_issuance_bank,lc_value,goods_type,applicant_name,bene_name,quotation_received,inserted_date,validity,accepted_on,transaction_status,rejected_on,lc_currency,status_reason from get_all_transaction where user_id IN (:subsidiaryList) and transaction_status=(:status)", nativeQuery = true)
	List findTransactionForCustByUserIdListAndStatus(List subsidiaryList,
			@Param("status") String status);
	
	@Query(value = "SELECT transaction_id,user_id,requirement_type,lc_issuance_bank,lc_value,goods_type,applicant_name,bene_name,quotation_received,inserted_date,validity,accepted_on,transaction_status,rejected_on,lc_currency,status_reason from get_all_transaction where user_id=(:userid) and transaction_status=(:status) and branch_user_email=(:branchEmailId)", nativeQuery = true)
	List findTransactionForCustByUserIdStatusBranchEmail(@Param("userid") String userId,
			@Param("status") String status, @Param("branchEmailId") String branchEmailId);

	@Query(value = "SELECT transaction_id,user_id,requirement_type,lc_issuance_bank,lc_value,goods_type,applicant_name,bene_name,quotation_received,inserted_date,validity,accepted_on,transaction_status,rejected_on,lc_currency,status_reason from get_all_transaction where transaction_status=(:status) and branch_user_email=(:branchEmailId)", nativeQuery = true)
	List findTransactionForCustByStatusBranchEmail(
			@Param("status") String status, @Param("branchEmailId") String branchEmailId);
	
	@Query(value = "SELECT\r\n" + 
			"DISTINCT(CASE WHEN nmt.applicant_name = (:applicantName) THEN 1 ELSE 0 END\r\n" + 
			"+CASE WHEN nmt.lc_value = (:lcValue) THEN 1 ELSE 0 END\r\n" + 
			"+CASE WHEN nmt.lc_currency = (:lcCurrency) THEN 1 ELSE 0 END\r\n" + 
			"+CASE WHEN nmt.lc_issuance_bank = (:issuanceBank) THEN 1 ELSE 0 END\r\n" + 
			"+CASE WHEN nmt.confirmation_period = (:confirmationPeriod) THEN 1 ELSE 0 END\r\n" + 
			"+CASE WHEN nmt.goods_type = (:goodsType) THEN 1 ELSE 0 END\r\n" + 
			"+CASE WHEN nmt.requirement_type = (:requirementType) THEN 1 ELSE 0 END\r\n" + 
			")\r\n" + 
			"AS Conditions\r\n" + 
			"From nimai_mm_transaction nmt WHERE nmt.user_id=(:userId) and nmt.transaction_status='Active' HAVING MIN(Conditions)>=3", nativeQuery = true)
	int getConditionValue(String userId, String applicantName, Double lcValue, String lcCurrency, String issuanceBank,
			String confirmationPeriod, String goodsType, String requirementType);

	@Modifying
	@Query(value = "update nimai_mm_transaction set quotation_received=quotation_received-1 where transaction_id=(:transId) and user_id=(:userId)", nativeQuery = true)
	void updateQuotationReceivedCount(String transId,String userId);

	@Modifying
	@Query(value = "update nimai_mm_transaction set quotation_received=\r\n" + 
			"case \r\n" + 
			"when quotation_received<=0 then 0\r\n" + 
			"when quotation_received>0 then\r\n" + 
			"quotation_received-1 \r\n" + 
			"END where transaction_id=(:transId) and user_id=(:userId)", nativeQuery = true)
	void updateQuotationReceivedCount1(String transId,String userId);

	@Modifying
	@Query(value = "update nimai_mm_transaction set quotation_received=quotation_received+1 where transaction_id=(:transId) and user_id=(:userId)", nativeQuery = true)
	void incrementQuotationReceived(String transId, String userId);

	@Modifying
	@Query(value = "UPDATE nimai_mm_transaction t SET t.quotation_received = (\r\n" + 
			"SELECT COUNT(*) FROM nimai_m_quotation q WHERE q.validity_date >= CURDATE() \r\n" + 
			"AND q.transaction_id = t.transaction_id AND q.quotation_status LIKE '%Placed') WHERE user_id=(:userId)", nativeQuery = true)
	void updateQuotationReceivedCountForQuoteExpValidity(String userId);

	@Query(value = "select lc_issuance_country from nimai_mm_transaction where transaction_id=(:transId)", nativeQuery = true)
	String getIssuingCountry(String transId);

	@Query(value = "select average_amount from nimai_m_display_features where country=(:lcCountry) and ccy=(:lcCurrency) and status='Active'", nativeQuery = true)
	Double getAvgAmouunt(String lcCountry,String lcCurrency);

	@Query(value = "select lc_currency from nimai_mm_transaction where transaction_id=(:transId)", nativeQuery = true)
	String getCurrency(String transId);

	@Query(value = "select quotation_received from nimai_mm_transaction where transaction_id=(:tid) and transaction_status='Active'", nativeQuery = true)
	int getTotalQuoteReceived(String tid);

	@Query(value = "select * from nimai_mm_transaction where transaction_id=(:tid) and user_id=(:userId) and (transaction_status='Accepted' or transaction_status='Expired')", nativeQuery = true)
	NimaiLCMaster getAcceptedORExpiredTrans(String tid, String userId);

	@Modifying
	@Query(value = "update nimai_mm_transaction set modified_date=now(), transaction_status='Cancelled', status_reason='Transaction Cancel' where transaction_id=(:transactionId) and user_id=(:userId)", nativeQuery = true)
	void updateTransactionStatusToCancel(String transactionId, String userId);

	@Query(value = "SELECT account_type from nimai_m_customer where userid=(:userid)", nativeQuery = true)
	String getAccountTypeByUserId(String userid);

	@Query(value = "SELECT account_source from nimai_m_customer where userid=(:userid)", nativeQuery = true)
	String getAccountSourceByUserId(String userid);

	@Modifying
	@Query(value = "update nimai_mm_transaction set transaction_flag=ifnull(transaction_flag,0)+1 where transaction_id=(:transactionId)", nativeQuery = true)
	void updateCounterAfterReopen(String transactionId);

	@Query(value = "SELECT transaction_flag from nimai_mm_transaction where transaction_id=(:transactionId)", nativeQuery = true)
	String getReopenCtr(String transactionId);

	@Query(value = "SELECT userid from nimai_m_customer where account_source=(:userId) or userid=(:userId)", nativeQuery = true)
	List<NimaiClient> getSubsidiaryList(String userId);

	@Query(value = "SELECT requirement_type from nimai_mm_transaction where transaction_id=(:transId)", nativeQuery = true)
	String getProductTypeByTransId(String transId);

	@Query(value = "SELECT confirmation_period from nimai_mm_transaction where transaction_id=(:transId)", nativeQuery = true)
	String getConfirmationPeriod(String transId);

	@Query(value = "SELECT discounting_period from nimai_mm_transaction where transaction_id=(:transId)", nativeQuery = true)
	String getDiscountingPeriod(String transId);

	@Query(value = "SELECT refinancing_period from nimai_mm_transaction where transaction_id=(:transId)", nativeQuery = true)
	String getRefinancingPeriod(String transId);

	@Query(value = "select annual_asset_value from nimai_m_savings_input where country_name=(:lcCountry) and currency=(:lcCurrency) ORDER BY id DESC LIMIT 1", nativeQuery = true)
	Double getAnnualAsset(String lcCountry, String lcCurrency);

	@Query(value = "select net_revenue from nimai_m_savings_input where country_name=(:lcCountry) and currency=(:lcCurrency) ORDER BY id DESC LIMIT 1", nativeQuery = true)
	Double getNetRevenue(String lcCountry, String lcCurrency);

	//@Query(value = "select avg_spread from nimai_m_savings_input where country_name=(:lcCountry) and currency=(:lcCurrency) ORDER BY id DESC LIMIT 1", nativeQuery = true)
	//Double getAvgSpread(String lcCountry, String lcCurrency);

	@Query(value = "SELECT lc_value from nimai_mm_transaction where transaction_id=(:transId)", nativeQuery = true)
	Double getLCValueByTransId(String transId);

	@Query(value = "SELECT transaction_id,requirement_type,lc_currency,inserted_date,modified_date,transaction_status,lc_issuance_country,branch_user_email from get_all_transaction where user_id=(:userid) and branch_user_email=(:emailid) and inserted_date>=(:fromDate) order by inserted_date desc", nativeQuery = true)
	List findTransactionByBranchEmailIdStartDate(@Param("userid") String userid,String emailid,@Param("fromDate") Date fromDate);

	@Query(value = "SELECT transaction_id,requirement_type,lc_currency,inserted_date,modified_date,transaction_status,lc_issuance_country,branch_user_email from get_all_transaction where user_id=(:userid) and branch_user_email=(:emailid) and inserted_date>=(:fromDate) and inserted_date<=(:toDate) order by inserted_date desc", nativeQuery = true)
	List findTransactionByBranchEmailIdStartDateEndDate(String userid, String emailid, Date fromDate, Date toDate);

	@Query(value = "SELECT transaction_flag from nimai_mm_transaction where transaction_id=(:transId)", nativeQuery = true)
	String findRejectionCount(String transId);
	
	@Query(value = "SELECT * from nimai_mm_transaction where transaction_status=(:status) and (user_id=(:userId) or user_id IN (select nmc.userid from nimai_m_customer nmc\r\n" + 
			"where nmc.account_source=(:userId)))", nativeQuery = true)
	List<NimaiLCMaster> findTransactionReportForCustByUserIdAndStatus(String userId,String status);
	
	@Query(value = "SELECT * from nimai_mm_transaction where transaction_status=(:status) and branch_user_email=(:branchEmailId)", nativeQuery = true)
	List<NimaiLCMaster> findTransactionReportForCustByUserIdAndStatusAndEmail(String status,String branchEmailId);
			
	
}
