package com.practice.librarymanagement;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

@Component
public class LibraryContext {
	
	private String username;

	public LibraryContext() {
		super();
		this.username="";
		System.out.println("LibraryContext Created");
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}
}
