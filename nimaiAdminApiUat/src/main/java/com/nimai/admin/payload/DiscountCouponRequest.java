package com.nimai.admin.payload;

import java.sql.Time;
import java.util.Date;
import java.util.List;

public class DiscountCouponRequest {
  private Integer discountId;
  
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
  
  private String[] countryName;
  
  private List<DisQuantityAndCouponCode> details;
  
  public String[] getCountryName() {
    return this.countryName;
  }
  
  public void setCountryName(String[] countryName) {
    this.countryName = countryName;
  }
  
  public Integer getDiscountId() {
    return this.discountId;
  }
  
  public void setDiscountId(Integer discountId) {
    this.discountId = discountId;
  }
  
  public String getDiscountType() {
    return this.discountType;
  }
  
  public void setDiscountType(String discountType) {
    this.discountType = discountType;
  }
  
  public double getAmount() {
    return this.amount;
  }
  
  public void setAmount(double amount) {
    this.amount = amount;
  }
  
  public String getCurrency() {
    return this.currency;
  }
  
  public void setCurrency(String currency) {
    this.currency = currency;
  }
  
  public double getDiscountPercentage() {
    return this.discountPercentage;
  }
  
  public void setDiscountPercentage(double discountPercentage) {
    this.discountPercentage = discountPercentage;
  }
  
  public double getMaxDiscount() {
    return this.maxDiscount;
  }
  
  public void setMaxDiscount(double maxDiscount) {
    this.maxDiscount = maxDiscount;
  }
  
  public String getCouponFor() {
    return this.couponFor;
  }
  
  public void setCouponFor(String couponFor) {
    this.couponFor = couponFor;
  }
  
  public String getSubscriptionPlan() {
    return this.subscriptionPlan;
  }
  
  public void setSubscriptionPlan(String subscriptionPlan) {
    this.subscriptionPlan = subscriptionPlan;
  }
  
  public String getCountry() {
    return this.country;
  }
  
  public void setCountry(String country) {
    this.country = country;
  }
  
  public Integer getConsumedCoupons() {
    return this.consumedCoupons;
  }
  
  public void setConsumedCoupons(Integer consumedCoupons) {
    this.consumedCoupons = consumedCoupons;
  }
  
  public Date getStartDate() {
    return this.startDate;
  }
  
  public void setStartDate(Date startDate) {
    this.startDate = startDate;
  }
  
  public Time getStartTime() {
    return this.startTime;
  }
  
  public void setStartTime(Time startTime) {
    this.startTime = startTime;
  }
  
  public Date getEndDate() {
    return this.endDate;
  }
  
  public void setEndDate(Date endDate) {
    this.endDate = endDate;
  }
  
  public Time getEndTime() {
    return this.endTime;
  }
  
  public void setEndTime(Time endTime) {
    this.endTime = endTime;
  }
  
  public String getApprovedBy() {
    return this.approvedBy;
  }
  
  public void setApprovedBy(String approvedBy) {
    this.approvedBy = approvedBy;
  }
  
  public Date getApprovalDate() {
    return this.approvalDate;
  }
  
  public void setApprovalDate(Date approvalDate) {
    this.approvalDate = approvalDate;
  }
  
  public String getCreatedBy() {
    return this.createdBy;
  }
  
  public void setCreatedBy(String createdBy) {
    this.createdBy = createdBy;
  }
  
  public Date getCreatedDate() {
    return this.createdDate;
  }
  
  public void setCreatedDate(Date createdDate) {
    this.createdDate = createdDate;
  }
  
  public String getModifiedBy() {
    return this.modifiedBy;
  }
  
  public void setModifiedBy(String modifiedBy) {
    this.modifiedBy = modifiedBy;
  }
  
  public Date getModifiedDate() {
    return this.modifiedDate;
  }
  
  public void setModifiedDate(Date modifiedDate) {
    this.modifiedDate = modifiedDate;
  }
  
  public String getStatus() {
    return this.status;
  }
  
  public void setStatus(String status) {
    this.status = status;
  }
  
  public List<DisQuantityAndCouponCode> getDetails() {
    return this.details;
  }
  
  public void setDetails(List<DisQuantityAndCouponCode> details) {
    this.details = details;
  }
}
