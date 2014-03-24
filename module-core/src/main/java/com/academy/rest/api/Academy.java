package com.academy.rest.api;

import org.springframework.hateoas.ResourceSupport;

public class Academy extends ResourceSupport {
	
	private String name;

	private String email;
	
	private String password;

	public String getName() {
		return name;
	}

	public String getEmail() {
		return email;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
	
}
