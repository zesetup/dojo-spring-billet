package com.github.zesetup.dojospringbillet.dao;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.github.zesetup.dojospringbillet.Loader;
import com.github.zesetup.dojospringbillet.model.*;

@Repository
public class EmployeeDao {
	private static final Logger logger = LoggerFactory.getLogger(EmployeeDao.class);

	@PersistenceContext(unitName="hsqldb")	
	private EntityManager entityManager;
	 
	public EntityManager getEntityManager() {
		return entityManager;
	}
	 
	public void setEntityManager(EntityManager entityManager) {
		this.entityManager = entityManager;
	}

	public void insert(Employee employee) {
		entityManager.persist(employee);
	}
	
	public void update(Employee employee) {
		entityManager.merge(employee);
	}

	public void remove(String employeeId) {
		Employee rootEntity= entityManager.getReference(Employee.class, employeeId);		
		entityManager.remove(rootEntity);
	}
	
	public Employee get(String id) {
		Employee e = entityManager.find(Employee.class, id);
		return e;
	}
	
	public Employee getByLogin(String login) {		
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Employee> criteriaQuery = criteriaBuilder.createQuery( Employee.class );
		Root<Employee> EmployeeRoot = criteriaQuery.from( Employee.class );
		criteriaQuery.where( criteriaBuilder.equal( EmployeeRoot.get("login"), login ) );
		
		criteriaQuery.select( EmployeeRoot );
		TypedQuery<Employee> typedQuery = entityManager.createQuery( criteriaQuery );
		List<Employee> result = typedQuery.getResultList();;
		if(result.size()>0) {
			return result.get(0);
		}else {
			return null;
		}
	}
	 
	public List<Employee> load(String sortField, Integer recordsOffset, Integer recordsLimit,  String fullSearch) {
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Employee> criteriaQuery = criteriaBuilder.createQuery( Employee.class );							
		Root<Employee> EmployeeRoot = criteriaQuery.from( Employee.class );
		List<Predicate> predicateList = new ArrayList<Predicate>();
		if(fullSearch!=null) {			
			Predicate predicate = criteriaBuilder.like(
					criteriaBuilder.upper(EmployeeRoot.<String>get("name")), 
					"%"+fullSearch.toUpperCase()+"%");
			Predicate  predicate2 = criteriaBuilder.like(
					criteriaBuilder.upper(EmployeeRoot.<String>get("surname")), 
					"%"+fullSearch.toUpperCase()+"%");
			predicate = criteriaBuilder.or(predicate, predicate2);
			predicateList.add(predicate);
			Predicate[] predicates = new Predicate[predicateList.size()];
		    predicateList.toArray(predicates);
		    criteriaQuery.where(predicates);
		}
		TypedQuery<Employee> typedQuery = entityManager.createQuery( criteriaQuery );
		if((recordsOffset!=null) && (recordsLimit!=null)) {
			typedQuery.setFirstResult(recordsOffset);
			typedQuery.setMaxResults(recordsLimit);
		}
		List<Employee> result = typedQuery.getResultList();		
		return result;
	}
	
	public Long getTotalCount(String fullSearch) {
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Employee> criteriaQuery = criteriaBuilder.createQuery( Employee.class );									
		CriteriaQuery<Long> criteriaQueryCount = criteriaBuilder.createQuery( Long.class );							
		criteriaQueryCount.select(criteriaBuilder.count(criteriaQueryCount.from(Employee.class)));
		Root<Employee> EmployeeRoot = criteriaQuery.from( Employee.class );
		List<Predicate> predicateList = new ArrayList<Predicate>();
		if(fullSearch!=null) {			
			Predicate predicate = criteriaBuilder.like(
					criteriaBuilder.upper(EmployeeRoot.<String>get("name")), 
					"%"+fullSearch.toUpperCase()+"%");
			Predicate  predicate2 = criteriaBuilder.like(
					criteriaBuilder.upper(EmployeeRoot.<String>get("surname")), 
					"%"+fullSearch.toUpperCase()+"%");
			predicate = criteriaBuilder.or(predicate, predicate2);
			predicateList.add(predicate);
			Predicate[] predicates = new Predicate[predicateList.size()];
		    predicateList.toArray(predicates);
		    criteriaQueryCount.where(predicates);
		}
		entityManager.createQuery( criteriaQuery );
		Long count = entityManager.createQuery(criteriaQueryCount).getSingleResult();
		logger.info("** count:"+count);
		return count;
	}
}
