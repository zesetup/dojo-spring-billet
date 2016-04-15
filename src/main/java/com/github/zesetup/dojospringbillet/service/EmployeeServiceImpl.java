package com.github.zesetup.dojospringbillet.service;

import java.util.List;
import javax.inject.Inject;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import com.github.zesetup.dojospringbillet.dao.EmployeeDao;
import com.github.zesetup.dojospringbillet.model.Employee;

@Component
@Transactional
public class EmployeeServiceImpl implements EmployeeService{

	@Inject
	private EmployeeDao employeeDao;

	@Override
	@Transactional
	public void insertEmployee(Employee employee) {
		employeeDao.insert(employee);
	}

	@Override
	public void update(Employee employee) {
		employeeDao.update(employee);
	}

	@Override
	public List<Employee> load(String sortField, Integer recordsOffset, Integer recordsLimit,  String fullSearch) {
		List<Employee> l =  employeeDao.load(sortField, recordsOffset, recordsLimit,  fullSearch);
		System.out.println("employee service load()");
		return l;
	}

	@Override
	public Employee get(String id) {
		Employee e =  employeeDao.get(id);
		return e;
	}

	@Override
	public Employee getByLogin(String login) {
		Employee e =  employeeDao.getByLogin(login);
		return e;
	}

	@Override
	public Long getTotalCount(String fullSearch) {
		return employeeDao.getTotalCount(fullSearch);
	}

	@Override
	public void remove(String id) {
		employeeDao.remove(id);
	}
}
