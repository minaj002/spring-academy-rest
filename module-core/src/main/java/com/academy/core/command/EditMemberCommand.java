package com.academy.core.command;

import com.academy.core.command.result.EditMemberResult;
import com.academy.core.dto.MemberBean;

public class EditMemberCommand implements Command<EditMemberResult> {
	
	private MemberBean member;

	private String userName;
	
	public EditMemberCommand(MemberBean member) {
		this.member=member;}

	public MemberBean getMember() {
		return member;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

}
