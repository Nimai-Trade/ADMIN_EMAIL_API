package com.nimai.admin.payload;

import com.nimai.admin.model.NimaiMCustomer;
import com.nimai.admin.model.NimaiMDiscount;
import java.util.Date;

public class DiscountMpCouponRequest {
  private Integer id;
  
  private String firstName;
  
  private String lastName;
  
  private String country;
  
  private String companyName;
  
  private Integer vas;
  
  private String subscriptionPlan;
  
  private String currentStatus;
  
  private Date startDate;
  
  private Date endDate;
  
  private Integer creditsRemaining;
  
  private String couponCode;
  
  private String status;
  
  private NimaiMDiscount discountId;
  
  private NimaiMCustomer userid;
  
  private String[] countryName;
  
  public String[] getCountryName() {
    return this.countryName;
  }
  
  public void setCountryName(String[] countryName) {
    this.countryName = countryName;
  }
  
  public Integer getId() {
    return this.id;
  }
  
  public void setId(Integer id) {
    this.id = id;
  }
  
  public String getFirstName() {
    return this.firstName;
  }
  
  public void setFirstName(String firstName) {
    this.firstName = firstName;
  }
  
  public String getLastName() {
    return this.lastName;
  }
  
  public void setLastName(String lastName) {
    this.lastName = lastName;
  }
  
  public String getCountry() {
    return this.country;
  }
  
  public void setCountry(String country) {
    this.country = country;
  }
  
  public String getCompanyName() {
    return this.companyName;
  }
  
  public void setCompanyName(String companyName) {
    this.companyName = companyName;
  }
  
  public Integer getVas() {
    return this.vas;
  }
  
  public void setVas(Integer vas) {
    this.vas = vas;
  }
  
  public String getSubscriptionPlan() {
    return this.subscriptionPlan;
  }
  
  public void setSubscriptionPlan(String subscriptionPlan) {
    this.subscriptionPlan = subscriptionPlan;
  }
  
  public String getCurrentStatus() {
    return this.currentStatus;
  }
  
  public void setCurrentStatus(String currentStatus) {
    this.currentStatus = currentStatus;
  }
  
  public Date getStartDate() {
    return this.startDate;
  }
  
  public void setStartDate(Date startDate) {
    this.startDate = startDate;
  }
  
  public Date getEndDate() {
    return this.endDate;
  }
  
  public void setEndDate(Date endDate) {
    this.endDate = endDate;
  }
  
  public Integer getCreditsRemaining() {
    return this.creditsRemaining;
  }
  
  public void setCreditsRemaining(Integer creditsRemaining) {
    this.creditsRemaining = creditsRemaining;
  }
  
  public String getCouponCode() {
    return this.couponCode;
  }
  
  public void setCouponCode(String couponCode) {
    this.couponCode = couponCode;
  }
  
  public String getStatus() {
    return this.status;
  }
  
  public void setStatus(String status) {
    this.status = status;
  }
  
  public NimaiMDiscount getDiscountId() {
    return this.discountId;
  }
  
  public void setDiscountId(NimaiMDiscount discountId) {
    this.discountId = discountId;
  }
  
  public NimaiMCustomer getUserid() {
    return this.userid;
  }
  
  public void setUserid(NimaiMCustomer userid) {
    this.userid = userid;
  }
}
