package com.academy.core.command.handler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.academy.core.command.AddMemberCommand;
import com.academy.core.command.response.AddMemberResult;
import com.academy.core.domain.Address;
import com.academy.core.domain.Member;
import com.academy.repository.MemberRepository;
import com.google.common.base.Function;

@Component
public class AddMemberHandler implements CommandHandler<AddMemberCommand, AddMemberResult> {

	@Autowired
	MemberRepository memberRepository;
	
	private static final Function<AddMemberCommand, Member> ADD_MEMBER_COMMAND_TO_MEMBER = new AddMemberCommandToMemberFunction();
	
	@Override
	public AddMemberResult execute(AddMemberCommand command) {

		Member member = ADD_MEMBER_COMMAND_TO_MEMBER.apply(command);
		memberRepository.save(member);
		
		return new AddMemberResult(member.getId());
	}

	
	static class AddMemberCommandToMemberFunction implements Function<AddMemberCommand, Member>{

		@Override
		public Member apply(AddMemberCommand command) {
			Member member = new Member();
			member.setFirstName(command.getFirstName());
			member.setLastName(command.getLastName());
			member.setDateOfBirth(command.getDateOfBirth());
			Address address= new Address();
			address.setStreet(command.getStreet());
			address.setCity(command.getCity());
			member.setAddress(address);
			return member;
		}
		
	}
	
}
