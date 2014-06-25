package com.academy.core.command.result;

public class EditMemberResult implements CommandResult {

	private String memberId;

	public EditMemberResult(String memberId) {
		this.memberId = memberId;
	}

	public String getMemberId() {
		return memberId;
	}
	
}
