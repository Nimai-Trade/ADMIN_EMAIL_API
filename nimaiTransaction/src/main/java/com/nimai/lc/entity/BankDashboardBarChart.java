package com.nimai.lc.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="temp_bank_country_wise_stat")
public class BankDashboardBarChart {
	
	
	@Id
	@Column(name="id")
	private int id;
	
	@Column(name="country")
	private String country;
	
	@Column(name="transactionavailable")
	private int transactionavailable;
	
	@Column(name="transactionquote")
	private int transactionquote;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public int getTransactionavailable() {
		return transactionavailable;
	}

	public void setTransactionavailable(int transactionavailable) {
		this.transactionavailable = transactionavailable;
	}

	public int getTransactionquote() {
		return transactionquote;
	}

	public void setTransactionquote(int transactionquote) {
		this.transactionquote = transactionquote;
	}
	
	
	

}
