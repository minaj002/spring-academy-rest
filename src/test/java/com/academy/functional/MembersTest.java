package com.academy.functional;

import static org.junit.Assert.*;

import java.util.Arrays;
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

import com.academy.core.domain.Member;
import com.academy.rest.controller.fixture.RestEventFisture;
import com.google.common.collect.Lists;

public class MembersTest {

	@Test
	public void thatOrdersCanBeAddedAndQueried() {

		RestTemplate template = new RestTemplate();

		HttpEntity<String> requestEntity = new HttpEntity<String>(
				RestEventFisture.newMemberJSON(), getHeaders("letsnosh" + ":" + "noshing"));

		ResponseEntity<Member> entity = template.postForEntity(
				"http://localhost:8080/members/new", requestEntity,
				Member.class);

		assertEquals(HttpStatus.CREATED, entity.getStatusCode());

		requestEntity = new HttpEntity<String>(getHeaders("letsnosh" + ":" + "noshing"));
//		ResponseEntity<String> members = template.getForEntity(
//				"http://localhost:8080/members", String.class, requestEntity);

		ResponseEntity<List> members =template.exchange
		 ("http://localhost:8080/members", HttpMethod.GET, new HttpEntity<String>(getHeaders("letsnosh" + ":" + "noshing")), List.class);
		
		// System.out.println ("The Order ID is " + order.getKey());
		// System.out.println ("The Location is " +
		// entity.getHeaders().getLocation());
		//
		// assertEquals(2, order.getItems().size());
	}
	@Test(expected=HttpClientErrorException.class)
	public void thatOrdersCanNotBeAddedAndQueried() {
		
		RestTemplate template = new RestTemplate();
		
		HttpEntity<String> requestEntity = new HttpEntity<String>(
				RestEventFisture.newMemberJSON(), getHeaders("letsnosh" + ":" + "BAD_PASS"));
		
		ResponseEntity<Member> entity = template.postForEntity(
				"http://localhost:8080/members/new", requestEntity,
				Member.class);
		fail("Request passed incoreectly with status: "+ entity.getStatusCode());
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
