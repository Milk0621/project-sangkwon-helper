package com.sangkwon.backend.jwt;

import java.security.Key;
import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtTokenProvider {
	private final Key key;
	private final long accessTokenExpiration;
	private final long refreshTokenExpiration;
	
	public JwtTokenProvider(
			@Value("${jwt.secret}") String secretKey, 
			@Value("${jwt.access-token-expiration}") long accessTokenExpiration, 
			@Value("${jwt.refresh-token-expiration}") long refreshTokenExpiration) {
		super();
		this.key = Keys.hmacShaKeyFor(secretKey.getBytes());
		this.accessTokenExpiration = accessTokenExpiration;
		this.refreshTokenExpiration = refreshTokenExpiration;
	}
	
	// Access Token 생성
	public String generateAccessToken(String email, String role) {
		Date now = new Date();
		Date expiry = new Date(now.getTime() + accessTokenExpiration);
		
		return Jwts.builder()
				.setSubject(email)			// sub: 사용자 식별자
				.claim("role", role)		// custom claim: 사용자 권한
				.setIssuedAt(now)			// iat: 발급 시간
				.setExpiration(expiry)		// exp: 만료 시간
				.signWith(key, SignatureAlgorithm.HS256) // 서명 알고리즘과 키 설정
				.compact();					// JWT 문자열로 변환 (최종 결과)
	}
	
	// Refresh Token 생성
	public String generateRefreshToken() {
		Date now = new Date();
		Date expiry = new Date(now.getTime() + refreshTokenExpiration);
		
		return Jwts.builder()
				.setIssuedAt(now)
				.setExpiration(expiry)
				.signWith(key, SignatureAlgorithm.HS256)
				.compact();
	}

}
