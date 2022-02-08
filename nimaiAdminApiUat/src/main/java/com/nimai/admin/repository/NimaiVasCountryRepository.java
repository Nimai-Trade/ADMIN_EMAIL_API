package com.nimai.admin.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.nimai.admin.model.NimaiMSubscriptionCountry;
import com.nimai.admin.model.NimaiMVasCountry;

public interface NimaiVasCountryRepository
extends JpaRepository<NimaiMVasCountry, Integer>{

}
