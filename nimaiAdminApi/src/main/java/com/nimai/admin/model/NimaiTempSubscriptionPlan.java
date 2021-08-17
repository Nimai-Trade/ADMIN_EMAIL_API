package com.nimai.admin.model;

import java.util.Date;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.DynamicUpdate;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity
@Table(name = "nimai_temp_subscription_plan")
@EntityListeners(AuditingEntityListener.class)
@DynamicUpdate
public class NimaiTempSubscriptionPlan {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Basic(optional = false)
	@Column(name = "SUBSCRIPTION_ID")
	private int subscriptionId;

	@Column(name = "CUSTOMER_TYPE")
	private String customerType;

	@Column(name = "COUNTRY_NAME")
	private String countryName;

	@Column(name = "PLAN_NAME")
	private String planName;

	@Column(name = "CREDITS")
	private int credits;

	@Column(name = "SUBSIDIARIES")
	private int subsidiaries;

	@Column(name = "RM")
	private String rm;

	@Column(name = "PRICING")
	private float pricing;

	@Column(name = "VALIDITY")
	private int validity;

	@Column(name = "FLAG")
	private boolean flag;

	@Column(name = "STATUS")
	private String status;
	@CreatedBy
	@Column(name = "CREATED_BY")
	private String createdBy;
	@CreatedDate
	@Column(name = "CREATED_DATE")
	private Date createdDate;

	@Column(name = "MODIFIED_BY")
	private String modifiedBy;

	@Column(name = "MODIFIED_DATE")
	private Date modifiedDate;

	public NimaiTempSubscriptionPlan(int subscriptionId, String customerType, String countryName, String planName,
			int credits, int subsidiaries, String rm, float pricing, int validity, boolean flag, String status,
			String createdBy, Date createdDate, String modifiedBy, Date modifiedDate) {
		super();
		this.subscriptionId = subscriptionId;
		this.customerType = customerType;
		this.countryName = countryName;
		this.planName = planName;
		this.credits = credits;
		this.subsidiaries = subsidiaries;
		this.rm = rm;
		this.pricing = pricing;
		this.validity = validity;
		this.flag = flag;
		this.status = status;
		this.createdBy = createdBy;
		this.createdDate = createdDate;
		this.modifiedBy = modifiedBy;
		this.modifiedDate = modifiedDate;
	}

	public NimaiTempSubscriptionPlan() {
		super();
		// TODO Auto-generated constructor stub
	}

	public int getSubscriptionId() {
		return subscriptionId;
	}

	public void setSubscriptionId(int subscriptionId) {
		this.subscriptionId = subscriptionId;
	}

	public String getCustomerType() {
		return customerType;
	}

	public void setCustomerType(String customerType) {
		this.customerType = customerType;
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

	public int getCredits() {
		return credits;
	}

	public void setCredits(int credits) {
		this.credits = credits;
	}

	public int getSubsidiaries() {
		return subsidiaries;
	}

	public void setSubsidiaries(int subsidiaries) {
		this.subsidiaries = subsidiaries;
	}

	public String getRm() {
		return rm;
	}

	public void setRm(String rm) {
		this.rm = rm;
	}

	public float getPricing() {
		return pricing;
	}

	public void setPricing(float pricing) {
		this.pricing = pricing;
	}

	public int getValidity() {
		return validity;
	}

	public void setValidity(int validity) {
		this.validity = validity;
	}

	public boolean isFlag() {
		return flag;
	}

	public void setFlag(boolean flag) {
		this.flag = flag;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
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
