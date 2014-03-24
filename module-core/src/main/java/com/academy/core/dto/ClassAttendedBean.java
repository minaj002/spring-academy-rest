package com.academy.core.dto;

import java.util.Date;
import java.util.List;

public class ClassAttendedBean {

	private Date date;
	private List<MemberBean> members;
	 
	public Date getDate() {
		return date;
	}
	public List<MemberBean> getMembers() {
		return members;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public void setMembers(List<MemberBean> members) {
		this.members = members;
	}
	 
}
