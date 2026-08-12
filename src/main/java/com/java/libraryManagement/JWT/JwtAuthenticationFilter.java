package com.java.libraryManagement.JWT;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.java.libraryManagement.Entity.User;
import com.java.libraryManagement.Repository.UserRepository;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {
	
	@Autowired
	private final UserRepository userRepository;
	
	@Autowired
	private final JwtService jwtService;
	
	
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		
//		Getting the header
		 final String authHeader = request.getHeader("Authorization");
		 final String jwtToken;
		 final String username;
		 
//		 checking if Authorization header is present and starts with "Bearer"
		 if(authHeader==null || !authHeader.startsWith("Bearer ")) {
			 filterChain.doFilter(request, response);
			 return;
		 }
		 
//		 Extract jwtToken from header
		 jwtToken = authHeader.substring(7);
		 username = jwtService.extractUserName(jwtToken);
		 
//		 Checking if we have a user and no authenticated exist yet
		 if(username !=null && SecurityContextHolder.getContext().getAuthentication() == null) {
			 
//			 Extract the user details from database
			User userdetails = userRepository.findByUserName(username)
											 .orElseThrow(() -> new RuntimeException("User not Found"));
			
//			validate the token
			if(jwtService.validToken(jwtToken,userdetails)) {
				
//				creating the authentication with user roles
				List<SimpleGrantedAuthority> authorities = userdetails.getRoles()
						 											  .stream()
						 											  .map(SimpleGrantedAuthority::new)
						 											  .collect(Collectors.toList());
				
																					
				UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
																				userdetails,null,authorities);
				
//				setAuthentication details
				authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
				
//				update Security context with Authentication
				SecurityContextHolder.getContext().setAuthentication(authToken);
				
			}
		 }
		 filterChain.doFilter(request, response);
	}

}
