package com.practice.librarymanagement.controller;

import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import javax.validation.constraints.Size;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.practice.librarymanagement.model.User;
import com.practice.librarymanagement.model.UserDTO;
import com.practice.librarymanagement.service.LoginService;

@RestController
public class LoginController {

	@Autowired
	LoginService loginService;
	
	@PostMapping(value = "/register")
	public UserDTO register(@RequestBody User user) {
		
		return loginService.addUser(user);
	}

//	@PostMapping("/login")
//	public UserDTO doLogin(@RequestBody HashMap<String, String> creds) {
//		System.out.println("Logging in");
//		
//		return loginService.loginUser(Long.parseLong( creds.get("enrollmentId")), creds.get("password"));
//	}
	
//	@GetMapping(value = "logout")
//	public void doLogout(HttpServletRequest request) {
//		
//	}

	@GetMapping("/user")
	public List<User> getAllUsers() {
		return loginService.getAllUsers();
	}

//	@GetMapping("/user/{enrollmentId}")
//	public User getUserByEnrollmentId(@PathVariable long enrollmentId) {
//		return loginService.findByEnrollmentId(enrollmentId);
//	}
//	
	@PostMapping(value = "/user")
	public UserDTO addUser(@Valid @RequestBody User u) {
		//System.out.println(u.getName());
		return loginService.addUser(u);
	}
	
//	@DeleteMapping(value = "/user/{enrollmentId}")
//	public void deleteByEnrollmentId(@PathVariable long enrollmentId) {
//		loginService.deleteByEnrollmentId(enrollmentId);
//		return;
//	}

}
