package com.nimai.admin.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.nimai.admin.model.NimaiFKyc;
import com.nimai.admin.model.NimaiKyc;
import com.nimai.admin.model.NimaiMCustomer;



public interface NimaiKycRepository extends JpaRepository<NimaiKyc, Integer> {

	@Query(value="select * from nimai_f_kyc n where n.userId=:userid order by n.id desc limit 2",nativeQuery=true)
	List<NimaiKyc> findByUseridDesc(@Param("userid") String  userid);
	
	@Query(value="select * from nimai_f_kyc n where n.userId=:userid",nativeQuery=true)
	List<NimaiKyc> findByUserid(@Param("userid") String  userid);
}
