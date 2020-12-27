package com.React_Spring.SpringBlog.dao;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.React_Spring.SpringBlog.models.ERole;
import com.React_Spring.SpringBlog.models.Role;

public interface RoleDAO extends JpaRepository<Role, Integer> {
	Optional<Role> findByName(ERole name);
}
