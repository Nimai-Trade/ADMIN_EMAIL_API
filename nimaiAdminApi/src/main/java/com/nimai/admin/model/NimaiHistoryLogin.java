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
@Table(name = "nimai_history_login")
@NamedQueries({
    @NamedQuery(name = "NimaiHistoryLogin.findAll", query = "SELECT n FROM NimaiHistoryLogin n")})
public class NimaiHistoryLogin implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "LOGIN_HISTORY_ID")
    private Integer loginHistoryId;
    @Basic(optional = false)
    @Size(min = 1, max = 20)
    @Column(name = "USERID")
    private String userid;
    @Size(max = 200)
    @Column(name = "PASSWORD")
    private String password;
    @Size(max = 20)
    @Column(name = "USER_TYPE")
    private String userType;
    @Size(max = 20)
    @Column(name = "IS_ACT_PASSED")
    private String isActPassed;
    @Size(max = 20)
    @Column(name = "FLAG")
    private String flag;
    @Size(max = 20)
    @Column(name = "STATUS")
    private String status;
    @Basic(optional = false)
    @NotNull
    @Column(name = "INSERTED_DATE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date insertedDate;
    @Column(name = "MODIFIED_DATE")
    @Temporal(TemporalType.DATE)
    private Date modifiedDate;
    @Column(name = "LAST_LOGIN_TIME")
    @Temporal(TemporalType.DATE)
    private Date lastLoginTime;
    @Column(name = "LAST_ACTIVITY_TIME")
    @Temporal(TemporalType.DATE)
    private Date lastActivityTime;
    @Size(max = 20)
    @Column(name = "TOKEN")
    private String token;
    @Column(name = "TOKEN_EXPIRY_DATE")
    @Temporal(TemporalType.DATE)
    private Date tokenExpiryDate;
    @JoinColumn(name = "LOGIN_ID", referencedColumnName = "LOGIN_ID")
    @ManyToOne
    private NimaiMLogin loginId;

    public NimaiHistoryLogin() {
    }

    public NimaiHistoryLogin(Integer loginHistoryId) {
        this.loginHistoryId = loginHistoryId;
    }

    public NimaiHistoryLogin(Integer loginHistoryId, String userid, Date insertedDate) {
        this.loginHistoryId = loginHistoryId;
        this.userid = userid;
        this.insertedDate = insertedDate;
    }

    public Integer getLoginHistoryId() {
        return loginHistoryId;
    }

    public void setLoginHistoryId(Integer loginHistoryId) {
        this.loginHistoryId = loginHistoryId;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
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

    public NimaiMLogin getLoginId() {
        return loginId;
    }

    public void setLoginId(NimaiMLogin loginId) {
        this.loginId = loginId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (loginHistoryId != null ? loginHistoryId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof NimaiHistoryLogin)) {
            return false;
        }
        NimaiHistoryLogin other = (NimaiHistoryLogin) object;
        if ((this.loginHistoryId == null && other.loginHistoryId != null) || (this.loginHistoryId != null && !this.loginHistoryId.equals(other.loginHistoryId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.raysonjewels.erp.model.NimaiHistoryLogin[ loginHistoryId=" + loginHistoryId + " ]";
    }
    
	public int compareTo(NimaiHistoryLogin ob) {
		return loginHistoryId.compareTo(ob.getLoginHistoryId());
	}
    
}
