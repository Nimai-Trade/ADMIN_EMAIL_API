package com.nimai.lc.bean;

import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

public class CustomerTransactionBean implements Serializable
{
	private static final long serialVersionUID = 1L;
	private String transactionId;
    private String userId; 
    private String requirementType;
    private String lCIssuanceBank;
    private Double lCValue;
    private String lCCurrency;
    private String goodsType;
    private String applicantName;
	private String beneName;
	private Date validity;
	@JsonFormat(pattern = "yyyy-MM-dd")
	private Date insertedDate;
	private String transactionStatus;
	private Integer quotationReceived;
	@JsonFormat(pattern = "yyyy-MM-dd")
	private Date acceptedOn;
	@JsonFormat(pattern = "yyyy-MM-dd")
	private Date rejectedOn;
	private String statusReason;
	
	
	
	public String getStatusReason() {
		return statusReason;
	}
	public void setStatusReason(String statusReason) {
		this.statusReason = statusReason;
	}
	public String getTransactionId() {
		return transactionId;
	}
	public void setTransactionId(String transactionId) {
		this.transactionId = transactionId;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getRequirementType() {
		return requirementType;
	}
	public void setRequirementType(String requirementType) {
		this.requirementType = requirementType;
	}
	public String getlCIssuanceBank() {
		return lCIssuanceBank;
	}
	public void setlCIssuanceBank(String lCIssuanceBank) {
		this.lCIssuanceBank = lCIssuanceBank;
	}
	public Double getlCValue() {
		return lCValue;
	}
	public void setlCValue(Double lCValue) {
		this.lCValue = lCValue;
	}
	public String getGoodsType() {
		return goodsType;
	}
	public void setGoodsType(String goodsType) {
		this.goodsType = goodsType;
	}
	public String getApplicantName() {
		return applicantName;
	}
	public void setApplicantName(String applicantName) {
		this.applicantName = applicantName;
	}
	public String getBeneName() {
		return beneName;
	}
	public void setBeneName(String beneName) {
		this.beneName = beneName;
	}
	public Date getValidity() {
		return validity;
	}
	public void setValidity(Date validity) {
		this.validity = validity;
	}
	public Date getInsertedDate() {
		return insertedDate;
	}
	public void setInsertedDate(Date insertedDate) {
		this.insertedDate = insertedDate;
	}
	public String getTransactionStatus() {
		return transactionStatus;
	}
	public void setTransactionStatus(String transactionStatus) {
		this.transactionStatus = transactionStatus;
	}
	public Integer getQuotationReceived() {
		return quotationReceived;
	}
	public void setQuotationReceived(Integer quotationReceived) {
		this.quotationReceived = quotationReceived;
	}
	public Date getAcceptedOn() {
		return acceptedOn;
	}
	public void setAcceptedOn(Date acceptedOn) {
		this.acceptedOn = acceptedOn;
	}
	public Date getRejectedOn() {
		return rejectedOn;
	}
	public void setRejectedOn(Date rejectedOn) {
		this.rejectedOn = rejectedOn;
	}
	public String getlCCurrency() {
		return lCCurrency;
	}
	public void setlCCurrency(String lCCurrency) {
		this.lCCurrency = lCCurrency;
	}
	
	
}
