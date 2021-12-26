package com.React_Spring.SpringBlog.dao;

import java.util.Optional;
import java.util.Set;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.React_Spring.SpringBlog.models.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface UserDAO extends JpaRepository<User,Long> {
	Optional<User> findByEmail(String email);
	Optional<User> findByUsername(String username);

	@Query(value="SELECT u FROM User u WHERE u.username LIKE %:username%")
	Page<User> searchByUsername(@Param("username") String username,Pageable pageable);

	Boolean existsByUsername(String username);
	Boolean existsByEmail(String email);
}
