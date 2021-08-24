package com.nimai.lc.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.nimai.lc.entity.BankDashBoardCount;
@Repository
public interface BankDashboardCountRepository extends JpaRepository<BankDashBoardCount, String> {

}
