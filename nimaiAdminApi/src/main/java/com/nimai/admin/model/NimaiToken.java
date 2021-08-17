package com.nimai.admin.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;


/**
 *
 * @author sahadeo.naik
 */
@Entity
@Table(name = "NIMAI_M_TOKEN")
public class NimaiToken implements Serializable 
{

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "user_id")
	private String userId;

	@Column(name = "token")
	private String token;

	@Column(name = "INSERTED_DATE")
	@Temporal(TemporalType.TIMESTAMP)
	private Date insertedDate;
	
	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public Date getInsertedDate() {
		return insertedDate;
	}

	public void setInsertedDate(Date insertedDate) {
		this.insertedDate = insertedDate;
	}

	
}
