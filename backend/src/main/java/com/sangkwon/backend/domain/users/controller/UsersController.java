package com.sangkwon.backend.domain.users.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sangkwon.backend.domain.users.service.UsersService;

@RestController
@RequestMapping("/api/users")
public class UsersController {
	
	@Autowired
	private UsersService usersSerivce;
	
}
