package com.nimai.email.repository;


import java.sql.Date;

import java.util.List;
import java.util.Map;

import javax.persistence.Tuple;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import com.nimai.email.entity.NimaiSubscriptionDetails;



@Repository
public interface SubscriptionDetailsRepository
		extends JpaRepository<NimaiSubscriptionDetails, Integer>, JpaSpecificationExecutor<NimaiSubscriptionDetails> {



	@Query(value = "SELECT nc.USERID,nc.EMAIL_ADDRESS,ns.SUBSCRIPTION_ID,ns.SUBSCRIPTION_AMOUNT,\n" + 
			"ns.SUBSCRIPTION_NAME,nc.COMPANY_NAME,\n" + 
			"ns.`STATUS`,ns.SPLAN_END_DATE,nc.ACCOUNT_TYPE,nc.ACCOUNT_SOURCE\n" + 
			" FROM nimai_m_customer nc INNER JOIN nimai_subscription_details ns\n" + 
			"ON nc.USERID=ns.userid WHERE nc.ACCOUNT_TYPE='REFER' AND    \n" + 
			"nc.KYC_STATUS='Approved'\n" + 
			"AND nc.ACCOUNT_SOURCE!='WEBSITE' AND ns.`STATUS`='ACTIVE' AND \n" + 
			"DATE_ADD(NOW(), INTERVAL 30 DAY)>= ns.SPLAN_END_DATE", nativeQuery = true)
	List<Tuple> getafter30DaysReferSplanDetils();



}