package com.nimai.admin.payload;

import java.util.Date;
import java.util.List;

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
  
  private List<SourceDetailsList> sourceDetails;
  
  private String comment;
  
  public List<SourceDetailsList> getSourceDetails() {
    return this.sourceDetails;
  }
  
  public void setSourceDetails(List<SourceDetailsList> sourceDetails) {
    this.sourceDetails = sourceDetails;
  }
  
  public String getComment() {
    return this.comment;
  }
  
  public void setComment(String comment) {
    this.comment = comment;
  }
  
  public String getCheckerComment() {
    return this.checkerComment;
  }
  
  public void setCheckerComment(String checkerComment) {
    this.checkerComment = checkerComment;
  }
  
  public String getUserId() {
    return this.userId;
  }
  
  public void setUserId(String userId) {
    this.userId = userId;
  }
  
  public Integer getKycid() {
    return this.kycid;
  }
  
  public void setKycid(Integer kycid) {
    this.kycid = kycid;
  }
  
  public String getDocName() {
    return this.docName;
  }
  
  public void setDocName(String docName) {
    this.docName = docName;
  }
  
  public String getUserid() {
    return this.userid;
  }
  
  public void setUserid(String userid) {
    this.userid = userid;
  }
  
  public String getCountry() {
    return this.country;
  }
  
  public void setCountry(String country) {
    this.country = country;
  }
  
  public String getKycType() {
    return this.kycType;
  }
  
  public void setKycType(String kycType) {
    this.kycType = kycType;
  }
  
  public String getDocType() {
    return this.docType;
  }
  
  public void setDocType(String docType) {
    this.docType = docType;
  }
  
  public String getReason() {
    return this.reason;
  }
  
  public void setReason(String reason) {
    this.reason = reason;
  }
  
  public String getKycStatus() {
    return this.kycStatus;
  }
  
  public void setKycStatus(String kycStatus) {
    this.kycStatus = kycStatus;
  }
  
  public String getApproverName() {
    return this.approverName;
  }
  
  public void setApproverName(String approverName) {
    this.approverName = approverName;
  }
  
  public Date getApprovalDate() {
    return this.approvalDate;
  }
  
  public void setApprovalDate(Date approvalDate) {
    this.approvalDate = approvalDate;
  }
  
  public String getApprovalReason() {
    return this.approvalReason;
  }
  
  public void setApprovalReason(String approvalReason) {
    this.approvalReason = approvalReason;
  }
  
  public String getEncodedFileContent() {
    return this.encodedFileContent;
  }
  
  public void setEncodedFileContent(String encodedFileContent) {
    this.encodedFileContent = encodedFileContent;
  }
  
  public KycBDetailResponse(Integer kycid, String docName, String country, String kycType, String docType, String reason, String kycStatus, String checkerComment, String encodedFileContent) {
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
  
  public KycBDetailResponse() {}
}
