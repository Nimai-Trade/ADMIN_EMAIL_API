package com.nimai.admin.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.nimai.admin.model.NimaiMRights;

public interface RightsRepository extends JpaRepository<NimaiMRights, Integer> {
	
	boolean existsByRightShortName(String rightShortName);

}
