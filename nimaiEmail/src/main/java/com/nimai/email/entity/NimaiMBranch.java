package com.nimai.email.entity;

import java.util.Date;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.DynamicUpdate;

@Entity
@DynamicUpdate
@Table(name = "nimai_m_branch")
public class NimaiMBranch {

	@Id
	@Basic(optional = false)
	@NotNull
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private int brId;

	@Column(name = "batch_id")
	private String batchId;

	@Column(name = "employee_id")
	private String employeeId;

	@Column(name = "isInvalidCaptcha")
	private String invalidCaptcha;

	
	@Column(name = "email_id")
	private String emailId;

	@Column(name = "employee_name")
	private String employeeName;

	@Column(name = "passcode_value")
	private String passcodeValue;

	@Column(name = "token_expiry_time")
	private Date expryTime;
	
	@Column(name="passcode_count")
	private Integer passcodeCount;
	
	@Column(name="token")
	private String token;

	@Column(name = "inserted_by")
	private String insertBy;

	@Column(name = "inserted_date")
	private Date insertTime;

	@Column(name = "modified_by")
	private String modifyBy;

	@Column(name = "modified_date")
	private Date modifyTime;
	
//	@JoinColumn(name = "USERID", referencedColumnName="USERID")
//	@ManyToOne(optional = false)
//	private NimaiClient userid;
	
	@Column(name="USERID")
	private String userid;
	
	
	



	public Integer getPasscodeCount() {
		return passcodeCount;
	}

	public void setPasscodeCount(Integer passcodeCount) {
		this.passcodeCount = passcodeCount;
	}

	/**
	 * @return the userid
	 */
	public String getUserid() {
		return userid;
	}

	/**
	 * @param userid the userid to set
	 */
	public void setUserid(String userid) {
		this.userid = userid;
	}



	public String getInvalidCaptcha() {
		return invalidCaptcha;
	}

	public void setInvalidCaptcha(String invalidCaptcha) {
		this.invalidCaptcha = invalidCaptcha;
	}

	public int getBrId() {
		return brId;
	}

	public void setBrId(int brId) {
		this.brId = brId;
	}

	/**
	 * @return the batchId
	 */
	public String getBatchId() {
		return batchId;
	}

	/**
	 * @param batchId the batchId to set
	 */
	public void setBatchId(String batchId) {
		this.batchId = batchId;
	}

	/**
	 * @return the employeeId
	 */
	public String getEmployeeId() {
		return employeeId;
	}

	/**
	 * @param employeeId the employeeId to set
	 */
	public void setEmployeeId(String employeeId) {
		this.employeeId = employeeId;
	}

	/**
	 * @return the emailId
	 */
	public String getEmailId() {
		return emailId;
	}

	/**
	 * @param emailId the emailId to set
	 */
	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}

	/**
	 * @return the employeeName
	 */
	public String getEmployeeName() {
		return employeeName;
	}

	/**
	 * @param employeeName the employeeName to set
	 */
	public void setEmployeeName(String employeeName) {
		this.employeeName = employeeName;
	}

	/**
	 * @return the passcodeValue
	 */
	public String getPasscodeValue() {
		return passcodeValue;
	}

	/**
	 * @param passcodeValue the passcodeValue to set
	 */
	public void setPasscodeValue(String passcodeValue) {
		this.passcodeValue = passcodeValue;
	}

	/**
	 * @return the expryTime
	 */
	public Date getExpryTime() {
		return expryTime;
	}

	/**
	 * @param expryTime the expryTime to set
	 */
	public void setExpryTime(Date expryTime) {
		this.expryTime = expryTime;
	}

	/**
	 * @return the token
	 */
	public String getToken() {
		return token;
	}

	/**
	 * @param token the token to set
	 */
	public void setToken(String token) {
		this.token = token;
	}

	/**
	 * @return the insertBy
	 */
	public String getInsertBy() {
		return insertBy;
	}

	/**
	 * @param insertBy the insertBy to set
	 */
	public void setInsertBy(String insertBy) {
		this.insertBy = insertBy;
	}

	/**
	 * @return the insertTime
	 */
	public Date getInsertTime() {
		return insertTime;
	}

	/**
	 * @param insertTime the insertTime to set
	 */
	public void setInsertTime(Date insertTime) {
		this.insertTime = insertTime;
	}

	/**
	 * @return the modifyBy
	 */
	public String getModifyBy() {
		return modifyBy;
	}

	/**
	 * @param modifyBy the modifyBy to set
	 */
	public void setModifyBy(String modifyBy) {
		this.modifyBy = modifyBy;
	}

	/**
	 * @return the modifyTime
	 */
	public Date getModifyTime() {
		return modifyTime;
	}

	/**
	 * @param modifyTime the modifyTime to set
	 */
	public void setModifyTime(Date modifyTime) {
		this.modifyTime = modifyTime;
	}

	@Override
	public String toString() {
		return "NimaiMBranch [brId=" + brId + ", batchId=" + batchId + ", employeeId=" + employeeId + ", emailId="
				+ emailId + ", employeeName=" + employeeName + ", passcodeValue=" + passcodeValue + ", expryTime="
				+ expryTime + ", passcodeCount=" + passcodeCount + ", token=" + token + ", insertBy=" + insertBy
				+ ", insertTime=" + insertTime + ", modifyBy=" + modifyBy + ", modifyTime=" + modifyTime + ", userid="
				+ userid + "]";
	}






	
	
	
}
