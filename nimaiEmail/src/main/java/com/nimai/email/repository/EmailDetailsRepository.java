package com.nimai.email.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.nimai.email.entity.EmailDetails;


@Repository
public interface EmailDetailsRepository
extends JpaRepository<EmailDetails, Integer>, JpaSpecificationExecutor<EmailDetails>{

	@Query(value="FROM EmailDetails where emailSendFlg=:emailSendFlg")
	List<EmailDetails> findAllByEmailFlag(@Param("emailSendFlg")int emailSendFlg);

}
