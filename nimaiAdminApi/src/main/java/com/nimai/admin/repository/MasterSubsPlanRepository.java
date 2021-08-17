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

//	NimaiMSubscriptionPlan getSplanBySPlanName(String getsPLanName);
}
