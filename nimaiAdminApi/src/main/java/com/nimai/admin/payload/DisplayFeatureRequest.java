package com.nimai.admin.payload;

public class DisplayFeatureRequest {

	private String country;

	private double avgAmount;

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

	public String getCcy() {
		return ccy;
	}

	public void setCcy(String ccy) {
		this.ccy = ccy;
	}

	public DisplayFeatureRequest() {
		super();
	}
}
