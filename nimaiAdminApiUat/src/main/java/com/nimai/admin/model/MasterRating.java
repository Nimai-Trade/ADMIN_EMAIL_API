package com.nimai.admin.model;

import javax.persistence.Column;
import javax.persistence.Basic;
import javax.persistence.GenerationType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Entity;

@Entity
@Table(name = "nimai_m_ratings")
public class MasterRating
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID")
    private Integer id;
    @Column(name = "AGENCY")
    private String agency;
    @Column(name = "scale")
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