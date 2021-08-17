package com.nimai.admin.payload;

public class DashNewUserStat {

	private String month;
	private Double subs_amount;
	private Integer customers;
	private double subs_rate;

	public DashNewUserStat() {
		super();
		// TODO Auto-generated constructor stub
	}
	public Double getSubs_amount() {
		return subs_amount;
	}

	public void setSubs_amount(Double subs_amount) {
		this.subs_amount = subs_amount;
	}

	public String getMonth() {
		return month;
	}

	public void setMonth(String month) {
		this.month = month;
	}

	public Integer getCustomers() {
		return customers;
	}

	public void setCustomers(Integer customers) {
		this.customers = customers;
	}

	public double getSubs_rate() {
		return subs_rate;
	}

	public void setSubs_rate(double subs_rate) {
		this.subs_rate = subs_rate;
	}

}
