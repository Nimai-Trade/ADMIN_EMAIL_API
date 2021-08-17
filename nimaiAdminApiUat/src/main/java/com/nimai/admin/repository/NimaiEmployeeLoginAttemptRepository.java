package com.nimai.admin.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import com.nimai.admin.model.NimaiEmployeeLoginAttempt;

public interface NimaiEmployeeLoginAttemptRepository
extends JpaRepository<NimaiEmployeeLoginAttempt, Integer>, JpaSpecificationExecutor<NimaiEmployeeLoginAttempt>{

	@Query("From NimaiEmployeeLoginAttempt n where n.empCode=:username")
	NimaiEmployeeLoginAttempt getcountDetailsByEmpCode(String username);

}
