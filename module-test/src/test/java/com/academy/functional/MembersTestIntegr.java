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
import com.google.common.collect.Lists;
import com.google.gson.Gson;

public class MembersTestIntegr {

    RestTemplate template = new RestTemplate();
    HttpEntity<String> requestEntity;
    //String host = "http://localhost:8080";

     String host = "http://academy-manager-v1.herokuapp.com";

    @Test
    public void thatNewAcademyCreated() {
	createNewAcademy("BjjAcademy", "admin@bjj.com");
    }

    @SuppressWarnings("unchecked")
    @Test
    public void thatAcademyCanBeAddedAndMembersCanBeAddedToNewAcademy() {

	createNewAcademy("BjjAcademy", "admin@bjj.com");

	ResponseEntity<List> members = getMembersForOwner("admin@bjj.com", "password", 0);

	ResponseEntity<Member> memberEntity = addNewMemberToAcademy("admin@bjj.com", "password");

	assertEquals(HttpStatus.CREATED, memberEntity.getStatusCode());

	members = getMembersForOwner("admin@bjj.com", "password", 1);

	memberEntity = addNewMember("admin@bjj.com", "password", "first2", "last2", "last2@email.com");

	assertEquals(HttpStatus.CREATED, memberEntity.getStatusCode());

	members = getMembersForOwner("admin@bjj.com", "password", 2);

	createNewAcademy("EEacademy", "admin@ee.com");

	members = getMembersForOwner("admin@ee.com", "password", 0);
	memberEntity = addNewMember("admin@ee.com", "password", "first3", "last3", "last3@email.com");
	members = getMembersForOwner("admin@ee.com", "password", 1);
	memberEntity = addNewMember("admin@ee.com", "password", "first4", "last4", "last4@email.com");
	members = getMembersForOwner("admin@ee.com", "password", 2);

	ClassAttended classAttended = new ClassAttended();
	classAttended.setMembers(members.getBody());
	classAttended.setDate("2014-12-12");

	addClass("admin@ee.com", "password", classAttended);

	@SuppressWarnings("unchecked")
	LinkedHashMap<String, String> addedMember = (LinkedHashMap<String, String>) members.getBody().get(0);

	addPayment("admin@ee.com", "password", addedMember.get("memberId"), "2012-01-08", "2012-02-08", BigDecimal.valueOf(43));

	ResponseEntity<List> payments = getPayments("admin@ee.com", "password", "2012-01-02", 1);
	
	
	getAcademies(2);

	deleteMember("admin@ee.com", "password", addedMember);
	members = getMembersForOwner("admin@ee.com", "password", 1);
	
	addedMember = (LinkedHashMap<String, String>) members.getBody().get(0);
	deleteMember("admin@ee.com", "password", addedMember);
	members = getMembersForOwner("admin@ee.com", "password", 0);

    }

    private void deleteMember(String user, String pass, LinkedHashMap<String, String> member) {

	ResponseEntity<Member> memberEntity;

	System.out.println(member);
	Gson gson = new Gson();
	requestEntity = new HttpEntity<String>(gson.toJson(member), getHeaders(user + ":" + pass));

	memberEntity = template.postForEntity(host + "/members/delete", requestEntity, Member.class);

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

	ResponseEntity<List> members = getMembersForOwner("admin@bjj.com", "password", 4);

	LinkedHashMap<String, String> addedMember = (LinkedHashMap<String, String>) members.getBody().get(0);

	String id = (String) addedMember.get("memberId");
	addPayment("admin@bjj.com", "password", id, "2012-01-08", "2012-02-08", BigDecimal.valueOf(43));
    }

    @Test
    public void addClass() {
	ResponseEntity<List> members = getMembersForOwner("admin@ee.com", "password", 2);
	ClassAttended classAttended = new ClassAttended();
	classAttended.setMembers(members.getBody());
	classAttended.setDate("2014-12-12");

	addClass("admin@ee.com", "password", classAttended);
    }

    @Test
    public void getPayments() {
	getPayments("admin@ee.com", "password", "2012-01-02", 1);
    }

    private ResponseEntity<List> getPayments(String owner, String pass, String date, int count) {

	requestEntity = new HttpEntity<String>(getHeaders(owner + ":" + pass));

	ResponseEntity<List> payments = template.exchange(host + "/payments/" + date, HttpMethod.GET, requestEntity, List.class);

	System.out.println(payments.getBody());

	assertEquals(count, payments.getBody().size());
	return payments;
    }

    private void addPayment(String owner, String pass, String memberId, String date, String dueDate, BigDecimal amount) {
	ResponseEntity<Payment> paymentEntity;

	requestEntity = new HttpEntity<String>(RestEventFixsture.newPayment(memberId, date, dueDate, amount), getHeaders(owner + ":" + pass));

	paymentEntity = template.postForEntity(host + "/payments/new", requestEntity, Payment.class);

	assertEquals(HttpStatus.CREATED, paymentEntity.getStatusCode());
    }

    private ResponseEntity<Member> addNewMember(String owner, String pass, String firstName, String lastName, String email) {
	ResponseEntity<Member> memberEntity;
	requestEntity = new HttpEntity<String>(RestEventFixsture.newMemberJSON(firstName, lastName, email), getHeaders(owner + ":" + pass));

	memberEntity = template.postForEntity(host + "/members/new", requestEntity, Member.class);
	return memberEntity;
    }

    private ResponseEntity<Member> addClass(String owner, String pass, ClassAttended classAttended) {
	ResponseEntity<Member> memberEntity;
	Gson gson = new Gson();

	System.out.println(gson.toJson(classAttended));

	requestEntity = new HttpEntity<String>(gson.toJson(classAttended), getHeaders(owner + ":" + pass));

	memberEntity = template.postForEntity(host + "/classes/new", requestEntity, Member.class);
	return memberEntity;
    }

    private void createNewAcademy(String academyName, String academyEmail) {

	ResponseEntity<Academy> entity;
	requestEntity = new HttpEntity<String>(RestEventFixsture.newAcademyJSON(academyName, academyEmail), getHeaders("JevBjj@mail.com" + ":" + "password"));

	entity = template.postForEntity(host + "/academy/new", requestEntity, Academy.class);
	// entity = template.postForEntity("http://localhost:8080/academy/new",
	// requestEntity, Academy.class);

	assertEquals(HttpStatus.CREATED, entity.getStatusCode());
    }

    private void getAcademies(int count) {

	requestEntity = new HttpEntity<String>(getHeaders("JevBjj@mail.com" + ":" + "password"));

	ResponseEntity<List> members = template.exchange(host + "/academies", HttpMethod.GET, requestEntity, List.class);

	System.out.println(members.getBody());

	assertEquals(count, members.getBody().size());
    }

    private void createNewAcademyBad(String academyName, String academyEmail, String user) {

	ResponseEntity<Academy> entity;
	requestEntity = new HttpEntity<String>(RestEventFixsture.newAcademyJSON(academyName, academyEmail), getHeaders(user + ":" + "password"));

	entity = template.postForEntity(host + "/academy/new", requestEntity, Academy.class);

	assertEquals(HttpStatus.CREATED, entity.getStatusCode());
    }

    private ResponseEntity<Member> addNewMemberToAcademy(String email, String password) {
	HttpEntity<String> requestEntity;
	requestEntity = new HttpEntity<String>(RestEventFixsture.newMemberJSON(), getHeaders(email + ":" + password));

	ResponseEntity<Member> memberEntity = template.postForEntity(host + "/members/new", requestEntity, Member.class);
	return memberEntity;
    }

    private ResponseEntity<List> getMembersForOwner(String user, String pass, int expectedCount) {
	ResponseEntity<List> members = template.exchange(host + "/members", HttpMethod.GET, new HttpEntity<String>(getHeaders(user + ":" + pass)), List.class);

	assertEquals(expectedCount, members.getBody().size());
	return members;
    }

    @Test(expected = HttpClientErrorException.class)
    public void thatMembersCanNotBeAddedAndQueried() {

	RestTemplate template = new RestTemplate();

	HttpEntity<String> requestEntity = new HttpEntity<String>(RestEventFixsture.newMemberJSON(), getHeaders("letsnosh" + ":" + "BAD_PASS"));

	ResponseEntity<Member> entity = template.postForEntity(host + "/members/new", requestEntity, Member.class);
	fail("Request passed incoreectly with status: " + entity.getStatusCode());
    }

    static HttpHeaders getHeaders(String auth) {
	HttpHeaders headers = new HttpHeaders();
	headers.setContentType(MediaType.APPLICATION_JSON);
	headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));

	byte[] encodedAuthorisation = Base64.encode(auth.getBytes());
	headers.add("Authorization", "Basic " + new String(encodedAuthorisation));

	return headers;
    }

    @Test
    public void requestAsString() {
	System.out.println(RestEventFixsture.newAcademyJSON("academy", "email@email.com"));
	System.out.println(RestEventFixsture.newMemberJSON());
	System.out.println(RestEventFixsture.newPayment("15648864", "1985-12-31", "2014-12-31", BigDecimal.valueOf(43.00)));
	Gson gson = new Gson();
	ClassAttended classAttended = new ClassAttended();
	List<Member> members = Lists.newArrayList();
	Member member = new Member();
	member.setFirstName("firstName");
	member.setLastName("lastName");
	member.setDateOfBirth("1981-12-02");
	member.setMemberId("1354684sdf43");
	members.add(member);
	classAttended.setMembers(members);
	classAttended.setDate("2014-12-12");
	System.out.println(gson.toJson(classAttended));
    }

}
