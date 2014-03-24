package com.academy.core.query.result;

import java.util.List;

import com.academy.core.dto.ClassAttendedBean;
import com.google.common.collect.Lists;

public class GetClassesForDateResult implements QueryResult {

	private List<ClassAttendedBean> classesAttended= Lists.newArrayList();

	public GetClassesForDateResult(List<ClassAttendedBean> classesAttended) {
		this.classesAttended = classesAttended;
	}

	public List<ClassAttendedBean> getClassesAttended() {
		return classesAttended;
	}
	
}
