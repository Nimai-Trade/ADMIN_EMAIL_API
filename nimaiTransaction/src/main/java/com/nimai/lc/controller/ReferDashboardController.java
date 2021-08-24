package com.nimai.lc.controller;

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
import com.nimai.lc.bean.ReferDashboardProcedureBean;
import com.nimai.lc.entity.ReferMainDashboard;
import com.nimai.lc.payload.GenericResponse;
import com.nimai.lc.service.ReferDashboardServiceImpl;

@CrossOrigin(origins = "*")
@RestController
public class ReferDashboardController {
	private static final Logger logger = LoggerFactory.getLogger(NimaiTransactionApplication.class);

	
	@Autowired
   ReferDashboardServiceImpl referDsbrdservice;
	
	@CrossOrigin(origins = "*", allowedHeaders = "*")
	 @RequestMapping(value ="/referDashboard",produces = "application/json", method = RequestMethod.POST)
	  public ResponseEntity<Object> referdashboard(@RequestBody ReferDashboardProcedureBean referdsbrdbean) {
		logger.info("=========== Get Details For Referrer Dashboard ===========");
		GenericResponse response = new GenericResponse<>();
	  String userId=referdsbrdbean.getUserId();
	  String year=referdsbrdbean.getYear();
	
	  try {
		ReferMainDashboard outdata =referDsbrdservice.referDashBoard(userId,year);
	    response.setData(outdata);
	    response.setStatus("Success");
	    return new ResponseEntity<Object>(response, HttpStatus.OK); 

	 }
	  catch(Exception e) {
			response.setStatus("Failure");
			response.setErrCode("EXE000");
			return new ResponseEntity<Object>(response, HttpStatus.BAD_REQUEST);
		}
}


}
