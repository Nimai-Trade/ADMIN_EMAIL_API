package com.nimai.admin.payload;

public class SourceDetailsList
{
    private Integer sourceId;
    private String sourceDtails;
    
    public String getSourceDtails() {
        return this.sourceDtails;
    }
    
    public void setSourceDtails(final String sourceDtails) {
        this.sourceDtails = sourceDtails;
    }
    
    public Integer getSourceId() {
        return this.sourceId;
    }
    
    public void setSourceId(final Integer sourceId) {
        this.sourceId = sourceId;
    }
}