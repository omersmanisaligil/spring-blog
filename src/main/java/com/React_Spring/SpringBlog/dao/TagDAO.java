package com.React_Spring.SpringBlog.dao;

import com.React_Spring.SpringBlog.models.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.React_Spring.SpringBlog.models.Tag;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Set;

public interface TagDAO extends JpaRepository<Tag, Integer> {
    @Query(value="SELECT t.posts FROM Tag t WHERE t.tagName=:tagName")
    Page<Post> findPostsWithTag(@Param("tagName") String tagName, Pageable pageable);
}
