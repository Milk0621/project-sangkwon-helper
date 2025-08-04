package com.sangkwon.backend.domain.users.mapper.jpa;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sangkwon.backend.domain.users.model.Users;

public interface UsersJpaRepository extends JpaRepository<Users, Integer> {
	
}
