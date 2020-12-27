package com.React_Spring.SpringBlog.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.React_Spring.SpringBlog.models.Blog;

public interface BlogDAO extends JpaRepository<Blog, Integer> {

}
