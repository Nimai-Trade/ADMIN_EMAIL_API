package com.nimai.admin.payload;

import java.math.BigInteger;

public class ReportCountryWise {

	private String country1of_registration0;
	private String ccy;
	private Integer trxn_Count;
	private String cumulative_Trxn_Amount;
	private Integer trxn_Accepted;
	private Integer trxn_Closed;
	private Integer trxn_Rejected;
	private Integer trxn_Expired;

	public ReportCountryWise() {
		super();
		// TODO Auto-generated constructor stub
	}

	public String getCountry1of_registration0() {
		return country1of_registration0;
	}

	public void setCountry1of_registration0(String country1of_registration0) {
		this.country1of_registration0 = country1of_registration0;
	}

	public String getCcy() {
		return ccy;
	}

	public void setCcy(String ccy) {
		this.ccy = ccy;
	}

	public Integer getTrxn_Count() {
		return trxn_Count;
	}

	public void setTrxn_Count(Integer trxn_Count) {
		this.trxn_Count = trxn_Count;
	}

	public String getCumulative_Trxn_Amount() {
		return cumulative_Trxn_Amount;
	}

	public void setCumulative_Trxn_Amount(String cumulative_Trxn_Amount) {
		this.cumulative_Trxn_Amount = cumulative_Trxn_Amount;
	}

	public Integer getTrxn_Accepted() {
		return trxn_Accepted;
	}

	public void setTrxn_Accepted(Integer trxn_Accepted) {
		this.trxn_Accepted = trxn_Accepted;
	}

	public Integer getTrxn_Closed() {
		return trxn_Closed;
	}

	public void setTrxn_Closed(Integer trxn_Closed) {
		this.trxn_Closed = trxn_Closed;
	}

	public Integer getTrxn_Rejected() {
		return trxn_Rejected;
	}

	public void setTrxn_Rejected(Integer trxn_Rejected) {
		this.trxn_Rejected = trxn_Rejected;
	}

	public Integer getTrxn_Expired() {
		return trxn_Expired;
	}

	public void setTrxn_Expired(Integer trxn_Expired) {
		this.trxn_Expired = trxn_Expired;
	}


}
