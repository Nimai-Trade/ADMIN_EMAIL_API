package com.nimai.lc.bean;

import java.util.Date;

public class NimaiLCGoodsBean {
	
	private String goodsId;
	private String	transactionId;
	private String	userId;
	private String	blacklistedGoods;
	private String	status;
	private String	insertedBy;
	private Date  insertedDate;
	private String modifiedBy;
	public String getGoodsId() {
		return goodsId;
	}
	public void setGoodsId(String goodsId) {
		this.goodsId = goodsId;
	}
	public String getTransactionId() {
		return transactionId;
	}
	public void setTransactionId(String transactionId) {
		this.transactionId = transactionId;
	}
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
	
	
	
}
