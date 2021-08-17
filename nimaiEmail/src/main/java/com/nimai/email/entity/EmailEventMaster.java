package com.nimai.email.entity;

import java.math.BigDecimal;

import java.util.HashSet;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**
 * EmailEventMaster generated
 */
@Entity
@Table(name = "NIMAI_EMAIL_M_EVENT")
//@SequenceGenerator(sequenceName = "SEQ_EMAIL_EVENT_ID", name = "SEQ_EMAIL_EVENT_ID", allocationSize = 1)
public class EmailEventMaster implements java.io.Serializable {

	private BigDecimal emailEventId;
	private String emailEventName;
	private Set<EmailComponentMaster> emailComponentMasters = new HashSet<EmailComponentMaster>(0);

	public EmailEventMaster() {
	}

	public EmailEventMaster(BigDecimal emailEventId) {
		this.emailEventId = emailEventId;
	}

	public EmailEventMaster(BigDecimal emailEventId, String emailEventName,
			Set<EmailComponentMaster> emailComponentMasters) {
		this.emailEventId = emailEventId;
		this.emailEventName = emailEventName;
		this.emailComponentMasters = emailComponentMasters;
	}

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "EMAIL_EVENT_ID", unique = true, nullable = false, scale = 0)
	public BigDecimal getEmailEventId() {
		return this.emailEventId;
	}

	public void setEmailEventId(BigDecimal emailEventId) {
		this.emailEventId = emailEventId;
	}

	@Column(name = "EMAIL_EVENT_NAME")
	public String getEmailEventName() {
		return this.emailEventName;
	}

	public void setEmailEventName(String emailEventName) {
		this.emailEventName = emailEventName;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "emailEventMaster")
	public Set<EmailComponentMaster> getEmailComponentMasters() {
		return this.emailComponentMasters;
	}

	public void setEmailComponentMasters(Set<EmailComponentMaster> emailComponentMasters) {
		this.emailComponentMasters = emailComponentMasters;
	}

}
