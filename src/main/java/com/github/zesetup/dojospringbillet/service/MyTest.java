package com.github.zesetup.dojospringbillet.service;

import java.util.List;

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
public class MyTest {

	@Autowired
	EmployeeServiceImpl es;

	@Autowired
	CacheManager cm;

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
		e.setNotes("NEW notes");
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
		System.out.println(list);

		list = es.load(null, null, null, null);
		System.out.println(list);
	}

}
