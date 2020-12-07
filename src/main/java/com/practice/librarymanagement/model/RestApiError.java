package com.practice.librarymanagement.model;

import java.util.Date;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

//@Component
public class RestApiError {
																																						
	private Date timestamp;
	private String errorMsg;
	private String errorDescription;
	private String exceptionTitle;
	private String exceptionBody;
	//@Enumerated
	private HttpStatus status;
	
	public RestApiError() {
		super();
		timestamp = new Date();
		errorMsg = errorDescription = exceptionTitle = exceptionBody = "";
	}
	
	public RestApiError(String errorMsg, String errorDescription) {
		super();
		this.errorMsg = errorMsg;
		this.errorDescription = errorDescription;
	}
	
	public RestApiError(String errorMsg, String errorDescription, String exceptionTitle, String exceptionBody) {
		super();
		this.errorMsg = errorMsg;
		this.errorDescription = errorDescription;
		this.exceptionTitle = exceptionTitle;
		this.exceptionBody = exceptionBody;
	}

	public String getErrorMsg() {
		return errorMsg;
	}

	public RestApiError setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
		return this;
	}

	public String getErrorDescription() {
		return errorDescription;
	}

	public RestApiError setErrorDescription(String errorDescription) {
		this.errorDescription = errorDescription;
		return this;
	}

	public String getExceptionTitle() {
		return exceptionTitle;
	}

	public RestApiError setExceptionTitle(String exceptionTitle) {
		this.exceptionTitle = exceptionTitle;
		return this;
	}

	public String getExceptionBody() {
		return exceptionBody;
	}

	public RestApiError setExceptionBody(String exceptionBody) {
		this.exceptionBody = exceptionBody;
		return this;
	}

	public HttpStatus getStatus() {
		return status;
	}

	public RestApiError setStatus(HttpStatus status) {
		this.status = status;
		return this;
	}

	public Date getTimestamp() {
		return timestamp;
	}

	public RestApiError setTimestamp(Date timestamp) {
		this.timestamp = timestamp;
		return this;
	}
	
	
	
}
