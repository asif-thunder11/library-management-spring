package com.practice.librarymanagement.model;

public class UserDTO {

	long enrollmentId;
	String name;
	UserRole category;

	public UserDTO() {
		super();
	}

	/**
	 * 
	 * @param enrollmentId
	 * @param name
	 * @param category
	 */
	public UserDTO(long enrollmentId, String name, UserRole category) {
		this();
		this.enrollmentId = enrollmentId;
		this.name = name;
		this.category = category;
	}

	public long getEnrollmentId() {
		return enrollmentId;
	}

	public void setEnrollmentId(long enrollmentId) {
		this.enrollmentId = enrollmentId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public UserRole getCategory() {
		return category;
	}

	public void setCategory(UserRole category) {
		this.category = category;
	}

}
