package com.academy.core.command.result;

public class DeleteClassResult implements CommandResult {

	private String classId;

	public DeleteClassResult(String classId) {
		this.classId = classId;
	}

	public String getClassId() {
		return classId;
	}
	
}
