package com.nimai.email.dao;

import java.util.ArrayList;


import java.util.Date;
import java.util.List;

import javax.persistence.NoResultException;
import javax.persistence.TemporalType;
import javax.sound.midi.Soundbank;
import javax.transaction.Transactional;

import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.engine.query.spi.sql.NativeSQLQueryCollectionReturn;
import org.hibernate.query.NativeQuery;
import org.hibernate.query.Query;
import org.hibernate.type.TimestampType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.nimai.email.entity.AdminDailyCountDetailsBean;
import com.nimai.email.entity.AdminRmWiseCount;
import com.nimai.email.entity.BankMonthlyReport;
import com.nimai.email.entity.CustomerBankMonthlyReort;
import com.nimai.email.entity.EodBankDailyReport;
import com.nimai.email.entity.EodCustomerDailyReort;
import com.nimai.email.entity.NimaiClient;
import com.nimai.email.entity.NimaiEmailScheduler;
import com.nimai.email.entity.NimaiLCMaster;
import com.nimai.email.entity.NimaiMBranch;
import com.nimai.email.entity.NimaiMEmployee;
import com.nimai.email.entity.NimaiMLogin;
import com.nimai.email.entity.NimaiMSubscription;
import com.nimai.email.entity.NimaiSubscriptionDetails;


@Repository
@Transactional
public class DailySchedulerDaoImpl implements DailySchedulerDao {

	private static Logger logger = LoggerFactory.getLogger(DailySchedulerDaoImpl.class);
	@Autowired
	SessionFactory sessionFactory;

	@SuppressWarnings("rawtypes")
	@Override
	public List<NimaiSubscriptionDetails> getPlanDetails(Date dnow, Date sevendaysDate, Date thirtydaysEndDate, String renewalStatus) {
		List<NimaiSubscriptionDetails> emailDetailsScheduled = new ArrayList<>();
		try {
			Session session = sessionFactory.getCurrentSession();
			System.out.println("======================" + dnow + "=============");
			System.out.println("======================" + sevendaysDate + "=============");
			System.out.println("======================" + thirtydaysEndDate + "=============");

			Query query = session.createQuery(
					"from NimaiSubscriptionDetails  where (subscriptionEndDate=:dnow or subscriptionEndDate=:sevendaysDate or subscriptionEndDate=:thirtydaysEndDate) AND status= 'ACTIVE' AND renewalEmailStatus=:renewalStatus",
					NimaiSubscriptionDetails.class);
			query.setParameter("dnow", dnow, TemporalType.DATE);
			query.setParameter("sevendaysDate", sevendaysDate, TemporalType.DATE);
			query.setParameter("thirtydaysEndDate", thirtydaysEndDate, TemporalType.DATE);
			query.setParameter("renewalStatus", renewalStatus);

			emailDetailsScheduled = query.getResultList();
			return emailDetailsScheduled;
		} catch (NoResultException nre) {
			nre.printStackTrace();
			return null;
		}

	}

	public NimaiSubscriptionDetails getCurrencyDetails(Date splanEndDate, Date endDate) {
		// TODO Auto-generated method stub
		return null;
	}

	public NimaiMSubscription getCurrencyDetails(String subscriptionId) {
		NimaiMSubscription emailDetailsScheduled = null;
		logger.info("inside save DailySchedulerDaoImpl method of getCurrencyDetails class");
		try {
			Session session = sessionFactory.getCurrentSession();

			Query query = session.createQuery("from NimaiMSubscription n where n.subscriptionId=:subscriptionId",
					NimaiMSubscription.class);
			query.setParameter("subscriptionId", subscriptionId);

			emailDetailsScheduled = (NimaiMSubscription) query.getSingleResult();
			return emailDetailsScheduled;
		} catch (NoResultException nre) {
			nre.printStackTrace();
			return null;
		}
	}

	public void updaterenewalEmailStatus(Integer getsPlSerialNUmber) {
		// TODO Auto-generated method stub
		logger.info("inside save DailySchedulerDaoImpl method of updaterenewalEmailStatus class");
		try {
			System.out.println("=================" + getsPlSerialNUmber + "========================");
			Session session = sessionFactory.getCurrentSession();
			NimaiSubscriptionDetails email = (NimaiSubscriptionDetails) session.load(NimaiSubscriptionDetails.class,
					new Integer(getsPlSerialNUmber));
			if (null != email) {
				email.setRenewalEmailStatus("Sent");
				session.update(email);
			}

		} catch (NoResultException nre) {
			nre.printStackTrace();
		}

	}

	public Object getAdminTotalCustCount() {
		Object countDetails = null;
		try {
			Session session = sessionFactory.getCurrentSession();

			@SuppressWarnings("rawtypes")
			SQLQuery query = session
					.createSQLQuery("select t1.yesterday_totalcorporate,t2.yesterday_totalunderwriterbank,\r\n" + 
							"t3.yesterday_totalcustomerbank,t4.yesterday_numberOfTransaction,ifnull(t5.yesterday_totalamount,0) as yesterday_totalamount, \r\n" + 
							"t6.lifetime_totalcorporate,t7.lifetime_totalunderwriterbank,\r\n" + 
							"t8.lifetime_totalcustomerbank,t9.lifetime_numberOfTransaction,ifnull(t10.lifetime_totalamount,0) as lifetime_totalamount\r\n" + 
							"from(select count(*) as yesterday_totalcorporate from nimai_m_customer nm where nm.SUBSCRIBER_TYPE='CUSTOMER' \r\n" + 
							"and nm.ACCOUNT_STATUS='ACTIVE' and nm.INSERTED_DATE=SUBDATE(NOW(), 1))t1,\r\n" + 
							"(select count(*) as yesterday_totalunderwriterbank from nimai_m_customer nm where \r\n" + 
							"nm.SUBSCRIBER_TYPE='BANK' and nm.BANK_TYPE='UNDERWRITER' and nm.ACCOUNT_STATUS='ACTIVE'\r\n" + 
							"and nm.INSERTED_DATE=SUBDATE(NOW(), 1))t2,\r\n" + 
							"(select count(*) as yesterday_totalcustomerbank from nimai_m_customer \r\n" + 
							"nm where nm.SUBSCRIBER_TYPE='BANK' and nm.BANK_TYPE='CUSTOMER' and nm.ACCOUNT_STATUS='ACTIVE'\r\n" + 
							"and nm.INSERTED_DATE=SUBDATE(NOW(), 1))t3,\r\n" + 
							"(select count(*) as yesterday_numberOfTransaction \r\n" + 
							"from nimai_mm_transaction nt where nt.transaction_status='ACTIVE' and nt.INSERTED_DATE=SUBDATE(NOW(), 1))t4,\r\n" + 
							"(select sum(nt.lc_value) as yesterday_totalamount \r\n" + 
							"from nimai_mm_transaction nt where nt.transaction_status='ACTIVE' and nt.INSERTED_DATE=SUBDATE(NOW(), 1))t5,\r\n" + 
							"(select count(*) as lifetime_totalcorporate \r\n" + 
							"from nimai_m_customer nm where nm.SUBSCRIBER_TYPE='CUSTOMER' and nm.ACCOUNT_STATUS='ACTIVE')t6,\r\n" + 
							"(select count(*) as lifetime_totalunderwriterbank from nimai_m_customer nm \r\n" + 
							"where nm.SUBSCRIBER_TYPE='BANK' and nm.BANK_TYPE='UNDERWRITER' and nm.ACCOUNT_STATUS='ACTIVE')t7,\r\n" + 
							"(select count(*) as lifetime_totalcustomerbank from nimai_m_customer nm where \r\n" + 
							"nm.SUBSCRIBER_TYPE='BANK' and nm.BANK_TYPE='CUSTOMER' and nm.ACCOUNT_STATUS='ACTIVE')t8,\r\n" + 
							"(select count(*) as lifetime_numberOfTransaction from nimai_mm_transaction nt \r\n" + 
							"where nt.transaction_status='ACTIVE' )t9,\r\n" + 
							"(select sum(nt.lc_value) as lifetime_totalamount from nimai_mm_transaction nt where \r\n" + 
							"nt.transaction_status='ACTIVE' )t10");

			countDetails = query.list();
			return countDetails;
		} catch (NoResultException nre) {
			nre.printStackTrace();
			return null;
		}

	}

	@Override
	public List<AdminRmWiseCount> getRmCount(Date dnow) {
		List<AdminRmWiseCount> result = null;
		try {
			logger.info("============Inside  DailySchedulerDaoImpl getRmCount==========");
			System.out.println("======================catch:"
					+ "Inside DailySchedulerDaoImpl getRmCount "
					+ "===============================");
			Session session = sessionFactory.getCurrentSession();
			Query query = session.createQuery(
					"from AdminRmWiseCount nb where nb.emailStatus= :emalStatus");

			//query.setParameter("dnow", dnow);
			query.setParameter("emalStatus", "Pending");
			result=query.getResultList();
		} catch (NoResultException nre) {
			nre.printStackTrace();
			return null;
		}
		return result;
	}

	@Override
	public AdminDailyCountDetailsBean getDailyCountDetails(Date date) {
		// TODO Auto-generated method stub
		AdminDailyCountDetailsBean result = new AdminDailyCountDetailsBean();
		try {
			logger.info("============Inside  DailySchedulerDaoImpl getDailyCountDetails==========");
			System.out.println("======================catch:"
					+ "Inside DailySchedulerDaoImpl getDailyCountDetails "
					+ "===============================");
			Session session = sessionFactory.getCurrentSession();
			Query query = session.createQuery(
					"from AdminDailyCountDetailsBean nb where nb.emailStatus= :emalStatus");

			//query.setParameter("dnow", dnow);
			query.setParameter("emalStatus", "Pending");
			result=(AdminDailyCountDetailsBean) query.getSingleResult();
		} catch (NoResultException nre) {
			nre.printStackTrace();
			return null;
		}
		return result;
}

	@Override
	public List<NimaiMEmployee> findManagementEmailIds() {
		List<NimaiMEmployee> managementList = null;
		try {
			logger.info("============Inside  DailySchedulerDaoImpl findManagementEmailIds==========");
			System.out.println("======================catch:"
					+ "Inside DailySchedulerDaoImpl findManagementEmailIds "
					+ "===============================");
			Session session = sessionFactory.getCurrentSession();
			Query query = session.createQuery(
					"from NimaiMEmployee nb where nb.designation= :designation");

			//query.setParameter("dnow", dnow);
			query.setParameter("designation", "Management");
			managementList=query.getResultList();
		} catch (NoResultException nre) {
			nre.printStackTrace();
			return null;
		}
		return managementList;
	}

	public List<EodCustomerDailyReort> getCuDailyReport() {
		// TODO Auto-generated method stub
		List<EodCustomerDailyReort> result = new ArrayList<>();
		logger.info("============Inside  DailySchedulerDaoImpl getCuDailyReport ==========");
		System.out.println("======================catch:"
				+ "Inside DailySchedulerDaoImpl getCuDailyReport "
				+ "===============================");
				try {
					Session session = sessionFactory.getCurrentSession();
					Query query = session.createQuery(
							"from EodCustomerDailyReort nb where nb.emailStatus= :emalStatus");

					//query.setParameter("dnow", dnow);
					query.setParameter("emalStatus", "Pending");
					result= query.getResultList();
				} catch (NoResultException nre) {
					nre.printStackTrace();
					return null;
				}
				return result;
	}

	public List<EodBankDailyReport> getBankDailyReport() {
		// TODO Auto-generated method stub
		List<EodBankDailyReport> result = new ArrayList<>();
		logger.info("============Inside  DailySchedulerDaoImpl getBankDailyReport==========");
		System.out.println("======================catch:"
				+ "Inside DailySchedulerDaoImpl getBankDailyReport "
				+ "===============================");
		try {
			Session session = sessionFactory.getCurrentSession();
			Query query = session.createQuery(
					"from EodBankDailyReport nb where nb.emailstatus= :emalStatus");

			//query.setParameter("dnow", dnow);
			query.setParameter("emalStatus", "Pending");
			result= query.getResultList();
		} catch (NoResultException nre) {
			nre.printStackTrace();
			return null;
		}
		return result;
	}

	public List<NimaiLCMaster> getAcceptedTrList() {
		// TODO Auto-generated method stub
		logger.info("inside save  List<NimaiLCMaster> method of UserServiceDaoImpl class");
		List<NimaiLCMaster> result = new ArrayList<>();
		try {
			Session session = sessionFactory.getCurrentSession();
			Query query = session.createSQLQuery("select *  from nimai_mm_transaction nm  \r\n" + 
					" WHERE YEAR(nm.INSERTED_DATE) = YEAR(CURRENT_DATE)\r\n" + 
					"AND MONTH(nm.INSERTED_DATE) = MONTH(CURRENT_DATE) and nm.transaction_status=:status");
					query.setParameter("status", "Accepted");
			result= query.getResultList();
		} catch (NoResultException nre) {
			nre.printStackTrace();
			return null;
		}
		return result;
	}

	public void updateAdminReportEmailStatus(int reportId) {
		// TODO Auto-generated method stub
		try {
			System.out.println("=================" + reportId + "========================");
			logger.info("============Inside  DailySchedulerDaoImpl updateAdminReportEmailStatus==========");
			System.out.println("======================catch:"
					+ "Inside DailySchedulerDaoImpl updateAdminReportEmailStatus admincount"
					+ "===============================");
			Session session = sessionFactory.getCurrentSession();
			AdminDailyCountDetailsBean email = (AdminDailyCountDetailsBean) session.load(AdminDailyCountDetailsBean.class,
					new Integer(reportId));
			if (null != email) {
				email.setEmailStatus("Sent");
				session.update(email);
			}

		} catch (NoResultException nre) {
			nre.printStackTrace();
		}
	}

	public void updateRmWiseCountReportEmailStatus(int reportId) {
		// TODO Auto-generated method stub
		try {
			System.out.println("=================" + reportId + "========================");
			logger.info("============Inside  DailySchedulerDaoImpl updateRmWiseCountReportEmailStatus ==========");
			System.out.println("======================catch:"
					+ "Inside DailySchedulerDaoImpl updateRmWiseCountReportEmailStatus ");
			Session session = sessionFactory.getCurrentSession();
			AdminRmWiseCount email = (AdminRmWiseCount) session.load(AdminRmWiseCount.class,
					new Integer(reportId));
			if (null != email) {
				email.setEmailStatus("Sent");
				session.update(email);
			}

		} catch (NoResultException nre) {
			nre.printStackTrace();
		}
		
	}

	public void updateCuReportEmailStatus(int reportId) {
		// TODO Auto-generated method stub
		try {
			System.out.println("=================" + reportId + "========================");
			logger.info("============Inside  DailySchedulerDaoImpl updateCuReportEmailStatus ==========");
			System.out.println("======================catch:"
					+ "Inside DailySchedulerDaoImpl updateCuReportEmailStatus ");
			Session session = sessionFactory.getCurrentSession();
			EodCustomerDailyReort email = (EodCustomerDailyReort) session.load(EodCustomerDailyReort.class,
					new Integer(reportId));
			if (null != email) {
				email.setEmailStatus("Sent");
				session.update(email);
			}

		} catch (NoResultException nre) {
			nre.printStackTrace();
		}
	}
	public List<CustomerBankMonthlyReort> getMonthlyReport() {
		// TODO Auto-generated method stub
		List<CustomerBankMonthlyReort> result = new ArrayList<>();
		
				try {
					Session session = sessionFactory.getCurrentSession();
					Query query = session.createQuery(
							"from CustomerBankMonthlyReort cb where cb.emailstatus= :emailstatus");

					query.setParameter("emailstatus", "Pending");
					result= query.getResultList();
				} catch (NoResultException nre) {
					nre.printStackTrace();
					return null;
				}
				return result;
	}
	
	public List<BankMonthlyReport> getBankMonthlyReport() {
		// TODO Auto-generated method stub
		List<BankMonthlyReport> result = new ArrayList<>();
		
				try {
					Session session = sessionFactory.getCurrentSession();
					Query query = session.createQuery(
							"from BankMonthlyReport cb where cb.emailstatus= :emailstatus");

					query.setParameter("emailstatus", "Pending");
					result= query.getResultList();
				} catch (NoResultException nre) {
					nre.printStackTrace();
					return null;
				}
				return result;
	}

	
}

