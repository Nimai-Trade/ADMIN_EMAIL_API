package com.nimai.email.bean;

import java.util.List;

public class AlertToBanksBean {
	private String quotationId;
	private String customerUserId;
	private String transactionId;
	private String event;
	private String reason;
	private String emailFlag;
	private List<EmailSendingDetails> bankEmails;
	private String bankEmail;

	
	
	
	/**
	 * @return the emailFlag
	 */
	public String getEmailFlag() {
		return emailFlag;
	}

	/**
	 * @param emailFlag the emailFlag to set
	 */
	public void setEmailFlag(String emailFlag) {
		this.emailFlag = emailFlag;
	}

	/**
	 * @return the customerUserId
	 */
	public String getCustomerUserId() {
		return customerUserId;
	}

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
	 * @return the bankEmails
	 */
	public List<EmailSendingDetails> getBankEmails() {
		return bankEmails;
	}

	/**
	 * @param bankEmails the bankEmails to set
	 */
	public void setBankEmails(List<EmailSendingDetails> bankEmails) {
		this.bankEmails = bankEmails;
	}

	/**
	 * @return the bankEmail
	 */
	public String getBankEmail() {
		return bankEmail;
	}

	/**
	 * @param bankEmail the bankEmail to set
	 */
	public void setBankEmail(String bankEmail) {
		this.bankEmail = bankEmail;
	}

	/**
	 * @param customerUserId the customerUserId to set
	 */
	public void setCustomerUserId(String customerUserId) {
		this.customerUserId = customerUserId;
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

	@Override
	public String toString() {
		return "AlertToBanksBean [quotationId=" + quotationId + ", customerUserId=" + customerUserId
				+ ", transactionId=" + transactionId + ", event=" + event + ", reason=" + reason + ", bankEmails="
				+ bankEmails + ", bankEmail=" + bankEmail + "]";
	}

}
