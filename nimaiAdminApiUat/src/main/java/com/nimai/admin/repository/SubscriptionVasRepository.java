package com.nimai.admin.repository;

import com.nimai.admin.model.NimaiSubscriptionVas;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface SubscriptionVasRepository extends JpaRepository<NimaiSubscriptionVas, Integer>, JpaSpecificationExecutor<NimaiSubscriptionVas> {
  List<NimaiSubscriptionVas> findByUserIdAndSubscriptionId(String paramString1, String paramString2);
  
  @Query("from NimaiSubscriptionVas vs where vs.userId=:userid and vs.subscriptionId=:subscriptionId and vs.sPLanSerialNumber=:splanSerialNumber")
  List<NimaiSubscriptionVas> findByUserIdAndSubscriptionIdAndSerialNo(@Param("userid") String userid, @Param("subscriptionId") String subscriptionId, @Param("splanSerialNumber") int splanSerialNumber);
  
  @Query("from NimaiSubscriptionVas vs where vs.userId=:userid and vs.status='Active'")
  List<NimaiSubscriptionVas> findVasByUserId(@Param("userid") String userid);
  
  @Query(value = "select * from nimai_subscription_vas n where n.userId=:userId and n.subscription_id=:subscriptionId order by n.id desc limit 1", nativeQuery = true)
  NimaiSubscriptionVas getVasDetailsBySplanId(@Param("subscriptionId") String subscriptionId, @Param("userId") String userId);
  
  @Query(value = "select * from nimai_subscription_vas n where n.userId=:userId and "
  		+ "n.subscription_id=:subscriptionId and n.status='ACTIVE' order by n.id desc", nativeQuery = true)
  List<NimaiSubscriptionVas> getVasListDetailsBySplanId(@Param("subscriptionId") String subscriptionId, @Param("userId") String userId);
  
  @Query("from NimaiSubscriptionVas nc where nc.countryName=:country and nc.mode='Wire' and "
  		+ "nc.paymentSts='Maker Approved' and nc.sPlanVasFlag!=1 GROUP BY "
  		+ "nc.sPLanSerialNumber order by nc.id desc")
  Page<NimaiSubscriptionVas> getVasListByCountryname(String country, Pageable paramPageable);
  
  @Query("from NimaiSubscriptionVas nc where nc.mode='Wire' and nc.paymentSts='Maker Approved' and"
  		+ " nc.sPlanVasFlag!=1 GROUP BY nc.sPLanSerialNumber order by nc.id desc")
  Page<NimaiSubscriptionVas> getAllVasListByCountryname(String paramString, Pageable paramPageable);
  
  @Query("from NimaiSubscriptionVas k where k.paymentSts='Maker Approved' "
  		+ "and k.mode='Wire'and k.countryName IN "
  		+ "(:countries) and k.sPlanVasFlag!=1 GROUP BY k.sPLanSerialNumber order by k.id desc")
  Page<NimaiSubscriptionVas> findMakerApprovedVasDetails(List<String> countries, Pageable paramPageable);
}
