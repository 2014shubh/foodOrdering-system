package com.impetus.ogos.dto;

import java.util.Set;

public class UserCreateDto {
	
	private String email;
	 
	private String firstname;
	
	private String lastname;
	
	private String password;
	
	private String mobile;
	
	private Set<String> role;
	
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getFirstname() {
		return firstname;
	}

	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	public String getLastname() {
		return lastname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public Set<String> getRole() {
		return role;
	}

	public void setRole(Set<String> role) {
		this.role = role;
	}

	public UserCreateDto(String email, String firstname, String lastname, String password, String mobile,
			Set<String> role) {
		super();
		this.email = email;
		this.firstname = firstname;
		this.lastname = lastname;
		this.password = password;
		this.mobile = mobile;
		this.role = role;
	}

	public UserCreateDto() {
		super();
	}
	
	
	
}
