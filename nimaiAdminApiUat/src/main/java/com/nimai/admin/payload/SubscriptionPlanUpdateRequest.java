package com.nimai.admin.payload;

import java.util.Date;

public class SubscriptionPlanUpdateRequest {

	private int subscriptionPlanId;
	private String countryName;
	private String status;
	private String modifiedBy;
	private Date modifiedDate;
	private String customerType;

	public String getCustomerType() {
		return customerType;
	}

	public void setCustomerType(String customerType) {
		this.customerType = customerType;
	}

	public String getModifiedBy() {
		return modifiedBy;
	}

	public void setModifiedBy(String modifiedBy) {
		this.modifiedBy = modifiedBy;
	}

	public Date getModifiedDate() {
		return modifiedDate;
	}

	public void setModifiedDate(Date modifiedDate) {
		this.modifiedDate = modifiedDate;
	}

	public SubscriptionPlanUpdateRequest() {
		super();
		// TODO Auto-generated constructor stub
	}

	public int getSubscriptionPlanId() {
		return subscriptionPlanId;
	}

	public void setSubscriptionPlanId(int subscriptionPlanId) {
		this.subscriptionPlanId = subscriptionPlanId;
	}

	public String getCountryName() {
		return countryName;
	}

	public void setCountryName(String countryName) {
		this.countryName = countryName;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
}
