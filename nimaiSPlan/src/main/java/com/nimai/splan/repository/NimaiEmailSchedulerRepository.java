package com.nimai.splan.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.nimai.splan.model.NimaiAdvisory;
import com.nimai.splan.model.NimaiEmailScheduler;

public interface NimaiEmailSchedulerRepository extends JpaRepository<NimaiEmailScheduler, Integer> {

}
