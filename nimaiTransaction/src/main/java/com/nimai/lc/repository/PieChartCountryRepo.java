package com.nimai.lc.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.query.Procedure;

import com.nimai.lc.entity.PieChartCountry;

public interface PieChartCountryRepo extends JpaRepository<PieChartCountry, String>{
	
	
	
	@Procedure("CUSTOMER_DASHBOARD_COUNTRY_PIE_CHART")
	void piechartCountry();
	
}
