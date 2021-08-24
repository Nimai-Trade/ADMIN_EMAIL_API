package com.nimai.lc.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="temp_latest_accepted_transaction_bank")
public class BankLatestAcceptedTransaction {
	@Id
	@Column(name="id")
	private int id;
	
	@Column(name="user_id")
	private String userId;
	
	@Column(name="date")
	private Date date;
	
	@Column(name="trxn_id")
	private String trxn_id;
	
	@Column(name="requirement")
	private String requirement;
	
	@Column(name="country")
	private String country;
	
	@Column(name="amount")
	private int amount;
	
	@Column(name="Ccy")
	private String Ccy;
	
	@Column(name="trxn_status")
	private String trxn_status;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	
	
	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public String getTrxn_id() {
		return trxn_id;
	}

	public void setTrxn_id(String trxn_id) {
		this.trxn_id = trxn_id;
	}

	public String getRequirement() {
		return requirement;
	}

	public void setRequirement(String requirement) {
		this.requirement = requirement;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public int getAmount() {
		return amount;
	}

	public void setAmount(int amount) {
		this.amount = amount;
	}

	public String getCcy() {
		return Ccy;
	}

	public void setCcy(String ccy) {
		Ccy = ccy;
	}

	public String getTrxn_status() {
		return trxn_status;
	}

	public void setTrxn_status(String trxn_status) {
		this.trxn_status = trxn_status;
	}
	
	
	
	
	
	

}
