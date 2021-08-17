package com.nimai.admin.payload;

public class DashTransStat {

	private String month;
	private Integer day;

	public Integer getDay() {
		return day;
	}

	public void setDay(Integer day) {
		this.day = day;
	}

	private Integer trxn_count;
	private long cumulative_amount;

	public DashTransStat() {
		super();
		// TODO Auto-generated constructor stub
	}

	public String getMonth() {
		return month;
	}

	public void setMonth(String month) {
		this.month = month;
	}

	public Integer getTrxn_count() {
		return trxn_count;
	}

	public void setTrxn_count(Integer trxn_count) {
		this.trxn_count = trxn_count;
	}

	public long getCumulative_amount() {
		return cumulative_amount;
	}

	public void setCumulative_amount(long cumulative_amount) {
		this.cumulative_amount = cumulative_amount;
	}

}
