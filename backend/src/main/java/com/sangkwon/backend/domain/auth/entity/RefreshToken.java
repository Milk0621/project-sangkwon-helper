package com.sangkwon.backend.domain.auth.entity;

import java.time.LocalDateTime;

public class RefreshToken {
    private Long id;
    private String email;
    private String token;          // (권장: 해시 저장)
    private String jti;
    private boolean revoked;
    private String replacedByJti;
    private LocalDateTime expiresAt;
    private LocalDateTime createdAt;
	
	public RefreshToken() {}

	public RefreshToken(Long id, String email, String token, String jti, boolean revoked, String replacedByJti,
			LocalDateTime expiresAt, LocalDateTime createdAt) {
		super();
		this.id = id;
		this.email = email;
		this.token = token;
		this.jti = jti;
		this.revoked = revoked;
		this.replacedByJti = replacedByJti;
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

	public String getJti() {
		return jti;
	}

	public void setJti(String jti) {
		this.jti = jti;
	}

	public boolean isRevoked() {
		return revoked;
	}

	public void setRevoked(boolean revoked) {
		this.revoked = revoked;
	}

	public String getReplacedByJti() {
		return replacedByJti;
	}

	public void setReplacedByJti(String replacedByJti) {
		this.replacedByJti = replacedByJti;
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
