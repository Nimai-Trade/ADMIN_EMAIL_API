package com.nimai.lc.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.query.Procedure;

import com.nimai.lc.entity.PieChartGoods;

public interface PieChartGoodsRepo extends JpaRepository<PieChartGoods, String> {
	
	@Procedure("CUSTOMER_DASHBOARD_GOODS_PIE_CHART")
	void piechartGoods();

}
