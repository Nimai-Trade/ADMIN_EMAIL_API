package com.nimai.admin.payload;

import java.util.Date;

public class CustomerResponse {

	private String subscriberType;
	private String userid;
	private String firstName;
	private String lastName;
	private String emailAddress;
	private String mobileNumber;
	private String countryName;
	private String landline;
	private String designation;
	private String companyName;
	private String businessType;
	private Date insertedDate;
	private Date modifiedDate;
	private String bankName;
	private String branchName;
	private String swiftCode;
	private String registeredCountry;
	private String telephone;
	private String minValueofLc;
	private String ownerName;
	private String registrationType;
	private String provincename;
	private String address1;
	private String address2;
	private String address3;
	private String city;
	private String pincode;
	private String relationshipManager;
	private int planId;
	private String planOfPayments;
	private long totalTxn;
	private String kyc;
	private int totalReference;
	private long approvedReference;
	private long rejectedReference;
	private long pendingReference;
	private String earning;
	private String ccy;
	private String accountStatus;
	private Integer referId;

	private Date signUpDate;
	private String rmFirstName;
	private String rmLastName;
	private String rmDesignation;
	private String rmApprovedBy;

	public String getSubscriberType() {
		return subscriberType;
	}

	public void setSubscriberType(String subscriberType) {
		this.subscriberType = subscriberType;
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

	public String getMobileNumber() {
		return mobileNumber;
	}

	public void setMobileNumber(String mobileNumber) {
		this.mobileNumber = mobileNumber;
	}

	public String getCountryName() {
		return countryName;
	}

	public void setCountryName(String countryName) {
		this.countryName = countryName;
	}

	public String getLandline() {
		return landline;
	}

	public void setLandline(String landline) {
		this.landline = landline;
	}

	public String getDesignation() {
		return designation;
	}

	public void setDesignation(String designation) {
		this.designation = designation;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public String getBusinessType() {
		return businessType;
	}

	public void setBusinessType(String businessType) {
		this.businessType = businessType;
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

	public String getBankName() {
		return bankName;
	}

	public void setBankName(String bankName) {
		this.bankName = bankName;
	}

	public String getBranchName() {
		return branchName;
	}

	public void setBranchName(String branchName) {
		this.branchName = branchName;
	}

	public String getSwiftCode() {
		return swiftCode;
	}

	public void setSwiftCode(String swiftCode) {
		this.swiftCode = swiftCode;
	}

	public String getRegisteredCountry() {
		return registeredCountry;
	}

	public void setRegisteredCountry(String registeredCountry) {
		this.registeredCountry = registeredCountry;
	}

	public String getTelephone() {
		return telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	public String getMinValueofLc() {
		return minValueofLc;
	}

	public void setMinValueofLc(String minValueofLc) {
		this.minValueofLc = minValueofLc;
	}

	public String getOwnerName() {
		return ownerName;
	}

	public void setOwnerName(String ownerName) {
		this.ownerName = ownerName;
	}

	public String getRegistrationType() {
		return registrationType;
	}

	public void setRegistrationType(String registrationType) {
		this.registrationType = registrationType;
	}

	public String getProvincename() {
		return provincename;
	}

	public void setProvincename(String provincename) {
		this.provincename = provincename;
	}

	public String getAddress1() {
		return address1;
	}

	public void setAddress1(String address1) {
		this.address1 = address1;
	}

	public String getAddress2() {
		return address2;
	}

	public void setAddress2(String address2) {
		this.address2 = address2;
	}

	public String getAddress3() {
		return address3;
	}

	public void setAddress3(String address3) {
		this.address3 = address3;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getPincode() {
		return pincode;
	}

	public void setPincode(String pincode) {
		this.pincode = pincode;
	}

	public String getRelationshipManager() {
		return relationshipManager;
	}

	public void setRelationshipManager(String relationshipManager) {
		this.relationshipManager = relationshipManager;
	}

	public String getPlanOfPayments() {
		return planOfPayments;
	}

	public void setPlanOfPayments(String planOfPayments) {
		this.planOfPayments = planOfPayments;
	}

	public long getTotalTxn() {
		return totalTxn;
	}

	public void setTotalTxn(long totalTxn) {
		this.totalTxn = totalTxn;
	}

	public String getKyc() {
		return kyc;
	}

	public void setKyc(String kyc) {
		this.kyc = kyc;
	}

	public int getPlanId() {
		return planId;
	}

	public void setPlanId(int planId) {
		this.planId = planId;
	}

	public int getTotalReference() {
		return totalReference;
	}

	public void setTotalReference(int totalReference) {
		this.totalReference = totalReference;
	}

	public long getApprovedReference() {
		return approvedReference;
	}

	public void setApprovedReference(long approvedReference) {
		this.approvedReference = approvedReference;
	}

	public long getRejectedReference() {
		return rejectedReference;
	}

	public void setRejectedReference(long rejectedReference) {
		this.rejectedReference = rejectedReference;
	}

	public long getPendingReference() {
		return pendingReference;
	}

	public void setPendingReference(long pendingReference) {
		this.pendingReference = pendingReference;
	}

	public String getEarning() {
		return earning;
	}

	public void setEarning(String earning) {
		this.earning = earning;
	}

	public String getCcy() {
		return ccy;
	}

	public void setCcy(String ccy) {
		this.ccy = ccy;
	}

	public String getAccountStatus() {
		return accountStatus;
	}

	public void setAccountStatus(String accountStatus) {
		this.accountStatus = accountStatus;
	}

	public Integer getReferId() {
		return referId;
	}

	public void setReferId(Integer referId) {
		this.referId = referId;
	}

	public Date getSignUpDate() {
		return signUpDate;
	}

	public void setSignUpDate(Date signUpDate) {
		this.signUpDate = signUpDate;
	}

	public String getRmFirstName() {
		return rmFirstName;
	}

	public void setRmFirstName(String rmFirstName) {
		this.rmFirstName = rmFirstName;
	}

	public String getRmLastName() {
		return rmLastName;
	}

	public void setRmLastName(String rmLastName) {
		this.rmLastName = rmLastName;
	}

	public String getRmDesignation() {
		return rmDesignation;
	}

	public void setRmDesignation(String rmDesignation) {
		this.rmDesignation = rmDesignation;
	}

	public String getRmApprovedBy() {
		return rmApprovedBy;
	}

	public void setRmApprovedBy(String rmApprovedBy) {
		this.rmApprovedBy = rmApprovedBy;
	}

}
