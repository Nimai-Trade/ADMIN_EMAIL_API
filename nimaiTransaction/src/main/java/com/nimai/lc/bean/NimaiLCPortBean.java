package com.nimai.lc.bean;

import java.io.Serializable;

public class NimaiLCPortBean implements Serializable
{
	private static final long serialVersionUID = 1L;
	
	private String countryName;
	private String port;
	private String code;
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
