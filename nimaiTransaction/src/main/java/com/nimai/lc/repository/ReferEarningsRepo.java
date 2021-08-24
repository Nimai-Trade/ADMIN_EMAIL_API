package com.nimai.lc.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.nimai.lc.entity.ReferEarnings;

@Repository
public interface ReferEarningsRepo extends JpaRepository<ReferEarnings, String> {

}
