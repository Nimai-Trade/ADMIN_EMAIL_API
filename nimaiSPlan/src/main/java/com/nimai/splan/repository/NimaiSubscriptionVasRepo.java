package com.nimai.splan.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.nimai.splan.model.NimaiSubscriptionVas;

public interface NimaiSubscriptionVasRepo extends JpaRepository<NimaiSubscriptionVas, Integer>
{
	@Query("SELECT na FROM NimaiSubscriptionVas na WHERE na.userId = (:userId)")
	List<NimaiSubscriptionVas> findAllByUserId(String userId);

	@Query("SELECT na FROM NimaiSubscriptionVas na WHERE na.userId = (:userId) and na.status = 'Active'")
	List<NimaiSubscriptionVas> findActiveVASByUserId(String userId);
}
