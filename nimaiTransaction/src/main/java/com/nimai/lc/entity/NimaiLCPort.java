package com.nimai.lc.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="nimai_m_port")
public class NimaiLCPort 
{
	@Id
	@Column(name="ID") 
	private Integer id;
	
	@Column(name="COUNTRY") 
	private String countryName;
	
	@Column(name="PORT") 
	private String port;
	
	@Column(name="CODE") 
	private String code;
	
	
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getCountryName() {
		return countryName;
	}
	public void setCountryName(String countryName) {
		this.countryName = countryName;
	}
	public String getPort() {
		return port;
	}
	public void setPort(String port) {
		this.port = port;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	
	
}
