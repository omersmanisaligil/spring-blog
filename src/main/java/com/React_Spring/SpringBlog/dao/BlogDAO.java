package com.React_Spring.SpringBlog.dao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.React_Spring.SpringBlog.models.Blog;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Set;

public interface BlogDAO extends JpaRepository<Blog, Integer> {
    @Override
    Page<Blog> findAll(Pageable pageable);

    @Query("SELECT b FROM Blog b WHERE b.name LIKE %:name%")
    Set<Blog> findByNameLike(@Param("name") String name);

}
