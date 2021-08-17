package com.nimai.email;


import java.util.concurrent.TimeUnit;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.http.CacheControl;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@SpringBootApplication
@EnableAutoConfiguration(exclude = HibernateJpaAutoConfiguration.class)
@EnableScheduling
public class NimaiEmailApplication implements WebMvcConfigurer{

	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {

	    // Register resource handler for images
	    registry.addResourceHandler("/images/**").addResourceLocations("/WEB-INF/images/")
	            .setCacheControl(CacheControl.maxAge(2, TimeUnit.HOURS).cachePublic());
	}
	
	public static void main(String[] args) {  
		SpringApplication.run(NimaiEmailApplication.class, args);
		System.out.println(" =========== NIMAI EMAIL SERVICE =========== ");
	}
}
