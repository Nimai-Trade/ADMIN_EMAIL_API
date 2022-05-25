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
	@Query("from NimaiMSubscriptionPlan m where m.status='Active' and m.countryName= :countryName and m.customerType= :customerType ")
	List<NimaiMSubscriptionPlan> getPlanAmount(@Param("customerType") String customerType,
			@Param("countryName") String countryName);

	
	@Query(value = " SELECT * FROM nimai_m_subscription nms INNER JOIN "
			+ "nimai_m_subscriptioncountry ncm "
			+ "  ON nms.SUBSCRIPTION_ID=ncm.subscription_id"
			+ "  WHERE (ncm.subscription_country IN (:value) OR ncm.subscription_country='All')"
			+ "  AND nms.CUSTOMER_TYPE=:custType group by nms.SUBSCRIPTION_ID", 
			countQuery = "SELECT cnt FROM (SELECT count(DISTINCT ncm.subscription_id) AS "
					+ "cnt FROM nimai_m_subscription nms INNER JOIN nimai_m_subscriptioncountry ncm "
					+ "ON nms.SUBSCRIPTION_ID=ncm.subscription_id where nms.customer_type=:custType "
					+ " and (ncm.subscription_country IN (:value) OR ncm.subscription_country='All'))AS cnt", nativeQuery = true)
	  Page<NimaiMSubscriptionPlan> getAllSubscriptionPlan(List<String> value, String custType, Pageable paramPageable);
	  
	 @Query(value = " SELECT DISTINCT m.*  from nimai_m_subscription m INNER  JOIN nimai_m_subscriptioncountry nms ON m.SUBS_PLAN_ID=nms.subscription_plan_id"
	 		+ "where m.status='Active' and m.CUSTOMER_TYPE= :customerType and  nms.subscription_country  IN (:value) ", nativeQuery = true)
	  List<NimaiMSubscriptionPlan> getPlanAmount(@Param("customerType") String customerType, @Param("value") List<String> value);
	  
	
	
//	NimaiMSubscriptionPlan getSplanBySPlanName(String getsPLanName);
}
