package com.nimai.admin.model;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.DynamicUpdate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity
@Table(name = "customer_preferred_banks")
@EntityListeners(AuditingEntityListener.class)
@DynamicUpdate
public class PreferredBankModel 
{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Basic(optional = false)
	@Column(name = "ID")
	private int id;
	
	@Column(name = "CUST_USERID")
	private String custUserId;
	
	@Column(name = "BANK_USERID")
	private String bankUserId;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getCustUserId() {
		return custUserId;
	}

	public void setCustUserId(String custUserId) {
		this.custUserId = custUserId;
	}

	public String getBankUserId() {
		return bankUserId;
	}

	public void setBankUserId(String bankUserId) {
		this.bankUserId = bankUserId;
	}
	
	
}
