package com.academy.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.academy.core.domain.AcademyUser;

public interface AcademyUserRepository extends MongoRepository<AcademyUser, String>{

	AcademyUser findByName(String academyUsername);
	
}
