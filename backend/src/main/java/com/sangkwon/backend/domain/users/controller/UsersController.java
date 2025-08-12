package com.sangkwon.backend.domain.users.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sangkwon.backend.domain.auth.jwt.JwtTokenProvider;
import com.sangkwon.backend.domain.users.entity.Users;
import com.sangkwon.backend.domain.users.service.UsersService;

@RestController
@RequestMapping("/api/users")
public class UsersController {
	
	private UsersService usersService;
	private final JwtTokenProvider jwtTokenProvider;
	
	public UsersController(UsersService usersService, JwtTokenProvider jwtTokenProvider) {
		this.usersService = usersService;
		this.jwtTokenProvider = jwtTokenProvider;
	}
	
	@GetMapping("/me")
	public ResponseEntity<?> getCurrentUser(Authentication authentication) {
		if (authentication == null || authentication.getPrincipal() == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("인증되지 않았습니다.");
        }
		
		String email = authentication.getPrincipal().toString();
		Users user = usersService.findByEmail(email);
		if (user == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("사용자를 찾을 수 없습니다.");
        }
		return ResponseEntity.ok(user);
	}
}
