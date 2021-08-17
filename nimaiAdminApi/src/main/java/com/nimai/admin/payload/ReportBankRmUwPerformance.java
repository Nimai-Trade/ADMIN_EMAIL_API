package com.nimai.admin.payload;

import java.math.BigDecimal;
import java.math.BigInteger;

public class ReportBankRmUwPerformance {

	private String country;
	private String first_Name;
	private String last_Name;
	private Integer bank_Accounts;
	private Integer quote_Count;
	private Double cumulative_Quote_Amount;
	private Double accepted_Quote;
	private Double rejected_Quote;
	private Double expired_Quote;

	public ReportBankRmUwPerformance() {
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

	public Integer getBank_Accounts() {
		return bank_Accounts;
	}

	public void setBank_Accounts(Integer bank_Accounts) {
		this.bank_Accounts = bank_Accounts;
	}

	public Integer getQuote_Count() {
		return quote_Count;
	}

	public void setQuote_Count(Integer quote_Count) {
		this.quote_Count = quote_Count;
	}

	public Double getCumulative_Quote_Amount() {
		return cumulative_Quote_Amount;
	}

	public void setCumulative_Quote_Amount(Double cumulative_Quote_Amount) {
		this.cumulative_Quote_Amount = cumulative_Quote_Amount;
	}

	public Double getAccepted_Quote() {
		return accepted_Quote;
	}

	public void setAccepted_Quote(Double accepted_Quote) {
		this.accepted_Quote = accepted_Quote;
	}

	public Double getRejected_Quote() {
		return rejected_Quote;
	}

	public void setRejected_Quote(Double rejected_Quote) {
		this.rejected_Quote = rejected_Quote;
	}

	public Double getExpired_Quote() {
		return expired_Quote;
	}

	public void setExpired_Quote(Double expired_Quote) {
		this.expired_Quote = expired_Quote;
	}



	
}
