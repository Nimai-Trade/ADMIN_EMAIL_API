package com.nimai.admin.payload;

public class AssociatedAccountsDetails {
	
	private String userId;
	private String name;
	private String emailId;
	private String mobile;
	private String country;
	private String landline;
	private String status;
	
	
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
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getEmailId() {
		return emailId;
	}
	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	public String getLandline() {
		return landline;
	}
	public void setLandline(String landline) {
		this.landline = landline;
	}
	public AssociatedAccountsDetails(String userId, String name, String emailId, String mobile, String country,
			String landline,String status) {
		this.userId = userId;
		this.name = name;
		this.emailId = emailId;
		this.mobile = mobile;
		this.country = country;
		this.landline = landline;
		this.status = status;
		
	}
	public AssociatedAccountsDetails() {
		
	}
	
	
	

}
