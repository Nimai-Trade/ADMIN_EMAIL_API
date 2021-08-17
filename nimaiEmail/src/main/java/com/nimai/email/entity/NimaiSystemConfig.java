package com.nimai.email.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="nimai_system_config")
public class NimaiSystemConfig {

	
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id")
	private int id;
	
	@Column(name="system_config_entity")
	private String systenEntity;
	
	@Column(name="system_config_entity_value")
	private String systemEntityValue;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getSystenEntity() {
		return systenEntity;
	}

	public void setSystenEntity(String systenEntity) {
		this.systenEntity = systenEntity;
	}

	public String getSystemEntityValue() {
		return systemEntityValue;
	}

	public void setSystemEntityValue(String systemEntityValue) {
		this.systemEntityValue = systemEntityValue;
	}
	
	
	
	
	
	
}
