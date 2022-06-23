package com.impetus.ogos;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
public class UserAuthenticationServiceApplication {
	
	 @Bean
	    public RestTemplate getRestTemplate()
	    {
	        return new RestTemplate();
	    }

	public static void main(String[] args) {
		SpringApplication.run(UserAuthenticationServiceApplication.class, args);
	}

}
