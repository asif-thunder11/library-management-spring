package com.practice.librarymanagement.model;

import static com.practice.librarymanagement.model.UserPermission.BOOK_READ;
import static com.practice.librarymanagement.model.UserPermission.BOOK_RECORD_WRITE;
import static com.practice.librarymanagement.model.UserPermission.BOOK_WRITE;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.security.core.authority.SimpleGrantedAuthority;

public enum UserRole {
	
	NORMAL( List.of(BOOK_READ, BOOK_RECORD_WRITE) ), 
	ADMIN( List.of(BOOK_READ, BOOK_WRITE) );

	List<UserPermission> permissions;
	
	private UserRole( List<UserPermission> permissions) {
		this.permissions = permissions;
	}

	public List<UserPermission> getPermissions() {
		return permissions;
	}

	public void setPermissions(List<UserPermission> permissions) {
		this.permissions = permissions;
	}

	public Set<SimpleGrantedAuthority> getAuthorities(){
		Set<SimpleGrantedAuthority> authorities;
	
		authorities = permissions.stream()
			.map( permission -> new SimpleGrantedAuthority(permission.getPermission()) )
			.collect(Collectors.toSet());
		authorities.add( new SimpleGrantedAuthority("ROLE_"+this.name()));
		return authorities;
	}
	

}
