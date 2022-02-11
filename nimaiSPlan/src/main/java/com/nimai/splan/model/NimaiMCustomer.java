package com.nimai.splan.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedStoredProcedureQueries;
import javax.persistence.NamedStoredProcedureQuery;
import javax.persistence.OneToMany;
import javax.persistence.ParameterMode;
import javax.persistence.StoredProcedureParameter;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "NIMAI_M_CUSTOMER")
@NamedStoredProcedureQueries({
    @NamedStoredProcedureQuery(name = "GET_DETAILS_FOR_DASHBOARD", procedureName = "GET_DETAILS_FOR_DASHBOARD", parameters = {
	@StoredProcedureParameter(mode = ParameterMode.IN, name = "i_user_id", type = String.class),
	@StoredProcedureParameter(mode = ParameterMode.IN, name = "i_email_id", type = String.class),
	@StoredProcedureParameter(mode = ParameterMode.OUT, name = "OUT_LC_COUNT", type = Integer.class),
	@StoredProcedureParameter(mode = ParameterMode.OUT, name = "OUT_SUBSIDIARIES_COUNT", type = Integer.class),
	@StoredProcedureParameter(mode = ParameterMode.OUT, name = "OUT_LC_Utilized_Count", type = Integer.class),
	@StoredProcedureParameter(mode = ParameterMode.OUT, name = "OUT_Subsidiary_Utilized_Count", type = Integer.class),
	@StoredProcedureParameter(mode = ParameterMode.OUT, name = "OUT_KYC_count", type = Integer.class),
	@StoredProcedureParameter(mode = ParameterMode.OUT, name = "OUT_isRegister", type = Boolean.class),
	@StoredProcedureParameter(mode = ParameterMode.OUT, name = "OUT_isBDetailsFilled", type = Boolean.class),
	@StoredProcedureParameter(mode = ParameterMode.OUT, name = "OUT_isSPlanPurchased", type = String.class),
	@StoredProcedureParameter(mode = ParameterMode.OUT, name = "OUT_ModeofPayment", type = String.class),
	@StoredProcedureParameter(mode = ParameterMode.OUT, name = "OUT_paymentStatus", type = String.class),
	@StoredProcedureParameter(mode = ParameterMode.OUT, name = "OUT_paymentDate", type = Date.class),
	@StoredProcedureParameter(mode = ParameterMode.OUT, name = "OUT_paymentTransId", type = String.class),
	@StoredProcedureParameter(mode = ParameterMode.OUT, name = "OUT_KycStatus", type = String.class),
	@StoredProcedureParameter(mode = ParameterMode.OUT, name = "OUT_kycApprovalDate", type = Date.class),
	@StoredProcedureParameter(mode = ParameterMode.OUT, name = "OUT_REFER_COUNT", type = Integer.class),
	@StoredProcedureParameter(mode = ParameterMode.OUT, name = "OUT_SUBSCRIBER_TYPE", type = String.class),
	@StoredProcedureParameter(mode = ParameterMode.OUT, name = "OUT_USERID", type = String.class),
	@StoredProcedureParameter(mode = ParameterMode.OUT, name = "OUT_BANK_TYPE", type = String.class),
	@StoredProcedureParameter(mode = ParameterMode.OUT, name = "OUT_FIRST_NAME", type = String.class),
	@StoredProcedureParameter(mode = ParameterMode.OUT, name = "OUT_LAST_NAME", type = String.class),
	@StoredProcedureParameter(mode = ParameterMode.OUT, name = "OUT_EMAIL_ADDRESS", type = String.class),
	@StoredProcedureParameter(mode = ParameterMode.OUT, name = "OUT_MOBILE_NUMBER", type = String.class),
	@StoredProcedureParameter(mode = ParameterMode.OUT, name = "OUT_COUNTRY_NAME", type = String.class),
	@StoredProcedureParameter(mode = ParameterMode.OUT, name = "OUT_LANDLINE", type = String.class),
	@StoredProcedureParameter(mode = ParameterMode.OUT, name = "OUT_DESIGNATION", type = String.class),
	@StoredProcedureParameter(mode = ParameterMode.OUT, name = "OUT_COMPANY_NAME", type = String.class),
	@StoredProcedureParameter(mode = ParameterMode.OUT, name = "OUT_BUSINESS_TYPE", type = String.class),
	@StoredProcedureParameter(mode = ParameterMode.OUT, name = "OUT_BANK_NAME", type = String.class),
	@StoredProcedureParameter(mode = ParameterMode.OUT, name = "OUT_BRANCH_NAME", type = String.class),
	@StoredProcedureParameter(mode = ParameterMode.OUT, name = "OUT_SWIFT_CODE", type = String.class),
	@StoredProcedureParameter(mode = ParameterMode.OUT, name = "OUT_TELEPHONE", type = String.class),
	@StoredProcedureParameter(mode = ParameterMode.OUT, name = "OUT_MIN_VALUEOF_LC", type = String.class),
	@StoredProcedureParameter(mode = ParameterMode.OUT, name = "OUT_REGISTRATION_TYPE", type = String.class),
	@StoredProcedureParameter(mode = ParameterMode.OUT, name = "OUT_PROVINCENAME", type = String.class),
	@StoredProcedureParameter(mode = ParameterMode.OUT, name = "OUT_ADDRESS1", type = String.class),
	@StoredProcedureParameter(mode = ParameterMode.OUT, name = "OUT_ADDRESS2", type = String.class),
	@StoredProcedureParameter(mode = ParameterMode.OUT, name = "OUT_ADDRESS3", type = String.class),
	@StoredProcedureParameter(mode = ParameterMode.OUT, name = "OUT_CITY", type = String.class),
	@StoredProcedureParameter(mode = ParameterMode.OUT, name = "OUT_PINCODE", type = String.class),
	@StoredProcedureParameter(mode = ParameterMode.OUT, name = "OUT_REGISTERED_COUNTRY", type = String.class),
	@StoredProcedureParameter(mode = ParameterMode.OUT, name = "OUT_IS_RMASSIGNED", type = Boolean.class),
	@StoredProcedureParameter(mode = ParameterMode.OUT, name = "OUT_RM_ID", type = String.class),
	@StoredProcedureParameter(mode = ParameterMode.OUT, name = "OUT_DRAFT_COUNT", type = Integer.class),
	@StoredProcedureParameter(mode = ParameterMode.OUT, name = "OUT_ACCOUNT_CREATED_DATE", type = Date.class),
	@StoredProcedureParameter(mode = ParameterMode.OUT, name = "OUT_ACCOUNT_SOURCE", type = String.class),
	@StoredProcedureParameter(mode = ParameterMode.OUT, name = "OUT_ACCOUNT_STATUS", type = String.class),
	@StoredProcedureParameter(mode = ParameterMode.OUT, name = "OUT_ACCOUNT_TYPE", type = String.class),
	@StoredProcedureParameter(mode = ParameterMode.OUT, name = "OUT_CURRENCY_CODE", type = String.class),
	@StoredProcedureParameter(mode = ParameterMode.OUT, name = "OUT_SPL_SERIAL_NUMBER", type = Integer.class),
	@StoredProcedureParameter(mode = ParameterMode.OUT, name = "OUT_SUBSCRIPTION_ID", type = String.class),
	@StoredProcedureParameter(mode = ParameterMode.OUT, name = "OUT_SUBSCRIPTION_NAME", type = String.class),
	@StoredProcedureParameter(mode = ParameterMode.OUT, name = "OUT_SUBSCRIPTION_AMOUNT", type = Integer.class),
	@StoredProcedureParameter(mode = ParameterMode.OUT, name = "OUT_SPLAN_START_DATE", type = Date.class),
	@StoredProcedureParameter(mode = ParameterMode.OUT, name = "OUT_SPLAN_END_DATE", type = Date.class),
	@StoredProcedureParameter(mode = ParameterMode.OUT, name = "OUT_FLAG", type = Integer.class),
	@StoredProcedureParameter(mode = ParameterMode.OUT, name = "OUT_RELATIONSHIP_MANAGER", type = String.class),
	@StoredProcedureParameter(mode = ParameterMode.OUT, name = "OUT_CUSTOMER_SUPPORT", type = String.class),
	@StoredProcedureParameter(mode = ParameterMode.OUT, name = "OUT_REMARK", type = String.class),
	@StoredProcedureParameter(mode = ParameterMode.OUT, name = "OUT_STATUS", type = String.class),
	@StoredProcedureParameter(mode = ParameterMode.OUT, name = "OUT_SUBSCRIPTION_VALIDITY", type = String.class),
	@StoredProcedureParameter(mode = ParameterMode.OUT, name = "OUT_INSERTED_BY", type = String.class),
	@StoredProcedureParameter(mode = ParameterMode.OUT, name = "OUT_MODIFIED_BY", type = String.class),
	@StoredProcedureParameter(mode = ParameterMode.OUT, name = "OUT_is_vas_applied", type = Boolean.class),
	@StoredProcedureParameter(mode = ParameterMode.OUT, name = "OUT_vas_amount", type = Integer.class),
	@StoredProcedureParameter(mode = ParameterMode.OUT, name = "OUT_grand_amount", type = Integer.class),
	@StoredProcedureParameter(mode = ParameterMode.OUT, name = "OUT_discount_id", type = Integer.class),
	@StoredProcedureParameter(mode = ParameterMode.OUT, name = "OUT_discount_amount", type = Double.class),
	@StoredProcedureParameter(mode = ParameterMode.OUT, name = "OUT_TNC_DATE", type = Date.class)
	})
})
public class NimaiMCustomer implements Serializable {

	private static final long serialVersionUID = 1L;

	@Basic(optional = false)
	@NotNull
	@Column(name = "SUBSCRIBER_TYPE")
	private String subscriberType;

	@Id
	@Basic(optional = false)
	@NotNull
	@Column(name = "USERID")
	private String userid;

	@Basic(optional = false)
	@Column(name = "BANK_TYPE")
	private String bankType;

	@Basic(optional = false)
	@NotNull
	@Column(name = "FIRST_NAME")
	private String firstName;

	@Basic(optional = false)
	@NotNull
	@Column(name = "LAST_NAME")
	private String lastName;

	@Basic(optional = false)
	@NotNull
	@Column(name = "EMAIL_ADDRESS")
	private String emailAddress;

	@Basic(optional = false)
	@NotNull
	@Column(name = "MOBILE_NUMBER")
	private String mobileNumber;

	@Column(name = "COUNTRY_NAME")
	private String countryName;

	@Column(name = "LANDLINE")
	private String landline;

	@Column(name = "DESIGNATION")
	private String designation;

	@Column(name = "COMPANY_NAME")
	private String companyName;

	@Column(name = "BUSINESS_TYPE")
	private String businessType;

	@Column(name = "INSERTED_DATE")
	@Temporal(TemporalType.TIMESTAMP)
	private Date insertedDate;

	@Column(name = "MODIFIED_DATE")
	@Temporal(TemporalType.TIMESTAMP)
	private Date modifiedDate;

	@Column(name = "BANK_NAME")
	private String bankNbfcName;

	@Column(name = "BRANCH_NAME")
	private String branchName;

	@Column(name = "SWIFT_CODE")
	private String swiftCode;

	@Column(name = "REGISTERED_COUNTRY")
	private String registredCountry;

	@Column(name = "TELEPHONE")
	private String telephone;


	@Column(name = "MIN_VALUEOF_LC")
	private String minValueofLc;

	@Column(name = "REGISTRATION_TYPE")
	private String registrationType;


	@Column(name = "PROVINCENAME")
	private String provincename;


	@Column(name = "ADDRESS1")
	private String address1;

	@Column(name = "ADDRESS2")
	private String address2;

	@Column(name = "ADDRESS3")
	private String address3;

	@Column(name = "CITY")
	private String city;

	@Column(name = "PINCODE")
	private String pincode;
	
	@Column(name="IS_SPLANPURCHASED")
    private Boolean isSPlanPurchased;
    
    
    @Column(name="MODE_OF_PAYMENT")
    private String modeOfPayment;
    
    
    @Column(name="PAYMENT_STATUS")
    private String paymentStatus;
    
    
    @Column(name="PAYMENT_DATE")
    private Date paymentDate;
	
    @Column(name="PAYMENT_TRANS_ID")
    private String paymentTransId;
    
	

//	@OneToOne(cascade = CascadeType.ALL, mappedBy = "userid")
//	private NimaiMSubscription nimaiMSubscription;
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "userid")
	private List<NimaiSubscriptionDetails> subscriptionDetails;
   

	public String getPaymentTransId() {
		return paymentTransId;
	}

	public void setPaymentTransId(String paymentTransId) {
		this.paymentTransId = paymentTransId;
	}

	public Boolean getIsSPlanPurchased() {
		return isSPlanPurchased;
	}

	public void setIsSPlanPurchased(Boolean isSPlanPurchased) {
		this.isSPlanPurchased = isSPlanPurchased;
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

	

	public NimaiMCustomer() {
	}

	public List<NimaiSubscriptionDetails> getSubscriptionDetails() {
		return subscriptionDetails;
	}

	public void setSubscriptionDetails(List<NimaiSubscriptionDetails> subscriptionDetails) {
		this.subscriptionDetails = subscriptionDetails;
	}

	public NimaiMCustomer(String userid) {
		this.userid = userid;
	}

	public NimaiMCustomer(String userid, String subscriberType, String firstName, String lastName, String emailAddress,
			String mobileNumber) {
		this.userid = userid;
		this.subscriberType = subscriberType;
		this.firstName = firstName;
		this.lastName = lastName;
		this.emailAddress = emailAddress;
		this.mobileNumber = mobileNumber;
	}

	public String getBankType() {
		return bankType;
	}

	public void setBankType(String bankType) {
		this.bankType = bankType;
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

	public String getBankNbfcName() {
		return bankNbfcName;
	}

	public void setBankNbfcName(String bankNbfcName) {
		this.bankNbfcName = bankNbfcName;
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

	public String getRegistredCountry() {
		return registredCountry;
	}

	public void setRegistredCountry(String registredCountry) {
		this.registredCountry = registredCountry;
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
		return "in.nimai.NimaiMCustomer[ userid=" + userid + " ]";
	}

}
