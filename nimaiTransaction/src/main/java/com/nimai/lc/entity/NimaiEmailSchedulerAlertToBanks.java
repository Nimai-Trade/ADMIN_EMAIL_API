package com.nimai.lc.entity;

import java.util.Date;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table(name="nimai_email_scheduler_alerts_tobanks")
public class NimaiEmailSchedulerAlertToBanks {

	@Id
	@Basic(optional = false)
	@NotNull
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "scheduler_Id")
	private int scedulerid;

	@Column(name = "customer_user_id")
	private String customerid;

	@Column(name = "transaction_id")
	private String transactionid;

	@Column(name = "reason")
	private String reason;
	
	@Column(name="banks_email_id")
	private String banksEmailID;

	@Column(name = "inserted_date")
	private Date insertedDate;
	
	@Column(name="bank_user_id")
	private String bankUserid;
	
	@Column(name="bank_user_name")
	private String bankUserName;
	
	@Column(name="email_event")
	private String emailEvent;
	
	@Column(name="quotation_id")
	private Integer quotationId;
	
	@Column(name="customer_email_id")
	private String customerEmail;
	
	@Column(name="passcode_user_email")
	private String passcodeuserEmail;
	
	@Column(name="customer_user_name")
	private String customerUserName;
	
	@Column(name="Email_Status")
	private String emailStatus;
	
	@Column(name="TR_SCHEDULED_ID")
	private String trScheduledId;
	
	@Column(name="TR_Email_status_ToBanks")
	private String transactionEmailStatusToBanks;
	
	
	
	public String getPasscodeuserEmail() {
		return passcodeuserEmail;
	}

	public void setPasscodeuserEmail(String passcodeuserEmail) {
		this.passcodeuserEmail = passcodeuserEmail;
	}

	public String getTrScheduledId() {
		return trScheduledId;
	}

	public void setTrScheduledId(String trScheduledId) {
		this.trScheduledId = trScheduledId;
	}

	public String getTransactionEmailStatusToBanks() {
		return transactionEmailStatusToBanks;
	}

	public void setTransactionEmailStatusToBanks(String transactionEmailStatusToBanks) {
		this.transactionEmailStatusToBanks = transactionEmailStatusToBanks;
	}

	public void setEmailStatus(String emailStatus) {
		this.emailStatus = emailStatus;
	}

	/**
	 * @return the emailFlag
	 */
	public String getEmailStatus() {
		return emailStatus;
	}

	/**
	 * @param emailFlag the emailFlag to set
	 */
	public void setEmailFlag(String emailStatus) {
		this.emailStatus = emailStatus;
	}

	/**
	 * @return the customerUserName
	 */
	public String getCustomerUserName() {
		return customerUserName;
	}

	/**
	 * @param customerUserName the customerUserName to set
	 */
	public void setCustomerUserName(String customerUserName) {
		this.customerUserName = customerUserName;
	}

	/**
	 * @return the customerEmail
	 */
	public String getCustomerEmail() {
		return customerEmail;
	}

	/**
	 * @param customerEmail the customerEmail to set
	 */
	public void setCustomerEmail(String customerEmail) {
		this.customerEmail = customerEmail;
	}

	/**
	 * @return the quotationId
	 */
	public Integer getQuotationId() {
		return quotationId;
	}

	/**
	 * @param quotationId the quotationId to set
	 */
	public void setQuotationId(Integer quotationId) {
		this.quotationId = quotationId;
	}

	/**
	 * @return the emailEvent
	 */
	public String getEmailEvent() {
		return emailEvent;
	}

	/**
	 * @param emailEvent the emailEvent to set
	 */
	public void setEmailEvent(String emailEvent) {
		this.emailEvent = emailEvent;
	}

	/**
	 * @return the bankUserid
	 */
	public String getBankUserid() {
		return bankUserid;
	}

	/**
	 * @param bankUserid the bankUserid to set
	 */
	public void setBankUserid(String bankUserid) {
		this.bankUserid = bankUserid;
	}

	/**
	 * @return the bankUserName
	 */
	public String getBankUserName() {
		return bankUserName;
	}

	/**
	 * @param bankUserName the bankUserName to set
	 */
	public void setBankUserName(String bankUserName) {
		this.bankUserName = bankUserName;
	}

	/**
	 * @return the banksEmailID
	 */
	public String getBanksEmailID() {
		return banksEmailID;
	}

	/**
	 * @param banksEmailID the banksEmailID to set
	 */
	public void setBanksEmailID(String banksEmailID) {
		this.banksEmailID = banksEmailID;
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
	 * @return the scedulerid
	 */
	public int getScedulerid() {
		return scedulerid;
	}

	/**
	 * @param scedulerid the scedulerid to set
	 */
	public void setScedulerid(int scedulerid) {
		this.scedulerid = scedulerid;
	}

	/**
	 * @return the customerid
	 */
	public String getCustomerid() {
		return customerid;
	}

	/**
	 * @param customerid the customerid to set
	 */
	public void setCustomerid(String customerid) {
		this.customerid = customerid;
	}

	/**
	 * @return the transactionid
	 */
	public String getTransactionid() {
		return transactionid;
	}

	/**
	 * @param transactionid the transactionid to set
	 */
	public void setTransactionid(String transactionid) {
		this.transactionid = transactionid;
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

}
