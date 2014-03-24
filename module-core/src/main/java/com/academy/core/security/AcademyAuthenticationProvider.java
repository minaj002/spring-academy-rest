package com.academy.core.security;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.AbstractUserDetailsAuthenticationProvider;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.security.core.userdetails.User;

import com.academy.core.domain.AcademyUser;

@Component
public class AcademyAuthenticationProvider extends
		AbstractUserDetailsAuthenticationProvider {

	private final Logger logger = LoggerFactory.getLogger(AcademyAuthenticationProvider.class);
	
	@Autowired 
	AcademyUserService userService;
	
    @Autowired 
    private PasswordEncoder encoder;
	
	@Override
	protected void additionalAuthenticationChecks(UserDetails userDetails,
			UsernamePasswordAuthenticationToken authentication)
			throws AuthenticationException {

	}

	@Override
	protected UserDetails retrieveUser(String username,
			UsernamePasswordAuthenticationToken authentication)
			throws AuthenticationException {
		 String password = (String) authentication.getCredentials();
	        if (!StringUtils.hasText(password)) {
	        	logger.warn("Username {}: no password provided", username);
	            throw new BadCredentialsException("Please enter password");
	        }

	        AcademyUser user = userService.loadUserByUsername(username);
	        if (user == null) {
	        	logger.warn("Username {} password {}: user not found", username, password);
	            throw new UsernameNotFoundException("Invalid Login");
	        }
	        
	        if (!encoder.matches(password, user.getPassword())) {
	        	logger.warn("Username {} password {}: invalid password", username, password);
	            throw new BadCredentialsException("Invalid Login");
	        }
	        
	        final List<GrantedAuthority> auths =AuthorityUtils.createAuthorityList(user.getRolesAsArray());
	        
	        return new User(username, password, user.isActive(), // enabled
	                true, // account not expired
	                true, // credentials not expired
	                true, // account not locked
	                auths);
	}

}
