package com.sangkwon.backend.domain.users.dao;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;

import com.sangkwon.backend.domain.users.model.Users;

public class MyBatisUsersDAO implements UsersDAO{

	@Autowired
	private UsersDAO mapper;
	
	public MyBatisUsersDAO(SqlSession sqlSession) {
		mapper = sqlSession.getMapper(UsersDAO.class);
	}

	@Override
	public void insertUser(Users user) {
		mapper.insertUser(user);
	}
	
}
