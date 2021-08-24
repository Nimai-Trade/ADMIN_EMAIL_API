package com.nimai.lc.repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.nimai.lc.entity.Countrycurrency;

@Repository
@Transactional
public interface CountrycurrencyRepository extends JpaRepository<Countrycurrency, String>{
	@Query(value= "SELECT * FROM countrycurrency", nativeQuery = true)
	List<Countrycurrency> getCountry();
	
	@Query(value= "SELECT DISTINCT currency FROM nimai_m_currency where currency NOT LIKE '' ORDER BY  currency asc", nativeQuery = true)
	List<String> getCurrency();

}
