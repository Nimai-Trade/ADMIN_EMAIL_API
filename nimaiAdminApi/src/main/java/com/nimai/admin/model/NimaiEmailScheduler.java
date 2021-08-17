package com.nimai.admin.model;

import java.util.Date;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.ColumnResult;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.DynamicUpdate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity
@Table(name = "nimai_email_scheduler_account_alert")
@EntityListeners(AuditingEntityListener.class)
@DynamicUpdate
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

	@Column(name = "sub_user_id")
	private String subUserId;

	@Column(name = "sub_first_name")
	private String subFirstName;

	@Column(name = "sub_last_name")
	private String subLastName;

	@Column(name = "sub_Email")
	private String subOfficailEmail;

	@Column(name = "sub_country")
	private String subCountry;

	@Column(name = "sub_Mobile")
	private String subMobile;

	@Column(name = "sub_land_line")
	private String subLandLine;

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

	@Column(name = "SPLAN_LC_COUNT")
	private String sPLanLcCount;

	@Column(name = "SUBSIDIARIES")
	private String subsidiaryUsers;

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

	@Column(name = "Coupon_CODE")
	private String couponCode;

	@Column(name = "email_status")
	private String emailStatus;

	@Column(name = "email_Id")
	private String emailId;

	@Column(name = "Branch_id")
	private String branchId;

	@Column(name = "SPLAN_CURRENCY")
	private String sPlanCurrency;

	@Column(name = "CUSTOMER_TYPE")
	private String customerType;

	@Column(name = "Discount_Type")
	private String discountType;

	@Column(name = "Coupon_Start_Date")
	private String startDate;

	@Column(name = "Coupon_Start_Time")
	private String startTime;

	@Column(name = "Coupon_End_Date")
	private String endDate;

	@Column(name = "Coupon_End_Time")
	private String endTime;

	@Column(name = "Quantity")
	private String quantity;

	@Column(name = "Discount_Percentage")
	private String discountPercentage;

	@Column(name = "DESCRIPTION1")
	private String description1;

	@Column(name = "DESCRIPTION2")
	private String description2;

	@Column(name = "DESCRIPTION3")
	private String description3;

	@Column(name = "DESCRIPTION4")
	private String description4;

	@Column(name = "DESCRIPTION5")
	private String description5;

	@Column(name = "inserted_date")
	private Date insertedDate;

	public String getDescription1() {
		return description1;
	}

	public void setDescription1(String description1) {
		this.description1 = description1;
	}

	public String getDescription2() {
		return description2;
	}

	public void setDescription2(String description2) {
		this.description2 = description2;
	}

	public String getDescription3() {
		return description3;
	}

	public void setDescription3(String description3) {
		this.description3 = description3;
	}

	public String getDescription4() {
		return description4;
	}

	public void setDescription4(String description4) {
		this.description4 = description4;
	}

	public String getDescription5() {
		return description5;
	}

	public void setDescription5(String description5) {
		this.description5 = description5;
	}

	public String getCouponCode() {
		return couponCode;
	}

	public void setCouponCode(String couponCode) {
		this.couponCode = couponCode;
	}

	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	public String getQuantity() {
		return quantity;
	}

	public void setQuantity(String quantity) {
		this.quantity = quantity;
	}

	public String getDiscountPercentage() {
		return discountPercentage;
	}

	public void setDiscountPercentage(String discountPercentage) {
		this.discountPercentage = discountPercentage;
	}

	public String getDiscountType() {
		return discountType;
	}

	public void setDiscountType(String discountType) {
		this.discountType = discountType;
	}

	public String getCustomerType() {
		return customerType;
	}

	public void setCustomerType(String customerType) {
		this.customerType = customerType;
	}

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

	public String getSubUserId() {
		return subUserId;
	}

	public void setSubUserId(String subUserId) {
		this.subUserId = subUserId;
	}

	public String getSubFirstName() {
		return subFirstName;
	}

	public void setSubFirstName(String subFirstName) {
		this.subFirstName = subFirstName;
	}

	public String getsPLanLcCount() {
		return sPLanLcCount;
	}

	public void setsPLanLcCount(String sPLanLcCount) {
		this.sPLanLcCount = sPLanLcCount;
	}

	public String getSubsidiaryUsers() {
		return subsidiaryUsers;
	}

	public void setSubsidiaryUsers(String subsidiaryUsers) {
		this.subsidiaryUsers = subsidiaryUsers;
	}

	public String getsPlanCurrency() {
		return sPlanCurrency;
	}

	public void setsPlanCurrency(String sPlanCurrency) {
		this.sPlanCurrency = sPlanCurrency;
	}

	public String getSubLastName() {
		return subLastName;
	}

	public void setSubLastName(String subLastName) {
		this.subLastName = subLastName;
	}

	public String getSubOfficailEmail() {
		return subOfficailEmail;
	}

	public void setSubOfficailEmail(String subOfficailEmail) {
		this.subOfficailEmail = subOfficailEmail;
	}

	public String getSubCountry() {
		return subCountry;
	}

	public void setSubCountry(String subCountry) {
		this.subCountry = subCountry;
	}

	public String getSubMobile() {
		return subMobile;
	}

	public void setSubMobile(String subMobile) {
		this.subMobile = subMobile;
	}

	public String getSubLandLine() {
		return subLandLine;
	}

	public void setSubLandLine(String subLandLine) {
		this.subLandLine = subLandLine;
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

	@Override
	public String toString() {
		return "NimaiEmailScheduler [accountSchedulerId=" + accountSchedulerId + ", subscriptionId=" + subscriptionId
				+ ", subscriptionName=" + subscriptionName + ", subscriptionAmount=" + subscriptionAmount
				+ ", subscriptionValidity=" + subscriptionValidity + ", relationshipManager=" + relationshipManager
				+ ", customerSupport=" + customerSupport + ", sPLanCountry=" + sPLanCountry + ", subscriptionStartDate="
				+ subscriptionStartDate + ", subscriptionEndDate=" + subscriptionEndDate + ", kycDocName=" + kycDocName
				+ ", rMName=" + rMName + ", rMemailId=" + rMemailId + ", kycApprovalDate=" + kycApprovalDate
				+ ", personalKycDoc=" + personalKycDoc + ", BusinessKycDoc=" + BusinessKycDoc + ", kycRejectedDate="
				+ kycRejectedDate + ", reason=" + reason + ", rejectedDoc=" + rejectedDoc + "]";
	}

}

