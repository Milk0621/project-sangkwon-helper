package com.sangkwon.backend.domain.auth.service;

import java.time.LocalDateTime;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.sangkwon.backend.domain.auth.dao.AuthDAO;
import com.sangkwon.backend.domain.auth.dto.LoginRequestDTO;
import com.sangkwon.backend.domain.auth.dto.TokenResponseDTO;
import com.sangkwon.backend.domain.auth.entity.RefreshToken;
import com.sangkwon.backend.domain.auth.jwt.JwtTokenProvider;

@Service
public class AuthServiceImpl implements AuthService {
	
	private final AuthDAO authDAO;
	private final JwtTokenProvider jwtTokenProvider;
	private final PasswordEncoder passwordEncoder;
	
	public AuthServiceImpl(AuthDAO authDAO, JwtTokenProvider jwtTokenProvider, PasswordEncoder passwordEncoder) {
		this.authDAO = authDAO;
		this.jwtTokenProvider = jwtTokenProvider;
		this.passwordEncoder = passwordEncoder;
	}

	@Override
	public TokenResponseDTO login(LoginRequestDTO request) {
		// 사용자 조회 (네 구조에서는 RefreshToken만 관리하므로 이메일만 확인)
		// 실제 구현 시 사용자 정보는 다른 DAO를 사용 (usersDAO 등)

		// 여기선 비밀번호 검증 없이 흐름만 설명
		// → 실제 프로젝트에서는 UserDAO에서 사용자 정보 가져와서 비번 비교

		// 토큰 생성
		String accessToken = jwtTokenProvider.generateAccessToken(request.getEmail());
		String refreshToken = jwtTokenProvider.generateRefreshToken();
		LocalDateTime now = LocalDateTime.now();
		LocalDateTime expiresAt = now.plusDays(7);

		// 기존 RefreshToken 존재 여부 확인
		RefreshToken existing = authDAO.findByEmail(request.getEmail());

		if (existing == null) {
			// 새로 저장
			RefreshToken newToken = new RefreshToken(
				0,
				request.getEmail(),
				refreshToken,
				expiresAt,
				now
			);
			authDAO.insertToken(newToken);
		} else {
			// 기존 토큰 갱신
			existing.setToken(refreshToken);
			existing.setExpiresAt(expiresAt);
			existing.setCreatedAt(now);
			authDAO.updateToken(existing);
		}

		return new TokenResponseDTO(accessToken, refreshToken);
	}
	
}
