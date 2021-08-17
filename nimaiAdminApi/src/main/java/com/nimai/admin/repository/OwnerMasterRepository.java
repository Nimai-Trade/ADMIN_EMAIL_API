package com.nimai.admin.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.nimai.admin.model.NimaiFOwner;
import com.nimai.admin.model.NimaiMCustomer;

public interface OwnerMasterRepository extends JpaRepository<NimaiFOwner, Long> {
	
	
	
	//List<NimaiFOwner> findByuserid(NimaiMCustomer cust);
	
	@Query("FROM NimaiFOwner where USERID =:userid")
	public List<NimaiFOwner> findDataByUserId(@Param("userid") String userid);

}
