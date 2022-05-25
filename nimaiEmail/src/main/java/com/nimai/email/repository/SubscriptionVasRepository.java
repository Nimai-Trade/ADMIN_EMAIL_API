package com.nimai.email.repository;

import com.nimai.email.entity.NimaiSubscriptionVas;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface SubscriptionVasRepository extends JpaRepository<NimaiSubscriptionVas, Integer>, JpaSpecificationExecutor<NimaiSubscriptionVas> {
  @Query(value = "select * from nimai_subscription_vas vs where  vs.userid=(:userId) and vs.plan_name=(:subscriptionName) and vs.`status`='Active' ORDER BY vs.id DESC LIMIT 1", nativeQuery = true)
  NimaiSubscriptionVas getVasDetails(@Param("userId") String userId, @Param("subscriptionName") String subscriptionName);
  
  @Query(value = "select * from nimai_subscription_vas vs where  vs.userid=(:userId) and vs.SPL_SERIAL_NUMBER=(:sPLanSerialNumber) and vs.`status`='Active' ORDER BY vs.id DESC LIMIT 1", nativeQuery = true)
  NimaiSubscriptionVas getVasDetailsBySerialNumber(@Param("userId") String userId, @Param("sPLanSerialNumber") Integer sPLanSerialNumber);
  
  @Query(value = "select * from nimai_subscription_vas vs where  vs.userid=(:userId) and vs.plan_name=(:invoiceId) and ORDER BY vs.id DESC LIMIT 1", nativeQuery = true)
  NimaiSubscriptionVas getVasDetailsByTransactionId(@Param("userId") String userId, @Param("invoiceId") String invoiceId);
  
  @Query(value = "select * from nimai_subscription_vas vs where  vs.userid=(:userId) and vs.invoice_id=(:invoiceId) ORDER BY vs.id DESC LIMIT 1", nativeQuery = true)
  NimaiSubscriptionVas getVasDetailsByInvoiceId(@Param("userId") String userId, @Param("invoiceId") String invoiceId);
}
