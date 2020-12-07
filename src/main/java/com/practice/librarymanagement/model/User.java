package com.practice.librarymanagement.model;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Size;

import org.springframework.security.core.authority.SimpleGrantedAuthority;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.collect.Sets;

import static com.practice.librarymanagement.model.UserPermission.*;

@Entity(name = "user")
@Table(name = "users")
public class User {
	
	@Id
	//@Size(min = 8, max = 8, message = "ID should be 8 characters long.")
	private long enrollmentId;
	
	@JsonProperty
	@Size(min = 3, max = 40, message = "Name length should be between 3 & 40.")
	private String name;
	
	@JsonProperty
	@Size(min = 6, message = "Password length should be atleast 6 characters long.")
	private String password;
	
	private UserRole category;

	public User() {
		super();
		this.category = UserRole.NORMAL;
	}
	

	public User(long enrollmentId, String name, String password) {
		this();
		this.enrollmentId = enrollmentId;
		this.name = name;
		this.password = password;
	}

	public User(long enrollmentId, String name, String password, UserRole category) {
		this();
		this.enrollmentId = enrollmentId;
		this.name = name;
		this.password = password;
		this.category = category;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}


	public UserRole getCategory() {
		return category;
	}


	public void setCategory(UserRole category) {
		this.category = category;
	}
	
	public long getEnrollmentId() {
		return enrollmentId;
	}


	public void setEnrollmentId(long enrollmentId) {
		this.enrollmentId = enrollmentId;
		
	}	
	
}

