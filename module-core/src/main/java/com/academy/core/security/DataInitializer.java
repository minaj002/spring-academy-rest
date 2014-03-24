package com.academy.core.security;

import static com.academy.core.domain.AcademyUser.Role.ROLE_ADMIN;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.academy.core.domain.AcademyUser;
import com.academy.repository.AcademyUserRepository;

@Component
public class DataInitializer {
	private final Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	AcademyUserRepository academyUserRepository;

	@Autowired
	PasswordEncoder passwordEncoder;

	@PostConstruct
	public void init() {

		logger.info("Initializing Default data");

		if (academyUserRepository.count() == 0) {

			AcademyUser user = new AcademyUser();
			user.setActive(true);
			user.setName("JevBjj@mail.com");
			user.setPassword(passwordEncoder.encode("password"));
			
			user.addRole(ROLE_ADMIN);

			academyUserRepository.save(user);
		}
		else{
			logger.info("Default data already initialized");
		}
	}

}
