package com.nimai.admin.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.nimai.admin.model.NimaiEmailSchedulerAlertToBanks;
import com.nimai.admin.model.NimaiMCustomer;




public interface NimaiEmailSchedulerAlertToBanksRepository  extends JpaRepository<NimaiEmailSchedulerAlertToBanks, Integer>
{
	@Query("SELECT custDet FROM NimaiMCustomer custDet WHERE custDet.userid= (:userid)")
	NimaiMCustomer getCustDetailsByUserId(@Param("userid") String userid);
	
}
