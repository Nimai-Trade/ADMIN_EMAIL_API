package com.nimai.lc.bean;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

public class NimaiCustomerBean {
		
	private String subscriberType;
	private String userid;
	private String bankType;
	private String firstName;
	private String lastName;
	private String emailAddress;
	private String mobileNumber;
	private String countryName;
	private String companyName;
	private String businessType;
	private Date insertedDate;
	private Date modifiedDate;
    private String telephone;
    private String minValueofLc;
	private String registrationType;
	private String provincename;
	private String address1;
	private String address2;
	private String address3;
	private String city;
	private String pincode;
	private Boolean isRegister;

	
	private String accountType;
	private String accountSource;
    private Date accountCreatedDate;
    private String accountStatus;
	private String 	regCurrency;
	private String 	emailAddress1;
	private String 	emailAddress2;
	private String 	emailAddress3;
	private String passcodeUser;
	private Date txnInsertedDate;
	private Date txnDate;
	private String transactionId;
	private String transactionType;
	private String transactionStatus;
	private Integer creditUsed;
	private Double savings;
	private String ccy;

	
		public String getPasscodeUser() {
		return passcodeUser;
	}


	public void setPasscodeUser(String passcodeUser) {
		this.passcodeUser = passcodeUser;
	}


		public Date getTxnInsertedDate() {
		return txnInsertedDate;
	}


	public void setTxnInsertedDate(Date txnInsertedDate) {
		this.txnInsertedDate = txnInsertedDate;
	}


		public Integer getCreditUsed() {
			return creditUsed;
		}


		public void setCreditUsed(Integer creditUsed) {
			this.creditUsed = creditUsed;
		}

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


		public String getBankType() {
			return bankType;
		}


		public void setBankType(String bankType) {
			this.bankType = bankType;
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


		public Boolean getIsRegister() {
			return isRegister;
		}


		public void setIsRegister(Boolean isRegister) {
			this.isRegister = isRegister;
		}


		
		public String getAccountType() {
			return accountType;
		}


		public void setAccountType(String accountType) {
			this.accountType = accountType;
		}


		public String getAccountSource() {
			return accountSource;
		}


		public void setAccountSource(String accountSource) {
			this.accountSource = accountSource;
		}


		public Date getAccountCreatedDate() {
			return accountCreatedDate;
		}


		public void setAccountCreatedDate(Date accountCreatedDate) {
			this.accountCreatedDate = accountCreatedDate;
		}


		public String getAccountStatus() {
			return accountStatus;
		}


		public void setAccountStatus(String accountStatus) {
			this.accountStatus = accountStatus;
		}


		public String getRegCurrency() {
			return regCurrency;
		}


		public void setRegCurrency(String regCurrency) {
			this.regCurrency = regCurrency;
		}


		public String getEmailAddress1() {
			return emailAddress1;
		}


		public void setEmailAddress1(String emailAddress1) {
			this.emailAddress1 = emailAddress1;
		}


		public String getEmailAddress2() {
			return emailAddress2;
		}


		public void setEmailAddress2(String emailAddress2) {
			this.emailAddress2 = emailAddress2;
		}


		public String getEmailAddress3() {
			return emailAddress3;
		}


		public void setEmailAddress3(String emailAddress3) {
			this.emailAddress3 = emailAddress3;
		}


		public Date getTxnDate() {
			return txnDate;
		}


		public void setTxnDate(Date txnDate) {
			this.txnDate = txnDate;
		}


		public String getTransactionId() {
			return transactionId;
		}


		public void setTransactionId(String transactionId) {
			this.transactionId = transactionId;
		}


		public String getTransactionType() {
			return transactionType;
		}


		public void setTransactionType(String transactionType) {
			this.transactionType = transactionType;
		}


		public String getTransactionStatus() {
			return transactionStatus;
		}


		public void setTransactionStatus(String transactionStatus) {
			this.transactionStatus = transactionStatus;
		}


		public Double getSavings() {
			return savings;
		}


		public void setSavings(Double savings) {
			this.savings = savings;
		}


		public String getCcy() {
			return ccy;
		}


		public void setCcy(String ccy) {
			this.ccy = ccy;
		}
		
		

	}



