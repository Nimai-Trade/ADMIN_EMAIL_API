package com.nimai.admin.payload;

public class SorceDetailsReponseBean
{
    private Integer sourceId;
    private String userId;
    private String sourceDtails;
    
    public String getUserId() {
        return this.userId;
    }
    
    public void setUserId(final String userId) {
        this.userId = userId;
    }
    
    public SorceDetailsReponseBean(final int sourceId2, final String sourceDetails) {
        this.sourceId = sourceId2;
        this.sourceDtails = sourceDetails;
    }
    
    public Integer getSourceId() {
        return this.sourceId;
    }
    
    public void setSourceId(final Integer sourceId) {
        this.sourceId = sourceId;
    }
    
    public String getSourceDtails() {
        return this.sourceDtails;
    }
    
    public void setSourceDtails(final String sourceDtails) {
        this.sourceDtails = sourceDtails;
    }
}