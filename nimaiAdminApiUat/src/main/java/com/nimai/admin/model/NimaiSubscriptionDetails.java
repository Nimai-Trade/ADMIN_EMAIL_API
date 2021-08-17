/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nimai.admin.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.NamedStoredProcedureQuery;
import javax.persistence.ParameterMode;
import javax.persistence.StoredProcedureParameter;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author Sahadeo
 */
@Entity
@Table(name = "nimai_subscription_details")
@NamedQueries({
		@NamedQuery(name = "NimaiSubscriptionDetails.findAll", query = "SELECT n FROM NimaiSubscriptionDetails n") })
@NamedStoredProcedureQuery(name = "dashboardRevenue", procedureName = "DASHBOARD_REVENUE", parameters = {
		@StoredProcedureParameter(mode = ParameterMode.IN, name = "query_no", type = Integer.class),
		@StoredProcedureParameter(mode = ParameterMode.IN, name = "dateFrom", type = java.sql.Date.class),
		@StoredProcedureParameter(mode = ParameterMode.IN, name = "dateTo", type = java.sql.Date.class),
		@StoredProcedureParameter(mode = ParameterMode.IN, name = "countryNames", type = String.class),
		@StoredProcedureParameter(mode = ParameterMode.OUT, name = "subscription_count", type = Integer.class),
		@StoredProcedureParameter(mode = ParameterMode.OUT, name = "amount", type = Double.class), })

public class NimaiSubscriptionDetails implements Serializable{

	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Basic(optional = false)
	@Column(name = "SPL_SERIAL_NUMBER")
	private Integer splSerialNumber;
	
	@Column(name = "SUBSCRIPTION_ID")
	private String subscriptionId;
	
	@Column(name = "SUBSCRIPTION_NAME")
	private String subscriptionName;
	
	@Column(name = "SUBSCRIPTION_AMOUNT")
	private Integer subscriptionAmount;
	
	@Column(name = "LC_COUNT")
	private String lcCount;
	
	@Column(name = "SPLAN_START_DATE")
	@Temporal(TemporalType.DATE)
	private Date splanStartDate;
	
	@Column(name = "SPLAN_END_DATE")
	@Temporal(TemporalType.DATE)
	private Date splanEndDate;
	
	@Column(name = "FLAG")
	private Integer flag;
	
	@Column(name = "SUBSIDIARIES")
	private String subsidiaries;
	
	@Column(name = "RELATIONSHIP_MANAGER")
	private String relationshipManager;
	
	@Column(name = "CUSTOMER_SUPPORT")
	private String customerSupport;
	
	@Column(name = "REMARK")
	private String remark;
	
	@Column(name = "STATUS")
	private String status;
	
	@Column(name="DISCOUNT_ID")
	private int discountId;

	@Column(name = "SUBSCRIPTION_VALIDITY")
	private String subscriptionValidity;

	@Column(name = "LC_UTILIZED_COUNT")
	private int lcUtilizedCount;

	@Column(name = "SUBSIDIARIES_UTILIZED_COUNT")
	private int subsidiariesUtilizedCount;

	@Column(name = "IS_VAS_APPLIED")
	private int isVasApplied;

	@Column(name = "VAS_AMOUNT")
	private int vasAmount;

	@Column(name = "GRAND_AMOUNT")
	private Double grandAmount;

	@Column(name = "KYC_COUNT")
	private int kycCount;
	
	@Column(name = "INSERTED_BY")
	private String insertedBy;
	
	@Column(name = "MODIFIED_BY")
	private String modifiedBy;
	
	@Column(name = "CUSTOMER_TYPE")
	private String customerType;
	
	@Column(name = "COUNTRY_NAME")
	private String countryName;
	
	@JoinColumn(name = "userid", referencedColumnName = "USERID")
	@ManyToOne
	private NimaiMCustomer userid;

	@Column(name="PAYMENT_STATUS")
	private String paymentStatus;
	
	@Column(name="PAYMENT_MODE")
	private String paymentMode;
	
	@Column(name = "DISCOUNT")
	private Double discount;

	@Column(name = "INSERTED_DATE")
	private Date insertedDate;

	@Column(name = "MAKER_COMMENT")
	private String makerComment;

	@Column(name = "CHECKER_COMMENT")
	private String checkerComment;
	
	@Column(name = "RENEWAL_EMAIL_STATUS")
	private String renewalStatus;
	
	@Column(name="PAYMENT_TXN_ID")
	private String paymentTxnId;
	
	@Column(name="INVOICE_ID")
	private String invoiceId;
	
	
	
	
	

	public String getInvoiceId() {
		return invoiceId;
	}




	public void setInvoiceId(String invoiceId) {
		this.invoiceId = invoiceId;
	}




	public String getPaymentTxnId() {
		return paymentTxnId;
	}




	public void setPaymentTxnId(String paymentTxnId) {
		this.paymentTxnId = paymentTxnId;
	}




	public String getRenewalStatus() {
		return renewalStatus;
	}




	public void setRenewalStatus(String renewalStatus) {
		this.renewalStatus = renewalStatus;
	}




	public NimaiSubscriptionDetails() {
	}
	
	
	

	public NimaiSubscriptionDetails(Integer splSerialNumber, String subscriptionId, String subscriptionName,
			Integer subscriptionAmount, String lcCount, Date splanStartDate, Date splanEndDate, Integer flag,
			String subsidiaries, String relationshipManager, String customerSupport, String remark, String status,
			int discountId, String subscriptionValidity, int lcUtilizedCount, int subsidiariesUtilizedCount,
			int isVasApplied, int vasAmount, Double grandAmount, int kycCount, String insertedBy, String modifiedBy,
			String customerType, String countryName, NimaiMCustomer userid, Double discount, Date insertedDate,
			String makerComment, String checkerComment) {
		super();
		this.splSerialNumber = splSerialNumber;
		this.subscriptionId = subscriptionId;
		this.subscriptionName = subscriptionName;
		this.subscriptionAmount = subscriptionAmount;
		this.lcCount = lcCount;
		this.splanStartDate = splanStartDate;
		this.splanEndDate = splanEndDate;
		this.flag = flag;
		this.subsidiaries = subsidiaries;
		this.relationshipManager = relationshipManager;
		this.customerSupport = customerSupport;
		this.remark = remark;
		this.status = status;
		this.discountId = discountId;
		this.subscriptionValidity = subscriptionValidity;
		this.lcUtilizedCount = lcUtilizedCount;
		this.subsidiariesUtilizedCount = subsidiariesUtilizedCount;
		this.isVasApplied = isVasApplied;
		this.vasAmount = vasAmount;
		this.grandAmount = grandAmount;
		this.kycCount = kycCount;
		this.insertedBy = insertedBy;
		this.modifiedBy = modifiedBy;
		this.customerType = customerType;
		this.countryName = countryName;
		this.userid = userid;
		this.discount = discount;
		this.insertedDate = insertedDate;
		this.makerComment = makerComment;
		this.checkerComment = checkerComment;
	}




	public NimaiSubscriptionDetails(Integer splSerialNumber) {
		this.splSerialNumber = splSerialNumber;
	}

	public NimaiSubscriptionDetails(Integer splSerialNumber, String subscriptionValidity) {
		this.splSerialNumber = splSerialNumber;
		this.subscriptionValidity = subscriptionValidity;
	}

	public Integer getSplSerialNumber() {
		return splSerialNumber;
	}

	public void setSplSerialNumber(Integer splSerialNumber) {
		this.splSerialNumber = splSerialNumber;
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

	public Integer getSubscriptionAmount() {
		return subscriptionAmount;
	}

	public void setSubscriptionAmount(Integer subscriptionAmount) {
		this.subscriptionAmount = subscriptionAmount;
	}

	public String getLcCount() {
		return lcCount;
	}

	public void setLcCount(String lcCount) {
		this.lcCount = lcCount;
	}
	

	public String getPaymentStatus() {
		return paymentStatus;
	}




	public void setPaymentStatus(String paymentStatus) {
		this.paymentStatus = paymentStatus;
	}




	public String getPaymentMode() {
		return paymentMode;
	}




	public void setPaymentMode(String paymentMode) {
		this.paymentMode = paymentMode;
	}




	public Date getSplanStartDate() {
		return splanStartDate;
	}

	public void setSplanStartDate(Date splanStartDate) {
		this.splanStartDate = splanStartDate;
	}

	public Date getSplanEndDate() {
		return splanEndDate;
	}

	public void setSplanEndDate(Date splanEndDate) {
		this.splanEndDate = splanEndDate;
	}

	public Integer getFlag() {
		return flag;
	}

	public void setFlag(Integer flag) {
		this.flag = flag;
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

	public int getDiscountId() {
		return discountId;
	}

	public void setDiscountId(int discountId) {
		this.discountId = discountId;
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

	public String getSubscriptionValidity() {
		return subscriptionValidity;
	}

	public void setSubscriptionValidity(String subscriptionValidity) {
		this.subscriptionValidity = subscriptionValidity;
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

	public int getLcUtilizedCount() {
		return lcUtilizedCount;
	}

	public void setLcUtilizedCount(int lcUtilizedCount) {
		this.lcUtilizedCount = lcUtilizedCount;
	}

	public int getSubsidiariesUtilizedCount() {
		return subsidiariesUtilizedCount;
	}

	public void setSubsidiariesUtilizedCount(int subsidiariesUtilizedCount) {
		this.subsidiariesUtilizedCount = subsidiariesUtilizedCount;
	}

	public int getIsVasApplied() {
		return isVasApplied;
	}

	public void setIsVasApplied(int isVasApplied) {
		this.isVasApplied = isVasApplied;
	}

	public int getVasAmount() {
		return vasAmount;
	}

	public void setVasAmount(int vasAmount) {
		this.vasAmount = vasAmount;
	}

	public Double getGrandAmount() {
		return grandAmount;
	}

	public void setGrandAmount(Double grandAmount) {
		this.grandAmount = grandAmount;
	}

	public int getKycCount() {
		return kycCount;
	}

	public void setKycCount(int kycCount) {
		this.kycCount = kycCount;
	}

	public String getCustomerType() {
		return customerType;
	}

	public void setCustomerType(String customerType) {
		this.customerType = customerType;
	}

	public String getCountryName() {
		return countryName;
	}

	public void setCountryName(String countryName) {
		this.countryName = countryName;
	}

	public Double getDiscount() {
		return discount;
	}

	public void setDiscount(Double discount) {
		this.discount = discount;
	}

	public Date getInsertedDate() {
		return insertedDate;
	}

	public void setInsertedDate(Date insertedDate) {
		this.insertedDate = insertedDate;
	}

	public String getMakerComment() {
		return makerComment;
	}

	public void setMakerComment(String makerComment) {
		this.makerComment = makerComment;
	}

	public String getCheckerComment() {
		return checkerComment;
	}

	public void setCheckerComment(String checkerComment) {
		this.checkerComment = checkerComment;
	}

	@Override
	public int hashCode() {
		int hash = 0;
		hash += (splSerialNumber != null ? splSerialNumber.hashCode() : 0);
		return hash;
	}

	@Override
	public boolean equals(Object object) {
		// TODO: Warning - this method won't work in the case the id fields are not set
		if (!(object instanceof NimaiSubscriptionDetails)) {
			return false;
		}
		NimaiSubscriptionDetails other = (NimaiSubscriptionDetails) object;
		if ((this.splSerialNumber == null && other.splSerialNumber != null)
				|| (this.splSerialNumber != null && !this.splSerialNumber.equals(other.splSerialNumber))) {
			return false;
		}
		return true;
	}




	@Override
	public String toString() {
		return "NimaiSubscriptionDetails [splSerialNumber=" + splSerialNumber + ", subscriptionId=" + subscriptionId
				+ ", subscriptionName=" + subscriptionName + ", subscriptionAmount=" + subscriptionAmount + ", lcCount="
				+ lcCount + ", splanStartDate=" + splanStartDate + ", splanEndDate=" + splanEndDate + ", flag=" + flag
				+ ", subsidiaries=" + subsidiaries + ", relationshipManager=" + relationshipManager
				+ ", customerSupport=" + customerSupport + ", remark=" + remark + ", status=" + status + ", discountId="
				+ discountId + ", subscriptionValidity=" + subscriptionValidity + ", lcUtilizedCount=" + lcUtilizedCount
				+ ", subsidiariesUtilizedCount=" + subsidiariesUtilizedCount + ", isVasApplied=" + isVasApplied
				+ ", vasAmount=" + vasAmount + ", grandAmount=" + grandAmount + ", kycCount=" + kycCount
				+ ", insertedBy=" + insertedBy + ", modifiedBy=" + modifiedBy + ", customerType=" + customerType
				+ ", countryName=" + countryName + ", userid=" + userid + ", discount=" + discount + ", insertedDate="
				+ insertedDate + ", makerComment=" + makerComment + ", checkerComment=" + checkerComment + "]";
	}








}
