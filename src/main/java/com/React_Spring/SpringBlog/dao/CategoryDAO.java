package com.React_Spring.SpringBlog.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.React_Spring.SpringBlog.models.Category;

public interface CategoryDAO extends JpaRepository<Category, Integer>{

}
