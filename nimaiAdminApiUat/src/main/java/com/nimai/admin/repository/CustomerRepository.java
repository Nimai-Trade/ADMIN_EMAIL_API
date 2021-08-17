package com.nimai.admin.repository;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.ParameterMode;
import javax.persistence.StoredProcedureQuery;
import javax.persistence.Tuple;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.nimai.admin.model.NimaiFKyc;
import com.nimai.admin.model.NimaiFOwner;
import com.nimai.admin.model.NimaiMCustomer;
import com.nimai.admin.model.NimaiMRefer;
import com.nimai.admin.model.NimaiMmTransaction;
import com.nimai.admin.model.NimaiSubscriptionDetails;
import com.nimai.admin.model.NimaiSubscriptionVas;

@Repository
public interface CustomerRepository
		extends JpaRepository<NimaiMCustomer, String>, JpaSpecificationExecutor<NimaiMCustomer> {

	@Query("SELECT userid FROM NimaiMCustomer where lower(userid) like %:userid%")
	public List<String> userIdSearch(@Param("userid") String userid);

	@Query("SELECT emailAddress FROM NimaiMCustomer where lower(emailAddress) like %:emailAddress%")
	public List<String> emailIdSearch(@Param("emailAddress") String emailId);

	@Query("SELECT mobileNumber FROM NimaiMCustomer where lower(mobileNumber) like %:mobileNumber%")
	public List<String> mobileNumberSearch(@Param("mobileNumber") String mobileNo);

	@Query("SELECT companyName FROM NimaiMCustomer where lower(companyName) like %:companyName%")
	public List<String> companyNameSearch(@Param("companyName") String companyName);

	@Query("SELECT userid FROM NimaiMCustomer where emailAddress like %:emailAddress% or mobileNumber like %:mobileNumber% or countryName like %:companyName%")
	public List<String> getUserIdOnEmailMobileNumberCompanyName(@Param("emailAddress") String emailId,
			@Param("mobileNumber") String mobileNo, @Param("companyName") String companyName);

	public NimaiMCustomer findByUserid(String userid);

	// @Query(value= "select quotation_id from nimai_m_quotation where
	// transaction_id=(:transactionId) and userid=(:userId)", nativeQuery = true)
	// String getQuotationId(String transactionId, String userId);

	// @Query("SELECT ownerDesignation from NimaiFOwner where userid=:userid ")
	// public String designation(@Param("userid") String userid);

	// public NimaiFOwner fOwner(String ownerId);

	// Bashir R&D successfull
	public NimaiMCustomer findByUseridAndCountryNameIn(String userid, List<String> countryNames);

	@Query("SELECT bankName FROM NimaiMCustomer where userid =:userid")
	public String findBankName(@Param("userid") String userid);

	/**
	 * 
	 *
	 */
	@Query("SELECT userid FROM NimaiMCustomer where lower(userid) like %:userid% and userid like %:data%")
	public List<String> userIdDataSearch(@Param("userid") String userid, @Param("data") String data);

//	@Query("SELECT emailAddress FROM NimaiMCustomer where lower(emailAddress) like %:emailAddress% and userid like %:data%")
//	public List<String> emailIdDataSearch(@Param("emailAddress") String emailId, @Param("data") String data);

	@Query("SELECT emailAddress FROM NimaiMCustomer where lower(emailAddress) like %:emailAddress%")
	public List<String> emailIdDataSearch(@Param("emailAddress") String emailId);

//	@Query("SELECT mobileNumber FROM NimaiMCustomer where lower(mobileNumber) like %:mobileNumber% and userid like %:data%")
//	public List<String> mobileNumberDataSearch(@Param("mobileNumber") String mobileNo, @Param("data") String data);

	@Query("SELECT mobileNumber FROM NimaiMCustomer where lower(mobileNumber) like %:mobileNumber%")
	public List<String> mobileNumberDataSearch(@Param("mobileNumber") String mobileNo);

//	@Query("SELECT companyName FROM NimaiMCustomer where lower(companyName) like %:companyName% and userid like %:data%")
//	public List<String> companyNameDataSearch(@Param("companyName") String companyName, @Param("data") String data);

	@Query("SELECT companyName FROM NimaiMCustomer where lower(companyName) like %:companyName%")
	public List<String> companyNameDataSearch(@Param("companyName") String companyName);

	@Query("from NimaiMCustomer where lower(emailAddress) =:emailAddress")
	public NimaiMCustomer findByEmailAddress(@Param("emailAddress") String emailAddress);

	@Query("select kycStatus from NimaiMCustomer where lower(emailAddress) =:emailAddress")
	public String findKycByEmailAddress(@Param("emailAddress") String emailAddress);

//	@Query("SELECT bankName FROM NimaiMCustomer where lower(bankName) like %:bankName% and userid like %:data%")
//	public List<String> bankNameDataSearch(@Param("bankName") String bankName, @Param("data") String data);

	@Query("SELECT bankName FROM NimaiMCustomer where lower(bankName) like %:bankName%")
	public List<String> bankNameDataSearch(@Param("bankName") String bankName);

	@Query(value = "select c.userid,c.first_name,c.last_name,c.company_name,c.country_name,sd.subscription_name,\r\n"
			+ "sd.is_vas_applied,sd.status,sd.splan_start_date,sd.splan_end_date,(sd.lc_count-sd.lc_utilized_count)\r\n"
			+ "as bal_count from nimai_m_customer c LEFT join nimai_subscription_details sd on c.userid=sd.userid where c.country_name= :country_name and c.subscriber_type= :subscriber_type \r\n"
			+ "and c.BANK_TYPE= :bankType", nativeQuery = true)
	List<Object[]> getByCountryAndCustType(@Param("country_name") String countryName,
			@Param("subscriber_type") String subscriberType, @Param("bankType") String bankType);

	// bashir 16-9 // bank Transaction details

	@Query(value = "select q.bank_userid,q.mobile_number,q.email_address,q.inserted_date,q.bank_name,q.branch_name,q.country_name,\r\n"
			+ "t.transaction_id,t.requirement_type,t.lc_issuance_bank,t.lc_value,t.lc_currency,t.original_tenor_days,q.applicable_benchmark,q.confirmation_charges,\r\n"
			+ "q.discounting_charges,q.refinancing_charges,q.banker_accept_charges,\r\n"
			+ "q.conf_chgs_issuance_to_negot,q.conf_chgs_issuance_to_matur,q.negotiation_charges_fixed,q.negotiation_charges_perct,q.other_charges,q.minimum_transaction_charges,\r\n"
			+ "count(q.quotation_id) as total_quotes,q.quotation_id,t.validity from nimai_m_customer m inner join nimai_mm_transaction t on m.userid=t.user_id \r\n"
			+ "inner join nimai_m_quotation q on t.transaction_id=q.transaction_id  where q.inserted_date between :startDate and :endDate Group by t.transaction_id", nativeQuery = true)
	List<Tuple> getAllTransactionDetails(@Param("startDate") Date startDate, @Param("endDate") Date endDate);

	// bank Transaction Details User Id
	@Query(value = "select m.userid,q.mobile_number,q.email_address,q.bank_userid,\n"
			+ "			q.inserted_date,q.bank_name,q.branch_name,q.country_name, \n"
			+ "				t.transaction_id,t.requirement_type, \n"
			+ "				t.lc_issuance_bank,t.lc_value,t.lc_currency,t.original_tenor_days,q.applicable_benchmark, \n"
			+ "				q.confirmation_charges,q.discounting_charges,q.refinancing_charges,q.banker_accept_charges, \n"
			+ "				q.conf_chgs_issuance_to_negot,q.conf_chgs_issuance_to_matur,q.negotiation_charges_fixed,q.negotiation_charges_perct, \n"
			+ "				q.other_charges,q.minimum_transaction_charges, \n"
			+ "				count(q.quotation_id) as total_quotes,q.quotation_id,t.validity\n"
			+ "				from nimai_m_customer m inner join nimai_mm_transaction t on \n"
			+ "				m.userid=t.user_id\n" + "				inner join nimai_m_quotation q on  \n"
			+ "				t.transaction_id=q.transaction_id  \n" + "				where \n"
			+ "				 q.inserted_date between (:startDate) AND (:endDate)  \n"
			+ "				AND q.bank_userid=(:userid) GROUP by t.transaction_id", nativeQuery = true)
	List<Tuple> getAllTransactionDetailsUserId(@Param("startDate") String startDate, @Param("endDate") Date endDate,
			@Param("userid") String userid);

	// New User Reports
	@Query(value = "select m.userid as userid,m.subscriber_type as customer_type,m.company_name as customer_name,"
			+ "s.subscription_name as subs_plan,s.is_vas_applied as vas,s.GRAND_AMOUNT as fee,m.currency_code"
			+ " as ccy,m.mode_of_payment,m.payment_status,m.kyc_status,m.rm_id as rm from nimai_m_customer "
			+ "m left join nimai_subscription_details s on m.USERID=s.userid where m.KYC_STATUS='Pending' and"
			+ " m.inserted_date between :startDate and :endDate group by m.USERID ", nativeQuery = true)
	List<Tuple> getNewUserReports(@Param("startDate") Date startDate, @Param("endDate") Date endDate);

	// NewReports on Id
	@Query(value = "select m.userid as userid,m.subscriber_type as customer_type,m.company_name as customer_name,s.subscription_name "
			+ "as subs_plan,s.is_vas_applied as vas,s.GRAND_AMOUNT as fee,m.currency_code as ccy,m.mode_of_payment,m.payment_status,"
			+ "m.kyc_status,m.rm_id as rm from nimai_m_customer m left join nimai_subscription_details s on m.USERID=s.userid "
			+ "where m.KYC_STATUS='Pending' and m.INSERTED_DATE between :startDate and :endDate and m.USERID= :userid group by m.USERID", nativeQuery = true)
	public List<Tuple> getNewUserIdReports(@Param("startDate") Date startDate, @Param("endDate") Date endDate,
			@Param("userid") String userid);

	// PaymentAndSubscription
	@Query(value = "select m.userid,m.subscriber_type,m.company_name,m.mobile_number,m.landline,m.country_name,m.email_address\r\n"
			+ ",m.first_name,m.last_name,s.subscription_name,s.is_vas_applied,s.subscription_amount,\r\n"
			+ "m.currency_code,m.mode_of_payment,s.inserted_date,s.splan_start_date,s.splan_end_date\r\n"
			+ " from nimai_subscription_details s inner join nimai_m_customer m on s.userid=m.USERID where s.INSERTED_DATE between :dateFrom and :dateTo", nativeQuery = true)
	public List<Tuple> getPaymentSubReport(@Param("dateFrom") String dateFrom, @Param("dateTo") Date dateTo);

	@Query(value = "select m.userid,m.subscriber_type,m.company_name,m.mobile_number,m.landline,m.country_name,m.email_address\r\n"
			+ ",m.first_name,m.last_name,s.subscription_name,s.is_vas_applied,s.subscription_amount,\r\n"
			+ "m.currency_code,m.mode_of_payment,s.inserted_date,s.splan_start_date,s.splan_end_date\r\n"
			+ " from nimai_subscription_details s inner join nimai_m_customer m on s.userid=m.USERID where s.userid= :userid and s.INSERTED_DATE between :dateFrom and :dateTo", nativeQuery = true)
	public List<Tuple> getPaymentSubUserIdReport(@Param("dateFrom") String dateFrom, @Param("dateTo") Date dateTo,
			@Param("userid") String userid);

	@Query(value = "select m.userid,m.subscriber_type,m.mobile_number,m.email_address,m.inserted_date,t.transaction_id,t.applicant_name\r\n"
			+ ",t.applicant_country,t.bene_name,\r\n"
			+ "t.bene_country,t.bene_contact_person,t.bene_contact_person_email,t.bene_bank_country,t.bene_swift_code,t.bene_bank_name,\r\n"
			+ "t.lc_issuance_bank,t.lc_issuance_branch,t.swift_code,t.lc_issuance_country,t.requirement_type,t.lc_value,t.lc_currency,t.lc_issuing_date,\r\n"
			+ "t.last_shipment_date,t.negotiation_date,t.goods_type,t.usance_days,t.start_date,t.confirmation_period,t.original_tenor_days,\r\n"
			+ "t.refinancing_period,t.lc_maturity_date,t.last_bene_bank,t.last_bene_swift_code,t.last_bank_country,t.loading_port,\r\n"
			+ "t.loading_country,t.discharge_port,t.discharge_country,t.charges_type,t.validity,t.quotation_received\r\n"
			+ "from nimai_m_customer m left join  nimai_mm_transaction t on m.USERID=t.user_id group by m.USERID,t.transaction_id;", nativeQuery = true)
	public List<Tuple> getCustomerTransactionReport();

	@Query(value = "SELECT * from nimai_mm_transaction nm where \n"
			+ "            nm.inserted_date >= (:fromDate) AND\n"
			+ "        nm.inserted_date   <= (:toDate);", nativeQuery = true)
	public List<Tuple> getCustomerTransactionReportByDates(@Param("fromDate") String fromDate,
			@Param("toDate") String toDate);

//	,t.lc_pro_forma
	// --------------------DashBoard--------------//
//	@Query(value = "select c.REGISTERED_COUNTRY,\r\n"
//			+ "(select count(m.subscriber_type)from nimai_m_customer m where m.REGISTERED_COUNTRY=c.REGISTERED_COUNTRY)-((select count(u.subscriber_type)from nimai_m_customer u where u.REGISTERED_COUNTRY=c.REGISTERED_COUNTRY and u.BANK_TYPE='UNDERWRITER'))-(select count(n.subscriber_type)from nimai_m_customer n where n.REGISTERED_COUNTRY=c.REGISTERED_COUNTRY and n.SUBSCRIBER_TYPE='Referrer')as total_customers,\r\n"
//			+ "(select count(u.subscriber_type)from nimai_m_customer u where u.REGISTERED_COUNTRY=c.REGISTERED_COUNTRY and u.BANK_TYPE='UNDERWRITER') as total_underwriter,\r\n"
//			+ "(select count(t.transaction_id) from nimai_mm_transaction t where t.lc_issuance_country=c.REGISTERED_COUNTRY) as total_trxn,\r\n"
//			+ "(select COALESCE(SUM(tr.lc_value),0) from nimai_mm_transaction tr where tr.lc_issuance_country=c.REGISTERED_COUNTRY) as cumulative_lc_amount\r\n"
//			+ "from nimai_m_customer c where c.KYC_STATUS='Approved' AND (c.PAYMENT_STATUS='Approved' OR c.PAYMENT_STATUS='Success')"
//			+ "group by c.REGISTERED_COUNTRY  order by c.REGISTERED_COUNTRY ;", nativeQuery = true)
//	List<Tuple> getCountryDetailsAnalysis();
	
	
	
	@Query(value = "	select c.REGISTERED_COUNTRY,\n" + 
			"				(select count(m.subscriber_type)from nimai_m_customer m \n" + 
			"				where   m.REGISTERED_COUNTRY=c.REGISTERED_COUNTRY    \n" + 
			"				AND\n" + 
			"				 (m.SUBSCRIBER_TYPE='Customer'  OR m.SUBSCRIBER_TYPE='Referrer' OR(m.SUBSCRIBER_TYPE='Bank' \n" + 
			"				and m.BANK_TYPE='Customer')) and\n" + 
			"				m.KYC_STATUS='Approved' AND (m.PAYMENT_STATUS='Approved' OR m.PAYMENT_STATUS='Success'))as total_customers,\n" + 
			"			(select count(u.subscriber_type)from nimai_m_customer u \n" + 
			"			where u.REGISTERED_COUNTRY=c.REGISTERED_COUNTRY AND u.SUBSCRIBER_TYPE='Bank' and \n" + 
			"			u.BANK_TYPE='UNDERWRITER' and (u.PAYMENT_STATUS='Approved' OR u.PAYMENT_STATUS='Success')AND u.KYC_STATUS='Approved')\n" + 
			"			as total_underwriter,\n" + 
			"			(select count(t.transaction_id) from nimai_mm_transaction t \n" + 
			"			where t.lc_issuance_country=c.REGISTERED_COUNTRY) as total_trxn,\n" + 
			"			(select COALESCE(SUM(tr.lc_value),0) from nimai_mm_transaction tr\n" + 
			"			 where tr.lc_issuance_country=c.REGISTERED_COUNTRY) as cumulative_lc_amount\n" + 
			"				from nimai_m_customer c where\n" + 
			"		 c.KYC_STATUS='Approved' AND (c.PAYMENT_STATUS='Approved' OR c.PAYMENT_STATUS='Success')  \n" + 
			"		group by c.REGISTERED_COUNTRY order by c.REGISTERED_COUNTRY;", nativeQuery = true)
	List<Tuple> getCountryDetailsAnalysis();
	
	
	
	

//	@Query(value = "select c.REGISTERED_COUNTRY,\r\n"
//			+ "			(select count(m.subscriber_type)from nimai_m_customer m where m.REGISTERED_COUNTRY=c.REGISTERED_COUNTRY)-((select count(u.subscriber_type)from nimai_m_customer u where u.REGISTERED_COUNTRY=c.REGISTERED_COUNTRY and u.BANK_TYPE='UNDERWRITER'))-(select count(n.subscriber_type)from nimai_m_customer n where n.REGISTERED_COUNTRY=c.REGISTERED_COUNTRY and n.SUBSCRIBER_TYPE='Referrer')as total_customers,\r\n"
//			+ "			(select count(u.subscriber_type)from nimai_m_customer u where u.REGISTERED_COUNTRY=c.REGISTERED_COUNTRY and u.BANK_TYPE='UNDERWRITER') as total_underwriter,\r\n"
//			+ "			(select count(t.transaction_id) from nimai_mm_transaction t where t.lc_issuance_country=c.REGISTERED_COUNTRY) as total_trxn,\r\n"
//			+ "			(select COALESCE(SUM(tr.lc_value),0) from nimai_mm_transaction tr where tr.lc_issuance_country=c.REGISTERED_COUNTRY) as cumulative_lc_amount\r\n"
//			+ "			from nimai_m_customer c where c.REGISTERED_COUNTRY IN (:userCountry)"
//			+ " AND c.KYC_STATUS='Approved' AND (c.PAYMENT_STATUS='Approved' OR c.PAYMENT_STATUS='Success')  "
//			+ "group by c.REGISTERED_COUNTRY order by c.REGISTERED_COUNTRY;	", nativeQuery = true)
//	public List<Tuple> getCountryFilteredDetails(@Param("userCountry") List<String> userCountry);// mgmnt based on
//																									// country

	
	@Query(value = "	select c.REGISTERED_COUNTRY,\n" + 
			"				(select count(m.subscriber_type)from nimai_m_customer m \n" + 
			"				where   m.REGISTERED_COUNTRY=c.REGISTERED_COUNTRY    \n" + 
			"				AND\n" + 
			"				 (m.SUBSCRIBER_TYPE='Customer'  OR m.SUBSCRIBER_TYPE='Referrer' OR(m.SUBSCRIBER_TYPE='Bank' \n" + 
			"				and m.BANK_TYPE='Customer')) and\n" + 
			"				m.KYC_STATUS='Approved' AND (m.PAYMENT_STATUS='Approved' OR m.PAYMENT_STATUS='Success'))as total_customers,\n" + 
			"			(select count(u.subscriber_type)from nimai_m_customer u \n" + 
			"			where u.REGISTERED_COUNTRY=c.REGISTERED_COUNTRY AND u.SUBSCRIBER_TYPE='Bank' and \n" + 
			"			u.BANK_TYPE='UNDERWRITER' and (u.PAYMENT_STATUS='Approved' OR u.PAYMENT_STATUS='Success')AND u.KYC_STATUS='Approved')\n" + 
			"			as total_underwriter,\n" + 
			"			(select count(t.transaction_id) from nimai_mm_transaction t \n" + 
			"			where t.lc_issuance_country=c.REGISTERED_COUNTRY) as total_trxn,\n" + 
			"			(select COALESCE(SUM(tr.lc_value),0) from nimai_mm_transaction tr\n" + 
			"			 where tr.lc_issuance_country=c.REGISTERED_COUNTRY) as cumulative_lc_amount\n" + 
			"				from nimai_m_customer c where c.REGISTERED_COUNTRY IN (:userCountry)\n" + 
			"		AND c.KYC_STATUS='Approved' AND (c.PAYMENT_STATUS='Approved' OR c.PAYMENT_STATUS='Success')  \n" + 
			"		group by c.REGISTERED_COUNTRY order by c.REGISTERED_COUNTRY;", nativeQuery = true)
	public List<Tuple> getCountryFilteredDetails(@Param("userCountry") List<String> userCountry);// mgmnt based on
		
	
	@Procedure(name = "dashboardCount", outputParameterName = "result")
	int getDashboardCount(@Param("query_no") int query_no, @Param("subscriberType") String subsType,
			@Param("bankType") String bankType, @Param("status") String status, @Param("exp_day") Integer exp_day,
			@Param("dateFrom") String dateFrom, @Param("dateTo") String dateTo, @Param("cases") String cases,
			@Param("countryNames") String countryNames);

//-------------New Stat----------------

	@Query(value = "select MONTHNAME(m.INSERTED_DATE) as month,count(m.userid) as customers,\r\n"
			+ "COALESCE((select COALESCE(sum(su.subscription_amount),0) from nimai_subscription_details su inner join nimai_m_customer mn \r\n"
			+ "  on su.userid=mn.USERID where mn.SUBSCRIBER_TYPE=m.SUBSCRIBER_TYPE \r\n"
			+ "  and MONTHNAME(mn.INSERTED_DATE)=MONTHNAME(m.INSERTED_DATE))\r\n"
			+ "/(select count(c.userid) from nimai_m_customer c where c.is_splanpurchased='1' \r\n"
			+ "  and c.SUBSCRIBER_TYPE=m.SUBSCRIBER_TYPE\r\n"
			+ "  and MONTHNAME(c.INSERTED_DATE)=MONTHNAME(m.INSERTED_DATE)),0) as subscription_rate\r\n"
			+ " from nimai_m_customer m where YEAR(m.INSERTED_DATE)= :year and m.subscriber_type='Customer'\r\n"
			+ "group by MONTHNAME(m.INSERTED_DATE)\r\n"
			+ "ORDER BY DATE_FORMAT(m.INSERTED_DATE,\"%m-%d\") asc; ", nativeQuery = true)
	List<Tuple> getDashboardUserStat(@Param("year") String year);

	@Query(value = "select MONTHNAME(m.INSERTED_DATE) as month,count(m.userid) as customers,\r\n"
			+ "COALESCE((select sum(su.subscription_amount) from nimai_subscription_details su inner join nimai_m_customer mn \r\n"
			+ "on su.userid=mn.USERID where  mn.SUBSCRIBER_TYPE=m.SUBSCRIBER_TYPE and MONTHNAME(mn.INSERTED_DATE)=MONTHNAME(m.INSERTED_DATE))\r\n"
			+ "/(select count(c.userid) from nimai_m_customer c where c.is_splanpurchased='1' and c.SUBSCRIBER_TYPE=m.SUBSCRIBER_TYPE\r\n"
			+ "and MONTHNAME(c.INSERTED_DATE)=MONTHNAME(m.INSERTED_DATE)),0) as subscription_rate\r\n"
			+ "from nimai_m_customer m where YEAR(m.INSERTED_DATE)= :year and m.subscriber_type='Customer' \r\n"
			+ "and FIND_IN_SET(m.country_name,:userCountry)\r\n" + "group by MONTHNAME(m.INSERTED_DATE)\r\n"
			+ "ORDER BY DATE_FORMAT(m.INSERTED_DATE, \"%m-%d\") asc; ", nativeQuery = true)
	public List<Tuple> getDashboardCountryrUserStat(@Param("year") String year,
			@Param("userCountry") String userCountry);// for cust new u stat based on country of login user

	@Query(value = " select MONTHNAME(m.INSERTED_DATE) as month,count(m.userid) as customers,\r\n"
			+ "COALESCE((select sum(su.subscription_amount) from nimai_subscription_details su inner join nimai_m_customer mn  \r\n"
			+ "on su.userid=mn.USERID where  mn.SUBSCRIBER_TYPE=m.subscriber_type and mn.BANK_TYPE=m.bank_type and MONTHNAME(mn.INSERTED_DATE) like MONTHNAME(m.INSERTED_DATE) )\r\n"
			+ "/(select count(c.userid) from nimai_m_customer c where c.is_splanpurchased='1' and c.SUBSCRIBER_TYPE=m.SUBSCRIBER_TYPE and c.BANK_TYPE= 'Customer'\r\n"
			+ "and MONTHNAME(c.INSERTED_DATE)=MONTHNAME(m.INSERTED_DATE)),0) as subscription_rate\r\n"
			+ "from nimai_m_customer m\r\n"
			+ "where YEAR(m.INSERTED_DATE)= :year and m.subscriber_type='Bank' and m.bank_type=:bankType\r\n"
			+ "group by MONTHNAME(m.INSERTED_DATE)\r\n"
			+ "ORDER BY DATE_FORMAT(m.INSERTED_DATE,\"%m-%d\") asc;", nativeQuery = true)
	public List<Tuple> getDashboardBankStat(@Param("year") String year, @Param("bankType") String bankType);

	@Query(value = "select MONTHNAME(m.INSERTED_DATE) as month,count(m.userid) as customers,\r\n"
			+ "COALESCE((select sum(su.subscription_amount) from nimai_subscription_details su inner join nimai_m_customer mn\r\n"
			+ "on su.userid=mn.USERID where  mn.SUBSCRIBER_TYPE=m.subscriber_type and mn.BANK_TYPE=m.bank_type and MONTHNAME(mn.INSERTED_DATE) like MONTHNAME(m.INSERTED_DATE) )\r\n"
			+ "/(select count(c.userid) from nimai_m_customer c where c.is_splanpurchased='1' and c.SUBSCRIBER_TYPE=m.SUBSCRIBER_TYPE and c.BANK_TYPE= m.bank_type\r\n"
			+ "and MONTHNAME(c.INSERTED_DATE)=MONTHNAME(m.INSERTED_DATE)),0) as subscription_rate\r\n"
			+ "from nimai_m_customer m\r\n"
			+ "where YEAR(m.INSERTED_DATE)=:year and m.subscriber_type='Bank' and m.bank_type= :bankType\r\n"
			+ "and FIND_IN_SET(m.country_name, :userCountry)\r\n" + "group by MONTHNAME(m.INSERTED_DATE)\r\n"
			+ "ORDER BY DATE_FORMAT(m.INSERTED_DATE,\"%m-%d\") asc; ", nativeQuery = true)
	public List<Tuple> getDashboardBankCountryStat(@Param("year") String year, @Param("bankType") String bankType,
			@Param("userCountry") String userCountry);

//---------------------------------	

	@Query("SELECT userid FROM NimaiMCustomer where lower(userid) like %:userid% and countryName IN (:names)")
	public List<String> userIdSearchByCountry(@Param("userid") String userid, @Param("names") List<String> names);

	@Query("SELECT emailAddress FROM NimaiMCustomer where lower(emailAddress) like %:emailAddress% and countryName IN (:names)")
	public List<String> emailIdSearchByCountry(@Param("emailAddress") String emailId,
			@Param("names") List<String> names);

	@Query("SELECT mobileNumber FROM NimaiMCustomer where lower(mobileNumber) like %:mobileNumber% and countryName IN (:names)")
	public List<String> mobileNumberSearchByCountry(@Param("mobileNumber") String mobileNo,
			@Param("names") List<String> names);

	@Query("SELECT companyName FROM NimaiMCustomer where lower(companyName) like %:companyName% and countryName IN (:names)")
	public List<String> companyNameSearchByCountry(@Param("companyName") String companyName,
			@Param("names") List<String> names);

	/**  
	 * 
	 */
	@Query("SELECT userid FROM NimaiMCustomer where lower(userid) like %:userid% and userid like %:data% and countryName IN (:names)")
	public List<String> userIdDataSearchByCountry(@Param("userid") String userid, @Param("data") String data,
			@Param("names") List<String> names);

//	@Query("SELECT emailAddress FROM NimaiMCustomer where lower(emailAddress) like %:emailAddress% and userid like %:data% and countryName IN (:names)")
//	public List<String> emailIdDataSearchByCountry(@Param("emailAddress") String emailId, @Param("data") String data,
//			@Param("names") List<String> names);

	@Query("SELECT emailAddress FROM NimaiMCustomer where lower(emailAddress) like %:emailAddress% and countryName IN (:names)")
	public List<String> emailIdDataSearchByCountry(@Param("emailAddress") String emailId,
			@Param("names") List<String> names);

//	@Query("SELECT mobileNumber FROM NimaiMCustomer where lower(mobileNumber) like %:mobileNumber% and userid like %:data% and countryName IN (:names)")
//	public List<String> mobileNumberDataSearchByCountry(@Param("mobileNumber") String mobileNo,
//			@Param("data") String data, @Param("names") List<String> names);

	@Query("SELECT mobileNumber FROM NimaiMCustomer where lower(mobileNumber) like %:mobileNumber%  and countryName IN (:names)")
	public List<String> mobileNumberDataSearchByCountry(@Param("mobileNumber") String mobileNo,
			@Param("names") List<String> names);

//	@Query("SELECT companyName FROM NimaiMCustomer where lower(companyName) like %:companyName% and userid like %:data% and countryName IN (:names)")
//	public List<String> companyNameDataSearchByCountry(@Param("companyName") String companyName,
//			@Param("data") String data, @Param("names") List<String> names);

	@Query("SELECT companyName FROM NimaiMCustomer where lower(companyName) like %:companyName% and countryName IN (:names)")
	public List<String> companyNameDataSearchByCountry(@Param("companyName") String companyName,
			@Param("names") List<String> names);

//	@Query("SELECT bankName FROM NimaiMCustomer where lower(bankName) like %:bankName% and userid like %:data% and countryName IN (:names)")
//	public List<String> bankNameDataSearchByCountry(@Param("bankName") String bankName, @Param("data") String data,
//			@Param("names") List<String> names);

	@Query("SELECT bankName FROM NimaiMCustomer where lower(bankName) like %:bankName% and countryName IN (:names)")
	public List<String> bankNameDataSearchByCountry(@Param("bankName") String bankName,
			@Param("names") List<String> names);

//	public List<Tuple> getDashboardBankStat(@Param("years") String year, @Param("bankType") String bankType);

	@Query("FROM NimaiMCustomer where accountSource =:userid and accountType = 'SUBSIDIARY' ")
	public List<NimaiMCustomer> findSubsidiaryByUserId(@Param("userid") String userid);

	@Query("select country from NimaiMCurrency")
	public List<String> getCountryList();

	@Query("SELECT userid FROM NimaiMCustomer where lower(userid) like %:userid% and (userid like 'CU%' or userid like 'BC%')")
	public List<String> userIdSearchForCustomer(@Param("userid") String userid);

	@Query(value = "select c.country_name,\r\n"
			+ "(select count(m.subscriber_type)from nimai_m_customer m where m.country_name=c.country_name)-((select count(u.subscriber_type)from nimai_m_customer u where u.country_name=c.country_name and u.BANK_TYPE='UNDERWRITER'))-(select count(n.subscriber_type)from nimai_m_customer n where n.country_name=c.country_name and n.SUBSCRIBER_TYPE='Referrer')as total_customers,\r\n"
			+ "(select count(u.subscriber_type)from nimai_m_customer u where u.country_name=c.country_name and u.BANK_TYPE='UNDERWRITER') as total_underwriter,\r\n"
			+ "(select count(t.transaction_id) from nimai_mm_transaction t where t.lc_issuance_country=c.country_name) as total_trxn,\r\n"
			+ "(select COALESCE(SUM(tr.lc_value),0) from nimai_mm_transaction tr where tr.lc_issuance_country=c.country_name) as cumulative_lc_amount\r\n"
			+ "from nimai_m_customer c group by c.country_name order by c.country_name limit :limitRow offset :offsetData", nativeQuery = true)
	List<Tuple> getCountryDetailsAnalysis(@Param("limitRow") int limitRow, @Param("offsetData") int offsetData);

	@Query(value = "select c.country_name,\r\n"
			+ "			(select count(m.subscriber_type)from nimai_m_customer m where m.country_name=c.country_name)-((select count(u.subscriber_type)from nimai_m_customer u where u.country_name=c.country_name and u.BANK_TYPE='UNDERWRITER'))-(select count(n.subscriber_type)from nimai_m_customer n where n.country_name=c.country_name and n.SUBSCRIBER_TYPE='Referrer')as total_customers,\r\n"
			+ "			(select count(u.subscriber_type)from nimai_m_customer u where u.country_name=c.country_name and u.BANK_TYPE='UNDERWRITER') as total_underwriter,\r\n"
			+ "			(select count(t.transaction_id) from nimai_mm_transaction t where t.lc_issuance_country=c.country_name) as total_trxn,\r\n"
			+ "			(select COALESCE(SUM(tr.lc_value),0) from nimai_mm_transaction tr where tr.lc_issuance_country=c.country_name) as cumulative_lc_amount\r\n"
			+ "			from nimai_m_customer c where FIND_IN_SET(c.country_name, :userCountry) group by c.country_name order by c.country_name limit :limitRow offset :offsetData", nativeQuery = true)
	public List<Tuple> getCountryFilteredDetails(@Param("userCountry") String userCountry,
			@Param("limitRow") int limitRow, @Param("offsetData") int offsetData);// mgmnt based on country

	@Query("FROM NimaiMCustomer where accountSource =:userid and accountType = 'BANKUSER' ")
	public List<NimaiMCustomer> findAdditionalUserByUserId(@Param("userid") String userid);

	@Query(value = "SELECT * from nimai_mm_transaction nm where \n"
			+ "            nm.inserted_date >= (:fromDate) AND\n"
			+ "        nm.inserted_date   <= (:toDate) AND nm.user_id=(:userId);", nativeQuery = true)
	public List<NimaiMmTransaction> findByUsrIdDates(@Param("userId") String userId, @Param("fromDate") String fromDate,
			@Param("toDate") String toDate);

	@Query(value = "SELECT * from nimai_mm_transaction nm where \n"
			+ "            nm.inserted_date >= (:fromDate) AND\n"
			+ "        nm.inserted_date   <= (:toDate);", nativeQuery = true)
	public List<NimaiMmTransaction> getcuTrDetailsByDates(@Param("fromDate") String fromDate,
			@Param("toDate") String toDate);

	@Query(value = "SELECT * from nimai_subscription_details nm where \n"
			+ "            nm.inserted_date >= (:fromDate) AND\n"
			+ "        nm.inserted_date   <= (:toDate);", nativeQuery = true)
	public List<NimaiSubscriptionDetails> getCustomerDetails(@Param("fromDate") String fromDate,
			@Param("toDate") String toDate);

	@Query(value = "from NimaiSubscriptionDetails nm where \n" + "            nm.inserted_date >= (:fromDate) AND\n"
			+ "        nm.inserted_date   <= (:toDate);", nativeQuery = true)
	public List<NimaiSubscriptionDetails> getCustomerDetail(@Param("fromDate") Date fromDate,
			@Param("toDate") Date toDate);

	@Query(value = "from nimai_subscription_details nm where \n" + "            nm.inserted_date >= (:fromDate) AND\n"
			+ "        nm.inserted_date   <= (:toDate) AND nm.userid=(:userId)", nativeQuery = true)
	public List<NimaiSubscriptionDetails> getCustomerDetailsByUserId(@Param("fromDate") String fromDate,
			@Param("toDate") String toDate, @Param("userId") String userId);

	@Query(value = "SELECT * from nimai_m_customer m where\n" + "            m.INSERTED_DATE >= (:fromDate) AND\n"
			+ "        m.INSERTED_DATE   <= (:toDate) AND \n"
			+ "		  m.ACCOUNT_SOURCE!='WEBSITE' ;", nativeQuery = true)
	public List<NimaiMCustomer> findByDates(@Param("fromDate") String dateFrom, @Param("toDate") String dateTo);

	@Query(value = "SELECT COUNT(nc.USERID) AS approvedReferrence FROM nimai_m_customer nc \n"
			+ " WHERE nc.ACCOUNT_SOURCE=(:userId) AND nc.KYC_STATUS='Approved'", nativeQuery = true)
	public int getApprovedReferrence(@Param("userId") String userId);

	@Query(value = "SELECT COUNT(nc.USERID) AS totalReferences FROM nimai_m_customer nc\n"
			+ "WHERE nc.ACCOUNT_SOURCE=(:userid)", nativeQuery = true)
	public int getTotareference(@Param("userid") String userid);

	@Query(value = "SELECT COUNT(nc.USERID) AS pendingReference FROM nimai_m_customer nc\n"
			+ "WHERE nc.ACCOUNT_SOURCE=(:userid) AND nc.KYC_STATUS='Pending';", nativeQuery = true)
	public int getpendingReference(@Param("userid") String userid);

	@Query(value = "SELECT COUNT(nc.USERID) AS rejectedReference FROM nimai_m_customer nc\n"
			+ " WHERE nc.ACCOUNT_SOURCE=(:userid) AND nc.KYC_STATUS='Rejected';", nativeQuery = true)
	public int getRejectedReference(@Param("userid") String userid);

	@Query(value = " SELECT sum(nd.SUBSCRIPTION_AMOUNT)*0.07 AS Earning FROM nimai_m_customer nc\n"
			+ "INNER JOIN nimai_subscription_details \n"
			+ "		  nd WHERE nc.ACCOUNT_SOURCE=(:userid);", nativeQuery = true)
	public Double getEarning(@Param("userid") String userid);

	/*
	 * @Query(
	 * value="SELECT * from nimai_m_customer nc INNER JOIN nimai_subscription_details nd \n"
	 * + " ON nc.USERID=nd.userid \n" +
	 * "  where nc.REGISTERED_COUNTRY In(:country) \n" +
	 * "		and nc.MODE_OF_PAYMENT='Wire' and nc.SUBSCRIBER_TYPE!='REFERRER' \n"
	 * +
	 * "		and nc.PAYMENT_STATUS='Maker Approved'	and nd.`STATUS`='ACTIVE' AND nc.USERID=nd.userid"
	 * ,nativeQuery=true)
	 */
	@Query(value = "SELECT * from nimai_m_customer nc INNER JOIN nimai_subscription_details nd  \n"
			+ "			 ON nc.USERID=nd.userid  \n" + "			  where nc.REGISTERED_COUNTRY In(:country)  \n"
			+ "					and nc.MODE_OF_PAYMENT='Wire' and nc.SUBSCRIBER_TYPE!='REFERRER'  \n"
			+ "					and nc.PAYMENT_STATUS='Maker Approved'	and nd.`STATUS`='ACTIVE' AND nc.USERID=nd.userid", countQuery = "SELECT count(*) from nimai_m_customer nc INNER JOIN nimai_subscription_details nd  \n"
					+ " ON nc.USERID=nd.userid  \n" + " where nc.REGISTERED_COUNTRY In(:country)  \n"
					+ "	and nc.MODE_OF_PAYMENT='Wire' and nc.SUBSCRIBER_TYPE!='REFERRER'  \n"
					+ "	and nc.PAYMENT_STATUS='Maker Approved'	and nd.`STATUS`='ACTIVE' AND nc.USERID=nd.userid", nativeQuery = true)
	public Page<NimaiMCustomer> getListByCountryname(@Param("country") String country, Pageable pageable);

	@Query(value = "select * from nimai_m_customer k INNER JOIN nimai_subscription_details nd ON k.USERID=nd.userid  where k.PAYMENT_STATUS='Maker Approved' "
			+ "and k.MODE_OF_PAYMENT='Wire' " + "and k.SUBSCRIBER_TYPE!='REFERRER' and nd.`STATUS`='ACTIVE' "
			+ "and k.REGISTERED_COUNTRY IN (:countries)  ", nativeQuery = true)
	Page<NimaiMCustomer> findMakerApprovedPaymentDetails(@Param("countries") List<String> countries, Pageable pageable);

	@Query(value = "select * from nimai_m_customer k INNER JOIN nimai_subscription_details nd ON k.USERID=nd.userid where k.PAYMENT_STATUS='Maker Approved' "
			+ "and k.MODE_OF_PAYMENT='Wire' and nd.`STATUS`='ACTIVE' "
			+ "and k.SUBSCRIBER_TYPE!='REFERRER'   ", nativeQuery = true)
	Page<NimaiMCustomer> findAllMakerApprovedPaymentDetails(Pageable pageable);

	@Query(value = "	select distinct count(t.transaction_id)  from nimai_mm_transaction "
			+ "t inner join nimai_m_customer c \n" + "on c.USERID=t.user_id\n"
			+ "where  t.transaction_status='Expired' and t.validity between DATE_SUB(NOW(), INTERVAL 7 DAY) and NOW() \n"
			+ "AND c.REGISTERED_COUNTRY IN (:value)\n"
			+ "and (c.SUBSCRIBER_TYPE='Customer' or c.BANK_TYPE='Customer');", nativeQuery = true)
	public int getDashboardCountByCountryWise(List<String> value);

	@Query(value = "select distinct count(t.transaction_id)  from nimai_mm_transaction t inner join nimai_m_customer c \n"
			+ "on c.USERID=t.user_id\n"
			+ "where  t.transaction_status='Rejected' and t.inserted_date between DATE_SUB(NOW(), INTERVAL 7 DAY) and NOW() \n"
			+ "AND c.REGISTERED_COUNTRY IN (:value)\n"
			+ "and (c.SUBSCRIBER_TYPE='Customer' or c.BANK_TYPE='Customer');", nativeQuery = true)
	public int getDashboardRejectedCountByCountryWise(List<String> value);

}
