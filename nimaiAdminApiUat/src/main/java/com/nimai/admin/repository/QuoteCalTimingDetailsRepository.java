package com.nimai.admin.repository;

import com.nimai.admin.model.QuoteCalTimingDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface QuoteCalTimingDetailsRepository extends JpaRepository<QuoteCalTimingDetails, Integer>, JpaSpecificationExecutor<QuoteCalTimingDetails> {}
