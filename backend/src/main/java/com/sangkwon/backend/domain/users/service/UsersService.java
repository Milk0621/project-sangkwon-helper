package com.sangkwon.backend.domain.users.service;

import com.sangkwon.backend.domain.users.dto.UserRegisterRequestDTO;

public interface UsersService {
	void register(UserRegisterRequestDTO dto);
}
