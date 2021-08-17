package com.nimai.admin.payload;

import java.util.Date;

public class ReportPaymentAndSubscription {
	private String user_ID;
	private String user_Type;
	private String organization;
	private String mobile;
	private String landline;
	private String country;
	private String email;
	private String first_Name;
	private String last_Name;
	private String plan;
	private String vAS;
	private String coupon_Code;
	private double coupon_Discount;
	private String discount_Ccy;
	private double fee_Paid;
	private String ccy;
	private String mode_of_Payment;
	private Date date_3_Time;
	private String payment_ID;
	private Date plan_activation_Date;
	private Date plan_expiry_Date;

	public ReportPaymentAndSubscription() {
		super();
		// TODO Auto-generated constructor stub
	}

	public String getUser_ID() {
		return user_ID;
	}

	public void setUser_ID(String user_ID) {
		this.user_ID = user_ID;
	}

	public String getUser_Type() {
		return user_Type;
	}

	public void setUser_Type(String user_Type) {
		this.user_Type = user_Type;
	}

	public String getOrganization() {
		return organization;
	}

	public void setOrganization(String organization) {
		this.organization = organization;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getLandline() {
		return landline;
	}

	public void setLandline(String landline) {
		this.landline = landline;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getFirst_Name() {
		return first_Name;
	}

	public void setFirst_Name(String first_Name) {
		this.first_Name = first_Name;
	}

	public String getLast_Name() {
		return last_Name;
	}

	public void setLast_Name(String last_Name) {
		this.last_Name = last_Name;
	}

	public String getPlan() {
		return plan;
	}

	public void setPlan(String plan) {
		this.plan = plan;
	}

	public String getvAS() {
		return vAS;
	}

	public void setvAS(String vAS) {
		this.vAS = vAS;
	}

	public String getCoupon_Code() {
		return coupon_Code;
	}

	public void setCoupon_Code(String coupon_Code) {
		this.coupon_Code = coupon_Code;
	}

	public double getCoupon_Discount() {
		return coupon_Discount;
	}

	public void setCoupon_Discount(double coupon_Discount) {
		this.coupon_Discount = coupon_Discount;
	}

	public String getDiscount_Ccy() {
		return discount_Ccy;
	}

	public void setDiscount_Ccy(String discount_Ccy) {
		this.discount_Ccy = discount_Ccy;
	}

	public double getFee_Paid() {
		return fee_Paid;
	}

	public void setFee_Paid(double fee_Paid) {
		this.fee_Paid = fee_Paid;
	}

	public String getCcy() {
		return ccy;
	}

	public void setCcy(String ccy) {
		this.ccy = ccy;
	}

	public String getMode_of_Payment() {
		return mode_of_Payment;
	}

	public void setMode_of_Payment(String mode_of_Payment) {
		this.mode_of_Payment = mode_of_Payment;
	}

	public Date getDate_3_Time() {
		return date_3_Time;
	}

	public void setDate_3_Time(Date date_3_Time) {
		this.date_3_Time = date_3_Time;
	}

	public String getPayment_ID() {
		return payment_ID;
	}

	public void setPayment_ID(String payment_ID) {
		this.payment_ID = payment_ID;
	}

	public Date getPlan_activation_Date() {
		return plan_activation_Date;
	}

	public void setPlan_activation_Date(Date plan_activation_Date) {
		this.plan_activation_Date = plan_activation_Date;
	}

	public Date getPlan_expiry_Date() {
		return plan_expiry_Date;
	}

	public void setPlan_expiry_Date(Date plan_expiry_Date) {
		this.plan_expiry_Date = plan_expiry_Date;
	}

}
