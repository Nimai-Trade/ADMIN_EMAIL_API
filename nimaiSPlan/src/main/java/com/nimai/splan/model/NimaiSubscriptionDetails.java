package com.nimai.splan.model;

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
import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;

import com.fasterxml.jackson.annotation.JsonFormat;

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

	@Column(name = "SPLAN_START_DATE")
	@Temporal(TemporalType.TIMESTAMP)
	private Date subscriptionStartDate;

	@Column(name = "SPLAN_END_DATE")
	@Temporal(TemporalType.TIMESTAMP)
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
	private NimaiMCustomer userid;

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
	@DecimalMin("0.00") 
	@DecimalMax("99999.00") 
	private Double grandAmount;

	@Column(name = "KYC_COUNT")
	private int kycCount;
	
	@Column(name = "DISCOUNT")
	private Double discount;

	@Column(name = "DISCOUNT_ID")
	private int discountId;
	
	@Column(name="RENEWAL_EMAIL_STATUS")
	private String renewalEmailStatus;
	
	@Column(name="INSERTED_DATE")
	private Date insertedDate;
	
	@Column(name="PAYMENT_MODE")
	private String paymentMode;
	
	@Column(name="PAYMENT_STATUS")
	private String paymentStatus;
	
	@Column(name="INVOICE_ID")
	private String invoiceId;
	
	
	public String getInvoiceId() {
		return invoiceId;
	}

	public void setInvoiceId(String invoiceId) {
		this.invoiceId = invoiceId;
	}

	public String getPaymentMode() {
		return paymentMode;
	}

	public void setPaymentMode(String paymentMode) {
		this.paymentMode = paymentMode;
	}

	public String getPaymentStatus() {
		return paymentStatus;
	}

	public void setPaymentStatus(String paymentStatus) {
		this.paymentStatus = paymentStatus;
	}

	public Date getInsertedDate() {
		return insertedDate;
	}

	public void setInsertedDate(Date insertedDate) {
		this.insertedDate = insertedDate;
	}

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

	public Double getDiscount() {
		return discount;
	}

	public void setDiscount(Double discount) {
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
	public Double getGrandAmount() {
		return grandAmount;
	}

	/**
	 * @param grandAmount the grandAmount to set
	 */
	public void setGrandAmount(Double grandAmount) {
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

	public NimaiMCustomer getUserid() {
		return userid;
	}

	public void setUserid(NimaiMCustomer userid) {
		this.userid = userid;
	}

}
