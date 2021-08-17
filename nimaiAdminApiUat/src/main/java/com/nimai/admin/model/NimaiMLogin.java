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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

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
@Table(name = "nimai_m_login")
@EntityListeners(AuditingEntityListener.class)
@DynamicUpdate
public class NimaiMLogin implements Serializable {

    private static final long serialVersionUID = 1L;
 
    @Column(name = "PASSWORD")
    private String password;

    @Column(name = "USER_TYPE")
    private String userType;

    @Column(name = "IS_ACT_PASSED")
    private String isActPassed;

    @Column(name = "FLAG")
    private String flag;

    @Column(name = "STATUS")
    private String status;
    
    
    @CreatedBy
    @Column(name = "INSERTED_BY")
    private String createdBy;
    
    @CreatedDate
    @Column(name = "INSERTED_DATE")
    private Date createdDate;
    
    @LastModifiedBy
    @Column(name = "LAST_MODIFIED_BY")
    private String lastModifiedBy;
    
    @LastModifiedDate
    @Column(name = "MODIFIED_DATE")
    private Date lastModifiedDate;
    
   
    @CreatedDate
    @Column(name = "LAST_LOGIN_TIME")
    private Date lastLoginTime;
    
    @Column(name = "LAST_ACTIVITY_TIME")
    @Temporal(TemporalType.DATE)
    private Date lastActivityTime;
    
    @Column(name = "TOKEN")
    private String token;
    @Column(name = "TOKEN_EXPIRY_DATE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date tokenExpiryDate;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "LOGIN_ID")
    private Long loginId;
    
    @JoinColumn(name = "EMP_CODE", referencedColumnName = "EMP_CODE")
    @ManyToOne
    private NimaiMEmployee empCode;
    
    @JoinColumn(name = "USERID", referencedColumnName = "USERID")
    @ManyToOne
    private NimaiMCustomer userid;
    
    @OneToMany(mappedBy = "loginId")
    private List<NimaiHistoryLogin> nimaiHistoryLoginList;

    public NimaiMLogin() {
    }

    public NimaiMLogin(Long loginId) {
        this.loginId = loginId;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public String getIsActPassed() {
        return isActPassed;
    }

    public void setIsActPassed(String isActPassed) {
        this.isActPassed = isActPassed;
    }

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
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

	public Date getLastLoginTime() {
        return lastLoginTime;
    }

    public void setLastLoginTime(Date lastLoginTime) {
        this.lastLoginTime = lastLoginTime;
    }

    public Date getLastActivityTime() {
        return lastActivityTime;
    }

    public void setLastActivityTime(Date lastActivityTime) {
        this.lastActivityTime = lastActivityTime;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Date getTokenExpiryDate() {
        return tokenExpiryDate;
    }

    public void setTokenExpiryDate(Date tokenExpiryDate) {
        this.tokenExpiryDate = tokenExpiryDate;
    }

    
    public Long getLoginId() {
		return loginId;
	}

	public void setLoginId(Long loginId) {
		this.loginId = loginId;
	}

	public NimaiMEmployee getEmpCode() {
        return empCode;
    }

    public void setEmpCode(NimaiMEmployee empCode) {
        this.empCode = empCode;
    }

    public NimaiMCustomer getUserid() {
        return userid;
    }

    public void setUserid(NimaiMCustomer userid) {
        this.userid = userid;
    }

    public List<NimaiHistoryLogin> getNimaiHistoryLoginList() {
        return nimaiHistoryLoginList;
    }

    public void setNimaiHistoryLoginList(List<NimaiHistoryLogin> nimaiHistoryLoginList) {
        this.nimaiHistoryLoginList = nimaiHistoryLoginList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (loginId != null ? loginId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof NimaiMLogin)) {
            return false;
        }
        NimaiMLogin other = (NimaiMLogin) object;
        if ((this.loginId == null && other.loginId != null) || (this.loginId != null && !this.loginId.equals(other.loginId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.raysonjewels.erp.model.NimaiMLogin[ loginId=" + loginId + " ]";
    }
    
}
