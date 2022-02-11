package com.nimai.splan.model;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name="NIMAI_F_COUPEN_COUNTRY")
public class NimaiFCoupenCountry implements Serializable{
	
private static final long serialVersionUID = 1L;
		
@Id
@Column(name = "COUNTRY_ID")	
private String country_Id;

@Column(name = "COUPEN_ID")
private String coupenId;
	
@Column(name="USER_ID")
private String userId; 
	
@Column(name = "COUNTRY_NAME")
private String country_Name;

public String getCountry_Id() {
	return country_Id;
}

public void setCountry_Id(String country_Id) {
	this.country_Id = country_Id;
}

public String getCountry_Name() {
	return country_Name;
}

public void setCountry_Name(String country_Name) {
	this.country_Name = country_Name;
}

public String getUserId() {
	return userId;
}

public void setUserId(String userId) {
	this.userId = userId;
}



public String getCoupenId() {
	return coupenId;
}

public void setCoupenId(String coupenId) {
	this.coupenId = coupenId;
}



@JoinColumn(name = "COUPEN_ID",referencedColumnName = "COUPEN_ID",insertable=false, updatable=false )
@ManyToOne(cascade = CascadeType.ALL,fetch=FetchType.LAZY )
private NimaiMMCoupen nimaicoupen;

public NimaiMMCoupen getNimaicoupen() {
	return nimaicoupen;
}

public void setNimaicoupen(NimaiMMCoupen nimaicoupen) {
	this.nimaicoupen = nimaicoupen;
}


@OneToMany(mappedBy = "coupencountry",cascade = {CascadeType.ALL})
private List<NimaiFcoupenCountryDiscount> coupencountrydiscount;
public void setCoupencountrydiscount(List<NimaiFcoupenCountryDiscount> coupencountrydiscount) {
	this.coupencountrydiscount = coupencountrydiscount;
}

public static long getSerialversionuid() {
	return serialVersionUID;
}

}
