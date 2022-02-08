package com.nimai.admin.payload;

public class BankRatingRequest {
	
	private Integer id;
	private String bankUserid;
	private String rating;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	
	public String getBankUserid() {
		return bankUserid;
	}
	public void setBankUserid(String bankUserid) {
		this.bankUserid = bankUserid;
	}
	public String getRating() {
		return rating;
	}
	public void setRating(String rating) {
		this.rating = rating;
	}
	
	

}
