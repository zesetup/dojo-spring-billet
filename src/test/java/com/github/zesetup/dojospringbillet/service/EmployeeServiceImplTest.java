package com.github.zesetup.dojospringbillet.service;

import static org.junit.Assert.*;

import java.util.List;

import javax.inject.Inject;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import com.github.zesetup.dojospringbillet.Loader;
import com.github.zesetup.dojospringbillet.dao.EmployeeDao;
import com.github.zesetup.dojospringbillet.model.Employee;
import com.github.zesetup.dojospringbillet.service.EmployeeService;

@ContextConfiguration("file:src/main/webapp/WEB-INF/spring/servlet-context.xml")
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class EmployeeServiceImplTest {

	@InjectMocks
	EmployeeServiceImpl employeeServiceImpl;
	
	@Mock
	EmployeeDao employeeDaoMock;
	
	private static final Logger logger = LoggerFactory.getLogger(Loader.class);

	@Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }
	
	/*
	@Test
	public void testDelete(){
		Employee employee  = new Employee("john", "John123", "Connor", "Engineer");
		employeeService.insertEmployee(employee);
		String employeeId = employee.getId();
		// Mockito expectations
        Mockito.doNothing().when(mockEmployeeService).remove(employeeId);
        // Execute the method being tested
        employeeService.remove(employeeId);
        // Validation
        assertNull(employeeService.get(employeeId));
        mockEmployeeService.remove(employeeId);
        Mockito.verify(mockEmployeeService).remove(employeeId);
	
		Employee employee  = Mockito.mock(Employee.class);
		Mockito.when(employee.getId()).thenReturn("43");
		assertEquals(employee.getId(), "43");
	}
*/

	@Test(expected = Exception.class) 
	public void testDisallowCreteDublicateLogin() throws Exception {
		Employee employee  = Mockito.mock(Employee.class);
		Mockito.doReturn("john2").when(employee).getLogin();
		Mockito.doReturn(employee).when(employeeDaoMock).getByLogin("john2");		
		employeeServiceImpl.insertEmployee(employee);
	}

	
	@Test(expected = Exception.class) 
	public void testDisallowUpdateDublicateLogin() throws Exception {
		Employee employee  = Mockito.mock(Employee.class);
		Mockito.doReturn("john2").when(employee).getLogin();
		Mockito.doReturn(employee).when(employeeDaoMock).getByLogin("john2");		
		employeeServiceImpl.update(employee);
	}
}
