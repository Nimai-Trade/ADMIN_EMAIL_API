package com.nimai.admin.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.nimai.admin.model.NimaiEmailScheduler;
import com.nimai.admin.model.NimaiMpDiscount;

public interface NimaiEmailSchedulerRepository
extends JpaRepository<NimaiEmailScheduler, Integer>, JpaSpecificationExecutor<NimaiEmailScheduler> {

}
