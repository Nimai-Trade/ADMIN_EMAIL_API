package com.nimai.kyc.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.nimai.kyc.model.NimaiEmailScheduler;

@Repository
public interface NimaiEmailSchedularRepository extends JpaRepository<NimaiEmailScheduler, Integer> {

}
