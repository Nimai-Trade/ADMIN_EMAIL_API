package com.nimai.lc.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.nimai.lc.entity.NimaiClient;
import com.nimai.lc.entity.NimaiEmailSchedulerAlertToBanks;


public interface NimaiEmailSchedulerAlertToBanksRepository  extends JpaRepository<NimaiEmailSchedulerAlertToBanks, Integer>
{
	@Query("SELECT custDet FROM NimaiClient custDet WHERE custDet.userid= (:userid)")
	NimaiClient getCustDetailsByUserId(@Param("userid") String userid);
	
}
