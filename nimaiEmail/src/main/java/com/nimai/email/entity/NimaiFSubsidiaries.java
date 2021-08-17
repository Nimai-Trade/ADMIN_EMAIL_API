package com.nimai.email.entity;

import java.util.Date;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "nimai_f_subsidiaries")
public class NimaiFSubsidiaries {

	@Id
	@Basic(optional = false)
	@NotNull
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "subsidiary_id")
	private int subsidiaryId;

	@Column(name = "Sub_User_Id")
	private String subUserId;

	@Column(name = "subsidiary_email")
	private String subsidiaryEmail;

	@Column(name = "token_expiryDate")
	private Date tokenExpiryDate;

	@Column(name = "inserted_date")
	private Date insertedDate;

	@JoinColumn(name = "USERID", referencedColumnName = "USERID")
	@ManyToOne(optional = false)
	private NimaiClient userid;

	@Column(name = "subsidiary_token")
	private String subsidiaryToken;

	public int getSubsidiaryId() {
		return subsidiaryId;
	}

	public void setSubsidiaryId(int subsidiaryId) {
		this.subsidiaryId = subsidiaryId;
	}

	public String getSubUserId() {
		return subUserId;
	}

	public void setSubUserId(String subUserId) {
		this.subUserId = subUserId;
	}

	public Date getInsertedDate() {
		return insertedDate;
	}

	public void setInsertedDate(Date insertedDate) {
		this.insertedDate = insertedDate;
	}

	public String getSubsidiaryToken() {
		return subsidiaryToken;
	}

	public void setSubsidiaryToken(String subsidiaryToken) {
		this.subsidiaryToken = subsidiaryToken;
	}

	public Date getTokenExpiryDate() {
		return tokenExpiryDate;
	}

	public void setTokenExpiryDate(Date tokenExpiryDate) {
		this.tokenExpiryDate = tokenExpiryDate;
	}

	public NimaiClient getUserid() {
		return userid;
	}

	public void setUserid(NimaiClient userid) {
		this.userid = userid;
	}

	public String getSubsidiaryEmail() {
		return subsidiaryEmail;
	}

	public void setSubsidiaryEmail(String subsidiaryEmail) {
		this.subsidiaryEmail = subsidiaryEmail;
	}

}
