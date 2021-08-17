package com.nimai.admin.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.nimai.admin.model.NimaiMCustomer;
import com.nimai.admin.model.NimaiMQuotation;

public interface QuotationRepository
		extends JpaRepository<NimaiMQuotation, Integer>, JpaSpecificationExecutor<NimaiMQuotation> {

	List<NimaiMQuotation> findByUserid(NimaiMCustomer userid);

	List<NimaiMQuotation> findByUseridAndCountryNameIn(NimaiMCustomer userid, List<String> countryName);

	@Query("select count(*) FROM NimaiMQuotation m where m.bankUserid = :userid")
	long quoteCout(@Param("userid") String userid);

}
