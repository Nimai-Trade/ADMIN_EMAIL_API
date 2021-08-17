package com.nimai.email.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import com.nimai.email.entity.NimaiClient;


@Repository
public interface NimaiMCustomerRepo
extends JpaRepository<NimaiClient, String>, JpaSpecificationExecutor<NimaiClient>{

}
