package com.nimai.admin.payload;

import com.fasterxml.jackson.annotation.JsonFormat;

public class SearchRequest {
	private int page;
	private int size;
	private String direction;
	private String sortBy;
	private String userId;
	private String emailId;
	private String mobileNo;
	private String companyName;
	private String bankName;
	private String country;
	private String txtStatus;
	private String transactionId;
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "YYYY-MM-DD")
	private String dateFrom;
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "YYYY-MM-DD")
	private String dateTo;
	private String requirementType;
	private String subscriberType;
	private String bankType;
	private boolean flag;
	private String customerType;
	private String status;
	private String goodsType;
	private String role;
	// bashir changes 10-10
	private String countryNames;
	private String loginUserId;
	private String rmStatus;
	private int kycId;
	private String vasMakerComment;
	private String vasCheckerComment;
	private String paymentMode;
	
	
	
	
	
	
	
	
	

	public String getPaymentMode() {
		return paymentMode;
	}

	public void setPaymentMode(String paymentMode) {
		this.paymentMode = paymentMode;
	}

	public String getVasMakerComment() {
		return vasMakerComment;
	}

	public void setVasMakerComment(String vasMakerComment) {
		this.vasMakerComment = vasMakerComment;
	}

	public String getVasCheckerComment() {
		return vasCheckerComment;
	}

	public void setVasCheckerComment(String vasCheckerComment) {
		this.vasCheckerComment = vasCheckerComment;
	}

	public int getKycId() {
		return kycId;
	}

	public void setKycId(int kycId) {
		this.kycId = kycId;
	}

	public String getCountryNames() {
		return countryNames;
	}

	public void setCountryNames(String countryNames) {
		this.countryNames = countryNames;
	}

	public int getPage() {
		return page;
	}

	public void setPage(int page) {
		this.page = page;
	}

	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}

	public String getDirection() {
		return direction;
	}

	public void setDirection(String direction) {
		this.direction = direction;
	}

	public String getSortBy() {
		return sortBy;
	}

	public void setSortBy(String sortBy) {
		this.sortBy = sortBy;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getEmailId() {
		return emailId;
	}

	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}

	public String getMobileNo() {
		return mobileNo;
	}

	public void setMobileNo(String mobileNo) {
		this.mobileNo = mobileNo;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getTxtStatus() {
		return txtStatus;
	}

	public void setTxtStatus(String txtStatus) {
		this.txtStatus = txtStatus;
	}

	public String getTransactionId() {
		return transactionId;
	}

	public void setTransactionId(String transactionId) {
		this.transactionId = transactionId;
	}

	public String getDateFrom() {
		return dateFrom;
	}

	public void setDateFrom(String dateFrom) {
		this.dateFrom = dateFrom;
	}

	public String getDateTo() {
		return dateTo;
	}

	public void setDateTo(String dateTo) {
		this.dateTo = dateTo;
	}

	public String getRequirementType() {
		return requirementType;
	}

	public void setRequirementType(String requirementType) {
		this.requirementType = requirementType;
	}

	public String getSubscriberType() {
		return subscriberType;
	}

	public void setSubscriberType(String subscriberType) {
		this.subscriberType = subscriberType;
	}

	public String getBankType() {
		return bankType;
	}

	public void setBankType(String bankType) {
		this.bankType = bankType;
	}

	public boolean isFlag() {
		return flag;
	}

	public void setFlag(boolean flag) {
		this.flag = flag;
	}

	public String getCustomerType() {
		return customerType;
	}

	public void setCustomerType(String customerType) {
		this.customerType = customerType;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getGoodsType() {
		return goodsType;
	}

	public void setGoodsType(String goodsType) {
		this.goodsType = goodsType;
	}

	public String getBankName() {
		return bankName;
	}

	public void setBankName(String bankName) {
		this.bankName = bankName;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public String getLoginUserId() {
		return loginUserId;
	}

	public void setLoginUserId(String loginUserId) {
		this.loginUserId = loginUserId;
	}

	public String getRmStatus() {
		return rmStatus;
	}

	public void setRmStatus(String rmStatus) {
		this.rmStatus = rmStatus;
	}

	@Override
	public String toString() {
		return "SearchRequest [page=" + page + ", size=" + size + ", direction=" + direction + ", sortBy=" + sortBy
				+ ", userId=" + userId + ", emailId=" + emailId + ", mobileNo=" + mobileNo + ", companyName="
				+ companyName + ", bankName=" + bankName + ", country=" + country + ", txtStatus=" + txtStatus
				+ ", transactionId=" + transactionId + ", dateFrom=" + dateFrom + ", dateTo=" + dateTo
				+ ", requirementType=" + requirementType + ", subscriberType=" + subscriberType + ", bankType="
				+ bankType + ", flag=" + flag + ", customerType=" + customerType + ", status=" + status + ", goodsType="
				+ goodsType + "]";
	}

}
