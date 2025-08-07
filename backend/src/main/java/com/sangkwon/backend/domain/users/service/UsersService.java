package com.sangkwon.backend.domain.users.service;

import com.sangkwon.backend.domain.users.entity.Users;

public interface UsersService {

	Users findByEmail(String email);
	
}
