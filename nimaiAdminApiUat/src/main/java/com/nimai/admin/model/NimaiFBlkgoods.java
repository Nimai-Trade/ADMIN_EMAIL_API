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
@Table(name = "nimai_f_blkgoods")
@NamedQueries({
    @NamedQuery(name = "NimaiFBlkgoods.findAll", query = "SELECT n FROM NimaiFBlkgoods n")})
public class NimaiFBlkgoods implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "GOODS_ID")
    private Long goodsId;
    @Size(max = 40)
    @Column(name = "GOODS_NAME")
    private String goodsName;
    @Column(name = "INSERTED_DATE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date insertedDate;
    @Column(name = "MODIFIED_DATE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date modifiedDate;
    @Basic(optional = false)
    @NotNull
    @Column(name = "GOODS_MID")
    private int goodsMid;
    @JoinColumn(name = "USERID", referencedColumnName = "USERID")
    @ManyToOne
    private NimaiMCustomer userid;

    public NimaiFBlkgoods() {
    }

    public NimaiFBlkgoods(Long goodsId) {
        this.goodsId = goodsId;
    }

    public NimaiFBlkgoods(Long goodsId, int goodsMid) {
        this.goodsId = goodsId;
        this.goodsMid = goodsMid;
    }

    public Long getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(Long goodsId) {
        this.goodsId = goodsId;
    }

    public String getGoodsName() {
        return goodsName;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
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

    public int getGoodsMid() {
        return goodsMid;
    }

    public void setGoodsMid(int goodsMid) {
        this.goodsMid = goodsMid;
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
        hash += (goodsId != null ? goodsId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof NimaiFBlkgoods)) {
            return false;
        }
        NimaiFBlkgoods other = (NimaiFBlkgoods) object;
        if ((this.goodsId == null && other.goodsId != null) || (this.goodsId != null && !this.goodsId.equals(other.goodsId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.nimai.admin.model.NimaiFBlkgoods[ goodsId=" + goodsId + " ]";
    }
    
}
