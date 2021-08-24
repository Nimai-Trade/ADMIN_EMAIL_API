package com.nimai.lc.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.nimai.lc.entity.SavingInput;


@Repository
public interface SavingInpRepo extends JpaRepository<SavingInput, Integer> 
{
	@Query(value = "SELECT * from nimai_m_savings_input where country_name=(:lcCountry) and currency=(:lcCurrency) order by id desc limit 1", nativeQuery = true)
	List<SavingInput> checkForSavingData(String lcCountry, String lcCurrency);
}
