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
@Table(name="eod_customer_daily_report")
public class EodCustomerDailyReort {

	@NotNull
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="cust_report_id")
	private int reportId;
	
	@Id
	@Column(name="transaction_id")
	private String transactionId;
	 
	@Column(name="user_Id")
	private String userId;
	
	@Column(name="Amount")
	private Double amount;
	
	@Column(name="Currency")
	private String curreny;
	
	@Column(name="Total_Quotes")
	private int totalQuotes;
	
	@Column(name="Email_status")
	private String emailStatus;
	
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

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public Double getAmount() {
		return amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}

	public String getCurreny() {
		return curreny;
	}

	public void setCurreny(String curreny) {
		this.curreny = curreny;
	}

	public int getTotalQuotes() {
		return totalQuotes;
	}

	public void setTotalQuotes(int totalQuotes) {
		this.totalQuotes = totalQuotes;
	}

	public String getEmailStatus() {
		return emailStatus;
	}

	public void setEmailStatus(String emailStatus) {
		this.emailStatus = emailStatus;
	}

	public Date getInsertedDate() {
		return insertedDate;
	}

	public void setInsertedDate(Date insertedDate) {
		this.insertedDate = insertedDate;
	}

	@Override
	public String toString() {
		return "EodCustomerDailyReort [reportId=" + reportId + ", transactionId=" + transactionId + ", userId=" + userId
				+ ", amount=" + amount + ", curreny=" + curreny + ", totalQuotes=" + totalQuotes + ", emailStatus="
				+ emailStatus + ", insertedDate=" + insertedDate + "]";
	}
	
	
	
	
	
}
