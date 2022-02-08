package com.nimai.admin.payload;

public class PreferredBankListResponse {
	private String userid;
	private String custUserid;
	private String firstName;
	private String lastName;
	private String emailAddress;
	private String bankName;
	
	public PreferredBankListResponse()
	{}
	public PreferredBankListResponse(String userid, String firstName, String lastName, String emailAddress, String bankName) {
		super();
		this.userid = userid;
		this.firstName = firstName;
		this.lastName = lastName;
		this.emailAddress = emailAddress;
		this.bankName=bankName;
	}
	
	public PreferredBankListResponse(String userid, String custUserid,String bankName) {
		super();
		this.userid = userid;
		this.custUserid=custUserid;
		this.bankName=bankName;
	}
	
	public String getCustUserid() {
		return custUserid;
	}


	public void setCustUserid(String custUserid) {
		this.custUserid = custUserid;
	}


	public String getUserid() {
		return userid;
	}
	public void setUserid(String userid) {
		this.userid = userid;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public String getEmailAddress() {
		return emailAddress;
	}
	public void setEmailAddress(String emailAddress) {
		this.emailAddress = emailAddress;
	}
	public String getBankName() {
		return bankName;
	}
	public void setBankName(String bankName) {
		this.bankName = bankName;
	}
	
	
}
