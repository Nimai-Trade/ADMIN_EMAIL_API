package com.nimai.email.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import com.nimai.email.entity.EmailComponentMaster;
import com.nimai.email.entity.NimaiEmailScheduler;

@Repository
public interface NimaiEmailSchedulerRepo extends JpaRepository<NimaiEmailScheduler, Integer>, 
JpaSpecificationExecutor<EmailComponentMaster> {

}
