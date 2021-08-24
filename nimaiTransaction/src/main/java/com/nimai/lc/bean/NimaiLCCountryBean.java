package com.nimai.lc.bean;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

public class NimaiLCCountryBean {
	
	private Integer countryId;
	private String  transactionId;
	private String	userId;
	private String countryName;
	private String	status;
	private String insertedBy;
	
	@JsonFormat(pattern = "yyyy-MM-dd")
	private Date insertedDate;
	
	private String modifiedBy;
	
	@JsonFormat(pattern = "yyyy-MM-dd")
	private Date modifiedDate;
	
	private NimaiLCBean nimailccountry;
	
	public String getTransactionId() {
		return transactionId;
	}
	public void setTransactionId(String transactionId) {
		this.transactionId = transactionId;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getCountryName() {
		return countryName;
	}
	public void setCountryName(String countryName) {
		this.countryName = countryName;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getInsertedBy() {
		return insertedBy;
	}
	public void setInsertedBy(String insertedBy) {
		this.insertedBy = insertedBy;
	}
	
	public Date getInsertedDate() {
		return insertedDate;
	}
	public void setInsertedDate(Date insertedDate) {
		this.insertedDate = insertedDate;
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
	public Integer getCountryId() {
		return countryId;
	}
	public void setCountryId(Integer countryId) {
		this.countryId = countryId;
	}
	public NimaiLCBean getNimailccountry() {
		return nimailccountry;
	}
	public void setNimailccountry(NimaiLCBean nimailccountry) {
		this.nimailccountry = nimailccountry;
	}

	
	
	
	

}
