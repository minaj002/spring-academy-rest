package com.academy.core.command.result;

public class AddClassResult implements CommandResult {

	private String id;

	public AddClassResult(String id) {
		this.id = id;
	}

	public String getId() {
		return id;
	}
	
}
