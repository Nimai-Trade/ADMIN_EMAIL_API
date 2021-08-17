package com.nimai.admin.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.nimai.admin.model.NimaiSystemConfig;


@Repository
public interface nimaiSystemConfigRepository extends JpaRepository<NimaiSystemConfig, Integer>,
JpaSpecificationExecutor<NimaiSystemConfig>{

	


	@Query(value="select ns.system_config_entity_value from nimai_system_config ns where ns.system_config_entity='ADMIN_LINK_EXPIRE_DAYS'",nativeQuery = true)
	String findByLinkDays();

}
