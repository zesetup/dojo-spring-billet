package com.github.zesetup.dojospringbillet;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import javax.inject.Inject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;
import com.github.zesetup.dojospringbillet.model.Employee;
import com.github.zesetup.dojospringbillet.service.EmployeeService;


@Component
public class Loader implements ApplicationListener<ContextRefreshedEvent>{
	@Inject
	private EmployeeService employeeService;

	private static final Logger logger = LoggerFactory.getLogger(Loader.class);
	public static boolean isJUnitTest() {
	    StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();
	    List<StackTraceElement> list = Arrays.asList(stackTrace);
	    for (StackTraceElement element : list) {
	        if (element.getClassName().startsWith("org.junit.")) {
	            return true;
	        }           
	    }
	    return false;
	}
	public void onApplicationEvent(ContextRefreshedEvent event) {
		logger.info("******************** Application started ********************");
		
		if (isJUnitTest()){
			logger.info("Test environment, not loading data...");
			return;
		}
		
		Employee employee01 = new Employee("ivanov", "Ivan", "Ivanovich", "Engeneer");			
		employeeService.insertEmployee(employee01); 	 	
		Employee employee02 = new Employee("johnson", "John", "Johnson", "Project Manager");			
		employeeService.insertEmployee(employee02);
		Employee employee03 = new Employee("jonauskas", "Jonas", "Jonauskas", "Officer");			
		employeeService.insertEmployee(employee03);
		for(int i=0; i<0;i++){
			Employee employee = new Employee(
					"log"+UUID.randomUUID().toString().substring(0, 5), 
					"Name"+UUID.randomUUID().toString().substring(0, 8), 
					"Surna"+UUID.randomUUID().toString().substring(0, 10), 
					"Posit"+UUID.randomUUID().toString().substring(0, 15));			
			employeeService.insertEmployee(employee);
		}
	}
}
