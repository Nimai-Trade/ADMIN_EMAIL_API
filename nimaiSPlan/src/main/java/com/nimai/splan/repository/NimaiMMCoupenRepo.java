// 
// Decompiled by Procyon v0.5.36
// 

package com.nimai.splan.repository;

import java.util.List;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.stereotype.Repository;
import com.nimai.splan.model.NimaiMMCoupen;
import org.springframework.data.jpa.repository.JpaRepository;

@Repository
public interface NimaiMMCoupenRepo extends JpaRepository<NimaiMMCoupen, String>
{
    @Procedure("VALIDATE_COUPEN")
    void validCoupen(final String coupenId, final String countryName, final String subscriptionPlan, final String coupenfor);
    
  //  @Query("FROM NimaiMMCoupen n where n.userid.userid = :userId and n.status = 'Active'")
  //  NimaiMMCoupen getCouponDetailsByDiscId(Integer discountId);
    
    @Query(value = "SELECT discount_type from nimai_m_discount where COUPON_CODE=(:coupenCode) and status='Active' and CONSUMED_COUPONS<quantity and subscription_plan=(:subscriptionName) and coupon_for=(:couponFor) and country=(:businesscountry)", nativeQuery = true)
    String getDiscountTypeByCoupenCodeSubscriptionNameStatusAndConsumption(@Param("coupenCode") final String coupenCode, @Param("subscriptionName") final String subscriptionName, @Param("couponFor") final String couponFor,@Param("businesscountry")  final String businesscountry);
    
    @Query(value = "SELECT discount_type from nimai_m_discount where COUPON_CODE=(:coupenCode) and status='Active' and CONSUMED_COUPONS<quantity", nativeQuery = true)
    String getDiscountTypeByCoupenCodeStatusAndConsumption(@Param("coupenCode") final String coupenCode);
    
    @Query(value = "SELECT discount_type from nimai_m_discount where discount_id=(:discountId)", nativeQuery = true)
    String getDiscountTypeByDiscountId(@Param("discountId") Double discountId);
    
    //23 AUG 2021
    //@Query(value = "SELECT amount from nimai_m_discount where COUPON_FOR=(:coupenFor) and COUPON_CODE=(:coupenCode) and status='Active' and country=(:businessCountry) and subscription_plan=(:subscriptionName)", nativeQuery = true)
    //Double getAmountByCoupenCode(final String coupenFor, final String coupenCode, final String businessCountry, final String subscriptionName);
    
    @Query(value = "SELECT amount from nimai_m_discount where discount_id=(:discountId) and status='Active'", nativeQuery = true)
    Double getAmountByDiscId(@Param("discountId") Double discountId);
    
    @Query(value = "SELECT amount from nimai_m_discount where discount_id=(:discountId)", nativeQuery = true)
    Double getAmountByDiscountId(Double discountId);
    
    @Transactional
    @Modifying
    @Query(value = "update nimai_m_discount set consumed_coupons=ifnull(consumed_coupons,0)+1 where DISCOUNT_ID=(:discountId)", nativeQuery = true)
    void updateConsumption(final Double discountId);
    
    @Query(value = "SELECT discount_percentage from nimai_m_discount where COUPON_CODE=(:coupenCode) and status='Active' and country=(:businessCountry)", nativeQuery = true)
    Double getDiscPercByCoupenCode(final String coupenCode, final String businessCountry);
    
    @Query(value = "SELECT discount_percentage from nimai_m_discount where discount_id=(:discountId)", nativeQuery = true)
    Double getDiscPercByDiscountId(Double discountId);
    
    @Query(value = "SELECT max_discount from nimai_m_discount where COUPON_CODE=(:coupenCode) and status='Active' and country=(:businessCountry)", nativeQuery = true)
    Double getMaxDiscByCoupenCode(final String coupenCode, final String businessCountry);
    
    @Query(value = "SELECT max_discount from nimai_m_discount where discount_id=(:discountId)", nativeQuery = true)
    Double getMaxDiscByDiscountId(Double discountId);
    
    @Query(value = "SELECT discount_id from nimai_m_discount where COUPON_CODE=(:coupenCode) and subscription_plan=(:subscriptionName) and status='Active' and country=(:businessCountry) and coupon_for=(:coupenFor)", nativeQuery = true)
    Double getDiscountId(final String coupenCode, final String businessCountry, final String subscriptionName, final String coupenFor);
    
    @Query(value = "SELECT discount_id from nimai_m_discount where COUPON_CODE=(:coupenCode) and status='Active'", nativeQuery = true)
    Double getDiscountIdByCouponCode(final String coupenCode);
    
    @Query(value = "SELECT lead_id from nimai_m_customer where userid=(:userId)", nativeQuery = true)
    Integer getLeadId(final String userId);
    
    @Transactional
    @Modifying
    @Query(value = "update nimai_m_discount set consumed_coupons=if((consumed_coupons-1)<0,0,consumed_coupons-1) where discount_id=(:discountId)", nativeQuery = true)
    void decrementConsumption(final int discountId);
    
    @Query(value = "SELECT distinct coupon_type from nimai_m_discount where COUPON_CODE=(:coupenCode) and status='Active' and CONSUMED_COUPONS<quantity and subscription_plan=(:subscriptionName) and coupon_for=(:couponFor) and country=(:businesscountry)", nativeQuery = true)
    String getCouponTypeByCoupenCodeSubscriptionNameStatusAndConsumption(@Param("coupenCode")final String coupenCode,@Param("subscriptionName") final String subscriptionName,@Param("couponFor")  final String couponFor,@Param("businesscountry")  final String businesscountry);
    
    @Query(value = "SELECT distinct coupon_type from nimai_m_discount where COUPON_CODE=(:coupenCode) and status='Active'", nativeQuery = true)
    String getCouponTypeByCoupenCode(@Param("coupenCode")final String coupenCode);
    
    @Query(value = "SELECT * from nimai_mp_discount where user_id=(:userId) and status='Active'", nativeQuery = true)
    List getDataByUserIdAndStatus(final String userId);
    
  //TIME(CONVERT_TZ(CURTIME(),'+00:00','+05:30'))
    //DATE_ADD(CONVERT_TZ(SYSDATE(),'+00:00','+05:30'),INTERVAL 23 SECOND)
    @Query(value = "SELECT count(*) from nimai_m_discount nmd where nmd.COUPON_CODE=(:coupenCode) \n" + 
    		"and nmd.status='Active' \n" + 
    		"and \n" + 
    		"case \n" + 
    		"when nmd.START_TIME is null then\n" + 
    		" nmd.START_DATE<=CURDATE()\n" + 
    		"when nmd.START_TIME is not null then\n" + 
    		" STR_TO_DATE(CONCAT(nmd.START_DATE,' ',TIME_FORMAT(ADDTIME(STR_TO_DATE(CONCAT(nmd.START_DATE,\n" + 
    		"' ',nmd.START_TIME),'%Y-%m-%d %H:%i:%s'),'05:30:00'),'%T')),'%Y-%m-%d %H:%i:%s')<=CONVERT_TZ(SYSDATE(),'+00:00','+05:30')" + 
    		"END ", nativeQuery = true)
    int getCountForValidCoupon(final String coupenCode);

    @Query(value = "SELECT amount from nimai_m_discount where discount_id=(:discountId)", nativeQuery = true)
	Double getDiscAmountByDiscId(Double discountId);

    @Query(value = "SELECT email_address from nimai_m_customer where userid=(:userId)", nativeQuery = true)
    String getEmailId(final String userId);
    
    @Query(value = "SELECT distinct promo_code from nimai_m_refer where email_address=(:emailID)", nativeQuery = true)
	String getPromoCode(String emailID);
}