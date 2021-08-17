package com.nimai.email.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table(name="monthly_cuorbac_report")
public class CustomerBankMonthlyReort {

//	@NotNull
//	@GeneratedValue(strategy = GenerationType.IDENTITY)
//	@Column(name="user_id")
//	private int reportId;
	
	@Id
	@Column(name="user_id")
	private String userId;
	
	@Column(name="transaction_Placed")
	private int transactionPlaced;
	
	@Column(name="quote_Received")
	private int quoteReceived;
	
	@Column(name="acceptedQuotes")
	private int acceptedQuotes;
	
	@Column(name="rejectdQuotes")
	private int rejectdQuotes;
	
	@Column(name="expired_Quotes")
	private int expired_Quotes;
	
	@Column(name="Cancelled_Transaction")
	private int Cancelled_Transaction;
	
	@Column(name="credit_Remaining")
	private int creditRemaining;
	
	@Column(name="subsidiary_Slot")
	private int subsidiarySlot;
		
	@Column(name="email_status")
	private String emailstatus;
	
	@Column(name="inserted_date")
	private Date insertedDate;

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public int getTransactionPlaced() {
		return transactionPlaced;
	}

	public void setTransactionPlaced(int transactionPlaced) {
		this.transactionPlaced = transactionPlaced;
	}

	public int getQuoteReceived() {
		return quoteReceived;
	}

	public void setQuoteReceived(int quoteReceived) {
		this.quoteReceived = quoteReceived;
	}

	public int getAcceptedQuotes() {
		return acceptedQuotes;
	}

	public void setAcceptedQuotes(int acceptedQuotes) {
		this.acceptedQuotes = acceptedQuotes;
	}

	public int getRejectdQuotes() {
		return rejectdQuotes;
	}

	public void setRejectdQuotes(int rejectdQuotes) {
		this.rejectdQuotes = rejectdQuotes;
	}

	public int getExpired_Quotes() {
		return expired_Quotes;
	}

	public void setExpired_Quotes(int expired_Quotes) {
		this.expired_Quotes = expired_Quotes;
	}

	public int getCancelled_Transaction() {
		return Cancelled_Transaction;
	}

	public void setCancelled_Transaction(int cancelled_Transaction) {
		Cancelled_Transaction = cancelled_Transaction;
	}

	public int getCreditRemaining() {
		return creditRemaining;
	}

	public void setCreditRemaining(int creditRemaining) {
		this.creditRemaining = creditRemaining;
	}

	public int getSubsidiarySlot() {
		return subsidiarySlot;
	}

	public void setSubsidiarySlot(int subsidiarySlot) {
		this.subsidiarySlot = subsidiarySlot;
	}

	public String getEmailstatus() {
		return emailstatus;
	}

	public void setEmailstatus(String emailstatus) {
		this.emailstatus = emailstatus;
	}

	public Date getInsertedDate() {
		return insertedDate;
	}

	public void setInsertedDate(Date insertedDate) {
		this.insertedDate = insertedDate;
	}

	@Override
	public String toString() {
		return "CustomerBankMonthlyReort [userId=" + userId + ", transactionPlaced=" + transactionPlaced
				+ ", quoteReceived=" + quoteReceived + ", acceptedQuotes=" + acceptedQuotes + ", rejectdQuotes="
				+ rejectdQuotes + ", expired_Quotes=" + expired_Quotes + ", Cancelled_Transaction="
				+ Cancelled_Transaction + ", creditRemaining=" + creditRemaining + ", subsidiarySlot=" + subsidiarySlot
				+ ", emailstatus=" + emailstatus + ", insertedDate=" + insertedDate + "]";
	}

	
	
	
	
}
