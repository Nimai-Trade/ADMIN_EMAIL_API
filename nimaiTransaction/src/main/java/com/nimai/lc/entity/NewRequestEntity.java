package com.nimai.lc.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedStoredProcedureQuery;
import javax.persistence.ParameterMode;
import javax.persistence.StoredProcedureParameter;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name="transaction_for_bank")
@NamedStoredProcedureQuery(name = "get_transaction_for_bank", procedureName = "get_transaction_for_bank", parameters = {
		@StoredProcedureParameter(mode = ParameterMode.IN, name = "user_id", type = String.class)})
public class NewRequestEntity 
{
	@Id 
	@Column(name="TRANSACTION_ID") 
	private String transactionId;
	
	@Column(name="USER_ID")
	private String userId; 
	
	@Column(name="REQUIREMENT_TYPE")
	private String requirementType;
	
	@Column(name="LC_ISSUANCE_BANK")
	private String lCIssuanceBank;
	
	@Column(name="LC_VALUE")
	private Double lCValue;
	
	@Column(name="LC_CURRENCY")
	private String lCCurrency;
	
	@Column(name="CONFIRMATION_PERIOD")
	private String confirmationPeriod;
	
	@Column(name="DISCOUNTING_PERIOD")
	private String discountingPeriod;
	
	@Column(name="REFINANCING_PERIOD")
	private String refinancingPeriod;
	
	@Column(name="goods_type")
	private String goodsType;
	
	@Column(name="APPLICANT_NAME")
	private String applicantName;
	
	@Column(name="BENE_NAME")
	private String beneName;
	
	@Column(name="VALIDITY")
	private Date validity;
	
	@Column(name="INSERTED_DATE")
	@Temporal(value=TemporalType.TIMESTAMP)
	private Date insertedDate;

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
	
	public String getlCCurrency() {
		return lCCurrency;
	}

	public void setlCCurrency(String lCCurrency) {
		this.lCCurrency = lCCurrency;
	}

	public String getConfirmationPeriod() {
		return confirmationPeriod;
	}

	public void setConfirmationPeriod(String confirmationPeriod) {
		this.confirmationPeriod = confirmationPeriod;
	}

	public String getDiscountingPeriod() {
		return discountingPeriod;
	}

	public void setDiscountingPeriod(String discountingPeriod) {
		this.discountingPeriod = discountingPeriod;
	}

	public String getRefinancingPeriod() {
		return refinancingPeriod;
	}

	public void setRefinancingPeriod(String refinancingPeriod) {
		this.refinancingPeriod = refinancingPeriod;
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

	
}