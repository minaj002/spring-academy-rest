package com.academy.core.security;

import com.academy.core.domain.AcademyUser;

public interface AcademyUserService {

	AcademyUser loadUserByUsername(String username);
	
}
