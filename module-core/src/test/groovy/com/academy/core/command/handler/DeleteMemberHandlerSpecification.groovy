package com.academy.core.command.handler

import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;

import com.academy.config.CoreConfig;
import com.academy.core.command.AddClassCommand;
import com.academy.core.command.DeleteMemberCommand
import com.academy.core.command.result.AddClassResult
import com.academy.core.domain.Academy
import com.academy.core.domain.AcademyUser;
import com.academy.core.domain.ClassAttended
import com.academy.core.domain.Member
import com.academy.core.dto.MemberBean
import com.academy.repository.AcademyUserRepository;
import com.academy.repository.ClassAttendedRepository;
import com.academy.repository.MemberRepository

import spock.lang.Specification

class DeleteMemberHandlerSpecification extends Specification {


    MemberRepository memberRepository = Mock()

    DeleteMemberHandler handler
    DeleteMemberCommand command

    def setup(){
	handler = new DeleteMemberHandler()

	List members =[]

	MemberBean member
	member=new MemberBean()
	member.setFirstName("firstName")
	member.setLastName("lastName")
	member.setId("id")

	command = new DeleteMemberCommand(member)

	handler.memberRepository=memberRepository
    }


    def "Testing that DeleteMemberHandler delete Member"(){

	when: "Calling DeleteMemberHander execute method"

		handler.execute(command)

	then: "MemberRepository delete method should be executed"

    		1*memberRepository.delete(_ as Member)
	
    }
}
