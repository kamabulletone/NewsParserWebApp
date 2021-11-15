package com.webapp.newsparser;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class NewsparserApplication {

	private static final String URL_ECONOMY = "https://ria.ru/economy";
	private static final ObjectMapper objectMapper = new ObjectMapper();
	private static final int NEWS_PARSE_COUNT = 4;


	public static void main(String[] args) {
		SpringApplication.run(NewsparserApplication.class, args);
	}

}
