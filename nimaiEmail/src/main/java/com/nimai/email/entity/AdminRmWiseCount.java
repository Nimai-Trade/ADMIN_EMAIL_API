package com.nimai.email.entity;

import java.util.Date;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

@Entity
@Table(name="eod_rm_wise_count_report")
public class AdminRmWiseCount {

	@Id
	@Basic(optional = false)
	@NotNull
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="rm_wise_report_id")
	private int reportId;
	
	@Column(name="EMP_FIRST_NAME")
	private String employeeName;
	
	@Column(name="bankasunderwriter")
	private int bankasunderwriter;
	
	@Column(name="bankcustomer")
	private int bankcustomer;
	
	@Column(name="customer")
	private int customer;
	
	@Column(name="referrer")
	private int referrer;
	
	@Column(name="addedyesterday")
	private int addedyesterday;
	
	@Column(name="email_status")
	private String emailStatus;

	@Column(name="inserted_date")
	@Temporal(TemporalType.DATE)
	private Date inserted_date;


	public int getReportId() {
		return reportId;
	}


	public void setReportId(int reportId) {
		this.reportId = reportId;
	}


	public String getEmployeeName() {
		return employeeName;
	}


	public void setEmployeeName(String employeeName) {
		this.employeeName = employeeName;
	}


	public int getBankasunderwriter() {
		return bankasunderwriter;
	}


	public void setBankasunderwriter(int bankasunderwriter) {
		this.bankasunderwriter = bankasunderwriter;
	}


	public int getBankcustomer() {
		return bankcustomer;
	}


	public void setBankcustomer(int bankcustomer) {
		this.bankcustomer = bankcustomer;
	}


	public int getCustomer() {
		return customer;
	}


	public void setCustomer(int customer) {
		this.customer = customer;
	}


	public int getReferrer() {
		return referrer;
	}


	public void setReferrer(int referrer) {
		this.referrer = referrer;
	}


	public int getAddedyesterday() {
		return addedyesterday;
	}


	public void setAddedyesterday(int addedyesterday) {
		this.addedyesterday = addedyesterday;
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


	@Override
	public String toString() {
		return "AdminRmWiseCount [reportId=" + reportId + ", employeeName=" + employeeName + ", bankasunderwriter="
				+ bankasunderwriter + ", bankcustomer=" + bankcustomer + ", customer=" + customer + ", referrer="
				+ referrer + ", addedyesterday=" + addedyesterday + ", emailStatus=" + emailStatus + ", inserted_date="
				+ inserted_date + "]";
	}
	
	
	
	
}
