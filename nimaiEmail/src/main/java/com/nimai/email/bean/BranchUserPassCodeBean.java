package com.nimai.email.bean;

public class BranchUserPassCodeBean {
	private String token;
	private String passcodeValue;
	private String emailid;
	private String recaptchaResponse;
	private String userId;
	private int id;
	
	
	
	
	
	
	
	  
	  
	  
	  
	  
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getRecaptchaResponse() {
		return recaptchaResponse;
	}
	public void setRecaptchaResponse(String recaptchaResponse) {
		this.recaptchaResponse = recaptchaResponse;
	}
	/**
	 * @return the token
	 */
	public String getToken() {
		return token;
	}
	/**
	 * @param token the token to set
	 */
	public void setToken(String token) {
		this.token = token;
	}
	/**
	 * @return the passcodeValue
	 */
	public String getPasscodeValue() {
		return passcodeValue;
	}
	/**
	 * @param passcodeValue the passcodeValue to set
	 */
	public void setPasscodeValue(String passcodeValue) {
		this.passcodeValue = passcodeValue;
	}
	/**
	 * @return the emailid
	 */
	public String getEmailid() {
		return emailid;
	}
	/**
	 * @param emailid the emailid to set
	 */
	public void setEmailid(String emailid) {
		this.emailid = emailid;
	}
	
	

}
