package com.nimai.admin.model;

import javax.persistence.Column;
import javax.persistence.Basic;
import javax.persistence.GenerationType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import javax.persistence.EntityListeners;
import javax.persistence.Table;
import javax.persistence.Entity;

@Entity
@Table(name = "bank_rating")
@EntityListeners({ AuditingEntityListener.class })
@DynamicUpdate
public class BankRatingModel
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID")
    private Integer id;
    @Column(name = "BANK_USERID")
    private String bankUserid;
    @Column(name = "RATING")
    private String rating;
    
    public Integer getId() {
        return this.id;
    }
    
    public void setId(final Integer id) {
        this.id = id;
    }
    
    public String getBankUserid() {
        return this.bankUserid;
    }
    
    public void setBankUserid(final String bankUserid) {
        this.bankUserid = bankUserid;
    }
    
    public String getRating() {
        return this.rating;
    }
    
    public void setRating(final String rating) {
        this.rating = rating;
    }
}