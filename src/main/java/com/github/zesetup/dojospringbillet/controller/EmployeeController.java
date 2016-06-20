package com.github.zesetup.dojospringbillet.controller;

import java.net.URISyntaxException;
import java.util.List;
import javax.inject.Inject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.github.zesetup.dojospringbillet.model.*;
import com.github.zesetup.dojospringbillet.service.*;;

 
@RestController
public class EmployeeController {
	private static final Logger logger = LoggerFactory.getLogger(EmployeeController.class);

	@Inject
    private EmployeeService employeeService;
	@RequestMapping( value = "employee", method = RequestMethod.GET)
	public	ResponseEntity<List<Employee>> showEmployeesJson(
			@RequestParam(value="sort", required=false) String sort,
			@RequestParam(value="fullSearch", required=false) String fullSearch,
			@RequestHeader(value="Range", required=false) String rangeString)	 		
			throws URISyntaxException {
		Integer offset= 0, limit= 0;
		HttpHeaders headers = new HttpHeaders();
		headers.set("Accept-Ranges", "items");
		headers.set("Cache-Control", "no-cache");
		if(rangeString != null) {
			rangeString = rangeString.replace("items=","");
			offset = Integer.valueOf(rangeString.substring(0, rangeString.indexOf("-")));
			limit  = Integer.valueOf(rangeString.substring(rangeString.indexOf("-")+1, rangeString.length()));
			limit = limit-offset+1;
		}
		Long count  =employeeService.getTotalCount(fullSearch);
		headers.set("Content-Range", "items="+offset+"-"+limit+"/"+count+"");
		if(sort!=null) {
			sort = sort.trim();
		}
		logger.info("sort="+sort+" range="+rangeString+" totalCount="+count+" offset="+offset+" limit="+limit
				+" fullSearch="+fullSearch);
		return new ResponseEntity<>(this.employeeService.load(sort, offset, limit, fullSearch), headers, HttpStatus.OK);
	}

	@RequestMapping( value = "employee/{employeeId}", method = RequestMethod.GET)
	public Employee getEmployee( @PathVariable String employeeId){
		Employee employee = employeeService.get(employeeId);
		return employee;
	}
	@RequestMapping( value = "employee", method = RequestMethod.POST)
	public ResponseEntity<String> createEmployee(@RequestBody Employee empl){
		try{
			employeeService.insertEmployee(empl);
			return new ResponseEntity<String>(HttpStatus.CREATED);
		}catch(Exception e){
			logger.warn(e.getMessage());
			return new ResponseEntity<String>(HttpStatus.CONFLICT);
		}
	}
	
	@RequestMapping( value = "employee/{employeeId}", method = RequestMethod.PUT)
	public ResponseEntity<String> updateEmployee(@PathVariable String employeeId, @RequestBody Employee employeeParam){
		Employee employee = employeeService.get(employeeId);
		employee.setName(employeeParam.getName());
		employee.setSurname(employeeParam.getSurname());
		employee.setPosition(employeeParam.getPosition());
		employee.setLogin(employeeParam.getLogin());
		try{
			employeeService.update(employee);
			return new ResponseEntity<String>(HttpStatus.ACCEPTED);
		}catch(Exception e){
			logger.warn(e.getMessage());
			return new ResponseEntity<String>(HttpStatus.CONFLICT);	
		}
	}
	@RequestMapping( value = "employee/{employeeId}", method = RequestMethod.DELETE)
	public ResponseEntity<String> removeEmployee(@PathVariable String employeeId){
		employeeService.remove(employeeId);
		return new ResponseEntity<String>(HttpStatus.NO_CONTENT);
	}
}