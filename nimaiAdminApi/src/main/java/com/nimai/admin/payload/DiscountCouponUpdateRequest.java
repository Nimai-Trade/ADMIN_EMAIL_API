package com.nimai.admin.payload;

import java.util.Date;

public class DiscountCouponUpdateRequest {
	private Integer discountId;
	private String country;
	private String status;
	private String modifiedBy;
	private Date modifiedDate;
	private  String couponFor;
	private String discountType;
	
	
	
	
	
	
	
	public String getDiscountType() {
		return discountType;
	}
	public void setDiscountType(String discountType) {
		this.discountType = discountType;
	}
	public String getCouponFor() {
		return couponFor;
	}
	public void setCouponFor(String couponFor) {
		this.couponFor = couponFor;
	}
	public Integer getDiscountId() {
		return discountId;
	}
	public void setDiscountId(Integer discountId) {
		this.discountId = discountId;
	}
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
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
	public DiscountCouponUpdateRequest() {
		super();
		// TODO Auto-generated constructor stub
	}
}
