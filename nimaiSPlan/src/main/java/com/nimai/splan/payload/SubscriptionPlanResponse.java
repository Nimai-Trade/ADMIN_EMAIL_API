package com.nimai.splan.payload;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

public class SubscriptionPlanResponse {
	private String userId;
	private String subscriptionId;
	private String subscriptionName;
	private int subscriptionAmount;
	private String lcCount;
	private String remark;
	private String status;
	private int subscriptionValidity;
	private String subsidiaries;
	private String relationshipManager;
	private String customerSupport;
	private int vasAmount;
	private Double discount;
	private Double grandAmount;
	private int isVasApplied;
	private int discountId;
	private Date subsStartDate;
	private String invoiceId;
	
	
	
	public String getInvoiceId() {
		return invoiceId;
	}

	public void setInvoiceId(String invoiceId) {
		this.invoiceId = invoiceId;
	}

	public Date getSubsStartDate() {
		return subsStartDate;
	}

	public void setSubsStartDate(Date subsStartDate) {
		this.subsStartDate = subsStartDate;
	}

	public int getDiscountId() {
		return discountId;
	}

	public void setDiscountId(int discountId) {
		this.discountId = discountId;
	}

	public int getIsVasApplied() {
		return isVasApplied;
	}

	public void setIsVasApplied(int isVasApplied) {
		this.isVasApplied = isVasApplied;
	}

	public int getVasAmount() {
		return vasAmount;
	}

	public void setVasAmount(int vasAmount) {
		this.vasAmount = vasAmount;
	}

	public Double getDiscount() {
		return discount;
	}

	public void setDiscount(Double discount) {
		this.discount = discount;
	}

	public Double getGrandAmount() {
		return grandAmount;
	}

	public void setGrandAmount(Double grandAmount) {
		this.grandAmount = grandAmount;
	}

	public int getSubscriptionValidity() {
		return subscriptionValidity;
	}

	public void setSubscriptionValidity(int subscriptionValidity) {
		this.subscriptionValidity = subscriptionValidity;
	}

	public String getSubsidiaries() {
		return subsidiaries;
	}

	public void setSubsidiaries(String subsidiaries) {
		this.subsidiaries = subsidiaries;
	}

	public String getRelationshipManager() {
		return relationshipManager;
	}

	public void setRelationshipManager(String relationshipManager) {
		this.relationshipManager = relationshipManager;
	}

	public String getCustomerSupport() {
		return customerSupport;
	}

	public void setCustomerSupport(String customerSupport) {
		this.customerSupport = customerSupport;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getSubscriptionId() {
		return subscriptionId;
	}

	public void setSubscriptionId(String subscriptionId) {
		this.subscriptionId = subscriptionId;
	}

	public String getSubscriptionName() {
		return subscriptionName;
	}

	public void setSubscriptionName(String subscriptionName) {
		this.subscriptionName = subscriptionName;
	}

	public int getSubscriptionAmount() {
		return subscriptionAmount;
	}

	public void setSubscriptionAmount(int subscriptionAmount) {
		this.subscriptionAmount = subscriptionAmount;
	}

	public String getLcCount() {
		return lcCount;
	}

	public void setLcCount(String lcCount) {
		this.lcCount = lcCount;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}


}
