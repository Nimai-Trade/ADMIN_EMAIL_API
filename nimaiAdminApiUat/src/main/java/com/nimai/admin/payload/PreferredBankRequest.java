package com.nimai.admin.payload;

public class PreferredBankRequest 
{
	private String custUserId;
	private String banks[];
	
	
	public String getCustUserId() {
		return custUserId;
	}
	public void setCustUserId(String custUserId) {
		this.custUserId = custUserId;
	}
	public String[] getBanks() {
		return banks;
	}
	public void setBanks(String[] banks) {
		this.banks = banks;
	}
	
	
}
