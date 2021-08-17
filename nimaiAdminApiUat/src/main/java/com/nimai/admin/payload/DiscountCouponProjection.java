package com.nimai.admin.payload;

import java.util.Date;
import java.util.List;

public class DiscountCouponProjection {

	private String userid;
	private String firstName;
	private String lastName;
	private String countryName;
	private String companyName;
	private Integer isVasApplied;
	private String subscriptionName;
	private String status;
	private Date startDate;
	private Date endDate;
	private Double creditsRemaining;
	private List<String> coupon;
	
	
	public DiscountCouponProjection() {
		super();
		// TODO Auto-generated constructor stub
	}

	
	public List<String> getCoupon() {
		return coupon;
	}


	public void setCoupon(List<String> coupon) {
		this.coupon = coupon;
	}


	public String getUserid() {
		return userid;
	}

	public void setUserid(String userid) {
		this.userid = userid;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getCountryName() {
		return countryName;
	}

	public void setCountryName(String countryName) {
		this.countryName = countryName;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public Integer getIsVasApplied() {
		return isVasApplied;
	}

	public void setIsVasApplied(Integer isVasApplied) {
		this.isVasApplied = isVasApplied;
	}

	public String getSubscriptionName() {
		return subscriptionName;
	}

	public void setSubscriptionName(String subscriptionName) {
		this.subscriptionName = subscriptionName;
	}

	public String getStatus() {
		return status;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Double getCreditsRemaining() {
		return creditsRemaining;
	}

	public void setCreditsRemaining(Double creditsRemaining) {
		this.creditsRemaining = creditsRemaining;
	}

}
