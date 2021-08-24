package com.nimai.lc.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="temp_savings")
public class LifeTimeSaving {
	
	@Id
	@Column(name="id")
	private int id;
	
	@Column(name="savings")
	private String savings;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getSavings() {
		return savings;
	}

	public void setSavings(String savings) {
		this.savings = savings;
	}
	
	
	
	
	
	
	

}
