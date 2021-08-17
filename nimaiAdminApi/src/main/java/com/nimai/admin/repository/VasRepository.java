package com.nimai.admin.repository;

import java.util.Date;
import java.util.List;

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

	@Query("Select count(v) FROM NimaiMVas v where v.countryName= :countryName and  v.status='Active'")
	int checkAvailability(@Param("countryName") String countryName);
	
	@Query("FROM NimaiMVas v where v.countryName= :countryName and  v.status='Active'")
	List<NimaiMVas> getVasDetails(@Param("countryName") String countryName);
	
}
