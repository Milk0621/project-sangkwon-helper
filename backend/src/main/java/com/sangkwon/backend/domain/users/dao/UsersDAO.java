package com.sangkwon.backend.domain.users.dao;

import com.sangkwon.backend.domain.users.entity.Users;

public interface UsersDAO {
	public void insertUser(Users user);
	public Users findByEmail(String email);
}
