package com.nimai.splan.utility;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.nimai.splan.model.NimaiEmailScheduler;
import com.nimai.splan.model.NimaiMSubscription;
import com.nimai.splan.model.NimaiSubscriptionDetails;
import com.nimai.splan.payload.SPlanResponseBean;
import com.nimai.splan.payload.SubscriptionBean;
import com.nimai.splan.payload.SubscriptionPlanResponse;
import com.nimai.splan.payload.banksSplansReponse;
import com.nimai.splan.payload.customerSPlansResponse;



@Component
public class ModelMapper {

	private static Logger logger = LoggerFactory.getLogger(ModelMapper.class);
	
	public static SubscriptionPlanResponse mapEntityToEntityResponse(NimaiSubscriptionDetails subscriptionEntity) {
		logger.info("============SubscriptionPlanResponse mapper method===========");
		SubscriptionPlanResponse responseBean = new SubscriptionPlanResponse();
		responseBean.setSubscriptionAmount(subscriptionEntity.getSubscriptionAmount());
		responseBean.setSubscriptionName(subscriptionEntity.getSubscriptionName());
		responseBean.setSubscriptionId(subscriptionEntity.getSubscriptionId());
		responseBean.setSubscriptionValidity(subscriptionEntity.getSubscriptionValidity());
		responseBean.setLcCount(subscriptionEntity.getlCount());
		responseBean.setRemark(subscriptionEntity.getRemark());
		responseBean.setUserId(subscriptionEntity.getUserid().getUserid());
		responseBean.setStatus(subscriptionEntity.getStatus());
		responseBean.setSubsidiaries(subscriptionEntity.getSubsidiaries());
		responseBean.setRelationshipManager(subscriptionEntity.getRelationshipManager());
		responseBean.setCustomerSupport(subscriptionEntity.getCustomerSupport());
		return responseBean;
	}

	public static List<customerSPlansResponse> mapCustSplanListToSBeanRsponse(List<NimaiMSubscription> custSPlanList) {
		List<customerSPlansResponse> custSubscriptionBean=new ArrayList<customerSPlansResponse>();
		logger.info("============mapCustSplanListToSBeanRsponse mapper method===========");
		SPlanResponseBean sPlanBean = new SPlanResponseBean();
		for (NimaiMSubscription cuSPlan : custSPlanList) {

			customerSPlansResponse custResponseBean = new customerSPlansResponse();
			custResponseBean.setSubscriptionAmount(cuSPlan.getSubscriptionAmount());
			custResponseBean.setSubscriptionName(cuSPlan.getSubscriptionName());
			custResponseBean.setSubscriptionId(cuSPlan.getSubscriptionId());
			custResponseBean.setSubscriptionValidity(cuSPlan.getSubscriptionValidity());
			custResponseBean.setCustomerSupport(cuSPlan.getCustomerSupport());
			custResponseBean.setRelationshipManager(cuSPlan.getRelationshipManager());
			custResponseBean.setLcCount(cuSPlan.getlCount());
			custResponseBean.setSubsidiaries(cuSPlan.getSubsidiaries());
			custResponseBean.setCustomerType(cuSPlan.getCustomerType());
			custResponseBean.setsPLanCountry(cuSPlan.getsPLanCountry());
			custResponseBean.setSubscriptionCurrency(cuSPlan.getCurrency());
			custSubscriptionBean.add(custResponseBean);
			
			
		}
		return custSubscriptionBean;
	}

	public static List<banksSplansReponse> mapBankSplanListToSBeanRsponse(List<NimaiMSubscription> banksSPlanList) {
		List<banksSplansReponse> banksubscriptionBean=new ArrayList<banksSplansReponse>();
		logger.info("============mapBankSplanListToSBeanRsponse mapper method===========");
		for (NimaiMSubscription cuSPlan : banksSPlanList) {

			banksSplansReponse bankResponseBean = new banksSplansReponse();
			bankResponseBean.setSubscriptionAmount(cuSPlan.getSubscriptionAmount());
			bankResponseBean.setSubscriptionName(cuSPlan.getSubscriptionName());
			bankResponseBean.setSubscriptionId(cuSPlan.getSubscriptionId());
			bankResponseBean.setSubscriptionValidity(cuSPlan.getSubscriptionValidity());
			bankResponseBean.setCustomerSupport(cuSPlan.getCustomerSupport());
			bankResponseBean.setRelationshipManager(cuSPlan.getRelationshipManager());
			bankResponseBean.setLcCount(cuSPlan.getlCount());
			bankResponseBean.setSubsidiaries(cuSPlan.getSubsidiaries());
			bankResponseBean.setCustomerType(cuSPlan.getCustomerType());
			bankResponseBean.setsPLanCountry(cuSPlan.getsPLanCountry());
			bankResponseBean.setSubscriptionCurrency(cuSPlan.getCurrency());
			banksubscriptionBean.add(bankResponseBean);
			
			

		}
		return banksubscriptionBean;
	}

	public static SubscriptionBean mapCustSplanListToSBeanRsponse(NimaiEmailScheduler schedularData) {
		// TODO Auto-generated method stub
		return null;
	}

	
	
}
