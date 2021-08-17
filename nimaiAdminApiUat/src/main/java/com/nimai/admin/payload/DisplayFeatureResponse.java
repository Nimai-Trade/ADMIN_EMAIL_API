package com.nimai.admin.payload;

import java.util.Date;

public class DisplayFeatureResponse {

	private String country;

	private double avgAmount;

	private Date createdDate;

	private String ccy;

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public double getAvgAmount() {
		return avgAmount;
	}

	public void setAvgAmount(double avgAmount) {
		this.avgAmount = avgAmount;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public DisplayFeatureResponse() {
		super();
		// TODO Auto-generated constructor stub
	}

	public String getCcy() {
		return ccy;
	}

	public void setCcy(String ccy) {
		this.ccy = ccy;
	}

}
