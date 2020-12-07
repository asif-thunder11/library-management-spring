package com.practice.librarymanagement.security;

public class UserNamePasswordAuthenticationRequest {

	String username;
	String password;
	
	
	
	public UserNamePasswordAuthenticationRequest() {
		super();
	}
	public UserNamePasswordAuthenticationRequest(String username, String password) {
		this();
		this.username = username;
		this.password = password;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
	
}
