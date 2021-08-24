package com.nimai.lc.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.nimai.lc.bean.BankDashboardProcedureBean;
import com.nimai.lc.bean.CustomerDashboardProcedureBean;

import com.nimai.lc.entity.BankMainDashboard;
import com.nimai.lc.entity.MainDashboard;
import com.nimai.lc.payload.GenericResponse;
import com.nimai.lc.service.BankDashBoardServiceImpl;
import com.nimai.lc.service.CustomerDashBoardServiceImpl;
import com.nimai.lc.service.LCService;

@CrossOrigin(origins = "*")
@RestController
public class BankDashBoardController {

	@Autowired
	LCService lcservice;

	@Autowired
	BankDashBoardServiceImpl bankDsbrdservice;
	
	@CrossOrigin(origins = "*", allowedHeaders = "*")
	 @RequestMapping(value ="/bankDashboard",produces = "application/json", method = RequestMethod.POST)
	  public ResponseEntity<Object> bankdashboard(@RequestBody BankDashboardProcedureBean bankdsbrdbean) {
		GenericResponse response = new GenericResponse<>();
	  String userId=bankdsbrdbean.getUserId();
	  String country=bankdsbrdbean.getCountry();
	  String productreq=bankdsbrdbean.getProductRequirement();
	  //String obtainUserId=lcservice.checkMasterForSubsidiary(userId);
	  try {
		BankMainDashboard outdata =bankDsbrdservice.bankDashBoard(userId,country,productreq);
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