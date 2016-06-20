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

import org.hibernate.jpa.criteria.compile.CriteriaQueryTypeQueryAdapter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

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
	
	
	@Transactional(readOnly = true)
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
	
	@Transactional(readOnly = true)
	public List<Employee> load(String sortField, Integer recordsOffset, Integer recordsLimit,  String fullSearch) {
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Employee> criteriaQuery = criteriaBuilder.createQuery( Employee.class );							
		Root<Employee> employeeRoot = criteriaQuery.from( Employee.class );
		if(fullSearch!=null) {
			criteriaQuery.where(fullSearchToPredicates(fullSearch, criteriaBuilder, criteriaQuery, employeeRoot));
		}
		TypedQuery<Employee> typedQuery = entityManager.createQuery( criteriaQuery );
		if((recordsOffset!=null) && (recordsLimit!=null)) {
			typedQuery.setFirstResult(recordsOffset);
			typedQuery.setMaxResults(recordsLimit);
		}
		List<Employee> result = typedQuery.getResultList();		
		logger.info("** DEV result size:"+result.size());
		return result;
	}
	
	private Predicate[]  fullSearchToPredicates(
			String fullSearch,
			CriteriaBuilder criteriaBuilder,
			CriteriaQuery<?> criteriaQuery,
			Root<Employee> employeeRoot
			){									
		List<Predicate> predicateList = new ArrayList<Predicate>();	
		Predicate predicate = criteriaBuilder.like(
				criteriaBuilder.upper(employeeRoot.<String>get("name")), 
				"%"+fullSearch.toUpperCase()+"%");
		Predicate  predicate2 = criteriaBuilder.like(
				criteriaBuilder.upper(employeeRoot.<String>get("surname")), 
				"%"+fullSearch.toUpperCase()+"%");
		predicate = criteriaBuilder.or(predicate, predicate2);
		predicateList.add(predicate);
		Predicate[] predicates = new Predicate[predicateList.size()];
	    predicateList.toArray(predicates);
	    return predicates;
	}

	@Transactional(readOnly = true)
	public Long getTotalCount(String fullSearch) {
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Long> criteriaQueryCount = criteriaBuilder.createQuery( Long.class );							
		Root<Employee> employeeRoot = criteriaQueryCount.from(Employee.class);
		criteriaQueryCount.select(criteriaBuilder.countDistinct(employeeRoot));
		if(fullSearch!=null) {
			criteriaQueryCount.where(fullSearchToPredicates(fullSearch, criteriaBuilder, criteriaQueryCount, employeeRoot));
		}
		Long count = entityManager.createQuery(criteriaQueryCount).getSingleResult();
		logger.info("** DEV count:"+count);
		return count;
	}
}
