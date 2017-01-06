package com.github.zesetup.dojospringbillet.service;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.UUID;

import javax.annotation.Resource;
import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.ehcache.EhCacheCache;
import org.springframework.cache.ehcache.EhCacheCacheManager;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.github.zesetup.dojospringbillet.controller.EmployeeController;
import com.github.zesetup.dojospringbillet.dao.EmployeeDao;
import com.github.zesetup.dojospringbillet.model.Employee;

import net.sf.ehcache.Ehcache;
import net.sf.ehcache.Element;

@Component
@Transactional
public class EmployeeServiceImpl implements EmployeeService{

	private static final Logger logger = LoggerFactory.getLogger(EmployeeController.class);
	
	//@Resource(name="cacheManager")
    //private EhCacheCacheManager cacheManager;
	
	@Inject
	private EhCacheCacheManager cacheManager;
	
	@Inject
	private EmployeeDao employeeDao;

	@Override
	@Transactional
	public Employee insertEmployee(Employee employee) throws Exception {
		Employee employeeForCheck = getByLogin(employee.getLogin());
		if(employeeForCheck!=null) {
			if( (!employeeForCheck.getId().equals(employee.getId())) && (employeeForCheck.getLogin().equals(employee.getLogin()))) {
				throw new Exception("Dublicate login:"+employee.getLogin());
			}
		}
		employeeDao.insert(employee);
		
		Ehcache    cache = ( (EhCacheCache) cacheManager.getCache("employeeCache")).getNativeCache();;
		/**/
		Map<Object, Element>  elements = cache.getAll(cache.getKeys());
	    for (Element element : elements.values()) {
	    	ArrayList empList = (ArrayList) element.getObjectValue();
	    	empList.add(employee);
	    }
	    /**/
/*	    
		for (Object key : cache.getKeys()) {
			Element element = cache.get(key);
			ArrayList empList = (ArrayList) element.getObjectValue();
			empList.add(employee);
		}
	    
*/
		return employee;
	}

	@Override
	public Employee update(Employee employee) throws Exception {
		Employee employeeForCheck = getByLogin(employee.getLogin());
		if(employeeForCheck!=null) {
			if( (!employeeForCheck.getId().equals(employee.getId())) && (employeeForCheck.getLogin().equals(employee.getLogin()))) {
				throw new Exception("Dublicate login:"+employee.getLogin());
			}
		}
		employeeDao.update(employee);
		/**/
		Ehcache    cache = ( (EhCacheCache) cacheManager.getCache("employeeCache")).getNativeCache();;
		Map<Object, Element>  elements = cache.getAll(cache.getKeys());
	    for (Element element : elements.values()) {
	    	ArrayList empList = (ArrayList) element.getObjectValue();
	    	for(int i=0; i<empList.size(); i++) {
	    		Employee e = (Employee) empList.get(i);
	    		if(e.getId().equals(employee.getId())) {
	    			logger.info("found Employee to update: "+e.getLogin());
	    			empList.set(i, employee);
	    			cache.put(new Element(element.getObjectKey(), element.getObjectValue()));
	    			break;
	    		}
	    	}
	    }
	    /**/
		logger.info(">>> employee updated");
		return employee;
	}

	@Override
	@Cacheable(value="employeeCache")
	public List<Employee> load(String sortField, Integer recordsOffset, Integer recordsLimit,  String fullSearch) {
		logger.info(">>> load employees fired");
		List<Employee> l =  employeeDao.load(sortField, recordsOffset, recordsLimit,  fullSearch);
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
	//@CacheEvict(value="employeeCache", key="#id")
	public Employee remove(String id) {
		Employee employee =  employeeDao.get(id);
		employeeDao.remove(id);
		Ehcache    cache = ( (EhCacheCache) cacheManager.getCache("employeeCache")).getNativeCache();;
		Map<Object, Element>  elements = cache.getAll(cache.getKeys());
	    for (Element element : elements.values()) {
	    	ArrayList empList = (ArrayList) element.getObjectValue();
	    	for(int i=0; i<empList.size(); i++) {
	    		Employee e = (Employee) empList.get(i);
	    		if(e.getId().equals(employee.getId())) {
	    			logger.info("found Employee to update: "+e.getLogin());
	    			empList.remove(i);
	    			//empList.set(i, employee);
	    			cache.put(new Element(element.getObjectKey(), element.getObjectValue()));
	    			break;
	    		}
	    	}
	    }	
	    
		return employee;
	}
}
