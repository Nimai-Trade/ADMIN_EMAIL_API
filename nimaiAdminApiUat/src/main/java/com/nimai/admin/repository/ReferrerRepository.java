package com.nimai.admin.repository;

import com.nimai.admin.model.NimaiMRefer;
import java.util.Date;
import java.util.List;
import javax.persistence.Tuple;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ReferrerRepository extends JpaRepository<NimaiMRefer, Integer>, JpaSpecificationExecutor<NimaiMRefer> {
  @Query(value = "select userid from nimai_m_refer where email_address =:emailAddress", nativeQuery = true)
  String findReferredUserByEmailId(@Param("emailAddress") String emailAddress);
  
  @Query(value = "SELECT * from nimai_m_refer nm where   nm.INSERTED_DATE >= (:fromDate) AND nm.INSERTED_DATE <= (:toDate) GROUP BY nm.USERID", nativeQuery = true)
  List<NimaiMRefer> finBydates(@Param("fromDate") String fromDate, @Param("toDate") String toDate);
  
  @Query(value = "SELECT * from nimai_m_refer nm where nm.INSERTED_DATE >= (:fromDate) AND nm.INSERTED_DATE <= (:toDate) AND nm.USERID=(:userId);", nativeQuery = true)
  List<Tuple> finBydatesandUserId(@Param("userId") String userId, @Param("fromDate") Date fromDate, @Param("toDate") Date toDate);
  
  @Query("select re from NimaiMRefer re where re.userid.userid= (:userId)")
  List<NimaiMRefer> findReferByUserId(@Param("userId") String userId);
  
  @Query(value = "SELECT  * FROM nimai_m_customer nc inner JOIN nnimai_m_refer nr ON nr.USERID=nc.ACCOUNT_SOURCE WHERE nr.USERID=:value "
  		+ "AND nr.EMAIL_ADDRESS=nc.EMAIL_ADDRESS", 
		  countQuery = "SELECT count(*) FROM nimai_m_customer nc inner JOIN nimai_m_refer nr ON nr.USERID=nc.ACCOUNT_SOURCE WHERE"
		  		+ " nr.USERID=:value AND nr.EMAIL_ADDRESS=nc.EMAIL_ADDRESS", nativeQuery = true)
  Page<NimaiMRefer> getReferrerDetails(String value, Pageable paramPageable);
  
  @Query(value = "SELECT * FROM nimai_m_customer nc inner join nimai_m_refer nr on "
  		+ "nr.USERID=nc.ACCOUNT_SOURCE  WHERE nr.USERID=:value AND nc.KYC_STATUS=:kycStatus and nr.EMAIL_ADDRESS=nc.EMAIL_ADDRESS", 
  		countQuery = "SELECT COUNT(nc.USERID) AS approvedReferrence FROM nimai_m_customer nc inner join nimai_m_refer nr on nr.USERID=nc.ACCOUNT_SOURCE WHERE "
  				+ "nr.USERID=:value AND nc.KYC_STATUS=:kycStatus "
  				+ "and nr.EMAIL_ADDRESS=nc.EMAIL_ADDRESS", nativeQuery = true)
  Page<NimaiMRefer> approvedReferrerDetails(String value, String kycStatus, Pageable paramPageable);
}
