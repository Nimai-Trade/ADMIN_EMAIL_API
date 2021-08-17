package com.nimai.admin.repository;

import java.util.Date;
import java.util.List;

import javax.persistence.Tuple;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.nimai.admin.model.NimaiMRefer;
import com.nimai.admin.model.NimaiSubscriptionDetails;

@Repository
public interface ReferrerRepository extends JpaRepository<NimaiMRefer, Integer>, JpaSpecificationExecutor<NimaiMRefer> {

	@Query(value="select userid from nimai_m_refer where email_address =:emailAddress", nativeQuery = true)
	public String findReferredUserByEmailId(@Param("emailAddress") String emailAddress);

	@Query(value="from NimaiMRefer nm where \n" + 
			"            nm.insertedDate >= (:fromDate) AND\n" + 
			"        nm.insertedDate   <= (:toDate)")
	public List<NimaiMRefer> finBydates(@Param("fromDate")Date fromDate,@Param("toDate") Date toDate);

//
//	@Query(value="from NimaiMRefer nm where nm.insertedDate >= (:fromDate) AND nm.insertedDate <= (:toDate)")
//	public List<NimaiMRefer> finBydates(@Param("fromDate")Date fromDate,@Param("toDate") Date toDate);

	
	@Query(value="SELECT * from nimai_m_refer nm where \n" + 
			"            nm.INSERTED_DATE >= (:fromDate) AND\n" + 
			"        nm.INSERTED_DATE   <= (:toDate) AND nm.USERID=(:userId);",nativeQuery = true)
	public List<Tuple> finBydatesandUserId(@Param("userId")String userId,@Param("fromDate") Date fromDate,
			@Param("toDate") Date toDate);
	
	
	
	
	
	
}
