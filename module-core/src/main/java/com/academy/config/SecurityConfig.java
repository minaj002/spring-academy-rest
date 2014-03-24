package com.academy.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import com.academy.core.security.AcademyAuthenticationProvider;
import com.academy.core.security.DataInitializer;

@EnableWebSecurity
@ComponentScan(basePackages={"com.academy.core.security"})
@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	DataInitializer dataInitializer;
	
	@Autowired
	AcademyAuthenticationProvider academyAuthenticationProvider;
	
    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
		auth.authenticationProvider(academyAuthenticationProvider);
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		
		http.csrf().disable().authorizeRequests().antMatchers("/academy/new").hasRole("ADMIN").anyRequest().authenticated().and().httpBasic();
		
	}
}