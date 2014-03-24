package com.academy.core.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.academy.core.domain.AcademyUser;
import com.academy.repository.AcademyUserRepository;

@Service
//public class AcademyUserServiceImpl implements UserDetailsService {
	public class AcademyUserServiceImpl implements AcademyUserService {

	@Autowired
	private AcademyUserRepository academyUserRepository;

	@Override
	public AcademyUser loadUserByUsername(String username) {
		return academyUserRepository.findByName(username);
	}
	
	
	
	
	

//	@Override
//	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//		
//		//passwordEncoder.
//		
//		AcademyUser academyUser = academyUserRepository.findByName(username);
//		Collection<? extends GrantedAuthority> auths= AuthorityUtils.commaSeparatedStringToAuthorityList("ROLE_USER,ROLE_OWNER,ROLE_ADMIN");
//		UserDetails userDetail = new User(username, academyUser.getPassword(), academyUser.isActive(), // enabled
//                true, // account not expired
//                true, // credentials not expired
//                true, // account not locked
//                auths);
//		return userDetail;
//	}

}
