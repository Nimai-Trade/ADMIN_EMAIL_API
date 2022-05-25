
package com.nimai.admin.repository;

import com.nimai.admin.model.NimaiMCustomer;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import com.nimai.admin.model.NimaiLCMaster;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NimaiLcMasterRepository extends JpaRepository<NimaiLCMaster, String>
{
    @Query("SELECT lc FROM NimaiLCMaster lc WHERE lc.transactionId= (:transactionId) and lc.userId=(:userId)")
    NimaiLCMaster findByTransactionIdUserId(@Param("transactionId") final String transId, @Param("userId") final String userId);
    
    @Query(value = "SELECT * from get_all_transaction where transaction_id=(:transid)", nativeQuery = true)
    NimaiLCMaster findSpecificTransactionById(@Param("transid") final String transid);
    
    @Query(value = "select * from nimai_m_customer where EMAIL_ADDRESS=(:userId)", nativeQuery = true)
    NimaiMCustomer getCustomerDetais(final String userId);
    
    @Query(value = "select first_name from nimai_m_customer where userid=(:userId)", nativeQuery = true)
    String getCustomerName(final String userId);
    
    @Query(value = "select email_address from nimai_m_customer where userid=(:userId)", nativeQuery = true)
    String getCustomerEmailId(final String userId);
}