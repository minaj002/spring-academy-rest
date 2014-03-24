package com.academy.core.query;

import java.util.Date;

import com.academy.core.query.result.GetClassesForDateResult;

public class GetClassesForDateQuery implements Query<GetClassesForDateResult> {

	private String user;
	private Date date;

	public GetClassesForDateQuery(String user, Date date) {

		this.user= user;
		this.date = date;
	}

	public String getUser() {
		return user;
	}

	public Date getDate() {
		return date;
	}

}
