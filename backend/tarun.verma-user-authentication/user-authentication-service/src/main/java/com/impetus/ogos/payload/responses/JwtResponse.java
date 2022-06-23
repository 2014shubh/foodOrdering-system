package com.impetus.ogos.payload.responses;

import java.util.List;

public class JwtResponse {
	private String token;
	private String type = "Bearer";
	private String userId;
	private String firstname;
	private String email;
	private List<String> roles;

	public JwtResponse(String token, String userId, String firstname, String email, List<String> roles) {
		this.token = token;
		this.userId = userId;
		this.firstname = firstname;
		this.email = email;
		this.roles = roles;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getTokenType() {
		return type;
	}

	public void setTokenType(String tokenType) {
		this.type = tokenType;
	}

	public String getUserId() {
		return userId;
	}

	public void setId(String userId) {
		this.userId = userId;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getFirstname() {
		return firstname;
	}

	public void setUsername(String firstname) {
		this.firstname = firstname;
	}

	public List<String> getRoles() {
		return roles;
	}
}
