package com.nimai.admin.repository;

import java.math.BigInteger;
import java.sql.Date;
import java.util.List;

import javax.persistence.Tuple;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.nimai.admin.model.NimaiMCustomer;
import com.nimai.admin.model.NimaiMmTransaction;
import com.nimai.admin.model.NimaiSubscriptionDetails;

@Repository
public interface TransactionsRepository
		extends JpaRepository<NimaiMmTransaction, String>, JpaSpecificationExecutor<NimaiMmTransaction> {

	@Query("SELECT userId FROM NimaiMmTransaction where userId like %:userId%")
	public List<String> userIdSearch(@Param("userId") String userId);

	@Query("SELECT userId FROM NimaiMmTransaction where userId like %:userId%")
	public List<String> countrySearch(String userId);
	
	@Query(value="SELECT * from nimai_mm_transaction nm where \n" + 
			"            nm.inserted_date >= (:fromDate) AND\n" + 
			"        nm.inserted_date   <= (:toDate) AND nm.user_id=(:userId);",nativeQuery=true)
	public List<Tuple> findByUsrIdDates(@Param("userId")String userId,@Param("fromDate") String fromDate,
			@Param("toDate") Date toDate);

	@Query(value="SELECT * from nimai_mm_transaction nm where \n" + 
	"            nm.inserted_date >= (:fromDate) AND\n" + 
	"        nm.inserted_date   <= (:toDate) ",nativeQuery=true)
public List<Tuple> findByDates(@Param("fromDate") String fromDate,
	@Param("toDate") Date toDate);
	
	@Query(value="from NimaiSubscriptionDetails nm where \n" + 
			"            nm.insertedDate >= (:fromDate) AND\n" + 
			"        nm.insertedDate   <= (:toDate)")
	public List<NimaiSubscriptionDetails> getCustomerDetail(@Param("fromDate") java.util.Date fromDate,@Param("toDate") java.util.Date toDate);

	long countByUserId(String userId);

	// bashir17
	public List<NimaiMmTransaction> findByUserId(NimaiMCustomer userId);

	public List<NimaiMmTransaction> findByLcIssuanceCountryAndLcCurrency(String lcIssuanceCountry, String lcCurrency);

	// Txn report details
	@Query(value = "select t.transaction_id as txnId,t.inserted_date as date_time,"
			+ "t.applicant_name as applicant,t.applicant_country"
			+ " as a_country,t.bene_name as beneficiary,t.bene_country as "
			+ "b_country,t.requirement_type as requirement,"
			+ "t.lc_value as amount,t.lc_currency as ccy,t.applicant_name as customer,t.validity,DATEDIFF(t.validity,CURDATE()) "
			+ "as expires_in,m.rm_id as rm from nimai_mm_transaction t "
			+ "inner join nimai_m_customer m on t.user_id=m.USERID  where t.inserted_date between :startDate and :endDate", nativeQuery = true)
	public List<Tuple> getAllTransDetails(@Param("startDate") Date startDate, @Param("endDate") Date endDate);

	// Txn report details on userId
	@Query(value = "select t.transaction_id as txnId,t.inserted_date as date_time,t.applicant_name as "
			+ "applicant,t.applicant_country as a_country,t.bene_name as beneficiary,"
			+ "t.bene_country as b_country,t.requirement_type as requirement,"
			+ "t.lc_value as amount,t.lc_currency as ccy,t.applicant_name as customer,t.validity,DATEDIFF(t.validity,CURDATE()) "
			+ "as expires_in,m.rm_id as rm from nimai_mm_transaction t inner join nimai_m_customer m on t.user_id=m.USERID  where t.inserted_date between :startDate and :endDate and t.user_id= :userid", nativeQuery = true)
	public List<Tuple> getAllTransDetailsUserId(@Param("startDate") Date startDate, @Param("endDate") Date endDate,
			@Param("userid") String userid);

	// Product Requirement Report
	@Query(value = "select t.lc_issuance_country,t.lc_currency,t.requirement_type,count(distinct t.transaction_id) as txn_placed,sum(t.lc_value) as lc_value,\r\n"
			+ "count(q.quotation_id) as total_quotes,sum(q.total_quote_value) as quote_value from nimai_mm_transaction t left join nimai_m_quotation q on t.transaction_id\r\n"
			+ "=q.transaction_id where t.lc_issuing_date between :startDate and :endDate group by t.requirement_type,t.lc_currency,t.lc_issuance_country order by t.lc_issuance_country asc", nativeQuery = true)
	public List<Tuple> getProductReqRep(@Param("startDate") Date startDate, @Param("endDate") Date endDate);

	// ---------->>>>>>>>>>>>>>>>>>>>>>>>>>>>
	// for dashboard
	@Query(value = "select Monthname(t.inserted_date)as month,count(t.transaction_id) as transaction_count ,\r\n"
			+ "(select  day(SUBDATE(r.inserted_date, weekday(r.inserted_date)))  from nimai_mm_transaction r \r\n"
			+ "where r.inserted_date=t.inserted_date group by week(inserted_date))as day  , \r\n"
			+ "(select count(q.quotation_id) from nimai_m_quotation q where q.transaction_id=t.transaction_id) as total_quotes\r\n"
			+ "from nimai_mm_transaction t where t.inserted_date between :dateFrom and :dateTo\r\n"
			+ " group by Monthname(t.inserted_date),week(t.inserted_date) order by DATE_FORMAT(t.INSERTED_DATE, \"%m-%d\") asc;  \r\n"
			+ "", nativeQuery = true)
	public List<Tuple> getAvgQuote(@Param("dateFrom") Date dateFrom, @Param("dateTo") Date dateTo);

	@Query(value = "select Monthname(t.inserted_date)as month,count(t.transaction_id) as transaction_count ,\r\n"
			+ "(select  day(SUBDATE(r.inserted_date, weekday(r.inserted_date)))  from nimai_mm_transaction r\r\n"
			+ "where r.inserted_date=t.inserted_date group by week(inserted_date))as day  ,\r\n"
			+ "(select count(q.quotation_id) from nimai_m_quotation q where q.transaction_id=t.transaction_id) as total_quotes\r\n"
			+ "from nimai_mm_transaction t where t.inserted_date between :dateFrom and :dateTo \r\n"
			+ "and FIND_IN_SET(t.lc_issuance_country, :userCountry)\r\n"
			+ "group by Monthname(t.inserted_date),week(t.inserted_date) order by DATE_FORMAT(t.INSERTED_DATE, \"%m-%d\") asc;", nativeQuery = true)
	public List<Tuple> getAvgCountryQuote(@Param("dateFrom") Date dateFrom, @Param("dateTo") Date dateTo,
			@Param("userCountry") String userCountry);

	// --------------------Bank Avg Quotes------------------
	@Query(value = "select Monthname(t.inserted_date)as month,count(t.transaction_id) as transaction_count ,\r\n"
			+ "(select  day(SUBDATE(r.inserted_date, weekday(r.inserted_date)))  from nimai_mm_transaction r\r\n"
			+ "where r.inserted_date=t.inserted_date group by week(inserted_date))as day  ,\r\n"
			+ "(select count(q.quotation_id) from nimai_m_quotation q where q.transaction_id=t.transaction_id) as total_quotes\r\n"
			+ "from nimai_mm_transaction t inner join nimai_m_customer m on t.user_id=m.USERID\r\n"
			+ " where t.inserted_date between :dateFrom and :dateTo\r\n"
			+ "and m.SUBSCRIBER_TYPE='Bank' and m.RM_ID= :rmId and m.RM_STATUS='Active'\r\n"
			+ "group by Monthname(t.inserted_date),week(t.inserted_date) order by DATE_FORMAT(t.INSERTED_DATE, \"%m-%d\") asc;", nativeQuery = true)
	public List<Tuple> getAvgBankQuote(@Param("dateFrom") Date dateFrom, @Param("dateTo") Date dateTo,
			@Param("rmId") String rmId);// bank avg quotes

	@Query(value = "SELECT MONTHNAME(t.inserted_date) as month,day(SUBDATE(t.inserted_date, weekday(t.inserted_date))) as weekDay,count(t.transaction_id) as trxn_count,COALESCE(sum(t.lc_value),0) as cumulative_amount\r\n"
			+ "FROM nimai_mm_transaction t inner join nimai_m_customer m on t.user_id=m.USERID where m.SUBSCRIBER_TYPE= :subscriberType and t.inserted_date between :dateFrom and :dateTo \r\n"
			+ "and m.RM_ID= :rmId and m.RM_STATUS='Active' group by Week(t.inserted_date),Month(t.inserted_date)\r\n"
			+ "order by DATE_FORMAT(t.inserted_date,\"%m-%d\") asc;", nativeQuery = true)
	public List<Tuple> getCumulativeCustTrxn(@Param("subscriberType") String subscriberType,
			@Param("dateFrom") Date dateFrom, @Param("dateTo") Date dateTo, @Param("rmId") String rmId);

	@Query(value = "SELECT MONTHNAME(t.inserted_date) as month,day(SUBDATE(t.inserted_date, weekday(t.inserted_date))) as weekDay,count(t.transaction_id) as trxn_count,COALESCE(sum(t.lc_value),0) as cumulative_amount\r\n"
			+ "FROM nimai_mm_transaction t inner join nimai_m_customer m on t.user_id=m.USERID where m.SUBSCRIBER_TYPE= :subscriberType and m.bank_type='Customer' and t.inserted_date between :dateFrom and :dateTo \r\n"
			+ "and m.RM_ID= :rmId and m.RM_STATUS='Active' group by Week(t.inserted_date),Month(t.inserted_date)\r\n"
			+ "order by DATE_FORMAT(t.inserted_date,\"%m-%d\") asc;", nativeQuery = true)
	public List<Tuple> getCumulativeBankTrxn(@Param("subscriberType") String subscriberType,
			@Param("dateFrom") Date dateFrom, @Param("dateTo") Date dateTo, @Param("rmId") String rmId);

	@Query(value = "SELECT MONTHNAME(t.inserted_date) as month,day(SUBDATE(t.inserted_date, weekday(t.inserted_date))) as weekDay,count(t.transaction_id) as trxn_count,COALESCE(sum(t.lc_value),0) as cumulative_amount \r\n"
			+ "FROM nimai_mm_transaction t inner join nimai_m_customer m on t.user_id=m.USERID where\r\n"
			+ "t.inserted_date between :dateFrom and :dateTo group by Week(t.inserted_date),Month(t.inserted_date)\r\n"
			+ "order by DATE_FORMAT(t.inserted_date,\"%m-%d\") asc;", nativeQuery = true)
	public List<Tuple> getGenCumulativeTrxn(@Param("dateFrom") Date dateFrom, @Param("dateTo") Date dateTo);

	@Query(value = "SELECT MONTHNAME(t.inserted_date) as month,day(SUBDATE(t.inserted_date, weekday(t.inserted_date))) as weekDay,count(t.transaction_id) as trxn_count,COALESCE(sum(t.lc_value),0) as cumulative_amount \r\n"
			+ "FROM nimai_mm_transaction t inner join nimai_m_customer m on t.user_id=m.USERID where FIND_IN_SET(m.COUNTRY_NAME, :userCountry) and\r\n"
			+ "t.inserted_date between :dateFrom and :dateTo group by Week(t.inserted_date),Month(t.inserted_date)\r\n"
			+ "order by DATE_FORMAT(t.inserted_date,\"%m-%d\") asc;", nativeQuery = true)
	public List<Tuple> getGenCumulativeCountryTrxn(@Param("dateFrom") Date dateFrom, @Param("dateTo") Date dateTo,
			@Param("userCountry") String userCountry);// general trxn based on country

	@Query(value = "select \r\n"
			+ "MONTHNAME(t.inserted_date) as month,day(SUBDATE(t.inserted_date, weekday(t.inserted_date))) as day,\r\n"
			+ "t.requirement_type ,count(t.transaction_id) as transactions from nimai_mm_transaction t inner join nimai_m_customer m on\r\n"
			+ "m.USERID=t.user_id where m.RM_ID= :rmId and m.RM_STATUS='Active' and t.inserted_date between :dateFrom and :dateTo\r\n"
			+ "and m.SUBSCRIBER_TYPE='Bank' and m.BANK_TYPE= :bankType\r\n"
			+ "group by requirement_type ;", nativeQuery = true)
	public List<Tuple> getBankTransComparision(@Param("bankType") String bankType, @Param("dateFrom") Date dateFrom,
			@Param("dateTo") Date dateTo, @Param("rmId") String rmId);

	@Query(value = "SELECT MONTHNAME(t.inserted_date) as 'Month',day(SUBDATE(t.inserted_date, weekday(t.inserted_date))) as day,\r\n" + 
			"COUNT(CASE WHEN t.requirement_type='Confirmation' THEN t.transaction_id ELSE NULL END) AS 'Confirmation',\r\n" + 
			"COUNT(CASE WHEN t.requirement_type='Discounting' THEN t.transaction_id ELSE NULL END) AS 'Discounting',\r\n" + 
			"COUNT(CASE WHEN t.requirement_type='Refinance' THEN t.transaction_id ELSE NULL END) AS 'Refinance',\r\n" + 
			"COUNT(CASE WHEN t.requirement_type='ConfirmAndDiscount' THEN t.transaction_id ELSE NULL END) AS 'Confirm And Discount',\r\n" + 
			"COUNT(CASE WHEN t.requirement_type='Banker' THEN t.transaction_id ELSE NULL END) AS 'Banker'\r\n" + 
			"FROM nimai_mm_transaction t, nimai_m_customer c, nimai_m_employee e\r\n" + 
			"where \r\n" + 
			"e.EMP_CODE = c.RM_ID and\r\n" + 
			"c.RM_ID = :rmId and c.RM_STATUS='Active'\r\n" + 
			"and t.user_id in (select u.USERID from nimai_m_customer u where u.RM_ID = :rmId and u.rm_status='Active') \r\n" + 
			"AND t.inserted_date between :dateFrom and :dateTo\r\n" + 
			"GROUP BY Week(t.inserted_date),MONTHNAME(t.inserted_date) Order by t.inserted_date asc;", nativeQuery = true)
	public List<Tuple> getCustTransComparision(@Param("dateFrom") Date dateFrom, @Param("dateTo") Date dateTo,
			@Param("rmId") String rmId);

	@Query(value="SELECT * from nimai_mm_transaction nm where \n" + 
			"            nm.inserted_date >= (:dateFrom) AND  \n" + 
			"        nm.inserted_date   <= (:dateTo) group by  nm.lc_currency",nativeQuery=true)
	public List<NimaiMmTransaction> findCountryWiseDataByDates(@Param("dateFrom")String dateFrom,@Param("dateTo") String dateTo);

	@Query(value="SELECT COUNT(nc.lc_issuance_country) AS txnCount FROM nimai_mm_transaction nc \n" + 
			" WHERE nc.lc_issuance_country=(:lcIssuanceCountry) and nc.lc_currency=(:lcCurrency)",nativeQuery=true)
	public Integer getTxnCount(@Param("lcIssuanceCountry")String lcIssuanceCountry,@Param("lcCurrency")String lcCurrency);
	
	
	@Query(value="SELECT sum(nc.lc_value) AS txnCount FROM nimai_mm_transaction nc\n" + 
			" WHERE nc.lc_issuance_country=(:lcIssuanceCountry) and nc.lc_currency=(:lcCurrency)",nativeQuery=true)
	public Double getcumulativeTxnAMount(@Param("lcIssuanceCountry")String lcIssuanceCountry,@Param("lcCurrency")String lcCurrency);
	
	
	@Query(value="SELECT COUNT(nc.transaction_status) AS txnAccepted FROM nimai_mm_transaction nc\n" + 
			"WHERE nc.lc_issuance_country=(:lcIssuanceCountry) and  nc.transaction_status=('Active') and nc.lc_currency=(:lcCurrency)",nativeQuery=true)
	public Integer gettxnAccepted(@Param("lcIssuanceCountry")String lcIssuanceCountry,@Param("lcCurrency")String lcCurrency);

	@Query(value="SELECT COUNT(nc.transaction_status) AS txnAccepted FROM nimai_mm_transaction nc\n" + 
			"WHERE nc.lc_issuance_country=(:lcIssuanceCountry) and nc.transaction_status=('Rejected') and nc.lc_currency=(:lcCurrency)",nativeQuery=true)
	public Integer gettxnRejected(@Param("lcIssuanceCountry")String lcIssuanceCountry,@Param("lcCurrency")String lcCurrency);
	
	@Query(value="SELECT COUNT(nc.transaction_status) AS txnAccepted FROM nimai_mm_transaction nc\n" + 
			"WHERE nc.lc_issuance_country=(:lcIssuanceCountry) and nc.transaction_status=('Expired') and nc.lc_currency=(:lcCurrency)",nativeQuery=true)
	public Integer gettxnExpired(@Param("lcIssuanceCountry")String lcIssuanceCountry,@Param("lcCurrency")String lcCurrency);


	@Query(value="SELECT COUNT(nc.transaction_status) AS txnAccepted FROM nimai_mm_transaction nc\n" + 
			"WHERE nc.lc_issuance_country=(:lcIssuanceCountry) and nc.transaction_status=('Closed') and nc.lc_currency=(:lcCurrency)",nativeQuery=true)
	public Integer gettxnclosedTxn(@Param("lcIssuanceCountry")String lcIssuanceCountry,@Param("lcCurrency")String lcCurrency);



}

