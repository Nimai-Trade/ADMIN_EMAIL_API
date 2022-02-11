package com.nimai.splan.repository;

import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.nimai.splan.model.NimaiMCustomer;

public interface NimaiMCustomerRepository extends JpaRepository<NimaiMCustomer, String>  {

	@Query("FROM NimaiMCustomer r where r.userid = :userId")
	Optional<NimaiMCustomer> findByUserId(String userId);
	
	@Query("FROM NimaiMCustomer r where r.userid = :userId")
	NimaiMCustomer findCustomerDetailsByUserId(String userId);

	@Modifying
	@Transactional
	@Query("update NimaiMCustomer c set c.isSPlanPurchased= ?1 WHERE c.userid= ?2")
	void updateKycStatus(boolean b, String userid);
	
	@Modifying
	@Transactional
	@Query("update NimaiMCustomer c set c.isSPlanPurchased= 1 WHERE c.userid= ?1")
	void updatePlanPurchasedStatus(String userid);
	
	@Modifying
	@Transactional
	@Query("update NimaiMCustomer c set c.modeOfPayment= ?1 WHERE c.userid= ?2")
	void updatePaymentMode(String mode, String userid);
	
	@Modifying
	@Transactional
	@Query("update NimaiMCustomer c set c.modeOfPayment= ?1, c.paymentTransId= ?2 WHERE c.userid= ?3")
	void updatePaymentTxnId(String mode, String invoiceId, String userid);
	
	@Modifying
	@Transactional
	@Query("update NimaiMCustomer c set c.paymentStatus= ?1, c.paymentTransId= ?2 WHERE c.userid= ?3")
	void updatePaymentStatus(String sts, String paymentId, String userid);

	@Modifying
	@Transactional
	@Query("update NimaiMCustomer c set c.paymentStatus= 'Pending' WHERE c.userid= ?1")
	void updatePaymentStatus(String userid);
	
	@Modifying
	@Transactional
	@Query("update NimaiMCustomer c set c.paymentStatus= 'Approved' WHERE c.userid= ?1")
	void updatePaymentStatusForCredit(String userid);

	@Modifying
	@Transactional
	@Query("update NimaiMCustomer c set c.paymentTransId= ?2 WHERE c.userid= ?1")
	void updatePaymentTransactionId(String userid,String generatePaymentTtransactionID);

	@Query(value = "SELECT account_type from nimai_m_customer where userid=(:userid)", nativeQuery = true)
	String getAccountTypeByUserId(String userid);

	@Query(value = "SELECT account_source from nimai_m_customer where userid=(:userid)", nativeQuery = true)
	String getAccountSourceByUserId(String userid);

	@Query(value = "SELECT mode_of_payment from nimai_m_customer where userid=(:userid)", nativeQuery = true)
	String getModeOfPayment(String userid);
	
	

}
