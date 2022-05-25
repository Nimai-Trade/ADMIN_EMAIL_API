package com.nimai.admin.repository;

import com.nimai.admin.model.NimaiFKyc;
import com.nimai.admin.model.NimaiMCustomer;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface KycRepository extends JpaRepository<NimaiFKyc, Integer> {
	
  @Query("from NimaiFKyc k where k.userid= :userid")
  List<NimaiFKyc> findByUserid(@Param("userid") NimaiMCustomer userid);
  
  @Query(value = "select * from nimai_f_kyc k where k.userId= :userid", nativeQuery = true)
  List<NimaiFKyc> findKycByUserid(@Param("userid") String userid);
  
  @Query("from NimaiFKyc k where k.userid= :userid")
  List<NimaiFKyc> findByUserid(@Param("userid") String userid);
  
  List<NimaiFKyc> findByUseridAndCountryIn(NimaiMCustomer paramNimaiMCustomer, List<String> paramList);
  
  @Query("select DISTINCT kycStatus from NimaiFKyc k where k.userid= :userid")
  List<String> findByUser(@Param("userid") NimaiMCustomer userid);
  
  @Query("from NimaiFKyc k where k.kycStatus='Maker Approved' and k.country IN :countries")
  Page<NimaiFKyc> findMakerApprovedKyc(List<String> countries, Pageable paramPageable);
  
  @Query(value = "SELECT * FROM nimai_f_kyc k INNER JOIN nimai_m_customer nc ON k.userId=nc.USERID WHERE k.kyc_status='Maker Approved' AND nc.COUNTRY_NAME IN :value", nativeQuery = true)
  Page<NimaiFKyc> findMakerApprovedKycByCountries(@Param("value") List<String> value, Pageable paramPageable);
  
  @Query(value = "SELECT * FROM nimai_f_kyc k INNER JOIN nimai_m_customer nc ON k.userId=nc.USERID WHERE k.kyc_status='Maker Approved' AND nc.subscriber_type=:subsType and nc.bank_type=:bankType and nc.COUNTRY_NAME IN :value", nativeQuery = true)
  Page<NimaiFKyc> findMakerApprovedKycByCountriesSubsTypeBankType(@Param("value") List<String> value,@Param("subsType") String subsType,@Param("bankType") String bankType, Pageable paramPageable);
  
  @Query(value = "SELECT * FROM nimai_f_kyc k INNER JOIN nimai_m_customer nc  ON k.userId=nc.USERID WHERE nc.subscriber_type=:subsType and k.kyc_status='Maker Approved' AND nc.COUNTRY_NAME IN :value", countQuery = "SELECT COUNT(*) AS cnt FROM nimai_f_kyc k INNER JOIN nimai_m_customer nc ON k.userId=nc.USERID\r\n WHERE nc.subscriber_type='Referrer'  \r\nAND k.kyc_status='Maker Approved' \r\nAND nc.COUNTRY_NAME IN :value", nativeQuery = true)
  Page<NimaiFKyc> findCustomerReferrerMakerApprovedKycByCountries(@Param("value") List<String> value,@Param("subsType") String subsType, Pageable paramPageable);
}
