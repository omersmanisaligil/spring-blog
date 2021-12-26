package com.React_Spring.SpringBlog.dao;

import com.React_Spring.SpringBlog.models.Blog;
import com.React_Spring.SpringBlog.models.Category;
import com.React_Spring.SpringBlog.models.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class CategoryDAOTest {

    @Autowired
    CategoryDAO categoryDAO;

    @Autowired
    BlogDAO blogDAO;

    @Autowired
    UserDAO userDAO;

    Category cat1 = new Category("category1");

    User user = new User("testUser","1234","testUser@email.com");

    Blog blog = new Blog("testBlog",user);

    Pageable pageable = PageRequest.of(0, 10);

    @BeforeEach
    void setUp() {
	categoryDAO.save(cat1);
	userDAO.save(user);

	blog.getBlogCategories().add(cat1);

	blogDAO.save(blog);
    }

    @AfterEach
    void tearDown() {
        blogDAO.deleteAll();
        userDAO.deleteAll();
        categoryDAO.deleteAll();
    }

    @Test
    void findBlogsWithCategory() {
	List<Blog> blogs = categoryDAO.findBlogsWithCategory(cat1.getCategoryName(),pageable)
							.stream().collect(Collectors.toList());
	assertTrue(blogs.contains(blog));
    }

    @Test
    void findByCategoryName() {
    	Category category = categoryDAO.findByCategoryName(cat1.getCategoryName()).orElse(null);
    	assertEquals(category,cat1);
    }
}