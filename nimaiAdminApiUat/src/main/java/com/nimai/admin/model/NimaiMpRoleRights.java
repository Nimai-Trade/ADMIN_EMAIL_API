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
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.Size;

import org.hibernate.annotations.DynamicUpdate;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

/**
 *
 * @author Sahadeo
 */
@Entity
@Table(name = "nimai_mp_role_rights")
@EntityListeners(AuditingEntityListener.class)
@DynamicUpdate
public class NimaiMpRoleRights implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "USER_RIGHTS_ID")
    private Integer userRightsId;
    @Size(max = 10)
    @Column(name = "STATUS")
    private String status;
  
    
    @CreatedBy
    @Column(name = "CREATED_BY")
    private String createdBy;
    
    @CreatedDate
    @Column(name = "CREATED_DATE")
    private Date createdDate;
    
    @LastModifiedBy
    @Column(name = "LAST_MODIFIED_BY")
    private String lastModifiedBy;
    
    @LastModifiedDate
    @Column(name = "LAST_MODIFIED_DATE")
    private Date lastModifiedDate;
    
    @JoinColumn(name = "RIGHT_ID", referencedColumnName = "RIGHT_ID")
    @ManyToOne
    private NimaiMRights rightId;
    @JoinColumn(name = "ROLE_ID", referencedColumnName = "ROLE_ID")
    @ManyToOne
    private NimaiMRole roleId;

    public NimaiMpRoleRights() {
    }

    public NimaiMpRoleRights(Integer userRightsId) {
        this.userRightsId = userRightsId;
    }

    public NimaiMpRoleRights(Integer userRightsId, String createdBy, Date createdDate) {
        this.userRightsId = userRightsId;
        this.createdBy = createdBy;
        this.createdDate = createdDate;
    }

    public Integer getUserRightsId() {
        return userRightsId;
    }

    public void setUserRightsId(Integer userRightsId) {
        this.userRightsId = userRightsId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public String getLastModifiedBy() {
        return lastModifiedBy;
    }

    public void setLastModifiedBy(String lastModifiedBy) {
        this.lastModifiedBy = lastModifiedBy;
    }

    public Date getLastModifiedDate() {
        return lastModifiedDate;
    }

    public void setLastModifiedDate(Date lastModifiedDate) {
        this.lastModifiedDate = lastModifiedDate;
    }

    public NimaiMRights getRightId() {
        return rightId;
    }

    public void setRightId(NimaiMRights rightId) {
        this.rightId = rightId;
    }

    public NimaiMRole getRoleId() {
        return roleId;
    }

    public void setRoleId(NimaiMRole roleId) {
        this.roleId = roleId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (userRightsId != null ? userRightsId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof NimaiMpRoleRights)) {
            return false;
        }
        NimaiMpRoleRights other = (NimaiMpRoleRights) object;
        if ((this.userRightsId == null && other.userRightsId != null) || (this.userRightsId != null && !this.userRightsId.equals(other.userRightsId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.raysonjewels.erp.model.NimaiMpRoleRights[ userRightsId=" + userRightsId + " ]";
    }
    
}
