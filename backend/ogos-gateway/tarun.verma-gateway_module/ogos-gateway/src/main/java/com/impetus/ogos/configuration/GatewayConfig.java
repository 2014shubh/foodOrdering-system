package com.impetus.ogos.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import com.impetus.ogos.filter.JwtAuthenticationFilter;

@Configuration
public class GatewayConfig {

	@Autowired
	private JwtAuthenticationFilter filter;

	@Bean
	public RouteLocator routes(RouteLocatorBuilder builder) {
		return builder.routes()
				.route("user-authentication-service", r -> r.path("/auth/**").filters(f -> f.filter(filter)).uri("lb://user-authentication-service"))
				.route("product-service", r -> r.path("/product/**").filters(f -> f.filter(filter)).uri("lb://product-service"))
				.route("product-service", r -> r.path("/category/**").filters(f -> f.filter(filter)).uri("lb://product-service"))
				.route("inventory-service", r -> r.path("/inventory/**").filters(f -> f.filter(filter)).uri("lb://inventory-service"))
				.route("order-service", r -> r.path("/order/**").filters(f -> f.filter(filter)).uri("lb://order-service"))
				.route("cart-service", r -> r.path("/cart/**").filters(f -> f.filter(filter)).uri("lb://cart-service")).build();
	}

}