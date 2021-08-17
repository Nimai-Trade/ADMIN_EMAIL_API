package com.nimai.admin.payload;

import java.util.Date;

public class VasCheckerResponse {
	private Integer vasid;

	private String countryName;

	private String planName;

	private String description1;

	private String description2;

	private String description3;

	private String description4;

	private String description5;

	private float pricing;

	private String status;

	private String createdBy;

	private Date createdDate;

	private String modifiedBy;

	private Date modifiedDate;

	public Integer getVasid() {
		return vasid;
	}

	public void setVasid(Integer vasid) {
		this.vasid = vasid;
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

	public VasCheckerResponse() {
		super();
		// TODO Auto-generated constructor stub
	}

	public VasCheckerResponse(Integer vasid, String countryName, String planName, String description1,
			String description2, String description3, String description4, String description5, float pricing,
			String status, String createdBy, Date createdDate, String modifiedBy, Date modifiedDate) {
		super();
		this.vasid = vasid;
		this.countryName = countryName;
		this.planName = planName;
		this.description1 = description1;
		this.description2 = description2;
		this.description3 = description3;
		this.description4 = description4;
		this.description5 = description5;
		this.pricing = pricing;
		this.status = status;
		this.createdBy = createdBy;
		this.createdDate = createdDate;
		this.modifiedBy = modifiedBy;
		this.modifiedDate = modifiedDate;
	}
}
