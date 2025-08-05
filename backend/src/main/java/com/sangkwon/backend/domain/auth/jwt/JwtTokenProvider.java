package com.sangkwon.backend.domain.auth.jwt;

import java.security.Key;
import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
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
	public String generateAccessToken(String email) {
		Date now = new Date();
		Date expiry = new Date(now.getTime() + accessTokenExpiration);
		
		return Jwts.builder()
				.setSubject(email)			// sub: 사용자 식별자
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
	
	// 토큰 유효성 검사
	public boolean validateToken(String token) {
		try {
			Jwts.parserBuilder()		// 서명 위조, 만료시간 검증 => 위반 시 JwtException 발생
				.setSigningKey(key)		// 서명 검증
				.build()
				.parseClaimsJws(token);	// 토큰이 유요한지 검증
			return true;
		} catch (JwtException | IllegalArgumentException e) {
			return false;				// 위조되었거나 만료됨
		}
	}
	
	// 토큰에서 이메일 추출
	public String getUserEmailFromToken(String token) {
		Claims claims = Jwts.parserBuilder()
							.setSigningKey(key)
							.build()
							.parseClaimsJws(token)
							.getBody();	// Payload 부분 추출
		
		return claims.getSubject(); // sub: 사용자 식별자 (email)
	}

}
