package com.nimai.admin.payload;

import java.util.Date;

public class CurrencyResponce {

	private String country;
	private String code;
	private String countryId;
	private String currency;
	private String insertedBy;
	private Date insertedDate;
	private String modifiedBy;
	private Date modifiedDate;
	
//	public CurrencyResponce() {
//		
//	}
	
	public CurrencyResponce(String country, String code, String countryId, String currency) {
		super();
		this.country = country;
		this.code = code;
		this.countryId = countryId;
		this.currency = currency;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getCountryId() {
		return countryId;
	}

	public void setCountryId(String countryId) {
		this.countryId = countryId;
	}

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public String getInsertedBy() {
		return insertedBy;
	}

	public void setInsertedBy(String insertedBy) {
		this.insertedBy = insertedBy;
	}

	public Date getInsertedDate() {
		return insertedDate;
	}

	public void setInsertedDate(Date insertedDate) {
		this.insertedDate = insertedDate;
	}

	public String getModifiedBy() {
		return modifiedBy;
	}

	public void setModifiedBy(String modifiedBy) {
		this.modifiedBy = modifiedBy;
	}

	public Date getModifiedDate() {
		return modifiedDate;
	}

	public void setModifiedDate(Date modifiedDate) {
		this.modifiedDate = modifiedDate;
	}
	
	
	
}
