package com.React_Spring.SpringBlog.dao;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.React_Spring.SpringBlog.models.User;

public interface UserDAO extends JpaRepository<User,Integer> {
	Optional<User> findByEmail(String email);
	Optional<User> findByUsername(String username);
	Optional<User> findById(int id);
	
	Boolean existsByUsername(String username);
	Boolean existsByEmail(String email);
}
