package com.sangkwon.backend.domain.users.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.sangkwon.backend.domain.users.dao.UsersDAO;
import com.sangkwon.backend.domain.users.entity.Users;

@Service
public class UsersServiceImpl implements UsersService {
	
	private final UsersDAO usersDAO;
	
	@Autowired
	public UsersServiceImpl(@Qualifier("myBatisUsersDAO") UsersDAO usersDAO) {
		this.usersDAO = usersDAO;
	}

	@Override
	public Users findByEmail(String email) {
		return usersDAO.findByEmail(email);
	}
	
}
