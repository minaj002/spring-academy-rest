package com.academy.rest.function;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.*;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormatter;
import org.springframework.format.datetime.joda.DateTimeFormatterFactory;

import com.academy.core.dto.MemberBean;
import com.academy.rest.api.Member;
import com.academy.rest.api.Payment;
import com.academy.rest.controller.command.PaymentsCommandController;
import com.google.common.base.Function;

public class MemberBeanToMemberFunction implements Function<MemberBean, Member> {

	@Override
	public Member apply(MemberBean bean) {
		Member member= new Member();
		member.setMemberId(bean.getId());
		member.setCity(bean.getCity());
		member.setFirstName(bean.getFirstName());
		member.setLastName(bean.getLastName());
		member.setStreet(bean.getStreet());
		member.setPhone(bean.getPhone());
		member.setEmail(bean.getEmail());
		
		DateTimeFormatterFactory formatterFactory = new DateTimeFormatterFactory();
		formatterFactory.setPattern("yyyy-MM-dd");
		DateTimeFormatter formatter = formatterFactory.createDateTimeFormatter();
		DateTime date = new DateTime(bean.getDateOfBirth());
		member.setDateOfBirth(date.toString(formatter));
		
		Payment payment = new Payment();
		payment.setMemberId(bean.getId());
		//member.add(linkTo(methodOn(PaymentsCommandController.class).addNewPayment(payment)).withSelfRel());
		
		return member;
	}
}
