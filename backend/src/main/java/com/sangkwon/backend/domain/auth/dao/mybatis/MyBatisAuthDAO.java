package com.sangkwon.backend.domain.auth.dao.mybatis;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.sangkwon.backend.domain.auth.dao.AuthDAO;
import com.sangkwon.backend.domain.auth.entity.RefreshToken;
import com.sangkwon.backend.domain.auth.mapper.mybatis.RefreshTokenMapper;

@Repository
public class MyBatisAuthDAO implements AuthDAO {
	private final RefreshTokenMapper refreshTokenMapper;

	@Autowired
	public MyBatisAuthDAO(RefreshTokenMapper refreshTokenMapper) {
		this.refreshTokenMapper = refreshTokenMapper;
	}

	@Override
	public RefreshToken findByEmail(String email) {
		return refreshTokenMapper.findByEmail(email);
	}

	@Override
	public void insertToken(RefreshToken refreshToken) {
		refreshTokenMapper.insertToken(refreshToken);
	}

	@Override
	public void updateToken(RefreshToken refreshToken) {
		refreshTokenMapper.updateToken(refreshToken);
	}

	@Override
	public RefreshToken findByToken(String refreshToken) {
		return refreshTokenMapper.findByToken(refreshToken);
	}

	@Override
	public void insertBlacklistedToken(String token, LocalDateTime expiresAt, LocalDateTime now) {
		refreshTokenMapper.insertBlacklistedToken(token, expiresAt, now);
	}

	@Override
	public boolean isTokenBlacklisted(String token) {
		Integer result = refreshTokenMapper.isTokenBlacklisted(token);
	    return result != null && result == 1;
	}
		
}
