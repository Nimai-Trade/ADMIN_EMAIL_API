package com.nimai.admin.payload;

public class BankRatingRequest
{
    private Integer id;
    private String bankUserid;
    private String rating;
    
    public Integer getId() {
        return this.id;
    }
    
    public void setId(final Integer id) {
        this.id = id;
    }
    
    public String getBankUserid() {
        return this.bankUserid;
    }
    
    public void setBankUserid(final String bankUserid) {
        this.bankUserid = bankUserid;
    }
    
    public String getRating() {
        return this.rating;
    }
    
    public void setRating(final String rating) {
        this.rating = rating;
    }
}