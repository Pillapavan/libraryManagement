package com.java.libraryManagement.DTOS;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
public class RegisterRequestDTO {
	
	private String username;
	
	private String email;
	
	private String password;

}
