package com.practice.librarymanagement.security;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.practice.librarymanagement.exception.RestApiException;
import com.practice.librarymanagement.model.User;
import com.practice.librarymanagement.repository.UserDao;

@Service
public class SimpleUserDetailsService implements UserDetailsService {

	@Autowired
	PasswordEncoder passwordEncoder;
	
	@Autowired
	UserDao userDao;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Optional<User> existingUser = userDao.findByEnrollmentId(Long.parseLong(username));
		
		return mapper_UserToUserDetails(  existingUser.orElseThrow( ()-> {
			throw new UsernameNotFoundException("Username: "+username+" Not Found");
			})
		);
	}
	
	private UserDetails mapper_UserToUserDetails(User user) {
		return new SimpleUserDetails( user.getEnrollmentId(), user.getPassword(), user.getCategory() );
	}

}
