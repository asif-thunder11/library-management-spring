package com.practice.librarymanagement.security.jwt;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import javax.crypto.SecretKey;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import com.google.common.base.Strings;
import com.practice.librarymanagement.LibraryContext;
import com.practice.librarymanagement.exception.RestApiException;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;

public class JwtTokenVerifier extends OncePerRequestFilter {
	
	//no autowiring cuz this class is instantiated manually
	
    private final SecretKey secretKey;
    private final JwtConfig jwtConfig;
    LibraryContext context;		
    
    public JwtTokenVerifier(SecretKey secretKey,
                            JwtConfig jwtConfig,
                            LibraryContext context) {
        this.secretKey = secretKey;
        this.jwtConfig = jwtConfig;
        this.context = context;
       
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        String authorizationHeader = request.getHeader(jwtConfig.getAuthorizationHeader());

        if (Strings.isNullOrEmpty(authorizationHeader) || !authorizationHeader.startsWith(jwtConfig.getTokenPrefix())) {
            filterChain.doFilter(request, response);
            return;
        }

        String token = authorizationHeader.replace(jwtConfig.getTokenPrefix(), "");

        try {

            Jws<Claims> claimsJws = Jwts.parser()
                    .setSigningKey(secretKey)
                    .parseClaimsJws(token);

            Claims body = claimsJws.getBody();

            String username = body.getSubject();
            
            context.setUsername(username);
            System.out.println("MyContext Username: "+username);
            
            List<Map<String, String>> authorities = (List<Map<String, String>>) body.get("authorities");

            Set<SimpleGrantedAuthority> simpleGrantedAuthorities = authorities.stream()
                    .map(m -> new SimpleGrantedAuthority(m.get("authority")))
                    .collect(Collectors.toSet());

            Authentication authentication = new UsernamePasswordAuthenticationToken(
                    username,
                    null,
                    simpleGrantedAuthorities
            );
            
            SecurityContextHolder.getContext().setAuthentication(authentication);
            System.out.println("SecurityContext Username: "+SecurityContextHolder.getContext().getAuthentication().getPrincipal());	
        } catch (JwtException e) {
//            throw new IllegalStateException(String.format("Token %s cannot be trusted", token));
            throw new RestApiException(e.getLocalizedMessage(), HttpStatus.BAD_REQUEST);
        }

        filterChain.doFilter(request, response);
    }
    
}
