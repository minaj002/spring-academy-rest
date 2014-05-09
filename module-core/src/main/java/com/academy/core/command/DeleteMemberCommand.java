package com.academy.core.command;

import com.academy.core.command.result.DeleteMemberResult;
import com.academy.core.dto.MemberBean;

public class DeleteMemberCommand implements Command<DeleteMemberResult>{

	private MemberBean member;

	public DeleteMemberCommand(MemberBean member) {
		this.member = member;
	}

	public MemberBean getMember() {
		return member;
	}
	
}
