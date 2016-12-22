package com.github.zesetup.dojospringbillet.model;

import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonView;

@Entity
@Table(name = "employee")
public class Employee {		
	public void setSurname(String surname) {
		this.surname = surname;
	}
	@Id
	@Column(name = "employeeId", unique = true,  nullable = false, length = 36)
	public String employeeId;	

	@JsonView(JsonViews.EmployeesList.class)
	@Column(name = "login", unique = true, nullable = true, length = 32)
	private String login;

	@JsonView(JsonViews.EmployeesList.class)
	@Column(name = "name", nullable = false, length = 32)
	private String name;

	@Column(name = "surname", nullable = false, length = 32)
	@JsonView(JsonViews.EmployeesList.class)
	private String surname;

	@Column(name = "position", nullable = false, length = 64)
	@JsonView(JsonViews.EmployeesList.class)
	private String position; 

	@JsonView(JsonViews.EmployeesList.class)
	@Column(name = "isActive")
	private Boolean isActive = true;

	@JsonView(JsonViews.EmployeesList.class)
	@Column(name = "notes", unique = false,  nullable = true, length = 256)
	private String notes;
	

	public Employee(){
		this.employeeId = UUID.randomUUID().toString();
	}

	public Employee(String login, String name, String surname, String position){
		this.login = login;
		this.name = name;
		this.surname = surname;
		this.position = position;
		this.employeeId = UUID.randomUUID().toString();
	}
	@JsonView(JsonViews.EmployeesList.class)
	public String getId() {
		return employeeId;
	}

	public String getLogin() {
		return login+"";
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSurname() {
		return surname;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getPosition() {
		return position;
	}

	public void setPosition(String position) {
		this.position = position;
	}

	public Boolean getIsActive() {
		return isActive;
	}

	public void setIsActive(Boolean isActive) {
		this.isActive = isActive;
	}

	public String getNotes() {
		return notes;
	}

	public void setNotes(String notes) {
		this.notes = notes;
	}
}
