package com.ecommerce.main;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
public class UserServiceDomainApplication {

	public static void main(String[] args) {
		SpringApplication.run(UserServiceDomainApplication.class, args);
	}

	@Bean 
	public RestTemplate rs() {
		
		return new RestTemplate();
	}


}
