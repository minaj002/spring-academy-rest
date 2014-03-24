package com.academy.core.function;

import com.academy.core.domain.Address;
import com.academy.core.domain.Member;
import com.academy.core.dto.MemberBean;
import com.google.common.base.Function;

public class MemberBeanToMemberFunction implements Function<MemberBean, Member> {

	@Override
	public Member apply(MemberBean bean) {
			Member member = new Member();
			member.setFirstName(bean.getFirstName());
			member.setLastName(bean.getLastName());
			member.setDateOfBirth(bean.getDateOfBirth());
			member.setEmail(bean.getEmail());
			member.setPhone(bean.getPhone());
			member.setId(bean.getId());
			
			Address address= new Address();
			address.setStreet(bean.getStreet());
			address.setCity(bean.getCity());
			member.setAddress(address);
			return member;
	}

}
