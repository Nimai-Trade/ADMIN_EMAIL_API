package com.nimai.admin.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import com.nimai.admin.model.NimaiMCountry;

@Repository
public interface CountryRepository
		extends JpaRepository<NimaiMCountry, Integer>, JpaSpecificationExecutor<NimaiMCountry> {

	
}
