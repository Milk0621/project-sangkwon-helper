package com.sangkwon.backend.domain.auth.mapper.mybatis;

import java.time.LocalDateTime;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.sangkwon.backend.domain.auth.entity.RefreshToken;

@Mapper
public interface RefreshTokenMapper {
	
	public void saveRefreshToken(
			@Param("email") String email, 
			@Param("token") String token,
			@Param("jti") String jti, 
			@Param("expiresAt") LocalDateTime expiresAt, 
			@Param("now") LocalDateTime now
	);
	
	public RefreshToken findRefreshByJti(@Param("jti") String jti);
	
	public void revokeByJti(@Param("jti") String jti, @Param("replacedByJti") String replacedByJti);

	public void revokeAllByEmail(String email);
	
	public void deleteExpiredRefreshTokens(LocalDateTime now);
	
	public void insertBlacklistedToken(
			@Param("token") String token,
	        @Param("expiresAt") LocalDateTime expiresAt,
	        @Param("createdAt") LocalDateTime createdAt
	);

	public Integer isTokenBlacklisted(String token);
}
