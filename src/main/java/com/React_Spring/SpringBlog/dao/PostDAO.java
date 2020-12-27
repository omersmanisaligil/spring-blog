package com.React_Spring.SpringBlog.dao;


import org.springframework.data.jpa.repository.JpaRepository;

import com.React_Spring.SpringBlog.models.Post;

public interface PostDAO extends JpaRepository<Post,Integer> {

}
