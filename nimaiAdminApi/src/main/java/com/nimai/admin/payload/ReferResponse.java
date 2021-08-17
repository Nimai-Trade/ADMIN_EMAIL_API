package com.nimai.admin.payload;
/*bashir */
import java.util.Date;

public class ReferResponse {

	private String referenceId;

	private String branchUserId;

	private String userid;

	private String firstName;

	public ReferResponse(String referenceId, String branchUserId, String userid, String firstName, String lastName,
			String emailAddress, String mobileNo, String countryName, String companyName, String status,
			Date insertedDate, Date modifiedDate, String insertedBy, String modifiedBy) {
		super();
		this.referenceId = referenceId;
		this.branchUserId = branchUserId;
		this.userid = userid;
		this.firstName = firstName;
		this.lastName = lastName;
		this.emailAddress = emailAddress;
		this.mobileNo = mobileNo;
		this.countryName = countryName;
		this.companyName = companyName;
		this.status = status;
		this.insertedDate = insertedDate;
		this.modifiedDate = modifiedDate;
		this.insertedBy = insertedBy;
		this.modifiedBy = modifiedBy;
	}

	public ReferResponse() {
		super();
		// TODO Auto-generated constructor stub
	}

	public String getReferenceId() {
		return referenceId;
	}

	public void setReferenceId(String referenceId) {
		this.referenceId = referenceId;
	}

	public String getBranchUserId() {
		return branchUserId;
	}

	public void setBranchUserId(String branchUserId) {
		this.branchUserId = branchUserId;
	}

	public String getUserid() {
		return userid;
	}

	public void setUserid(String userid) {
		this.userid = userid;
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

	private String lastName;

	private String emailAddress;

	private String mobileNo;

	private String countryName;

	private String companyName;

	private String status;

	private Date insertedDate;

	private Date modifiedDate;

	private String insertedBy;

	private String modifiedBy;
}
