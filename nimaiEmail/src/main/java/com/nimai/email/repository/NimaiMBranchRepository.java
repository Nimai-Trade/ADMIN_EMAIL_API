package com.nimai.email.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.nimai.email.entity.NimaiMBranch;

@Repository
public interface NimaiMBranchRepository
extends JpaRepository<NimaiMBranch, Integer>, JpaSpecificationExecutor<NimaiMBranch>{

	@Query(value="From NimaiMBranch b WHERE emailId=:emailAddress order by b.insertTime desc")
	NimaiMBranch getBranchUserDetails(@Param("emailAddress")String emailAddress);

	
	
}
