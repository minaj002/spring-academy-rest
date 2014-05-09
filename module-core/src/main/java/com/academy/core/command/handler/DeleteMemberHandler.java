package com.academy.core.command.handler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.academy.core.command.DeleteMemberCommand;
import com.academy.core.command.result.DeleteMemberResult;
import com.academy.core.domain.Member;
import com.academy.core.dto.MemberBean;
import com.academy.core.function.MemberBeanToMemberFunction;
import com.academy.repository.AcademyUserRepository;
import com.academy.repository.MemberRepository;
import com.google.common.base.Function;

@Component
public class DeleteMemberHandler implements CommandHandler<DeleteMemberCommand, DeleteMemberResult> {

    @Autowired
    MemberRepository memberRepository;

    @Autowired
    AcademyUserRepository academyUserRepository;

    private static final Function<MemberBean, Member> MEMBER_BEAN_TO_MEMBER = new MemberBeanToMemberFunction();

    @Override
    public DeleteMemberResult execute(DeleteMemberCommand command) {

	Member member = MEMBER_BEAN_TO_MEMBER.apply(command.getMember());
	memberRepository.delete(member);

	return new DeleteMemberResult(member.getId());
    }

}
