package com.nimai.kyc.model;

import java.util.Collection;
import java.util.Date;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "nimai_f_kyc")
public class NimaiKyc {

	private static final long serialVersionUID = 1L;
	@Id
	@Basic(optional = false)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private int kycId;

	@JoinColumn(name = "USERID", referencedColumnName = "USERID")
	@ManyToOne(optional = false)
	private NimaiMCustomer custUserId;

	@Column(name = "kyc_type")
	private String title;

	@Column(name = "country")
	private String country;

	@Column(name = "encoded_file_content")
	private String encodedFileContent;

	@Column(name = "document_Name")
	private String documentName;

	@Column(name = "document_type")
	private String documentType;

	@Column(name = "checker_comment")
	private String checkerComment;

	@Column(name = "reason")
	private String reason;

	@Column(name = "kyc_status")
	private String kycStatus;

	@Column(name = "inserted_by")
	private String insertedBy;

	@Column(name = "inserted_date")
	private Date insertedDate;

	@Column(name = "modified_by")
	private String modifiedBy;

	@Column(name = "modified_date")
	private Date modifiedDate;

	@Column(name = "approved_by")
	private String approvedBy;

	@Column(name = "approved_date")
	private Date approvedDate;

	@Column(name = "comment")
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
	 * @return the custUserId
	 */
	public NimaiMCustomer getCustUserId() {
		return custUserId;
	}

	/**
	 * @param custUserId the custUserId to set
	 */
	public void setCustUserId(NimaiMCustomer custUserId) {
		this.custUserId = custUserId;
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

	@Override
	public String toString() {
		return "NimaiKyc [kycId=" + kycId + ", custUserId=" + custUserId + ", title=" + title + ", country=" + country
				+ ", encodedFileContent=" + encodedFileContent + ", documentName=" + documentName + ", documentType="
				+ documentType + ", reason=" + reason + ", kycStatus=" + kycStatus + ", insertedBy=" + insertedBy
				+ ", insertedDate=" + insertedDate + ", modifiedBy=" + modifiedBy + ", modifiedDate=" + modifiedDate
				+ ", approvedBy=" + approvedBy + ", approvedDate=" + approvedDate + "]";
	}

	public Collection<NimaiKyc> kycList() {
		// TODO Auto-generated method stub
		return null;
	}



	


	

}
