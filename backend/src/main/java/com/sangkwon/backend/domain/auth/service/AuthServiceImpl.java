package com.sangkwon.backend.domain.auth.service;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.sangkwon.backend.domain.auth.dao.AuthDAO;
import com.sangkwon.backend.domain.auth.dto.LoginRequestDTO;
import com.sangkwon.backend.domain.auth.dto.TokenResponseDTO;
import com.sangkwon.backend.domain.auth.entity.RefreshToken;
import com.sangkwon.backend.domain.auth.jwt.JwtTokenProvider;
import com.sangkwon.backend.domain.users.dao.UsersDAO;
import com.sangkwon.backend.domain.users.dto.UserRegisterRequestDTO;
import com.sangkwon.backend.domain.users.entity.Users;

@Service
public class AuthServiceImpl implements AuthService {
	
	private final AuthDAO authDAO;
	private final JwtTokenProvider jwtTokenProvider;
	private final PasswordEncoder passwordEncoder;
	private final UsersDAO usersDAO;
	
	public AuthServiceImpl(
			AuthDAO authDAO, 
			JwtTokenProvider jwtTokenProvider, 
			PasswordEncoder passwordEncoder, 
			@Qualifier("myBatisUsersDAO") UsersDAO usersDAO
	) {
		this.authDAO = authDAO;
		this.jwtTokenProvider = jwtTokenProvider;
		this.passwordEncoder = passwordEncoder;
		this.usersDAO = usersDAO;
	}

	@Override
	public TokenResponseDTO login(LoginRequestDTO request) {
		// 사용자 조회
		Users user = usersDAO.findByEmail(request.getEmail());
		
		if (user == null) {
			throw new RuntimeException("사용자가 존재하지 않습니다.");
		}
		
		// 비밀번호 일치 여부 검증
		if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
			throw new RuntimeException("비밀번호가 일치하지 않습니다.");
		}

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
	
	@Override
	public void register(UserRegisterRequestDTO dto) {
		Users user = new Users();
		user.setEmail(dto.getEmail());
		user.setPassword(passwordEncoder.encode(dto.getPassword())); // BCrypt 암호화 적용
		user.setName(dto.getName());
		user.setNumber(dto.getNumber());
		user.setIndustry(dto.getIndustry());
		user.setCreate_at(LocalDateTime.now());

		usersDAO.insertUser(user);			
	}
	 
}
