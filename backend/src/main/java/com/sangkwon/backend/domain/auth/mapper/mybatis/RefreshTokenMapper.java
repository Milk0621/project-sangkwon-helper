package com.sangkwon.backend.domain.auth.mapper.mybatis;

import java.time.LocalDateTime;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.sangkwon.backend.domain.auth.entity.RefreshToken;

@Mapper
public interface RefreshTokenMapper {
	
	RefreshToken findByEmail(@Param("email") String email);
	
	void insertToken(RefreshToken refreshToken);
	
	void updateToken(RefreshToken regreshToken);

	RefreshToken findByToken(String refreshToken);

	void insertBlacklistedToken(
			@Param("token") String token,
	        @Param("expiresAt") LocalDateTime expiresAt,
	        @Param("createdAt") LocalDateTime createdAt
	);

	Integer isTokenBlacklisted(String token);
}
