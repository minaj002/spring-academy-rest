package com.academy.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.MongoDbFactory;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.SimpleMongoDbFactory;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.mongodb.MongoClient;

@Configuration
@ComponentScan(basePackages={"com.academy.service","com.academy.core.command.service","com.academy.core.command.handler","com.academy.core.query.service","com.academy.core.query.handler"})
@EnableMongoRepositories(basePackages = "com.academy.repository")
public class CoreConfig {

	
	@Bean
	public MongoDbFactory mongoDbFactory() throws Exception {
		return new SimpleMongoDbFactory(new MongoClient(), "academy");
	}
	
	@Bean
	public MongoTemplate mongoTemplate() throws Exception {
		MongoTemplate mongoTemplate = new MongoTemplate(mongoDbFactory());
		return mongoTemplate;
	}
	
	@Bean
	protected PasswordEncoder getPasswordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
}
