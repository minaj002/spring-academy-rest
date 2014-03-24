package com.academy.core.command;

import com.academy.core.command.result.AddMemberResult;
import com.academy.core.dto.MemberBean;

public class AddMemberCommand implements Command<AddMemberResult> {
	
	private MemberBean member;

	private String userName;
	
	public AddMemberCommand(MemberBean member) {
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
