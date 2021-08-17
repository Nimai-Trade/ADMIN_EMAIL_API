package com.nimai.admin.payload;

import java.math.BigDecimal;
import java.math.BigInteger;

public class ReportBankRmPerformance {
	private String country;
	private String first_Name;
	private String last_Name;
	private Integer bank_As_Customer_accounts;
	private Integer trxn_Count;
	private Double cumulative_LC_Amount;
	private Double trxn_Accepted;
	private Double trxn_Rejected;
	private Double trxn_Expired;

	public ReportBankRmPerformance() {
		super();
		// TODO Auto-generated constructor stub
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
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


	public Double getCumulative_LC_Amount() {
		return cumulative_LC_Amount;
	}

	public void setCumulative_LC_Amount(Double cumulative_LC_Amount) {
		this.cumulative_LC_Amount = cumulative_LC_Amount;
	}

	

	public Integer getBank_As_Customer_accounts() {
		return bank_As_Customer_accounts;
	}

	public void setBank_As_Customer_accounts(Integer bank_As_Customer_accounts) {
		this.bank_As_Customer_accounts = bank_As_Customer_accounts;
	}

	public Integer getTrxn_Count() {
		return trxn_Count;
	}

	public void setTrxn_Count(Integer trxn_Count) {
		this.trxn_Count = trxn_Count;
	}

	public Double getTrxn_Accepted() {
		return trxn_Accepted;
	}

	public void setTrxn_Accepted(Double trxn_Accepted) {
		this.trxn_Accepted = trxn_Accepted;
	}

	public Double getTrxn_Rejected() {
		return trxn_Rejected;
	}

	public void setTrxn_Rejected(Double trxn_Rejected) {
		this.trxn_Rejected = trxn_Rejected;
	}

	public Double getTrxn_Expired() {
		return trxn_Expired;
	}

	public void setTrxn_Expired(Double trxn_Expired) {
		this.trxn_Expired = trxn_Expired;
	}

	@Override
	public String toString() {
		return "ReportBankRmPerformance [country=" + country + ", first_Name=" + first_Name + ", last_Name=" + last_Name
				+ ", bank_As_Customer_accounts=" + bank_As_Customer_accounts + ", trxn_Count=" + trxn_Count
				+ ", cumulative_LC_Amount=" + cumulative_LC_Amount + ", trxn_Accepted=" + trxn_Accepted
				+ ", trxn_Rejected=" + trxn_Rejected + ", trxn_Expired=" + trxn_Expired + "]";
	}

	
}
