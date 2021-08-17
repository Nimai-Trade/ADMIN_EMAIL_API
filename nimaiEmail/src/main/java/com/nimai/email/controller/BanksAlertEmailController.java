package com.nimai.email.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nimai.email.bean.AlertToBanksBean;
import com.nimai.email.bean.QuotationAlertRequest;
import com.nimai.email.service.BanksALertEmailService;
import com.nimai.email.service.UserService;

@RestController
public class BanksAlertEmailController {

	private static Logger logger = LoggerFactory.getLogger(BanksAlertEmailController.class);

	@Autowired
	BanksALertEmailService banksAlertService;

	/* sending the email after customer uploading lc to eligible banks */
	@CrossOrigin(origins = "*", allowedHeaders = "*")
	@RequestMapping("/sendTransactionStatusToBanks")
	public ResponseEntity<?> sendLcUploadStatusToBanks(@RequestBody AlertToBanksBean alertBanksBean) {
		logger.info(" ================ sendLcUploadStatusToBanks API Invoked ================:"
				+ alertBanksBean.toString());
		return banksAlertService.sendTransactionStatusToBanks(alertBanksBean);

	}

	/* sending the email after customer accept or reject the quotation to banks */
	@CrossOrigin(origins = "*", allowedHeaders = "*")
	@RequestMapping("/sendQuotationStatusToBanks")
	public ResponseEntity<?> sendQuotationStatusToBanks(@RequestBody QuotationAlertRequest qauotatioReq) {
		logger.info(
				" ================ sendQuotationStatusToBanks API Invoked ================:" + qauotatioReq.toString());
		return banksAlertService.sendQuotationStatusToBanks(qauotatioReq);

	}

}
