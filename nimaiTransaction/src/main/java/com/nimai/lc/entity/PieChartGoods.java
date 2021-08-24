package com.nimai.lc.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="temp_pie_chart_goods")
public class PieChartGoods {

	
	@Id
	@Column(name="id")
	private int id;
	
	
	@Column(name="goods_type")
	private String goodsType;
	
	@Column(name="goods_count")
	private String goodsCount;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getGoodsType() {
		return goodsType;
	}

	public void setGoodsType(String goodsType) {
		this.goodsType = goodsType;
	}

	public String getGoodsCount() {
		return goodsCount;
	}

	public void setGoodsCount(String goodsCount) {
		this.goodsCount = goodsCount;
	}
	
	
	


	
	
	
}
