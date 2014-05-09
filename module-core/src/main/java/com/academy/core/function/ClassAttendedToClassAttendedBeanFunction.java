package com.academy.core.function;

import java.util.List;

import com.academy.core.domain.ClassAttended;
import com.academy.core.domain.Member;
import com.academy.core.dto.ClassAttendedBean;
import com.academy.core.dto.MemberBean;
import com.google.common.base.Function;
import com.google.common.collect.Collections2;
import com.google.common.collect.Lists;

public class ClassAttendedToClassAttendedBeanFunction implements Function<ClassAttended, ClassAttendedBean> {

    private Function<Member, MemberBean> MEMBER_TO_MEMBER_BEAN_FUNCTION = new MemberToMemberBeanFunction();

    @Override
    public ClassAttendedBean apply(ClassAttended from) {

	ClassAttendedBean bean = new ClassAttendedBean();
	bean.setDate(from.getDate());
	List<MemberBean> membersAttended = Lists.newArrayList(Collections2.transform(from.getMembers(), MEMBER_TO_MEMBER_BEAN_FUNCTION));
	bean.setMembers(membersAttended);

	return bean;
    }

}
