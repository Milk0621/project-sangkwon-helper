package com.sangkwon.backend.domain.users.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sangkwon.backend.domain.users.dao.UsersDAO;
import com.sangkwon.backend.domain.users.model.Users;

@Service
public class UsersServiceImpl implements UsersService {
	
	private final UsersDAO usersDAO;
	
	@Autowired
	public UsersServiceImpl(UsersDAO usersDAO) {
		this.usersDAO = usersDAO;
	}
	
	@Override
	public void register(Users users) {
		usersDAO.insertUser(users);
	}
	
}
