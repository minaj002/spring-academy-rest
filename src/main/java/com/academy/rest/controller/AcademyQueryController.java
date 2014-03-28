package com.academy.rest.controller;

import java.util.Collection;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.academy.core.dto.MemberBean;
import com.academy.core.query.GetAcademyMembersQuery;
import com.academy.core.query.result.GetAcademyMembersResult;
import com.academy.core.query.service.QueryService;
import com.academy.rest.api.Member;
import com.google.common.base.Function;
import com.google.common.collect.Collections2;
import com.google.common.collect.Lists;

@Controller
@RequestMapping("/members")
public class AcademyQueryController {

	private static Logger LOG = LoggerFactory
			.getLogger(AcademyCommandController.class);
	
	private static Function<MemberBean, Member> MEMBER_BEAN_TO_MEMBER_FUNCTION = new MemberBeanToMemberJsonFunction();
	
	@Autowired
	QueryService queryService;

	@RequestMapping(method = RequestMethod.GET)
	@ResponseBody
	public Collection<Member> getAllOrders() {
		GetAcademyMembersQuery query = new GetAcademyMembersQuery();
		GetAcademyMembersResult members = queryService.execute(query);
		
		List<Member> membersJson =Lists.newArrayList(Collections2.transform(members.getMembers(), MEMBER_BEAN_TO_MEMBER_FUNCTION));
		
		return membersJson;
	}

	private static class MemberBeanToMemberJsonFunction implements Function<MemberBean, Member>{

		@Override
		public Member apply(MemberBean bean) {
			Member member= new Member();
			member.setId(bean.getId());
			member.setCity(bean.getCity());
			member.setFirstName(bean.getFirstName());
			member.setLastName(bean.getLastName());
			member.setStreet(bean.getStreet());
			member.setPhone(bean.getPhone());
			member.setEmail(bean.getEmail());
			return member;
		}
		
	}
	
}
