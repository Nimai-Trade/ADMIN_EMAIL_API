package com.nimai.splan.payload;

public class PaymentResponseBean 
{
	private String userId;
	private String merchantId;
	private String orderId;
	private String trackingId;
	private String bankReceiptNo;
	private String bankRefNo;
	private Double amount;
	private String currency;
	private String statusMsg;
	private String orderStatus;
	private String paymentMode;
	private Double eciValue;
	private String failureMsg;
	private String cardName;
	private String requestDump;
	private String responseDump;
	private String status;
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getMerchantId() {
		return merchantId;
	}
	public void setMerchantId(String merchantId) {
		this.merchantId = merchantId;
	}
	public String getOrderId() {
		return orderId;
	}
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	public String getTrackingId() {
		return trackingId;
	}
	public void setTrackingId(String trackingId) {
		this.trackingId = trackingId;
	}
	public String getBankReceiptNo() {
		return bankReceiptNo;
	}
	public void setBankReceiptNo(String bankReceiptNo) {
		this.bankReceiptNo = bankReceiptNo;
	}
	public String getBankRefNo() {
		return bankRefNo;
	}
	public void setBankRefNo(String bankRefNo) {
		this.bankRefNo = bankRefNo;
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
	public String getStatusMsg() {
		return statusMsg;
	}
	public void setStatusMsg(String statusMsg) {
		this.statusMsg = statusMsg;
	}
	public String getOrderStatus() {
		return orderStatus;
	}
	public void setOrderStatus(String orderStatus) {
		this.orderStatus = orderStatus;
	}
	public String getPaymentMode() {
		return paymentMode;
	}
	public void setPaymentMode(String paymentMode) {
		this.paymentMode = paymentMode;
	}
	public Double getEciValue() {
		return eciValue;
	}
	public void setEciValue(Double eciValue) {
		this.eciValue = eciValue;
	}
	public String getFailureMsg() {
		return failureMsg;
	}
	public void setFailureMsg(String failureMsg) {
		this.failureMsg = failureMsg;
	}
	public String getCardName() {
		return cardName;
	}
	public void setCardName(String cardName) {
		this.cardName = cardName;
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
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	
	
}
