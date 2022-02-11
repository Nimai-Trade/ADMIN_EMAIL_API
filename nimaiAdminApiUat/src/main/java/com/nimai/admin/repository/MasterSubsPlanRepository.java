package com.nimai.admin.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.nimai.admin.model.NimaiMSubscriptionPlan;
import com.nimai.admin.model.NimaiMVas;
import com.nimai.admin.payload.SubscriptionMPlanResponse;

@Repository
public interface MasterSubsPlanRepository
		extends JpaRepository<NimaiMSubscriptionPlan, Integer>, JpaSpecificationExecutor<NimaiMSubscriptionPlan> {

	@Query("Select count(v) FROM NimaiMSubscriptionPlan v where v.countryName= :countryName and v.customerType= :customerType and v.status='Approved'")
	int checkAvailability(@Param("countryName") String countryName, @Param("customerType") String customerType);

	@Modifying
	@Query("update NimaiMSubscriptionPlan v set v.modifiedBy= :modifiedBy,v.modifiedDate= :modifiedDate,v.status='Deactivated' where v.subscriptionPlanId= :subscriptionPlanId and v.status='Approved'")
	int deactivateSubsPlan(@Param("subscriptionPlanId") int subscriptionPlanId, @Param("modifiedBy") String modifiedBy,
			@Param("modifiedDate") Date modifiedDate);

	@Modifying
	@Query("update NimaiMSubscriptionPlan n set n.status= :status, n.modifiedBy= :modifiedBy, n.modifiedDate= :modifiedDate where n.subscriptionPlanId= :subscriptionPlanId")
	int updateTemp(@Param("subscriptionPlanId") int subscriptionPlanId, @Param("status") String status,
			@Param("modifiedBy") String modifiedBy, @Param("modifiedDate") Date modifiedDate);

	@Query
	Page<NimaiMSubscriptionPlan> findByStatus(String status, Pageable pageable);

	// 01-09-2020
	@Query("from NimaiMSubscriptionPlan m where m.status='Active' and"
			+ " m.countryName = :countryName and m.customerType= :customerType ")
	List<NimaiMSubscriptionPlan> getPlanAmount(@Param("customerType") String customerType,
			@Param("countryName") String countryName);

	@Query(value = " SELECT * FROM nimai_m_subscription nms INNER JOIN nimai_m_subscriptioncountry ncm \r\n" + 
			"     ON nms.SUBSCRIPTION_ID=ncm.subscription_id\r\n" + 
			"     WHERE ncm.subscription_country IN (:value)\r\n" + 
			"     AND nms.CUSTOMER_TYPE=:custType group by nms.SUBSCRIPTION_ID",
	 		countQuery = "SELECT cnt FROM\n" + 
	 				"(SELECT count(DISTINCT ncm.subscription_id) AS cnt FROM nimai_m_subscription nms INNER JOIN nimai_m_subscriptioncountry ncm \n" + 
	 				"ON nms.SUBSCRIPTION_ID=ncm.subscription_id "
	 				+ "where nms.customer_type=:custType "
	 				+ " and ncm.subscription_country IN (:value))\n" + 
	 				"AS cnt",nativeQuery = true)
	public Page<NimaiMSubscriptionPlan> getAllSubscriptionPlan(List<String> value, String custType, Pageable pageable);



//	@Query(value = "SELECT sub.* FROM nimai_m_subscription sub where FIND_IN_SET ((:countryValues),sub.country_name) and"
//			+ " sub.customer_type=:customerType"
//			+ " order by  sub.INSERTED_DATE  desc ",
//			countQuery = "SELECT cnt FROM\n" + 
//	 				"(SELECT COUNT(*) AS cnt FROM nimai_m_subscription sub \n" + 
//	 				"where sub.customer_type=:custType and FIND_IN_SET ((:countryValues),sub.country_name) "
//	 				+ " order by sub.INSERTED_DATE desc limit i )\n" + 
//	 				"AS cnt",nativeQuery = true)
//	List<NimaiMSubscriptionPlan> getAllSubscriptionPlanNew(String countryValues, String customerType, Pageable pageable);
//
//	

	@Query(value = "SELECT sub.* FROM nimai_m_subscription sub where FIND_IN_SET ((:countryValues),sub.country_name) and"
			+ " sub.customer_type=:customerType",
			countQuery = "SELECT cnt FROM\n" + 
	 				"(SELECT COUNT(*) AS cnt FROM nimai_m_subscription sub \n" + 
	 				"where sub.customer_type=:custType and FIND_IN_SET ((:countryValues),sub.country_name) "+ 
	 				"AS cnt",nativeQuery = true)
	List<NimaiMSubscriptionPlan> getAllSubscriptionPlanNew(String countryValues, String customerType, Pageable pageable);

	
	
	
	
	
	
	
//	NimaiMSubscriptionPlan getSplanBySPlanName(String getsPLanName);
}
