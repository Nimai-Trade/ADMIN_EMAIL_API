package com.nimai.admin.payload;

import java.util.Date;

public class ReportBankTransaction {

	private String user_ID;
	private String mobile;
	private String email;
	private Date date_3_Time;
	private String bank_Name;
	private String branch_Name;
	private String country;
	private String transaction_Id;
	private String requirement;
	private String iB;
	private Double amount;
	private String ccy;
	private Integer tenor;
	private Float applicable_benchmark;
	private Float confirmation_charges_p_a;
	private Float discounting_charges_p_a;
	private Float refinancing_charges_p_a;
	private Float banker_accept_charges_p_a;
	private String confirmation_charges_from_date_of_issuance_till_negotiation_date;
	private String confirmation_charges_from_date_of_issuance_till_maturity_date;
	private Float negotiation_charges_in_percentage;
	private Float negotiation_charges_in_fixed;
	private Float other_Charges;
	private Float min_Trxn_Charges;
	private Float total_Quote;
	private String validity;

	
	
	
	
	public Float getApplicable_benchmark() {
		return applicable_benchmark;
	}

	public void setApplicable_benchmark(Float applicable_benchmark) {
		this.applicable_benchmark = applicable_benchmark;
	}

	public Float getNegotiation_charges_in_percentage() {
		return negotiation_charges_in_percentage;
	}

	public void setNegotiation_charges_in_percentage(Float negotiation_charges_in_percentage) {
		this.negotiation_charges_in_percentage = negotiation_charges_in_percentage;
	}

	public Float getNegotiation_charges_in_fixed() {
		return negotiation_charges_in_fixed;
	}

	public void setNegotiation_charges_in_fixed(Float negotiation_charges_in_fixed) {
		this.negotiation_charges_in_fixed = negotiation_charges_in_fixed;
	}
	
	public ReportBankTransaction() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Date getDate_3_Time() {
		return date_3_Time;
	}

	public void setDate_3_Time(Date date_3_Time) {
		this.date_3_Time = date_3_Time;
	}

	public String getUser_ID() {
		return user_ID;
	}

	public void setUser_ID(String user_ID) {
		this.user_ID = user_ID;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getBank_Name() {
		return bank_Name;
	}

	public void setBank_Name(String bank_Name) {
		this.bank_Name = bank_Name;
	}

	public String getBranch_Name() {
		return branch_Name;
	}

	public void setBranch_Name(String branch_Name) {
		this.branch_Name = branch_Name;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getTransaction_Id() {
		return transaction_Id;
	}

	public void setTransaction_Id(String transaction_Id) {
		this.transaction_Id = transaction_Id;
	}

	public String getRequirement() {
		return requirement;
	}

	public void setRequirement(String requirement) {
		this.requirement = requirement;
	}

	public String getiB() {
		return iB;
	}

	public void setiB(String iB) {
		this.iB = iB;
	}

	public Double getAmount() {
		return amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}

	public String getCcy() {
		return ccy;
	}

	public void setCcy(String ccy) {
		this.ccy = ccy;
	}

	public Integer getTenor() {
		return tenor;
	}

	public void setTenor(Integer tenor) {
		this.tenor = tenor;
	}

	public Float getConfirmation_charges_p_a() {
		return confirmation_charges_p_a;
	}

	public void setConfirmation_charges_p_a(Float confirmation_charges_p_a) {
		this.confirmation_charges_p_a = confirmation_charges_p_a;
	}

	public String getConfirmation_charges_from_date_of_issuance_till_negotiation_date() {
		return confirmation_charges_from_date_of_issuance_till_negotiation_date;
	}

	public void setConfirmation_charges_from_date_of_issuance_till_negotiation_date(
			String confirmation_charges_from_date_of_issuance_till_negotiation_date) {
		this.confirmation_charges_from_date_of_issuance_till_negotiation_date = confirmation_charges_from_date_of_issuance_till_negotiation_date;
	}

	public String getConfirmation_charges_from_date_of_issuance_till_maturity_date() {
		return confirmation_charges_from_date_of_issuance_till_maturity_date;
	}

	public void setConfirmation_charges_from_date_of_issuance_till_maturity_date(
			String confirmation_charges_from_date_of_issuance_till_maturity_date) {
		this.confirmation_charges_from_date_of_issuance_till_maturity_date = confirmation_charges_from_date_of_issuance_till_maturity_date;
	}

	public Float getOther_Charges() {
		return other_Charges;
	}

	public void setOther_Charges(Float other_Charges) {
		this.other_Charges = other_Charges;
	}

	public Float getMin_Trxn_Charges() {
		return min_Trxn_Charges;
	}

	public void setMin_Trxn_Charges(Float min_Trxn_Charges) {
		this.min_Trxn_Charges = min_Trxn_Charges;
	}

	public Float getTotal_Quote() {
		return total_Quote;
	}

	public void setTotal_Quote(Float total_Quote) {
		this.total_Quote = total_Quote;
	}

	public String getValidity() {
		return validity;
	}

	public void setValidity(String validity) {
		this.validity = validity;
	}

	public Float getDiscounting_charges_p_a() {
		return discounting_charges_p_a;
	}

	public void setDiscounting_charges_p_a(Float discounting_charges_p_a) {
		this.discounting_charges_p_a = discounting_charges_p_a;
	}

	public Float getRefinancing_charges_p_a() {
		return refinancing_charges_p_a;
	}

	public void setRefinancing_charges_p_a(Float refinancing_charges_p_a) {
		this.refinancing_charges_p_a = refinancing_charges_p_a;
	}

	public Float getBanker_accept_charges_p_a() {
		return banker_accept_charges_p_a;
	}

	public void setBanker_accept_charges_p_a(Float banker_accept_charges_p_a) {
		this.banker_accept_charges_p_a = banker_accept_charges_p_a;
	}

}
