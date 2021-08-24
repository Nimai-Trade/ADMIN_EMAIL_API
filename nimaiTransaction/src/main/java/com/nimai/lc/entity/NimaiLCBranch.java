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
@Entity
@Table(name="TRANSBRANCH_TEMP")
public class NimaiLCBranch {
	@Id
	@Column(name="BRANCH_ID")
	private String branchId;
	
	@Column(name="BANK_ID")
	private String bankId;
	
	@Column(name="BRANCH_NAME")
	private String branchName;
	
	@Column(name="USER_ID")
	private String userId;
	
	@Column(name="STATUS")
	private String status;
	
	@Column(name="INSERTED_BY")
	private String inserted_By;
	
	@Column(name="INSERTED_DATE")
	private Date inserted_Date;
	
	@Column(name="MODIFIED_BY")
    private String modified_By;
	
	@Column(name="MODIFIED_DATE")
    private Date modified_Date;

	public String getBranchId() {
		return branchId;
	}

	public void setBranchId(String branchId) {
		this.branchId = branchId;
	}

	public String getBankId() {
		return bankId;
	}

	public void setBankId(String bankId) {
		this.bankId = bankId;
	}

	public String getBranchName() {
		return branchName;
	}

	public void setBranchName(String branchName) {
		this.branchName = branchName;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
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
	
	@JoinColumn(name = "TRANSACTION_ID",referencedColumnName = "TRANSACTION_ID" )
	@ManyToOne(cascade = CascadeType.ALL,fetch=FetchType.LAZY )
	@JsonBackReference
	private NimaiLC nimailcbranch;

	public NimaiLC getNimailcbranch() {
		return nimailcbranch;
	}

	public void setNimailcbranch(NimaiLC nimailcbranch) {
		this.nimailcbranch = nimailcbranch;
	}
	
	

}
