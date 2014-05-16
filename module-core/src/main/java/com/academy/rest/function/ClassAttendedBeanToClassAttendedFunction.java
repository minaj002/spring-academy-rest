package com.academy.rest.function;

import java.util.List;

import com.academy.core.dto.ClassAttendedBean;
import com.academy.core.dto.MemberBean;
import com.academy.rest.api.ClassAttended;
import com.academy.rest.api.Member;
import com.academy.utils.DateTimeUtils;
import com.google.common.base.Function;
import com.google.common.collect.Collections2;
import com.google.common.collect.Lists;

public class ClassAttendedBeanToClassAttendedFunction implements
		Function<ClassAttendedBean, ClassAttended> {

	private static Function<MemberBean, Member> MEMBER_BEAN_TO_MEMBER_FUNCTION = new MemberBeanToMemberFunction();
	
	@Override
	public ClassAttended apply(ClassAttendedBean from) {
	
		ClassAttended classAttended = new ClassAttended();
		List<Member> members = Lists.newArrayList(Collections2.transform(from.getMembers(), MEMBER_BEAN_TO_MEMBER_FUNCTION));
		classAttended.setMembers(members);
		classAttended.setDate(DateTimeUtils.dateToString(from.getDate()));
		
		return classAttended;
	}

}
