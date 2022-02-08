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

import com.nimai.admin.model.NimaiMDiscount;
import com.nimai.admin.model.NimaiMVas;

@Repository
public interface DiscountRepository
		extends JpaRepository<NimaiMDiscount, Integer>, JpaSpecificationExecutor<NimaiMDiscount> {

	@Modifying
	@Query("update NimaiMDiscount n set n.status= :status,n.modifiedBy= :modifiedBy"
			+ ",n.modifiedDate= :modifiedDate where n.discountId= :discountId")
	public int updateDisCoupon(@Param("status") String status, @Param("modifiedBy") String modifiedBy,
			@Param("modifiedDate") Date modifiedDate);
	
	@Query("from NimaiMDiscount nm where status=:status")
	Page<NimaiMDiscount> getActiveCoup(String status, Pageable pageable);

	@Query("from NimaiMDiscount nd where nd.couponCode=:couponCode and nd.country=:countryName and nd.status=:status and nd.couponFor=:customerType")
	public NimaiMDiscount getDetailsByCoupon(@Param("couponCode")String couponCode,@Param("countryName") String countryName,@Param("status")String status,@Param("customerType")String customerType);

	
	
	
	
	@Query(value = "SELECT * FROM nimai_m_discount sub "
			+ "inner join nimai_m_discountcountry nmd  on sub.DISCOUNT_ID=nmd.discount_id where"
			+ " nmd.discount_country in :value group by nmd.discount_id",
	 		countQuery = "SELECT cnt FROM\n" + 
	 				"(SELECT COUNT(DISTINCT nmd.discount_id) AS cnt FROM nimai_m_discount sub \n"
	 				+ "inner join nimai_m_discountcountry nmd  on sub.DISCOUNT_ID=nmd.discount_id "
	 				+ "where  nmd.discount_country IN :value  )\n" + 
	 				"AS cnt",nativeQuery = true)
	public Page<NimaiMDiscount> getAllVasPlan(List<String> value, Pageable pageable);

	@Query(value = "SELECT * FROM nimai_m_discount sub inner join nimai_m_discountcountry nmd"
			+ " on sub.DISCOUNT_ID=nmd.discount_id  where nmd.discount_country in :value and sub.COUPON_FOR=:customerType group by nmd.discount_id ",
	 		countQuery = "SELECT cnt FROM\n" + 
	 				"(SELECT COUNT(DISTINCT nmd.discount_id) AS cnt FROM nimai_m_discount sub inner join nimai_m_discountcountry nmd\n" + 
	 				"on sub.DISCOUNT_ID=nmd.discount_id where  nmd.discount_country :value and sub.COUPON_FOR=:customerType  )\n" + 
	 				"AS cnt",nativeQuery = true)
	public Page<NimaiMDiscount> getAsPerCuTypeDiscount(List<String> value, String customerType, Pageable pageable);

	
	@Query(value = "SELECT * FROM nimai_m_discount sub  "
			+ "inner join nimai_m_discountcountry nmd  on sub.DISCOUNT_ID=nmd.discount_id where nmd.discount_country in :value and sub.STATUS=:status group by nmd.discount_id",
	 		countQuery = "SELECT cnt FROM\n" + 
	 				"(SELECT COUNT(DISTINCT nmd.discount_id) AS cnt FROM nimai_m_discount sub \n"
	 				+ "inner join nimai_m_discountcountry nmd  on "
	 				+ "  sub.DISCOUNT_ID=nmd.discount_id where nmd.discount_country IN :value and sub.STATUS=:status)\n" + 
	 				"AS cnt",nativeQuery = true)
	public Page<NimaiMDiscount> getAllDiscountByStats(List<String> value, String status, Pageable pageable);

	
	
	
	
	
	//@Query("")
	//public NimaiMDiscount getDetailsByCoupon(@Param("couponCode")String couponCode,@Param("countryName") String countryName);
}
