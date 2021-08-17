package com.nimai.email.entity;

import javax.persistence.Table;

import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

import java.math.BigInteger;
import java.util.Date;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
@Table(name="eod_admin_management_report")
public class AdminDailyCountDetailsBean {

	@Id
	@Basic(optional = false)
	@NotNull
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="report_id")
	private int reportId;
	
	@Column(name="yesterday_totalcorporate")
	private int yesterdayTotalCorporate;
	
	@Column(name="yesterday_totalunderwriterbank",insertable = false, updatable = false)
	private int yesterdayTotalBAU;
	
	@Column(name="yesterday_totalcustomerbank")
	private int yesterdayTotalBAC;
	
	@Column(name="yesterday_numberOfTransaction")
	private int yesterdayTotalTransaction;
	
	@Column(name="yesterday_totalamount")
	private BigInteger yesterdayTotalAmount;
	
	@Column(name="lifetime_totalcorporate")
	private int lifetimeTotalCorporate;
	
	@Column(name="lifetime_totalunderwriterbank")
	private int lifetimeTotalBAU;
	
	@Column(name="yesterday_totalunderwriterbank")
	private int lifetimeTotalBAC;
	
	@Column(name="lifetime_numberOfTransaction")
	private int lifetimeTotalTransaction;
	
	@Column(name="lifetime_totalamount")
	private BigInteger lifetimeTotalAmount;

	@Column(name="email_status")
	private String emailStatus;

	@Column(name="inserted_date")
	@Temporal(TemporalType.DATE)
	private Date inserted_date;
	
	public int getYesterdayTotalCorporate() {
		return yesterdayTotalCorporate;
	}

	public void setYesterdayTotalCorporate(int yesterdayTotalCorporate) {
		this.yesterdayTotalCorporate = yesterdayTotalCorporate;
	}

	public int getYesterdayTotalBAU() {
		return yesterdayTotalBAU;
	}

	public void setYesterdayTotalBAU(int yesterdayTotalBAU) {
		this.yesterdayTotalBAU = yesterdayTotalBAU;
	}

	public int getYesterdayTotalBAC() {
		return yesterdayTotalBAC;
	}

	public void setYesterdayTotalBAC(int yesterdayTotalBAC) {
		this.yesterdayTotalBAC = yesterdayTotalBAC;
	}

	public int getYesterdayTotalTransaction() {
		return yesterdayTotalTransaction;
	}

	public void setYesterdayTotalTransaction(int yesterdayTotalTransaction) {
		this.yesterdayTotalTransaction = yesterdayTotalTransaction;
	}

	public BigInteger getYesterdayTotalAmount() {
		return yesterdayTotalAmount;
	}

	public void setYesterdayTotalAmount(BigInteger yesterdayTotalAmount) {
		this.yesterdayTotalAmount = yesterdayTotalAmount;
	}

	public int getReportId() {
		return reportId;
	}

	public void setReportId(int reportId) {
		this.reportId = reportId;
	}

	public String getEmailStatus() {
		return emailStatus;
	}

	public void setEmailStatus(String emailStatus) {
		this.emailStatus = emailStatus;
	}

	public Date getInserted_date() {
		return inserted_date;
	}

	public void setInserted_date(Date inserted_date) {
		this.inserted_date = inserted_date;
	}

	public int getLifetimeTotalCorporate() {
		return lifetimeTotalCorporate;
	}

	public void setLifetimeTotalCorporate(int lifetimeTotalCorporate) {
		this.lifetimeTotalCorporate = lifetimeTotalCorporate;
	}

	public int getLifetimeTotalBAU() {
		return lifetimeTotalBAU;
	}

	public void setLifetimeTotalBAU(int lifetimeTotalBAU) {
		this.lifetimeTotalBAU = lifetimeTotalBAU;
	}

	public int getLifetimeTotalBAC() {
		return lifetimeTotalBAC;
	}

	public void setLifetimeTotalBAC(int lifetimeTotalBAC) {
		this.lifetimeTotalBAC = lifetimeTotalBAC;
	}

	public int getLifetimeTotalTransaction() {
		return lifetimeTotalTransaction;
	}

	public void setLifetimeTotalTransaction(int lifetimeTotalTransaction) {
		this.lifetimeTotalTransaction = lifetimeTotalTransaction;
	}

	public BigInteger getLifetimeTotalAmount() {
		return lifetimeTotalAmount;
	}

	public void setLifetimeTotalAmount(BigInteger lifetimeTotalAmount) {
		this.lifetimeTotalAmount = lifetimeTotalAmount;
	}

	@Override
	public String toString() {
		return "AdminDailyCountDetailsBean [reportId=" + reportId + ", yesterdayTotalCorporate="
				+ yesterdayTotalCorporate + ", yesterdayTotalBAU=" + yesterdayTotalBAU + ", yesterdayTotalBAC="
				+ yesterdayTotalBAC + ", yesterdayTotalTransaction=" + yesterdayTotalTransaction
				+ ", yesterdayTotalAmount=" + yesterdayTotalAmount + ", lifetimeTotalCorporate="
				+ lifetimeTotalCorporate + ", lifetimeTotalBAU=" + lifetimeTotalBAU + ", lifetimeTotalBAC="
				+ lifetimeTotalBAC + ", lifetimeTotalTransaction=" + lifetimeTotalTransaction + ", lifetimeTotalAmount="
				+ lifetimeTotalAmount + ", emailStatus=" + emailStatus + ", inserted_date=" + inserted_date + "]";
	}
	
	
	
}
