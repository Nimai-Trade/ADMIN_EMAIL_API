package com.nimai.lc.entity;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonFormat;
@Entity
@Table(name="TRANSGOODS_TEMP")
public class NimaiLCGoods {
	
	@Id
	@Column(name="GOODS_ID") 
	private String goodsId;
	
//	@Column(name="TRANSACTION_ID") 
//	private String	transactionId;
	
	@Column(name="USERID") 
    private String	userId;
	
	@Column(name="BLACKLISTED_GOODS") 
	private String	blacklistedGoods;
	
	@Column(name="STATUS") 
	private String	status;
	
	@Column(name="INSERTED_BY") 
	private String	insertedBy;
	
	@JsonFormat(pattern = "yyyy-MM-dd")
	@Column(name="INSERTED_DATE") 
	private Date  insertedDate;
	
	@JsonFormat(pattern = "yyyy-MM-dd")
	@Column(name="MODIFIED_BY") 
	private String modifiedBy;
	
	@JsonFormat(pattern = "yyyy-MM-dd")
	@Column(name="MODIFIED_DATE") 
	private	Date modifiedDate;
	
	
	public String getGoodsId() {
		return goodsId;
	}
	public void setGoodsId(String goodsId) {
		this.goodsId = goodsId;
	}
//	public String getTransactionId() {
//		return transactionId;
//	}
//	public void setTransactionId(String transactionId) {
//		this.transactionId = transactionId;
//	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getBlacklistedGoods() {
		return blacklistedGoods;
	}
	public void setBlacklistedGoods(String blacklistedGoods) {
		this.blacklistedGoods = blacklistedGoods;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
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
	
	@JoinColumn(name = "TRANSACTION_ID",referencedColumnName = "TRANSACTION_ID" )
	@ManyToOne(cascade = CascadeType.ALL,fetch=FetchType.LAZY )
	@JsonBackReference
	private NimaiLC nimailcgoods;


	public NimaiLC getNimailcgoods() {
		return nimailcgoods;
	}
	public void setNimailcgoods(NimaiLC nimailcgoods) {
		this.nimailcgoods = nimailcgoods;
	}
	

}
