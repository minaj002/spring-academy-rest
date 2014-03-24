package com.academy.functional;

import static org.junit.Assert.*;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;

import org.junit.Test;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.codec.Base64;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import com.academy.rest.api.Academy;
import com.academy.rest.api.ClassAttended;
import com.academy.rest.api.Member;
import com.academy.rest.api.Payment;
import com.academy.rest.controller.fixture.RestEventFixsture;
import com.google.gson.Gson;

public class MembersTest {

	RestTemplate template = new RestTemplate();
	HttpEntity<String> requestEntity;

	@Test
	public void thatAcademyCanBeAddedAndMembersCanBeAddedToNewAcademy() {

		createNewAcademy("BjjAcademy", "admin@bjj.com");

		ResponseEntity<List> members = getMembersForOwner("admin@bjj.com",
				"password", 0);

		ResponseEntity<Member> memberEntity = addNewMemberToAcademy(
				"admin@bjj.com", "password");

		assertEquals(HttpStatus.CREATED, memberEntity.getStatusCode());

		members = getMembersForOwner("admin@bjj.com", "password", 1);

		memberEntity = addNewMember("admin@bjj.com", "password", "first2",
				"last2", "last2@email.com");

		assertEquals(HttpStatus.CREATED, memberEntity.getStatusCode());

		members = getMembersForOwner("admin@bjj.com", "password", 2);

		createNewAcademy("EEacademy", "admin@ee.com");

		members = getMembersForOwner("admin@ee.com", "password", 0);
		memberEntity = addNewMember("admin@ee.com", "password", "first3",
				"last3", "last3@email.com");
		members = getMembersForOwner("admin@ee.com", "password", 1);
		memberEntity = addNewMember("admin@ee.com", "password", "first4",
				"last4", "last4@email.com");
		members = getMembersForOwner("admin@ee.com", "password", 2);

		ClassAttended classAttended = new ClassAttended();
		classAttended.setMembers(members.getBody());
		classAttended.setDate("2014-12-12");

		addClass("admin@ee.com", "password", classAttended);

		@SuppressWarnings("unchecked")
		LinkedHashMap<String, String> addedMember = (LinkedHashMap<String, String>) members
				.getBody().get(0);

		addPayment("admin@ee.com", "password", addedMember.get("memberId"),
				"2012-01-08", "2012-02-08", BigDecimal.valueOf(43));

		getAcademies(2);
	}

	
	
	
	@Test
	public void AcademyCountTest() {
		
		getAcademies(3);
		
	}
	
	
	@Test(expected = HttpClientErrorException.class)
	public void failCreatingNewAcademyWithWrongCredentialsTest() {

		createNewAcademy("LTacademy", "admin@lt.com");

		createNewAcademyBad("NAmeBAdAcad", "namebad@emial.com", "admin@lt.com");

	}

	@Test
	public void addPayment() {

		ResponseEntity<List> members = getMembersForOwner("admin@ee.com",
				"password", 2);

		LinkedHashMap<String, String> addedMember = (LinkedHashMap<String, String>) members
				.getBody().get(0);

		String id = (String) addedMember.get("memberId");
		addPayment("admin@ee.com", "password", id, "2012-01-08", "2012-02-08",
				BigDecimal.valueOf(43));
	}

	@Test
	public void addClass() {
		ResponseEntity<List> members = getMembersForOwner("admin@ee.com",
				"password", 2);
		ClassAttended classAttended = new ClassAttended();
		classAttended.setMembers(members.getBody());
		classAttended.setDate("2014-12-12");

		addClass("admin@ee.com", "password", classAttended);
	}

	private void addPayment(String owner, String pass, String memberId,
			String date, String dueDate, BigDecimal amount) {
		ResponseEntity<Payment> paymentEntity;

		requestEntity = new HttpEntity<String>(RestEventFixsture.newPayment(
				memberId, date, dueDate, amount),
				getHeaders(owner + ":" + pass));

		paymentEntity = template.postForEntity(
				"http://localhost:8080/payments/new", requestEntity,
				Payment.class);

		assertEquals(HttpStatus.CREATED, paymentEntity.getStatusCode());
	}

	private ResponseEntity<Member> addNewMember(String owner, String pass,
			String firstName, String lastName, String email) {
		ResponseEntity<Member> memberEntity;
		requestEntity = new HttpEntity<String>(RestEventFixsture.newMemberJSON(
				firstName, lastName, email), getHeaders(owner + ":" + pass));

		memberEntity = template.postForEntity(
				"http://localhost:8080/members/new", requestEntity,
				Member.class);
		return memberEntity;
	}

	private ResponseEntity<Member> addClass(String owner, String pass,
			ClassAttended classAttended) {
		ResponseEntity<Member> memberEntity;
		Gson gson = new Gson();

		System.out.println(gson.toJson(classAttended));

		requestEntity = new HttpEntity<String>(gson.toJson(classAttended),
				getHeaders(owner + ":" + pass));

		memberEntity = template.postForEntity(
				"http://localhost:8080/classes/new", requestEntity,
				Member.class);
		return memberEntity;
	}

	private void createNewAcademy(String academyName, String academyEmail) {

		ResponseEntity<Academy> entity;
		requestEntity = new HttpEntity<String>(
				RestEventFixsture.newAcademyJSON(academyName, academyEmail),
				getHeaders("JevBjj@mail.com" + ":" + "password"));

		entity = template.postForEntity("http://localhost:8080/academy/new",
				requestEntity, Academy.class);

		assertEquals(HttpStatus.CREATED, entity.getStatusCode());
	}

	private void getAcademies(int count) {

		requestEntity = new HttpEntity<String>(getHeaders("JevBjj@mail.com"
				+ ":" + "password"));

		ResponseEntity<List> members = template.exchange(
				"http://localhost:8080/academies", HttpMethod.GET,
				requestEntity, List.class);

		System.out.println(members.getBody());
		
		assertEquals(count, members.getBody().size());
	}

	private void createNewAcademyBad(String academyName, String academyEmail,
			String user) {

		ResponseEntity<Academy> entity;
		requestEntity = new HttpEntity<String>(
				RestEventFixsture.newAcademyJSON(academyName, academyEmail),
				getHeaders(user + ":" + "password"));

		entity = template.postForEntity("http://localhost:8080/academy/new",
				requestEntity, Academy.class);

		assertEquals(HttpStatus.CREATED, entity.getStatusCode());
	}

	private ResponseEntity<Member> addNewMemberToAcademy(String email,
			String password) {
		HttpEntity<String> requestEntity;
		requestEntity = new HttpEntity<String>(
				RestEventFixsture.newMemberJSON(), getHeaders(email + ":"
						+ password));

		ResponseEntity<Member> memberEntity = template.postForEntity(
				"http://localhost:8080/members/new", requestEntity,
				Member.class);
		return memberEntity;
	}

	private ResponseEntity<List> getMembersForOwner(String user, String pass,
			int expectedCount) {
		ResponseEntity<List> members = template.exchange(
				"http://localhost:8080/members", HttpMethod.GET,
				new HttpEntity<String>(getHeaders(user + ":" + pass)),
				List.class);

		assertTrue(members.getBody().size() == expectedCount);
		return members;
	}

	@Test(expected = HttpClientErrorException.class)
	public void thatMembersCanNotBeAddedAndQueried() {

		RestTemplate template = new RestTemplate();

		HttpEntity<String> requestEntity = new HttpEntity<String>(
				RestEventFixsture.newMemberJSON(), getHeaders("letsnosh" + ":"
						+ "BAD_PASS"));

		ResponseEntity<Member> entity = template.postForEntity(
				"http://localhost:8080/members/new", requestEntity,
				Member.class);
		fail("Request passed incoreectly with status: "
				+ entity.getStatusCode());
	}

	static HttpHeaders getHeaders(String auth) {
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));

		byte[] encodedAuthorisation = Base64.encode(auth.getBytes());
		headers.add("Authorization", "Basic "
				+ new String(encodedAuthorisation));

		return headers;
	}

}
