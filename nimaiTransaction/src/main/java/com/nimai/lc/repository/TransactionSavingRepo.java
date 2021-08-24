package com.nimai.lc.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.nimai.lc.entity.SavingInput;
import com.nimai.lc.entity.TransactionSaving;

@Transactional
@Repository
public interface TransactionSavingRepo extends JpaRepository<TransactionSaving, Integer> 
{
	@Query(value = "SELECT savings from nimai_m_transaction_savings where transaction_id=(:transId)", nativeQuery = true)
	Double getSavingsByTransId(String transId);

	@Modifying
	@Query(value = "delete from nimai_m_transaction_savings where transaction_id=(:tid)", nativeQuery = true)
	void deleteSavingsByTxnId(String tid);
}
