package com.nimai.splan.service;

import java.util.HashMap;

import org.springframework.http.ResponseEntity;

import com.nimai.splan.model.NimaiCustomerSubscriptionGrandAmount;

public interface ValidateCoupenService {
	
	public HashMap<String, String> validateCoupen(String coupenId,String countryName,String subscriptionPlan,String coupenfor);

	public ResponseEntity<?> applyForCoupen(String userId, String coupenCode2, String subscriptionName,String coupenCode, Integer subscriptionAmount);

	public ResponseEntity<?> removeFromCoupen(String userId, int discountId, NimaiCustomerSubscriptionGrandAmount ncsga);
	
	public HashMap<String, Double> discountCalculate(Double discountId,String subscriptionId);
}
