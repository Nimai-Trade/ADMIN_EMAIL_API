package com.nimai.admin.payload;

import java.util.Date;

public class SubscriptionMPlanResponse {

	private Integer subscriptionPlanId;
	private String customerType;
	private String countryName;
	private String planName;
	private int credits;
	private int subsidiaries;
	private String rm;
	private float pricing;
	private String validity;
	private String status;
	private Date createdDate;
	private String createdBy;
	private String customerSupport;
	private String remark;
	private String countryCurrency; 
	private String modifiedBy;
	
	
	
	
	public String getModifiedBy() {
		return modifiedBy;
	}

	public void setModifiedBy(String modifiedBy) {
		this.modifiedBy = modifiedBy;
	}

	public Integer getSubscriptionPlanId() {
		return subscriptionPlanId;
	}

	public void setSubscriptionPlanId(Integer subscriptionPlanId) {
		this.subscriptionPlanId = subscriptionPlanId;
	}

	public String getCustomerType() {
		return customerType;
	}

	public void setCustomerType(String customerType) {
		this.customerType = customerType;
	}

	public String getCountryName() {
		return countryName;
	}

	public void setCountryName(String countryName) {
		this.countryName = countryName;
	}

	public String getPlanName() {
		return planName;
	}

	public void setPlanName(String planName) {
		this.planName = planName;
	}

	public int getCredits() {
		return credits;
	}

	public void setCredits(int credits) {
		this.credits = credits;
	}

	public int getSubsidiaries() {
		return subsidiaries;
	}

	public void setSubsidiaries(int subsidiaries) {
		this.subsidiaries = subsidiaries;
	}

	public String getRm() {
		return rm;
	}

	public void setRm(String rm) {
		this.rm = rm;
	}

	public float getPricing() {
		return pricing;
	}

	public void setPricing(float pricing) {
		this.pricing = pricing;
	}

	public String getValidity() {
		return validity;
	}

	public void setValidity(String validity) {
		this.validity = validity;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public String getCustomerSupport() {
		return customerSupport;
	}

	public void setCustomerSupport(String customerSupport) {
		this.customerSupport = customerSupport;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getCountryCurrency() {
		return countryCurrency;
	}

	public void setCountryCurrency(String countryCurrency) {
		this.countryCurrency = countryCurrency;
	}
	
	

}
