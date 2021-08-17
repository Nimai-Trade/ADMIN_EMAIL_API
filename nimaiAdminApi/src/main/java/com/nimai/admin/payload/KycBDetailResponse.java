package com.nimai.admin.payload;

import java.util.Date;

// bashir
public class KycBDetailResponse {

	private Integer kycid;
	private String docName;
	private String userid;
	private String country;
	private String kycType;
	private String docType;
	private String reason;
	private String kycStatus;
	private String approverName;
	private Date approvalDate;
	private String approvalReason;
	private String encodedFileContent;
	private String userId;
	private String checkerComment;
	
	private String comment;
	
	
	

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public String getCheckerComment() {
		return checkerComment;
	}

	public void setCheckerComment(String checkerComment) {
		this.checkerComment = checkerComment;
	}
	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public Integer getKycid() {
		return kycid;
	}

	public void setKycid(Integer kycid) {
		this.kycid = kycid;
	}

	public String getDocName() {
		return docName;
	}

	public void setDocName(String docName) {
		this.docName = docName;
	}

	public String getUserid() {
		return userid;
	}

	public void setUserid(String userid) {
		this.userid = userid;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getKycType() {
		return kycType;
	}

	public void setKycType(String kycType) {
		this.kycType = kycType;
	}

	public String getDocType() {
		return docType;
	}

	public void setDocType(String docType) {
		this.docType = docType;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	public String getKycStatus() {
		return kycStatus;
	}

	public void setKycStatus(String kycStatus) {
		this.kycStatus = kycStatus;
	}

	public String getApproverName() {
		return approverName;
	}

	public void setApproverName(String approverName) {
		this.approverName = approverName;
	}

	public Date getApprovalDate() {
		return approvalDate;
	}

	public void setApprovalDate(Date approvalDate) {
		this.approvalDate = approvalDate;
	}

	public String getApprovalReason() {
		return approvalReason;
	}

	public void setApprovalReason(String approvalReason) {
		this.approvalReason = approvalReason;
	}

	public String getEncodedFileContent() {
		return encodedFileContent;
	}

	public void setEncodedFileContent(String encodedFileContent) {
		this.encodedFileContent = encodedFileContent;
	}

	public KycBDetailResponse(Integer kycid, String docName, String country, String kycType,
			String docType, String reason, String kycStatus, String checkerComment, String encodedFileContent) {
		super();
		this.kycid = kycid;
		this.docName = docName;
		this.country = country;
		this.kycType = kycType;
		this.docType = docType;
		this.reason = reason;
		this.kycStatus = kycStatus;
		this.checkerComment = checkerComment;
		this.encodedFileContent = encodedFileContent;
	}

	public KycBDetailResponse() {
	}

	
	
}
