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
import javax.validation.constraints.Size;

/**
 *
 * @author Sahadeo
 */
@Entity
@Table(name = "nimai_f_owner")
@NamedQueries({
    @NamedQuery(name = "NimaiFOwner.findAll", query = "SELECT n FROM NimaiFOwner n")})
public class NimaiFOwner implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "OWNER_ID")
    private Long ownerId;
    @Size(max = 25)
    @Column(name = "OWNER_FNAME")
    private String ownerFname;
    @Size(max = 25)
    @Column(name = "OWNER_LNAME")
    private String ownerLname;
    @Size(max = 25)
    @Column(name = "OWNER_DESIGNATION")
    private String ownerDesignation;
    @Column(name = "INSERTED_DATE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date insertedDate;
    @Column(name = "MODIFIED_DATE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date modifiedDate;
    @JoinColumn(name = "USERID", referencedColumnName = "USERID")
    @ManyToOne
    private NimaiMCustomer userid;

    public NimaiFOwner() {
    }

    public NimaiFOwner(Long ownerId) {
        this.ownerId = ownerId;
    }

    public Long getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(Long ownerId) {
        this.ownerId = ownerId;
    }

    public String getOwnerFname() {
        return ownerFname;
    }

    public void setOwnerFname(String ownerFname) {
        this.ownerFname = ownerFname;
    }

    public String getOwnerLname() {
        return ownerLname;
    }

    public void setOwnerLname(String ownerLname) {
        this.ownerLname = ownerLname;
    }

    public String getOwnerDesignation() {
        return ownerDesignation;
    }

    public void setOwnerDesignation(String ownerDesignation) {
        this.ownerDesignation = ownerDesignation;
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

    public NimaiMCustomer getUserid() {
        return userid;
    }

    public void setUserid(NimaiMCustomer userid) {
        this.userid = userid;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (ownerId != null ? ownerId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof NimaiFOwner)) {
            return false;
        }
        NimaiFOwner other = (NimaiFOwner) object;
        if ((this.ownerId == null && other.ownerId != null) || (this.ownerId != null && !this.ownerId.equals(other.ownerId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.nimai.admin.model.NimaiFOwner[ ownerId=" + ownerId + " ]";
    }
    
}
