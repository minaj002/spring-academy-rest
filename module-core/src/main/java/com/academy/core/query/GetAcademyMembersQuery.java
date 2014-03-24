package com.academy.core.query;

import com.academy.core.query.result.GetAcademyMembersResult;

public class GetAcademyMembersQuery implements Query<GetAcademyMembersResult> {
	
	private String user;

	public GetAcademyMembersQuery(String user) {
		super();
		this.user = user;
	}

	public String getUser() {
		return user;
	}

}
