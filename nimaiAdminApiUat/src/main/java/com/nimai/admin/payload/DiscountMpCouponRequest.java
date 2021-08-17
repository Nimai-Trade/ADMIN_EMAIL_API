package com.nimai.admin.payload;

import java.util.Date;

import com.nimai.admin.model.NimaiMCustomer;
import com.nimai.admin.model.NimaiMDiscount;

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

	public DiscountMpCouponRequest() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
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

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public Integer getVas() {
		return vas;
	}

	public void setVas(Integer vas) {
		this.vas = vas;
	}

	public String getSubscriptionPlan() {
		return subscriptionPlan;
	}

	public void setSubscriptionPlan(String subscriptionPlan) {
		this.subscriptionPlan = subscriptionPlan;
	}

	public String getCurrentStatus() {
		return currentStatus;
	}

	public void setCurrentStatus(String currentStatus) {
		this.currentStatus = currentStatus;
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

	public Integer getCreditsRemaining() {
		return creditsRemaining;
	}

	public void setCreditsRemaining(Integer creditsRemaining) {
		this.creditsRemaining = creditsRemaining;
	}

	public String getCouponCode() {
		return couponCode;
	}

	public void setCouponCode(String couponCode) {
		this.couponCode = couponCode;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public NimaiMDiscount getDiscountId() {
		return discountId;
	}

	public void setDiscountId(NimaiMDiscount discountId) {
		this.discountId = discountId;
	}

	public NimaiMCustomer getUserid() {
		return userid;
	}

	public void setUserid(NimaiMCustomer userid) {
		this.userid = userid;
	}
}
