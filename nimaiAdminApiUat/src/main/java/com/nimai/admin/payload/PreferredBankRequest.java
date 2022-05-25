package com.nimai.admin.payload;

public class PreferredBankRequest
{
    private String custUserId;
    private String[] banks;
    
    public String getCustUserId() {
        return this.custUserId;
    }
    
    public void setCustUserId(final String custUserId) {
        this.custUserId = custUserId;
    }
    
    public String[] getBanks() {
        return this.banks;
    }
    
    public void setBanks(final String[] banks) {
        this.banks = banks;
    }
}