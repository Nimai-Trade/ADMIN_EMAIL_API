package com.nimai.admin.model;

import java.util.Date;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.DynamicUpdate;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity
@Table(name = "nimai_m_display_features")
@EntityListeners(AuditingEntityListener.class)
@DynamicUpdate
public class NimaiMDisplayFeatures {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Basic(optional = false)
	@Column(name = "ID")
	private Integer id;
	@Column(name = "COUNTRY")
	private String country;
	@Column(name = "AVERAGE_AMOUNT")
	private double avgAmount;
	@Column(name = "STATUS")
	private String status;
	@CreatedBy
	@Column(name = "CREATED_BY")
	private String createdBy;
	@CreatedDate
	@Column(name = "CREATED_DATE")
	private Date createdDate;
	@LastModifiedBy
	@Column(name = "MODIFIED_BY")
	private String modifiedBy;
	@LastModifiedDate
	@Column(name = "MODIFIED_DATE")
	private Date modifiedDate;

	// ----
	@Column(name = "CCY")
	private String ccy;

	public NimaiMDisplayFeatures() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public double getAvgAmount() {
		return avgAmount;
	}

	public void setAvgAmount(double avgAmount) {
		this.avgAmount = avgAmount;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
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

	public String getCcy() {
		return ccy;
	}

	public void setCcy(String ccy) {
		this.ccy = ccy;
	}

}
