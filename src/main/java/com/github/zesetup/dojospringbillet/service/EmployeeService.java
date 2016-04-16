package com.github.zesetup.dojospringbillet.service;

import java.util.List;
import org.springframework.stereotype.Service;
import com.github.zesetup.dojospringbillet.model.*;

@Service
public interface EmployeeService {
	void insertEmployee(Employee employee);
	void update(Employee employee);
	void remove(String employeeId);
	List<Employee> load(String sortField, Integer recordsOffset, Integer recordsLimit,  String fullSearch);
	Employee get(String employeeId);
	Employee getByLogin(String login);
	Long getTotalCount(String searchRegistrar);
}