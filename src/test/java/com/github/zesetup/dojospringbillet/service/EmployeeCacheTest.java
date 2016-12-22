package com.github.zesetup.dojospringbillet.service;

import static org.junit.Assert.assertNotNull;

import java.util.ArrayList;
import java.util.List;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.github.zesetup.dojospringbillet.model.Employee;

import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("file:src/main/webapp/WEB-INF/spring/servlet-context.xml")
public class EmployeeCacheTest {

	@Autowired
	EmployeeServiceImpl es;

	@Autowired
	CacheManager cm;
	@Ignore
	@Test
	public void t1() throws Exception {
		List<Employee> list;
		Employee e = new Employee();
		e.setName("name");
		e.setLogin("login");
		e.setIsActive(true);
		e.setNotes("notes");
		e.setPosition("manager");
		e.setSurname("surname");

		es.insertEmployee(e);

		e = es.getByLogin("login");
		System.out.println("getByLogin");
		System.out.println(e);

		e.setNotes("notes1");
		e = es.update(e);
		System.out.println("update");
		System.out.println(e);

		System.out.println("Check cache");
		for (Object key : cm.getEhcache("employeeCache").getKeys()) {
			Element element = cm.getEhcache("employeeCache").get(key);
			System.out.println(element);
		}

		System.out.println("first load");
		list = es.load(null, null, null, null);
		
		
		e = es.getByLogin("login");
		System.out.println("getByLogin");
		System.out.println(e);
		e.setNotes("NEWnotes");
		e = es.update(e);
		System.out.println("update");
		System.out.println(e);

		System.out.println("Check cache");
		for (Object key : cm.getEhcache("employeeCache").getKeys()) {
			Element element = cm.getEhcache("employeeCache").get(key);
			System.out.println(element);
		}

		System.out.println("run load twice");
		list = es.load(null, null, null, null);
		//System.out.println(list);

		list = es.load(null, null, null, null);
		//System.out.println(list);
		Employee updatedEmployee = null;
		for(Employee employee:list){
			if(employee.getNotes().equals("NEWnotes")){
				updatedEmployee = employee;
				break;
			}
		}
		assertNotNull(updatedEmployee);
	}
	@Test
	public void testChacheInsert() throws Exception {
		List<Employee> list = new ArrayList<Employee>();
		Employee e1 = new Employee("login1","name1", "surname1", "pos1");
		es.insertEmployee(e1);
		// caching
		list = es.load(null, null, null, null);
		Employee e2 = new Employee("login2","name2", "surname2", "pos2");
		es.insertEmployee(e2);
		// second load
		list = es.load(null, null, null, null);
		Employee foundEmployee = null;
		for(Employee employee:list){
			if(employee.getLogin().equals("login2")){
				foundEmployee = employee;
				break;
			}
		}
		assertNotNull(foundEmployee);
	}
}
