package com.nimai.admin.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.nimai.admin.model.NimaiMCountry;
import com.nimai.admin.model.NimaiMKycfileds;

public interface KycFiledRepository 
extends JpaRepository<NimaiMKycfileds, Integer>, JpaSpecificationExecutor<NimaiMKycfileds>{

	@Query("from NimaiMKycfileds nk where nk.userId=:userId")
	NimaiMKycfileds getFiledData(@Param("userId") String userId);

	
	
}
