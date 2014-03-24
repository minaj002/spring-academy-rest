package com.academy.core.command.handler

import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;

import com.academy.config.CoreConfig;
import com.academy.core.command.AddClassCommand;
import com.academy.core.command.result.AddClassResult
import com.academy.core.domain.Academy
import com.academy.core.domain.AcademyUser;
import com.academy.core.domain.ClassAttended
import com.academy.core.dto.MemberBean
import com.academy.repository.AcademyUserRepository;
import com.academy.repository.ClassAttendedRepository;

import spock.lang.Specification

class AddClassHandlerSpecification extends Specification {

	
	ClassAttendedRepository classAttendedRepository = Mock() 
	AcademyUserRepository academyUserRepository = Mock();
	
	AddClassHandler handler
	AddClassCommand command
	ClassAttended classAttended
	
	def setup(){
		handler = new AddClassHandler()
		
		List members =[]
			
		MemberBean member
			
						(0L..9L).each { item ->
									member=new MemberBean()
									member.setFirstName("firstName$item")
									member.setLastName("lastName$item")
									members.add(member)
									member=null;
						}
			
						assert members.size()==10
		
		command = new AddClassCommand(DateTime.parse("2012-12-12").toDate(), members)
		command.setUserName("userName")
		
		Academy academy = new Academy()
		academy.setName("academy")
		
		AcademyUser user = new AcademyUser()
		user.setAcademy(academy)
		
		academyUserRepository.findByName("userName") >> user;
		
		handler.academyUserRepository = academyUserRepository		
				}
	
	
	def "Testing that AddClassHandler correctly transforms AddClassCommand to ClassAttended"(){
		
		when: "Calling AddClassHander toClassAttended method"
		
			ClassAttended classAttended = handler.toClassAttended(command);
			List transformedMembers = classAttended.getMembers()
			
			
			
			
		
		then: "AddClassCommand must be transform in to classAttended"
			
			classAttended.date==DateTime.parse("2012-12-12").toDate()
			
			transformedMembers[0].getFirstName()=="firstName0"
			transformedMembers[0].getLastName()=="lastName0"
			transformedMembers[7].getFirstName()=="firstName7"
			transformedMembers[7].getLastName()=="lastName7"
		
	}
	
}
