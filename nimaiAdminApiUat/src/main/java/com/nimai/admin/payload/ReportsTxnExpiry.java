package com.nimai.admin.payload;

import java.util.Date;

public class ReportsTxnExpiry {

	private String trxn_ID;
	private Date date_Time;
	private String applicant;
	private String a_Country;
	private String beneficiary;
	private String b_country;
	private String requirement;
	private Long amount;
	private String ccy;
	private String customer;
	private String validity;
	private String expired_in;
	private String rm;

	public ReportsTxnExpiry() {
		super();
		// TODO Auto-generated constructor stub
	}

	public String getTrxn_ID() {
		return trxn_ID;
	}

	public void setTrxn_ID(String trxn_ID) {
		this.trxn_ID = trxn_ID;
	}

	public Date getDate_Time() {
		return date_Time;
	}

	public void setDate_Time(Date date_Time) {
		this.date_Time = date_Time;
	}

	public String getApplicant() {
		return applicant;
	}

	public void setApplicant(String applicant) {
		this.applicant = applicant;
	}

	public String getA_Country() {
		return a_Country;
	}

	public void setA_Country(String a_Country) {
		this.a_Country = a_Country;
	}

	public String getBeneficiary() {
		return beneficiary;
	}

	public void setBeneficiary(String beneficiary) {
		this.beneficiary = beneficiary;
	}

	public String getB_country() {
		return b_country;
	}

	public void setB_country(String b_country) {
		this.b_country = b_country;
	}

	public String getRequirement() {
		return requirement;
	}

	public void setRequirement(String requirement) {
		this.requirement = requirement;
	}

	public Long getAmount() {
		return amount;
	}

	public void setAmount(Long amount) {
		this.amount = amount;
	}

	public String getCcy() {
		return ccy;
	}

	public void setCcy(String ccy) {
		this.ccy = ccy;
	}

	public String getCustomer() {
		return customer;
	}

	public void setCustomer(String customer) {
		this.customer = customer;
	}

	public String getValidity() {
		return validity;
	}

	public void setValidity(String validity) {
		this.validity = validity;
	}

	public String getExpired_in() {
		return expired_in;
	}

	public void setExpired_in(String expired_in) {
		this.expired_in = expired_in;
	}

	public String getRm() {
		return rm;
	}

	public void setRm(String rm) {
		this.rm = rm;
	}

}
