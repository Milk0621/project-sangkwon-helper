package com.sangkwon.backend.domain.users.mapper.mybatis;

import org.apache.ibatis.annotations.Mapper;

import com.sangkwon.backend.domain.users.model.Users;

@Mapper
public interface UsersMapper {
	void insertUser(Users user);
}
