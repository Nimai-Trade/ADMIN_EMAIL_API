package com.nimai.splan.repository;

import java.util.List;

import org.hibernate.query.NativeQuery;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.nimai.splan.model.NimaiMCustomer;
import com.nimai.splan.model.NimaiMSubscription;

public interface NimaiMSPlanRepository extends JpaRepository<NimaiMSubscription, String> {

	@Query(value="select * From nimai_m_subscription s INNER JOIN nimai_m_subscriptioncountry ncm on s.SUBSCRIPTION_ID=ncm.subscription_id where s.CUSTOMER_TYPE=:customerType AND ncm.subscription_country=:countrName AND s.STATUS = 'Active'",
			nativeQuery = true)
	List<NimaiMSubscription> findByCountry(String customerType, String countrName);
	
	@Query("From NimaiMSubscription s where s.subscriptionId=:subscriptionid" )
	NimaiMSubscription findDetailBySubscriptionId(String subscriptionid);

	@Query("From NimaiMSubscription s where s.customerType=:string AND s.sPLanCountry=:countryName AND s.status = 'Active'" )
	List<NimaiMSubscription> findByCustomerType(String string,String countryName);

	@Query(value="SELECT system_config_entity_value from nimai_system_config where system_config_entity='invoice_gst'", nativeQuery = true )
	Double getGSTValue();
	
	@Query(value="select nc.REGISTERED_COUNTRY from nimai_m_customer nc where nc.USERID=:userId", nativeQuery = true )
	String getBusinessCountry(String userId);
	
}
