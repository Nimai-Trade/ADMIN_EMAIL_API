package com.nimai.email.bean;

import java.util.Date;

public class TupleBean {
private String userId;

private String corporate;

private String sPlanName;

private String currency;

private String sPlanFee;


private String emailAddress;

private String 	subscriptionId;

private String sPlanUserId;

private String status;
 
private Date splanendDate;

private String accountType;

private String accoutSource;






public String getCorporate() {
	return corporate;
}

public void setCorporate(String corporate) {
	this.corporate = corporate;
}

public String getsPlanName() {
	return sPlanName;
}

public void setsPlanName(String sPlanName) {
	this.sPlanName = sPlanName;
}

public String getCurrency() {
	return currency;
}

public void setCurrency(String currency) {
	this.currency = currency;
}

public String getsPlanFee() {
	return sPlanFee;
}

public void setsPlanFee(String sPlanFee) {
	this.sPlanFee = sPlanFee;
}

public String getUserId() {
	return userId;
}

public void setUserId(String userId) {
	this.userId = userId;
}

public String getEmailAddress() {
	return emailAddress;
}

public void setEmailAddress(String emailAddress) {
	this.emailAddress = emailAddress;
}

public String getSubscriptionId() {
	return subscriptionId;
}

public void setSubscriptionId(String subscriptionId) {
	this.subscriptionId = subscriptionId;
}

public String getsPlanUserId() {
	return sPlanUserId;
}

public void setsPlanUserId(String sPlanUserId) {
	this.sPlanUserId = sPlanUserId;
}

public String getStatus() {
	return status;
}

public void setStatus(String status) {
	this.status = status;
}

public Date getSplanendDate() {
	return splanendDate;
}

public void setSplanendDate(Date date) {
	this.splanendDate = date;
}

public String getAccountType() {
	return accountType;
}

public void setAccountType(String accountType) {
	this.accountType = accountType;
}

public String getAccoutSource() {
	return accoutSource;
}

public void setAccoutSource(String accoutSource) {
	this.accoutSource = accoutSource;
}

@Override
public String toString() {
	return "TupleBean [userId=" + userId + ", corporate=" + corporate + ", sPlanName=" + sPlanName + ", currency="
			+ currency + ", sPlanFee=" + sPlanFee + ", emailAddress=" + emailAddress + ", subscriptionId="
			+ subscriptionId + ", sPlanUserId=" + sPlanUserId + ", status=" + status + ", splanendDate=" + splanendDate
			+ ", accountType=" + accountType + ", accoutSource=" + accoutSource + "]";
}


}
