package com.academy.core.command.handler

import org.springframework.security.crypto.password.PasswordEncoder;

import com.academy.core.command.AddAcademyCommand;
import com.academy.core.command.result.AddAcademyResult
import com.academy.core.domain.Academy
import com.academy.core.domain.AcademyUser
import com.academy.core.domain.Address
import com.academy.core.domain.Member
import com.academy.core.query.GetAcademyMembersQuery
import com.academy.core.query.handler.GetAcademyMembersHandler;
import com.academy.core.query.result.GetAcademyMembersResult;
import com.academy.repository.AcademyRepository;
import com.academy.repository.AcademyUserRepository;
import com.academy.repository.MemberRepository;

import spock.lang.Specification

class AddAcademyHandlerSpecification extends Specification {

	def academyRepository = Mock(AcademyRepository)
	def academyUserRepository = Mock(AcademyUserRepository)
	def passwordEncoder = Mock(PasswordEncoder)

	
	

	def "Testing that AddAcademyHandler save new user in user repository"(){

		given: "Add academy handler"
			AddAcademyHandler handler = new AddAcademyHandler()
			handler.academyRepository=academyRepository
			handler.academyUserRepository=academyUserRepository
			handler.encoder=passwordEncoder
	
			AddAcademyCommand command = new AddAcademyCommand("academyName", "academy.email@mail.com","parole1")
			
			Academy academyOut = new Academy()
			
			academyOut.email="academy.email@mail.com"
			academyOut.name="academyName"
			
			academyOut.id="1232154"

		when: "Calling handlers execute method"

			AddAcademyResult result = handler.execute(command);

		then: "return result with new academy Id and created new academy record and new academy user"

			handler.academyRepository.save(_)>>academyOut
			passwordEncoder.encode(_)>>"encodesPass"
			
			result.id == "1232154"
			
			1*academyUserRepository.save(_)
	}
}
