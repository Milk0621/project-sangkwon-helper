package com.sangkwon.backend.domain.auth.dao;

import java.time.LocalDateTime;

import com.sangkwon.backend.domain.auth.entity.RefreshToken;

public interface AuthDAO {
	RefreshToken findByEmail(String email);

    void insertToken(RefreshToken refreshToken);

    void updateToken(RefreshToken refreshToken);

	RefreshToken findByToken(String refreshToken);

	void insertBlacklistedToken(String token, LocalDateTime expiresAt, LocalDateTime now);

	boolean isTokenBlacklisted(String token);
}
