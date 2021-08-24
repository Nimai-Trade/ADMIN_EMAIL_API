package com.nimai.lc.service;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.ParameterMode;
import javax.persistence.StoredProcedureQuery;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nimai.lc.entity.ReferCumulativetxnvsTrxnCount;
import com.nimai.lc.entity.ReferDashboardCount;
import com.nimai.lc.entity.ReferEarnings;
import com.nimai.lc.entity.ReferMainDashboard;
import com.nimai.lc.repository.ReferCumulativetrxnVstrxnCountRepo;
import com.nimai.lc.repository.ReferEarningsRepo;
import com.nimai.lc.repository.ReferdashboardCountRepo;

@Service
public class ReferDashboardServiceImpl implements ReferDashboardService{
	
	@Autowired
	EntityManagerFactory emFactory;
	
	@Autowired
	ReferCumulativetrxnVstrxnCountRepo refercumulativetrxnrepo;
	
	@Autowired
	ReferdashboardCountRepo referdsbrdcountrepo;
	
	@Autowired
	ReferEarningsRepo referearningrepo;

	@Override
	public ReferMainDashboard referDashBoard(String userId, String year) {
		
		EntityManager entityManager1 = emFactory.createEntityManager();
		ReferMainDashboard mbd=new ReferMainDashboard();
		
		try
		{ 
		StoredProcedureQuery storedProcedureQuery = entityManager1
		.createStoredProcedureQuery("Refer_Cumulative_Trxn_Amount_Vs_Trxn_Count", ReferCumulativetxnvsTrxnCount.class);
		storedProcedureQuery.registerStoredProcedureParameter("inp_userid", String.class, ParameterMode.IN);
		storedProcedureQuery.registerStoredProcedureParameter("inp_year", String.class, ParameterMode.IN);
		storedProcedureQuery.setParameter("inp_userid", userId);
		storedProcedureQuery.setParameter("inp_year", year);
		
		storedProcedureQuery.execute();
		List<ReferCumulativetxnvsTrxnCount> refercumulativetrxn = storedProcedureQuery.getResultList();
		mbd.setReferCumulativetrxn(refercumulativetrxn);
		
		
		StoredProcedureQuery storedProcedureQuery1= entityManager1
		.createStoredProcedureQuery("Refer_Dashboard_Count", ReferDashboardCount.class);
		storedProcedureQuery1.registerStoredProcedureParameter("i_user_id", String.class, ParameterMode.IN);
		storedProcedureQuery1.setParameter("i_user_id", userId);
		storedProcedureQuery1.execute();
		List<ReferDashboardCount> refrdsbrdcount = storedProcedureQuery1.getResultList();
		mbd.setReferdashbrdCount(refrdsbrdcount);
				
		   StoredProcedureQuery storedProcedureQuery2 = entityManager1
	   .createStoredProcedureQuery("Refer_Earning_Procedure", ReferEarnings.class);
	   storedProcedureQuery2.registerStoredProcedureParameter("i_refer_id", String.class, ParameterMode.IN);
		storedProcedureQuery2.registerStoredProcedureParameter("i_year", String.class, ParameterMode.IN);
		storedProcedureQuery2.setParameter("i_refer_id", userId);
		storedProcedureQuery2.setParameter("i_year", year);
		storedProcedureQuery2.execute();
		List<ReferEarnings> referernings = storedProcedureQuery2.getResultList();
		mbd.setReferEarnings(referernings);
		
		
		return mbd;
		
		
	}
	catch (Exception e) 
	{
		System.out.println(""+e.getMessage());
	}
	finally 
	{
		entityManager1.close();

	}
	return null;
	}
}