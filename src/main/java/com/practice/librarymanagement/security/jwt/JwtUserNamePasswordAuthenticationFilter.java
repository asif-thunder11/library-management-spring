package com.practice.librarymanagement.security.jwt;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.tomcat.jni.Local;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.bind.annotation.CrossOrigin;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.practice.librarymanagement.exception.RestApiException;
import com.practice.librarymanagement.security.UserNamePasswordAuthenticationRequest;

import io.jsonwebtoken.Jwts;

import java.io.IOException;
import java.time.LocalDate;
import java.util.Date;

public class JwtUserNamePasswordAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

	AuthenticationManager authenticationManager;
	JwtConfig jwtConfig;

	public JwtUserNamePasswordAuthenticationFilter(AuthenticationManager authenticationManager, JwtConfig jwtConfig) {
		super();
		this.authenticationManager = authenticationManager;
		this.jwtConfig = jwtConfig;
		
		
	}

	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
			throws AuthenticationException {
		
		try {
			UserNamePasswordAuthenticationRequest authRequest = new ObjectMapper().readValue
					(request.getInputStream(), UserNamePasswordAuthenticationRequest.class);
			
			//partially populated authentication object
			Authentication authentication = new UsernamePasswordAuthenticationToken(
													authRequest.getUsername(), 
													authRequest.getPassword()
													);
			//fully populated authentication object with authorities; if authentication is successful
			Authentication authenticate = authenticationManager.authenticate(authentication);
			return authenticate;
			
		} catch (IOException e) {
			throw new RestApiException( e.getMessage() , HttpStatus.BAD_REQUEST);
		} 
			
	}
	
	
	@Override
	 @CrossOrigin(origins = "http://localhost:8080") 
	protected void successfulAuthentication(HttpServletRequest request,HttpServletResponse response, FilterChain chain,
			Authentication authResult) throws IOException, ServletException {
		String token = Jwts.builder()
				.setSubject(authResult.getName())
				.claim("authorities", authResult.getAuthorities())
				.setIssuedAt(new Date(2020, 10, 28, 11, 55))
				.setExpiration(java.sql.Date.valueOf( LocalDate.now().plusDays(jwtConfig.getTokenExpirationAfterDays())))
				.signWith( jwtConfig.getHmacShaKey() )
				.compact();
		response.addHeader(jwtConfig.getAuthorizationHeader(), jwtConfig.getTokenPrefix()+token);
		
	}
}
