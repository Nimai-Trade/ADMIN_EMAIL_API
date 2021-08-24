package com.nimai.lc.repository;



import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.stereotype.Repository;

import com.nimai.lc.entity.CumulativeTransactionAmount;

@Repository
public interface CumulativeTransactionAmountRepository extends JpaRepository<CumulativeTransactionAmount, String> {
	
	
	
	

}
