package com.nimai.kyc.repository;

import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.nimai.kyc.model.NimaiKyc;

@Repository
public interface NimaiKycBase64Repository extends JpaRepository<NimaiKyc, Integer>, JpaSpecificationExecutor<NimaiKyc> {

	//@Query("FROM NimaiKyc n where n.custUserId.userid = :userid")
	//List<NimaiKyc> getKycDetailsByUserId(String userid);

	//@Query("select n FROM NimaiKyc n where n.custUserId.userid = :userid and n.kycStatus like '%Rejected' order by n.kycId desc limit 1")
	@Query(value = "SELECT count(*) from nimai_f_kyc where userId=(:userid) and kyc_type='Business' and kyc_status like '%Rejected'", nativeQuery = true)
	int getRejectedBusinessKycDetailsCountByUserId(String userid);
	
	@Query(value = "SELECT count(*) from nimai_f_kyc where userId=(:userid) and kyc_type='Personal' and kyc_status like '%Rejected'", nativeQuery = true)
	int getRejectedPersonalKycDetailsCountByUserId(String userid);
	
	@Query(value = "SELECT * from nimai_f_kyc where userId=(:userid) and kyc_status like '%Rejected' and kyc_type='Business' order by id desc limit 1", nativeQuery = true)
	List<NimaiKyc> getOneBusinessKycDetailsByUserId(String userid);
	
	@Query(value = "SELECT * from nimai_f_kyc where userId=(:userid) and kyc_status like '%Rejected' and kyc_type='Personal' order by id desc limit 1", nativeQuery = true)
	List<NimaiKyc> getOnePersonalKycDetailsByUserId(String userid);
	
	@Query(value = "SELECT * from nimai_f_kyc where userId=(:userid) and kyc_status like '%Rejected' and kyc_type='Personal' order by id desc limit 2", nativeQuery = true)
	List<NimaiKyc> getTwoPersonalKycDetailsByUserId(String userid);
	
	//Commented for new query written down
	//@Query(value = "SELECT * from nimai_f_kyc where userId=(:userid) and kyc_status like '%Rejected' order by id desc limit 2", nativeQuery = true)
	//List<NimaiKyc> getKycDetailsByUserId(String userid);
	
	@Query(value = "SELECT * \n" + 
			"FROM nimai_f_kyc \n" + 
			"where id IN \n" + 
			"(\n" + 
			"SELECT max(id) \n" + 
			"FROM nimai_f_kyc \n" + 
			"WHERE userId = :userid \n" + 
			"group by kyc_type \n" + 
			") AND kyc_status like '%Rejected'", nativeQuery = true)
	List<NimaiKyc> getKycDetailsByUserId(String userid);
	
	@Query(value = "SELECT * \n" + 
			"FROM nimai_f_kyc \n" + 
			"where id IN \n" + 
			"(\n" + 
			"SELECT max(id) \n" + 
			"FROM nimai_f_kyc \n" + 
			"WHERE userId = :userid \n" + 
			"group by kyc_type \n" + 
			")", nativeQuery = true)
	List<NimaiKyc> getLatestKycDetailsByUserId(String userid);
	
	@Query(value = "SELECT * from nimai_f_kyc where userId=(:userid) and kyc_status like '%Rejected' order by id desc limit 1", nativeQuery = true)
	List<NimaiKyc> getOneKycDetailsByUserId(String userid);
	
	@Modifying
	@Transactional 
	@Query("update NimaiKyc u set u.country = ?1,u.title = ?2,u.encodedFileContent = ?3,u.custUserId = ?4,u.modifiedDate = ?5,u.kycStatus = ?6 where u.kycId = ?7")
	void update(String country, String title, String encodedFileContent, Object setCustUserId,
			Date modifiedDate,String status,Integer kycId);




	
}
