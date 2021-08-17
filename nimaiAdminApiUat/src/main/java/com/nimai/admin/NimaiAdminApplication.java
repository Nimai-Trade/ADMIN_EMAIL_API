package com.nimai.admin;

import java.util.TimeZone;

import javax.annotation.PostConstruct;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.convert.threeten.Jsr310JpaConverters;


import com.nimai.admin.model.Tokens;

/**
 * Application Nimai Admin
 * 
 * @author sahadeo.naik
 *
 */
@SpringBootApplication
@EntityScan(basePackageClasses = { NimaiAdminApplication.class, Jsr310JpaConverters.class })
//@EnableConfigurationProperties({
//	FileStorageProperties.class
//})
public class NimaiAdminApplication extends SpringBootServletInitializer {

//	private static HikariPoolMXBean poolProxy;
//	private static final Logger log = LoggerFactory.getLogger(NimaiAdminApplication.class);

	@PostConstruct
	void init() { 
		TimeZone.setDefault(TimeZone.getTimeZone("GMT"));
	}

	public static void main(String[] args) {
		SpringApplication.run(NimaiAdminApplication.class, args);
	}

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
		return builder.sources(NimaiAdminApplication.class);
	}

	/* Redis Connection establishment */ // modified by bashir//
//	@Bean
//	RedisConnectionFactory redisConnectionFactory() {
//		return new JedisConnectionFactory();
//	}

	/* Redis Template configuration */ // modified by bashir//
//	@Bean
//	RedisTemplate<String, Tokens> redisTemplate() {
//		RedisTemplate<String, Tokens> redisTemplate = new RedisTemplate<String, Tokens>();
//		redisTemplate.setConnectionFactory(redisConnectionFactory());
//		return redisTemplate;
//	}

}