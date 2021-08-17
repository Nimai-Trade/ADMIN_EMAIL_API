package com.nimai.email.bean;

public class UserRegistrationBean {

	private String event;
	private String email;
	private String userId;
	private String link;
	private String userName;
	private String referId;
	private String recaptchaResponse;
	
	
	
	
	
	

	public String getReferId() {
		return referId;
	}

	public void setReferId(String referId) {
		this.referId = referId;
	}

	public String getRecaptchaResponse() {
		return recaptchaResponse;
	}

	public void setRecaptchaResponse(String recaptchaResponse) {
		this.recaptchaResponse = recaptchaResponse;
	}

	/**
	 * @return the userName
	 */
	public String getUserName() {
		return userName;
	}

	/**
	 * @param userName the userName to set
	 */
	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getEvent() {
		return event;
	}

	public void setEvent(String event) {
		this.event = event;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * @return the link
	 */
	public String getLink() {
		return link;
	}

	/**
	 * @param link the link to set
	 */
	public void setLink(String link) {
		this.link = link;
	}

	@Override
	public String toString() {
		return "UserRegistrationBean [event=" + event + ", email=" + email + ", userId=" + userId + ", link=" + link
				+ ", userName=" + userName + ", referId=" + referId + "]";
	}

}
