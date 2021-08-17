package com.nimai.admin.model;

import java.util.Date;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.DynamicUpdate;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity
@Table(name = "nimai_m_vas")
@EntityListeners(AuditingEntityListener.class)
@DynamicUpdate
public class NimaiMVas {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Basic(optional = false)
	@Column(name = "VAS_ID")
	private int vasid;
	@Column(name = "CUSTOMER_TYPE")
	private String customerType;
	@Column(name = "COUNTRY_NAME")
	private String countryName;
	@Column(name = "PLAN_NAME")
	private String planName;
	@Column(name = "DESCRIPTION_1")
	private String description1;
	@Column(name = "DESCRIPTION_2")
	private String description2;
	@Column(name = "DESCRIPTION_3")
	private String description3;
	@Column(name = "DESCRIPTION_4")
	private String description4;
	@Column(name = "DESCRIPTION_5")
	private String description5;
	@Column(name = "PRICING")
	private float pricing;
	
	



	@Column(name = "STATUS")
	private String status;

	@Column(name = "CURRENCY")
	private String currency;

	@CreatedBy
	@Column(name = "CREATED_BY")
	private String createdBy;

	@CreatedDate
	@Column(name = "CREATED_DATE")
	private Date createdDate;

	@Column(name = "MODIFIED_BY")
	@LastModifiedBy
	private String modifiedBy;

	@LastModifiedDate
	@Column(name = "MODIFIED_DATE")
	private Date modifiedDate;

	public NimaiMVas() {
		super();
		// TODO Auto-generated constructor stub
	}

	public int getVasid() {
		return vasid;
	}

	public void setVasid(int vasid) {
		this.vasid = vasid;
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

	public String getDescription1() {
		return description1;
	}

	public void setDescription1(String description1) {
		this.description1 = description1;
	}

	public String getDescription2() {
		return description2;
	}

	public void setDescription2(String description2) {
		this.description2 = description2;
	}

	public String getDescription3() {
		return description3;
	}

	public void setDescription3(String description3) {
		this.description3 = description3;
	}

	public String getDescription4() {
		return description4;
	}

	public void setDescription4(String description4) {
		this.description4 = description4;
	}

	public String getDescription5() {
		return description5;
	}

	public void setDescription5(String description5) {
		this.description5 = description5;
	}

	public float getPricing() {
		return pricing;
	}

	public void setPricing(float pricing) {
		this.pricing = pricing;
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

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

}
