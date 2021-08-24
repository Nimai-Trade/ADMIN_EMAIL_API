package com.nimai.kyc.model;

import java.util.Date;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.ColumnResult;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

import com.nimai.kyc.payload.BusinessKycList;

@Entity
@Table(name = "nimai_email_scheduler_account_alert")
public class NimaiEmailScheduler {

	@Id
	@Basic(optional = false)
	@NotNull
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "account_scheduler_id")
	private int accountSchedulerId;

	@Column(name = "user_id")
	private String userid;

	@Column(name = "user_name")
	private String userName;

	@Column(name = "Branch_id")
	private String branchId;

	@Column(name = "SUBSCRIPTION_ID")
	private String subscriptionId;

	@Column(name = "SUBSCRIPTION_NAME")
	private String subscriptionName;

	@Column(name = "SUBSCRIPTION_AMOUNT")
	private String subscriptionAmount;

	@Column(name = "SUBSCRIPTION_VALIDITY")
	private String subscriptionValidity;

	@Column(name = "RELATIONSHIP_MANAGER")
	private String relationshipManager;

	@Column(name = "CUSTOMER_SUPPORT")
	private String customerSupport;

	@Column(name = "COUNTRY_NAME")
	private String sPLanCountry;

	@Column(name = "SPLAN_START_DATE")
	@Temporal(TemporalType.TIMESTAMP)
	private Date subscriptionStartDate;

	@Column(name = "SPLAN_END_DATE")
	@Temporal(TemporalType.TIMESTAMP)
	private Date subscriptionEndDate;

	@Column(name = "kyc_doc_name")
	private String kycDocName;

	@Column(name = "rm_name")
	private String rMName;

	@Column(name = "rm_email_id")
	private String rMemailId;

	@Column(name = "kyc_approval_date")
	private Date kycApprovalDate;

	@Column(name = "kyc_personal_doc")
	private String personalKycDoc;

	@Column(name = "kyc_business_doc")
	private String BusinessKycDoc;

	@Column(name = "kyc_rejected_date")
	private String kycRejectedDate;

	@Column(name = "reject_reason")
	private String reason;

	@Column(name = "rejectec_doc")
	private String rejectedDoc;

	@Column(name = "event")
	private String event;

	@Column(name = "email_status")
	private String emailStatus;

	@Column(name = "email_Id")
	private String emailId;

	@Column(name = "inserted_date")
	private Date insertedDate;

	/**
	 * @return the insertedDate
	 */
	public Date getInsertedDate() {
		return insertedDate;
	}

	/**
	 * @param insertedDate the insertedDate to set
	 */
	public void setInsertedDate(Date insertedDate) {
		this.insertedDate = insertedDate;
	}

	/**
	 * @return the branchId
	 */
	public String getBranchId() {
		return branchId;
	}

	/**
	 * @param branchId the branchId to set
	 */
	public void setBranchId(String branchId) {
		this.branchId = branchId;
	}

	/**
	 * @return the rejectedDoc
	 */
	public String getRejectedDoc() {
		return rejectedDoc;
	}

	/**
	 * @param rejectedDoc the rejectedDoc to set
	 */
	public void setRejectedDoc(String rejectedDoc) {
		this.rejectedDoc = rejectedDoc;
	}

	/**
	 * @return the reason
	 */
	public String getReason() {
		return reason;
	}

	/**
	 * @param reason the reason to set
	 */
	public void setReason(String reason) {
		this.reason = reason;
	}

	/**
	 * @return the personalKycDoc
	 */
	public String getPersonalKycDoc() {
		return personalKycDoc;
	}

	/**
	 * @param personalKycDoc the personalKycDoc to set
	 */
	public void setPersonalKycDoc(String personalKycDoc) {
		this.personalKycDoc = personalKycDoc;
	}

	/**
	 * @return the businessKycDoc
	 */
	public String getBusinessKycDoc() {
		return BusinessKycDoc;
	}

	/**
	 * @param businessKycDoc the businessKycDoc to set
	 */
	public void setBusinessKycDoc(String businessKycDoc) {
		BusinessKycDoc = businessKycDoc;
	}

	/**
	 * @return the kycDocName
	 */
	public String getKycDocName() {
		return kycDocName;
	}

	/**
	 * @param kycDocName the kycDocName to set
	 */
	public void setKycDocName(String kycDocName) {
		this.kycDocName = kycDocName;
	}

	/**
	 * @return the rMName
	 */
	public String getrMName() {
		return rMName;
	}

	/**
	 * @param rMName the rMName to set
	 */
	public void setrMName(String rMName) {
		this.rMName = rMName;
	}

	/**
	 * @return the rMemailId
	 */
	public String getrMemailId() {
		return rMemailId;
	}

	/**
	 * @param rMemailId the rMemailId to set
	 */
	public void setrMemailId(String rMemailId) {
		this.rMemailId = rMemailId;
	}

	/**
	 * @return the kycApprovalDate
	 */
	public Date getKycApprovalDate() {
		return kycApprovalDate;
	}

	/**
	 * @param kycApprovalDate the kycApprovalDate to set
	 */
	public void setKycApprovalDate(Date kycApprovalDate) {
		this.kycApprovalDate = kycApprovalDate;
	}

	/**
	 * @return the kycRejectedDate
	 */
	public String getKycRejectedDate() {
		return kycRejectedDate;
	}

	/**
	 * @param kycRejectedDate the kycRejectedDate to set
	 */
	public void setKycRejectedDate(String kycRejectedDate) {
		this.kycRejectedDate = kycRejectedDate;
	}

	/**
	 * @return the accountSchedulerId
	 */
	public int getAccountSchedulerId() {
		return accountSchedulerId;
	}

	/**
	 * @param accountSchedulerId the accountSchedulerId to set
	 */
	public void setAccountSchedulerId(int accountSchedulerId) {
		this.accountSchedulerId = accountSchedulerId;
	}

	/**
	 * @return the userid
	 */
	public String getUserid() {
		return userid;
	}

	/**
	 * @param userid the userid to set
	 */
	public void setUserid(String userid) {
		this.userid = userid;
	}

	/**
	 * @return the emailId
	 */
	public String getEmailId() {
		return emailId;
	}

	/**
	 * @param emailId the emailId to set
	 */
	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}

	/**
	 * @return the userName
	 */
	public String getUserName() {
		return userName;
	}

	/**
	 * @param userName the userName to set
	 */
	public void setUserName(String userName) {
		this.userName = userName;
	}

	/**
	 * @return the event
	 */
	public String getEvent() {
		return event;
	}

	/**
	 * @param event the event to set
	 */
	public void setEvent(String event) {
		this.event = event;
	}

	/**
	 * @return the emailStatus
	 */
	public String getEmailStatus() {
		return emailStatus;
	}

	/**
	 * @param emailStatus the emailStatus to set
	 */
	public void setEmailStatus(String emailStatus) {
		this.emailStatus = emailStatus;
	}

	/**
	 * @return the subscriptionId
	 */
	public String getSubscriptionId() {
		return subscriptionId;
	}

	/**
	 * @param subscriptionId the subscriptionId to set
	 */
	public void setSubscriptionId(String subscriptionId) {
		this.subscriptionId = subscriptionId;
	}

	/**
	 * @return the subscriptionName
	 */
	public String getSubscriptionName() {
		return subscriptionName;
	}

	/**
	 * @param subscriptionName the subscriptionName to set
	 */
	public void setSubscriptionName(String subscriptionName) {
		this.subscriptionName = subscriptionName;
	}

	/**
	 * @return the subscriptionAmount
	 */
	public String getSubscriptionAmount() {
		return subscriptionAmount;
	}

	/**
	 * @param subscriptionAmount the subscriptionAmount to set
	 */
	public void setSubscriptionAmount(String subscriptionAmount) {
		this.subscriptionAmount = subscriptionAmount;
	}

	/**
	 * @return the subscriptionValidity
	 */
	public String getSubscriptionValidity() {
		return subscriptionValidity;
	}

	/**
	 * @param subscriptionValidity the subscriptionValidity to set
	 */
	public void setSubscriptionValidity(String subscriptionValidity) {
		this.subscriptionValidity = subscriptionValidity;
	}

	/**
	 * @return the relationshipManager
	 */
	public String getRelationshipManager() {
		return relationshipManager;
	}

	/**
	 * @param relationshipManager the relationshipManager to set
	 */
	public void setRelationshipManager(String relationshipManager) {
		this.relationshipManager = relationshipManager;
	}

	/**
	 * @return the customerSupport
	 */
	public String getCustomerSupport() {
		return customerSupport;
	}

	/**
	 * @param customerSupport the customerSupport to set
	 */
	public void setCustomerSupport(String customerSupport) {
		this.customerSupport = customerSupport;
	}

	/**
	 * @return the sPLanCountry
	 */
	public String getsPLanCountry() {
		return sPLanCountry;
	}

	/**
	 * @param sPLanCountry the sPLanCountry to set
	 */
	public void setsPLanCountry(String sPLanCountry) {
		this.sPLanCountry = sPLanCountry;
	}

	/**
	 * @return the subscriptionStartDate
	 */
	public Date getSubscriptionStartDate() {
		return subscriptionStartDate;
	}

	/**
	 * @param subscriptionStartDate the subscriptionStartDate to set
	 */
	public void setSubscriptionStartDate(Date subscriptionStartDate) {
		this.subscriptionStartDate = subscriptionStartDate;
	}

	/**
	 * @return the subscriptionEndDate
	 */
	public Date getSubscriptionEndDate() {
		return subscriptionEndDate;
	}

	/**
	 * @param subscriptionEndDate the subscriptionEndDate to set
	 */
	public void setSubscriptionEndDate(Date subscriptionEndDate) {
		this.subscriptionEndDate = subscriptionEndDate;
	}

}
