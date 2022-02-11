package com.nimai.splan.payload;

import java.util.Arrays;

public class SPlanApprovalBean {
private String approvedBy;
SPlanApprovalDetails approvalDetails[];



@Override
public String toString() {
	return "SPlanApprovalBean [approvedBy=" + approvedBy + ", approvalDetails=" + Arrays.toString(approvalDetails)
			+ "]";
}
public String getApprovedBy() {
	return approvedBy;
}
public void setApprovedBy(String approvedBy) {
	this.approvedBy = approvedBy;
}
public SPlanApprovalDetails[] getApprovalDetails() {
	return approvalDetails;
}
public void setApprovalDetails(SPlanApprovalDetails[] approvalDetails) {
	this.approvalDetails = approvalDetails;
}


}
