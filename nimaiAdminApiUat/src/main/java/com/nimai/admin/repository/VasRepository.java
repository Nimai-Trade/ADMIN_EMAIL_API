package com.nimai.admin.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.nimai.admin.model.NimaiMVas;

@Repository
public interface VasRepository extends JpaRepository<NimaiMVas, Integer>, JpaSpecificationExecutor<NimaiMVas> {

	@Modifying
	@Query("update NimaiMVas v set v.modifiedBy= :modifiedBy,v.modifiedDate= :modifiedDate,v.status='Deactivated' where vasid= :vasid and status='Active'")
	int updateCheckerVas(@Param("vasid") int vasid, @Param("modifiedBy") String modifiedBy,
			@Param("modifiedDate") Date modifiedDate);

	@Query("Select count(v) FROM NimaiMVas v where v.countryName= :countryName and  v.status='Active' and v.customerType= :custType")
	int checkAvailability(@Param("countryName") String countryName,@Param("custType") String custType);
	
	@Query("FROM NimaiMVas v where v.countryName= :countryName and  (v.status='Active' or v.status='Pending')  and v.customerType= :custType")
	List<NimaiMVas> getVasDetails(@Param("countryName") String countryName,@Param("custType") String custType);
	
	@Query(value = "SELECT sub.* FROM nimai_m_vas sub INNER JOIN nimai_m_vascoutry vas\r\n" + 
			"ON sub.VAS_ID=vas.vasid\r\n" + 
			"  where vas.vas_country in :value ",
	 		countQuery = "SELECT cnt FROM\n" + 
	 				"(SELECT COUNT(*) AS cnt FROM nimai_m_vas sub INNER JOIN nimai_m_vascoutry vas ON"
	 				+ " sub.VAS_ID=vas.vasid\n" + 
	 				"where   vas.vas_country IN :value  )\n" + 
	 				"AS cnt",nativeQuery = true)
	public Page<NimaiMVas> getAllVasPlan(List<String> value, Pageable pageable);
}
