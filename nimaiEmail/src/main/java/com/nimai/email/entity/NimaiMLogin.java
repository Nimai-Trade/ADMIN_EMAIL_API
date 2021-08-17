package com.nimai.email.entity;


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

import com.fasterxml.jackson.annotation.JsonBackReference;


/**
 *
 * @author sahadeo.naik
 */
@Entity
@Table(name = "NIMAI_M_LOGIN")
@NamedQueries({ @NamedQuery(name = "NimaiMLogin.findAll", query = "SELECT n FROM NimaiMLogin n") })
public class NimaiMLogin implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@Basic(optional = false)
	@NotNull
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "LOGIN_ID")
	private Long loginId;

	 @JsonBackReference
	@JoinColumn(name = "USERID", referencedColumnName = "USERID")
	@ManyToOne(optional = false)
	private NimaiClient userid;


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

	@Column(name = "TOKEN")
	private String token;

	@Column(name = "INSERTED_DATE")
	@Temporal(TemporalType.TIMESTAMP)
	private Date insertedDate;

	@Column(name = "MODIFIED_DATE")
	@Temporal(TemporalType.TIMESTAMP)
	private Date modifiedDate;

	@Column(name = "LAST_LOGIN_TIME")
	@Temporal(TemporalType.TIMESTAMP)
	private Date lastLoginTime;

	@Column(name = "LAST_ACTIVITY_TIME")
	@Temporal(TemporalType.TIMESTAMP)
	private Date lastActivityTime;

	@Column(name = "TOKEN_EXPIRY_DATE")
	@Temporal(TemporalType.TIMESTAMP)
	private Date tokenExpiryDate;

    @JoinColumn(name = "EMP_CODE", referencedColumnName = "EMP_CODE")
    @ManyToOne
    private NimaiMEmployee empCode;
    
    
    

	public NimaiMEmployee getEmpCode() {
		return empCode;
	}

	public void setEmpCode(NimaiMEmployee empCode) {
		this.empCode = empCode;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public NimaiMLogin() {
	}

	public Long getLoginId() {
		return loginId;
	}

	public void setLoginId(Long loginId) {
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

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public NimaiClient getUserid() {
		return userid;
	}

	public void setUserid(NimaiClient userid) {
		this.userid = userid;
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
		if ((this.loginId == null && other.loginId != null)
				|| (this.loginId != null && !this.loginId.equals(other.loginId))) {
			return false;
		}
		return true;
	}

	public Date getTokenExpiryDate() {
		return tokenExpiryDate;
	}

	public void setTokenExpiryDate(Date tokenExpiryDate) {
		this.tokenExpiryDate = tokenExpiryDate;
	}


	
}
