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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
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
@Table(name = "nimai_m_rights")
@EntityListeners(AuditingEntityListener.class)
@DynamicUpdate
public class NimaiMRights implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "RIGHT_ID")
    private Integer rightId;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "RIGHT_NAME")
    private String rightName;
    @Size(max = 10)
    @Column(name = "RIGHT_STATUS")
    private String rightStatus;
    
    @CreatedBy
    @Column(name = "CREATED_BY")
    private String createdBy;
    
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "RIGHT_SHORT_NAME")
    private String rightShortName;
    
    @CreatedDate
    @Column(name = "CREATED_DATE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdDate;
    @OneToMany(mappedBy = "rightId")
    private List<NimaiMpRoleRights> nimaiMpRoleRightsList;

    public NimaiMRights() {
    }

    public NimaiMRights(Integer rightId) {
        this.rightId = rightId;
    }

    public NimaiMRights(Integer rightId, String rightName, Date createdDate) {
        this.rightId = rightId;
        this.rightName = rightName;
        this.createdDate = createdDate;
    }

    public Integer getRightId() {
        return rightId;
    }

    public void setRightId(Integer rightId) {
        this.rightId = rightId;
    }

    public String getRightName() {
        return rightName;
    }

    public void setRightName(String rightName) {
        this.rightName = rightName;
    }

    public String getRightStatus() {
        return rightStatus;
    }

    public void setRightStatus(String rightStatus) {
        this.rightStatus = rightStatus;
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
    
    

    public String getRightShortName() {
		return rightShortName;
	}

	public void setRightShortName(String rightShortName) {
		this.rightShortName = rightShortName;
	}

	@Override
    public int hashCode() {
        int hash = 0;
        hash += (rightId != null ? rightId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof NimaiMRights)) {
            return false;
        }
        NimaiMRights other = (NimaiMRights) object;
        if ((this.rightId == null && other.rightId != null) || (this.rightId != null && !this.rightId.equals(other.rightId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.raysonjewels.erp.model.NimaiMRights[ rightId=" + rightId + " ]";
    }
    
}
