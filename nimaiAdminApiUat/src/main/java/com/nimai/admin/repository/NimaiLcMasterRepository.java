package com.nimai.admin.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.nimai.admin.model.NimaiKyc;
import com.nimai.admin.model.NimaiLCMaster;
import com.nimai.admin.model.NimaiMCustomer;


public interface NimaiLcMasterRepository extends JpaRepository<NimaiLCMaster, String>{

	@Query("SELECT lc FROM NimaiLCMaster lc WHERE lc.transactionId= (:transactionId) and lc.userId=(:userId)")
	NimaiLCMaster findByTransactionIdUserId(@Param("transactionId")String transId, @Param("userId") String userId);

	@Query(value = "SELECT * from get_all_transaction where transaction_id=(:transid)", nativeQuery = true)
	NimaiLCMaster findSpecificTransactionById(@Param("transid") String transid);

	@Query(value = "select * from nimai_m_customer where EMAIL_ADDRESS=(:userId)", nativeQuery = true)
	NimaiMCustomer getCustomerDetais(String userId);

	@Query(value = "select first_name from nimai_m_customer where userid=(:userId)", nativeQuery = true)
	String getCustomerName(String userId);
	
	@Query(value = "select email_address from nimai_m_customer where userid=(:userId)", nativeQuery = true)
	String getCustomerEmailId(String userId);
	
}
