package com.nimai.email.dao;

import java.util.ArrayList;

import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.NoResultException;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.transaction.Transactional;

import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.nimai.email.bean.BranchUserPassCodeBean;
import com.nimai.email.entity.InvoiceSequence;
import com.nimai.email.entity.NimaiClient;
import com.nimai.email.entity.NimaiEmailScheduler;
import com.nimai.email.entity.NimaiEmailSchedulerAlertToBanks;
import com.nimai.email.entity.NimaiFSubsidiaries;
import com.nimai.email.entity.NimaiLC;
import com.nimai.email.entity.NimaiMBranch;
import com.nimai.email.entity.NimaiMEmployee;
import com.nimai.email.entity.NimaiMLogin;
import com.nimai.email.entity.NimaiMRefer;
import com.nimai.email.entity.NimaiSubscriptionDetails;
import com.nimai.email.entity.NimaiSubscriptionVas;
import com.nimai.email.entity.NimaiToken;

@Repository
@Transactional(dontRollbackOn = Exception.class)
public class UserServiceDaoImpl implements UserServiceDao {

	private static Logger logger = LoggerFactory.getLogger(UserServiceDaoImpl.class);

	@Autowired
	SessionFactory sessionFactory;

	@Override
	public NimaiMLogin getCustomerDetailsByUserID(String userId) throws Exception {
		NimaiMLogin results = null;
		try {
			Session session = sessionFactory.getCurrentSession();
			Query query = session.createQuery("from NimaiMLogin n where n.userid.userid = :userId ", NimaiMLogin.class);
			query.setParameter("userId", userId);
			results = (NimaiMLogin) query.getSingleResult();
		} catch (NoResultException nre) {
			nre.printStackTrace();
			return null;
		}

		return results;

	}

	@Override
	public NimaiMLogin update(NimaiMLogin nimaiLogin) {
		try {
			Session session = sessionFactory.getCurrentSession();
			session.update(nimaiLogin);
			return nimaiLogin;
		} catch (Exception e) {
			e.printStackTrace();

		}
		return nimaiLogin;

	}

	@Override
	public NimaiClient getClientDetailsbyUserId(String userId) {
		NimaiClient results = null;
		try {
			Session session = sessionFactory.getCurrentSession();
			Query query = session.createQuery("from NimaiClient n where n.userid = :userId ", NimaiClient.class);
			query.setParameter("userId", userId);
			results = (NimaiClient) query.getSingleResult();
		} catch (NoResultException nre) {
			nre.printStackTrace();
			return null;
		}

		return results;

	}

	@Override
	public boolean checkUserTokenKey(String token) {
		try {
			Session session = sessionFactory.getCurrentSession();
			Query query = session.createQuery("FROM NimaiMLogin WHERE token = :token");
			query.setParameter("token", token);
			NimaiMLogin result = (NimaiMLogin) query.getSingleResult();
			if (!result.getToken().equals(token)) {
				return false;
			}
		} catch (NoResultException e) {
			return false;
		}
		return true;
	}

	@Override
	public NimaiMLogin getUserDetailsByTokenKey(String token) {
		NimaiMLogin result = null;
		try {
			Session session = sessionFactory.getCurrentSession();
			Query query = session.createQuery("FROM NimaiMLogin WHERE token = :token");
			query.setParameter("token", token);
			result = (NimaiMLogin) query.getSingleResult();
		} catch (NoResultException nre) {
			nre.printStackTrace();
			return null;
		}

		return result;
	}

	@Override
	public NimaiClient getcliDetailsByEmailId(String emailId) {

		NimaiClient results = null;
		try {
			Session session = sessionFactory.getCurrentSession();
			Query query = session.createQuery("from NimaiClient where emailAddress = :emailId ");
			query.setParameter("emailId", emailId);
			results = (NimaiClient) query.getSingleResult();
		} catch (NoResultException nre) {
			nre.printStackTrace();
			return null;
		}

		return results;

	}

	@Override
	public NimaiFSubsidiaries saveSubsidiaryDetails(NimaiFSubsidiaries subsidiaryDetails) {
		try {
			Session session = sessionFactory.getCurrentSession();
			session.save(subsidiaryDetails);
			return subsidiaryDetails;
		} catch (Exception e) {
			e.printStackTrace();

		}
		return subsidiaryDetails;
	}

	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	@Override
	public NimaiFSubsidiaries getSubsidiaryDetailsByToken(String token) {
		NimaiFSubsidiaries result = null;
		try {
			Session session = sessionFactory.getCurrentSession();
			Query query = session.createQuery("FROM NimaiFSubsidiaries WHERE subsidiaryToken = :token");
			query.setParameter("token", token);
			result = (NimaiFSubsidiaries) query.getSingleResult();
		} catch (NoResultException nre) {
			nre.printStackTrace();
			return null;
		}

		return result;
	}

	@Override
	public boolean isUserIdExisist(String userId) {
		try {
			Session session = sessionFactory.getCurrentSession();
			Query query = session.createQuery("FROM NimaiClient WHERE userid = :userId");
			query.setParameter("userId", userId);
			NimaiClient result = (NimaiClient) query.getSingleResult();
			if (!result.getUserid().equals(userId)) {
				return false;
			}
		} catch (NoResultException e) {
			return false;
		}
		return true;
	}

	@Override
	public NimaiMBranch getBranchUserDetails(String emailId,String userId) {
		NimaiMBranch result = null;
		try {
			Session session = sessionFactory.getCurrentSession();
			Query query = session.createQuery("From NimaiMBranch b WHERE emailId=:emailId and userid=:userId order by b.insertTime desc");
			query.setParameter("emailId", emailId);
			query.setParameter("userId", userId);
			query.setMaxResults(1);
			result = (NimaiMBranch) query.getSingleResult();
		} catch (NoResultException re) {
			return null;
		}
		return result;
	}

	@Override
	public NimaiMBranch updateBranchUser(NimaiMBranch branchUserDetails) {
		try {
			Session session = sessionFactory.getCurrentSession();
			session.saveOrUpdate(branchUserDetails);
			return branchUserDetails;
		} catch (Exception e) {
			e.printStackTrace();

		}
		return branchUserDetails;
	}

	@Override
	public NimaiMBranch updateBranchUserDetails(NimaiMBranch branchUserDetails) {
		try {
			Session session = sessionFactory.getCurrentSession();
			session.saveOrUpdate(branchUserDetails);
			return branchUserDetails;
		} catch (Exception e) {
			e.printStackTrace();

		}
		return branchUserDetails;

	}

	@Override
	public NimaiMRefer saveReferTokenDetails(NimaiMRefer referDetails) {
		try {
			Session session = sessionFactory.getCurrentSession();
			session.save(referDetails);
			return referDetails;
		} catch (Exception e) {
			e.printStackTrace();

		}
		return referDetails;
	}

	@Override
	public NimaiMRefer getReferDetailsByToken(String token) {
		NimaiMRefer result = null;
		try {
			Session session = sessionFactory.getCurrentSession();
			Query query = session.createQuery("FROM NimaiMRefer WHERE token = :token");
			query.setParameter("token", token);
			result = (NimaiMRefer) query.getSingleResult();
		} catch (NoResultException nre) {
			nre.printStackTrace();
			return null;
		}
		return result;

	}

	@Override
	public NimaiMBranch getbranchDetailsByTokenPassCode(String token, String passcode) {
		NimaiMBranch result = null;
		try {
			Session session = sessionFactory.getCurrentSession();
			Query query = session
					.createQuery("FROM NimaiMBranch WHERE token = :token and passcodeValue=:passcode order by id desc");
			query.setParameter("token", token);
			query.setParameter("passcode", passcode);
			query.setMaxResults(1);
			result = (NimaiMBranch) query.getSingleResult();
		} catch (NoResultException nre) {
			nre.printStackTrace();
			return null;
		}
		return result;
	}

	@Override
	public NimaiMBranch getbranchDetailsByToken(String token) {
		NimaiMBranch result = null;
		try {
			Session session = sessionFactory.getCurrentSession();
			Query query = session.createQuery("FROM NimaiMBranch WHERE token = :token");
			query.setParameter("token", token);

			result = (NimaiMBranch) query.getSingleResult();
		} catch (NoResultException nre) {
			nre.printStackTrace();
			return null;
		}
		return result;
	}

	@Override
	public NimaiMBranch updateBranchUserDetails(String emailId, Date dnow, String passcodeValue, String token) {
		NimaiMBranch result = null;
		try {
			Session session = sessionFactory.getCurrentSession();
			Query query = session.createQuery(
					"UPDATE NimaiMBranch nb set nb.passcodeValue= :passcodeValue,nb.insertTime= :dnow,nb.modifyTime= :dnow,nb.token= :token where nb.emailId= :emailId");
			query.setParameter("passcodeValue", passcodeValue);
			query.setParameter("dnow", dnow);
			query.setParameter("emailId", emailId);
			query.setParameter("token", token);

			query.executeUpdate();
		} catch (NoResultException nre) {
			nre.printStackTrace();
			return null;
		}
		return result;
	}

	@Override
	public void updatepasscodeFlag(String userId, String flag) {

		try {
			Session session = sessionFactory.getCurrentSession();
			Query query = session
					.createQuery("UPDATE NimaiToken nb set nb.isInvalidCaptcha= :flag where nb.userId= :userId");
			System.out.println("set parameters===================" + userId);
			System.out.println("set parameters===================" + flag);
			query.setParameter("flag", flag);
			query.setParameter("userId", userId);
			query.executeUpdate();
		} catch (NoResultException nre) {
			nre.printStackTrace();

		}

	}

	public NimaiMBranch updateBranchUser(String emailId, Date dnow, String passcodeValue, String token) {
		NimaiMBranch result = null;
		try {
			Session session = sessionFactory.getCurrentSession();
			Query query = session.createQuery(
					"UPDATE NimaiMBranch nb set nb.passcodeValue= :passcodeValue,  nb.insertTime= :dnow, nb.modifyTime= :dnow,nb.token= :token where nb.emailId= :emailId");
			query.setParameter("passcodeValue", passcodeValue);
			query.setParameter("dnow", dnow);
			query.setParameter("emailId", emailId);
			query.setParameter("token", token);

			query.executeUpdate();
		} catch (NoResultException nre) {
			nre.printStackTrace();
			return null;
		}
		return result;
	}

	@Override
	public NimaiMBranch updateBranchUser(String passcode, String tokenKey, Date insertedDate, String emailId, int id,
			Date tokenExpiry) {
		Session session = null;
		boolean newSession = false;
		try {
			session = sessionFactory.getCurrentSession();
		} catch (NoResultException nre) {
			nre.printStackTrace();
			getSessionFactory().openSession();
			newSession = true;

		}
		Query query = session.createQuery("UPDATE NimaiMBranch nb set nb.passcodeValue= :passcode,nb.token= :tokenKey ,"
				+ "nb.insertTime= :insertedDate, nb.emailId= :emailId,nb.expryTime= :tokenExpiry where nb.id= :id");
		query.setParameter("passcode", passcode);
		query.setParameter("tokenKey", tokenKey);
		query.setParameter("insertedDate", insertedDate);
		query.setParameter("emailId", emailId);
		query.setParameter("tokenExpiry", tokenExpiry);
		query.setParameter("id", id);

		query.executeUpdate();
		if (newSession)
			session.close();
		return null;

	}

	@Override
	public boolean isEntryPresent(int id) {
		try {
			Session session = sessionFactory.getCurrentSession();
			Query query = session.createQuery("FROM NimaiMBranch WHERE id = :id");
			query.setParameter("id", id);
			NimaiMBranch result = (NimaiMBranch) query.getSingleResult();

		} catch (NoResultException e) {
			return false;
		}
		return true;

	}

	@Override
	public NimaiLC getTransactioDetailsByTransIs(String transactionid) {
		NimaiLC results = null;
		try {
			Session session = sessionFactory.getCurrentSession();
			Query query = session.createQuery("from NimaiLC n where n.transactionId = :transactionid ", NimaiLC.class);
			query.setParameter("transactionid", transactionid);
			results = (NimaiLC) query.getSingleResult();
		} catch (NoResultException nre) {
			nre.printStackTrace();
			return null;
		}

		return results;
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<NimaiEmailScheduler> getSchedulerDetails() {
		// TODO Auto-generated method stub
		logger.info("inside save getTransactionDetail method of UserServiceDaoImpl class");
		List<NimaiEmailScheduler> emailDetailsScheduled = new ArrayList<>();
		try {
			Session session = sessionFactory.getCurrentSession();
			Query query = session.createQuery("from NimaiEmailScheduler nb where nb.emailStatus = :emailStatus");
			query.setParameter("emailStatus", "pending");
			emailDetailsScheduled = query.getResultList();
			return emailDetailsScheduled;
		} catch (NoResultException nre) {
			nre.printStackTrace();
			return null;
		}
	}

	@Override
	@Transactional(dontRollbackOn = Exception.class)
	public void updateEmailStatus(int scedulerid) {
		// TODO Auto-generated method stub
		logger.info("inside save updateEmailTransactionDetail method of UserServiceDaoImpl class");
		try {
			Session session = sessionFactory.getCurrentSession();
			NimaiEmailScheduler email = (NimaiEmailScheduler) session.load(NimaiEmailScheduler.class,
					new Integer(scedulerid));
			if (null != email) {
				email.setEmailStatus("Sent");
				session.update(email);
			}

		} catch (NoResultException nre) {
			nre.printStackTrace();
		}

	}

	@Override
	public void updateInvalidIdEmailFlag(int scedulerid, String emailstatus) {
		// TODO Auto-generated method stub
		logger.info("inside save updateEmailFlag method of BanksAlertDaoImpl class");
		try {
			Session session = sessionFactory.getCurrentSession();
			NimaiEmailScheduler email = (NimaiEmailScheduler) session.load(NimaiEmailScheduler.class,
					new Integer(scedulerid));
			if (null != email) {
				email.setEmailStatus(emailstatus);
				session.update(email);
			}

		} catch (NoResultException nre) {
			nre.printStackTrace();
		}
	}

	@Override
	public void updateReferTokenDetails(Date tokenExpiry, String refertokenKey, NimaiClient clientUseId,
			Date insertedDate, String emailId, int referenceId) {
		// TODO Auto-generated method stub

		try {
			Session session = sessionFactory.getCurrentSession();
			NimaiMRefer refer = (NimaiMRefer) session.load(NimaiMRefer.class, new Integer(referenceId));
			if (null != refer) {
				refer.setTokenExpiryTime(tokenExpiry);
				refer.setToken(refertokenKey);
				refer.setUserid(clientUseId);
				refer.setTokenInsertedDate(insertedDate);
				refer.setEmailAddress(emailId);
				session.update(refer);
			}

		} catch (NoResultException nre) {
			nre.printStackTrace();
		}

	}

	@Override
	public void updateSubsidiaryTokenDetails(Date tokenExpiry, String tokenKey, String subsiCustId) {
		// TODO Auto-generated method stub
		Session session = null;
		boolean newSession = false;
		try {
			session = sessionFactory.getCurrentSession();
		} catch (NoResultException nre) {
			nre.printStackTrace();
			getSessionFactory().openSession();
			newSession = true;

		}
		// int query1 = session.createQuery("select n.loginId from NimaiMLogin n where
		// n.userid= :subsiCustId order by loginId desc");
		NimaiMLogin result = null;
		Query query1 = session
				.createQuery("From NimaiMLogin b WHERE b.userid.userid=:subsiCustId order by b.loginId desc");
		query1.setParameter("subsiCustId", subsiCustId);
		query1.setMaxResults(1);
		result = (NimaiMLogin) query1.getSingleResult();
		Long lid = result.getLoginId();
		Query query = session.createQuery("UPDATE NimaiMLogin nb set nb.token= :tokenKey,"
				+ "nb.tokenExpiryDate= :tokenExpiry where nb.loginId= :lid");

		query.setParameter("tokenKey", tokenKey);
		query.setParameter("tokenExpiry", tokenExpiry);
		query.setParameter("lid", lid);
		query.setMaxResults(1);
		query.executeUpdate();
		if (newSession)
			session.close();
		// return null;

	}

	@Override
	public NimaiMRefer getreferDetails(int referenceId) {
		NimaiMRefer result = null;
		try {
			Session session = sessionFactory.getCurrentSession();
			Query query = session.createQuery("From NimaiMRefer b WHERE b.id=:referenceId");
			query.setParameter("referenceId", referenceId);
			query.setMaxResults(1);
			result = (NimaiMRefer) query.getSingleResult();
		} catch (NoResultException re) {
			return null;
		}
		return result;
	}

	@Override
	public NimaiMBranch getBranchUserbyUserId(String userid) {
		NimaiMBranch results = null;
		try {
			Session session = sessionFactory.getCurrentSession();
			Query query = session.createQuery("from NimaiMBranch n where n.userid = :userid ", NimaiMBranch.class);
			query.setParameter("userid", userid);
			results = (NimaiMBranch) query.getSingleResult();
		} catch (NoResultException nre) {
			nre.printStackTrace();
			return null;
		}

		return results;

	}

	@Override
	public void updateEmailStatus(String userid) {
		Session session = null;
		boolean newSession = false;
		try {
			session = sessionFactory.getCurrentSession();
		} catch (NoResultException nre) {
			nre.printStackTrace();
			getSessionFactory().openSession();
			newSession = true;

		}
		Query query = session
				.createQuery("UPDATE NimaiEmailScheduler nb set nb.emailStatus= :emailStatus where nb.userid= :userid");
		query.setParameter("emailStatus", "sent");
		query.setParameter("userid", userid);
		query.executeUpdate();
		if (newSession)
			session.close();

	}

	@Override
	public void updateLoginEmailStatus(String userid) {
		// TODO Auto-generated method stub
		Session session = null;
		boolean newSession = false;
		try {
			session = sessionFactory.getCurrentSession();
		} catch (NoResultException nre) {
			nre.printStackTrace();
			getSessionFactory().openSession();
			newSession = true;

		}
		Query query = session
				.createQuery("UPDATE NimaiMLogin nb set nb.emailStatus= :emailStatus where nb.userid.userid= :userid");
		query.setParameter("emailStatus", "sent");
		query.setParameter("userid", userid);
		query.executeUpdate();
		if (newSession)
			session.close();

	}

	@Override
	public List<NimaiLC> getCustTransactionList(Date todaysDate) {
		// TODO Auto-generated method stub
		List<NimaiLC> results = null;
		try {
			Session session = sessionFactory.getCurrentSession();
			String hql = "from NimaiLC where insertedDate > :date";
			Query query = session.createQuery(hql);
			query.setParameter("date", todaysDate);
			results = query.getResultList();
		} catch (NoResultException nre) {
			nre.printStackTrace();
			return null;
		}

		return results;

	}

	@Override
	public void updateReTokenLoginTable(String refertokenKey, Date insertedate, Date expiryTime, String userid) {
		// TODO Auto-generated method stub
		Session session = null;
		boolean newSession = false;
		try {
			session = sessionFactory.getCurrentSession();
		} catch (NoResultException nre) {
			nre.printStackTrace();
			getSessionFactory().openSession();
			newSession = true;

		}
		Query query = session.createQuery("UPDATE NimaiMLogin nl set nl.token= :token,"
				+ "nl.insertedDate= :insertedDate,nl.tokenExpiryDate= :tokenExpiryDate where nl.userid.userid= :userId");
		query.setParameter("token", refertokenKey);
		query.setParameter("insertedDate", insertedate);
		query.setParameter("tokenExpiryDate", expiryTime);
		query.setParameter("userId", userid);

		query.executeUpdate();
		if (newSession)
			session.close();

	}

	@Override
	public NimaiClient getClientDetailsBySubsidiaryId(String emailId) {
		// TODO Auto-generated method stub
		NimaiClient results = null;
		try {
			Session session = sessionFactory.getCurrentSession();
			Query query = session.createQuery("from NimaiClient n where n.emailAddress = :emailId ", NimaiClient.class);
			query.setParameter("emailId", emailId);
			results = (NimaiClient) query.getSingleResult();
		} catch (NoResultException nre) {
			nre.printStackTrace();
			return null;
		}

		return results;
	}

	@Override
	public NimaiMBranch saveBranchUser(NimaiMBranch branchUserDetails) {
		try {
			Session session = sessionFactory.getCurrentSession();
			session.save(branchUserDetails);

		} catch (Exception e) {
			e.printStackTrace();

		}
		return branchUserDetails;

	}

	@Override
	public NimaiMRefer getreferDetailsByUserDetails(String emailAddress) {

		NimaiMRefer results = null;
		try {
			Session session = sessionFactory.getCurrentSession();
			Query query = session.createQuery("from NimaiMRefer where emailAddress = :emailId ");
			query.setParameter("emailId", emailAddress);
			results = (NimaiMRefer) query.getSingleResult();
		} catch (NoResultException nre) {
			nre.printStackTrace();
			return null;
		}

		return results;

	}

	@Override
	public NimaiEmailScheduler saveSubDetails(NimaiEmailScheduler schedularData) {
		// TODO Auto-generated method stub Session session = null;
		Session session = null;
		boolean newSession = false;
		try {
			session = sessionFactory.getCurrentSession();
		} catch (NoResultException nre) {
			nre.printStackTrace();
			getSessionFactory().openSession();
			newSession = true;

		}
		session.save(schedularData);
		if (newSession)
			session.close();
		return schedularData;

	}

	@Override
	public NimaiSubscriptionDetails getsPlanDetailsBySubscriptionId(String subscriptionId) {
		NimaiSubscriptionDetails results = null;
		try {
			Session session = sessionFactory.getCurrentSession();
			Query query = session.createQuery(
					"from NimaiSubscriptionDetails n where n.subscriptionId = :subscriptionId ",
					NimaiSubscriptionDetails.class);
			query.setParameter("subscriptionId", subscriptionId);

			results = (NimaiSubscriptionDetails) query.getSingleResult();
		} catch (NoResultException nre) {
			nre.printStackTrace();
			return null;
		}

		return results;
	}

	@Override
	public InvoiceSequence getSequence() {

		try {

			Session session = sessionFactory.getCurrentSession();

			Query query = session.createQuery("from InvoiceSequence", InvoiceSequence.class);

			InvoiceSequence results = (InvoiceSequence) query.getSingleResult();
			return results;
		} catch (NoResultException nre) {
			nre.printStackTrace();
			return null;
		}

	}

	@Override
	public NimaiMEmployee getempDetailsByEmpCode(String empCode) {
		NimaiMEmployee results = null;
		try {
			Session session = sessionFactory.getCurrentSession();
			Query query = session.createQuery("from NimaiMEmployee n where n.empCode = :empCode ",
					NimaiMEmployee.class);
			query.setParameter("empCode", empCode);
			results = (NimaiMEmployee) query.getSingleResult();
		} catch (NoResultException nre) {
			nre.printStackTrace();
			return null;
		}
		return results;

	}

	@Override
	public NimaiSubscriptionDetails getSplanDetails(String subscriptionId, String userId) {
		NimaiSubscriptionDetails results = null;
		try {
			Session session = sessionFactory.getCurrentSession();
			Query query = session.createQuery(
					"from NimaiSubscriptionDetails n where n.subscriptionId = :subscriptionId and n.userid.userid=:userId and n.status='ACTIVE'",
					NimaiSubscriptionDetails.class);
			query.setParameter("subscriptionId", subscriptionId);
			query.setParameter("userId", userId);
			// query.setParameter("status", "ACTIVE");
			System.out.println(query.getSingleResult());
			results = (NimaiSubscriptionDetails) query.getSingleResult();
			System.out.println("results :" + results.toString());
			return results;
		} catch (Exception nre) {
			nre.printStackTrace();
			return null;
		}

	}

	@Override
	public NimaiMBranch updateBranchUserDetails(String token, Date dnow, int passcodeCount) {
		NimaiMBranch result = null;
		try {
			Session session = sessionFactory.getCurrentSession();
			Query query = session.createQuery(
					"UPDATE NimaiMBranch nb set nb.insertTime= :dnow,nb.modifyTime= :dnow,nb.passcodeCount= :passcodeCOunt where nb.token= :token");
			query.setParameter("token", token);
			query.setParameter("dnow", dnow);
			query.setParameter("passcodeCOunt", passcodeCount);
			query.executeUpdate();
		} catch (NoResultException nre) {
			nre.printStackTrace();
			return null;
		}
		return result;
	}

	public NimaiMBranch updatePassCount(Date dnow, int passcodeCount, int id) {
		NimaiMBranch branchDetails = null;
		try {
			Session session = sessionFactory.getCurrentSession();
			branchDetails = (NimaiMBranch) session.load(NimaiMBranch.class, new Integer(id));
			if (null != branchDetails) {
				branchDetails.setInsertTime(dnow);
				;
				branchDetails.setPasscodeCount(passcodeCount);
				;

			}

		} catch (NoResultException nre) {
			nre.printStackTrace();
		}
		return branchDetails;
	}

	@Override
	public NimaiMBranch updateBranchUserUnlockTime(String token, Date dnow, int passcodecount, Date accounUnlockTime) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public NimaiMBranch getBranchUserDetailsById(int i) {
		NimaiMBranch results = null;
		try {
			Session session = sessionFactory.getCurrentSession();
			Query query = session.createQuery("from NimaiMBranch n where n.id = :userId ", NimaiMBranch.class);
			query.setParameter("userId", i);
			results = (NimaiMBranch) query.getSingleResult();
		} catch (NoResultException nre) {
			nre.printStackTrace();
			return null;
		}

		return results;
	}

	@Override
	public NimaiMBranch getBranchUserDetailsByEmaild(String emailId) {
		NimaiMBranch results = null;
		try {
			Session session = sessionFactory.getCurrentSession();
			Query query = session.createQuery("from NimaiMLogin n where n.emailId = :userId ", NimaiMBranch.class);
			query.setParameter("userId", emailId);
			results = (NimaiMBranch) query.getSingleResult();
		} catch (NoResultException nre) {
			nre.printStackTrace();
			return null;
		}

		return results;
	}

	@Override
	public NimaiMLogin existsByEmpCode(String empCode) {
		NimaiMLogin results = null;
		try {
			Session session = sessionFactory.getCurrentSession();
			Query query = session.createQuery("from NimaiMLogin n where n.empCode.empCode = :userId ",
					NimaiMLogin.class);
			query.setParameter("userId", empCode);
			results = (NimaiMLogin) query.getSingleResult();
		} catch (NoResultException nre) {
			nre.printStackTrace();
			return null;
		}

		return results;
	}

	@Override
	public NimaiMBranch getbranchDetailsByToken(String token, String passcode, String tokenke) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public NimaiToken isTokenExists(String userId, String token) {
		// TODO Auto-generated method stub

		try {
			Session session = sessionFactory.getCurrentSession();
//			Query query = session.createQuery("from NimaiToken where userId= :userId and token= :token and isInvalidCaptcha= :flag",
//					NimaiToken.class);

			Query query = session.createQuery("from NimaiToken nm WHERE nm.userId= :userId AND nm.token=:token");

			query.setParameter("token", token);
			query.setParameter("userId", userId);
//			String flag="false";
//			query.setParameter("captcha", flag);
			NimaiToken results = (NimaiToken) query.getSingleResult();
			return results;
		} catch (NoResultException nre) {
			nre.printStackTrace();
			return null;
		}

	}

	@Override
	public NimaiMBranch getbranchDetailsByToPassCode(String token) {
		NimaiMBranch result = null;
		try {
			Session session = sessionFactory.getCurrentSession();
			Query query = session.createQuery("FROM NimaiMBranch WHERE token = :token");
			query.setParameter("token", token);
			result = (NimaiMBranch) query.getSingleResult();
		} catch (NoResultException nre) {
			nre.printStackTrace();
			return null;
		}
		return result;
	}

	@Override
	public NimaiMBranch getbranchDetailsById(int id) {
		// TODO Auto-generated method stub
		NimaiMBranch results = null;
		try {
			Session session = sessionFactory.getCurrentSession();
			Query query = session.createQuery("from NimaiMBranch n where n.brId = :id ", NimaiMBranch.class);
			query.setParameter("id", id);
			results = (NimaiMBranch) query.getSingleResult();
		} catch (NoResultException nre) {
			nre.printStackTrace();
			return null;
		}

		return results;
	}

	@Override
	public void updateInvalidCaptcha(BranchUserPassCodeBean passCodeBean, String flag) {
		// TODO Auto-generated method stub
		logger.info("inside save updateEmailTransactionDetail method of UserServiceDaoImpl class");
		
		try {
			Session session = sessionFactory.getCurrentSession();
			Query query = session
					.createQuery("UPDATE NimaiToken nb set nb.isInvalidCaptcha= :flag where nb.userId= :userId");
			query.setParameter("userId", passCodeBean.getUserId());
			query.setParameter("flag", flag);

			query.executeUpdate();

		} catch (NoResultException nre) {
			nre.printStackTrace();
		}
		
		
//		try {
//			Session session = sessionFactory.getCurrentSession();
//			NimaiToken email = (NimaiToken) session.load(NimaiToken.class, (passCodeBean.getUserId()));
//			if (null != email) {
//
//				email.setIsInvalidCaptcha(flag);
//				session.update(email);
//			}
//
//		} catch (NoResultException nre) {
//			nre.printStackTrace();
//		}

	}

	@Override
	public NimaiMBranch getInvalidCaptchaStatus(BranchUserPassCodeBean passCodeBean) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public NimaiToken isTokenExists(String userId) {
		NimaiToken result = null;
		try {
			Session session = sessionFactory.getCurrentSession();
			Query query = session.createQuery("FROM NimaiToken WHERE userId = :userId");
			query.setParameter("userId", userId);
			result = (NimaiToken) query.getSingleResult();
		} catch (NoResultException nre) {
			nre.printStackTrace();
			return null;
		}
		return result;
	}

	@Override
	public void updateInvalCaptcha(String userId, String flag) {
		// TODO Auto-generated method stub
		logger.info("inside save updateEmailTransactionDetail method of UserServiceDaoImpl class");
		try {
			Session session = sessionFactory.getCurrentSession();
			Query query = session
					.createQuery("UPDATE NimaiToken nb set nb.isInvalidCaptcha= :flag where nb.userId= :userId");
			query.setParameter("userId", userId);
			query.setParameter("flag", flag);

			query.executeUpdate();

		} catch (NoResultException nre) {
			nre.printStackTrace();
		}
	}

	@Override
	public NimaiSubscriptionDetails getSplanDetails(String userid) {
		// TODO Auto-generated method stub
		NimaiSubscriptionDetails results = null;
		try {
			Session session = sessionFactory.getCurrentSession();
			Query query = session.createQuery(
					"from NimaiSubscriptionDetails n where n.userid.userid = :subscriptionId ",
					NimaiSubscriptionDetails.class);
			query.setParameter("userid", userid);
			String Status = "ACTIVE";
			query.setParameter("Status", Status);

			results = (NimaiSubscriptionDetails) query.getSingleResult();
		} catch (NoResultException nre) {
			nre.printStackTrace();
			return null;
		}

		return results;
	}

	@Override
	public NimaiSubscriptionVas getVasDetails(String subscriptionName, String userid) {
		// TODO Auto-generated method stub
		NimaiSubscriptionVas results = null;
		try {
			Session session = sessionFactory.getCurrentSession();
			Query query = session.createQuery(
					"from NimaiSubscriptionVas n where n.planName = :subscriptionName and n.userid.userid=:userId and n.status='ACTIVE'",
					NimaiSubscriptionVas.class);
			query.setParameter("subscriptionName", subscriptionName);
			query.setParameter("userId", userid);
			// query.setParameter("status", "ACTIVE");
			System.out.println(query.getSingleResult());
			results = (NimaiSubscriptionVas) query.getSingleResult();
			System.out.println("results :" + results.toString());
			return results;
		} catch (Exception nre) {
			nre.printStackTrace();
			return null;
		}
	}

	@Override
	public NimaiMBranch getBranchUserDetailsByEMpId(String emailAddress, String empId) {
		
		NimaiMBranch result = null;
		try {
			Session session = sessionFactory.getCurrentSession();
			Query query = session.createQuery("From NimaiMBranch b WHERE emailId=:emailAddress and employeeId=:empId order by b.insertTime desc");
			query.setParameter("emailAddress", emailAddress);
			query.setParameter("empId", empId);
			query.setMaxResults(1);
			result = (NimaiMBranch) query.getSingleResult();
		} catch (NoResultException re) {
			return null;
		}
		return result;
	}

}
