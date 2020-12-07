package com.practice.librarymanagement.security;

import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.practice.librarymanagement.model.UserRole;


@SuppressWarnings("serial")
public class SimpleUserDetails implements UserDetails {

	List<SimpleGrantedAuthority> authorities;
	long username;
	String password;
	boolean isAccountNonExpired;
	boolean isAccountNonLocked;
	boolean isCredentialsNonExpired;
	boolean isEnabled;
	UserRole role;
	
	public SimpleUserDetails() {
		super();
		isAccountNonExpired=true;
		isAccountNonLocked=true;
		isCredentialsNonExpired=true;
		isEnabled=true;
	}
	
	public SimpleUserDetails (long username, String password, UserRole role) {
		this();
		this.username = username;
		this.password = password;
		this.role = role;
	}
	
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return role.getAuthorities();
	}

	@Override
	public String getPassword() {
		return password;
	}

	@Override
	public String getUsername() {
		return String.valueOf(username);
	}

	@Override
	public boolean isAccountNonExpired() {
		return isAccountNonExpired;
	}

	@Override
	public boolean isAccountNonLocked() {
		return isAccountNonLocked;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return isCredentialsNonExpired;
	}

	@Override
	public boolean isEnabled() {
		return isEnabled;
	}
	
}
