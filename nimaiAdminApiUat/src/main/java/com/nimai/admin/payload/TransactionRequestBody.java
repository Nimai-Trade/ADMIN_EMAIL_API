package com.nimai.admin.payload;

public class TransactionRequestBody {
	private String countryName;
	private String transactionId;
	private String status;
	private String userId;
	private String makerComment;
	private String checkerComment;
	private String customerType;
	public String getCountryName() {
		return countryName;
	}
	public void setCountryName(String countryName) {
		this.countryName = countryName;
	}
	public String getTransactionId() {
		return transactionId;
	}
	public void setTransactionId(String transactionId) {
		this.transactionId = transactionId;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getMakerComment() {
		return makerComment;
	}
	public void setMakerComment(String makerComment) {
		this.makerComment = makerComment;
	}
	public String getCheckerComment() {
		return checkerComment;
	}
	public void setCheckerComment(String checkerComment) {
		this.checkerComment = checkerComment;
	}
	public String getCustomerType() {
		return customerType;
	}
	public void setCustomerType(String customerType) {
		this.customerType = customerType;
	}
	
	
	
	
	
}
