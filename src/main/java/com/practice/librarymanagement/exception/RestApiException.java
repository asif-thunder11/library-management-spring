package com.practice.librarymanagement.exception;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import com.practice.librarymanagement.model.RestApiError;

//@Component
public class RestApiException extends RuntimeException {
	
	//@Autowired
	private RestApiError restApiError;

	public RestApiException() {
		super();
		restApiError = new RestApiError();
		
	}
	
	public RestApiException(String message, HttpStatus status) {
		this();
		restApiError.setErrorMsg(message)
		.setStatus(status);
	}	
	
	public RestApiError getRestApiError() {
		return restApiError;
	}

	public void setRestApiError(RestApiError restApiError) {
		this.restApiError = restApiError;
	}
}
