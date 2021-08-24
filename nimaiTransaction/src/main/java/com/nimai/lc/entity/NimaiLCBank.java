package com.nimai.lc.entity;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonFormat;

@Entity
@Table(name="TRANSBANK_TEMP")
public class NimaiLCBank {
	
	@Id
	@Column(name="BANK_ID") 
	private String bankId;
	
	@Column(name="BANK_NAME") 
	private String bankName;
	
	@Column(name="STATUS") 
	private String status;
	
	
	@Column(name="USER_ID") 
	private String userId;
	
	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	@Column(name="INSERTED_BY") 
	private String inserted_By;
	
	@JsonFormat(pattern = "yyyy-MM-dd")
	@Column(name="INSERTED_DATE") 
	private Date inserted_Date;
	
	@Column(name="MODIFIED_BY") 
    private String modified_By;
	
	@JsonFormat(pattern = "yyyy-MM-dd")
	@Column(name="MODIFIED_DATE") 
    private Date modified_Date;

	@Column(name="CONFIRMED_FLAG") 
    private Integer confirmedFlag;
	
	public String getBankId() {
		return bankId;
	}

	public void setBankId(String bankId) {
		this.bankId = bankId;
	}

	public String getBankName() {
		return bankName;
	}

	public void setBankName(String bankName) {
		this.bankName = bankName;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getInserted_By() {
		return inserted_By;
	}

	public void setInserted_By(String inserted_By) {
		this.inserted_By = inserted_By;
	}

	public Date getInserted_Date() {
		return inserted_Date;
	}

	public void setInserted_Date(Date inserted_Date) {
		this.inserted_Date = inserted_Date;
	}

	public String getModified_By() {
		return modified_By;
	}

	public void setModified_By(String modified_By) {
		this.modified_By = modified_By;
	}

	public Date getModified_Date() {
		return modified_Date;
	}

	public void setModified_Date(Date modified_Date) {
		this.modified_Date = modified_Date;
	}
	
    public Integer getConfirmedFlag() {
		return confirmedFlag;
	}

	public void setConfirmedFlag(Integer confirmedFlag) {
		this.confirmedFlag = confirmedFlag;
	}

	@JoinColumn(name = "TRANSACTION_ID",referencedColumnName = "TRANSACTION_ID" )
	@ManyToOne(cascade = CascadeType.ALL,fetch=FetchType.LAZY )
	@JsonBackReference
	private NimaiLC nimailcbank;

	public NimaiLC getNimailcbank() {
		return nimailcbank;
	}

	public void setNimailcbank(NimaiLC nimailcbank) {
		this.nimailcbank = nimailcbank;
	}
	
	

}
