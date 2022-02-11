package com.nimai.splan.payload;

import java.util.Date;


public class NimaiAfterVasSubscriptionBean 
{
	private Integer id;
	private String userId;
	private String subscriptionId;
	private String vasId;
	private String countryName;
	private String planName;
	private String description_1;
	private String description_2;
	private String description_3;
	private String description_4;
	private String description_5;
	private String currency;
	private Float pricing;
	private String status;
	private String insertedBy;
	private Date insertedDate;
	private String modifiedBy;
	private Date modifiedDate;
	private String mode;
	private String paymentSts;
	private String paymentTxnId;
	private String invoiceId;
	
	
	
	public String getInvoiceId() {
		return invoiceId;
	}
	public void setInvoiceId(String invoiceId) {
		this.invoiceId = invoiceId;
	}
	public String getPaymentSts() {
		return paymentSts;
	}
	public void setPaymentSts(String paymentSts) {
		this.paymentSts = paymentSts;
	}
	public String getPaymentTxnId() {
		return paymentTxnId;
	}
	public void setPaymentTxnId(String paymentTxnId) {
		this.paymentTxnId = paymentTxnId;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
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
	public String getVasId() {
		return vasId;
	}
	public void setVasId(String vasId) {
		this.vasId = vasId;
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
	public String getDescription_1() {
		return description_1;
	}
	public void setDescription_1(String description_1) {
		this.description_1 = description_1;
	}
	public String getDescription_2() {
		return description_2;
	}
	public void setDescription_2(String description_2) {
		this.description_2 = description_2;
	}
	public String getDescription_3() {
		return description_3;
	}
	public void setDescription_3(String description_3) {
		this.description_3 = description_3;
	}
	public String getDescription_4() {
		return description_4;
	}
	public void setDescription_4(String description_4) {
		this.description_4 = description_4;
	}
	public String getDescription_5() {
		return description_5;
	}
	public void setDescription_5(String description_5) {
		this.description_5 = description_5;
	}
	public String getCurrency() {
		return currency;
	}
	public void setCurrency(String currency) {
		this.currency = currency;
	}
	public Float getPricing() {
		return pricing;
	}
	public void setPricing(Float pricing) {
		this.pricing = pricing;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getInsertedBy() {
		return insertedBy;
	}
	public void setInsertedBy(String insertedBy) {
		this.insertedBy = insertedBy;
	}
	public Date getInsertedDate() {
		return insertedDate;
	}
	public void setInsertedDate(Date insertedDate) {
		this.insertedDate = insertedDate;
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
	public String getMode() {
		return mode;
	}
	public void setMode(String mode) {
		this.mode = mode;
	}
	
	
}
