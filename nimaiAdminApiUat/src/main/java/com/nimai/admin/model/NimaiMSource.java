package com.nimai.admin.model;

import javax.persistence.Column;
import javax.persistence.Basic;
import javax.persistence.GenerationType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import javax.persistence.EntityListeners;
import javax.persistence.Table;
import javax.persistence.Entity;

@Entity
@Table(name = "nimai_m_source")
@EntityListeners({ AuditingEntityListener.class })
@DynamicUpdate
public class NimaiMSource
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "SOURCE_ID")
    private int sourceId;
    @Column(name = "SOURCE_NAME")
    private String sourceDetails;
    
    public int getSourceId() {
        return this.sourceId;
    }
    
    public void setSourceId(final int sourceId) {
        this.sourceId = sourceId;
    }
    
    public String getSourceDetails() {
        return this.sourceDetails;
    }
    
    public void setSourceDetails(final String sourceDetails) {
        this.sourceDetails = sourceDetails;
    }
}