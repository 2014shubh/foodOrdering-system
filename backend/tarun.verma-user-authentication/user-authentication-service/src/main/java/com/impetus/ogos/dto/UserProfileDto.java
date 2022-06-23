package com.impetus.ogos.dto;

import javax.validation.constraints.NotBlank;

public class UserProfileDto {

	private String email;
	
	private String firstname;
	
	private String lastname;
	
	private String mobile;
	


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

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}



	public UserProfileDto(String email, String firstname, String lastname, String mobile) {
		super();
		this.email = email;
		this.firstname = firstname;
		this.lastname = lastname;
		this.mobile = mobile;

	}

	public UserProfileDto() {
		super();
	}


	
	
	
}
