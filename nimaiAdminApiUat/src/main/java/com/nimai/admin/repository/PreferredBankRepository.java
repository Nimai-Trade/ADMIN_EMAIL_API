package com.nimai.admin.repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.nimai.admin.model.NimaiMCustomer;
import com.nimai.admin.model.PreferredBankModel;

@Repository
public interface PreferredBankRepository extends JpaRepository<PreferredBankModel, Integer>{

	@Modifying
	@Transactional
	@Query("delete from PreferredBankModel where custUserId = ?1 and isUploaded=0")
	void deletePreferredBank(String userId);
	
	@Modifying
	@Transactional
	@Query("delete from PreferredBankModel where custUserId = ?1 and isUploaded=1")
	void deleteUploadedPreferredBank(String userId);

	@Query("from PreferredBankModel pb where pb.custUserId = ?1 and pb.isUploaded=0")
	List<PreferredBankModel> findBankDetails(String custUserId);
	
	@Query("from PreferredBankModel pb where pb.custUserId = ?1 and pb.isUploaded=1")
	List<PreferredBankModel> findUploadedBankDetails(String custUserId);
	
	@Query("from PreferredBankModel pb where pb.custUserId = ?1 and pb.bankName=?2 and pb.bankCountry=?3 and pb.isUploaded=1")
	PreferredBankModel findUploadedBankDetailsForDuplicate(String custUserId, String bankName, String bankCountry);
	
	@Query(value = "SELECT * from customer_preferred_banks cpb where cpb.cust_userid='CU1515' AND\r\n" + 
			"cpb.bank_name='SBIU' AND cpb.bank_country='Europe' AND cpb.is_uploaded=1", nativeQuery = true)
	PreferredBankModel findUploadedBankDetailsForDuplict();
}
