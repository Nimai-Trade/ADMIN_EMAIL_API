package com.nimai.lc.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.nimai.lc.entity.NewRequestEntity;
import com.nimai.lc.entity.NewRequestEntity;

@Repository
public interface NewRequestRepository extends JpaRepository<NewRequestEntity, String>{

}
