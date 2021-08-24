package com.nimai.lc.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;


@Entity
@Table(name="temp_refer_earnings")
public class ReferEarnings {


	@Id
	@Column(name="id")
	private int id;
	
	@Column(name="earning")
	private Double earning;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Double getEarning() {
		return earning;
	}

	public void setEarning(Double earning) {
		this.earning = earning;
	}

	
	
	
	
	
	
	

}
