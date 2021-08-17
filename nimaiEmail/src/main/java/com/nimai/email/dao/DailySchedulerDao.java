package com.nimai.email.dao;




import java.util.Date;

import java.util.List;

import com.nimai.email.entity.AdminDailyCountDetailsBean;
import com.nimai.email.entity.AdminRmWiseCount;
import com.nimai.email.entity.NimaiMEmployee;
import com.nimai.email.entity.NimaiSubscriptionDetails;

public interface DailySchedulerDao {

	List<NimaiSubscriptionDetails> getPlanDetails(Date date,Date sevenDays,Date thirtydaysEndDate,String renewalStatus );
List<AdminRmWiseCount> getRmCount(Date date);
AdminDailyCountDetailsBean getDailyCountDetails(Date date);
List<NimaiMEmployee> findManagementEmailIds();

}
