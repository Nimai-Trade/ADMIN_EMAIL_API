package com.nimai.admin.payload;

import java.sql.Date;

public class ReportUserSubscriptionRenewal {

	private String user_ID;
	private String user_Type;
	private String customer$Bank$Bank_as_Customer_Name;
	private String subscription;
	private String vas;
	private Integer fees_Paid;
	
	private Date activation_Date;
	private Date expiry_Date;
	private Double credits_Available;
	private String subsidiaries$Additional;

	public ReportUserSubscriptionRenewal() {
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

	public String getCustomer$Bank$Bank_as_Customer_Name() {
		return customer$Bank$Bank_as_Customer_Name;
	}

	public void setCustomer$Bank$Bank_as_Customer_Name(String customer$Bank$Bank_as_Customer_Name) {
		this.customer$Bank$Bank_as_Customer_Name = customer$Bank$Bank_as_Customer_Name;
	}

	public String getSubscription() {
		return subscription;
	}

	public void setSubscription(String subscription) {
		this.subscription = subscription;
	}

	public String getVas() {
		return vas;
	}

	public void setVas(String vas) {
		this.vas = vas;
	}

	public Integer getFees_Paid() {
		return fees_Paid;
	}



	public void setFees_Paid(Integer fees_Paid) {
		this.fees_Paid = fees_Paid;
	}

	public Date getActivation_Date() {
		return activation_Date;
	}

	public void setActivation_Date(Date activation_Date) {
		this.activation_Date = activation_Date;
	}

	public Date getExpiry_Date() {
		return expiry_Date;
	}

	public void setExpiry_Date(Date expiry_Date) {
		this.expiry_Date = expiry_Date;
	}

	public Double getCredits_Available() {
		return credits_Available;
	}

	public void setCredits_Available(Double credits_Available) {
		this.credits_Available = credits_Available;
	}

	public String getSubsidiaries$Additional() {
		return subsidiaries$Additional;
	}

	public void setSubsidiaries$Additional(String subsidiaries$Additional) {
		this.subsidiaries$Additional = subsidiaries$Additional;
	}

	

}
