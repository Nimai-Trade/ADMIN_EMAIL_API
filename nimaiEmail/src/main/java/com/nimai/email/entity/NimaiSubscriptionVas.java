package com.nimai.email.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="nimai_subscription_vas")
public class NimaiSubscriptionVas implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="ID")
	private Integer id;
	

	@Column(name="SUBSCRIPTION_ID")
	private String subscriptionId;
	
	@Column(name="VAS_ID")
	private Integer vasId;
	
	@Column(name="COUNTRY_NAME")
	private String countryName;
	
	@Column(name="PLAN_NAME")
	private String planName;
	
	@Column(name="DESCRIPTION_1")
	private String description_1;
	
	@Column(name="DESCRIPTION_2")
	private String description_2;
	
	@Column(name="DESCRIPTION_3")
	private String description_3;
	
	@Column(name="DESCRIPTION_4")
	private String description_4;
	
	@Column(name="DESCRIPTION_5")
	private String description_5;
	
	@Column(name="CURRENCY")
	private String currency;
	
	@Column(name="PRICING")
	private Float pricing;
	
	@Column(name="STATUS")
	private String status;
	
	@Column(name="INSERTED_BY")
	private String insertedBy;
	
	@Column(name="INSERTED_DATE")
	private Date insertedDate;
	
	@Column(name="MODIFIED_BY")
	private String modifiedBy;
	
    @Column(name="MODIFIED_DATE")
	private Date modifiedDate;
    
    @JoinColumn(name = "USERID", referencedColumnName = "USERID")
	@ManyToOne(optional = false)
	private NimaiClient userid;
    
    @Column(name="invoice_id")
    private String invoiceId;
    
	@Column(name = "PAYMENT_APPROVED_BY")
	private String paymentApprovedBy;

	@Column(name = "PAYMENT_APPROVAL_DATE")
	private Date paymentApprovalDate;

	@Column(name = "MODE")
	private String mode;

	@Column(name = "PAYMENT_STATUS")
	private String paymentSts;

	@Column(name = "PAYMENT_TXN_ID")
	private String paymentTxnId;
	
	
	
	

	public String getInvoiceId() {
		return invoiceId;
	}

	public void setInvoiceId(String invoiceId) {
		this.invoiceId = invoiceId;
	}

	public String getPaymentApprovedBy() {
		return paymentApprovedBy;
	}

	public void setPaymentApprovedBy(String paymentApprovedBy) {
		this.paymentApprovedBy = paymentApprovedBy;
	}

	public Date getPaymentApprovalDate() {
		return paymentApprovalDate;
	}

	public void setPaymentApprovalDate(Date paymentApprovalDate) {
		this.paymentApprovalDate = paymentApprovalDate;
	}

	public String getMode() {
		return mode;
	}

	public void setMode(String mode) {
		this.mode = mode;
	}

	public String getPaymentSts() {
		return paymentSts;
	}

	public void setPaymentSts(String paymentSts) {
		this.paymentSts = paymentSts;
	}

	public String getPaymentTxnId() {
		return paymentTxnId;
	}

	public void setPaymentTxnId(String paymentTxnId) {
		this.paymentTxnId = paymentTxnId;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}


	public String getSubscriptionId() {
		return subscriptionId;
	}

	public void setSubscriptionId(String subscriptionId) {
		this.subscriptionId = subscriptionId;
	}

	public Integer getVasId() {
		return vasId;
	}

	public void setVasId(Integer vasId) {
		this.vasId = vasId;
	}

	public String getCountryName() {
		return countryName;
	}

	public void setCountryName(String countryName) {
		this.countryName = countryName;
	}

	public String getPlanName() {
		return planName;
	}

	public void setPlanName(String planName) {
		this.planName = planName;
	}

	public String getDescription_1() {
		return description_1;
	}

	public void setDescription_1(String description_1) {
		this.description_1 = description_1;
	}

	public String getDescription_2() {
		return description_2;
	}

	public void setDescription_2(String description_2) {
		this.description_2 = description_2;
	}

	public String getDescription_3() {
		return description_3;
	}

	public void setDescription_3(String description_3) {
		this.description_3 = description_3;
	}

	public String getDescription_4() {
		return description_4;
	}

	public void setDescription_4(String description_4) {
		this.description_4 = description_4;
	}

	public String getDescription_5() {
		return description_5;
	}

	public void setDescription_5(String description_5) {
		this.description_5 = description_5;
	}

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public Float getPricing() {
		return pricing;
	}

	public void setPricing(Float pricing) {
		this.pricing = pricing;
	}
	public NimaiClient getUserid() {
		return userid;
	}

	public void setUserid(NimaiClient userid) {
		this.userid = userid;
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

	public Date getInsertedDate() {
		return insertedDate;
	}

	public void setInsertedDate(Date insertedDate) {
		this.insertedDate = insertedDate;
	}

	public String getModifiedBy() {
		return modifiedBy;
	}

	public void setModifiedBy(String modifiedBy) {
		this.modifiedBy = modifiedBy;
	}

	public Date getModifiedDate() {
		return modifiedDate;
	}

	public void setModifiedDate(Date modifiedDate) {
		this.modifiedDate = modifiedDate;
	}

	
   
}
