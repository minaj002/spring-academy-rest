package com.academy.config;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;

import java.util.Arrays;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.codec.Base64;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.academy.config.CoreConfig;
import com.academy.config.MVCConfig;
import com.academy.config.SecurityConfig;
import com.academy.integration.AbstractMongoDbTest;
import com.academy.rest.controller.fixture.RestEventFixsture;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(classes = { CoreConfig.class, MVCConfig.class, SecurityConfig.class })
public class RestDomainIntegrationTest extends AbstractMongoDbTest{

	@Autowired
	WebApplicationContext webApplicationContext;

	private MockMvc mockMvc;

	@Before
	public void setup() {
		mockMvc = MockMvcBuilders
				.webAppContextSetup(webApplicationContext).build();
		
		
		
		setSecurityUser("user");
	}

	@Test
	public void addAcademy() throws Exception{
		
		
		
		mockMvc
		.perform(
				post("/academy/new")
				.headers(getHeaders("letsnosh" + ":" + "noshing"))
				.content(RestEventFixsture.newAcademyJSON())
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON))
				.andDo(print()).andExpect(status().isCreated());
		
		setSecurityUser("admin@bjj.com");
		mockMvc
		.perform(
				post("/members/new")
						.headers(getHeaders("admin@bjj.com" + ":" + "password"))
						.content(RestEventFixsture.newMemberJSON())
						.contentType(MediaType.APPLICATION_JSON)
						.accept(MediaType.APPLICATION_JSON))
		.andDo(print()).andExpect(status().isCreated());
		
		mockMvc.perform(
				get("/members").contentType(
						MediaType.APPLICATION_JSON).accept(
						MediaType.APPLICATION_JSON)).andDo(print())
				.andExpect(jsonPath("$.[0].firstName").value("firstName"));
		
	}

	private void setSecurityUser(String user) {
		Authentication authToken = new UsernamePasswordAuthenticationToken (user, "pass", null);
		SecurityContextHolder.getContext().setAuthentication(authToken);
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
