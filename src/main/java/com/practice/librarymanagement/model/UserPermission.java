package com.practice.librarymanagement.model;

public enum UserPermission {
	
	BOOK_READ("book:read"),
	BOOK_WRITE("book:write"),
	USER_READ("user:read"),
	USER_WRITE("user:write"),
	BOOK_RECORD_READ("record:read"),
	BOOK_RECORD_WRITE("record:write");

	String permission;
	
	private UserPermission(String permission) {
		this.permission = permission;
	}
	
	public String getPermission() {
		return permission;
	}
	
}
