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
@Table(name = "nimai_m_employee")
@EntityListeners(AuditingEntityListener.class)
@DynamicUpdate
public class NimaiMEmployee implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "EMP_ID")
    private Integer empId;

    @Column(name = "EMP_CODE")
    private String empCode;
    
    @Column(name = "EMP_FIRST_NAME")
    private String empName;
    
    @Column(name = "EMP_LAST_NAME")
    private String empLastName;
    
    
    @Column(name = "COUNTRY_EXT")
    private String countryExt;
    
    @Column(name = "EMP_MOBILE")
    private String empMobile;

    @Column(name = "EMP_EMAIL")
    private String empEmail;

    @Column(name = "DEPARTMENT")
    private String department;
    
    @Column(name = "DESIGNATION")
    private String designation;
    
    @Column(name = "REPORTING_MANAGER")
    private String reportingManager;
 
    @Column(name = "COUNTRY")
    private String country;
    
    @Column(name = "STATUS")
    private String status;
  
    @Column(name = "ADDRESS1")
    private String address1;

    @Column(name = "ADDRESS2")
    private String address2;
    
    
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

  
    @OneToMany(mappedBy = "empCode")
    private List<NimaiMLogin> nimaiMLoginList;
    
    @OneToMany(mappedBy = "empCode")
    private List<NimaiMpUserRole> nimaiMpUserRoleList;

    public NimaiMEmployee() {
    }

    public NimaiMEmployee(Integer empId) {
        this.empId = empId;
    }

    public Integer getEmpId() {
        return empId;
    }

    public void setEmpId(Integer empId) {
        this.empId = empId;
    }

    public String getEmpCode() {
        return empCode;
    }

    public void setEmpCode(String empCode) {
        this.empCode = empCode;
    }

    public String getEmpName() {
        return empName;
    }

    public void setEmpName(String empName) {
        this.empName = empName;
    }

    public String getEmpMobile() {
        return empMobile;
    }

    public void setEmpMobile(String empMobile) {
        this.empMobile = empMobile;
    }

    public String getEmpEmail() {
        return empEmail;
    }

    public void setEmpEmail(String empEmail) {
        this.empEmail = empEmail;
    }


    public String getEmpLastName() {
		return empLastName;
	}

	public void setEmpLastName(String empLastName) {
		this.empLastName = empLastName;
	}

	public String getDepartment() {
		return department;
	}

	public void setDepartment(String department) {
		this.department = department;
	}

	public String getDesignation() {
		return designation;
	}

	public void setDesignation(String designation) {
		this.designation = designation;
	}

	public String getReportingManager() {
		return reportingManager;
	}

	public void setReportingManager(String reportingManager) {
		this.reportingManager = reportingManager;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getAddress1() {
        return address1;
    }

    public void setAddress1(String address1) {
        this.address1 = address1;
    }

    public String getAddress2() {
        return address2;
    }

    public void setAddress2(String address2) {
        this.address2 = address2;
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

    public List<NimaiMLogin> getNimaiMLoginList() {
        return nimaiMLoginList;
    }

    public void setNimaiMLoginList(List<NimaiMLogin> nimaiMLoginList) {
        this.nimaiMLoginList = nimaiMLoginList;
    }

    public List<NimaiMpUserRole> getNimaiMpUserRoleList() {
        return nimaiMpUserRoleList;
    }

    public void setNimaiMpUserRoleList(List<NimaiMpUserRole> nimaiMpUserRoleList) {
        this.nimaiMpUserRoleList = nimaiMpUserRoleList;
    }
    
    

    public String getCountryExt() {
		return countryExt;
	}

	public void setCountryExt(String countryExt) {
		this.countryExt = countryExt;
	}

	@Override
    public int hashCode() {
        int hash = 0;
        hash += (empId != null ? empId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof NimaiMEmployee)) {
            return false;
        }
        NimaiMEmployee other = (NimaiMEmployee) object;
        if ((this.empId == null && other.empId != null) || (this.empId != null && !this.empId.equals(other.empId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.raysonjewels.erp.model.NimaiMEmployee[ empId=" + empId + " ]";
    }
    
}
