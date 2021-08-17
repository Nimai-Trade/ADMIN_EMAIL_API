package com.nimai.email.bean;

public class LcUploadBean {
private String transactionid;
private String userId;
private String event;
private String branchUserEmailId;









/**
 * @return the branchUserEmailId
 */
public String getBranchUserEmailId() {
	return branchUserEmailId;
}
/**
 * @param branchUserEmailId the branchUserEmailId to set
 */
public void setBranchUserEmailId(String branchUserEmailId) {
	this.branchUserEmailId = branchUserEmailId;
}
/**
 * @return the userId
 */
public String getUserId() {
	return userId;
}
/**
 * @param userId the userId to set
 */
public void setUserId(String userId) {
	this.userId = userId;
}
/**
 * @return the transactionid
 */
public String getTransactionid() {
	return transactionid;
}
/**
 * @param transactionid the transactionid to set
 */
public void setTransactionid(String transactionid) {
	this.transactionid = transactionid;
}

/**
 * @return the event
 */
public String getEvent() {
	return event;
}
/**
 * @param event the event to set
 */
public void setEvent(String event) {
	this.event = event;
}


}
