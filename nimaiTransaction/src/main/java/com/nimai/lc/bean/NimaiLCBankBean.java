package com.nimai.lc.bean;

import java.util.Date;

public class NimaiLCBankBean {
	
	private String bankId;
	private String bankName;
	private String userId;
	private String status;
	private String inserted_By;
	private Date inserted_Date;
    private String modified_By;
    private Date modified_Date;
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
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public Integer getConfirmedFlag() {
		return confirmedFlag;
	}
	public void setConfirmedFlag(Integer confirmedFlag) {
		this.confirmedFlag = confirmedFlag;
	}
	

}
