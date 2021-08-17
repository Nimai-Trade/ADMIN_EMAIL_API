package com.nimai.admin.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.nimai.admin.model.NimaiFKyc;
import com.nimai.admin.model.NimaiMCustomer;

public interface KycRepository extends JpaRepository<NimaiFKyc, Integer>//, JpaSpecificationExecutor<NimaiFKyc> {
{
	@Query("from NimaiFKyc k where k.userid= :userid")
	List<NimaiFKyc> findByUserid(@Param("userid") NimaiMCustomer  userid);
	
	
	
	@Query("from NimaiFKyc k where k.userid= :userid")
	List<NimaiFKyc> findByUserid(@Param("userid") String userid);

	List<NimaiFKyc> findByUseridAndCountryIn(NimaiMCustomer userid, List<String> countryName);

	@Query("select DISTINCT kycStatus from NimaiFKyc k where k.userid= :userid")
	List<String> findByUser(@Param("userid") NimaiMCustomer userid);
	
	//@Query(value = "SELECT n1.* FROM nimai_f_kyc as n1 LEFT JOIN nimai_m_customer as m\n" + 
	//		" ON (n1.userId = m.USERID)\n" + 
	//		"WHERE n1.kyc_status='Maker Approved' and n1.country IN (:countries)", nativeQuery = true)
//	@Query("from NimaiFKyc k where k.kycStatus='Maker Approved' and k.country IN :countries")
//	Page<NimaiFKyc> findMakerApprovedKyc(List<String> countries,Pageable pageable);
	
	//@Query(value ="SELECT * FROM nimai_f_kyc k INNER JOIN nimai_m_customer nc ON k.userId=nc.USERID WHERE k.kyc_status='Maker Approved' AND nc.COUNTRY_NAME IN :countries", nativeQuery = true)
	@Query("from NimaiFKyc k where k.kycStatus='Maker Approved' and k.country IN :countries")
	Page<NimaiFKyc> findMakerApprovedKyc(List<String> countries,Pageable pageable);

	
	@Query(value ="SELECT * FROM nimai_f_kyc k INNER JOIN nimai_m_customer nc ON k.userId=nc.USERID WHERE k.kyc_status='Maker Approved' AND nc.COUNTRY_NAME IN :value", nativeQuery = true)
	Page<NimaiFKyc> findMakerApprovedKycByCountries(@Param("value")List<String> value, Pageable pageable);
}
