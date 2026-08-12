package com.java.libraryManagement.JWT;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.java.libraryManagement.Entity.User;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

@Service
public class JwtService {
	
	@Value("${jwt.secret}")
	private String secretKey;

	@Value("${jwt.expiration}")
	private long jwtExpiration;

    

	public String extractUserName(String jwtToken) {
		return extractClaim(jwtToken,Claims::getSubject);
	}

	private <T> T extractClaim(String jwtToken,Function<Claims, T> claimResolver) {
		final Claims claims = extractAllClaims(jwtToken);
		return claimResolver.apply(claims);
	}
	
	private Claims extractAllClaims(String jwtToken) {
		return Jwts
				.parser()
				.verifyWith(getSignInKey())
				.build()
				.parseSignedClaims(jwtToken)
				.getPayload();
	}
	
	public SecretKey getSignInKey() {
		return Keys.hmacShaKeyFor(secretKey.getBytes());
	}
	
	
	public String generateToken(User user) {
		return generateToken(new HashMap<>(),user);
	}
	

	private String generateToken(Map<String, Object> extraClaims, User user) {
		// TODO Auto-generated method stub
		return Jwts
				.builder()
				.claims(extraClaims)
				.subject(user.getUsername())
				.issuedAt(new Date(System.currentTimeMillis()))
				.expiration(new Date(System.currentTimeMillis() + jwtExpiration))
				.signWith(getSignInKey())
				.compact();
	}

	public boolean validToken(String jwtToken, User userdetails) {
		final String userNameString = extractUserName(jwtToken);
		return (userdetails.getUsername().equals(userNameString) && !isTokenExpired(jwtToken));
	}

	private boolean isTokenExpired(String jwtToken) {
		return extractExpiration(jwtToken).before(new Date());
	}

	private Date extractExpiration(String jwtToken) {
		return extractClaim(jwtToken, Claims::getExpiration);
	}
	
	

}
