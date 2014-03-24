package com.academy.core.command;

import com.academy.core.command.result.AddAcademyResult;

public class AddAcademyCommand implements Command<AddAcademyResult>{
	private String name;
	private String email;
	private String password;
	
	public AddAcademyCommand(String name, String email, String password) {
		this.name = name;
		this.email = email;
		this.password=password;
	}
	public String getName() {
		return name;
	}
	public String getEmail() {
		return email;
	}
	public String getPassword() {
		return password;
	}
	
}
