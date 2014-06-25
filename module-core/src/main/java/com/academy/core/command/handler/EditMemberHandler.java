package com.academy.core.command.handler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.academy.core.command.EditMemberCommand;
import com.academy.core.command.result.EditMemberResult;
import com.academy.core.domain.Member;
import com.academy.core.dto.MemberBean;
import com.academy.core.function.MemberBeanToMemberFunction;
import com.academy.repository.MemberRepository;
import com.google.common.base.Function;

@Component
public class EditMemberHandler implements CommandHandler<EditMemberCommand, EditMemberResult> {

    @Autowired
    MemberRepository memberRepository;

    private static final Function<MemberBean, Member> MEMBER_BEAN_TO_MEMBER = new MemberBeanToMemberFunction();

    @Override
    public EditMemberResult execute(EditMemberCommand command) {

	Member member = MEMBER_BEAN_TO_MEMBER.apply(command.getMember());
	Member existingMember = memberRepository.findOne(member.getId());
	updateMemberInfo(existingMember, member);
	memberRepository.save(existingMember);

	return new EditMemberResult(member.getId());
    }

    private void updateMemberInfo(Member existingMember, Member member) {

	existingMember.setFirstName(member.getFirstName());
	existingMember.setLastName(member.getLastName());
	existingMember.setDateOfBirth(member.getDateOfBirth());
	existingMember.setEmail(member.getEmail());
	existingMember.setPhone(member.getPhone());
	existingMember.setAddress(member.getAddress());
    }

}
