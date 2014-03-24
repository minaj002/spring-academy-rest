package com.academy.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.academy.core.domain.Member;

public interface MemberRepository extends MongoRepository<Member, String> {

	List<Member> findByAcademyName(String academyName);
	
}
