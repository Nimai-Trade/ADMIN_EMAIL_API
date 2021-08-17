package com.nimai.admin.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.nimai.admin.model.NimaiMCurrency;

@Repository
public interface CurrencyRepository extends JpaRepository <NimaiMCurrency, String>, JpaSpecificationExecutor<NimaiMCurrency> {
	
	@Query("SELECT country FROM NimaiMCurrency where country like %:country%")
	public List<String> countrySearch(@Param("country") String country);

}
