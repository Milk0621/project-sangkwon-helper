package com.sangkwon.backend.domain.auth.service;

import com.sangkwon.backend.domain.auth.dto.LoginRequestDTO;
import com.sangkwon.backend.domain.auth.dto.TokenResponseDTO;
import com.sangkwon.backend.domain.users.dto.UserRegisterRequestDTO;

public interface AuthService {
	public TokenResponseDTO login(LoginRequestDTO request);
	public void register(UserRegisterRequestDTO dto);
	public TokenResponseDTO reissueAccessToken(String refreshToken);
	public void logout(String token);
}
