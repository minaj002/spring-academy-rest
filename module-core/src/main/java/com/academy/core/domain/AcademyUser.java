package com.academy.core.domain;

import java.util.Collection;
import java.util.List;

import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;

import com.google.common.base.Function;
import com.google.common.collect.Collections2;
import com.google.common.collect.Lists;

public class AcademyUser {

	public enum Role {
		
		ROLE_USER, ROLE_ADMIN, ROLE_OWNER;
	}
	
	@Indexed
	private String name;

	private String password;

	@DBRef
	private Academy academy;

	private boolean active;

	private List<Role> roles = Lists.newArrayList();

	public String getName() {
		return name;
	}

	public String getPassword() {
		return password;
	}

	public Academy getAcademy() {
		return academy;
	}

	public boolean isActive() {
		return active;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public void setAcademy(Academy academy) {
		this.academy = academy;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public List<Role> getRoles() {
		return roles;
	}

	public void setRoles(List<Role> roles) {
		this.roles = roles;
	}

	public void addRole(Role role) {
		roles.add(role);
	}

	public String[] getRolesAsArray() {
		Collection<String> rolesString = Collections2.transform(roles,
				new Function<Role, String>() {
					@Override
					public String apply(Role from) {
						return from.name();
					}
				});
		String[] array = (String[]) rolesString
				.toArray(new String[roles.size()]);
		return array;
	}

}
