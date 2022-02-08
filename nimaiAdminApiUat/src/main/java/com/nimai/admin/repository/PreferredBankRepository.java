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
	@Query("delete from PreferredBankModel where custUserId = ?1")
	void deletePreferredBank(String userId);

	@Query("from PreferredBankModel pb where pb.custUserId = ?1")
	List<PreferredBankModel> findBankDetails(String custUserId);
}
