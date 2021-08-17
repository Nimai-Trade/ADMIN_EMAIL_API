package com.nimai.email.dao;

import javax.persistence.NoResultException;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.nimai.email.entity.EmailComponentMaster;
import com.nimai.email.entity.NimaiEmailSchedulerAlertToBanks;

@Repository
@Transactional
public class EmailConfigurationdaoImpl {
	

    private static Logger logger =  LoggerFactory.getLogger(EmailConfigurationdaoImpl.class);

    @Autowired
    private SessionFactory sessionFactory;
     

    public EmailComponentMaster findEventConfiguration(String emailEventName) {
        
        System.out.println("In findEventConfiguration...."+emailEventName);
        EmailComponentMaster emailComponentmaster = new EmailComponentMaster();
        try {
//            Query q = sessionFactory.getCurrentSession().createQuery("from EmailComponentMaster where EmailEventMaster.emailEventName=:emailEventName");
//            q.setString("emailEventName", emailEventName);
        	Session session=sessionFactory.getCurrentSession();
        	Query query=session.createQuery("from EmailComponentMaster where emailEventMaster.emailEventName=:emailEventName");
        	query.setParameter("emailEventName", emailEventName);
            emailComponentmaster = (EmailComponentMaster) query.uniqueResult();
           System.out.println("emailComponentmaster..."+emailComponentmaster.getEmailFrom());
        } catch (Exception e) {
            System.out.println("findEventConfiguration exception " + e);           
            e.printStackTrace();
            return null;
        }
        return emailComponentmaster;
    }

    public EmailComponentMaster findByID(Long id) {

        EmailComponentMaster emailcomponentmaster = new EmailComponentMaster();
        try {
            Query q = sessionFactory.getCurrentSession().createQuery("from EmailComponentMaster where eventId=:eventId");
            q.setParameter("eventId", id.intValue());
            emailcomponentmaster = (EmailComponentMaster) q.uniqueResult();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return emailcomponentmaster;
    }
    
    public EmailComponentMaster findEventConfigurationId() {
        
//        System.out.println("In findEventConfiguration...."+emailEventName);
        EmailComponentMaster emailComponentmaster = new EmailComponentMaster();
        try {
//            Query q = sessionFactory.getCurrentSession().createQuery("from EmailComponentMaster where EmailEventMaster.emailEventName=:emailEventName");
//            q.setString("emailEventName", emailEventName);
        	Session session=sessionFactory.getCurrentSession();
        	Query query=session.createQuery("from EmailComponentMaster");
//        	query.setParameter("emailEventName", emailEventName);
            emailComponentmaster = (EmailComponentMaster) query.uniqueResult();
            System.out.println("emailComponentmaster..."+emailComponentmaster.getEmailFrom());
        } catch (Exception e) {
            System.out.println("findEventConfiguration exception " + e);           
            e.printStackTrace();
        }
        return emailComponentmaster;
    }

	public EmailComponentMaster saveCCEmails(String emailAddress1, String emailAddress2, String emailAddress3) {
		// TODO Auto-generated method stub
		return null;
	}

	public void saveCCEmails(String ccEmails,int eventId) {
		// TODO Auto-generated method stub
		logger.info("inside save updateCCemail method of EmailconfigurationDaoImpl class");
		try {
			Session session = sessionFactory.getCurrentSession();
			EmailComponentMaster email = (EmailComponentMaster) session
					.load(EmailComponentMaster.class, new Integer(eventId));
			if (null != email) {
				email.setCc(ccEmails);;
				session.update(email);
			}

		} catch (NoResultException nre) {
			nre.printStackTrace();
		}
	}
	public void saveBCCEmails(String BccEmails,int eventId) {
		// TODO Auto-generated method stub
		logger.info("inside save updateCCemail method of EmailconfigurationDaoImpl class");
		try {
			Session session = sessionFactory.getCurrentSession();
			EmailComponentMaster email = (EmailComponentMaster) session
					.load(EmailComponentMaster.class, new Integer(eventId));
			if (null != email) {
				email.setBcc(BccEmails);;
				session.update(email);
			}

		} catch (NoResultException nre) {
			nre.printStackTrace();
		}
	}
public void updateSubject(String subject,int eventId) {
	logger.info("inside save updateSubject method of EmailconfigurationDaoImpl class");
	try {
		Session session = sessionFactory.getCurrentSession();
		EmailComponentMaster email = (EmailComponentMaster) session
				.load(EmailComponentMaster.class, new Integer(eventId));
		if (null != email) {
			email.setSubject(subject);
			session.update(email);
		}

	} catch (NoResultException nre) {
		nre.printStackTrace();
	}
}

}



