package com.nimai.lc.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.nimai.lc.entity.BankLatestAcceptedTransaction;

@Repository
public interface BankLatestAcceptedTransactionRepository extends JpaRepository<BankLatestAcceptedTransaction, String> {

}
