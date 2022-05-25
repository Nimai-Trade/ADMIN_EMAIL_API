package com.nimai.admin.payload;

import java.util.Date;

public class KycSDetailResponse
{
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
    private String sourceDetails;
    private String comment;
    
    public String getComment() {
        return this.comment;
    }
    
    public void setComment(final String comment) {
        this.comment = comment;
    }
    
    public String getCheckerComment() {
        return this.checkerComment;
    }
    
    public void setCheckerComment(final String checkerComment) {
        this.checkerComment = checkerComment;
    }
    
    public String getUserId() {
        return this.userId;
    }
    
    public void setUserId(final String userId) {
        this.userId = userId;
    }
    
    public Integer getKycid() {
        return this.kycid;
    }
    
    public void setKycid(final Integer kycid) {
        this.kycid = kycid;
    }
    
    public String getDocName() {
        return this.docName;
    }
    
    public void setDocName(final String docName) {
        this.docName = docName;
    }
    
    public String getUserid() {
        return this.userid;
    }
    
    public void setUserid(final String userid) {
        this.userid = userid;
    }
    
    public String getCountry() {
        return this.country;
    }
    
    public void setCountry(final String country) {
        this.country = country;
    }
    
    public String getKycType() {
        return this.kycType;
    }
    
    public void setKycType(final String kycType) {
        this.kycType = kycType;
    }
    
    public String getDocType() {
        return this.docType;
    }
    
    public void setDocType(final String docType) {
        this.docType = docType;
    }
    
    public String getReason() {
        return this.reason;
    }
    
    public void setReason(final String reason) {
        this.reason = reason;
    }
    
    public String getKycStatus() {
        return this.kycStatus;
    }
    
    public void setKycStatus(final String kycStatus) {
        this.kycStatus = kycStatus;
    }
    
    public String getApproverName() {
        return this.approverName;
    }
    
    public void setApproverName(final String approverName) {
        this.approverName = approverName;
    }
    
    public Date getApprovalDate() {
        return this.approvalDate;
    }
    
    public void setApprovalDate(final Date approvalDate) {
        this.approvalDate = approvalDate;
    }
    
    public String getApprovalReason() {
        return this.approvalReason;
    }
    
    public void setApprovalReason(final String approvalReason) {
        this.approvalReason = approvalReason;
    }
    
    public String getEncodedFileContent() {
        return this.encodedFileContent;
    }
    
    public void setEncodedFileContent(final String encodedFileContent) {
        this.encodedFileContent = encodedFileContent;
    }
    
    public KycSDetailResponse(final Integer kycid, final String docName, final String country, final String sourceDetails, final String kycType, final String docType, final String reason, final String kycStatus, final String checkerComment, final String encodedFileContent) {
        this.kycid = kycid;
        this.docName = docName;
        this.country = country;
        this.sourceDetails = sourceDetails;
        this.kycType = kycType;
        this.docType = docType;
        this.reason = reason;
        this.kycStatus = kycStatus;
        this.checkerComment = checkerComment;
        this.encodedFileContent = encodedFileContent;
    }
    
    public KycSDetailResponse() {
    }
}