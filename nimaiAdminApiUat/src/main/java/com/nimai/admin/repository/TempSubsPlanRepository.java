package com.nimai.admin.repository;

import java.util.Date;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.nimai.admin.model.NimaiTempSubscriptionPlan;

@Repository
public interface TempSubsPlanRepository
		extends JpaRepository<NimaiTempSubscriptionPlan, Integer>, JpaSpecificationExecutor<NimaiTempSubscriptionPlan> {

	@Modifying
	@Query("update NimaiTempSubscriptionPlan n set n.flag=true ,n.status= :status, n.modifiedBy= :modifiedBy, n.modifiedDate= :modifiedDate where n.flag=0 and n.subscriptionId= :subscriptionId")
	int updateTemp(@Param("subscriptionId") int subscriptionId, @Param("status") String status,
			@Param("modifiedBy") String modifiedBy, @Param("modifiedDate") Date date);

	@Query
	Page<NimaiTempSubscriptionPlan> findByFlag(boolean flag,Pageable pageable);
}
