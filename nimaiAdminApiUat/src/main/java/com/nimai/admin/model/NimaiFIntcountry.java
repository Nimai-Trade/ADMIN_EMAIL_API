/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nimai.admin.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 *
 * @author Sahadeo
 */
@Entity
@Table(name = "nimai_f_intcountry")
@NamedQueries({
    @NamedQuery(name = "NimaiFIntcountry.findAll", query = "SELECT n FROM NimaiFIntcountry n")})
public class NimaiFIntcountry implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "COUNTRY_ID")
    private Long countryId;
    @Size(max = 40)
    @Column(name = "COUNTRY_NAME")
    private String countryName;
    @Column(name = "INSERTED_DATE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date insertedDate;
    @Column(name = "MODIFIED_DATE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date modifiedDate;
    @Basic(optional = false)
    @NotNull
    @Column(name = "COUNTRY_CURID")
    private String countryCurid;
    @JoinColumn(name = "USERID", referencedColumnName = "USERID")
    @ManyToOne
    private NimaiMCustomer userid;

    public NimaiFIntcountry() {
    }

    public NimaiFIntcountry(Long countryId) {
        this.countryId = countryId;
    }

    public NimaiFIntcountry(Long countryId, String countryCurid) {
        this.countryId = countryId;
        this.countryCurid = countryCurid;
    }

    public Long getCountryId() {
        return countryId;
    }

    public void setCountryId(Long countryId) {
        this.countryId = countryId;
    }

    public String getCountryName() {
        return countryName;
    }

    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }

    public Date getInsertedDate() {
        return insertedDate;
    }

    public void setInsertedDate(Date insertedDate) {
        this.insertedDate = insertedDate;
    }

    public Date getModifiedDate() {
        return modifiedDate;
    }

    public void setModifiedDate(Date modifiedDate) {
        this.modifiedDate = modifiedDate;
    }

    public String getCountryCurid() {
        return countryCurid;
    }

    public void setCountryCurid(String countryCurid) {
        this.countryCurid = countryCurid;
    }

    public NimaiMCustomer getUserid() {
        return userid;
    }

    public void setUserid(NimaiMCustomer userid) {
        this.userid = userid;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (countryId != null ? countryId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof NimaiFIntcountry)) {
            return false;
        }
        NimaiFIntcountry other = (NimaiFIntcountry) object;
        if ((this.countryId == null && other.countryId != null) || (this.countryId != null && !this.countryId.equals(other.countryId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.nimai.admin.model.NimaiFIntcountry[ countryId=" + countryId + " ]";
    }
    
}
