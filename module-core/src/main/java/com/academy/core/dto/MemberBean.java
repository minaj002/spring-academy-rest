package com.academy.core.dto;

import java.util.Date;

public class MemberBean {

	private String Id;
	private String firstName;
	private String lastName;
	private Date dateOfBirth;
	private String street;
	private String city;
	private String email;
	private String phone;
	private Date dueDate;
	
	public String getId() {
		return Id;
	}
	public String getFirstName() {
		return firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public Date getDateOfBirth() {
		return dateOfBirth;
	}
	public String getStreet() {
		return street;
	}
	public String getCity() {
		return city;
	}
	public String getEmail() {
		return email;
	}
	public String getPhone() {
		return phone;
	}
	public void setId(String id) {
		Id = id;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public void setDateOfBirth(Date dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}
	public void setStreet(String street) {
		this.street = street;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public Date getDueDate() {
		return dueDate;
	}
	public void setDueDate(Date dueDate) {
		this.dueDate = dueDate;
	}
	
	
	
}
