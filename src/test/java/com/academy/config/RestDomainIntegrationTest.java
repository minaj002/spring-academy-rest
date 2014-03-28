package com.academy.config;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.mockito.Mockito.*;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
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
import com.academy.rest.controller.fixture.RestEventFisture;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(classes = { CoreConfig.class, MVCConfig.class, SecurityConfig.class })
public class RestDomainIntegrationTest extends AbstractMongoDbTest{

	@Autowired
	WebApplicationContext webApplicationContext;

	private MockMvc mockMvc;

	@Before
	public void setup() {
		this.mockMvc = MockMvcBuilders
				.webAppContextSetup(webApplicationContext).build();
	}

	@Test
	public void addNewMember() throws Exception{
		this.mockMvc
				.perform(
						post("/members/new")
								.content(RestEventFisture.newMemberJSON())
								.contentType(MediaType.APPLICATION_JSON)
								.accept(MediaType.APPLICATION_JSON))
				.andDo(print()).andExpect(status().isCreated());

		mockMvc.perform(
				get("/members").contentType(
						MediaType.APPLICATION_JSON).accept(
						MediaType.APPLICATION_JSON)).andDo(print())
				.andExpect(jsonPath("$.[0].firstName").value("firstName"));
	}

}
