package com.nimai.lc;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class NimaiTransactionApplication {

	private static final Logger logger = LoggerFactory.getLogger(NimaiTransactionApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(NimaiTransactionApplication.class, args);
		logger.info("=========== NIMAI TRANSACTION ===========");
		System.out.println(" =========== NIMAI TRANSACTION =========== ");
	}

}
	