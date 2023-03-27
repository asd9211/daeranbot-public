package com.project.drbot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
@EnableCaching
public class DrbotApplication {

//	public static final String APPLICATION_LOCATIONS = "spring.config.location="
//			+ "classpath:application.yml,"
//			+ " /home/ec2-user/was/config/drbot/real-application.yml";


	public static void main(String[] args) {
		new SpringApplicationBuilder(DrbotApplication.class)
//				.properties(APPLICATION_LOCATIONS)
				.run(args);
	}

}
