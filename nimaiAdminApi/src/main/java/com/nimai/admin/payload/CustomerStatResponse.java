package com.nimai.admin.payload;

public class CustomerStatResponse {

	private String userid;
	private String customer;
	private String payment;
	private String kyc;

	public CustomerStatResponse() {
		super();
		// TODO Auto-generated constructor stub
	}

	public String getUserid() {
		return userid;
	}

	public void setUserid(String userid) {
		this.userid = userid;
	}

	public String getCustomer() {
		return customer;
	}

	public void setCustomer(String customer) {
		this.customer = customer;
	}

	public String getPayment() {
		return payment;
	}

	public void setPayment(String payment) {
		this.payment = payment;
	}

	public String getKyc() {
		return kyc;
	}

	public void setKyc(String kyc) {
		this.kyc = kyc;
	}
}
