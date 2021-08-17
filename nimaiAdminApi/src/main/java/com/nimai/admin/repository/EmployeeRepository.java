package com.nimai.admin.repository;

import java.util.Date;
import java.util.List;

import javax.persistence.Tuple;
import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.nimai.admin.model.NimaiMEmployee;
import com.nimai.admin.model.NimaiMRole;

@Repository
public interface EmployeeRepository
		extends JpaRepository<NimaiMEmployee, Integer>, JpaSpecificationExecutor<NimaiMEmployee> {

	@Query("FROM NimaiMEmployee r where r.empCode = :empCode")
	NimaiMEmployee findByEmpCode(@Param("empCode") String empCode);

	@Query(value = "SELECT AUTO_INCREMENT FROM information_schema.TABLES WHERE TABLE_NAME = ?", nativeQuery = true)
	int getNextAutoIncrementNumber(String tableName);
	
	//@Query(value="from NimaiMEmployee ne,NimaiMpUserRole nr where nr.empCode.empCode=ne.empCode,ne.COUNTRY like %:country%  and ne.status=:status and nr.roleId!=:roleId ")
	//List<NimaiMEmployee> findEmpListByCountry(@Param("country") String country, @Param("status") String status,@Param("roleId")int roleId);

	@Query("select count(*) FROM NimaiMEmployee m where m.status = :status")
	long countByEmployeeStatus(String status);

	boolean existsByEmpMobile(String empMobile);

	@Query("SELECT empName FROM NimaiMEmployee where empName like %:empName%")
	public List<String> empNameSearch(@Param("empName") String empName);

	@Query("SELECT empMobile FROM NimaiMEmployee where empMobile like %:empMobile%")
	public List<String> empMobileSearch(@Param("empMobile") String empMobile);

	@Query("FROM NimaiMEmployee r where r.empId = :empId")
	NimaiMEmployee loadByEmpId(long empId);

	@Query("FROM NimaiMEmployee where status = 'ACTIVE'")
	List<NimaiMEmployee> allEmpNameSearch();

	boolean existsByEmpCode(String empCode);

	@Query(value = "select \r\n" + "  o.country,\r\n" + "  o.emp_code,\r\n" + "  o.emp_first_name,\r\n"
			+ "  o.emp_last_name, \r\n" + "  count(distinct t.user_id ) as customer_accounts, \r\n"
			+ "  count(distinct t.transaction_id ) as trxn_count, \r\n"
			+ "  sum(t.lc_value) as cumulative_LC_Amount,\r\n"
			+ "  sum(t.transaction_status = 'Accepted') as trxn_Accepted,\r\n"
			+ "  sum(t.transaction_status = 'Rejected') as trxn_Rejected,\r\n"
			+ "  sum(t.transaction_status = 'Expired') as trxn_Expired\r\n" + "from \r\n" + "  nimai_m_employee o\r\n"
			+ "inner join nimai_m_customer od on o.emp_code = od.RM_ID \r\n"
			+ "left join nimai_mm_transaction t on t.user_id = od.USERID where o.CREATED_DATE>= (:fromDate) AND o.CREATED_DATE   <= (:toDate)"
			+ "and od.SUBSCRIBER_TYPE='CUSTOMER' \r\n"
			+ "group by o.emp_code", nativeQuery = true)
	List<Tuple> getCustRmReport(@Param("fromDate")String fromDate,@Param("toDate") Date toDate);

	
	@Query(value = "select \r\n" + "  o.country,\r\n" + "  o.emp_code,\r\n" + "  o.emp_first_name,\r\n"
			+ "  o.emp_last_name, \r\n" + "  count(distinct t.user_id ) as customer_accounts, \r\n"
			+ "  count(distinct t.transaction_id ) as trxn_count, \r\n"
			+ "  sum(t.lc_value) as cumulative_LC_Amount,\r\n"
			+ "  sum(t.transaction_status = 'Accepted') as trxn_Accepted,\r\n"
			+ "  sum(t.transaction_status = 'Rejected') as trxn_Rejected,\r\n"
			+ "  sum(t.transaction_status = 'Expired') as trxn_Expired\r\n" + "from \r\n" + "  nimai_m_employee o\r\n"
			+ "inner join nimai_m_customer od on o.emp_code = od.RM_ID \r\n"
			+ "left join nimai_mm_transaction t on t.user_id = od.USERID where o.CREATED_DATE>= (:fromDate) AND o.CREATED_DATE   <= (:toDate)"
			+ "and o.emp_code=:empCode \r\n"
			+ "group by o.emp_code", nativeQuery = true)
	List<Tuple> getCustRmReportByEmpCode(@Param("fromDate")String fromDate,@Param("toDate") Date toDate,@Param("empCode")String empCode);
	
	
	
	@Query(value = "select ne.* from nimai_m_employee ne, nimai_mp_user_role nr \n" + 
			"where nr.EMP_CODE=ne.EMP_CODE \n" + 
			"and nr.ROLE_ID!=:roleId and ne.COUNTRY LIKE %:country% and "
			+ "ne.STATUS='ACTIVE' GROUP BY ne.EMP_CODE", nativeQuery = true)
	List<Tuple> getEMpList(int roleId,String country);
	
	
	@Query(value = "select \r\n" + "  o.country,\r\n" + "  o.emp_code,\r\n" + "  o.emp_first_name,\r\n"
			+ "  o.emp_last_name, \r\n" + "  count(distinct t.user_id ) as customer_accounts, \r\n"
			+ "  count(distinct t.transaction_id ) as trxn_count, \r\n"
			+ "  sum(t.lc_value) as cumulative_LC_Amount,\r\n"
			+ "  sum(t.transaction_status = 'Accepted') as trxn_Accepted,\r\n"
			+ "  sum(t.transaction_status = 'Rejected') as trxn_Rejected,\r\n"
			+ "  sum(t.transaction_status = 'Expired') as trxn_Expired\r\n" + "from \r\n" + "  nimai_m_employee o\r\n"
			+ "inner join nimai_m_customer od on o.emp_code = od.RM_ID \r\n"
			+ "left join nimai_mm_transaction t on t.user_id = od.USERID where o.CREATED_DATE>= (:fromDate) AND o.CREATED_DATE   <= (:toDate)"
			+ "and od.SUBSCRIBER_TYPE='BANK' and od.BANK_TYPE='CUSTOMER'\r\n"
			+ "group by o.emp_code", nativeQuery = true)
	List<Tuple> getBankRmReport(@Param("fromDate")String fromDate,@Param("toDate") Date toDate);
	
	
	@Query(value = "select \r\n" + "  o.country,\r\n" + "  o.emp_code,\r\n" + "  o.emp_first_name,\r\n"
			+ "  o.emp_last_name, \r\n" + "  count(distinct t.user_id ) as customer_accounts, \r\n"
			+ "  count(distinct t.transaction_id ) as trxn_count, \r\n"
			+ "  sum(t.lc_value) as cumulative_LC_Amount,\r\n"
			+ "  sum(t.transaction_status = 'Accepted') as trxn_Accepted,\r\n"
			+ "  sum(t.transaction_status = 'Rejected') as trxn_Rejected,\r\n"
			+ "  sum(t.transaction_status = 'Expired') as trxn_Expired\r\n" + "from \r\n" + "  nimai_m_employee o\r\n"
			+ "inner join nimai_m_customer od on o.emp_code = od.RM_ID \r\n"
			+ "left join nimai_mm_transaction t on t.user_id = od.USERID where o.CREATED_DATE>= (:fromDate) AND o.CREATED_DATE   <= (:toDate)\r\n"
			+ "and o.emp_code=:empCode group by o.emp_code", nativeQuery = true)
	List<Tuple> getBankRmReportByEmpCode(@Param("fromDate")String fromDate,@Param("toDate") Date toDate,@Param("empCode")String empCode);

	@Query(value = "select \r\n" + "  o.country,\r\n" + "  o.emp_code,\r\n" + "  o.emp_first_name,\r\n"
			+ "  o.emp_last_name, \r\n" + "  count(distinct t.user_id ) as customer_accounts, \r\n"
			+ "  count(distinct t.transaction_id ) as trxn_count, \r\n"
			+ "  sum(t.lc_value) as cumulative_LC_Amount,\r\n"
			+ "  sum(t.transaction_status = 'Accepted') as trxn_Accepted,\r\n"
			+ "  sum(t.transaction_status = 'Rejected') as trxn_Rejected,\r\n"
			+ "  sum(t.transaction_status = 'Expired') as trxn_Expired\r\n" + "from \r\n" + "  nimai_m_employee o\r\n"
			+ "inner join nimai_m_customer od on o.emp_code = od.RM_ID \r\n"
			+ "left join nimai_mm_transaction t on t.user_id = od.USERID where o.CREATED_DATE>= (:fromDate) AND o.CREATED_DATE   <= (:toDate)"
			+ "and od.SUBSCRIBER_TYPE='BANK' and od.BANK_TYPE='UNDERWRITER'\r\n"
			+ "group by o.emp_code", nativeQuery = true)
	List<Tuple> getBankRmUwReport(@Param("fromDate")String fromDate,@Param("toDate") Date toDate);

	
	@Query(value = "select \r\n" + "  o.country,\r\n" + "  o.emp_code,\r\n" + "  o.emp_first_name,\r\n"
			+ "  o.emp_last_name, \r\n" + "  count(distinct t.user_id ) as customer_accounts, \r\n"
			+ "  count(distinct t.transaction_id ) as trxn_count, \r\n"
			+ "  sum(t.lc_value) as cumulative_LC_Amount,\r\n"
			+ "  sum(t.transaction_status = 'Accepted') as trxn_Accepted,\r\n"
			+ "  sum(t.transaction_status = 'Rejected') as trxn_Rejected,\r\n"
			+ "  sum(t.transaction_status = 'Expired') as trxn_Expired\r\n" + "from \r\n" + "  nimai_m_employee o\r\n"
			+ "inner join nimai_m_customer od on o.emp_code = od.RM_ID \r\n"
			+ "left join nimai_mm_transaction t on t.user_id = od.USERID\r\n"
			+ "group by o.emp_code", nativeQuery = true)
	List<Tuple> getBankRmUwReportbyEmpCode(@Param("fromDate")String fromDate,@Param("toDate") Date toDate,@Param("empCode")String empCode);
	
	@Modifying
	@Transactional
	@Query("update NimaiMLogin n set n.status = ?2 where n.empCode.empCode = ?1")
	void updateLoginStatus(String empCode, String status);


}
