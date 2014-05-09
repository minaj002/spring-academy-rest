package com.academy.core.command.handler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.academy.core.command.AddMemberCommand;
import com.academy.core.command.result.AddMemberResult;
import com.academy.core.domain.AcademyUser;
import com.academy.core.domain.Member;
import com.academy.core.dto.MemberBean;
import com.academy.core.function.MemberBeanToMemberFunction;
import com.academy.repository.AcademyUserRepository;
import com.academy.repository.MemberRepository;
import com.google.common.base.Function;

@Component
public class AddMemberHandler implements CommandHandler<AddMemberCommand, AddMemberResult> {

    @Autowired
    MemberRepository memberRepository;

    @Autowired
    AcademyUserRepository academyUserRepository;

    private static final Function<MemberBean, Member> MEMBER_BEAN_TO_MEMBER = new MemberBeanToMemberFunction();

    @Override
    public AddMemberResult execute(AddMemberCommand command) {

	AcademyUser user = academyUserRepository.findByName(command.getUserName());
	Member member = MEMBER_BEAN_TO_MEMBER.apply(command.getMember());
	member.setAcademyName(user.getAcademy().getName());
	memberRepository.save(member);

	return new AddMemberResult(member.getId());
    }

}
