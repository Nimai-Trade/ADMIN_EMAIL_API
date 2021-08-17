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

import com.nimai.admin.model.NimaiMDiscount;

@Repository
public interface DiscountRepository
		extends JpaRepository<NimaiMDiscount, Integer>, JpaSpecificationExecutor<NimaiMDiscount> {

	@Modifying
	@Query("update NimaiMDiscount n set n.status= :status,n.modifiedBy= :modifiedBy"
			+ ",n.modifiedDate= :modifiedDate where n.discountId= :discountId")
	public int updateDisCoupon(@Param("status") String status, @Param("modifiedBy") String modifiedBy,
			@Param("modifiedDate") Date modifiedDate);
	
	@Query("from NimaiMDiscount nm where status='Active'")
	Page<NimaiMDiscount> getActiveCoup(Pageable pageable);

	@Query("from NimaiMDiscount nd where nd.couponCode=:couponCode and nd.country=:countryName and nd.status=:status and nd.couponFor=:customerType")
	public NimaiMDiscount getDetailsByCoupon(@Param("couponCode")String couponCode,@Param("countryName") String countryName,@Param("status")String status,@Param("customerType")String customerType);

	//@Query("")
	//public NimaiMDiscount getDetailsByCoupon(@Param("couponCode")String couponCode,@Param("countryName") String countryName);
}
