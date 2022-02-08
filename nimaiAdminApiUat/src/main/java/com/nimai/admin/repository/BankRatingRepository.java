package com.nimai.admin.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.nimai.admin.model.BankRatingModel;
import com.nimai.admin.model.PreferredBankModel;


@Repository
public interface BankRatingRepository extends JpaRepository<BankRatingModel, Integer>{

	@Query("from BankRatingModel pb where pb.bankUserid = ?1")
	BankRatingModel getDetailsByBankUserId(String bank_userid);

}
