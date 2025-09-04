package com.sangkwon.backend.domain.auth.controller;

import java.time.Duration;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sangkwon.backend.domain.auth.dto.LoginRequestDTO;
import com.sangkwon.backend.domain.auth.dto.RefreshTokenRequestDTO;
import com.sangkwon.backend.domain.auth.dto.TokenResponseDTO;
import com.sangkwon.backend.domain.auth.service.AuthService;
import com.sangkwon.backend.domain.users.dto.UserRegisterRequestDTO;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Cookie;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
	
	private final AuthService authService;
	
    // 배포환경에서 true 로 (SameSite=None은 Secure 필수)
    private static final boolean COOKIE_SECURE = false; // TODO: prod=true
    private static final String REFRESH_COOKIE_NAME = "refreshToken";

	public AuthController(AuthService authService) {
		this.authService = authService;
	}
	
	@PostMapping("/login")
	public ResponseEntity<TokenResponseDTO> login(@RequestBody LoginRequestDTO request, HttpServletResponse response){
		// 토큰 발급
		TokenResponseDTO tokens = authService.login(request);
		String accessToken = tokens.getAccessToken();
	    String refreshToken = tokens.getRefreshToken();
	    
	    // Refresh Token을 쿠키로 내려줌
	    ResponseCookie refreshCookie = ResponseCookie.from(REFRESH_COOKIE_NAME, tokens.getRefreshToken())
	    		.httpOnly(true)
	    		.secure(COOKIE_SECURE) // 배포 시 true로 변경
	    		.sameSite("None") // 프론트와 도메인 다르면 반드시 필요
	    		.path("/")
	    		.maxAge(Duration.ofDays(7)) // 7일
	    		.build();
	    
	    // 쿠키를 응답 헤더에 포함
	    response.setHeader("Set-Cookie", refreshCookie.toString());
	    
	    // Access Token만 응답 본문에 포함
		return ResponseEntity.ok(new TokenResponseDTO(tokens.getAccessToken()));
	}
	
	@PostMapping("/register")
	public ResponseEntity<?> register(@RequestBody UserRegisterRequestDTO requestDTO) {
		try {
			authService.register(requestDTO);
			return ResponseEntity.status(HttpStatus.CREATED).body("회원가입 성공");
		} catch (DataIntegrityViolationException e) {
			String message = e.getRootCause().getMessage(); // MySQL 에러 메시지 추출

	        if (message != null && message.contains("email")) {
	            return ResponseEntity.status(HttpStatus.CONFLICT).body("이미 사용 중인 이메일입니다.");
	        } else if (message != null && message.contains("name")) {
	            return ResponseEntity.status(HttpStatus.CONFLICT).body("이미 사용 중인 이름입니다.");
	        } else {
	            return ResponseEntity.status(HttpStatus.CONFLICT).body("중복된 항목이 있습니다.");
	        }
		} catch (Exception e) {
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("회원가입 중 오류 발생");
	    }
	}
	
	// 리프레시: 쿠키에서 refreshToken 꺼내 회전 → 새 액세스 반환 + 새 리프레시 쿠키 재설정
	@PostMapping("/refresh")
	public ResponseEntity<TokenResponseDTO> refresh(HttpServletRequest request, HttpServletResponse response){
		String refreshToken = extractRefreshFromCookie(request, REFRESH_COOKIE_NAME);
		if (refreshToken == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
		
		TokenResponseDTO reissued = authService.reissueAccessToken(refreshToken);

        // 새 리프레시를 다시 쿠키로
        ResponseCookie refreshCookie = ResponseCookie.from(REFRESH_COOKIE_NAME, reissued.getRefreshToken())
                .httpOnly(true)
                .secure(COOKIE_SECURE)
                .sameSite("None")
                .path("/")
                .maxAge(Duration.ofDays(7)) // JwtTokenProvider 만료와 맞추는 게 이상적
                .build();
        response.addHeader("Set-Cookie", refreshCookie.toString());

        // 본문엔 새 Access만
        return ResponseEntity.ok(new TokenResponseDTO(reissued.getAccessToken()));
	}
	
	@PostMapping("/logout")
    public ResponseEntity<?> logout(@RequestHeader("Authorization") String authHeader,
                                    HttpServletResponse response) {
        String token = authHeader.replace("Bearer ", "");
        authService.logout(token);

        // 선택: refresh 쿠키 즉시 만료
        ResponseCookie expired = ResponseCookie.from(REFRESH_COOKIE_NAME, "")
                .httpOnly(true)
                .secure(COOKIE_SECURE)
                .sameSite("None")
                .path("/")
                .maxAge(0)
                .build();
        response.addHeader("Set-Cookie", expired.toString());

        return ResponseEntity.ok("로그아웃 완료");
    }

    private String extractRefreshFromCookie(HttpServletRequest request, String name) {
		Cookie[] cookies = request.getCookies();
		if (cookies == null) return null;
		for (Cookie c : cookies) {
		    if (name.equals(c.getName())) return c.getValue();
		}
		return null;
	}
}
