package com.academy.rest.controller;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.academy.core.command.AddMemberCommand;
import com.academy.core.command.response.AddMemberResult;
import com.academy.core.command.service.CommandService;
import com.academy.rest.api.Member;
import com.google.common.base.Function;

@Controller
@RequestMapping("/members/new")
public class AcademyCommandController {

	private static Logger LOG = LoggerFactory
			.getLogger(AcademyCommandController.class);

	@Autowired
	CommandService commandService;

	private static Function<Member, AddMemberCommand> MEMBER_TO_ADD_MEMBER_COMMAND_FUNCTION = new MemberToAddMemberFunction();
	
	@RequestMapping(method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<Member> addNewMember(@RequestBody Member member) {

		AddMemberResult result = commandService.execute(MEMBER_TO_ADD_MEMBER_COMMAND_FUNCTION.apply(member));

		if (!StringUtils.isEmpty(result.getMemberId())) {
			return new ResponseEntity<>(HttpStatus.CREATED);
		} else {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

	private static class MemberToAddMemberFunction implements
			Function<Member, AddMemberCommand> {

		@Override
		public AddMemberCommand apply(Member member) {
			AddMemberCommand command = new AddMemberCommand(
					member.getFirstName(), member.getLastName(),
					member.getDateOfBirth(), member.getStreet(),
					member.getCity(), member.getEmail(), member.getPhone());
			return command;
		}

	}

}
