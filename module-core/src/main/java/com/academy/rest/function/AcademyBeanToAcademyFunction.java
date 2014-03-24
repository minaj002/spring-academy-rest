package com.academy.rest.function;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

import com.academy.core.dto.AcademyBean;
import com.academy.rest.api.Academy;
import com.academy.rest.api.Member;
import com.academy.rest.controller.command.MembersCommandController;
import com.academy.rest.controller.query.MembersQueryController;
import com.google.common.base.Function;

public class AcademyBeanToAcademyFunction implements Function<AcademyBean, Academy> {

	@Override
	public Academy apply(AcademyBean from) {

		Academy academy = new Academy();
		academy.setEmail(from.getEmail());
		academy.setName(from.getName());
		
		Member member = new Member();
		academy.add(linkTo(methodOn(MembersCommandController.class).addNewMember(member)).withSelfRel());
		academy.add(linkTo(methodOn(MembersQueryController.class).getAllMembers()).withSelfRel());
		
		return academy;
	}

}
