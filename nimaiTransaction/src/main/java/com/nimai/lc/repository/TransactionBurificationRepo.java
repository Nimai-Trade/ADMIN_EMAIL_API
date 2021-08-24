package com.nimai.lc.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.nimai.lc.entity.TransactionBifurcation;

@Repository
public interface TransactionBurificationRepo extends JpaRepository<TransactionBifurcation, String> {

}
