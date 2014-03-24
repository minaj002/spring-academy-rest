package com.academy.rest.controller.query

import com.academy.config.CoreConfig;
import com.academy.config.MVCConfig;
import com.academy.config.SecurityConfig;
import com.academy.core.dto.MemberBean;
import com.academy.core.query.GetAcademyMembersQuery

import org.springframework.security.authentication.TestingAuthenticationProvider;
import org.springframework.security.authentication.TestingAuthenticationToken;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.core.userdetails.User
import org.springframework.test.context.ContextConfiguration;

import spock.lang.Specification

import com.academy.rest.controller.query.MembersQueryController;
import com.academy.core.query.result.GetAcademyMembersResult;
import com.academy.core.query.service.QueryService;


@ContextConfiguration(classes = [SecurityConfig])
class MembersQueryControllerSpecification extends Specification {

	def queryService = Mock(QueryService)
	SecurityContextHolder holder
	
	def setup(){
	
		holder = new SecurityContextHolder()
		Authentication auth = new UsernamePasswordAuthenticationToken("user","pass",null);
		holder.getContext().setAuthentication(auth);
		
	}
	
	def "Testing that controller returns correct number of members" (){
		
		given: "List of Members"
		List mockedList=[]
		
					MemberBean member
		
					def log=0;
					(0L..9L).each { item ->
								member=new MemberBean()
								member.setFirstName("firtsName$item")
								member.setLastName("lastName$item")
								mockedList.add(member)
								member=null;
					}
		
					assert mockedList.size()==10
					
					GetAcademyMembersResult members = new GetAcademyMembersResult();
					members.setMembers(mockedList);
					
		queryService.execute(_)>>members
		
		
		
		when: "Calling controllers getAllMembers() method"
			MembersQueryController controller = new MembersQueryController();
			controller.getUserName()>>"name"
			controller.queryService=queryService
			List result = controller.getAllMembers();
			
		then: "return list with all members"
				result.size()==10
				result.get(0).getFirstName()=="firtsName0"
				result.get(2).getFirstName()=="firtsName2"
				result.get(5).getFirstName()=="firtsName5"
				result.get(5).getLastName()=="lastName5"
				
		
		
	}
	
}
