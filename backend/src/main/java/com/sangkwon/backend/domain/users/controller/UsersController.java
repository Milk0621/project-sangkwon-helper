package com.sangkwon.backend.domain.users.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
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
		try {
			usersSerivce.register(requestDTO);
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
}
