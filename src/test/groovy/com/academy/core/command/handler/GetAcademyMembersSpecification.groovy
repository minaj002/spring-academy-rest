package com.academy.core.command.handler

import com.academy.core.domain.Address
import com.academy.core.domain.Member
import com.academy.core.query.GetAcademyMembersQuery
import com.academy.core.query.handler.GetAcademyMembersHandler;
import com.academy.core.query.result.GetAcademyMembersResult;
import com.academy.repository.MemberRepository;

import spock.lang.Specification

class GetAcademyMembersSpecification extends Specification {

	def memberRepository = Mock(MemberRepository)
	
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
		
			memberRepository.findAll() >> mockedList
		
		when: "Calling handlers execute method"
		
			GetAcademyMembersHandler handler = new GetAcademyMembersHandler()
			handler.memberRepository=memberRepository
		
			GetAcademyMembersResult result = handler.execute(new GetAcademyMembersQuery());
		
		then: "return Result with List af all members"
			
			result.getMembers().size()==8
			result.getMembers().get(0).getFirstName()=="firtsName0"
			result.getMembers()[2].getFirstName()=="firtsName2"
			result.getMembers()[5].getFirstName()=="firtsName5"
			result.getMembers()[5].getLastName()=="lastName5"
			result.getMembers()[5].getStreet()=="street5"
	}
}
