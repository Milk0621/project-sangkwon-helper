package com.sangkwon.backend.domain.auth.entity;

import java.time.LocalDateTime;

public class RefreshToken {
	private Long id;
	private String email;
	private String token;
	private LocalDateTime expiresAt;
	private LocalDateTime createdAt;
	
	public RefreshToken() {}
	
	public RefreshToken(Long id, String email, String token, LocalDateTime expiresAt, LocalDateTime createdAt) {
		this.id = id;
		this.email = email;
		this.token = token;
		this.expiresAt = expiresAt;
		this.createdAt = createdAt;
	}
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	public LocalDateTime getExpiresAt() {
		return expiresAt;
	}
	public void setExpiresAt(LocalDateTime expiresAt) {
		this.expiresAt = expiresAt;
	}
	public LocalDateTime getCreatedAt() {
		return createdAt;
	}
	public void setCreatedAt(LocalDateTime createdAt) {
		this.createdAt = createdAt;
	}
	
}
