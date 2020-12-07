package com.practice.librarymanagement.service.implementation;

import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.practice.librarymanagement.exception.RestApiException;
import com.practice.librarymanagement.model.Result;
import com.practice.librarymanagement.model.User;
import com.practice.librarymanagement.model.UserDTO;
import com.practice.librarymanagement.repository.UserDao;
import com.practice.librarymanagement.service.LoginService;

@Service
public class LoginServiceImpl implements LoginService {

	@Autowired
	UserDao userDao;
	
	@Autowired
	PasswordEncoder passwordEncoder;

	@Override
	public List<User> getAllUsers() {

		return userDao.findAll();
	}

	@Override
	@Transactional
	public UserDTO addUser(User user) {

		Optional<User> existingUser = userDao.findByEnrollmentId(user.getEnrollmentId());

		// if user exists, exception
		existingUser.ifPresent((u) -> {
			throw new RestApiException("User Already Exists", HttpStatus.BAD_REQUEST);
		});

		// else save user and return dto
		user.setPassword( passwordEncoder.encode( user.getPassword() ) );
		return mapper_UserDto(userDao.save(user));
		
	}

	@Override
	public UserDTO loginUser(long enrollmentId, String password) {
		Optional<User> user = userDao.findByEnrollmentIdAndPassword(enrollmentId, password);

		// if user not null return and then mapper_UserDto else exception
		return mapper_UserDto( user.orElseThrow(() -> {
			throw new RestApiException("Inavlid Credentials", HttpStatus.BAD_REQUEST);
		}));
	}

	@Override
	public void logoutUser(HttpServletRequest request) {
		request.getSession().setAttribute("user", null);
	}

	public UserDTO mapper_UserDto(User user) {
		return new UserDTO(user.getEnrollmentId(), user.getName(), user.getCategory());
	}

}
