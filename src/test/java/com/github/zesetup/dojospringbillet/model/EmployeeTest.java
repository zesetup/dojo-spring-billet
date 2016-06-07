package com.github.zesetup.dojospringbillet.model;

import static org.junit.Assert.*;


import org.junit.BeforeClass;
import org.junit.Test;

public class EmployeeTest {
	private static Employee employee;
	
	@BeforeClass
	public static void init(){
		employee  = new Employee("loginName", "John", "Connor", "Engineer");
	}
	
	@Test
	public void testGetLoginNameSurname() {
		assertNotNull(employee.getName());
		assertNotNull(employee.getSurname());
		assertNotNull(employee.getLogin());
	}

}
