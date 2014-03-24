package com.academy.core.command.result;

public class AddAcademyResult implements CommandResult{
	
	private String id;

	public AddAcademyResult(String id) {
		this.id = id;
	}

	public String getId() {
		return id;
	}	
}
