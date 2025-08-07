package com.sangkwon.backend.domain.users.controller;

import org.springframework.http.ResponseEntity;
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
	public ResponseEntity<Users> getCurrentUser(@RequestHeader("Authorization") String authHeader) {
		String token = authHeader.replace("Bearer ", "");
		String email = jwtTokenProvider.getUserEmailFromToken(token);
		
		Users user = usersService.findByEmail(email);
		return ResponseEntity.ok(user);
	}
}
