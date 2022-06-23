package com.impetus.ogos.payload.responses;

import java.util.List;

public class CustomerJwtResponse {

	private String token;
	private String type = "Bearer";
	private String userId;
	private String firstname;
	private String email;
	private List<String> roles;
	private int cid;

	public String getToken() {
		return token;
	}

	public String getFirstname() {
		return firstname;
	}

	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public List<String> getRoles() {
		return roles;
	}

	public void setRoles(List<String> roles) {
		this.roles = roles;
	}

	public int getCid() {
		return cid;
	}

	public void setCid(int cid) {
		this.cid = cid;
	}

	public CustomerJwtResponse(String token, String userId, String email, List<String> roles,
			int cid, String firstname) {
		super();
		this.token = token;
		this.userId = userId;
		this.email = email;
		this.roles = roles;
		this.cid = cid;
		this.firstname=firstname;
}
}
