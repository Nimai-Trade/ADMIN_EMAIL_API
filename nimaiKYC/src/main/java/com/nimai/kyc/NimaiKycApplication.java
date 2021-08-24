package com.nimai.kyc;

import java.util.TimeZone;

import javax.annotation.PostConstruct;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.convert.threeten.Jsr310JpaConverters;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@ComponentScan(value = "com.nimai.kyc.*")
@EntityScan(basePackageClasses = { NimaiKycApplication.class, Jsr310JpaConverters.class })
@SpringBootApplication
@EnableJpaAuditing
public class NimaiKycApplication {

	public static void main(String[] args) {
		SpringApplication.run(NimaiKycApplication.class, args);
		System.out.println(" =========== NIMAI KYC  =========== ");
	}
	
	@PostConstruct
	void init() {
		TimeZone.setDefault(TimeZone.getTimeZone("IST"));
	}


}
