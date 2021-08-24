package com.nimai.lc.entity;

import java.io.Serializable;

import java.util.Date;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.IdClass;
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

import org.springframework.data.jpa.domain.support.AuditingEntityListener;



/**
 *
 * @author sahadeo.naik
 */
@Entity
@Table(name = "NIMAI_M_CUSTOMER")
@EntityListeners(AuditingEntityListener.class)
@NamedStoredProcedureQuery(name = "get_eligible_banks", procedureName = "get_eligible_banks", parameters = {
		@StoredProcedureParameter(mode = ParameterMode.IN, name = "inp_customer_userID", type = String.class),
		@StoredProcedureParameter(mode = ParameterMode.IN, name = "inp_transaction_ID", type = String.class)})
public class NimaiClient implements Serializable {

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
	@NotNull
	@Column(name = "FIRST_NAME")
	private String firstName;

	@Basic(optional = false)
	@NotNull
	@Column(name = "LAST_NAME")
	private String lastName;

	@Basic(optional = false)
	@NotNull
	@Size(min = 1, max = 30)
	private String emailAddress;

	@Basic(optional = false)
	@NotNull
	@Size(min = 1, max = 20)
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

	@Column(name = "BANK_NAME")
	private String bankNbfcName;

	@Column(name = "BRANCH_NAME")
	private String branchName;

	@Column(name = "SWIFT_CODE")
	private String swiftCode;

	@Column(name = "MIN_VALUEOF_LC")
	private String minValueofLc;

	@Column(name = "TELEPHONE")
	private String telephone;

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

	@Column(name = "REGISTERED_COUNTRY")
	private String rgistredCountry;

	@Column(name = "IS_REGISTER")
	private Integer isRegister;

	@Column(name = "IS_RMASSIGNED")
	private Integer isRmassigned;

	@Column(name = "RM_ID")
	private String rmId;

	@Column(name = "IS_BDETAILSFILLED")
	private Integer isBdetailsfilled;

	@Column(name = "IS_SPLANPURCHASED")
	private Integer isSplanPurchased;

	@Column(name = "MODE_OF_PAYMENT")
	private String modeOfPayment;

	@Column(name = "PAYMENT_STATUS")
	private String paymentStatus;

	@Column(name = "PAYMENT_DATE")
	private Date paymentDate;

	@Column(name = "KYC_STATUS")
	private String kycStatus;

	@Column(name = "KYC_APPROVALDATE")
	@Temporal(TemporalType.TIMESTAMP)
	private Date kycApprovedDate;

	@Column(name = "INSERTED_DATE")
	@Temporal(TemporalType.TIMESTAMP)
	private Date insertedDate;

	@Column(name = "MODIFIED_DATE")
	@Temporal(TemporalType.TIMESTAMP)
	private Date modifiedDate;

	@Column(name = "ACCOUNT_TYPE")
	private String accountType;

	@Column(name = "ACCOUNT_SOURCE")
	private String accountSource;

	@Column(name = "ACCOUNT_CREATED_DATE")
	@Temporal(TemporalType.TIMESTAMP)
	private Date accountCreatedDate;

	@Column(name = "ACCOUNT_STATUS")
	private String accountStatus;

	@Column(name = "CURRENCY_CODE")
	private String currencyCode;

	@Column(name = "EMAIL_ADDRESS1")
	private String emailAddress1;

	@Column(name = "EMAIL_ADDRESS2")
	private String emailAddress2;

	@Column(name = "EMAIL_ADDRESS3")
	private String emailAddress3;   
	
	@OneToMany(mappedBy = "userid")
	private List<NimaiSubscriptionDetails> subscriptionDettails;
   
	/*@OneToMany(cascade = CascadeType.ALL, mappedBy = "userid")
    private List<NimaiMLogin> nimaiMLoginList;
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "userid")
    private List<NimaiFSubsidiaries> nimaiFSubList;
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "userid")
    private List<NimaiMBranch> nimaiBranchList;
    
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "userid")
    private List<NimaiMRefer> nimaiReferList;
    */

    
    

    
    
    
    
	/**
	 * @return the rgistredCountry
	 */
	public String getRgistredCountry() {
		return rgistredCountry;
	}

	public List<NimaiSubscriptionDetails> getSubscriptionDettails() {
		return subscriptionDettails;
	}

	public void setSubscriptionDettails(List<NimaiSubscriptionDetails> subscriptionDettails) {
		this.subscriptionDettails = subscriptionDettails;
	}

	/**
	 * @param rgistredCountry the rgistredCountry to set
	 */
	public void setRgistredCountry(String rgistredCountry) {
		this.rgistredCountry = rgistredCountry;
	}

	/**
	 * @return the isRegister
	 */
	public Integer getIsRegister() {
		return isRegister;
	}

	/**
	 * @param isRegister the isRegister to set
	 */
	public void setIsRegister(Integer isRegister) {
		this.isRegister = isRegister;
	}

	/**
	 * @return the isRmassigned
	 */
	public Integer getIsRmassigned() {
		return isRmassigned;
	}

	/**
	 * @param isRmassigned the isRmassigned to set
	 */
	public void setIsRmassigned(Integer isRmassigned) {
		this.isRmassigned = isRmassigned;
	}

	/**
	 * @return the rmId
	 */
	public String getRmId() {
		return rmId;
	}

	/**
	 * @param rmId the rmId to set
	 */
	public void setRmId(String rmId) {
		this.rmId = rmId;
	}

	/**
	 * @return the isBdetailsfilled
	 */
	public Integer getIsBdetailsfilled() {
		return isBdetailsfilled;
	}

	/**
	 * @param isBdetailsfilled the isBdetailsfilled to set
	 */
	public void setIsBdetailsfilled(Integer isBdetailsfilled) {
		this.isBdetailsfilled = isBdetailsfilled;
	}

	/**
	 * @return the isSplanPurchased
	 */
	public Integer getIsSplanPurchased() {
		return isSplanPurchased;
	}

	/**
	 * @param isSplanPurchased the isSplanPurchased to set
	 */
	public void setIsSplanPurchased(Integer isSplanPurchased) {
		this.isSplanPurchased = isSplanPurchased;
	}

	/**
	 * @return the modeOfPayment
	 */
	public String getModeOfPayment() {
		return modeOfPayment;
	}

	/**
	 * @param modeOfPayment the modeOfPayment to set
	 */
	public void setModeOfPayment(String modeOfPayment) {
		this.modeOfPayment = modeOfPayment;
	}

	/**
	 * @return the paymentStatus
	 */
	public String getPaymentStatus() {
		return paymentStatus;
	}

	/**
	 * @param paymentStatus the paymentStatus to set
	 */
	public void setPaymentStatus(String paymentStatus) {
		this.paymentStatus = paymentStatus;
	}

	/**
	 * @return the paymentDate
	 */
	public Date getPaymentDate() {
		return paymentDate;
	}

	/**
	 * @param paymentDate the paymentDate to set
	 */
	public void setPaymentDate(Date paymentDate) {
		this.paymentDate = paymentDate;
	}

	/**
	 * @return the kycStatus
	 */
	public String getKycStatus() {
		return kycStatus;
	}

	/**
	 * @param kycStatus the kycStatus to set
	 */
	public void setKycStatus(String kycStatus) {
		this.kycStatus = kycStatus;
	}

	/**
	 * @return the kycApprovedDate
	 */
	public Date getKycApprovedDate() {
		return kycApprovedDate;
	}

	/**
	 * @param kycApprovedDate the kycApprovedDate to set
	 */
	public void setKycApprovedDate(Date kycApprovedDate) {
		this.kycApprovedDate = kycApprovedDate;
	}

	/**
	 * @return the accountType
	 */
	public String getAccountType() {
		return accountType;
	}

	/**
	 * @param accountType the accountType to set
	 */
	public void setAccountType(String accountType) {
		this.accountType = accountType;
	}

	/**
	 * @return the accountSource
	 */
	public String getAccountSource() {
		return accountSource;
	}

	/**
	 * @param accountSource the accountSource to set
	 */
	public void setAccountSource(String accountSource) {
		this.accountSource = accountSource;
	}

	/**
	 * @return the accountCreatedDate
	 */
	public Date getAccountCreatedDate() {
		return accountCreatedDate;
	}

	/**
	 * @param accountCreatedDate the accountCreatedDate to set
	 */
	public void setAccountCreatedDate(Date accountCreatedDate) {
		this.accountCreatedDate = accountCreatedDate;
	}

	/**
	 * @return the accountStatus
	 */
	public String getAccountStatus() {
		return accountStatus;
	}

	/**
	 * @param accountStatus the accountStatus to set
	 */
	public void setAccountStatus(String accountStatus) {
		this.accountStatus = accountStatus;
	}

	/**
	 * @return the currencyCode
	 */
	public String getCurrencyCode() {
		return currencyCode;
	}

	/**
	 * @param currencyCode the currencyCode to set
	 */
	public void setCurrencyCode(String currencyCode) {
		this.currencyCode = currencyCode;
	}

	/**
	 * @return the emailAddress1
	 */
	public String getEmailAddress1() {
		return emailAddress1;
	}

	/**
	 * @param emailAddress1 the emailAddress1 to set
	 */
	public void setEmailAddress1(String emailAddress1) {
		this.emailAddress1 = emailAddress1;
	}

	/**
	 * @return the emailAddress2
	 */
	public String getEmailAddress2() {
		return emailAddress2;
	}

	/**
	 * @param emailAddress2 the emailAddress2 to set
	 */
	public void setEmailAddress2(String emailAddress2) {
		this.emailAddress2 = emailAddress2;
	}

	/**
	 * @return the emailAddress3
	 */
	public String getEmailAddress3() {
		return emailAddress3;
	}

	/**
	 * @param emailAddress3 the emailAddress3 to set
	 */
	public void setEmailAddress3(String emailAddress3) {
		this.emailAddress3 = emailAddress3;
	}

	/**
	 * @return the nimaiReferList
	 */
	//Comment by Adil
	/*public List<NimaiMRefer> getNimaiReferList() {
		return nimaiReferList;
	}

	
	public void setNimaiReferList(List<NimaiMRefer> nimaiReferList) {
		this.nimaiReferList = nimaiReferList;
	}

	
	public List<NimaiMBranch> getNimaiBranchList() {
		return nimaiBranchList;
	}

	
	public void setNimaiBranchList(List<NimaiMBranch> nimaiBranchList) {
		this.nimaiBranchList = nimaiBranchList;
	}

	public List<NimaiFSubsidiaries> getNimaiFSubList() {
		return nimaiFSubList;
	}

	public void setNimaiFSubList(List<NimaiFSubsidiaries> nimaiFSubList) {
		this.nimaiFSubList = nimaiFSubList;
	}
	*/
	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public NimaiClient() {
    }

    public NimaiClient(String userid) {
        this.userid = userid;
    }

    public NimaiClient(String userid, String subscriptionId, String subscriberType, String firstName, String lastName, String emailAddress, String mobileNumber) {
        this.userid = userid;
        //this.subscriptionId = subscriptionId;
        this.subscriberType = subscriberType;
        this.firstName = firstName;
        this.lastName = lastName;
        this.emailAddress = emailAddress;
        this.mobileNumber = mobileNumber;
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

   /*Comment by Adil 
    public List<NimaiMLogin> getNimaiMLoginList() {
        return nimaiMLoginList;
    }

    public void setNimaiMLoginList(List<NimaiMLogin> nimaiMLoginList) {
        this.nimaiMLoginList = nimaiMLoginList;
    }*/

//    public List<NimaiFCountrygood> getNimaiFCountrygoodList() {
//        return nimaiFCountrygoodList;
//    }
//
//    public void setNimaiFCountrygoodList(List<NimaiFCountrygood> nimaiFCountrygoodList) {
//        this.nimaiFCountrygoodList = nimaiFCountrygoodList;
//    }
//
//    public List<NimaiFKyc> getNimaiFKycList() {
//        return nimaiFKycList;
//    }
//
//    public void setNimaiFKycList(List<NimaiFKyc> nimaiFKycList) {
//        this.nimaiFKycList = nimaiFKycList;
//    }
//
//    public List<NimaiMRefer> getNimaiMReferList() {
//        return nimaiMReferList;
//    }
//
//    public void setNimaiMReferList(List<NimaiMRefer> nimaiMReferList) {
//        this.nimaiMReferList = nimaiMReferList;
//    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (userid != null ? userid.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof NimaiClient)) {
            return false;
        }
        NimaiClient other = (NimaiClient) object;
        if ((this.userid == null && other.userid != null) || (this.userid != null && !this.userid.equals(other.userid))) {
            return false;
        }
        return true;
    }

	@Override
	public String toString() {
		return "NimaiClient [subscriberType=" + subscriberType + ", userid=" + userid + ", firstName=" + firstName
				+ ", lastName=" + lastName + ", emailAddress=" + emailAddress + ", mobileNumber=" + mobileNumber
				+ ", countryName=" + countryName + ", landline=" + landline + ", designation=" + designation
				+ ", companyName=" + companyName + ", businessType=" + businessType + ", bankNbfcName=" + bankNbfcName
				+ ", branchName=" + branchName + ", swiftCode=" + swiftCode + ", minValueofLc=" + minValueofLc
				+ ", telephone=" + telephone + ", registrationType=" + registrationType + ", provincename="
				+ provincename + ", address1=" + address1 + ", address2=" + address2 + ", address3=" + address3
				+ ", city=" + city + ", pincode=" + pincode + ", rgistredCountry=" + rgistredCountry + ", isRegister="
				+ isRegister + ", isRmassigned=" + isRmassigned + ", rmId=" + rmId + ", isBdetailsfilled="
				+ isBdetailsfilled + ", isSplanPurchased=" + isSplanPurchased + ", modeOfPayment=" + modeOfPayment
				+ ", paymentStatus=" + paymentStatus + ", paymentDate=" + paymentDate + ", kycStatus=" + kycStatus
				+ ", kycApprovedDate=" + kycApprovedDate + ", insertedDate=" + insertedDate + ", modifiedDate="
				+ modifiedDate + ", accountType=" + accountType + ", accountSource=" + accountSource
				+ ", accountCreatedDate=" + accountCreatedDate + ", accountStatus=" + accountStatus + ", currencyCode="
				+ currencyCode + ", emailAddress1=" + emailAddress1 + ", emailAddress2=" + emailAddress2
				+ ", emailAddress3=" + emailAddress3 + "]";
	}



 
    
}
