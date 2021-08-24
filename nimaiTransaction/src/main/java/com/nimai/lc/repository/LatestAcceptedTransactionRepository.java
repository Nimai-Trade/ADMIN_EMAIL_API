package com.nimai.lc.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.nimai.lc.entity.LatestAcceptedTransaction;

public interface LatestAcceptedTransactionRepository extends JpaRepository<LatestAcceptedTransaction, String>{

}
