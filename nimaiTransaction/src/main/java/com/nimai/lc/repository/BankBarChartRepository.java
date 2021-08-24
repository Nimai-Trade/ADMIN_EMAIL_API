package com.nimai.lc.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.nimai.lc.entity.BankDashboardBarChart;

@Repository
public interface BankBarChartRepository extends JpaRepository<BankDashboardBarChart, String>{

}
