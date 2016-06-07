package com.github.zesetup.dojospringbillet.service;

import static org.junit.Assert.*;

import javax.inject.Inject;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import com.github.zesetup.dojospringbillet.Loader;
import com.github.zesetup.dojospringbillet.model.Employee;
import com.github.zesetup.dojospringbillet.service.EmployeeService;

@ContextConfiguration("file:src/main/webapp/WEB-INF/spring/servlet-context.xml")
@RunWith(SpringJUnit4ClassRunner.class)
public class EmployeeImplTest {
	@Inject 
	EmployeeService employeeService;
	private static final Logger logger = LoggerFactory.getLogger(Loader.class);

	@Test
	public void test() {
		Employee employee  = new Employee("john", "John123", "Connor", "Engineer");
		employeeService.insertEmployee(employee);
		Employee employee2  = new Employee("sarah", "Sara", "Connor", "DBA");	
		employeeService.insertEmployee(employee2);
		java.util.List<Employee> ee  = employeeService.load(null, null, null, "John123");
		assertEquals(ee.size(), 1);
	}

}
