package com.github.zesetup.dojospringbillet.model;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class EmployeeTest {
	private static Employee employee;
	
	@Before
	public  void init(){
		employee  = new Employee("loginName", "John", "Connor", "Engineer");
	}
	
	@Test
	public void testGetLoginNameSurname() {
		assertNotNull(employee.getName());
		assertNotNull(employee.getSurname());
		assertNotNull(employee.getLogin());
	}
}
