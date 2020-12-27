package com.React_Spring.SpringBlog.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.React_Spring.SpringBlog.models.Tag;

public interface TagDAO extends JpaRepository<Tag, Integer> {

}
