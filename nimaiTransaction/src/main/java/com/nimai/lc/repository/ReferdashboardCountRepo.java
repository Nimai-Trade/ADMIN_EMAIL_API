package com.nimai.lc.repository;

import javax.persistence.Entity;
import javax.persistence.Table;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.nimai.lc.controller.ReferDashboardController;
import com.nimai.lc.entity.ReferDashboardCount;

@Repository
public interface ReferdashboardCountRepo  extends JpaRepository<ReferDashboardCount, String>{
	
	
	

}
