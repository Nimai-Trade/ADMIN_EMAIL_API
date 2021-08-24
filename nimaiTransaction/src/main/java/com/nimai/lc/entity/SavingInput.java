package com.nimai.lc.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="nimai_m_savings_input")
public class SavingInput 
{
	@Id
	@Column(name="id") 
	private int id;
	
	@Column(name="country_name") 
	private String countryName;
	
	@Column(name="currency") 
	private String currency;
	
	@Column(name="annual_asset_value") 
	private Double annualAssetValue;
	
	@Column(name="net_revenue") 
	private Double netRevenue;

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

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public Double getAnnualAssetValue() {
		return annualAssetValue;
	}

	public void setAnnualAssetValue(Double annualAssetValue) {
		this.annualAssetValue = annualAssetValue;
	}

	public Double getNetRevenue() {
		return netRevenue;
	}

	public void setNetRevenue(Double netRevenue) {
		this.netRevenue = netRevenue;
	}

	
}
