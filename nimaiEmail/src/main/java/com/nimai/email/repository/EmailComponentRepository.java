package com.nimai.email.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.nimai.email.entity.EmailComponentMaster;


@Repository
public interface EmailComponentRepository
extends JpaRepository<EmailComponentMaster, Integer>, 
JpaSpecificationExecutor<EmailComponentMaster>{

	@Query(value="from EmailComponentMaster where emailEventMaster.emailEventName=:emailEventName")
	  public EmailComponentMaster findEventConfiguration(@Param("emailEventName")String emailEventName);
}
