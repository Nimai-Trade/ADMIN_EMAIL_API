package com.nimai.admin.repository;

import org.springframework.stereotype.Repository;
import com.nimai.admin.model.NimaiMSource;
import org.springframework.data.jpa.repository.JpaRepository;

@Repository
public interface SourceDetailsRepository extends JpaRepository<NimaiMSource, Integer>
{
}