package com.academy.core.command.handler;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.academy.core.command.AddClassCommand;
import com.academy.core.command.result.AddClassResult;
import com.academy.core.domain.AcademyUser;
import com.academy.core.domain.ClassAttended;
import com.academy.core.domain.Member;
import com.academy.core.dto.MemberBean;
import com.academy.core.function.MemberBeanToMemberFunction;
import com.academy.repository.AcademyUserRepository;
import com.academy.repository.ClassAttendedRepository;
import com.google.common.base.Function;
import com.google.common.collect.Collections2;
import com.google.common.collect.Lists;

@Component
public class AddClassHandler implements CommandHandler<AddClassCommand, AddClassResult> {

	@Autowired
	ClassAttendedRepository classAttendedRepository;
	
	@Autowired
	AcademyUserRepository academyUserRepository;
	
	private static Function<MemberBean, Member> MEMBER_BEAN_TO_MEMBER_FUNCTION = new MemberBeanToMemberFunction();
	
	@Override
	public AddClassResult execute(AddClassCommand command) {
		
		
		ClassAttended classAttended = toClassAttended(command);
		
		classAttended=classAttendedRepository.save(classAttended);
		
		return new AddClassResult(classAttended.getId());
	}

	private ClassAttended toClassAttended(AddClassCommand command) {
		ClassAttended classAttended= new ClassAttended();
		classAttended.setDate(command.getDate());
		
		AcademyUser user = academyUserRepository.findByName(command.getUserName());
		classAttended.setAcademy(user.getAcademy());
		
		List<Member> members =Lists.newArrayList(Collections2.transform(command.getMembers(), MEMBER_BEAN_TO_MEMBER_FUNCTION));
		classAttended.setMembers(members);
		return classAttended;
	}

}
