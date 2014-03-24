package com.academy.core.query.handler

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.context.ContextConfiguration;

import com.academy.config.SecurityConfig;
import com.academy.core.domain.Academy
import com.academy.core.domain.AcademyUser
import com.academy.core.domain.Address
import com.academy.core.domain.Member
import com.academy.core.query.GetAcademyMembersQuery
import com.academy.core.query.handler.GetAcademyMembersHandler;
import com.academy.core.query.result.GetAcademyMembersResult;
import com.academy.repository.AcademyUserRepository;
import com.academy.repository.MemberRepository;

import spock.lang.Specification

@ContextConfiguration(classes = [SecurityConfig])
class GetAcademyMembersSpecification extends Specification {

	def memberRepository = Mock(MemberRepository)
	def academyUserRepository = Mock(AcademyUserRepository)
	
	def "Testing that GetAcademyMembersHandler returns result with all members"(){

		given: "List of Members in database"
		
			List mockedList=[]
			Member member
			Address address
			
			(0L..7L).each{ item ->
				member = new Member()
				member.setFirstName("firtsName$item")
				member.setLastName("lastName$item")
				address = new Address()
				address.setStreet("street$item")
				member.setAddress(address)
				mockedList.add(member)
				member=null
				address=null
			}
			
			assert mockedList.size()==8
		
			memberRepository.findByAcademyName("owner") >> mockedList
			
			AcademyUser user= new AcademyUser()
			Academy academy = new Academy()
			academy.setName("owner")
			user.setAcademy(academy);
			
			academyUserRepository.findByName("owner")>>user
		
		when: "Calling handlers execute method"
		
			GetAcademyMembersHandler handler = new GetAcademyMembersHandler()
			handler.memberRepository=memberRepository
			handler.academyUserRepository=academyUserRepository
			GetAcademyMembersQuery query = new GetAcademyMembersQuery("owner");
			GetAcademyMembersResult result = handler.execute(query);
		
		then: "return Result with List af all members"
			
			result.getMembers().size()==8
			result.getMembers().get(0).getFirstName()=="firtsName0"
			result.getMembers()[2].getFirstName()=="firtsName2"
			result.getMembers()[5].getFirstName()=="firtsName5"
			result.getMembers()[5].getLastName()=="lastName5"
			result.getMembers()[5].getStreet()=="street5"
	}
}
