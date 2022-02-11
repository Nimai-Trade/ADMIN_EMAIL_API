package com.nimai.splan.payload;

import java.util.Date;


public class CustomerSubscriptionGrandAmountBean 
{
	private Integer id;
	private String userId;
	private Double grandAmount;
	private String discountApplied;
	private String vasApplied;
	private Date insertedDate;
	private String subscriptionId;
	private String vasId;
	private Double discountId;
	
	
	
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
	public Double getDiscountId() {
		return discountId;
	}
	public void setDiscountId(Double discountId) {
		this.discountId = discountId;
	}
	public String getVasApplied() {
		return vasApplied;
	}
	public void setVasApplied(String vasApplied) {
		this.vasApplied = vasApplied;
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
	public Double getGrandAmount() {
		return grandAmount;
	}
	public void setGrandAmount(Double grandAmount) {
		this.grandAmount = grandAmount;
	}
	
	public String getDiscountApplied() {
		return discountApplied;
	}
	public void setDiscountApplied(String discountApplied) {
		this.discountApplied = discountApplied;
	}
	public Date getInsertedDate() {
		return insertedDate;
	}
	public void setInsertedDate(Date insertedDate) {
		this.insertedDate = insertedDate;
	}
	
	
}
