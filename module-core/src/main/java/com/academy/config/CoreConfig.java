package com.academy.config;

import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.MongoDbFactory;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.SimpleMongoDbFactory;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.mongodb.DB;
import com.mongodb.MongoClient;

@Configuration
@ComponentScan(basePackages={"com.academy.service","com.academy.core.command.service","com.academy.core.command.handler","com.academy.core.query.service","com.academy.core.query.handler"})
@EnableMongoRepositories(basePackages = "com.academy.repository")
public class CoreConfig {

	
	@Bean
	public MongoDbFactory mongoDbFactory() throws Exception {
		MongoClient mongoClient = new MongoClient("ds045137.mongolab.com",45137);
		
		DB db = mongoClient.getDB("heroku_app23331098");
		
		db.authenticate("heroku_app23331098", "r7sir4ocq66034jq4gs5svpvbu".toCharArray());
		
		return new SimpleMongoDbFactory(mongoClient, "heroku_app23331098");
//		return new SimpleMongoDbFactory(new MongoClient(), "academy");
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
