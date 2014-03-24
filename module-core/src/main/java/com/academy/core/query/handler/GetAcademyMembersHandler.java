package com.academy.core.query.handler;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.academy.core.domain.AcademyUser;
import com.academy.core.domain.Member;
import com.academy.core.dto.MemberBean;
import com.academy.core.function.MemberToMemberBeanFunction;
import com.academy.core.query.GetAcademyMembersQuery;
import com.academy.core.query.result.GetAcademyMembersResult;
import com.academy.repository.AcademyUserRepository;
import com.academy.repository.MemberRepository;
import com.google.common.base.Function;
import com.google.common.collect.Collections2;
import com.google.common.collect.Lists;

@Component
public class GetAcademyMembersHandler implements
		QueryHandler<GetAcademyMembersQuery, GetAcademyMembersResult> {

	@Autowired
	MemberRepository memberRepository;
	
	@Autowired 
	AcademyUserRepository academyUserRepository;
	
	private Function<Member, MemberBean> MEMBER_TO_MEMBER_BEAN_FUNCTION= new MemberToMemberBeanFunction();

	
	
	@Override
	public GetAcademyMembersResult execute(GetAcademyMembersQuery query) {
		
		
		AcademyUser user = academyUserRepository.findByName(query.getUser());

		List<Member> members = memberRepository.findByAcademyName(user.getAcademy().getName());
		
		List<MemberBean> memberBeans =Lists.newArrayList(Collections2.transform(members, MEMBER_TO_MEMBER_BEAN_FUNCTION));
		GetAcademyMembersResult result = new GetAcademyMembersResult();
		result.setMembers(memberBeans);
		return result;

	}

}
