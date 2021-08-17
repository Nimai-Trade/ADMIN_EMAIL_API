package com.nimai.email.bean;

public class ResetPassBean {
private String userId;
private String event;
private String emailId;




public String getEmailId() {
	return emailId;
}

public void setEmailId(String emailId) {
	this.emailId = emailId;
}

@Override
public String toString() {
	return "ResetPassBean [userId=" + userId + ", event=" + event + "]";
}

public String getEvent() {
	return event;
}

public void setEvent(String event) {
	this.event = event;
}

public String getUserId() {
	return userId;
}

public void setUserId(String userId) {
	this.userId = userId;
}




}
