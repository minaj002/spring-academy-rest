package com.academy.core.query.handler;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.academy.core.domain.Member;
import com.academy.core.dto.MemberBean;
import com.academy.core.query.GetAcademyMembersQuery;
import com.academy.core.query.result.GetAcademyMembersResult;
import com.academy.repository.MemberRepository;
import com.google.common.base.Function;
import com.google.common.collect.Collections2;
import com.google.common.collect.Lists;

@Component
public class GetAcademyMembersHandler implements
		QueryHandler<GetAcademyMembersQuery, GetAcademyMembersResult> {

	@Autowired
	MemberRepository memberRepository;
	private Function<Member, MemberBean> MEMBER_TO_MEMBER_BEAN_FUNCTION= new MemberToMemberBeanFunction();

	
	
	@Override
	public GetAcademyMembersResult execute(GetAcademyMembersQuery query) {

		List<MemberBean> members =Lists.newArrayList(Collections2.transform(memberRepository.findAll(), MEMBER_TO_MEMBER_BEAN_FUNCTION));
		GetAcademyMembersResult result = new GetAcademyMembersResult();
		result.setMembers(members);
		return result;

	}
	
	static class MemberToMemberBeanFunction implements Function<Member,MemberBean>{

		@Override
		public MemberBean apply(Member from) {
			MemberBean bean = new MemberBean();
			bean.setFirstName(from.getFirstName());
			bean.setLastName(from.getLastName());
			bean.setDateOfBirth(from.getDateOfBirth());
			bean.setId(from.getId());
			bean.setPhone(from.getPhone());
			bean.setStreet(from.getAddress().getStreet());
			bean.setCity(from.getAddress().getCity());
			return bean;
		}
		
	}
	
}
