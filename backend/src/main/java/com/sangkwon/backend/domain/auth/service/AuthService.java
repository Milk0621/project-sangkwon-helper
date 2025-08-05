package com.sangkwon.backend.domain.auth.service;

import com.sangkwon.backend.domain.auth.dto.LoginRequestDTO;
import com.sangkwon.backend.domain.auth.dto.TokenResponseDTO;
import com.sangkwon.backend.domain.users.dto.UserRegisterRequestDTO;

public interface AuthService {
	public TokenResponseDTO login(LoginRequestDTO request);
	void register(UserRegisterRequestDTO dto);
}
