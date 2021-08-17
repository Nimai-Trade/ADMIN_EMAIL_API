package com.nimai.admin.payload;

import java.util.Date;

public class QuotationListResponse {

	private Integer quotationId;
	private String transactionId;
	private String bankUserid;
	private Float totalQuoteValue;
	private Date validityDate;
	private String quotationStatus;
	private String currency;
	private String userId;
	private String mobile;
	private String email;
	private String beneficiary;
	private String country;
	private Date insertedDate;
	private String ib;
	private String amount;
	private String ccy;
	private String quoteCcy;
	private String requirement;
	private String trxnStatus;

	public QuotationListResponse() {
		super();
	}

	public QuotationListResponse(Integer quotationId, String transactionId, String bankUserid, Float totalQuoteValue,
			Date validityDate, String quotationStatus, String currency) {
//		super();
		this.quotationId = quotationId;
		this.transactionId = transactionId;
		this.bankUserid = bankUserid;
		this.totalQuoteValue = totalQuoteValue;
		this.validityDate = validityDate;
		this.quotationStatus = quotationStatus;
		this.currency = currency;
	}

	public Integer getQuotationId() {
		return quotationId;
	}

	public void setQuotationId(Integer quotationId) {
		this.quotationId = quotationId;
	}

	public String getTransactionId() {
		return transactionId;
	}

	public void setTransactionId(String transactionId) {
		this.transactionId = transactionId;
	}

	public String getBankUserid() {
		return bankUserid;
	}

	public void setBankUserid(String bankUserid) {
		this.bankUserid = bankUserid;
	}

	public Float getTotalQuoteValue() {
		return totalQuoteValue;
	}

	public void setTotalQuoteValue(Float totalQuoteValue) {
		this.totalQuoteValue = totalQuoteValue;
	}

	public Date getValidityDate() {
		return validityDate;
	}

	public void setValidityDate(Date validityDate) {
		this.validityDate = validityDate;
	}

	public String getQuotationStatus() {
		return quotationStatus;
	}

	public void setQuotationStatus(String quotationStatus) {
		this.quotationStatus = quotationStatus;
	}

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getBeneficiary() {
		return beneficiary;
	}

	public void setBeneficiary(String beneficiary) {
		this.beneficiary = beneficiary;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public Date getInsertedDate() {
		return insertedDate;
	}

	public void setInsertedDate(Date insertedDate) {
		this.insertedDate = insertedDate;
	}

	public String getIb() {
		return ib;
	}

	public void setIb(String ib) {
		this.ib = ib;
	}

	public String getAmount() {
		return amount;
	}

	public void setAmount(String amount) {
		this.amount = amount;
	}

	public String getCcy() {
		return ccy;
	}

	public void setCcy(String ccy) {
		this.ccy = ccy;
	}

	public String getQuoteCcy() {
		return quoteCcy;
	}

	public void setQuoteCcy(String quoteCcy) {
		this.quoteCcy = quoteCcy;
	}

	public String getRequirement() {
		return requirement;
	}

	public void setRequirement(String requirement) {
		this.requirement = requirement;
	}

	public String getTrxnStatus() {
		return trxnStatus;
	}

	public void setTrxnStatus(String trxnStatus) {
		this.trxnStatus = trxnStatus;
	}

}
