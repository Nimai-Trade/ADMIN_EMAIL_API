package com.nimai.kyc.payload;

import java.util.Date;

public class kycResponseList {

	private int kycId;

	private String custUserId;

	private String title;

	private String country;

	private String encodedFileContent;

	private String documentName;

	private String documentType;

	private String checkerComment;
	
	private String reason;

	private String kycStatus;

	private String insertedBy;

	private Date insertedDate;

	private String modifiedBy;

	private Date modifiedDate;

	private String approvedBy;

	private Date approvedDate;

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

	/**
	 * @return the kycId
	 */
	public int getKycId() {
		return kycId;
	}

	/**
	 * @param kycId the kycId to set
	 */
	public void setKycId(int kycId) {
		this.kycId = kycId;
	}

	/**
	 * @return the custUserId
	 */
	public String getCustUserId() {
		return custUserId;
	}

	/**
	 * @param custUserId the custUserId to set
	 */
	public void setCustUserId(String custUserId) {
		this.custUserId = custUserId;
	}

	/**
	 * @return the title
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * @param title the title to set
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	/**
	 * @return the country
	 */
	public String getCountry() {
		return country;
	}

	/**
	 * @param country the country to set
	 */
	public void setCountry(String country) {
		this.country = country;
	}

	/**
	 * @return the encodedFileContent
	 */
	public String getEncodedFileContent() {
		return encodedFileContent;
	}

	/**
	 * @param encodedFileContent the encodedFileContent to set
	 */
	public void setEncodedFileContent(String encodedFileContent) {
		this.encodedFileContent = encodedFileContent;
	}

	/**
	 * @return the documentName
	 */
	public String getDocumentName() {
		return documentName;
	}

	/**
	 * @param documentName the documentName to set
	 */
	public void setDocumentName(String documentName) {
		this.documentName = documentName;
	}

	/**
	 * @return the documentType
	 */
	public String getDocumentType() {
		return documentType;
	}

	/**
	 * @param documentType the documentType to set
	 */
	public void setDocumentType(String documentType) {
		this.documentType = documentType;
	}

	/**
	 * @return the reason
	 */
	public String getReason() {
		return reason;
	}

	/**
	 * @param reason the reason to set
	 */
	public void setReason(String reason) {
		this.reason = reason;
	}

	/**
	 * @return the kycStatus
	 */
	public String getKycStatus() {
		return kycStatus;
	}

	/**
	 * @param kycStatus the kycStatus to set
	 */
	public void setKycStatus(String kycStatus) {
		this.kycStatus = kycStatus;
	}

	/**
	 * @return the insertedBy
	 */
	public String getInsertedBy() {
		return insertedBy;
	}

	/**
	 * @param insertedBy the insertedBy to set
	 */
	public void setInsertedBy(String insertedBy) {
		this.insertedBy = insertedBy;
	}

	/**
	 * @return the insertedDate
	 */
	public Date getInsertedDate() {
		return insertedDate;
	}

	/**
	 * @param insertedDate the insertedDate to set
	 */
	public void setInsertedDate(Date insertedDate) {
		this.insertedDate = insertedDate;
	}

	/**
	 * @return the modifiedBy
	 */
	public String getModifiedBy() {
		return modifiedBy;
	}

	/**
	 * @param modifiedBy the modifiedBy to set
	 */
	public void setModifiedBy(String modifiedBy) {
		this.modifiedBy = modifiedBy;
	}

	/**
	 * @return the modifiedDate
	 */
	public Date getModifiedDate() {
		return modifiedDate;
	}

	/**
	 * @param modifiedDate the modifiedDate to set
	 */
	public void setModifiedDate(Date modifiedDate) {
		this.modifiedDate = modifiedDate;
	}

	/**
	 * @return the approvedBy
	 */
	public String getApprovedBy() {
		return approvedBy;
	}

	/**
	 * @param approvedBy the approvedBy to set
	 */
	public void setApprovedBy(String approvedBy) {
		this.approvedBy = approvedBy;
	}

	/**
	 * @return the approvedDate
	 */
	public Date getApprovedDate() {
		return approvedDate;
	}

	/**
	 * @param approvedDate the approvedDate to set
	 */
	public void setApprovedDate(Date approvedDate) {
		this.approvedDate = approvedDate;
	}
}
