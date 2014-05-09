package com.academy.rest.function;

import static com.academy.utils.DateTimeUtils.dateToString;

import com.academy.core.dto.MemberBean;
import com.academy.rest.api.Member;
import com.google.common.base.Function;

public class MemberBeanToMemberFunction implements Function<MemberBean, Member> {

	@Override
	public Member apply(MemberBean bean) {
		Member member= new Member();
		member.setMemberId(bean.getId());
		member.setCity(bean.getCity());
		member.setFirstName(bean.getFirstName());
		member.setLastName(bean.getLastName());
		member.setStreet(bean.getStreet());
		member.setPhone(bean.getPhone());
		member.setEmail(bean.getEmail());
		member.setDateOfBirth(dateToString(bean.getDateOfBirth()));
		
		return member;
	}
}
