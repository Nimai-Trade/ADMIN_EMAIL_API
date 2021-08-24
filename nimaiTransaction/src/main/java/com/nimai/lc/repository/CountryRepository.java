package com.nimai.lc.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import org.springframework.transaction.annotation.Transactional;
import com.nimai.lc.entity.Country;

@Repository
@Transactional
public interface CountryRepository extends JpaRepository<Country, Integer>{
	
	@Query(value= "SELECT nc.country_id FROM nimai_m_currency nc,nimai_m_customer ncust where ncust.country_name=nc.country and ncust.userid=(:userid)", nativeQuery = true)
	String getCountryCodeForCountryName(@Param("userid") String userid);
	
	@Query(value= "SELECT nc.country_id FROM nimai_m_currency nc where nc.country=(:countryName)", nativeQuery = true)
	String getCountryCode(@Param("countryName") String countryName);
}