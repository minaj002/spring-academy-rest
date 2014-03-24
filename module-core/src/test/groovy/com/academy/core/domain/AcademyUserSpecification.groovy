package com.academy.core.domain

import spock.lang.Specification

class AcademyUserSpecification extends Specification {

	def "Testing that academyUser return RolesAsArray"(){
		
		given: "AcademyUser with 3 roles as enum"
			AcademyUser user = new AcademyUser();
			
			user.addRole(AcademyUser.Role.ROLE_ADMIN);
			user.addRole(AcademyUser.Role.ROLE_OWNER);
			user.addRole(AcademyUser.Role.ROLE_USER);
		
		when: "calling getRolesAsArray() method"
		
			String[] userRoles = user.getRolesAsArray();
			
		then: "return array with roles as strings"
		
			userRoles.size() == 3
			
			userRoles[0] == "ROLE_ADMIN"
			userRoles[1] == "ROLE_OWNER"
			userRoles[2] == "ROLE_USER"
			
	}
	
	
}
