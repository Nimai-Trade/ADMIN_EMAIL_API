package com.nimai.lc.repository;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.nimai.lc.entity.NimaiLC;
import com.nimai.lc.entity.NimaiLCCountry;

@Repository
@Transactional
public interface LCCountryRepository extends JpaRepository<NimaiLCCountry, String>
{
	NimaiLCCountry findByNimailccountry(NimaiLC nimailc);
}
