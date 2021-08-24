package com.nimai.lc.entity;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Date;

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

@Entity
@Table(name = "NIMAI_SUBSCRIPTION_DETAILS")
public class NimaiSubscriptionDetails implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "SPL_SERIAL_NUMBER")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer sPlSerialNUmber;

	@Column(name = "SUBSCRIPTION_ID")
	private String subscriptionId;

	@Column(name = "SUBSCRIPTION_NAME")
	private String subscriptionName;

	@Column(name = "SUBSCRIPTION_AMOUNT")
	private int subscriptionAmount;

	@Column(name = "LC_COUNT")
	private String lCount;

	@Column(name = "REMARK")
	private String remark;

	@Column(name = "STATUS")
	private String status;

	@Column(name = "SUBSCRIPTION_VALIDITY")
	private int subscriptionValidity;

	@Column(name = "INSERTED_BY")
	private String insertedBy;

	@Column(name = "MODIFIED_BY")
	private String modifiedBy;

	@Temporal(TemporalType.DATE)
	@Column(name = "SPLAN_START_DATE")
	private Date subscriptionStartDate;

	@Temporal(TemporalType.DATE)
	@Column(name = "SPLAN_END_DATE")
	private Date subscriptionEndDate;

	@Column(name = "SUBSIDIARIES")
	private String subsidiaries;

	@Column(name = "RELATIONSHIP_MANAGER")
	private String relationshipManager;

	@Column(name = "CUSTOMER_SUPPORT")
	private String customerSupport;

	@Column(name = "FLAG")
	private int flag;

	@JoinColumn(name = "USERID", referencedColumnName = "USERID")
	@ManyToOne(optional = false)
	private NimaiClient userid;

	@Column(name = "CUSTOMER_TYPE")
	private String customerType;

	@Column(name = "COUNTRY_NAME")
	private String sPLanCountry;

	@Column(name = "LC_UTILIZED_COUNT")
	private int lcUtilizedCount;

	@Column(name = "SUBSIDIARIES_UTILIZED_COUNT")
	private int subsidiaryUtilizedCount;

	@Column(name = "IS_VAS_APPLIED")
	private int isVasApplied;

	@Column(name = "VAS_AMOUNT")
	private int vasAmount;

	@Column(name = "GRAND_AMOUNT")
	private int grandAmount;

	@Column(name = "KYC_COUNT")
	private int kycCount;

	@Column(name = "DISCOUNT")
	private Integer discount;

	@Column(name = "DISCOUNT_ID")
	private int discountId;

	@Column(name = "RENEWAL_EMAIL_STATUS")
	private String renewalEmailStatus;

	public String getRenewalEmailStatus() {
		return renewalEmailStatus;
	}

	public void setRenewalEmailStatus(String renewalEmailStatus) {
		this.renewalEmailStatus = renewalEmailStatus;
	}

	public int getDiscountId() {
		return discountId;
	}

	public void setDiscountId(int discountId) {
		this.discountId = discountId;
	}

	public Integer getDiscount() {
		return discount;
	}

	public void setDiscount(Integer discount) {
		this.discount = discount;
	}

	/**
	 * @return the lcUtilizedCount
	 */
	public int getLcUtilizedCount() {
		return lcUtilizedCount;
	}

	/**
	 * @param lcUtilizedCount the lcUtilizedCount to set
	 */
	public void setLcUtilizedCount(int lcUtilizedCount) {
		this.lcUtilizedCount = lcUtilizedCount;
	}

	/**
	 * @return the subsidiaryUtilizedCount
	 */
	public int getSubsidiaryUtilizedCount() {
		return subsidiaryUtilizedCount;
	}

	/**
	 * @param subsidiaryUtilizedCount the subsidiaryUtilizedCount to set
	 */
	public void setSubsidiaryUtilizedCount(int subsidiaryUtilizedCount) {
		this.subsidiaryUtilizedCount = subsidiaryUtilizedCount;
	}

	/**
	 * @return the isVasApplied
	 */
	public int getIsVasApplied() {
		return isVasApplied;
	}

	/**
	 * @param isVasApplied the isVasApplied to set
	 */
	public void setIsVasApplied(int isVasApplied) {
		this.isVasApplied = isVasApplied;
	}

	/**
	 * @return the vasAmount
	 */
	public int getVasAmount() {
		return vasAmount;
	}

	/**
	 * @param vasAmount the vasAmount to set
	 */
	public void setVasAmount(int vasAmount) {
		this.vasAmount = vasAmount;
	}

	/**
	 * @return the grandAmount
	 */
	public int getGrandAmount() {
		return grandAmount;
	}

	/**
	 * @param grandAmount the grandAmount to set
	 */
	public void setGrandAmount(int grandAmount) {
		this.grandAmount = grandAmount;
	}

	/**
	 * @return the kycCount
	 */
	public int getKycCount() {
		return kycCount;
	}

	/**
	 * @param kycCount the kycCount to set
	 */
	public void setKycCount(int kycCount) {
		this.kycCount = kycCount;
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

	public String getCustomerType() {
		return customerType;
	}

	public void setCustomerType(String customerType) {
		this.customerType = customerType;
	}

	public int getSubscriptionValidity() {
		return subscriptionValidity;
	}

	public void setSubscriptionValidity(int subscriptionValidity) {
		this.subscriptionValidity = subscriptionValidity;
	}

	public String getSubsidiaries() {
		return subsidiaries;
	}

	public void setSubsidiaries(String subsidiaries) {
		this.subsidiaries = subsidiaries;
	}

	public String getRelationshipManager() {
		return relationshipManager;
	}

	public void setRelationshipManager(String relationshipManager) {
		this.relationshipManager = relationshipManager;
	}

	public String getCustomerSupport() {
		return customerSupport;
	}

	public void setCustomerSupport(String customerSupport) {
		this.customerSupport = customerSupport;
	}

	public int getFlag() {
		return flag;
	}

	public void setFlag(int flag) {
		this.flag = flag;
	}

	public Date getSubscriptionEndDate() {
		return subscriptionEndDate;
	}

	public void setSubscriptionEndDate(Date subscriptionEndDate) {
		this.subscriptionEndDate = subscriptionEndDate;
	}

	public Date getSubscriptionStartDate() {
		return subscriptionStartDate;
	}

	public void setSubscriptionStartDate(Date subscriptionStartDate) {
		this.subscriptionStartDate = subscriptionStartDate;
	}

	public Integer getsPlSerialNUmber() {
		return sPlSerialNUmber;
	}

	public void setsPlSerialNUmber(Integer sPlSerialNUmber) {
		this.sPlSerialNUmber = sPlSerialNUmber;
	}

	public String getSubscriptionId() {
		return subscriptionId;
	}

	public void setSubscriptionId(String subscriptionId) {
		this.subscriptionId = subscriptionId;
	}

	public String getSubscriptionName() {
		return subscriptionName;
	}

	public void setSubscriptionName(String subscriptionName) {
		this.subscriptionName = subscriptionName;
	}

	public int getSubscriptionAmount() {
		return subscriptionAmount;
	}

	public void setSubscriptionAmount(int subscriptionAmount) {
		this.subscriptionAmount = subscriptionAmount;
	}

	public String getlCount() {
		return lCount;
	}

	public void setlCount(String lCount) {
		this.lCount = lCount;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
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

	public NimaiClient getUserid() {
		return userid;
	}

	public void setUserid(NimaiClient userid) {
		this.userid = userid;
	}

	@Override
	public String toString() {
		return "NimaiSubscriptionDetails [sPlSerialNUmber=" + sPlSerialNUmber + ", subscriptionId=" + subscriptionId
				+ ", subscriptionName=" + subscriptionName + ", subscriptionAmount=" + subscriptionAmount + ", lCount="
				+ lCount + ", remark=" + remark + ", status=" + status + ", subscriptionValidity="
				+ subscriptionValidity + ", insertedBy=" + insertedBy + ", modifiedBy=" + modifiedBy
				+ ", subscriptionStartDate=" + subscriptionStartDate + ", subscriptionEndDate=" + subscriptionEndDate
				+ ", subsidiaries=" + subsidiaries + ", relationshipManager=" + relationshipManager
				+ ", customerSupport=" + customerSupport + ", flag=" + flag + ", userid=" + userid + ", customerType="
				+ customerType + ", sPLanCountry=" + sPLanCountry + ", lcUtilizedCount=" + lcUtilizedCount
				+ ", subsidiaryUtilizedCount=" + subsidiaryUtilizedCount + ", isVasApplied=" + isVasApplied
				+ ", vasAmount=" + vasAmount + ", grandAmount=" + grandAmount + ", kycCount=" + kycCount + ", discount="
				+ discount + ", discountId=" + discountId + "]";
	}

}
