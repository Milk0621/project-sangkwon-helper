package com.sangkwon.backend.domain.auth.controller;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.sangkwon.backend.domain.auth.dto.LoginRequestDTO;
import com.sangkwon.backend.domain.auth.dto.RefreshTokenRequestDTO;
import com.sangkwon.backend.domain.auth.dto.TokenResponseDTO;
import com.sangkwon.backend.domain.auth.service.AuthService;
import com.sangkwon.backend.domain.users.dto.UserRegisterRequestDTO;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
	
	private final AuthService authService;

	public AuthController(AuthService authService) {
		this.authService = authService;
	}
	
	@PostMapping("/login")
	public ResponseEntity<TokenResponseDTO> login(@RequestBody LoginRequestDTO request){
		TokenResponseDTO tokens = authService.login(request);
		return ResponseEntity.ok(tokens);
	}
	
	@PostMapping("/register")
	public ResponseEntity<?> register(@RequestBody UserRegisterRequestDTO requestDTO) {
		try {
			authService.register(requestDTO);
			return ResponseEntity.status(HttpStatus.CREATED).body("회원가입 성공");
		} catch (DataIntegrityViolationException e) {
			String message = e.getRootCause().getMessage(); // MySQL 에러 메시지 추출

	        if (message.contains("email")) {
	            return ResponseEntity.status(HttpStatus.CONFLICT).body("이미 사용 중인 이메일입니다.");
	        } else if (message.contains("name")) {
	            return ResponseEntity.status(HttpStatus.CONFLICT).body("이미 사용 중인 이름입니다.");
	        } else {
	            return ResponseEntity.status(HttpStatus.CONFLICT).body("중복된 항목이 있습니다.");
	        }
		} catch (Exception e) {
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("회원가입 중 오류 발생");
	    }
	}
	
	@PostMapping("/refresh")
	public ResponseEntity<TokenResponseDTO> refresh(@RequestBody RefreshTokenRequestDTO request){
		TokenResponseDTO tokens = authService.reissueAccessToken(request.getRefreshToken());
	    return ResponseEntity.ok(tokens);
	}
	
	@PostMapping("/logout")
	public ResponseEntity<?> logout(@RequestHeader("Authorization") String authHeader) {
		String token = authHeader.replace("Bearer ", "");
		
		authService.logout(token); // 서비스로 위임
		return ResponseEntity.ok("로그아웃 완료");
	}
	
}
