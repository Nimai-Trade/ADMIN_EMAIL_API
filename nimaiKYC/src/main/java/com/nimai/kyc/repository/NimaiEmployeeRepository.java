package com.nimai.kyc.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.nimai.kyc.model.NimaiMEmployee;

@Repository
public interface NimaiEmployeeRepository extends JpaRepository<NimaiMEmployee, Integer> {

	@Query("from NimaiMEmployee e where e.empCode=:rmId")
	NimaiMEmployee getDetailsByRmId(String rmId);

}
