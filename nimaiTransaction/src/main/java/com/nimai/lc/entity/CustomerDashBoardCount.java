package com.nimai.lc.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
@Entity
@Table(name="temp_cust_dashboard_count")
public class CustomerDashBoardCount {
	
	
	
	@Id
	@Column(name="id")
	private int id;
	
	@Column(name="Detail")
	private String detail;
	
	@Column(name="Count")
	private Integer count;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getDetail() {
		return detail;
	}

	public void setDetail(String detail) {
		this.detail = detail;
	}

	public Integer getCount() {
		return count;
	}

	public void setCount(Integer count) {
		this.count = count;
	}
	
	
	
	
}
