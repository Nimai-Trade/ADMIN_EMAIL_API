package com.nimai.lc.service;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.ParameterMode;
import javax.persistence.StoredProcedureQuery;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nimai.lc.entity.BankDashBoardCount;
import com.nimai.lc.entity.BankDashboardBarChart;
import com.nimai.lc.entity.BankLatestAcceptedTransaction;
import com.nimai.lc.entity.BankMainDashboard;
import com.nimai.lc.entity.MainDashboard;
import com.nimai.lc.entity.PieChartCountry;
import com.nimai.lc.entity.PieChartGoods;
import com.nimai.lc.repository.BankBarChartRepository;
import com.nimai.lc.repository.BankDashboardCountRepository;
import com.nimai.lc.repository.BankLatestAcceptedTransactionRepository;
@Service
public class BankDashBoardServiceImpl implements BankDashBoardService {
	
	@Autowired
	EntityManagerFactory emFactory;
	
    @Autowired
	BankDashboardCountRepository bankcountRepo;
	
	@Autowired
	BankLatestAcceptedTransactionRepository bankltstacptedtrxnRepo;
	
	@Autowired
	BankBarChartRepository bankbarchartRepo;
	
	@Override
	public BankMainDashboard bankDashBoard(String userId,String country,String productreq) {
		EntityManager entityManager1 = emFactory.createEntityManager();
		
		 BankMainDashboard mbd=new BankMainDashboard();
		 try
			{ 
		StoredProcedureQuery storedProcedureQuery = entityManager1
		.createStoredProcedureQuery("Latest_Accepted_Transaction_BANK", BankLatestAcceptedTransaction.class);
		storedProcedureQuery.registerStoredProcedureParameter("i_user_id", String.class, ParameterMode.IN);
		storedProcedureQuery.setParameter("i_user_id", userId);
		storedProcedureQuery.execute();
		List<BankLatestAcceptedTransaction> bnklstaccpttrxn = storedProcedureQuery.getResultList();
		mbd.setBanklatestaccepttrxn(bnklstaccpttrxn);
		
		
		StoredProcedureQuery storedProcedureQuery1 = entityManager1
	   .createStoredProcedureQuery("BANK_Dashboard_Count", BankDashBoardCount.class);
		
		storedProcedureQuery1.registerStoredProcedureParameter("i_user_id", String.class, ParameterMode.IN);
		storedProcedureQuery1.setParameter("i_user_id", userId);
		storedProcedureQuery1.execute();
		List<BankDashBoardCount> bankdashbrdcunt = storedProcedureQuery1.getResultList();
		mbd.setBankdashbrdcount(bankdashbrdcunt);
		
		
		StoredProcedureQuery storedProcedureQuery2 = entityManager1
		.createStoredProcedureQuery("Bank_Dashboard_BarChart", BankDashboardBarChart.class);
					
		storedProcedureQuery2.registerStoredProcedureParameter("i_user_id", String.class, ParameterMode.IN);
		storedProcedureQuery2.registerStoredProcedureParameter("in_country", String.class, ParameterMode.IN);
		storedProcedureQuery2.registerStoredProcedureParameter("in_product_req", String.class, ParameterMode.IN);
		storedProcedureQuery2.setParameter("i_user_id", userId);
		storedProcedureQuery2.setParameter("in_country", country);
		storedProcedureQuery2.setParameter("in_product_req", productreq);
		
		storedProcedureQuery2.execute();
		List<BankDashboardBarChart> bankbarchart = storedProcedureQuery2.getResultList();
		mbd.setBankBarChart(bankbarchart);
		
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
