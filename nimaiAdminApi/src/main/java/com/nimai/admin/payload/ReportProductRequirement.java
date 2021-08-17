package com.nimai.admin.payload;

public class ReportProductRequirement {

	private String country;
	private String ccy;
	private String product_Type;
	private Integer txn_Placed;
	private Double cumulative_LC_Value;
	private Integer quotes_Received;
	private Double cumulative_Quote_Value;

	public ReportProductRequirement() {
		super();
		// TODO Auto-generated constructor stub
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getCcy() {
		return ccy;
	}

	public void setCcy(String ccy) {
		this.ccy = ccy;
	}

	public String getProduct_Type() {
		return product_Type;
	}

	public void setProduct_Type(String product_Type) {
		this.product_Type = product_Type;
	}

	public Integer getTxn_Placed() {
		return txn_Placed;
	}

	public void setTxn_Placed(Integer txn_Placed) {
		this.txn_Placed = txn_Placed;
	}

	public Double getCumulative_LC_Value() {
		return cumulative_LC_Value;
	}

	public void setCumulative_LC_Value(Double cumulative_LC_Value) {
		this.cumulative_LC_Value = cumulative_LC_Value;
	}

	public Integer getQuotes_Received() {
		return quotes_Received;
	}

	public void setQuotes_Received(Integer quotes_Received) {
		this.quotes_Received = quotes_Received;
	}

	public Double getCumulative_Quote_Value() {
		return cumulative_Quote_Value;
	}

	public void setCumulative_Quote_Value(Double cumulative_Quote_Value) {
		this.cumulative_Quote_Value = cumulative_Quote_Value;
	}

}
