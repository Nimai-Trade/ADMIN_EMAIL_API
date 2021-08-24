package com.nimai.lc.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="temp_pie_chart")
public class PieChartCountry {
	@Id
	@Column(name="id")
	private int id;
	
	
	@Column(name="country")
	private String countryName;
	
	
	@Column(name="count")
	private int countryCount;


	public int getId() {
		return id;
	}


	public void setId(int id) {
		this.id = id;
	}


	public String getCountryName() {
		return countryName;
	}


	public void setCountryName(String countryName) {
		this.countryName = countryName;
	}


	public int getCountryCount() {
		return countryCount;
	}


	public void setCountryCount(int countryCount) {
		this.countryCount = countryCount;
	}

	
	
	
	

}
