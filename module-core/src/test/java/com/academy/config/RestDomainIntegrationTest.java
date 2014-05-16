package com.academy.config;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;

import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
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
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.JsonPathResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.academy.config.CoreConfig;
import com.academy.config.MVCConfig;
import com.academy.config.SecurityConfig;
import com.academy.integration.AbstractMongoDbTest;
import com.academy.rest.api.Member;
import com.academy.rest.controller.fixture.RestEventFixsture;
import com.google.common.collect.Lists;
import com.google.gson.Gson;
import com.google.gson.internal.LinkedTreeMap;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(classes = { CoreConfig.class, MVCConfig.class, SecurityConfig.class, PropertiesConfig.class })
public class RestDomainIntegrationTest extends AbstractMongoDbTest {

    @Autowired
    WebApplicationContext webApplicationContext;

    private MockMvc mockMvc;

    @Before
    public void setup() {
	mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();

	setSecurityUser("user");
    }

    @Test
    public void addClassesAndGetClasses() throws Exception {

	mockMvc.perform(
		post("/academy/new").headers(getHeaders("letsnosh" + ":" + "noshing")).content(RestEventFixsture.newAcademyJSON()).contentType(MediaType.APPLICATION_JSON)
			.accept(MediaType.APPLICATION_JSON)).andDo(print()).andExpect(status().isCreated());

	setSecurityUser("admin@bjj.com");
	mockMvc.perform(
		post("/members/new").headers(getHeaders("admin@bjj.com" + ":" + "password")).content(RestEventFixsture.newMemberJSON()).contentType(MediaType.APPLICATION_JSON)
			.accept(MediaType.APPLICATION_JSON)).andDo(print()).andExpect(status().isCreated());

	MvcResult result = mockMvc.perform(get("/members").contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)).andDo(print())
		.andExpect(jsonPath("$.[0].firstName").value("firstName")).andReturn();
	
	
	Gson gson = new Gson();
	List<LinkedTreeMap<String, String>> list = gson.fromJson(result.getResponse().getContentAsString(), List.class);
	
	
	List<Member> members = Lists.newArrayList();
	for (LinkedTreeMap<String, String> memberResp : list) {
	    Member member = new Member();
	    member.setMemberId(memberResp.get("memberId"));
	    member.setDateOfBirth(memberResp.get("dateOfBirth"));
	    member.setFirstName(memberResp.get("firstName"));
	    members.add(member);
	}
	mockMvc.perform(
		post("/classes/new").headers(getHeaders("admin@bjj.com" + ":" + "password")).content(RestEventFixsture.newClass(members)).contentType(MediaType.APPLICATION_JSON)
			.accept(MediaType.APPLICATION_JSON)).andDo(print()).andExpect(status().isCreated());

	mockMvc.perform(get("/classes/2014-02-02").contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)).andDo(print())
		.andExpect(jsonPath("$.[0].date").value("2014-02-02"));

    }

    @Test
    public void addAcademy() throws Exception {

	mockMvc.perform(
		post("/academy/new").headers(getHeaders("letsnosh" + ":" + "noshing")).content(RestEventFixsture.newAcademyJSON()).contentType(MediaType.APPLICATION_JSON)
			.accept(MediaType.APPLICATION_JSON)).andDo(print()).andExpect(status().isCreated());

	setSecurityUser("admin@bjj.com");
	mockMvc.perform(
		post("/members/new").headers(getHeaders("admin@bjj.com" + ":" + "password")).content(RestEventFixsture.newMemberJSON()).contentType(MediaType.APPLICATION_JSON)
			.accept(MediaType.APPLICATION_JSON)).andDo(print()).andExpect(status().isCreated());

	mockMvc.perform(get("/members").contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)).andDo(print())
		.andExpect(jsonPath("$.[0].firstName").value("firstName"));

    }

    private void setSecurityUser(String user) {
	Authentication authToken = new UsernamePasswordAuthenticationToken(user, "pass", null);
	SecurityContextHolder.getContext().setAuthentication(authToken);
    }

    static HttpHeaders getHeaders(String auth) {
	HttpHeaders headers = new HttpHeaders();
	headers.setContentType(MediaType.APPLICATION_JSON);
	headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));

	byte[] encodedAuthorisation = Base64.encode(auth.getBytes());
	headers.add("Authorization", "Basic " + new String(encodedAuthorisation));

	return headers;
    }

}
