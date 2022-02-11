package com.nimai.splan.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name="nimai_customer_subscription_amount")
public class NimaiCustomerSubscriptionGrandAmount implements Serializable {
	
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id")
	private Integer id;
	
	@Column(name = "user_id")
	private String userId;
	
	@Column(name="grand_amount")
	private Double grandAmount;
	
	@Column(name = "discount_applied")
	private String discountApplied;
	
	@Column(name = "vas_applied")
	private String vasApplied;
	
	@Column(name = "inserted_date")
	@Temporal(value=TemporalType.TIMESTAMP)
	private Date insertedDate;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public Double getGrandAmount() {
		return grandAmount;
	}

	public void setGrandAmount(Double grandAmount) {
		this.grandAmount = grandAmount;
	}
	
	public String getDiscountApplied() {
		return discountApplied;
	}

	public void setDiscountApplied(String discountApplied) {
		this.discountApplied = discountApplied;
	}

	
	public String getVasApplied() {
		return vasApplied;
	}

	public void setVasApplied(String vasApplied) {
		this.vasApplied = vasApplied;
	}

	public Date getInsertedDate() {
		return insertedDate;
	}

	public void setInsertedDate(Date insertedDate) {
		this.insertedDate = insertedDate;
	}
	
	
}	