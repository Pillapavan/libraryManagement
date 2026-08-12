package com.java.libraryManagement.Service;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.java.libraryManagement.DTOS.LoginRequestDTO;
import com.java.libraryManagement.DTOS.LoginResponseDTO;
import com.java.libraryManagement.DTOS.RegisterRequestDTO;
import com.java.libraryManagement.Entity.User;
import com.java.libraryManagement.JWT.JwtService;
import com.java.libraryManagement.Repository.UserRepository;


@Service
public class AuthenticationService {
	
	private final UserRepository userRepository;
	
	private final PasswordEncoder passwordEncoder;
	
	private final JwtService jwtService;
	
	private final AuthenticationManager authenticationManager;
	
	@Autowired
	public AuthenticationService(UserRepository userRepository,PasswordEncoder passwordEncoder,AuthenticationManager authenticationManager,JwtService jwtService) {
		this.userRepository = userRepository;
		this.passwordEncoder = passwordEncoder;
		this.authenticationManager = authenticationManager;
		this.jwtService = jwtService;
	}


	public User registerNormalUser(RegisterRequestDTO registerRequestDTO) {
		if(userRepository.findByUserName(registerRequestDTO.getUsername()).isPresent()) {
			throw new RuntimeException("user already exists");
		}
		Set<String> roleSet = new HashSet<>();
		roleSet.add("ROLE_USER");
		
		User user = new User();
		user.setUserName(registerRequestDTO.getUsername());
		user.setEmail(registerRequestDTO.getEmail());
		user.setPassWord(passwordEncoder.encode(registerRequestDTO.getPassword()));
		user.setRoles(roleSet);
		
		return userRepository.save(user);
	}


	public User registerAdmin(RegisterRequestDTO registerRequestDTO) {
		if(userRepository.findByUserName(registerRequestDTO.getUsername()).isPresent()) {
			throw new RuntimeException("user already exists");
		}
		Set<String> roleSet = new HashSet<>();
		roleSet.add("ROLE_ADMIN");
		roleSet.add("ROLE_USER");
		
		User user = new User();
		user.setUserName(registerRequestDTO.getUsername());
		user.setEmail(registerRequestDTO.getEmail());
		user.setPassWord(passwordEncoder.encode(registerRequestDTO.getPassword()));
		user.setRoles(roleSet);
		
		return userRepository.save(user);
	}
	
	public LoginResponseDTO loginUser(LoginRequestDTO loginRequestDTO) {
		
		authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(loginRequestDTO.getUsername(), loginRequestDTO.getPassword()));
		
		User user = userRepository.findByUserName(loginRequestDTO.getUsername())
								  .orElseThrow(() -> new RuntimeException("User Not Found"));
		
		String token = jwtService.generateToken(user);
		
		return LoginResponseDTO.builder()
						.token(token)
						.username(user.getUsername())
						.roles(user.getRoles())
						.build();
	}

}
