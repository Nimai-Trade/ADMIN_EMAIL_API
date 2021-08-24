package com.nimai.lc.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="NIMAI_M_COUNTRY")
public class Country {
	@Id
	@Column(name="id") 
	private String id;
	
	@Column(name="country_name") 
	private String countryName;
	
	@Column(name="country_code") 
	private String countryCode;

}
