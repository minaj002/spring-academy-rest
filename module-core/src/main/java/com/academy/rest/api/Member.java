package com.academy.rest.api;

public class Member {

	private String memberId;
	private String firstName;
	private String lastName;
	private String dateOfBirth;
	private String street;
	private String city;
	private String email;
	private String phone;
	
	
	public String getMemberId() {
		return memberId;
	}
	public void setMemberId(String id) {
		this.memberId = id;
	}
	public String getFirstName() {
		return firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public String getDateOfBirth() {
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
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public void setDateOfBirth(String dateOfBirth) {
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
	
}
