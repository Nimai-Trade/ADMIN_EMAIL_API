package com.nimai.lc.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="temp_refer_dashboard_count")
public class ReferDashboardCount {
	
	
	@Id
	@Column(name="id")
	private int id;
	
	@Column(name="Detail")
	private String Detail;
	
	@Column(name="Count")
	private int Count;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getDetail() {
		return Detail;
	}

	public void setDetail(String detail) {
		Detail = detail;
	}

	public int getCount() {
		return Count;
	}

	public void setCount(int count) {
		Count = count;
	}
	
	
	
	
	
	
	
	
	

}
