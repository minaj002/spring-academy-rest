package com.academy.core.command.result;

public class DeleteMemberResult implements CommandResult {

	private String memberId;

	public DeleteMemberResult(String memberId) {
		this.memberId = memberId;
	}

	public String getMemberId() {
		return memberId;
	}
	
}
