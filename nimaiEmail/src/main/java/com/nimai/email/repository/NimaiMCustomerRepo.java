package com.nimai.email.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.nimai.email.entity.NimaiClient;
import com.nimai.email.entity.NimaiSystemConfig;


@Repository
public interface NimaiMCustomerRepo
extends JpaRepository<NimaiClient, String>, JpaSpecificationExecutor<NimaiClient>{
	
	@Query(value="select ns.LEAD_ID from nimai_m_customer ns where ns.USERID=:userId",nativeQuery = true)
	Integer findLeadIdByUserId(String  userId);

}
