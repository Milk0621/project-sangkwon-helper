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
		if (user == null) throw new RuntimeException("사용자가 존재하지 않습니다.");
        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new RuntimeException("비밀번호가 일치하지 않습니다.");
        }

		// 토큰 생성
        String email = request.getEmail();
		String accessToken = jwtTokenProvider.generateAccessToken(email);
		String refreshToken = jwtTokenProvider.generateRefreshToken(email);
		String jti = jwtTokenProvider.getJti(refreshToken);
		LocalDateTime exp = jwtTokenProvider.getExpiration(refreshToken);
		LocalDateTime now = LocalDateTime.now();

		// RefreshToken 저장(항상 insert, 덮어쓰기 x)
		authDAO.saveRefreshToken(email, refreshToken, jti, exp, now);

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

	// 쿠키로 받은 refreshToken을 검증/회전하여 새 Access(및 새 Refresh 발급값 반환)
	@Override
	public TokenResponseDTO reissueAccessToken(String refreshToken) {
		// 형식, 서명, 만료 검증
		if (!jwtTokenProvider.validateToken(refreshToken)) {
			throw new RuntimeException("유효하지 않은 Refresh Token입니다.");
		}
		
		// jti로 저장 여부, 상태 확인
		String jti = jwtTokenProvider.getJti(refreshToken);
		RefreshToken stored = authDAO.findRefreshByJti(jti);
		if (stored == null || stored.isRevoked() || stored.getExpiresAt().isBefore(LocalDateTime.now())) {
			throw new RuntimeException("만료되었거나 무효화된 Refresh Token입니다.");
		}
		
		String email = jwtTokenProvider.getUserEmailFromToken(refreshToken);
		
		// 새 토큰 발급 (회전)
		String newAccess = jwtTokenProvider.generateAccessToken(email);
		String newRefresh = jwtTokenProvider.generateRefreshToken(email);
		String newJti = jwtTokenProvider.getJti(newRefresh);
		LocalDateTime newExp = jwtTokenProvider.getExpiration(newRefresh);
		LocalDateTime now  = LocalDateTime.now();
		
		// 기존 jti 무효화 + 새 리프레시 저장
        authDAO.revokeByJti(jti, newJti);
        authDAO.saveRefreshToken(email, newJti, newRefresh, newExp, now);
        
        // 새 엑세스 반환(새 리프레시는 컨트롤러에서 쿠키로 세팅)
        return new TokenResponseDTO(newAccess, newRefresh);
	}

	@Override
	public void logout(String accessToken) {
		if (!jwtTokenProvider.validateToken(accessToken)) {
			throw new RuntimeException("유효하지 않은 Access Token입니다.");
		}
		
		// 엑세스 블랙리스트
		LocalDateTime exp = jwtTokenProvider.getExpiration(accessToken);
        authDAO.insertBlacklistedToken(accessToken, exp, LocalDateTime.now());
	
        // 해당 사용자 리프레시 전부 무효화
		String email = jwtTokenProvider.getUserEmailFromToken(accessToken);
        authDAO.revokeAllByEmail(email);
	}
	 
}
