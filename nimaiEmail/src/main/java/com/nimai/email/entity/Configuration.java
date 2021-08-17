package com.nimai.email.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "NIMAI_EMAIL_CONFIG")
public class Configuration implements java.io.Serializable {

	private short configurationid;
	private String type;
	private String description;
	private String key;
	private String valuedescription;
	private Byte valuetype;
	private String valuestring;
	private Integer valuenumber;
	private Integer createdby;
	private Date createdon;
	private Integer modifiedby;
	private Date modifiedon;

	public Configuration() {
	}

	public Configuration(short configurationid, Date createdon) {
		this.configurationid = configurationid;
		this.createdon = createdon;
	}

	public Configuration(short configurationid, String type, String description, String key, String valuedescription,
			Byte valuetype, String valuestring, Integer valuenumber, Integer createdby, Date createdon,
			Integer modifiedby, Date modifiedon) {
		this.configurationid = configurationid;
		this.type = type;
		this.description = description;
		this.key = key;
		this.valuedescription = valuedescription;
		this.valuetype = valuetype;
		this.valuestring = valuestring;
		this.valuenumber = valuenumber;
		this.createdby = createdby;
		this.createdon = createdon;
		this.modifiedby = modifiedby;
		this.modifiedon = modifiedon;
	}

	@Id
	@Column(name = "CONFIGURATIONID", unique = true, nullable = false)
	public short getConfigurationid() {
		return this.configurationid;
	}

	public void setConfigurationid(short configurationid) {
		this.configurationid = configurationid;
	}

	@Column(name = "TYPE", length = 50)
	public String getType() {
		return this.type;
	}

	public void setType(String type) {
		this.type = type;
	}

	@Column(name = "DESCRIPTION", length = 100)
	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Column(name = "KEY", length = 50)
	public String getKey() {
		return this.key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	@Column(name = "VALUEDESCRIPTION", length = 100)
	public String getValuedescription() {
		return this.valuedescription;
	}

	public void setValuedescription(String valuedescription) {
		this.valuedescription = valuedescription;
	}

	@Column(name = "VALUETYPE")
	public Byte getValuetype() {
		return this.valuetype;
	}

	public void setValuetype(Byte valuetype) {
		this.valuetype = valuetype;
	}

	@Column(name = "VALUESTRING", length = 200)
	public String getValuestring() {
		return this.valuestring;
	}

	public void setValuestring(String valuestring) {
		this.valuestring = valuestring;
	}

	@Column(name = "VALUENUMBER")
	public Integer getValuenumber() {
		return this.valuenumber;
	}

	public void setValuenumber(Integer valuenumber) {
		this.valuenumber = valuenumber;
	}

	@Column(name = "CREATEDBY")
	public Integer getCreatedby() {
		return this.createdby;
	}

	public void setCreatedby(Integer createdby) {
		this.createdby = createdby;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "CREATEDON", nullable = false, length = 19)
	public Date getCreatedon() {
		return this.createdon;
	}

	public void setCreatedon(Date createdon) {
		this.createdon = createdon;
	}

	@Column(name = "MODIFIEDBY")
	public Integer getModifiedby() {
		return this.modifiedby;
	}

	public void setModifiedby(Integer modifiedby) {
		this.modifiedby = modifiedby;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "MODIFIEDON", length = 19)
	public Date getModifiedon() {
		return this.modifiedon;
	}

	public void setModifiedon(Date modifiedon) {
		this.modifiedon = modifiedon;
	}

}
