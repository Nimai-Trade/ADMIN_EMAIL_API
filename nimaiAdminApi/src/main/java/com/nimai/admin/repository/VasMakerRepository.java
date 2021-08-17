package com.nimai.admin.repository;

import java.util.Date;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.nimai.admin.model.NimaiTempVas;

@Repository
public interface VasMakerRepository
		extends JpaRepository<NimaiTempVas, Integer>, JpaSpecificationExecutor<NimaiTempVas> {

	@Modifying
	@Query("update NimaiTempVas v set v.flag= true ,v.status= :status,v.modifiedBy= :modifiedBy,v.modifiedDate= :modifiedDate where v.vasid= :vasid and flag= false")
	int updateByVasidAndFlag(@Param("vasid") int vasid, @Param("status") String status,
			@Param("modifiedBy") String modifiedBy, @Param("modifiedDate") Date modifiedDate);
	
	@Query
	Page<NimaiTempVas> findByFlag(boolean flag,Pageable pageable);
}
