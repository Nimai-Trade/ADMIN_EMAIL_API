package com.nimai.email.utility;

import java.util.ArrayList;

import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.nimai.email.bean.EligibleEmailBeanResponse;
import com.nimai.email.bean.EligibleEmailList;
import com.nimai.email.entity.NimaiClient;


public class ModelMapper {
private static Logger logger = LoggerFactory.getLogger(ModelMapper.class);
	
	public static List mapEntityToEntityResponse(NimaiClient customerEntity) {
		logger.info("============SubscriptionPlanResponse mapper method===========");
		EligibleEmailBeanResponse responseBean = new EligibleEmailBeanResponse();
		EligibleEmailList emailList=new EligibleEmailList();
		emailList.setEmailList(customerEntity.getEmailAddress());;
	List emailList1=new ArrayList<>();
	emailList1.add(emailList);
	
	
//	List<emailList> deptList = customerEntity.getn.stream().map(request -> {
//		DepartmentList departmentList = new DepartmentList();
//		departmentList.setDeptId(request.getDeptId().getDeptId() + "");
//		departmentList.setDeptName(request.getDeptId().getDeptName());
//
//		return departmentList;
//	}).collect(Collectors.toList());
	
	
	
		return  emailList1;
	}

}
