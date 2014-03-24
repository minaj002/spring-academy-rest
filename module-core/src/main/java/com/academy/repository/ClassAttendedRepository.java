package com.academy.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.academy.core.domain.ClassAttended;


public interface ClassAttendedRepository extends MongoRepository<ClassAttended, String>{

	List<ClassAttended> findByAcademyNameAndDate(String academyName, Date date);
	
}
