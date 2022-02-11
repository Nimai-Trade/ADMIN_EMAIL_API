package com.nimai.splan.payload;

public class SPlanApprovalDetails {
private String subscriptionId;
private String status;





@Override
public String toString() {
	return "SPlanApprovalDetails [subscriptionId=" + subscriptionId + ", status=" + status + "]";
}
public String getSubscriptionId() {
	return subscriptionId;
}
public void setSubscriptionId(String subscriptionId) {
	this.subscriptionId = subscriptionId;
}
public String getStatus() {
	return status;
}
public void setStatus(String status) {
	this.status = status;
}





}
