package com.nimai.lc.utility;

import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import com.nimai.lc.bean.EligibleEmailBeanResponse;
import com.nimai.lc.bean.EligibleEmailList;
import com.nimai.lc.entity.NimaiClient;

    @Component
    public class ModelMapperUtil extends ModelMapper{
        public ModelMapperUtil() {       
        this.getConfiguration().setFieldMatchingEnabled(true).setFieldAccessLevel(org.modelmapper.config.Configuration.AccessLevel.PRIVATE);
        }   
        
        public static List mapEntityToEntityResponse(NimaiClient customerEntity) {
    		EligibleEmailBeanResponse responseBean = new EligibleEmailBeanResponse();
    		EligibleEmailList emailList=new EligibleEmailList();
    		emailList.setEmailList(customerEntity.getEmailAddress());;
    	List emailList1=new ArrayList<>();
    	emailList1.add(emailList);
    	
	
    		return  emailList1;
    	}
        
    }