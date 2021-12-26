package com.React_Spring.SpringBlog.dao;


import com.React_Spring.SpringBlog.models.Tag;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.React_Spring.SpringBlog.models.Post;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Set;

public interface PostDAO extends JpaRepository<Post,Long> {
    @Override
    Page<Post> findAll(Pageable pageable);

    @Query(value="SELECT p FROM Post p WHERE header LIKE %:header%")
    Page<Post> findByHeaderLike(@Param("header") String header,Pageable pageable);
}
