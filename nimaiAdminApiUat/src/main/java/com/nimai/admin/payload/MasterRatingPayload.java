package com.nimai.admin.payload;

public class MasterRatingPayload
{
    private Integer id;
    private String agency;
    private String scale;
    
    public Integer getId() {
        return this.id;
    }
    
    public void setId(final Integer id) {
        this.id = id;
    }
    
    public String getAgency() {
        return this.agency;
    }
    
    public void setAgency(final String agency) {
        this.agency = agency;
    }
    
    public String getScale() {
        return this.scale;
    }
    
    public void setScale(final String scale) {
        this.scale = scale;
    }
}