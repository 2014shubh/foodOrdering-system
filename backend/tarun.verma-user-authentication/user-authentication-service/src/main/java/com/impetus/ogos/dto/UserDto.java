package com.impetus.ogos.dto;

import java.util.Set;
import javax.validation.constraints.NotBlank;
import com.impetus.ogos.models.Role;
import com.impetus.ogos.models.User;

public class UserDto {
	
	private String userId;
	
	private String email;
	
	private String firstname;
	
	private String lastname;
	
	private String mobile;
	
	private Set<Role> roles;
	
	public UserDto(User user) {
		this.setUserId(user.getUserId());
		this.setEmail(user.getEmail());
		this.setFirstname(user.getFirstname());
		this.setLastname(user.getLastname());
		this.setMobile(user.getMobile());
		this.setRoles(user.getRoles());
		
	}

	public void setRoles(Set<Role> roles) {
		this.roles = roles;
	}


	public UserDto(String userId, @NotBlank String email, @NotBlank String firstname,
			@NotBlank String lastname, @NotBlank String mobile, Set<Role> roles) {
		super();
		this.userId = userId;
		this.email = email;
		this.firstname = firstname;
		this.lastname = lastname;
		this.mobile = mobile;
		this.roles = roles;
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

	public Set<Role> getRoles() {
		return roles;
	}

}
