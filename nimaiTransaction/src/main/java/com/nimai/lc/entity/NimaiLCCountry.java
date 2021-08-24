package com.nimai.lc.entity;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.nimai.lc.bean.NimaiLCBean;

@Entity
@Table(name="TRANSCOUNTRIES_TEMP")
public class NimaiLCCountry {
	
	@Id
	@Column(name="COUNTRY_ID") 
	private Integer countryId;

//	@Column(name="TRANSACTION_ID") 
//	private String  transactionId;
	
	@Column(name="USER_ID") 
	private String	userId;
	
	@Column(name="COUNTRY_NAME") 
	private String countryName;
	
	@Column(name="STATUS") 
	private String	status;
	
	@Column(name="INSERTED_BY") 
	private String insertedBy;
	
	@Column(name="INSERTED_DATE") 
	private Date	insertedDate;
	
	@Column(name="MODIFIED_BY") 
	private String	modifiedBy;
	
	@Column(name="MODIFIED_DATE") 
	private Date modifiedDate;
	
//	public String getTransactionId() {
//		return transactionId;
//	}
//	public void setTransactionId(String transactionId) {
//		this.transactionId = transactionId;
//	}
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
	
	public String getModifiedBy() {
		return modifiedBy;
	}
	public void setModifiedBy(String modifiedBy) {
		this.modifiedBy = modifiedBy;
	}
	public Date getInsertedDate() {
		return insertedDate;
	}
	public void setInsertedDate(Date insertedDate) {
		this.insertedDate = insertedDate;
	}
	public Date getModifiedDate() {
		return modifiedDate;
	}
	public void setModifiedDate(Date modifiedDate) {
		this.modifiedDate = modifiedDate;
	}
	
	
	@JoinColumn(name = "TRANSACTION_ID",referencedColumnName = "TRANSACTION_ID" )
	@ManyToOne(cascade = CascadeType.ALL,fetch=FetchType.LAZY )
	@JsonBackReference
	private NimaiLC nimailccountry;

	public NimaiLC getNimailccountry() {
		return nimailccountry;
	}
	
	public void setNimailccountry(NimaiLC nimailccountry) {
		this.nimailccountry = nimailccountry;
	}
	public Integer getCountryId() {
		return countryId;
	}
	public void setCountryId(Integer countryId) {
		this.countryId = countryId;
	}
	
	
}
