package com.practice.librarymanagement.service;

import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import com.practice.librarymanagement.model.Result;
import com.practice.librarymanagement.model.User;
import com.practice.librarymanagement.model.UserDTO;

public interface LoginService {

	List<User> getAllUsers();
	UserDTO addUser(User user);
	UserDTO loginUser(long enrollmentId, String password);
	void logoutUser(HttpServletRequest request);

}
