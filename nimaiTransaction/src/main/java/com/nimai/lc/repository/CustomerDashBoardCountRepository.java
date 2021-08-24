package com.nimai.lc.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.nimai.lc.entity.CustomerDashBoardCount;

@Repository
public interface CustomerDashBoardCountRepository extends JpaRepository<CustomerDashBoardCount, String> {

}
