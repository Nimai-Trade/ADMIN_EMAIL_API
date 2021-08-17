package com.nimai.email.dao;





import java.util.HashMap;



import java.util.Iterator;
import java.util.List;


import org.hibernate.query.Query;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.nimai.email.constants.ConfigurationValues;
import com.nimai.email.constants.Constants;
import com.nimai.email.entity.Configuration;



//import com.cms.admin.constant.ConfigurationValues;

/**
 *
 * @author sahadeo.naik
 */
//import com.cms.admin.constant.ConfigurationValues;
//import com.cms.admin.constant.Constants;
//import com.cms.admin.entity.Configuration;

@Repository
@Transactional
public class ConfigurationDAO {

    final static Logger logger = LoggerFactory.getLogger(ConfigurationDAO.class);

    @Autowired
    private SessionFactory sessionFactory;

    public ConfigurationValues loadAllValues() {

        logger.debug(" loadAllValues start ");

        ConfigurationValues values = new ConfigurationValues();
        HashMap<String, String> hmapString = null;
        HashMap<String, Integer> hmapInt = null;
        HashMap<String, String> hmapEncrypted = null;
        HashMap<String, Integer> hmapType = null;
        try {
        	Query q = sessionFactory.getCurrentSession().createQuery("from Configuration");
            List<Configuration> list = q.getResultList();
            if (list.size() > 0) {
                hmapString = new HashMap<String, String>();
                hmapInt = new HashMap<String, Integer>();
                hmapEncrypted = new HashMap<String, String>();
                hmapType = new HashMap<String, Integer>();

                for(Configuration configuration:list) {
                	 hmapType.put(configuration.getType() + configuration.getKey(), configuration.getValuetype().intValue());
                     if (configuration.getValuetype().intValue() == Constants.VALUE_STRING) {

                         hmapString.put(configuration.getType() + configuration.getKey(), configuration.getValuestring());
                     } else if (configuration.getValuetype().intValue() == Constants.VALUE_NUMBER) {
                         hmapInt.put(configuration.getType() + configuration.getKey(), configuration.getValuenumber().intValue());
                     } else if (configuration.getValuetype().intValue() == Constants.VALUE_ENCRYPTED) {
                         hmapEncrypted.put(configuration.getType() + configuration.getKey(), configuration.getValuestring());
                     } else {
                         return null;
                     }
                }
                
                
                
                
//                Iterator<Configuration> itr = list.iterator();
//                while (itr.hasNext()) {
//                    Configuration configuration = itr.next();
//                    hmapType.put(configuration.getType() + configuration.getKey(), configuration.getValuetype().intValue());
//                    if (configuration.getValuetype().intValue() == Constants.VALUE_STRING) {
//
//                        hmapString.put(configuration.getType() + configuration.getKey(), configuration.getValuestring());
//                    } else if (configuration.getValuetype().intValue() == Constants.VALUE_NUMBER) {
//                        hmapInt.put(configuration.getType() + configuration.getKey(), configuration.getValuenumber().intValue());
//                    } else if (configuration.getValuetype().intValue() == Constants.VALUE_ENCRYPTED) {
//                        hmapEncrypted.put(configuration.getType() + configuration.getKey(), configuration.getValuestring());
//                    } else {
//                        return null;
//                    }
//                }
                values.setHmapEncrypted(hmapEncrypted);
                values.setHmapInt(hmapInt);
                values.setHmapString(hmapString);
                values.setHmapType(hmapType);
            } else {
                return null;
            }

        } catch (Exception e) {
            logger.error(e.getMessage());
            e.printStackTrace();
            return null;
        } 
        return values;
    }

    public void reset() {

        logger.debug(" reset start ");

        try {
            Query q = sessionFactory.getCurrentSession().createQuery("from Configuration where type=:type and key=:keyname");
            //q.setString("type", "ALL");
            q.setParameter("type", "ALL");
          //  q.setString("keyname", "ALL.RELOAD");
            q.setParameter("keyname", "ALL.RELOAD");
            List<Configuration> list = q.getResultList();

            if (list.size() > 0) {
                Configuration configuration = list.get(0);
                configuration.setValuenumber(Constants.CONFIGURATION_NOTRELOAD);
                sessionFactory.getCurrentSession().update(configuration);
            }

        } catch (Exception e) {
            e.printStackTrace(System.out);
            logger.error(e.getMessage());
            e.printStackTrace();
       } finally {

        }
    }

    public String getStringValue(String type, String keyname) {
        try {
            Query q = sessionFactory.getCurrentSession().createQuery("from Configuration where type=:type and key=:keyname");
            q.setParameter("type", type);
            q.setParameter("keyname", keyname);
            List<Configuration> list = q.getResultList();

            if (list.size() > 0) {
                Configuration configuration = list.get(0);
                if (configuration.getValuetype().intValue() == Constants.VALUE_STRING) {

                    return configuration.getValuestring();
//                } else if (configuration.getValuetype().intValue() == Constants.VALUE_ENCRYPTED) {
//                    ASEUtil r = ASEUtil.getInstance();
//                    String dec = r.decrypt(configuration.getValuestring());
//                    return dec;
                } else {
                    return null;
                }

                /*}*/
            } else {
                return null;
            }

        } catch (Exception e) {
            return null;
        } finally {

        }

    }

    public long getNumberValue(String type, String keyname) {
        logger.debug(" getNumberValue start || type : " + type + " || keyname : " + keyname);

        long temp = -1;

        try {
            Query q = sessionFactory.getCurrentSession().createQuery("from Configuration where type=:type and key=:keyname");

            q.setParameter("type", type);
            q.setParameter("keyname", keyname);
            List<Configuration> list = q.list();

            if (list.size() > 0) {
                Configuration configuration = list.get(0);
                if (configuration.getValuetype().intValue() == Constants.VALUE_NUMBER) {
                    temp = configuration.getValuenumber().longValue();
//                } else if (configuration.getValuetype().intValue() == Constants.VALUE_ENCRYPTED) {
//                    ASEUtil r = ASEUtil.getInstance();
//                    String dec = r.decrypt(configuration.getValuestring());
//                    try {
//                        temp = Long.parseLong(dec);
//                    } catch (NumberFormatException nfe) {
//
//                    }
                } else {
                }

            }
          

        } catch (Exception e) {
            logger.error(" getNumberValue end || " + e.getMessage());
        } finally {

        }
        logger.debug(" getNumberValue end || temp : " + temp);
        return temp;
    }
}


