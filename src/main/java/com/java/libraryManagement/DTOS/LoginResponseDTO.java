package com.java.libraryManagement.DTOS;

import java.util.Set;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class LoginResponseDTO {
	
	private String token;
	private String username;
	private Set<String> roles;
 }
