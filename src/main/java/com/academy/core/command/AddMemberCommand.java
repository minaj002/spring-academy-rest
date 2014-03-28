package com.academy.core.command;

import java.util.Date;

import com.academy.core.command.response.AddMemberResult;

public class AddMemberCommand implements Command<AddMemberResult> {

	private String firstName;
	private String lastName;
	private Date dateOfBirth;
	private String street;
	private String city;
	private String email;
	private String phone;
	
	public AddMemberCommand(String firstName, String lastName,
			Date dateOfBirth, String street, String city, String email,
			String phone) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.dateOfBirth = dateOfBirth;
		this.street = street;
		this.city = city;
		this.email = email;
		this.phone = phone;
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
}
