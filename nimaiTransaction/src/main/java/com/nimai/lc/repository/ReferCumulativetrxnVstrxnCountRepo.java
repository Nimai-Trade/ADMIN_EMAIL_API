package com.nimai.lc.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.nimai.lc.entity.ReferCumulativetxnvsTrxnCount;
@Repository
public interface ReferCumulativetrxnVstrxnCountRepo extends 
JpaRepository<ReferCumulativetxnvsTrxnCount, String> {

}
