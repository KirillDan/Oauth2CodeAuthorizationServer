package com.oauth2.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.oauth2.domain.User;

public interface UserRepository extends JpaRepository<User, Integer> {
	public Optional<User> findByUsername(String username);
	public Boolean existsByUsername(String username);
}
