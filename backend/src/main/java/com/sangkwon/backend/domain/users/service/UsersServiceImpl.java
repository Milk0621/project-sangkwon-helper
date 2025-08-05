package com.sangkwon.backend.domain.users.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.sangkwon.backend.domain.users.dao.UsersDAO;

@Service
public class UsersServiceImpl implements UsersService {
	
	private final UsersDAO usersDAO;
	
	@Autowired
	public UsersServiceImpl(@Qualifier("myBatisUsersDAO") UsersDAO usersDAO) {
		this.usersDAO = usersDAO;
	}
	
}
