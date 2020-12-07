package com.practice.librarymanagement.security.jwt;

import javax.crypto.SecretKey;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;

import io.jsonwebtoken.security.Keys;

@Configuration
@ConfigurationProperties(prefix = "application.jwt")
public class JwtConfig {

		String secretKey;
		String tokenPrefix;
		int tokenExpirationAfterDays;
		
		public JwtConfig() {
			super();
		}

		public JwtConfig(String secretKey, String tokenPrefix, int tokenExpirationAfterDays) {
			this();
			this.secretKey = secretKey;
			this.tokenPrefix = tokenPrefix;
			this.tokenExpirationAfterDays = tokenExpirationAfterDays;
		}

		public String getSecretKey() {
			return secretKey;
		}

		public void setSecretKey(String secretKey) {
			this.secretKey = secretKey;
		}

		public String getTokenPrefix() {
			return tokenPrefix;
		}

		public void setTokenPrefix(String tokenPrefix) {
			this.tokenPrefix = tokenPrefix;
		}

		public int getTokenExpirationAfterDays() {
			return tokenExpirationAfterDays;
		}

		public void setTokenExpirationAfterDays(int tokenExpirationAfterDays) {
			this.tokenExpirationAfterDays = tokenExpirationAfterDays;
		}
		
		public String getAuthorizationHeader() {
			return HttpHeaders.AUTHORIZATION;
		}
		
		@Bean
		public SecretKey getHmacShaKey() {
			return Keys.hmacShaKeyFor(this.secretKey.getBytes());
		}
}
