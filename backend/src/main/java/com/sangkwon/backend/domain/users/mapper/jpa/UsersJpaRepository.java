package com.sangkwon.backend.domain.users.mapper.jpa;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sangkwon.backend.domain.users.entity.Users;

public interface UsersJpaRepository extends JpaRepository<Users, Integer> {
	Users findByEmail(String email);
}
