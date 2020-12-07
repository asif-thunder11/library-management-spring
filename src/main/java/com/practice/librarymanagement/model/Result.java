package com.practice.librarymanagement.model;

public class Result<T extends Object>{
	
	private boolean isSuccess;
	private String error_title;
	private String error_msg;
	
	private T data;

	
	public Result() { 
		super();
		isSuccess = true; //by def true. Only add data, so quick.	
		error_title = error_msg = "";
	}
	
	public Result(T data) {
		this();
		this.data = data;
	}

	public Result(boolean isSuccess, String error_title, String error_msg) {
		this();
		this.isSuccess = isSuccess;
		this.error_title = error_title;
		this.error_msg = error_msg;
	}
	
	public Result(boolean isSuccess, String error_title) {
		this();
		this.isSuccess = isSuccess;
		this.error_title = error_title;
	}

	public boolean isSuccess() {
		return isSuccess;
	}

	public void setSuccess(boolean isSuccess) {
		this.isSuccess = isSuccess;
	}

	public String getError_title() {
		return error_title;
	}

	public void setError_title(String error_title) {
		this.error_title = error_title;
	}

	public String getError_msg() {
		return error_msg;
	}

	public void setError_msg(String error_msg) {
		this.error_msg = error_msg;
	}

	public T getData() {
		return data;
	}

	public void setData(T data) {
		this.data = data;
	}
	
	
}
