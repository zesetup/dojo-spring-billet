package com.github.zesetup.dojospringbillet.dao;

import static org.junit.Assert.*;

import java.util.List;

import javax.inject.Inject;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import com.github.zesetup.dojospringbillet.model.Employee;
@ContextConfiguration("file:src/main/webapp/WEB-INF/spring/servlet-context.xml")
@RunWith(SpringJUnit4ClassRunner.class)
public class EmployeeDaoTest {
	@Inject
	EmployeeDao employeeDao;	
	@Test
	@Transactional
	public void get() {
		List<Employee> employees = employeeDao.load(null, null, null, null);
		for (Employee e : employees) {
			System.out.println("************************ "+ e.getLogin() +" **************************");
			assertNotNull(employeeDao.get(e.getId()));	
		}
	}
	
	@Test
	@Transactional
	public void create() {
		Employee e = new Employee("test", "test", "test", "test");
		employeeDao.insert(e);
		assertNotNull(employeeDao.get(e.getId()));
	}
	
	@Test
	@Transactional 
	public void update() {
		Employee e = new Employee("test_", "test", "test", "test");
		employeeDao.insert(e);
		e.setName("test2");
		employeeDao.update(e);
		e = employeeDao.get(e.getId());
		assertEquals("test2", e.getName());
	}
	
	@Test
	@Transactional 
	public void delete() {
		Employee e = new Employee("test_todelete", "test", "test", "test");
		employeeDao.insert(e);
		employeeDao.remove(e.getId());
		e = employeeDao.get(e.getId());
		assertNull(e);
	}
}
