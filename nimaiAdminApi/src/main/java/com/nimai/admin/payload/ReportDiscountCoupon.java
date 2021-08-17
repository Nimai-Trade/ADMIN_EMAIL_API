package com.nimai.admin.payload;

import java.util.Date;

public class ReportDiscountCoupon {

	private String discount_Type;
	private Double discount_amount;
	private String ccy;
	private Double discount_2;
	private Double max_Discount;
	private String coupon_For;
	private String subscripton_Plan;
	private String country;
	private Integer quantity;
	private Date start_Date;
	private Date end_Date;
	private String coupon_Code;
	private Integer consumed;

	public ReportDiscountCoupon() {
		super();
		// TODO Auto-generated constructor stub
	}

	public String getDiscount_Type() {
		return discount_Type;
	}

	public void setDiscount_Type(String discount_Type) {
		this.discount_Type = discount_Type;
	}

	

	public Double getDiscount_amount() {
		return discount_amount;
	}

	public void setDiscount_amount(Double discount_amount) {
		this.discount_amount = discount_amount;
	}

	public String getCcy() {
		return ccy;
	}

	public void setCcy(String ccy) {
		this.ccy = ccy;
	}

	public Double getDiscount_2() {
		return discount_2;
	}

	public void setDiscount_2(Double discount_2) {
		this.discount_2 = discount_2;
	}

	public Double getMax_Discount() {
		return max_Discount;
	}

	public void setMax_Discount(Double max_Discount) {
		this.max_Discount = max_Discount;
	}

	public String getCoupon_For() {
		return coupon_For;
	}

	public void setCoupon_For(String coupon_For) {
		this.coupon_For = coupon_For;
	}

	public String getSubscripton_Plan() {
		return subscripton_Plan;
	}

	public void setSubscripton_Plan(String subscripton_Plan) {
		this.subscripton_Plan = subscripton_Plan;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

	public Date getStart_Date() {
		return start_Date;
	}

	public void setStart_Date(Date start_Date) {
		this.start_Date = start_Date;
	}

	public Date getEnd_Date() {
		return end_Date;
	}

	public void setEnd_Date(Date end_Date) {
		this.end_Date = end_Date;
	}

	public String getCoupon_Code() {
		return coupon_Code;
	}

	public void setCoupon_Code(String coupon_Code) {
		this.coupon_Code = coupon_Code;
	}

	public Integer getConsumed() {
		return consumed;
	}

	public void setConsumed(Integer consumed) {
		this.consumed = consumed;
	}

}
