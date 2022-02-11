package com.nimai.splan.model;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="NIMAI_F_COUPEN_COUNTRY_DISCOUNT")
public class NimaiFcoupenCountryDiscount implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(name="USER_ID")
	private String userId; 
	
	@Column(name = "COUNTRY_ID")	
	private String country_Id;
	
	@Column(name = "FLAT_DISCOUNT")
	private Integer flatDiscount;
	
	@Column(name = "PERCENT_DISCOUNT")
	private Integer percentDiscount;

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getCountry_Id() {
		return country_Id;
	}

	public void setCountry_Id(String country_Id) {
		this.country_Id = country_Id;
	}

	public Integer getFlatDiscount() {
		return flatDiscount;
	}

	public void setFlatDiscount(Integer flatDiscount) {
		this.flatDiscount = flatDiscount;
	}

	public Integer getPercentDiscount() {
		return percentDiscount;
	}

	public void setPercentDiscount(Integer percentDiscount) {
		this.percentDiscount = percentDiscount;
	}




	@JoinColumn(name = "COUNTRY_ID",referencedColumnName = "COUNTRY_ID",insertable=false, updatable=false )
	@ManyToOne(cascade = CascadeType.ALL,fetch=FetchType.LAZY)
	private NimaiFCoupenCountry coupencountry;

	public NimaiFCoupenCountry getCoupencountry() {
		return coupencountry;
	}

	public void setCoupencountry(NimaiFCoupenCountry coupencountry) {
		this.coupencountry = coupencountry;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	
	
	
}

