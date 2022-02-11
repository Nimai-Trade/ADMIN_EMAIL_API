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
@Table(name="NIMAI_F_COUPEN_SUBCRIPTION")
public class NimaiFCoupenSubscription implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(name = "SUBCRIPTION_ID")
	private String subsciptionId;
	
	@Column(name="USER_ID")
	private String userId; 
	
	@Column(name = "COUPEN_ID")
	private String coupenId;
	
	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	@Column(name = "SUBSCRIPTION_PLAN")
	private String subscriptionPlan;

	public String getSubsciptionId() {
		return subsciptionId;
	}

	public void setSubsciptionId(String subsciptionId) {
		this.subsciptionId = subsciptionId;
	}

	public String getSubscriptionPlan() {
		return subscriptionPlan;
	}

	public void setSubscriptionPlan(String subscriptionPlan) {
		this.subscriptionPlan = subscriptionPlan;
	}
	
	public String getCoupenId() {
		return coupenId;
	}

	public void setCoupenId(String coupenId) {
		this.coupenId = coupenId;
	}



	@JoinColumn(name = "COUPEN_ID",referencedColumnName = "COUPEN_ID",insertable=false, updatable=false)
	@ManyToOne(cascade = CascadeType.ALL,fetch=FetchType.LAZY )
		private NimaiMMCoupen nimaisubsciption;

	public NimaiMMCoupen getNimaisubsciption() {
		return nimaisubsciption;
	}

	public void setNimaisubsciption(NimaiMMCoupen nimaisubsciption) {
		this.nimaisubsciption = nimaisubsciption;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	
	
	

}
