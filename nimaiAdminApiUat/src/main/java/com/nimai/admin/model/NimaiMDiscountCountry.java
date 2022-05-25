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
@Table(name = "nimai_m_discountcountry")
@EntityListeners({AuditingEntityListener.class})
@DynamicUpdate
public class NimaiMDiscountCountry {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Basic(optional = false)
  @Column(name = "discount_country_id")
  private Integer id;
  
  @Column(name = "discount_country")
  private String discountCountry;
  
  @Column(name = "discount_id")
  private int discountId;
  
  public Integer getId() {
    return this.id;
  }
  
  public void setId(Integer id) {
    this.id = id;
  }
  
  public String getDiscountCountry() {
    return this.discountCountry;
  }
  
  public void setDiscountCountry(String discountCountry) {
    this.discountCountry = discountCountry;
  }
  
  public int getDiscountId() {
    return this.discountId;
  }
  
  public void setDiscountId(int discountId) {
    this.discountId = discountId;
  }
  
  public String toString() {
    return "NimaiMDiscountCountry [id=" + this.id + ", discountCountry=" + this.discountCountry + ", discountId=" + this.discountId + "]";
  }
}
