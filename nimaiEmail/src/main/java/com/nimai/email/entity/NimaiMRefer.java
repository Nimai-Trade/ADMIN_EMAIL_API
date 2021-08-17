package com.nimai.email.entity;

import java.io.Serializable;
import java.sql.Timestamp;
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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "nimai_m_refer")
public class NimaiMRefer implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@Basic(optional = false)
	@NotNull
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private int id;
	
	
	@Column(name = "REFERERNCEID")
	private String referenceId;

	@Column(name = "FIRST_NAME")
	private String firstName;

	@Column(name = "LAST_NAME")
	private String lastName;

	@Column(name = "EMAIL_ADDRESS")
	private String emailAddress;

	@Column(name = "MOBILE_NO")
	private String mobileNo;

	@Column(name = "COUNTRY_NAME")
	private String countryName;

	@Column(name = "COMPANY_NAME")
	private String companyName;

	@Column(name = "STATUS")
	private String status;

	@Column(name = "INSERTED_DATE")
	@Temporal(value = TemporalType.TIMESTAMP)
	private Date insertedDate;

	@Column(name = "MODIFIED_DATE")
	@Temporal(value = TemporalType.TIMESTAMP)
	private Date modifiedDate;

	@Column(name = "BRANCH_USER_ID")
	private String branchUserId;

	@Column(name = "INSERTED_BY")
	private String insertedBy;

	@Column(name = "MODIFIED_BY")
	private String modifiedBy;

	@Column(name = "token")
	private String token;

	@Column(name = "token_expiry_time")
	private Date tokenExpiryTime;

	@Column(name = "token_inserted_date")
	private Date tokenInsertedDate;
	
	@JoinColumn(name = "USERID", referencedColumnName = "USERID")
	@ManyToOne(optional = false)
	private NimaiClient userid;

	
	
	
	
	
  

	/**
	 * @return the id
	 */
	public int getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(int id) {
		this.id = id;
	}

	/**
	 * @return the userid
	 */
	public NimaiClient getUserid() {
		return userid;
	}

	/**
	 * @param userid the userid to set
	 */
	public void setUserid(NimaiClient userid) {
		this.userid = userid;
	}

	/**
	 * @return the tokenExpiryTime
	 */
	public Date getTokenExpiryTime() {
		return tokenExpiryTime;
	}

	/**
	 * @param tokenExpiryTime the tokenExpiryTime to set
	 */
	public void setTokenExpiryTime(Date tokenExpiryTime) {
		this.tokenExpiryTime = tokenExpiryTime;
	}

	/**
	 * @return the tokenInsertedDate
	 */
	public Date getTokenInsertedDate() {
		return tokenInsertedDate;
	}

	/**
	 * @param tokenInsertedDate the tokenInsertedDate to set
	 */
	public void setTokenInsertedDate(Date tokenInsertedDate) {
		this.tokenInsertedDate = tokenInsertedDate;
	}

	/**
	 * @return the token
	 */
	public String getToken() {
		return token;
	}

	/**
	 * @param token the token to set
	 */
	public void setToken(String token) {
		this.token = token;
	}


	/**
	 * @param tokenInsertedDate the tokenInsertedDate to set
	 */
	public void setTokenInsertedDate(Timestamp tokenInsertedDate) {
		this.tokenInsertedDate = tokenInsertedDate;
	}



	public String getReferenceId() {
		return referenceId;
	}

	public void setReferenceId(String referenceId) {
		this.referenceId = referenceId;
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

	public String getEmailAddress() {
		return emailAddress;
	}

	public void setEmailAddress(String emailAddress) {
		this.emailAddress = emailAddress;
	}

	public String getMobileNo() {
		return mobileNo;
	}

	public void setMobileNo(String mobileNo) {
		this.mobileNo = mobileNo;
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

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
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

	public String getBranchUserId() {
		return branchUserId;
	}

	public void setBranchUserId(String branchUserId) {
		this.branchUserId = branchUserId;
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

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}
