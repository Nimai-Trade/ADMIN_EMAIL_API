package com.nimai.admin.model;

import java.util.Date;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "nimai_mp_discount")
public class NimaiMpDiscount {
	@Id
	@Basic(optional = false)
	@Column(name = "ID")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@Column(name = "FIRST_NAME")
	private String firstName;

	@Column(name = "LAST_NAME")
	private String lastName;

	@Column(name = "COUNTRY")
	private String country;

	@Column(name = "COMPANY_NAME")
	private String companyName;

	@Column(name = "VAS")
	private Integer vas;

	@Column(name = "SUBSCRIPTION_PLAN")
	private String subscriptionPlan;

	@Column(name = "CURRENT_STATUS")
	private String currentStatus;

	@Column(name = "START_DATE")
	private Date startDate;

	@Column(name = "END_DATE")
	private Date endDate;

	@Column(name = "CREDITS_REMAINING")
	private Integer creditsRemaining;

	@Column(name = "COUPON_CODE")
	private String couponCode;

	@Column(name = "STATUS")
	private String status;

	@JoinColumn(name = "DISCOUNT_ID", referencedColumnName = "DISCOUNT_ID")
	@ManyToOne
	private NimaiMDiscount discountId;

	@JoinColumn(name = "USER_ID", referencedColumnName = "USERID")
	@ManyToOne
	private NimaiMCustomer userid;

	public NimaiMpDiscount() {
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

}
