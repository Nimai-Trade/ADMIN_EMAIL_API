/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nimai.admin.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.annotations.DynamicUpdate;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

/**
 *
 * @author Sahadeo
 */
@Entity
@Table(name = "nimai_m_role")
@EntityListeners(AuditingEntityListener.class)
@DynamicUpdate
public class NimaiMRole implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ROLE_ID")
    private Integer roleId;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "ROLE_NAME")
    private String roleName;
    
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "ROLE_SHORT_NAME")
    private String roleShortName;
    
    @Size(max = 10)
    @Column(name = "ROLE_STATUS")
    private String roleStatus;
    @CreatedBy
    @Column(name = "CREATED_BY")
    private String createdBy;
    
    @CreatedDate
    @Column(name = "CREATED_DATE")
    private Date createdDate;
    
    @OneToMany(mappedBy = "roleId")
    private List<NimaiMpRoleRights> nimaiMpRoleRightsList;
    @OneToMany(mappedBy = "roleId")
    private List<NimaiMpUserRole> nimaiMpUserRoleList;

    public NimaiMRole() {
    }

    public NimaiMRole(Integer roleId) {
        this.roleId = roleId;
    }

    public NimaiMRole(Integer roleId, String roleName, Date createdDate) {
        this.roleId = roleId;
        this.roleName = roleName;
        this.createdDate = createdDate;
    }

    public Integer getRoleId() {
        return roleId;
    }

    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public String getRoleStatus() {
        return roleStatus;
    }

    public void setRoleStatus(String roleStatus) {
        this.roleStatus = roleStatus;
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

    public List<NimaiMpRoleRights> getNimaiMpRoleRightsList() {
        return nimaiMpRoleRightsList;
    }

    public void setNimaiMpRoleRightsList(List<NimaiMpRoleRights> nimaiMpRoleRightsList) {
        this.nimaiMpRoleRightsList = nimaiMpRoleRightsList;
    }

    public List<NimaiMpUserRole> getNimaiMpUserRoleList() {
        return nimaiMpUserRoleList;
    }

    public void setNimaiMpUserRoleList(List<NimaiMpUserRole> nimaiMpUserRoleList) {
        this.nimaiMpUserRoleList = nimaiMpUserRoleList;
    }
    
    

    public String getRoleShortName() {
		return roleShortName;
	}

	public void setRoleShortName(String roleShortName) {
		this.roleShortName = roleShortName;
	}

	@Override
    public int hashCode() {
        int hash = 0;
        hash += (roleId != null ? roleId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof NimaiMRole)) {
            return false;
        }
        NimaiMRole other = (NimaiMRole) object;
        if ((this.roleId == null && other.roleId != null) || (this.roleId != null && !this.roleId.equals(other.roleId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.raysonjewels.erp.model.NimaiMRole[ roleId=" + roleId + " ]";
    }

    
}
