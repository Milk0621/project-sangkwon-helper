package com.sangkwon.backend.domain.auth.mapper.mybatis;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.sangkwon.backend.domain.auth.entity.RefreshToken;

@Mapper
public interface RefreshTokenMapper {
	
	RefreshToken findByEmail(@Param("email") String email);
	
	void insertToken(RefreshToken refreshToken);
	
	void updateToken(RefreshToken regreshToken);
}
