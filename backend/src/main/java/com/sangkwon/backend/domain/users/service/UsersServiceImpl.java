package com.sangkwon.backend.domain.users.service;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.sangkwon.backend.domain.users.dao.UsersDAO;
import com.sangkwon.backend.domain.users.dto.UserRegisterRequestDTO;
import com.sangkwon.backend.domain.users.model.Users;

@Service
public class UsersServiceImpl implements UsersService {
	
	private final UsersDAO usersDAO;
	
	@Autowired
	public UsersServiceImpl(@Qualifier("myBatisUsersDAO") UsersDAO usersDAO) {
		this.usersDAO = usersDAO;
	}
	
	@Override
	public void register(UserRegisterRequestDTO dto) {
		Users user = new Users();
		user.setEmail(dto.getEmail());
		user.setPassword(dto.getPassword());
		user.setName(dto.getName());
		user.setNumber(dto.getNumber());
		user.setIndustry(dto.getIndustry());
		user.setCreate_at(LocalDateTime.now());
		
		usersDAO.insertUser(user);
	}
	
}
