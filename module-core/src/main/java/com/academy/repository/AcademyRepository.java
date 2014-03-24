package com.academy.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.academy.core.domain.Academy;

public interface AcademyRepository extends MongoRepository<Academy, String>{

}
