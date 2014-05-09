package com.academy.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.academy.core.domain.Payment;

public interface PaymentRepository extends MongoRepository<Payment, String> {
	
	List<Payment> findByAcademyNameAndDateBetween(String academyName, Date start, Date end);
	
}
