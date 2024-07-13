package com.esprit.jobfinder;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
@EnableCaching
public class JobFinderApplication {

	public static void main(String[] args) {
    SpringApplication.run(JobFinderApplication.class, args);
	}
}
