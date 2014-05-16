package com.academy.core.query.handler

import org.springframework.beans.factory.annotation.Autowired;

import com.academy.core.domain.Academy
import com.academy.core.domain.AcademyUser
import com.academy.core.domain.Address
import com.academy.core.domain.ClassAttended
import com.academy.core.domain.Member
import com.academy.core.query.GetClassesForDateQuery;
import com.academy.core.query.result.GetClassesForDateResult;
import com.academy.repository.AcademyUserRepository;
import com.academy.repository.ClassAttendedRepository;

import org.joda.time.DateTime;

import spock.lang.Specification

class GetClassesAttendedForDateHandlerClassification extends Specification {

	
	AcademyUserRepository academyUserRepository = Mock();
	
	ClassAttendedRepository classAttendedRepository = Mock();
	
	def "Test that GetClassesAttendedForDateHandler return Result with ClassesAttendedBean list"(){
		
		
		given: "List of ClassAttended in database"
		
			List mockedList=[]
			ClassAttended classAttended; 
			
			(0L..7L).each{ item ->
				classAttended = new ClassAttended();
				
				Academy academy = new Academy()
				academy.setName("academy")
				classAttended.academy = academy
				classAttended.date = DateTime.parse("2012-01-01").toDate()
				
				List mockedMemberList=[]
				Member member
				Address address
				
				(0L..4L).each{ memberitem ->
					member = new Member()
					member.setFirstName("firtsName$item")
					member.setLastName("lastName$item")
					address = new Address()
					address.setStreet("street$item")
					member.setAddress(address)
					mockedMemberList.add(member)
					member=null
					address=null
				}
				
				assert mockedMemberList.size()==5
				
				classAttended.members = mockedMemberList 
				
				mockedList << classAttended
			}
			
			assert mockedList.size()==8
		
			
			AcademyUser user= new AcademyUser()
			Academy academy = new Academy()
			academy.setName("academy")
			user.setAcademy(academy);
			classAttendedRepository.findByAcademyAndDateIsBetween(academy, DateTime.parse("2012-01-01").toDate(),DateTime.parse("2012-02-01").toDate())>> mockedList
			
			academyUserRepository.findByName("owner")>>user
		
		when: "Calling handlers execute method"
		
			GetClassesAttendedForDateHandler handler = new GetClassesAttendedForDateHandler()
			handler.classAttendedRepository=classAttendedRepository
			handler.academyUserRepository=academyUserRepository
			GetClassesForDateQuery query = new GetClassesForDateQuery("owner",DateTime.parse("2012-01-01").toDate());
			GetClassesForDateResult result = handler.execute(query);
		
		then: "return Result with List af all members"
			
			result.classesAttended.size()==8
			result.classesAttended[0].date==DateTime.parse("2012-01-01").toDate()
			result.classesAttended[0].members.size()==5
		
	}
	
	
}
