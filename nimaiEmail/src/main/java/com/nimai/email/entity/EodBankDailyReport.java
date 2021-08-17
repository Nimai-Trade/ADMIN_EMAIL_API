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
@Table(name="eod_bank_daily_report")
public class EodBankDailyReport {

	@Id
	@NotNull
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="bank_Report_id")
	private int reportId;
	

	@Column(name="transaction_id")
	private String transactionId;
	

	@Column(name="bank_user_Id")
	private String bankUserId;

	@Column(name="lc_value")
	private double lcValue;
	

	@Column(name="total_quote_value")
	private float quoteValue;
	
@Column(name="currency")
	private String currency;
	
	@Column(name="email_status")
	private String emailstatus;
	
	@Column(name="inserted_date")
	private Date insertedDate;

	public int getReportId() {
		return reportId;
	}

	public void setReportId(int reportId) {
		this.reportId = reportId;
	}

	public String getTransactionId() {
		return transactionId;
	}

	public void setTransactionId(String transactionId) {
		this.transactionId = transactionId;
	}

	public String getBankUserId() {
		return bankUserId;
	}

	public void setBankUserId(String bankUserId) {
		this.bankUserId = bankUserId;
	}

	public double getLcValue() {
		return lcValue;
	}

	public void setLcValue(double lcValue) {
		this.lcValue = lcValue;
	}

	public float getQuoteValue() {
		return quoteValue;
	}

	public void setQuoteValue(float quoteValue) {
		this.quoteValue = quoteValue;
	}

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public Date getInsertedDate() {
		return insertedDate;
	}

	public void setInsertedDate(Date insertedDate) {
		this.insertedDate = insertedDate;
	}

	public String getEmailstatus() {
		return emailstatus;
	}

	public void setEmailstatus(String emailstatus) {
		this.emailstatus = emailstatus;
	}

	@Override
	public String toString() {
		return "EodBankDailyReport [reportId=" + reportId + ", transactionId=" + transactionId + ", bankUserId="
				+ bankUserId + ", lcValue=" + lcValue + ", quoteValue=" + quoteValue + ", currency=" + currency
				+ ", emailstatus=" + emailstatus + ", insertedDate=" + insertedDate + "]";
	}



	
}
