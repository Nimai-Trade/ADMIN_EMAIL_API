package com.nimai.admin.payload;

import java.sql.Time;
import java.util.Date;

public class DiscountCouponMResponse {
	private Integer discountId;

	private Integer quantity;
	private String couponCode;
	private String discountType;

	private double amount;

	private String currency;

	private double discountPercentage;

	private double maxDiscount;

	private String couponFor;

	private String subscriptionPlan;

	private String country;

	private Integer consumedCoupons;
	private String status;

	private Date startDate;

	private Time startTime;

	private Date endDate;

	private Time endTime;

	private String approvedBy;

	private Date approvalDate;

	private String createdBy;

	private Date createdDate;

	private String modifiedBy;

	private Date modifiedDate;

	public DiscountCouponMResponse() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Integer getDiscountId() {
		return discountId;
	}

	public void setDiscountId(Integer discountId) {
		this.discountId = discountId;
	}

	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

	public String getCouponCode() {
		return couponCode;
	}

	public void setCouponCode(String couponCode) {
		this.couponCode = couponCode;
	}

	public String getDiscountType() {
		return discountType;
	}

	public void setDiscountType(String discountType) {
		this.discountType = discountType;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public double getDiscountPercentage() {
		return discountPercentage;
	}

	public void setDiscountPercentage(double discountPercentage) {
		this.discountPercentage = discountPercentage;
	}

	public double getMaxDiscount() {
		return maxDiscount;
	}

	public void setMaxDiscount(double maxDiscount) {
		this.maxDiscount = maxDiscount;
	}

	public String getCouponFor() {
		return couponFor;
	}

	public void setCouponFor(String couponFor) {
		this.couponFor = couponFor;
	}

	public String getSubscriptionPlan() {
		return subscriptionPlan;
	}

	public void setSubscriptionPlan(String subscriptionPlan) {
		this.subscriptionPlan = subscriptionPlan;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public Integer getConsumedCoupons() {
		return consumedCoupons;
	}

	public void setConsumedCoupons(Integer consumedCoupons) {
		this.consumedCoupons = consumedCoupons;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Time getStartTime() {
		return startTime;
	}

	public void setStartTime(Time startTime) {
		this.startTime = startTime;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public Time getEndTime() {
		return endTime;
	}

	public void setEndTime(Time endTime) {
		this.endTime = endTime;
	}

	public String getApprovedBy() {
		return approvedBy;
	}

	public void setApprovedBy(String approvedBy) {
		this.approvedBy = approvedBy;
	}

	public Date getApprovalDate() {
		return approvalDate;
	}

	public void setApprovalDate(Date approvalDate) {
		this.approvalDate = approvalDate;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
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
}
