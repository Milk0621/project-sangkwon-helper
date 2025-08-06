package com.sangkwon.backend.domain.users.dao.mybatis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.sangkwon.backend.domain.users.dao.UsersDAO;
import com.sangkwon.backend.domain.users.entity.Users;
import com.sangkwon.backend.domain.users.mapper.mybatis.UsersMapper;

@Repository("myBatisUsersDAO")
public class MyBatisUsersDAO implements UsersDAO {
	
	private final UsersMapper mapper;

    @Autowired
    public MyBatisUsersDAO(UsersMapper mapper) {
        this.mapper = mapper;
    }

    @Override
    public void insertUser(Users user) {
        mapper.insertUser(user);
    }

	@Override
	public Users findByEmail(String email) {
		return mapper.findByEmail(email);
	}
}
