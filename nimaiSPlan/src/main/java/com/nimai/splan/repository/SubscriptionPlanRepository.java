package com.nimai.splan.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.nimai.splan.model.NimaiSubscriptionDetails;

@Transactional
@Repository
public interface SubscriptionPlanRepository extends JpaRepository<NimaiSubscriptionDetails, Integer> {

	@Query("FROM NimaiSubscriptionDetails n where n.userid.userid = :userId and Status = 'Active'")
	List<NimaiSubscriptionDetails> findAllByUserId(String userId);
	
	@Query("FROM NimaiSubscriptionDetails n where n.userid.userid = :userId and Status = 'Active' and paymentStatus!='Rejected'")
	List<NimaiSubscriptionDetails> findAllByUserIdExpReject(String userId);
	
	@Query(value = "select * FROM nimai_subscription_details where userid = :userId and Status = 'Inactive' ORDER BY SPL_SERIAL_NUMBER DESC LIMIT 1", nativeQuery = true)
	List<NimaiSubscriptionDetails> findAllInactivePlanByUserId(String userId);

	@Query("FROM NimaiSubscriptionDetails n where n.userid.userid = :userId and n.status = 'Active'")
	NimaiSubscriptionDetails findByUserId(String userId);
	
	@Query(value = "SELECT nsd.SPL_SERIAL_NUMBER from nimai_subscription_details nsd ORDER BY nsd.SPL_SERIAL_NUMBER DESC LIMIT 1", nativeQuery = true)
	Integer findLastSerialNo();
	
	/*@Modifying
	@Query("update NimaiSubscriptionDetails nsd set nsd.isVasApplied=1 where nsd.userid.userid = :userId and nsd.status = 'Active'")
	void updateIsVASApplied(String userId);*/
	
	@Modifying
	@Query(value = "update nimai_subscription_details set is_vas_applied=1 where userid=(:userId) and status='ACTIVE'", nativeQuery = true)
	void updateIsVASApplied(String userId);

	@Query(value = "select *\n" + 
			"   from nimai_subscription_details\n" + 
			"   where date(splan_start_date) = (select min(date(splan_start_date))\n" + 
			"      from nimai_subscription_details\n" + 
			"      where date(splan_start_date) >= date(curdate())\n" + 
			"   ) AND userid=(:userId) and status='INACTIVE' ORDER BY spl_serial_number DESC LIMIT 1", nativeQuery = true)
	NimaiSubscriptionDetails findLatestInactiveSubscriptionByUserId(String userId);
	
	@Query(value = "select *\n" + 
			"   from nimai_subscription_details\n" + 
			"   where userid=(:userId) and status='INACTIVE' ORDER BY spl_serial_number DESC LIMIT 1", nativeQuery = true)
	NimaiSubscriptionDetails findOnlyLatestInactiveSubscriptionByUserId(String userId);

	@Modifying
	@Query(value = "update nimai_subscription_details set discount_id=(:discountId) where userid=(:userId) and status='ACTIVE'", nativeQuery = true)
	void updateDiscountId(String userId, Double discountId);
	
	@Modifying
	@Query(value = "update nimai_subscription_details set PAYMENT_TXN_ID=(:payTxnId) where userid=(:userId) and status='ACTIVE'", nativeQuery = true)
	void updatePaymentTxnId(String userId, String payTxnId);
	
	@Modifying
	@Query(value = "update nimai_subscription_details set PAYMENT_TXN_ID=(:payTxnId), invoice_id=(:invoiceId) where userid=(:userId) and status='ACTIVE'", nativeQuery = true)
	void updatePaymentTxnIdInvId(String userId, String payTxnId, String invoiceId);
	
	@Modifying
	@Query(value = "update nimai_subscription_details set invoice_id=(:invoiceId) where userid=(:userId) and status='ACTIVE'", nativeQuery = true)
	void updateInvId(String userId, String invoiceId);
	
	@Modifying
	@Query(value = "update nimai_subscription_details set PAYMENT_TXN_ID=(:payTxnId), invoice_id=(:invoiceId), grand_amount=:grandgstValue where userid=(:userId) and status='ACTIVE'", nativeQuery = true)
	void updatePaymentTxnIdForWire(String userId, String payTxnId, String invoiceId, String grandgstValue);
	
	@Modifying
	@Query(value = "update nimai_subscription_details set LC_UTILIZED_COUNT=(:utilizedValue) where userid=(:userId) and status='ACTIVE'", nativeQuery = true)
	void updateLCUtilzed(String userId,Integer utilizedValue);

	@Modifying
	@Query(value = "update nimai_subscription_details set PAYMENT_TXN_ID=(:payTxnId), grand_amount=(:amt) where userid=(:userId) and status='ACTIVE'", nativeQuery = true)
	void updatePaymentTxnIdGrandAmount(String userId, String payTxnId, Double amt);
	
	@Query(value = "SELECT TIMESTAMPDIFF(month, nsd.SPLAN_START_DATE, nsd.SPLAN_END_DATE)\n" + 
			"FROM nimai_subscription_details nsd \n" + 
			"WHERE nsd.userid=(:userId) AND nsd.`STATUS`='ACTIVE'", nativeQuery = true)
	Double findNoOfMonthOfSubscriptionByUserId(String userId);
	
	@Query(value = "SELECT TIMESTAMPDIFF(month, nsd.SPLAN_START_DATE, CURDATE())\n" + 
			"FROM nimai_subscription_details nsd \n" + 
			"WHERE nsd.userid=(:userId) AND nsd.`STATUS`='ACTIVE'", nativeQuery = true)
	Double findDiffInSubscriptionStartAndCurrentByUserId(String userId);

	@Modifying
	@Query(value = "update nimai_subscription_details set is_vas_applied=1,vas_amount=vas_amount+(:vasAmount),grand_amount=grand_amount+(:pricing) where userid=(:userId) and status='ACTIVE'", nativeQuery = true)
	void updateVASDetailsApplied(String userId, Float vasAmount, Float pricing);

	@Modifying
	@Query(value = "update nimai_subscription_details set is_vas_applied=1,vas_amount=vas_amount+(:vasAmount),grand_amount=grand_amount+(:pricing) where userid=(:userId) and status='ACTIVE'", nativeQuery = true)
	void updateVASDetailsAppliedWire(String userId, String vasAmount, String pricing);
	
	@Query(value = "select subscription_amount from nimai_m_subscription where subscription_id=:subscriptionId and status='ACTIVE'", nativeQuery = true)
	Integer getSubscriptionAmt(String subscriptionId);
	
	/*@Query(value = "SELECT nsd.INVOICE_ID,nsd.INSERTED_DATE,nsd.PAYMENT_STATUS \n" + 
			"FROM nimai_subscription_details nsd where\n" + 
			"(nsd.STATUS!='Active' or nsd.PAYMENT_STATUS='Rejected') and nsd.userid=:userId \n" + 
			"order by nsd.SPL_SERIAL_NUMBER desc", nativeQuery = true)*/
	@Query(value = "select result.INVOICE_ID,result.INSERTED_DATE,result.PAYMENT_STATUS,\n" + 
			"result.SPL_SERIAL_NUMBER\n" + 
			"from\n" + 
			"(SELECT nsd.INVOICE_ID,nsd.INSERTED_DATE,nsd.PAYMENT_STATUS,\n" + 
			"nsd.SPL_SERIAL_NUMBER \n" + 
			"	FROM nimai_subscription_details nsd\n" + 
			"	where\n" + 
			"	(nsd.STATUS!='Active' or nsd.PAYMENT_STATUS='Rejected') \n" + 
			"	and nsd.userid=:userId \n" + 
			"	union\n" + 
			"	SELECT nsv.INVOICE_ID,nsv.INSERTED_DATE,nsv.PAYMENT_STATUS,\n" + 
			"	nsv.SPL_SERIAL_NUMBER \n" + 
			"	FROM nimai_subscription_vas nsv\n" + 
			"	where\n" + 
			"	(nsv.SPLAN_VAS_FLAG=0 and (nsv.STATUS!='Active' or nsv.PAYMENT_STATUS='Rejected')) \n" + 
			"	and nsv.userid=:userId) result\n" + 
			"	order by result.SPL_SERIAL_NUMBER desc", nativeQuery = true)
	List getPreviousSubscription(String userId);

	//@Modifying
	
	//@Query(value = "update nimai_subscription_details set status=(:sts) where userid=(:userId) and status='ACTIVE'", nativeQuery = true)
	//void updateDiscountId(String userId, String sts);

}
