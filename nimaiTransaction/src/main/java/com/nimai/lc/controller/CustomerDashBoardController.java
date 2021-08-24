package com.nimai.lc.controller;


import java.util.HashMap;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.nimai.lc.NimaiTransactionApplication;
import com.nimai.lc.bean.CustomerDashboardProcedureBean;
import com.nimai.lc.entity.MainDashboard;
import com.nimai.lc.payload.GenericResponse;
import com.nimai.lc.service.CustomerDashBoardServiceImpl;
import com.nimai.lc.utility.ErrorDescription;


@CrossOrigin(origins = "*")
@RestController
public class CustomerDashBoardController {
	private static final Logger logger = LoggerFactory.getLogger(NimaiTransactionApplication.class);

	@Autowired
	CustomerDashBoardServiceImpl customerDsbrdservice;
	
	 @CrossOrigin(value = "*", allowedHeaders = "*")
	 @RequestMapping(value ="/customerDashboard",produces = "application/json", method = RequestMethod.POST)
	  public ResponseEntity<Object> customerdashboard(@RequestBody CustomerDashboardProcedureBean cstmrdsbrdbean) {
      logger.info("=========== Get Details For Customer Dashboard ===========");
      GenericResponse response = new GenericResponse<>();
	  String userId=cstmrdsbrdbean.getUserId();
	  String emailId=cstmrdsbrdbean.getEmailId();
	  String startDate=cstmrdsbrdbean.getStartDate();
	  String endDate=cstmrdsbrdbean.getEndDate();
	  String currency=cstmrdsbrdbean.getCurrency();
	  String year=cstmrdsbrdbean.getYear();
	  
	  try
	  {
		  MainDashboard outdata =customerDsbrdservice.customerdashboard(year,userId,emailId,startDate,endDate);
		  response.setData(outdata);
		  return new ResponseEntity<Object>(response, HttpStatus.OK); 
	  }
	  catch(Exception e)
	  {
		  response.setStatus("Failure");
		  response.setErrMessage("Something Went Wrong. "+e);
		  return new ResponseEntity<Object>(response, HttpStatus.OK);
	  }
	
	 }
	 
	
}
