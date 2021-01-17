package com.React_Spring.SpringBlog.dao;

import java.util.Optional;

import com.React_Spring.SpringBlog.models.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.React_Spring.SpringBlog.models.ERole;
import com.React_Spring.SpringBlog.models.Role;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface RoleDAO extends JpaRepository<Role, Integer> {
	Optional<Role> findByName(ERole name);

	@Query(value="SELECT r.users FROM Role r WHERE r.name=:role")
	Page<User> findUsersByRole(@Param(value="role") String role, Pageable pageable);
}
