package com.nimai.email.dao;

import java.util.ArrayList;



import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/*//
//import com.cms.admin.constant.Constants;
//import com.cms.admin.email.EmailDao;
//import com.cms.admin.service.LoggingService;
*/import com.nimai.email.entity.EmailDetails;

@Transactional
@Repository
public class EmailDao {
	private static Logger logger = LoggerFactory.getLogger(EmailDao.class);

	@Autowired
	private SessionFactory sessionFactory;
	
//	@Autowired
//	private LoggingService loggingService;

//	public void setSessionFactory(SessionFactory sf) {
//		this.sessionFactory = sf;
//	}

	public int insertMailDetails(EmailDetails empdetails) {
		try {
			Session session = this.sessionFactory.getCurrentSession();
			int i = (Integer) session.save(empdetails);
			logger.info(" insertMailDetails end || i : " + i);
			return i;
		} catch (Exception e) {
			e.printStackTrace(System.out);
//			loggingService.saveExceptionLog("mail001", loggingService.stackTraceToString(e), "Error while inserting Data into email details table.", "", "", "", "");
			return 0;
		} finally {

		}
	}

	public ArrayList<EmailDetails> getAllemail() {
		System.out.println("getAllemail to Send");
		try {
			String hql = "FROM EmailDetails where emailSendFlg=:emailSendFlg ";
			Query query = this.sessionFactory.getCurrentSession().createQuery(hql);
			query.setInteger("emailSendFlg", com.nimai.email.constants.Constants.EMAIL_NOT_SEND_FLG);
			@SuppressWarnings("unchecked")
			ArrayList<EmailDetails> results = (ArrayList<EmailDetails>) query.list();
			logger.debug(" getAllemail end || results : " + results.size());
			return results;
		} catch (Exception e) {
			e.printStackTrace();
//			loggingService.saveExceptionLog("mail002", loggingService.stackTraceToString(e), "Error while getting email details table.", "", "", "", "");
			return null;
		} finally {

		}
	}

	public long updateMailDetails(int emaildetailId) {

		logger.debug(" updateMailDetails start || emaildetailId : " + emaildetailId);
		try {
			Query query = null;

			query = sessionFactory.getCurrentSession().createQuery(
					"update EmailDetails set emailSendFlg=:emailflg where emailDetailsId =:emaildetailsid ");
			query.setParameter("emailflg", com.nimai.email.constants.Constants.EMAIL_SEND_FLG);
			query.setInteger("emaildetailsid", emaildetailId);
			long result = query.executeUpdate();
			logger.debug(" updateMailDetails end || result : " + result);
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			//loggingService.saveExceptionLog("mail003", loggingService.stackTraceToString(e), "Error while updating email details table.", "", "", "", "");
			return 0;
		} finally {

		}
	}


}
