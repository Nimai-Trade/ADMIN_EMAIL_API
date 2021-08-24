package com.nimai.lc.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.nimai.lc.bean.NimaiLCBean;
import com.nimai.lc.entity.NimaiLC;
import com.nimai.lc.entity.NimaiLCCountry;
import com.nimai.lc.entity.Quotation;


@Repository
@Transactional
public interface LCRepository extends JpaRepository<NimaiLC, String>{

	NimaiLCCountry save(NimaiLCCountry nimailccountry);
	
	@Query(value="SELECT * from get_all_draft_transaction where user_id=(:userId)", nativeQuery = true )
	List<NimaiLC> findAllDraftTransactionByUserId(@Param("userId") String userId);
	
	@Query(value="SELECT * from get_all_draft_transaction where branch_user_email=(:branchEmailId)", nativeQuery = true )
	List<NimaiLC> findAllDraftTransactionByBranchEmailId(@Param("branchEmailId") String branchEmailId);
	
	@Query(value="SELECT * from get_all_draft_transaction where user_id=(:userId) and branch_user_email=(:branchEmailId)", nativeQuery = true )
	List<NimaiLC> findAllDraftTransactionByUserIdBranchEmailId(@Param("userId") String userId,@Param("branchEmailId") String branchEmailId);
	
	@Query(value="SELECT * from get_all_draft_transaction where transaction_id=(:transactionId)", nativeQuery = true )
	List<NimaiLC> findDraftTransactionByTransactionId(@Param("transactionId") String transactionId);

	@Query(value="SELECT * from temp_transaction where transaction_id=(:transactionId)", nativeQuery = true )
	NimaiLC findSpecificDraftTransaction(@Param("transactionId") String transactionId);
	
	@Query("SELECT lc FROM NimaiLC lc WHERE lc.transactionId= (:transactionId) and lc.confirmedFlag=1")
	NimaiLC findByTransactionId(@Param("transactionId") String transactionId);
	
	
	@Query("SELECT lc FROM NimaiLC lc WHERE lc.transactionStatus= (:transactionStatus) and lc.confirmedFlag=1")
	List<NimaiLC> findByTransactionStatus(@Param("transactionStatus") String transactionStatus);
	
	@Query("SELECT lc FROM NimaiLC lc WHERE lc.userId= (:userId) and lc.confirmedFlag=1")
	List<NimaiLC> findByTransactionByUserId(@Param("userId") String userId);
	

	@Query(value= "SELECT CASE WHEN cust.subscriber_type='BANK' THEN 'BA' WHEN cust.subscriber_type='CUSTOMER' THEN 'CU' WHEN cust.subscriber_type='REFERRER' THEN 'RE' ELSE 'BC' END AS 'Output' FROM nimai_m_customer cust where cust.userid=(:userid)", nativeQuery = true)
	String getSubscriberType(@Param("userid") String userid);
	
	
	
	
	@Procedure(name = "move_to_master")
	public void insertIntoMaster(@Param("inp_transaction_id") String transId, @Param("inp_userid") String userId);
	
	@Procedure(name = "clone_transaction")
	public void cloneTransactionDetails(@Param("inp_transaction_id") String transId, @Param("updated_transaction_id") String userId);

	@Query("SELECT lc FROM NimaiLC lc WHERE lc.transactionId= (:transactionId)")
	NimaiLC findTransactionIdToConfirm(@Param("transactionId") String transactionId);
	
	@Query("SELECT lc FROM NimaiLC lc WHERE lc.transactionId= (:transactionId) and lc.userId=(:userId)")
	NimaiLC findTransactionUserIdToConfirm(@Param("transactionId") String transactionId,@Param("userId") String userId);

	@Query(value="SELECT * from get_all_draft_transaction where user_id=(:userId) and branch_user_email=(:branchEmailId)", nativeQuery = true )
	List<NimaiLC> findAllDraftTransactionByUserIdAndBranchEmailId(@Param("userId") String userId, @Param("branchEmailId") String branchEmailId);

	@Query(value="SELECT lc_count from nimai_subscription_details where userid=(:userId) and status='ACTIVE'", nativeQuery = true )
	Integer findLCCount(@Param("userId") String userId);

	@Query(value="SELECT ifnull(LC_UTILIZED_COUNT,0) from nimai_subscription_details where userid=(:userId) and status='ACTIVE'", nativeQuery = true )
	Integer findUtilzedLCCount(@Param("userId") String userId);

	@Query(value="select count(*) from nimai_f_intcountry where country_name=(:countryName)", nativeQuery = true)
	Integer getBanksCountForCountry(String countryName);

	@Modifying
	@Query(value= "update temp_transaction set is_deleted=1 where transaction_id=(:transactionId)", nativeQuery = true)
	void deleteDraftTransaction(String transactionId);

	@Query(value="SELECT validity from get_all_draft_transaction where transaction_id=(:transId) and user_id=(:userId)", nativeQuery = true )
	String getValidityDateByTransIdUserId(String transId, String userId);

	@Query("SELECT lc FROM NimaiLC lc WHERE lc.transactionId= (:transactionId) and lc.userId=(:userId)")
	NimaiLC findByTransactionIdUserId(@Param("transactionId") String transactionId,@Param("userId") String userId);

	@Modifying
	@Query(value= "update temp_transaction set transaction_id=(:newtid) where transaction_id=(:transactionId)", nativeQuery = true)
	void updateTransactionIdByNew(String transactionId, String newtid);
}