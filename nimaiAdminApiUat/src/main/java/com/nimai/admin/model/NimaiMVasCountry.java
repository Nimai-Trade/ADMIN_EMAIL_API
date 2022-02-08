package com.nimai.admin.model;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.DynamicUpdate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity
@Table(name = "nimai_m_vascoutry")
@EntityListeners(AuditingEntityListener.class)
@DynamicUpdate
public class NimaiMVasCountry {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Basic(optional = false)
	@Column(name = "vas_plancountry_id")
	private Integer id;
	
	

	@Column(name="vasid")
	private int vasId;
	

	
	@Column(name="vas_country")
	private String vasCountry;



	public Integer getId() {
		return id;
	}



	public void setId(Integer id) {
		this.id = id;
	}



	public int getVasId() {
		return vasId;
	}



	public void setVasId(int vasId) {
		this.vasId = vasId;
	}



	public String getVasCountry() {
		return vasCountry;
	}



	public void setVasCountry(String vasCountry) {
		this.vasCountry = vasCountry;
	}
	
	
	
	
	
	
	
}
