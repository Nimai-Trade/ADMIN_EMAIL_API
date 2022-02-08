package com.nimai.admin.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.nimai.admin.model.NimaiLCMaster;
import com.nimai.admin.model.NimaiMSubscriptionCountry;

public interface NimaiMSubscriptionCountryRepository extends JpaRepository<NimaiMSubscriptionCountry, Integer> {

}
