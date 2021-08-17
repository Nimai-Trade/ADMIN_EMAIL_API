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
@Table(name = "nimai_m_subscription")
@EntityListeners(AuditingEntityListener.class)
@DynamicUpdate
public class NimaiMSubscriptionPlan {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Basic(optional = false)
	@Column(name = "SUBS_PLAN_ID")
	private int subscriptionPlanId;
	@Column(name = "CUSTOMER_TYPE")
	private String customerType;
	@Column(name = "COUNTRY_NAME")
	private String countryName;
	@Column(name = "SUBSCRIPTION_NAME")
	private String planName;

	@Column(name = "SUBSCRIPTION_ID")
	private String subscriptionId;

	@Column(name = "CUSTOMER_SUPPORT")
	private String customerSupport;

	@Column(name = "REMARK")
	private String remark;

	@Column(name = "LC_COUNT")
	private int credits;
	@Column(name = "SUBSIDIARIES")
	private int subsidiaries;
	@Column(name = "RELATIONSHIP_MANAGER")
	private String rm;
	@Column(name = "SUBSCRIPTION_AMOUNT")
	private float pricing;
	@Column(name = "SUBSCRIPTION_VALIDITY")
	private int validity;
	@Column(name = "STATUS")
	private String status;

	@Column(name = "APPROVED_BY")
	private String approvedBy;

	@Column(name = "APPROVAL_DATE")
	private Date approvedDate;

	@CreatedBy
	@Column(name = "INSERTED_BY")
	private String createdBy;
	
	@CreatedDate
	@Column(name = "INSERTED_DATE")
	private Date createdDate;

	@Column(name = "MODIFIED_BY")
	private String modifiedBy;

	@Column(name = "MODIFIED_DATE")
	private Date modifiedDate;

	@Column(name = "CURRENCY")
	private String currency;

	public NimaiMSubscriptionPlan() {
		super();
		// TODO Auto-generated constructor stub
	}

	public int getSubscriptionPlanId() {
		return subscriptionPlanId;
	}

	public void setSubscriptionPlanId(int subscriptionPlanId) {
		this.subscriptionPlanId = subscriptionPlanId;
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

	public String getSubscriptionId() {
		return subscriptionId;
	}

	public void setSubscriptionId(String subscriptionId) {
		this.subscriptionId = subscriptionId;
	}

	public String getCustomerSupport() {
		return customerSupport;
	}

	public void setCustomerSupport(String customerSupport) {
		this.customerSupport = customerSupport;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
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

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getApprovedBy() {
		return approvedBy;
	}

	public void setApprovedBy(String approvedBy) {
		this.approvedBy = approvedBy;
	}

	public Date getApprovedDate() {
		return approvedDate;
	}

	public void setApprovedDate(Date approvedDate) {
		this.approvedDate = approvedDate;
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

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public void setModifiedDate(Date modifiedDate) {
		this.modifiedDate = modifiedDate;
	}

	public NimaiMSubscriptionPlan(int subscriptionPlanId, String customerType, String countryName, String planName,
			String subscriptionId, String customerSupport, String remark, int credits, int subsidiaries, String rm,
			float pricing, int validity, String status, String approvedBy, Date approvedDate, String createdBy,
			Date createdDate, String modifiedBy, Date modifiedDate) {
		super();
		this.subscriptionPlanId = subscriptionPlanId;
		this.customerType = customerType;
		this.countryName = countryName;
		this.planName = planName;
		this.subscriptionId = subscriptionId;
		this.customerSupport = customerSupport;
		this.remark = remark;
		this.credits = credits;
		this.subsidiaries = subsidiaries;
		this.rm = rm;
		this.pricing = pricing;
		this.validity = validity;
		this.status = status;
		this.approvedBy = approvedBy;
		this.approvedDate = approvedDate;
		this.createdBy = createdBy;
		this.createdDate = createdDate;
		this.modifiedBy = modifiedBy;
		this.modifiedDate = modifiedDate;
	}

}
