package com.academy.rest.function;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

import org.joda.time.DateTime;

import com.academy.core.dto.AcademyBean;
import com.academy.rest.api.Academy;
import com.academy.rest.api.ClassAttended;
import com.academy.rest.api.Member;
import com.academy.rest.controller.command.ClassesCommandController;
import com.academy.rest.controller.command.MembersCommandController;
import com.academy.rest.controller.query.ClassesQueryController;
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
		academy.add(linkTo(methodOn(ClassesQueryController.class).getAllClasses(DateTime.now().toString("yyyy-MM-dd"))).withSelfRel());
		ClassAttended classAttended= new ClassAttended();
		academy.add(linkTo(methodOn(ClassesCommandController.class).addClass(classAttended)).withSelfRel());
		
		
		return academy;
	}

}
