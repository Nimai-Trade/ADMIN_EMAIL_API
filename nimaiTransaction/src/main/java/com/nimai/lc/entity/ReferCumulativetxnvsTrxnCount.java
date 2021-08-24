package com.nimai.lc.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="temp_cumulative_txn_amt_refer")
public class ReferCumulativetxnvsTrxnCount {
		
	
	@Id
	@Column(name="id")
	private int id;
	
	@Column(name="Month")
	private String Month;
	
	@Column(name="Count")
	private int Count;
	
	@Column(name="transaction_Amount")
	private int transaction_Amount;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getMonth() {
		return Month;
	}

	public void setMonth(String month) {
		Month = month;
	}

	public int getCount() {
		return Count;
	}

	public void setCount(int count) {
		Count = count;
	}

	public int getTransaction_Amount() {
		return transaction_Amount;
	}

	public void setTransaction_Amount(int transaction_Amount) {
		this.transaction_Amount = transaction_Amount;
	}

	

}
