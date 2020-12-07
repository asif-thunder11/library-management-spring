package com.practice.librarymanagement;

import java.util.Date;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.practice.librarymanagement.exception.RestApiException;
import com.practice.librarymanagement.model.RestApiError;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler{

//	@ExceptionHandler(MethodArgumentNotValidException.class)
	
	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		RestApiError restApiError = new RestApiError();
		restApiError.setStatus(HttpStatus.BAD_REQUEST)
		.setExceptionTitle("MethodArgumentNotValidException")
		.setExceptionBody(ex.getMessage())
		.setErrorMsg("Invalid Data Sent")
		
		//.setErrorDescription(ex.getParameter().toString()) 	//sends the controller method name
		;
		return new ResponseEntity<Object>(restApiError, restApiError.getStatus());
	}
	
	@ExceptionHandler(RestApiException.class)
	protected ResponseEntity<Object> handleBaselibraryException(RestApiException ex){
		return new ResponseEntity<Object>(ex.getRestApiError(), ex.getRestApiError().getStatus());
	}
	
	@ExceptionHandler(AccessDeniedException.class)
	protected ResponseEntity<Object> handleAccessDeniedException(AccessDeniedException ex){
		RestApiError error = new RestApiError(ex.getMessage(), ex.getStackTrace().toString());
		error.setStatus(HttpStatus.UNAUTHORIZED);
		error.setTimestamp(new Date());
		return new ResponseEntity<Object>(error, error.getStatus());
	}
	
	@ExceptionHandler(AuthenticationException.class)
	protected ResponseEntity<Object> handleAuthenticationException(AuthenticationException authEx){
		RestApiError restError = new RestApiError("Authentication Failed", "Invalid Credentials");
		restError.setStatus(HttpStatus.BAD_REQUEST);
		return new ResponseEntity<Object>(restError, restError.getStatus());
	}
	
	@ExceptionHandler(RuntimeException.class)
	protected ResponseEntity<Object> handleRuntimeException(RuntimeException ex){
		
		ex.printStackTrace();
		return new ResponseEntity<Object>(ex, HttpStatus.INTERNAL_SERVER_ERROR);
	}
}
