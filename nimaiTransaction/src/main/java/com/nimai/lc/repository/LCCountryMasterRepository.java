package com.nimai.lc.repository;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.nimai.lc.entity.NimaiLCCountryMaster;


@Repository
@Transactional
public interface LCCountryMasterRepository extends JpaRepository<NimaiLCCountryMaster, String>{

}
