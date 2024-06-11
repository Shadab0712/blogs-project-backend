package com.blogsproject.dto;

import java.io.Serializable;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

public class UserDTO implements Serializable {

	private static final long serialVersionUID = -1533265591425335767L;

	private Long id;

	@NotEmpty
	@Size(min = 4, message = "Name must be above 4 characters !!")
	private String name;

	@Email(message = "Email address must be valid !!")
	private String username;

	@NotEmpty
	@Size(min = 4, max = 9, message = "Password must be min 3 and max 8 characters !!")
	private String password;

	private Long mobileNumber;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getMobileNumber() {
		return mobileNumber;
	}

	public void setMobileNumber(Long mobileNumber) {
		this.mobileNumber = mobileNumber;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getUsername() {
		return username;
	}
 
	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
}
