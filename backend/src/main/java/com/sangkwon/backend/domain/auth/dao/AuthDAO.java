package com.sangkwon.backend.domain.auth.dao;

import java.time.LocalDateTime;

import com.sangkwon.backend.domain.auth.entity.RefreshToken;

public interface AuthDAO {
    // Refresh Token
    void saveRefreshToken(String email, String token, String jti, LocalDateTime expiresAt, LocalDateTime now);
    RefreshToken findRefreshByJti(String jti);
    void revokeByJti(String jti, String replacedByJti);     // 회전 시 사용
    void revokeAllByEmail(String email);                    // 로그아웃-올킬 등
    void deleteExpiredRefreshTokens(LocalDateTime now);     // 청소용(선택)

	// Access Token Blacklist
	void insertBlacklistedToken(String token, LocalDateTime expiresAt, LocalDateTime now);
	boolean isTokenBlacklisted(String token);
}
