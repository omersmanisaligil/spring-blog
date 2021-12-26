package com.React_Spring.SpringBlog.dao;

import com.React_Spring.SpringBlog.models.Blog;
import com.React_Spring.SpringBlog.models.Tag;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.React_Spring.SpringBlog.models.Category;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface CategoryDAO extends JpaRepository<Category, Long>{

    @Query(value="SELECT c.blogs FROM Category c WHERE c.categoryName=:categoryName")
    Page<Blog> findBlogsWithCategory(@Param("categoryName") String categoryName, Pageable pageable);

    Optional<Category> findByCategoryName(String categoryName);

}
