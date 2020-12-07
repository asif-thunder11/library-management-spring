package com.practice.librarymanagement.security;

import java.util.Arrays;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import com.practice.librarymanagement.LibraryContext;
import com.practice.librarymanagement.security.jwt.JwtConfig;
import com.practice.librarymanagement.security.jwt.JwtTokenVerifier;
import com.practice.librarymanagement.security.jwt.JwtUserNamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
	
	PasswordEncoder passwordEncoder;
	UserDetailsService userDetailsService;
	JwtConfig jwtConfig;
	SecretKey secretKey;
	LibraryContext context;
	
	@Autowired
	public WebSecurityConfig(PasswordEncoder passwordEncoder,
							 UserDetailsService userDetailsService,
							 JwtConfig jwtConfig,
							 SecretKey secretKey,
							 LibraryContext context) {
		this.passwordEncoder = passwordEncoder;
		this.userDetailsService = userDetailsService;
		this.jwtConfig = jwtConfig;
		this.secretKey = secretKey;
		this.context = context;
	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
	
		http
			.cors()	 //.configurationSource( request -> new CorsConfiguration().applyPermitDefaultValues() )
			.and()
			.csrf().disable()
			//.addFilterBefore(corsFilter(), JwtUserNamePasswordAuthenticationFilter.class)
			.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
			.and()
			.addFilter(new JwtUserNamePasswordAuthenticationFilter(authenticationManager(), jwtConfig))
			.addFilterAfter(new JwtTokenVerifier(jwtConfig.getHmacShaKey(), jwtConfig, context), JwtUserNamePasswordAuthenticationFilter.class)
			.authorizeRequests().antMatchers(HttpMethod.POST, "/register").permitAll()
			.and()
			.authorizeRequests().antMatchers("/forAngular/*").permitAll()
			.anyRequest().authenticated()
			;
	}
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.authenticationProvider(getDaoAuthenticationProvider());
	}
	
	@Bean
	public DaoAuthenticationProvider getDaoAuthenticationProvider() {
		DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
		provider.setUserDetailsService(this.userDetailsService);
		provider.setPasswordEncoder(this.passwordEncoder);
		
		return provider;
	}
	
	@Bean
	CorsConfigurationSource corsConfigurationSource() {
		CorsConfiguration configuration = new CorsConfiguration();
		configuration.setAllowedOrigins(Arrays.asList("*"));
		configuration.setAllowedMethods(Arrays.asList("GET","POST","PUT", "DELETE"));
		configuration.setAllowedHeaders(Arrays.asList("Authorization", "Cache-Control", "Content-Type"));
		configuration.setExposedHeaders( Arrays.asList("Authorization", "Cache-Control", "Content-Type") );
		//configuration.setAllowCredentials(true);
		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		source.registerCorsConfiguration("/**", configuration);
		return source;
	}

}
