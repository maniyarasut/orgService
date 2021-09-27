package com.org.service.dto;

import javax.validation.constraints.NotBlank;

public class LoginDto {
	@NotBlank(message = "Email is mandatory")
	private String email;

	@NotBlank(message = "Password is mandatory")
	private String password;

	public String getEmail() {
		return this.email;
	}
	
	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
}
