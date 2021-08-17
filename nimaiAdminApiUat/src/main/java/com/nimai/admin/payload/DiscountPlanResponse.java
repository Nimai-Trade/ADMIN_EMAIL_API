package com.nimai.admin.payload;

public class DiscountPlanResponse {

	private String planName;
	private double pricing;
	public DiscountPlanResponse() {
		super();
		// TODO Auto-generated constructor stub
	}
	public String getPlanName() {
		return planName;
	}
	public void setPlanName(String planName) {
		this.planName = planName;
	}
	public double getPricing() {
		return pricing;
	}
	public void setPricing(double pricing) {
		this.pricing = pricing;
	}
	
}
