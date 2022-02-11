package com.nimai.splan.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.nimai.splan.model.NimaiCustomerSubscriptionGrandAmount;

@Repository
public interface NimaiCustomerGrandAmountRepo extends JpaRepository<NimaiCustomerSubscriptionGrandAmount, Integer> 
{

	@Query(value = "SELECT * FROM nimai_customer_subscription_amount nc WHERE nc.user_id=:userId ORDER BY nc.id DESC LIMIT 1", nativeQuery = true)
	NimaiCustomerSubscriptionGrandAmount getDetByUserId(String userId);
	
	@Query(value = "SELECT * FROM nimai_customer_subscription_amount nc WHERE nc.user_id=:userId and nc.vas_applied='Yes' ORDER BY nc.id DESC LIMIT 1", nativeQuery = true)
	NimaiCustomerSubscriptionGrandAmount getVASDetByUserId(String userId);

	@Query(value = "SELECT * FROM nimai_customer_subscription_amount nc WHERE nc.id=:id and nc.grand_amount=:amt", nativeQuery = true)
	NimaiCustomerSubscriptionGrandAmount getDetByIdAndAmt(int id, Double amt);
	
}