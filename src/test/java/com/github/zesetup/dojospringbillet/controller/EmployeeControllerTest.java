package com.github.zesetup.dojospringbillet.controller;

import javax.inject.Inject;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.DefaultMockMvcBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("file:src/main/webapp/WEB-INF/spring/servlet-context.xml")
@WebAppConfiguration
public class EmployeeControllerTest {
	@Inject
	private WebApplicationContext wac;

	private MockMvc mockMvc;

	@Before
	public void setup () {
		DefaultMockMvcBuilder builder = MockMvcBuilders.webAppContextSetup(this.wac);
		this.mockMvc = builder.build();
	}

	@Test
	public void testUserController () throws Exception {

		// create one more user
		 MockHttpServletRequestBuilder  builder = MockMvcRequestBuilders.post("/employee")
										 .contentType(MediaType.APPLICATION_JSON)
										 .content(createEmployeeInJson("login1",
																   "name1",
																   "surname1",
																   "position1"));
 
		this.mockMvc.perform(builder)
					.andExpect(MockMvcResultMatchers.status()
													.isCreated());

		// get all users
		builder = MockMvcRequestBuilders.get("/employee")
										.accept(MediaType.APPLICATION_JSON);
		this.mockMvc.perform(builder)
					.andExpect(MockMvcResultMatchers.status()
													.isOk())
					.andDo(MockMvcResultHandlers.print());

	}

	private static String createEmployeeInJson (String login, String name, String surname, String position) {
		return "{ \"login\": \"" + login + "\", " 
				+ "\"name\": \"" + name + "\", " 
				+ "\"surname\":\"" + surname + "\"," 
				+ "\"position\":\"" + position + "\"}";
	}
}
