package com.impetus.ogos.filter;

import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import com.impetus.ogos.exception.JwtTokenMalformedException;
import com.impetus.ogos.exception.JwtTokenMissingException;
import com.impetus.ogos.security.jwt.JwtUtils;
import io.jsonwebtoken.Claims;
import reactor.core.publisher.Mono;

@Component
public class JwtAuthenticationFilter implements GatewayFilter {

	@Autowired
	JwtUtils jwtUtils;
	public static final Logger logger = LoggerFactory.getLogger(JwtAuthenticationFilter.class);

	@Override
	public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
		logger.debug("inside filter");
		ServerHttpRequest request = exchange.getRequest();
		final List<String> apiEndpoints = Arrays.asList("/auth/signin", "auth/signup","/product/all","auth/pass/forgot-password"
				,"/auth/pass/reset-password","/auth/user/verifyEmail","/category/all");
		Predicate<ServerHttpRequest> isApiSecured = r -> apiEndpoints.stream()
				.noneMatch(uri -> r.getURI().getPath().contains(uri));

		if (isApiSecured.test(request)) {
			if (!request.getHeaders().containsKey("Authorization")) {
				ServerHttpResponse response = exchange.getResponse();
				response.setStatusCode(HttpStatus.UNAUTHORIZED);

				return response.setComplete();
			}

			final String authHeader = request.getHeaders().getOrEmpty("Authorization").get(0);
			String[] parts = authHeader.split(" ");

			if (parts.length != 2 || !"Bearer".equals(parts[0])) {
				throw new RuntimeException("Incorrect authorization structure");
			}

			final String token = parts[1];

			try {
				logger.info(token);
				jwtUtils.validateToken(token);
			} catch (JwtTokenMalformedException | JwtTokenMissingException e) {
				// e.printStackTrace();

				ServerHttpResponse response = exchange.getResponse();
				response.setStatusCode(HttpStatus.BAD_REQUEST);

				return response.setComplete();
			}

			Claims claims = jwtUtils.getClaims(token);
			exchange.getRequest().mutate().header("userId", String.valueOf(claims.get("userId"))).build();
		}

		return chain.filter(exchange);
	}

}
