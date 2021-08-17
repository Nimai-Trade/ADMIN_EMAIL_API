package com.nimai.admin.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.nimai.admin.model.NimaiMDiscount;
import com.nimai.admin.model.NimaiMpDiscount;

public interface DiscountMpRepository
		extends JpaRepository<NimaiMpDiscount, Integer>, JpaSpecificationExecutor<NimaiMpDiscount> {

	@Modifying
	@Query("update NimaiMpDiscount mp set mp.status= :status where mp.discountId= :discountId ")
	public void updateMpDiscountStatus(@Param("status") String status,@Param("discountId") NimaiMDiscount nimaiMDiscount);
}
