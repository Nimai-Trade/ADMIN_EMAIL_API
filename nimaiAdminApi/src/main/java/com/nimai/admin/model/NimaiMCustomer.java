/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nimai.admin.model;

import java.io.Serializable;


import java.util.Date;
import java.util.List;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.NamedStoredProcedureQuery;
import javax.persistence.OneToMany;
import javax.persistence.ParameterMode;
import javax.persistence.StoredProcedureParameter;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.nimai.admin.payload.OwenerBean;


/**
 *
 * @author Sahadeo
 */
@Entity
@Table(name = "nimai_m_customer")
@NamedQueries({ @NamedQuery(name = "NimaiMCustomer.findAll", query = "SELECT n FROM NimaiMCustomer n") })

@NamedStoredProcedureQuery(name = "dashboardCount", procedureName = "DASHBOARD_COUNT_PROCEDURE", parameters = {
		@StoredProcedureParameter(mode = ParameterMode.IN, name = "query_no", type = Integer.class),
		@StoredProcedureParameter(mode = ParameterMode.IN, name = "subscriberType", type = String.class),
		@StoredProcedureParameter(mode = ParameterMode.IN, name = "bankType", type = String.class),
		@StoredProcedureParameter(mode = ParameterMode.IN, name = "status", type = String.class),
		@StoredProcedureParameter(mode = ParameterMode.IN, name = "exp_day", type = Integer.class),
		@StoredProcedureParameter(mode = ParameterMode.IN, name = "dateFrom", type = String.class),
		@StoredProcedureParameter(mode = ParameterMode.IN, name = "dateTo", type = String.class),
		@StoredProcedureParameter(mode = ParameterMode.IN, name = "cases", type = String.class),
		@StoredProcedureParameter(mode = ParameterMode.IN, name = "countryNames", type = String.class),
		@StoredProcedureParameter(mode = ParameterMode.OUT, name = "result", type = Integer.class)

})
public class NimaiMCustomer implements Serializable {

	private static final long serialVersionUID = 1L;
	@Size(max = 20)
	@Column(name = "SUBSCRIBER_TYPE")
	private String subscriberType;
	@Id
	@Basic(optional = false)
	@NotNull
	@Size(min = 1, max = 50)
	@Column(name = "USERID")
	private String userid;
	@Size(max = 20)
	@Column(name = "BANK_TYPE")
	private String bankType;
	@Size(max = 100)
	@Column(name = "FIRST_NAME")
	private String firstName;
	@Size(max = 100)
	@Column(name = "LAST_NAME")
	private String lastName;
	
	@Size(max = 100)
	@Column(name = "EMAIL_ADDRESS")
	private String emailAddress;
	
	@Size(max = 20)
	@Column(name = "MOBILE_NUMBER")
	private String mobileNumber;
	
	@Size(max = 100)
	@Column(name = "COUNTRY_NAME")
	private String countryName;
	
	@Size(max = 20)
	@Column(name = "LANDLINE")
	private String landline;
	
	@Size(max = 100)
	@Column(name = "DESIGNATION")
	private String designation;
	
	@Size(max = 100)
	@Column(name = "COMPANY_NAME")
	private String companyName;
	
	@Size(max = 20)
	@Column(name = "BUSINESS_TYPE")
	private String businessType;
	
	@Size(max = 100)
	@Column(name = "BANK_NAME")
	private String bankName;
	@Size(max = 100)
	@Column(name = "BRANCH_NAME")
	private String branchName;
	
	@Size(max = 30)
	@Column(name = "SWIFT_CODE")
	private String swiftCode;
	@Size(max = 20)
	@Column(name = "TELEPHONE")
	private String telephone;
	@Size(max = 20)
	@Column(name = "MIN_VALUEOF_LC")
	private String minValueofLc;
	@Size(max = 20)
	@Column(name = "REGISTRATION_TYPE")
	private String registrationType;
	
	@Size(max = 100)
	@Column(name = "PROVINCENAME")
	private String provincename;
	@Size(max = 100)
	@Column(name = "ADDRESS1")
	private String address1;
	@Size(max = 100)
	@Column(name = "ADDRESS2")
	private String address2;
	@Size(max =100)
	@Column(name = "ADDRESS3")
	private String address3;
	
	@Size(max = 75)
	@Column(name = "CITY")
	private String city;
	
	@Size(max = 10)
	@Column(name = "PINCODE")
	private String pincode;
	
	@Size(max = 100)
	@Column(name = "REGISTERED_COUNTRY")
	private String registeredCountry;
	
	@Column(name = "IS_REGISTER")
	private Boolean isRegister;
	@Column(name = "IS_RMASSIGNED")
	private Boolean isRmassigned;
	@Size(max = 100)
	@Column(name = "RM_ID")
	private String rmId;
	@Column(name = "IS_BDETAILSFILLED")
	private Boolean isBdetailsfilled;
	@Column(name = "IS_SPLANPURCHASED")
	private Boolean isSplanpurchased;
	@Size(max = 100)
	@Column(name = "MODE_OF_PAYMENT")
	private String modeOfPayment;
	@Size(max = 100)
	@Column(name = "PAYMENT_STATUS")
	private String paymentStatus;
	@Column(name = "PAYMENT_DATE")
	@Temporal(TemporalType.DATE)
	private Date paymentDate;
	@Size(max = 50)
	@Column(name = "KYC_STATUS")
	private String kycStatus;
	// bshir
	@Column(name = "CURRENCY_CODE")
	private String currencyCode;
	@Column(name = "KYC_APPROVALDATE")
	@Temporal(TemporalType.DATE)
	private Date kycApprovaldate;
	@Column(name = "INSERTED_DATE")
	@Temporal(TemporalType.TIMESTAMP)
	private Date insertedDate;
	@Column(name = "MODIFIED_DATE")
	@Temporal(TemporalType.DATE)
	private Date modifiedDate;
//    @OneToMany(mappedBy = "userid")
//    private List<NimaiMBranch> nimaiMBranchList;
	@OneToMany(mappedBy = "userId")
	private List<NimaiMmTransaction> nimaiMmTransactionList;
	@OneToMany(mappedBy = "userid")
	private List<NimaiMLogin> nimaiMLoginList;
	@OneToMany(mappedBy = "userid")
	private List<NimaiFIntcountry> nimaiFIntcountryList;
	@OneToMany(mappedBy = "userid")
	private List<NimaiSubscriptionDetails> nimaiSubscriptionDetailsList;
	
	
	@OneToMany(mappedBy = "userid")
	private List<NimaiFBlkgoods> nimaiFBlkgoodsList;
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "userid")
	private List<NimaiMRefer> nimaiMReferList;
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "userid")
	private List<NimaiMQuotation> nimaiMQuotationList;
	
	@OneToMany(mappedBy = "userid")
	private List<NimaiFKyc> nimaiFKycList;
	

	@OneToMany(mappedBy = "userid")
	private List<NimaiFOwner> nimaiFOwnerList;
	
	@Column(name="PAYMENT_TRANS_ID")
	private String paymentTransId;

	
	
	
	
	public String getPaymentTransId() {
		return paymentTransId;
	}

	public void setPaymentTransId(String paymentTransId) {
		this.paymentTransId = paymentTransId;
	}

	@Column(name = "RM_STATUS")
	private String rmStatus;

	@Column(name = "ACCOUNT_TYPE")
	private String accountType;

	@Column(name = "ACCOUNT_SOURCE")
	private String accountSource;

	@Column(name = "ACCOUNT_CREATED_DATE")
	@Temporal(TemporalType.DATE)
	private Date accountCreatedDate;

	@Column(name = "ACCOUNT_STATUS")
	private String accountStatus;

	@Column(name = "EMAIL_ADDRESS1")
	private String emailAddress1;

	@Column(name = "EMAIL_ADDRESS2")
	private String emailAddress2;

	@Column(name = "EMAIL_ADDRESS3")
	private String emailAddress3;

	@Column(name = "APPROVED_BY")
	private String approvedBy;

	@Column(name = "PAYMENT_APPROVED_BY")
	private String paymentApprovedBy;

	@Column(name = "ACCOUNT_REMARK")
	private String accountRemark;

	public String getApprovedBy() {
		return approvedBy;
	}

	public void setApprovedBy(String approvedBy) {
		this.approvedBy = approvedBy;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public NimaiMCustomer() {
	}

	public NimaiMCustomer(String userid) {
		this.userid = userid;
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

	public String getRegisteredCountry() {
		return registeredCountry;
	}

	public void setRegisteredCountry(String registeredCountry) {
		this.registeredCountry = registeredCountry;
	}

	public Boolean getIsRegister() {
		return isRegister;
	}

	public void setIsRegister(Boolean isRegister) {
		this.isRegister = isRegister;
	}

	public Boolean getIsRmassigned() {
		return isRmassigned;
	}

	public void setIsRmassigned(Boolean isRmassigned) {
		this.isRmassigned = isRmassigned;
	}

	public String getRmId() {
		return rmId;
	}

	public void setRmId(String rmId) {
		this.rmId = rmId;
	}

	public Boolean getIsBdetailsfilled() {
		return isBdetailsfilled;
	}

	public void setIsBdetailsfilled(Boolean isBdetailsfilled) {
		this.isBdetailsfilled = isBdetailsfilled;
	}

	public Boolean getIsSplanpurchased() {
		return isSplanpurchased;
	}

	public void setIsSplanpurchased(Boolean isSplanpurchased) {
		this.isSplanpurchased = isSplanpurchased;
	}

	public String getModeOfPayment() {
		return modeOfPayment;
	}

	public void setModeOfPayment(String modeOfPayment) {
		this.modeOfPayment = modeOfPayment;
	}
	
	


	public String getPaymentStatus() {
		return paymentStatus;
	}

	public void setPaymentStatus(String paymentStatus) {
		this.paymentStatus = paymentStatus;
	}

	public Date getPaymentDate() {
		return paymentDate;
	}

	public void setPaymentDate(Date paymentDate) {
		this.paymentDate = paymentDate;
	}

	public String getKycStatus() {
		return kycStatus;
	}

	public void setKycStatus(String kycStatus) {
		this.kycStatus = kycStatus;
	}

	public Date getKycApprovaldate() {
		return kycApprovaldate;
	}

	public void setKycApprovaldate(Date kycApprovaldate) {
		this.kycApprovaldate = kycApprovaldate;
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

//    public List<NimaiMBranch> getNimaiMBranchList() {
//        return nimaiMBranchList;
//    }
//
//    public void setNimaiMBranchList(List<NimaiMBranch> nimaiMBranchList) {
//        this.nimaiMBranchList = nimaiMBranchList;
//    }

	public List<NimaiMmTransaction> getNimaiMmTransactionList() {
		return nimaiMmTransactionList;
	}

	public void setNimaiMmTransactionList(List<NimaiMmTransaction> nimaiMmTransactionList) {
		this.nimaiMmTransactionList = nimaiMmTransactionList;
	}

	public List<NimaiMLogin> getNimaiMLoginList() {
		return nimaiMLoginList;
	}

	public void setNimaiMLoginList(List<NimaiMLogin> nimaiMLoginList) {
		this.nimaiMLoginList = nimaiMLoginList;
	}

	public List<NimaiFIntcountry> getNimaiFIntcountryList() {
		return nimaiFIntcountryList;
	}

	public void setNimaiFIntcountryList(List<NimaiFIntcountry> nimaiFIntcountryList) {
		this.nimaiFIntcountryList = nimaiFIntcountryList;
	}

	public List<NimaiSubscriptionDetails> getNimaiSubscriptionDetailsList() {
		return nimaiSubscriptionDetailsList;
	}

	public void setNimaiSubscriptionDetailsList(List<NimaiSubscriptionDetails> nimaiSubscriptionDetailsList) {
		this.nimaiSubscriptionDetailsList = nimaiSubscriptionDetailsList;
	}

	public List<NimaiFBlkgoods> getNimaiFBlkgoodsList() {
		return nimaiFBlkgoodsList;
	}

	public void setNimaiFBlkgoodsList(List<NimaiFBlkgoods> nimaiFBlkgoodsList) {
		this.nimaiFBlkgoodsList = nimaiFBlkgoodsList;
	}

	public List<NimaiMRefer> getNimaiMReferList() {
		return nimaiMReferList;
	}

	public void setNimaiMReferList(List<NimaiMRefer> nimaiMReferList) {
		this.nimaiMReferList = nimaiMReferList;
	}

	public List<NimaiMQuotation> getNimaiMQuotationList() {
		return nimaiMQuotationList;
	}

	public void setNimaiMQuotationList(List<NimaiMQuotation> nimaiMQuotationList) {
		this.nimaiMQuotationList = nimaiMQuotationList;
	}

	public List<NimaiFKyc> getNimaiFKycList() {
		return nimaiFKycList;
	}

	public void setNimaiFKycList(List<NimaiFKyc> nimaiFKycList) {
		this.nimaiFKycList = nimaiFKycList;
	}

	public List<NimaiFOwner> getNimaiFOwnerList() {
		return nimaiFOwnerList;
	}

	public void setNimaiFOwnerList(List<NimaiFOwner> nimaiFOwnerList) {
		this.nimaiFOwnerList = nimaiFOwnerList;
	}

	public String getCurrencyCode() {
		return currencyCode;
	}

	public void setCurrencyCode(String currencyCode) {
		this.currencyCode = currencyCode;
	}

	public String getRmStatus() {
		return rmStatus;
	}

	public void setRmStatus(String rmStatus) {
		this.rmStatus = rmStatus;
	}

	@Override
	public int hashCode() {
		int hash = 0;
		hash += (userid != null ? userid.hashCode() : 0);
		return hash;
	}

	@Override
	public boolean equals(Object object) {
		// TODO: Warning - this method won't work in the case the id fields are not set
		if (!(object instanceof NimaiMCustomer)) {
			return false;
		}
		NimaiMCustomer other = (NimaiMCustomer) object;
		if ((this.userid == null && other.userid != null)
				|| (this.userid != null && !this.userid.equals(other.userid))) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return "com.nimai.admin.model.NimaiMCustomer[ userid=" + userid + " ]";
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

	public String getPaymentApprovedBy() {
		return paymentApprovedBy;
	}

	public void setPaymentApprovedBy(String paymentApprovedBy) {
		this.paymentApprovedBy = paymentApprovedBy;
	}

	public String getAccountRemark() {
		return accountRemark;
	}

	public void setAccountRemark(String accountRemark) {
		this.accountRemark = accountRemark;
	}

}
