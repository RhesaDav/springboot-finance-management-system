package com.example.demo.security;

import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;

@Component
public class JwtTokenProvider {
	@Value("${jwt.access.secret}")
	private String accessSecret;
	
	@Value("${jwt.refresh.secret}")
	private String refreshSecret;
	
	@Value("${jwt.access.expiration.web}")
	private Long validityForWeb;
	
	@Value("${jwt.access.expiration.mobile}")
	private Long validityForMobile;
	
	@Value("${jwt.refresh.expiration.web}")
	private Long refreshValidityForWeb;
	
	@Value("${jwt.refresh.expiration.mobile}")
	private Long refreshValidityForMobile;
	
	private Key getSigningKey(String secretKey) {
		byte[] keyBytes = Decoders.BASE64.decode(secretKey);
		return Keys.hmacShaKeyFor(keyBytes);
	}
	
	public String createToken(String username, String deviceType, boolean isRefreshToken) {
		Long validity;
		String secretKey;
		
		if (isRefreshToken) {
			secretKey = refreshSecret;
			validity = "web".equalsIgnoreCase(deviceType) ? refreshValidityForWeb : refreshValidityForMobile;
		} else {
			secretKey = accessSecret;
			validity = "web".equalsIgnoreCase(deviceType) ? validityForWeb : validityForMobile;
		}
		return Jwts.builder()
				.setSubject(username)
				.setIssuedAt(new Date())
				.setExpiration(new Date(System.currentTimeMillis() + validity))
				.signWith(getSigningKey(secretKey), SignatureAlgorithm.HS256)
				.compact();
	}
	
	public boolean validateToken(String token, boolean isRefreshToken) {
		try {
			String secretKey = isRefreshToken ? refreshSecret : accessSecret;
			Jwts.parserBuilder().setSigningKey(getSigningKey(secretKey)).build().parseClaimsJws(token);
			return true;
		} catch (JwtException | IllegalArgumentException e) {
			return false;
		}
	}
	
	public String getUsername(String token, boolean isRefreshToken) {
		String secretKey = isRefreshToken ? refreshSecret : accessSecret;
		return Jwts.parserBuilder().setSigningKey(getSigningKey(secretKey)).build().parseClaimsJws(token).getBody().getSubject();
	}
}
