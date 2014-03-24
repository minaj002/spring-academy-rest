package com.academy.core.query.result;

import java.util.List;

import com.academy.core.dto.MemberBean;
import com.google.common.collect.Lists;

public class GetAcademyMembersResult implements QueryResult{

	private List<MemberBean> members= Lists.newArrayList();

	public List<MemberBean> getMembers() {
		return members;
	}

	public void addMember(MemberBean member) {
		this.members.add(member);
	}

	public void setMembers(List<MemberBean> members) {
		this.members = members;
	}
	
}
