/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nimai.admin.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.DynamicUpdate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

/**
 *
 * @author Sahadeo
 */
@Entity
@Table(name = "nimai_f_kyc")
@EntityListeners(AuditingEntityListener.class)
@DynamicUpdate
public class NimaiFKyc implements Serializable {

	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Basic(optional = false)
	@Column(name = "id")
	private Integer id;
	@Column(name = "kyc_type")
	private String kycType;

	@Column(name = "country")
	private String country;

	@Lob
	@Column(name = "encoded_file_content")
	private String encodedFileContent;

	@Column(name = "document_Name")
	private String documentName;

	@Column(name = "document_type")
	private String documentType;

	@Column(name = "reason")
	private String reason;

	@Column(name = "kyc_status")
	private String kycStatus;

//	@CreatedBy
	@Column(name = "inserted_by")
	private String insertedBy;

//	@CreatedDate
	@Column(name = "inserted_date")
	private Date insertedDate;

//	@LastModifiedBy
	@Column(name = "modified_by")
	private String modifiedBy;

//	@LastModifiedDate
	@Column(name = "modified_date")
	private Date modifiedDate;

	@Column(name = "approved_by")
	private String approvedBy;

	@Column(name = "approved_date")
	private Date approvedDate;

	@Column(name = "approved_maker")
	private String approvedMaker;

	@Column(name = "maker_approved_date")
	private Date makerApprovedDate;

	@Column(name = "checker_comment")
	private String checkerComment;

	@Column(name = "comment")
	private String comment;
	
	@JoinColumn(name = "USERID", referencedColumnName = "USERID")
	@ManyToOne
	private NimaiMCustomer userid;

	
	
	
	
	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public NimaiFKyc() {
	}

	public NimaiFKyc(Integer id) {
		this.id = id;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getKycType() {
		return kycType;
	}

	public void setKycType(String kycType) {
		this.kycType = kycType;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getEncodedFileContent() {
		return encodedFileContent;
	}

	public void setEncodedFileContent(String encodedFileContent) {
		this.encodedFileContent = encodedFileContent;
	}

	public String getDocumentName() {
		return documentName;
	}

	public void setDocumentName(String documentName) {
		this.documentName = documentName;
	}

	public String getDocumentType() {
		return documentType;
	}

	public void setDocumentType(String documentType) {
		this.documentType = documentType;
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

	public String getInsertedBy() {
		return insertedBy;
	}

	public void setInsertedBy(String insertedBy) {
		this.insertedBy = insertedBy;
	}

	public Date getInsertedDate() {
		return insertedDate;
	}

	public void setInsertedDate(Date insertedDate) {
		this.insertedDate = insertedDate;
	}

	public String getModifiedBy() {
		return modifiedBy;
	}

	public void setModifiedBy(String modifiedBy) {
		this.modifiedBy = modifiedBy;
	}

	public Date getModifiedDate() {
		return modifiedDate;
	}

	public void setModifiedDate(Date modifiedDate) {
		this.modifiedDate = modifiedDate;
	}

	public String getApprovedBy() {
		return approvedBy;
	}

	public void setApprovedBy(String approvedBy) {
		this.approvedBy = approvedBy;
	}

	public Date getApprovedDate() {
		return approvedDate;
	}

	public void setApprovedDate(Date approvedDate) {
		this.approvedDate = approvedDate;
	}

	public NimaiMCustomer getUserid() {
		return userid;
	}

	public void setUserid(NimaiMCustomer userid) {
		this.userid = userid;
	}

	public String getApprovedMaker() {
		return approvedMaker;
	}

	public void setApprovedMaker(String approvedMaker) {
		this.approvedMaker = approvedMaker;
	}

	public Date getMakerApprovedDate() {
		return makerApprovedDate;
	}

	public void setMakerApprovedDate(Date makerApprovedDate) {
		this.makerApprovedDate = makerApprovedDate;
	}

	public String getCheckerComment() {
		return checkerComment;
	}

	public void setCheckerComment(String checkerComment) {
		this.checkerComment = checkerComment;
	}

	@Override
	public int hashCode() {
		int hash = 0;
		hash += (id != null ? id.hashCode() : 0);
		return hash;
	}

	@Override
	public boolean equals(Object object) {
		// TODO: Warning - this method won't work in the case the id fields are not set
		if (!(object instanceof NimaiFKyc)) {
			return false;
		}
		NimaiFKyc other = (NimaiFKyc) object;
		if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return "com.nimai.admin.model.NimaiFKyc[ id=" + id + " ]";
	}

}
