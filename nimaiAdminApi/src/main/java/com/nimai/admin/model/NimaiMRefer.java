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
import javax.validation.constraints.Size;

/* by bashir*/
@Entity
@Table(name = "nimai_m_refer")
public class NimaiMRefer {

	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Basic(optional = false)
	@Column(name = "ID")
	private Integer id;
	@Size(max = 20)
	@Column(name = "REFERERNCEID")
	private String referernceid;
	@Size(max = 20)
	@Column(name = "FIRST_NAME")
	private String firstName;
	@Size(max = 20)
	@Column(name = "LAST_NAME")
	private String lastName;
	@Size(max = 30)
	@Column(name = "EMAIL_ADDRESS")
	private String emailAddress;
	@Size(max = 20)
	@Column(name = "MOBILE_NO")
	private String mobileNo;
	@Size(max = 20)
	@Column(name = "COUNTRY_NAME")
	private String countryName;
	@Size(max = 20)
	@Column(name = "COMPANY_NAME")
	private String companyName;
	@Size(max = 20)
	@Column(name = "STATUS")
	private String status;
	@Size(max = 20)
	@Column(name = "BRANCH_USER_ID")
	private String branchUserId;
	@Column(name = "INSERTED_DATE")
	private Date insertedDate;
	@Column(name = "MODIFIED_DATE")
	private Date modifiedDate;
	@Size(max = 50)
	@Column(name = "INSERTED_BY")
	private String insertedBy;
	@Size(max = 50)
	@Column(name = "MODIFIED_BY")
	private String modifiedBy;
	@Size(max = 50)
	@Column(name = "token")
	private String token;
	@Column(name = "token_expiry_time")
	private Date tokenExpiryTime;
	@Column(name = "token_inserted_date")
	private Date tokenInsertedDate;
	@JoinColumn(name = "USERID", referencedColumnName = "USERID")
	@ManyToOne(optional = false)
	private NimaiMCustomer userid;

	public NimaiMRefer() {
		super();

	}

	public String getBranchUserId() {
		return branchUserId;
	}

	public void setBranchUserId(String branchUserId) {
		this.branchUserId = branchUserId;
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

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getReferernceid() {
		return referernceid;
	}

	public void setReferernceid(String referernceid) {
		this.referernceid = referernceid;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public Date getTokenExpiryTime() {
		return tokenExpiryTime;
	}

	public void setTokenExpiryTime(Date tokenExpiryTime) {
		this.tokenExpiryTime = tokenExpiryTime;
	}

	public Date getTokenInsertedDate() {
		return tokenInsertedDate;
	}

	public void setTokenInsertedDate(Date tokenInsertedDate) {
		this.tokenInsertedDate = tokenInsertedDate;
	}

	public NimaiMCustomer getUserid() {
		return userid;
	}

	public void setUserid(NimaiMCustomer userid) {
		this.userid = userid;
	}
	

}
