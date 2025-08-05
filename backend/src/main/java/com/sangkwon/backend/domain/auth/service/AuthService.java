package com.sangkwon.backend.domain.auth.service;

import com.sangkwon.backend.domain.auth.dto.LoginRequestDTO;
import com.sangkwon.backend.domain.auth.dto.TokenResponseDTO;

public interface AuthService {
	public TokenResponseDTO login(LoginRequestDTO request);
}
