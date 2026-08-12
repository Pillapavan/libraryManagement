package com.java.libraryManagement.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.java.libraryManagement.DTOS.LoginRequestDTO;
import com.java.libraryManagement.DTOS.LoginResponseDTO;
import com.java.libraryManagement.DTOS.RegisterRequestDTO;
import com.java.libraryManagement.Entity.User;
import com.java.libraryManagement.Service.AuthenticationService;

@RestController
@RequestMapping("/auth")
public class AuthController {
	
	private final AuthenticationService authenticationService;
	
	@Autowired
	public AuthController(AuthenticationService authenticationService) {
		this.authenticationService =  authenticationService;
	}
	
	@PostMapping("/registernormaluser")
	public ResponseEntity<User> RegisterNormalUser(@RequestBody RegisterRequestDTO registerRequestDTO){
		return ResponseEntity.ok(authenticationService.registerNormalUser(registerRequestDTO));
	}
	
	@PostMapping("/loginuser")
	public ResponseEntity<LoginResponseDTO> loginUser(@RequestBody LoginRequestDTO loginRequestDTO){
		return ResponseEntity.ok(authenticationService.loginUser(loginRequestDTO));
	}
	

}
