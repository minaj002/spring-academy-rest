package com.academy.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.academy.core.domain.Payment;

public interface PaymentRepository extends MongoRepository<Payment, String> {

}
