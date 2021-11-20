package com.webapp.newsparser;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class NewsparserApplication {

	public static void main(String[] args) {
		SpringApplication.run(NewsparserApplication.class, args);
	}

}
