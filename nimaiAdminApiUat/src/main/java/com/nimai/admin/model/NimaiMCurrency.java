package com.nimai.admin.model;


import java.io.Serializable;
import java.util.Date;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.annotations.DynamicUpdate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

/**
 *
 * @author Sahadeo
 */
@Entity
@Table(name = "nimai_m_currency")
@EntityListeners(AuditingEntityListener.class)
@DynamicUpdate
public class NimaiMCurrency implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "country")
    private String country;
    @Size(max = 255)
    @Column(name = "code")
    private String code;
    @Size(max = 255)
    @Column(name = "country_id")
    private String countryId;
    @Size(max = 255)
    @Column(name = "currency")
    private String currency;
    @Size(max = 255)
    @Column(name = "inserted_by")
    private String insertedBy;
    @Column(name = "inserted_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date insertedDate;
    @Size(max = 255)
    @Column(name = "modified_by")
    private String modifiedBy;
    @Column(name = "modified_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date modifiedDate;

    public NimaiMCurrency() {
    }

    public NimaiMCurrency(String country) {
        this.country = country;
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

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (country != null ? country.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof NimaiMCurrency)) {
            return false;
        }
        NimaiMCurrency other = (NimaiMCurrency) object;
        if ((this.country == null && other.country != null) || (this.country != null && !this.country.equals(other.country))) {
            return false;
        }
        return true;
    }

    
}
