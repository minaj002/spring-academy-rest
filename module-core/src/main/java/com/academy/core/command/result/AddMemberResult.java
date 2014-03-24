package com.academy.core.command.result;

public class AddMemberResult implements CommandResult {

	private String memberId;

	public AddMemberResult(String memberId) {
		this.memberId = memberId;
	}

	public String getMemberId() {
		return memberId;
	}
	
}
