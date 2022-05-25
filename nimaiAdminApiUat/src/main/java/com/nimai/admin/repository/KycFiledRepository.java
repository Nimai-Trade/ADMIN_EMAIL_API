
package com.nimai.admin.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import com.nimai.admin.model.NimaiMKycfileds;
import org.springframework.data.jpa.repository.JpaRepository;

public interface KycFiledRepository extends JpaRepository<NimaiMKycfileds, Integer>, JpaSpecificationExecutor<NimaiMKycfileds>
{
    @Query("from NimaiMKycfileds nk where nk.userId=:userId")
    NimaiMKycfileds getFiledData(@Param("userId") final String userId);
    
    @Query(value = "select * from nimai_f_kycfields nk where nk.user_id =:userId  ORDER BY nk.field_id desc limit 1", nativeQuery = true)
    NimaiMKycfileds getFidData(@Param("userId") final String userId);
}