package com.nimai.admin.payload;

public class ResetPassword {
	
	private String username;
	private String oldPassword;
	private String newPassword;
	private String confirmPassword;
	private String token;
	private String empCode;
	private String recaptchaResponse;
	
	
	
	
	
	
	public String getRecaptchaResponse() {
		return recaptchaResponse;
	}
	public void setRecaptchaResponse(String recaptchaResponse) {
		this.recaptchaResponse = recaptchaResponse;
	}
	public String getEmpCode() {
		return empCode;
	}
	public void setEmpCode(String empCode) {
		this.empCode = empCode;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getOldPassword() {
		return oldPassword;
	}
	public void setOldPassword(String oldPassword) {
		this.oldPassword = oldPassword;
	}
	public String getNewPassword() {
		return newPassword;
	}
	public void setNewPassword(String newPassword) {
		this.newPassword = newPassword;
	}
	public String getConfirmPassword() {
		return confirmPassword;
	}
	public void setConfirmPassword(String confirmPassword) {
		this.confirmPassword = confirmPassword;
	}
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	
	

}
