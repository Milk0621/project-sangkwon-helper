package com.sangkwon.backend.domain.auth.dao.mybatis;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.sangkwon.backend.domain.auth.dao.AuthDAO;
import com.sangkwon.backend.domain.auth.entity.RefreshToken;
import com.sangkwon.backend.domain.auth.mapper.mybatis.RefreshTokenMapper;

@Repository
public class MyBatisAuthDAO implements AuthDAO {
	private final RefreshTokenMapper mapper;

	@Autowired
	public MyBatisAuthDAO(RefreshTokenMapper mapper) {
		this.mapper = mapper;
	}

	@Override
	public void saveRefreshToken(String email, String token, String jti, LocalDateTime expiresAt, LocalDateTime now) {
		mapper.saveRefreshToken(email, token, jti, expiresAt, now);
	}

	@Override
	public RefreshToken findRefreshByJti(String jti) {
		return mapper.findRefreshByJti(jti);
	}

	@Override
	public void revokeByJti(String jti, String replacedByJti) {
		mapper.revokeByJti(jti, replacedByJti);
	}

	@Override
	public void revokeAllByEmail(String email) {
		mapper.revokeAllByEmail(email);
	}

	@Override
	public void deleteExpiredRefreshTokens(LocalDateTime now) {
		mapper.deleteExpiredRefreshTokens(now);
	}
	
	@Override
	public void insertBlacklistedToken(String token, LocalDateTime expiresAt, LocalDateTime now) {
		mapper.insertBlacklistedToken(token, expiresAt, now);
	}

	@Override
	public boolean isTokenBlacklisted(String token) {
		Integer result = mapper.isTokenBlacklisted(token);
	    return result != null && result == 1;
	}
		
}
