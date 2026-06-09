package com.gmsmartplanner;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class GmsmartplannerApplication {

	public static void main(String[] args) {
		SpringApplication.run(GmsmartplannerApplication.class, args);
	}

}
