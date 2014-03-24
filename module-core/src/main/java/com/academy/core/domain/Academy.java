package com.academy.core.domain;

import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import com.academy.core.annotation.Email;

@Document
public class Academy {
	
	@Indexed
	private String id;
	
	@Indexed
	private String name;
	
	@Email
	private String email;
	
	public String getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public String getEmail() {
		return email;
	}

	public void setId(String id) {
		this.id = id;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	
}
