package com.org.service.dto;

import javax.validation.constraints.NotBlank;

public class AuthenticationRequest {

	@NotBlank
	private String userName;

	@NotBlank
	private String password;

	public AuthenticationRequest(String userName, String password) {
		super();
		this.userName = userName;
		this.password = password;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

}