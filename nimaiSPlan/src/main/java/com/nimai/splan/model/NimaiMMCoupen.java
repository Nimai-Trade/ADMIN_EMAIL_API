package com.nimai.splan.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedStoredProcedureQueries;
import javax.persistence.NamedStoredProcedureQuery;
import javax.persistence.OneToMany;
import javax.persistence.ParameterMode;
import javax.persistence.StoredProcedureParameter;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;




@Entity
@Table(name="NIMAI_MM_COUPEN")
@NamedStoredProcedureQueries({
	        @NamedStoredProcedureQuery(name = "VALIDATE_COUPEN", procedureName = "VALIDATE_COUPEN", parameters = {
			@StoredProcedureParameter(mode = ParameterMode.IN, name = "inp_coupen_id", type = String.class),
			@StoredProcedureParameter(mode = ParameterMode.IN, name = "inp_country_name", type = String.class),
			@StoredProcedureParameter(mode = ParameterMode.IN, name = "inp_subsciption_plan", type = String.class),
			@StoredProcedureParameter(mode = ParameterMode.IN, name = "inp_user_type", type = String.class),
			@StoredProcedureParameter(mode = ParameterMode.OUT, name = "out_coupen_status", type = String.class),
			@StoredProcedureParameter(mode = ParameterMode.OUT, name = "out_total_amount", type = float.class),
			
    })
})
public class NimaiMMCoupen implements Serializable{
	
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(name = "COUPEN_ID")
	private String coupenId;
	
	@Column(name="USER_ID")
	private String userId;
	
	@Column(name = "COUPEN_NAME")
	private String coupenName;
	
	@Column(name = "COUPEN_VALUE")
	private String coupenValue;
	
	@Column(name = "FLAT_DISCOUNT")
	private Integer flatDiscount;
	
	@Column(name = "PERCENT_DISCOUNT")
	private Integer percentDiscount;
	
	@Column(name = "STATUS")
	private String status;
	
	@Column(name = "START_DATE")
	@Temporal(value=TemporalType.TIMESTAMP)
	private Date startDate;
	
	@Column(name = "EXPIRY_DATE")
	@Temporal(value=TemporalType.TIMESTAMP)
	private Date expiryDate;
	
	@Column(name = "INSERTED_BY")
	private String insertedBy;
	
	@Column(name = "INSERTED_DATE")
	@Temporal(value=TemporalType.TIMESTAMP)
	private Date insertedDate;
	
	@Column(name = "MODIFIED_BY")
	private String modifiedBy;
	
	@Column(name = "MODIFIED_DATE")
	@Temporal(value=TemporalType.TIMESTAMP)
	private Date modifiedDate;
	
	@Column(name = "coupen_for")
	private String coupenfor;

	
	public String getCoupenfor() {
		return coupenfor;
	}

	public void setCoupenfor(String coupenfor) {
		this.coupenfor = coupenfor;
	}

	public String getCoupenId() {
		return coupenId;
	}

	public void setCoupenId(String coupenId) {
		this.coupenId = coupenId;
	}

	public String getCoupenName() {
		return coupenName;
	}

	public void setCoupenName(String coupenName) {
		this.coupenName = coupenName;
	}

	public String getCoupenValue() {
		return coupenValue;
	}

	public void setCoupenValue(String coupenValue) {
		this.coupenValue = coupenValue;
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

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
	public String getInsertedBy() {
		return insertedBy;
	}

	public void setInsertedBy(String insertedBy) {
		this.insertedBy = insertedBy;
	}

	
	public String getModifiedBy() {
		return modifiedBy;
	}

	public void setModifiedBy(String modifiedBy) {
		this.modifiedBy = modifiedBy;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getExpiryDate() {
		return expiryDate;
	}

	public void setExpiryDate(Date expiryDate) {
		this.expiryDate = expiryDate;
	}

	public Date getInsertedDate() {
		return insertedDate;
	}

	public void setInsertedDate(Date insertedDate) {
		this.insertedDate = insertedDate;
	}

	public Date getModifiedDate() {
		return modifiedDate;
	}

	public void setModifiedDate(Date modifiedDate) {
		this.modifiedDate = modifiedDate;
	}

	
	@OneToMany(mappedBy = "nimaicoupen",cascade = {CascadeType.ALL})
	private List<NimaiFCoupenCountry> coupencountry;

	public List<NimaiFCoupenCountry> getCoupencountry() {
		return coupencountry;
	}

	public void setCoupencountry(List<NimaiFCoupenCountry> coupencountry) {
		this.coupencountry = coupencountry;
	}

	@OneToMany(mappedBy = "nimaisubsciption",cascade = {CascadeType.ALL})
	private List<NimaiFCoupenSubscription> coupensubscription;

	public List<NimaiFCoupenSubscription> getCoupensubscription() {
		return coupensubscription;
	}

	public void setCoupensubscription(List<NimaiFCoupenSubscription> coupensubscription) {
		this.coupensubscription = coupensubscription;
	}



	

}
