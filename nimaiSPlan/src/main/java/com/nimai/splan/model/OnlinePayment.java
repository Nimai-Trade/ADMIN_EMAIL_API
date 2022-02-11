package com.nimai.splan.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="nimai_m_online_payment")
public class OnlinePayment implements Serializable {
	
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="ID")
	private Integer id;
	
	@Column(name="INVOICE_ID")
	private String invoiceId;
	
	@Column(name="USER_ID")
	private String userId;
	
	@Column(name="ORDER_ID")
	private String orderId;
	
	@Column(name="AMOUNT")
	private Double amount;
	
	@Column(name="CURRENCY")
	private String currency;
	
	@Column(name="BANK_RECEIPT_NO")
	private String bankReceiptNo;
	
	//@Column(name="TRACKING_ID")
	//private String trackingId;
	
	@Column(name="TRANSACTION_ID")
	private String transactionId;
	
	@Column(name="STATUS_MESSAGE")
	private String statusMessage;
	
	@Column(name="STATUS")
	private String status;
	
	@Column(name="FAILURE_MESSAGE")
	private String failureMessage;
	
	@Column(name="REQUEST_DUMP")
	private String requestDump;
	
	@Column(name="RESPONSE_DUMP")
	private String responseDump;
	
	@Column(name="INSERTED_BY")
	private String insertedBy;
	
	@Column(name="INSERTED_DATE")
	private Date insertedDate;
	
	@Column(name="MODIFIED_BY")
	private String modifiedBy;
	
    @Column(name="MODIFIED_DATE")
	private Date modifiedDate;

    
    
	public String getInvoiceId() {
		return invoiceId;
	}

	public void setInvoiceId(String invoiceId) {
		this.invoiceId = invoiceId;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public Double getAmount() {
		return amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public String getBankReceiptNo() {
		return bankReceiptNo;
	}

	public void setBankReceiptNo(String bankReceiptNo) {
		this.bankReceiptNo = bankReceiptNo;
	}

	/*public String getTrackingId() {
		return trackingId;
	}

	public void setTrackingId(String trackingId) {
		this.trackingId = trackingId;
	}*/
	
	public String getTransactionId() {
		return transactionId;
	}

	public void setTransactionId(String transactionId) {
		this.transactionId = transactionId;
	}

	public String getStatusMessage() {
		return statusMessage;
	}

	public void setStatusMessage(String statusMessage) {
		this.statusMessage = statusMessage;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getFailureMessage() {
		return failureMessage;
	}

	public void setFailureMessage(String failureMessage) {
		this.failureMessage = failureMessage;
	}

	public String getRequestDump() {
		return requestDump;
	}

	public void setRequestDump(String requestDump) {
		this.requestDump = requestDump;
	}

	public String getResponseDump() {
		return responseDump;
	}

	public void setResponseDump(String responseDump) {
		this.responseDump = responseDump;
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
