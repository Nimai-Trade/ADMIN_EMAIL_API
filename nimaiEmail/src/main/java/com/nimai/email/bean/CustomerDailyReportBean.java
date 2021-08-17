package com.nimai.email.bean;

public class CustomerDailyReportBean {

	private String lcValue;
	private String transactionId;
	private String lcCurrency;
	private String userId;
	private String totalQuotes;
	public String getLcValue() {
		return lcValue;
	}
	public void setLcValue(String lcValue) {
		this.lcValue = lcValue;
	}
	public String getTransactionId() {
		return transactionId;
	}
	public void setTransactionId(String transactionId) {
		this.transactionId = transactionId;
	}
	public String getLcCurrency() {
		return lcCurrency;
	}
	public void setLcCurrency(String lcCurrency) {
		this.lcCurrency = lcCurrency;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getTotalQuotes() {
		return totalQuotes;
	}
	public void setTotalQuotes(String totalQuotes) {
		this.totalQuotes = totalQuotes;
	}
	@Override
	public String toString() {
		return "CustomerDailyReportBean [lcValue=" + lcValue + ", transactionId=" + transactionId + ", lcCurrency="
				+ lcCurrency + ", userId=" + userId + ", totalQuotes=" + totalQuotes + "]";
	}
	
	
	
	
}
