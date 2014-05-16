package com.academy.rest.controller.fixture;

import java.math.BigDecimal;
import java.util.List;

import com.academy.rest.api.Academy;
import com.academy.rest.api.ClassAttended;
import com.academy.rest.api.Member;
import com.academy.rest.api.Payment;
import com.google.common.collect.Lists;
import com.google.gson.Gson;

public class RestEventFixsture {

    public static String newMemberJSON() {

	Member member = new Member();
	member.setFirstName("firstName");
	member.setLastName("lastName");
	member.setEmail("email@mail.com");
	member.setCity("Riga");
	member.setDateOfBirth("1985-12-31");
	member.setStreet("Mana Gatve");
	Gson gson = new Gson();

	return gson.toJson(member);
    }

    public static String newMemberJSON(String firstName, String lastName, String email) {

	Member member = new Member();
	member.setFirstName(firstName);
	member.setLastName(lastName);
	member.setEmail(email);
	member.setCity("Riga");
	member.setDateOfBirth("1985-12-12");
	member.setStreet("Mana Gatve");
	Gson gson = new Gson();

	return gson.toJson(member);
    }

    public static String newAcademyJSON() {

	Academy academy = new Academy();
	academy.setName("BjjAcademy");
	academy.setEmail("admin@bjj.com");
	academy.setPassword("password");
	Gson gson = new Gson();

	return gson.toJson(academy);

    }

    public static String newAcademyJSON(String name, String email) {

	Academy academy = new Academy();
	academy.setName(name);
	academy.setEmail(email);
	academy.setPassword("password");
	Gson gson = new Gson();

	return gson.toJson(academy);

    }

    public static String newPayment(String memberId, String date, String dueDate, BigDecimal amount) {

	Payment payment = new Payment();
	payment.setMemberId(memberId);
	payment.setPaymentDate(date);
	payment.setPaidUntill(dueDate);
	payment.setAmount(amount);
	Gson gson = new Gson();

	return gson.toJson(payment);
    }

    public static String newClass(List<Member> members) {

	ClassAttended classAttened = new ClassAttended();
	classAttened.setDate("2014-02-02");
	classAttened.setMembers(members);
	Gson gson = new Gson();
	return gson.toJson(classAttened);
    }

}
