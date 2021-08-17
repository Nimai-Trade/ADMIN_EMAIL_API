package com.nimai.email.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="nimai_m_transaction_savings")
public class TransactionSaving 
{
	@Id
	@Column(name="id") 
	private int id;
	
	@Column(name="userid") 
	private String userid;
	
	@Column(name="transaction_id") 
	private String transactionid;
	
	@Column(name="savings") 
	private Double savings;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getUserid() {
		return userid;
	}

	public void setUserid(String userid) {
		this.userid = userid;
	}

	public String getTransactionid() {
		return transactionid;
	}

	public void setTransactionid(String transactionid) {
		this.transactionid = transactionid;
	}

	public Double getSavings() {
		return savings;
	}

	public void setSavings(Double savings) {
		this.savings = savings;
	}
	
	
}