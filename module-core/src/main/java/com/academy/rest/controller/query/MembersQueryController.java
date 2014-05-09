package com.academy.rest.controller.query;

import java.util.Collection;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.academy.core.dto.MemberBean;
import com.academy.core.query.GetAcademyMembersQuery;
import com.academy.core.query.result.GetAcademyMembersResult;
import com.academy.core.query.service.QueryService;
import com.academy.rest.api.Member;
import com.academy.rest.function.MemberBeanToMemberFunction;
import com.google.common.base.Function;
import com.google.common.collect.Collections2;
import com.google.common.collect.Lists;

@Controller
@RequestMapping("/members")
public class MembersQueryController {

	private static Logger LOG = LoggerFactory
			.getLogger(MembersQueryController.class);
	
	private static Function<MemberBean, Member> MEMBER_BEAN_TO_MEMBER_FUNCTION = new MemberBeanToMemberFunction();
	
	@Autowired
	QueryService queryService;

	@RequestMapping(method = RequestMethod.GET)
	@ResponseBody
	public Collection<Member> getAllMembers() {
		
		String name = getUserName();

		LOG.debug("Getting members for ", name);
		GetAcademyMembersQuery query = new GetAcademyMembersQuery(name);
		GetAcademyMembersResult members = queryService.execute(query);
		
		List<Member> membersJson =Lists.newArrayList(Collections2.transform(members.getMembers(), MEMBER_BEAN_TO_MEMBER_FUNCTION));
		
		return membersJson;
	}

	private String getUserName() {
		return SecurityContextHolder.getContext().getAuthentication().getName();
	}
	
}
