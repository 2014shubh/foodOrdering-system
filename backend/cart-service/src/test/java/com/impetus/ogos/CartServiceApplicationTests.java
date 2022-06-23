package com.impetus.ogos;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@SpringBootTest
class CartServiceApplicationTests {

	
	@Bean
	public RestTemplate restTemplate() {
	    return new RestTemplate();
	}
}
