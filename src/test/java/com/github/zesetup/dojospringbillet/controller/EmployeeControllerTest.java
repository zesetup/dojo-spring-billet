package com.github.zesetup.dojospringbillet.controller;

import java.io.IOException;
import javax.inject.Inject;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.mock.http.MockHttpOutputMessage;
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

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.zesetup.dojospringbillet.model.Employee;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("file:src/main/webapp/WEB-INF/spring/servlet-context.xml")
@WebAppConfiguration
public class EmployeeControllerTest {
	@Inject
	private WebApplicationContext wac;

	private MockMvc mockMvc;
	/*	private MediaType contentType = new MediaType(MediaType.APPLICATION_JSON.getType(),
            MediaType.APPLICATION_JSON.getSubtype(),
            Charset.forName("utf8"));
	 */
	@Before
	public void setup () {
		DefaultMockMvcBuilder builder = MockMvcBuilders.webAppContextSetup(this.wac);
		this.mockMvc = builder.build();
	}

	@Test
	public void testUserController () throws Exception {
		// create one more employee
		MockHttpServletRequestBuilder  builder = MockMvcRequestBuilders.post("/employee")
				.contentType(MediaType.APPLICATION_JSON)
				.content(createEmployeeInJson("login1",
						"name1",
						"surname1",
						"position1"));
		this.mockMvc.perform(builder)
		.andExpect(MockMvcResultMatchers.status()
				.isCreated());
		// get all employees
		builder = MockMvcRequestBuilders.get("/employee")
				.accept(MediaType.APPLICATION_JSON);
		this.mockMvc.perform(builder)
		.andExpect(MockMvcResultMatchers.status()
				.isOk())
		.andDo(MockMvcResultHandlers.print());
	}

	private  String createEmployeeInJson (String login, String name, String surname, String position) throws HttpMessageNotWritableException, IOException {
		Employee e = new Employee("login1", "name1", "surname1", "position1");
		MappingJackson2HttpMessageConverter mappingJackson2HttpMessageConverter =  jacksonConverter();
		MockHttpOutputMessage mockHttpOutputMessage = new MockHttpOutputMessage();
		mappingJackson2HttpMessageConverter.write(
				e, MediaType.APPLICATION_JSON, mockHttpOutputMessage);
		System.out.println("*** JSON:"+mockHttpOutputMessage.getBodyAsString());
		return mockHttpOutputMessage.getBodyAsString();
	}

	private  MappingJackson2HttpMessageConverter jacksonConverter() {
		ObjectMapper objectMapper = new ObjectMapper();
		MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();
		converter.setObjectMapper(objectMapper);
		return converter;
	}
}
