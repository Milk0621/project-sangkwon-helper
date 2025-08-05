package com.sangkwon.backend.domain.users.dao.jpa;

import org.springframework.stereotype.Repository;

import com.sangkwon.backend.domain.users.dao.UsersDAO;
import com.sangkwon.backend.domain.users.entity.Users;
import com.sangkwon.backend.domain.users.mapper.jpa.UsersJpaRepository;

@Repository("jpaUsersDAO")
public class JpaUsersDAO implements UsersDAO{
	private final UsersJpaRepository usersRepository;

    public JpaUsersDAO(UsersJpaRepository usersRepository) {
        this.usersRepository = usersRepository;
    }

    @Override
    public void insertUser(Users user) {
        usersRepository.save(user);
    }
}
