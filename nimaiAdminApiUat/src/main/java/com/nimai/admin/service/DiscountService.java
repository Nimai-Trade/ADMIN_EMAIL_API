package com.nimai.admin.service;

import java.io.ByteArrayInputStream;
import java.util.List;

import org.springframework.http.ResponseEntity;

import com.nimai.admin.model.NimaiMpDiscount;
import com.nimai.admin.payload.DiscountCouponRequest;
import com.nimai.admin.payload.DiscountCouponUpdateRequest;
import com.nimai.admin.payload.DiscountIdCouponCode;
import com.nimai.admin.payload.DiscountMpCouponRequest;
import com.nimai.admin.payload.PagedResponse;
import com.nimai.admin.payload.SearchRequest;

public interface DiscountService {

	List<DiscountIdCouponCode> saveDiscountCoupon(DiscountCouponRequest request,String coupType);

	ResponseEntity<?> updateCoupon(DiscountCouponUpdateRequest request);

	PagedResponse<?> getCouponDetails(SearchRequest request);

	ResponseEntity<?> getCouponDetailsId(Integer discountId);

	PagedResponse<?> getActiveCoupons(SearchRequest request);

	ByteArrayInputStream load(String country, String customerType,String bankType,List<String> coupon);

	List<NimaiMpDiscount> saveMDiscount(List<DiscountMpCouponRequest> mpDiscouDetails,List<DiscountIdCouponCode> dCode);


}
