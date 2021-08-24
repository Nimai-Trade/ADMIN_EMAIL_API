package com.nimai.lc.repository;

import java.util.HashMap;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.nimai.lc.bean.QuotationBean;
import com.nimai.lc.entity.NimaiLC;
import com.nimai.lc.entity.Quotation;

@Repository
@Transactional
public interface QuotationRepository extends JpaRepository<Quotation, Integer>
{
	@Query(value="SELECT * from get_all_draft_quotation where userid=(:userId)", nativeQuery = true )
	List<Quotation> findAllDraftQuotation(@Param("userId") String userId);

	@Query(value="SELECT * from get_all_draft_quotation where quotation_id=(:quotationId)", nativeQuery = true )
	Quotation findSpecificDraftQuotation(@Param("quotationId") Integer quotationId);
	
	@Modifying
	@Query(value= "update nimai_mm_transaction set quotation_placed='Yes' where transaction_id=(:transId)", nativeQuery = true)
	public void updateQuotationPlaced(@Param("transId") String transId);
	
	@Query(value= "select count(q.quotation_id)\r\n" + 
			"from nimai_m_quotation q,nimai_mm_transaction t\r\n" + 
			"where q.transaction_id=(:transId) and q.transaction_id=t.transaction_id \r\n" + 
			"group by t.transaction_id;", nativeQuery = true)
	public int getQuotationCount(@Param("transId") String transId);
	
	@Procedure(name = "move_to_quotation_master")
	public void insertIntoQuotationMaster(@Param("inp_quotation_id") String quotationId,@Param("inp_transaction_id") String transId, @Param("inp_userid") String userId);
	
	@Query(value="SELECT * from get_all_draft_quotation where bank_userid=(:bankUserId)", nativeQuery = true )
	List<Quotation> findAllDraftQuotationByBankUserId(@Param("bankUserId") String bankUserId);
	
	@Query(value="SELECT * from get_draft_trans_quote_for_bank where bank_userid=(:bankUserId)", nativeQuery = true )
	List findDraftTransQuotationBybankUser(String bankUserId);
	
	@Query(value="SELECT * from get_draft_trans_quote_for_bank where (transaction_status='Active' or transaction_status='Accepted') and bank_userid=(:bankUserId)", nativeQuery = true )
	List findDraftTransQuotationBybankUserId(String bankUserId);

	@Query(value="SELECT q from Quotation q where transactionId=(:transId) and userId=(:userId) and bankUserId=(:bankUserId)")
	Quotation findQuotationDetails(String transId, String userId, String bankUserId);

	@Query(value="SELECT quotation_id from temp_quotation where transaction_id=(:transId) and userid=(:userId) and bank_userid=(:bankUserId)", nativeQuery = true )
	int getQuotationId(String transId, String userId, String bankUserId);

	@Query(value="SELECT * from get_all_draft_quotation where quotation_id=(:quotationId)", nativeQuery = true )
	Quotation findDraftQuotationByQuotationId(Integer quotationId);
	
	@Modifying
	@Query(value= "update temp_quotation set is_deleted=1 where quotation_id=(:quotationId)", nativeQuery = true)
	void deleteDraftQuotation(Integer quotationId);

	@Modifying
	@Query(value= "update temp_quotation set is_deleted=1 where transaction_id=(:transactionId) and userid=(:userId) and confirmed_flag is null", nativeQuery = true)
	void deleteQuoteByTrasanctionId(String transactionId, String userId);

	@Query(value="SELECT bank_userid from temp_quotation where quotation_id=(:quotationId)", nativeQuery = true )
	String getBankUserId(Integer quotationId);
	
}
