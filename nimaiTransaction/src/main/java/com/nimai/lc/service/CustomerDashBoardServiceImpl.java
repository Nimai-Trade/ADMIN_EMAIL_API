package com.nimai.lc.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.ParameterMode;
import javax.persistence.StoredProcedureQuery;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import com.nimai.lc.entity.CumulativeTransactionAmount;
import com.nimai.lc.entity.CustomerDashBoardCount;

import com.nimai.lc.entity.LatestAcceptedTransaction;
import com.nimai.lc.entity.LifeTimeSaving;
import com.nimai.lc.entity.MainDashboard;
import com.nimai.lc.entity.PieChartCountry;
import com.nimai.lc.entity.PieChartGoods;
import com.nimai.lc.entity.TransactionBifurcation;
import com.nimai.lc.repository.CumulativeTransactionAmountRepository;
import com.nimai.lc.repository.CustomerDashBoardCountRepository;
import com.nimai.lc.repository.LatestAcceptedTransactionRepository;
import com.nimai.lc.repository.PieChartCountryRepo;
import com.nimai.lc.repository.PieChartGoodsRepo;
import com.nimai.lc.repository.TransactionBurificationRepo;
import com.nimai.lc.utility.ModelMapperUtil;
@Service
public class CustomerDashBoardServiceImpl  implements CustomerDashboardService{
	
	
	@Autowired
	EntityManagerFactory emFactory;
	
	@Autowired 
	PieChartCountryRepo countryRepo;
	
	@Autowired 
	PieChartGoodsRepo goodsRepo;
	
	@Autowired 
	CumulativeTransactionAmountRepository cumulativeRepo;
	
	@Autowired
	TransactionBurificationRepo transactionburiRepo;
	
	@Autowired
	LatestAcceptedTransactionRepository latestaccetractionRepo;
	
	@Autowired
	CustomerDashBoardCountRepository customerDashboard;
	
	

	@Override
	public MainDashboard customerdashboard(String year,String userId,String emailId,String startDate,String endDate) {
		EntityManager entityManager1 = emFactory.createEntityManager();
		
		try
		{
			StoredProcedureQuery storedProcedureQuery = entityManager1
			.createStoredProcedureQuery("CUSTOMER_DASHBOARD_GOODS_PIE_CHART", PieChartGoods.class);
			storedProcedureQuery.registerStoredProcedureParameter("i_user_id", String.class, ParameterMode.IN);
			storedProcedureQuery.setParameter("i_user_id", userId);
			MainDashboard md=new MainDashboard();
			storedProcedureQuery.execute();
			List<PieChartGoods> pcg = storedProcedureQuery.getResultList();
			md.setPiechartgoods(pcg);
			
			
			StoredProcedureQuery storedProcedureQuery1 = entityManager1
		   .createStoredProcedureQuery("CUSTOMER_DASHBOARD_COUNTRY_PIE_CHART", PieChartCountry.class);
			
			storedProcedureQuery1.registerStoredProcedureParameter("i_user_id", String.class, ParameterMode.IN);
			storedProcedureQuery1.setParameter("i_user_id", userId);
			storedProcedureQuery1.execute();
			List<PieChartCountry> pcc = storedProcedureQuery1.getResultList();
			md.setPiechartcountry(pcc);
		/*************************************************************************/
			
			StoredProcedureQuery storedProcedureQuery2 = entityManager1
			.createStoredProcedureQuery("Cumulative_Trxn_Amount_Vs_Trxn_Count", CumulativeTransactionAmount.class);
			
			storedProcedureQuery2.registerStoredProcedureParameter("inp_year", String.class, ParameterMode.IN);
			storedProcedureQuery2.registerStoredProcedureParameter("i_user_id", String.class, ParameterMode.IN);
			storedProcedureQuery2.setParameter("inp_year", year);
			storedProcedureQuery2.setParameter("i_user_id", userId);
			storedProcedureQuery2.execute();
			List<CumulativeTransactionAmount> cumulativetrxn = storedProcedureQuery2.getResultList();
			md.setCumulativetrxnAmnt(cumulativetrxn);
			
			
			/************************************************************************/
			
			StoredProcedureQuery storedProcedureQuery3 = entityManager1
					.createStoredProcedureQuery("Transaction_Bifurcation",TransactionBifurcation.class);
					
					storedProcedureQuery3.registerStoredProcedureParameter("i_user_id", String.class, ParameterMode.IN);
					storedProcedureQuery3.registerStoredProcedureParameter("in_from_date", String.class, ParameterMode.IN);
					storedProcedureQuery3.registerStoredProcedureParameter("in_to_date", String.class, ParameterMode.IN);
					storedProcedureQuery3.setParameter("i_user_id", userId);
					storedProcedureQuery3.setParameter("in_from_date", startDate);
					storedProcedureQuery3.setParameter("in_to_date", endDate);
					storedProcedureQuery3.execute();
					List<TransactionBifurcation> transactionBifurcation = storedProcedureQuery3.getResultList();
					md.setTransactionbifurcation(transactionBifurcation);
			
			
			/************************************************************************************/
			StoredProcedureQuery storedProcedureQuery4 = entityManager1
					.createStoredProcedureQuery("Latest_Accepted_Transaction", LatestAcceptedTransaction.class);
					
					storedProcedureQuery4.registerStoredProcedureParameter("i_user_id", String.class, ParameterMode.IN);
					storedProcedureQuery4.registerStoredProcedureParameter("i_email_id", String.class, ParameterMode.IN);
					storedProcedureQuery4.setParameter("i_user_id", userId);
					storedProcedureQuery4.setParameter("i_email_id", emailId);
					storedProcedureQuery4.execute();
					List<LatestAcceptedTransaction> latestaccetrxn = storedProcedureQuery4.getResultList();
					md.setLatestacceptedtrxn(latestaccetrxn);
					
			/*******************************************************************************/
			
					StoredProcedureQuery storedProcedureQuery5 = entityManager1
							.createStoredProcedureQuery("Customer_Dashboard_Count", CustomerDashBoardCount.class);
							
							storedProcedureQuery5.registerStoredProcedureParameter("i_user_id", String.class, ParameterMode.IN);
							storedProcedureQuery5.setParameter("i_user_id", userId);
							storedProcedureQuery5.execute();
							List<CustomerDashBoardCount> cstmrDsbrdCount = storedProcedureQuery5.getResultList();
							md.setCustmrdasbrdcount(cstmrDsbrdCount);
					
					

					StoredProcedureQuery storedProcedureQuery6 = entityManager1
							.createStoredProcedureQuery("Life_Time_Savings", LifeTimeSaving.class);
											
							storedProcedureQuery6.registerStoredProcedureParameter("inp_userid", String.class, ParameterMode.IN);
							storedProcedureQuery6.setParameter("inp_userid", userId);
							storedProcedureQuery6.execute();
							List<LifeTimeSaving> lifesavings = storedProcedureQuery6.getResultList();
							if(lifesavings.size()==0)
							{
								LifeTimeSaving ls=new LifeTimeSaving();
								ls.setSavings("0");
								lifesavings.add(ls);
								md.setLifetimesaving(lifesavings);
							}
							else
							{
								md.setLifetimesaving(lifesavings);
							}
			
		  return md;
		
	
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