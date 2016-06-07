package com.github.zesetup.dojospringbillet.controller;

import static org.junit.Assert.*;

import java.util.Arrays;

import javax.inject.Inject;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpSession;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("file:src/main/webapp/WEB-INF/spring/servlet-context.xml")
@WebAppConfiguration
public class EmployeeControllerTest {

	@Inject
	WebApplicationContext webApplicationContext;
	@Inject 
	MockHttpSession session;
    @Inject
    MockHttpServletRequest request;
    
	private MockMvc mockMvc;
	
	 @Before
	 public void setup() throws Exception {
		    this.mockMvc = webAppContextSetup(webApplicationContext).build();
	 }
	
	@Test
	public void testShowEmployeesJson() throws Exception {
		this.mockMvc.perform(get("/employee")).andExpect(status().isOk());
		 //.andExpect(jsonPath("$.employee", hasSize(2))) ;
	}
/*        .andExpect(content().contentType(contentType))
        .andExpect(jsonPath("$", hasSize(2)))
        .andExpect(jsonPath("$[0].id", is(this.bookmarkList.get(0).getId().intValue())))
        .andExpect(jsonPath("$[0].uri", is("http://bookmark.com/1/" + userName)))
        .andExpect(jsonPath("$[0].description", is("A description")))
        .andExpect(jsonPath("$[1].id", is(this.bookmarkList.get(1).getId().intValue())))
        .andExpect(jsonPath("$[1].uri", is("http://bookmark.com/2/" + userName)))
        .andExpect(jsonPath("$[1].description", is("A description")));

	/*
	@Test
	public void testGetEmployee() {
		fail("Not yet implemented");
	}

	@Test
	public void testCreateEmployee() {
		fail("Not yet implemented");
	}

	@Test
	public void testUpdateEmployee() {
		fail("Not yet implemented");
	}

	@Test
	public void testRemoveEmployee() {
		fail("Not yet implemented");
	}

	}*/
}
