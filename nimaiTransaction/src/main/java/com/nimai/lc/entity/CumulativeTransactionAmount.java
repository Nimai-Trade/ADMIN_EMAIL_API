package com.nimai.lc.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="temp_cumulative_txn_amt")
public class CumulativeTransactionAmount {
	
     @Id
	@Column(name="id")
	private int id;
	
	@Column(name="Month")
	private String month;
	
	
	@Column(name="Count")
	private Integer count; 
	
	@Column(name="transaction_Amount")
	private Integer TransactionAmount;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getMonth() {
		return month;
	}

	public void setMonth(String month) {
		this.month = month;
	}

	public Integer getCount() {
		return count;
	}

	public void setCount(Integer count) {
		this.count = count;
	}

	public Integer getTransactionAmount() {
		return TransactionAmount;
	}

	public void setTransactionAmount(Integer transactionAmount) {
		TransactionAmount = transactionAmount;
	}

	

	


}
