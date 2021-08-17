package com.nimai.email.bean;

public class QuotationAlertRequest {
private String quotationId;
private String transactionId;
private String reason;
private String bankEmailId;
private String event;






/**
 * @return the quotationId
 */
public String getQuotationId() {
	return quotationId;
}
/**
 * @param quotationId the quotationId to set
 */
public void setQuotationId(String quotationId) {
	this.quotationId = quotationId;
}
/**
 * @return the transactionId
 */
public String getTransactionId() {
	return transactionId;
}
/**
 * @param transactionId the transactionId to set
 */
public void setTransactionId(String transactionId) {
	this.transactionId = transactionId;
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
 * @return the bankEmailId
 */
public String getBankEmailId() {
	return bankEmailId;
}
/**
 * @param bankEmailId the bankEmailId to set
 */
public void setBankEmailId(String bankEmailId) {
	this.bankEmailId = bankEmailId;
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



}
