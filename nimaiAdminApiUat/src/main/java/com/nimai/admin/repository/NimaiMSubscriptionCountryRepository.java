package com.nimai.admin.repository;

import com.nimai.admin.model.NimaiMSubscriptionCountry;
import javax.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

@Transactional
public interface NimaiMSubscriptionCountryRepository extends JpaRepository<NimaiMSubscriptionCountry, Integer> {
  @Modifying
  @Query(value = "delete from nimai_m_subscriptioncountry where subscription_plan_id=:subscriptionPlanId", nativeQuery = true)
  void deleteSplanCountryById(int subscriptionPlanId);
}
