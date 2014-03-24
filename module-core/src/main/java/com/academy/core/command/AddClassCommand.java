package com.academy.core.command;

import java.util.Date;
import java.util.List;

import com.academy.core.command.result.AddClassResult;
import com.academy.core.dto.MemberBean;

public class AddClassCommand implements Command<AddClassResult>{

	private String userName;
	
	private Date date;
	
	private  List<MemberBean> members;

	public AddClassCommand(Date date, List<MemberBean> members) {
		this.date = date;
		this.members = members;
	}

	public Date getDate() {
		return date;
	}

	public List<MemberBean> getMembers() {
		return members;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}
	
}
