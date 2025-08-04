package com.sangkwon.backend.domain.users.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sangkwon.backend.domain.users.dto.UserRegisterRequestDTO;
import com.sangkwon.backend.domain.users.service.UsersService;

@RestController
@RequestMapping("/api/users")
public class UsersController {
	
	@Autowired
	private UsersService usersSerivce;
	
	@PostMapping("/auth/register")
	public ResponseEntity<?> register(@RequestBody UserRegisterRequestDTO requestDTO) {
		usersSerivce.register(requestDTO);
		return ResponseEntity.status(HttpStatus.CREATED).body("회원가입 성공");
	}
}
