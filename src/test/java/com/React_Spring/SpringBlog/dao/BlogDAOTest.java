package com.React_Spring.SpringBlog.dao;

import com.React_Spring.SpringBlog.models.Blog;
import com.React_Spring.SpringBlog.models.Post;
import com.React_Spring.SpringBlog.models.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.*;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class BlogDAOTest {
    User user = new User("testUser","1234","testUser@email.com");
    Blog blog = new Blog("testBlog",user);
    Blog blog1 = new Blog("xyztk",user);
    Blog blog2 = new Blog("ktpef",user);
    List<Blog> blogList = new ArrayList<>(Arrays.asList(blog,blog1,blog2));

    @Autowired
    BlogDAO blogDAO;
    @Autowired
    UserDAO userDAO;
    @Autowired
    PostDAO postDAO;

    @BeforeEach
    void setUp() {
        userDAO.save(user);
        blogDAO.saveAll(blogList);
    }

    @AfterEach
    void tearDown() {
        blogDAO.deleteAll();
        userDAO.deleteAll();
    }

    @Test
    void findAll() {
        Pageable pageable = PageRequest.of(0, 10);
        Page<Blog> blogPage = blogDAO.findAll(pageable);
        boolean equals = blogList.equals(blogPage.stream().collect(Collectors.toList()));
        assertTrue(equals);
    }

    @Test
    void findByNameLike() {
        String name = "testB";
        Blog blogByName = (Blog)blogDAO.findByNameLike(name).toArray()[0];

        assertTrue(blogByName.equals(blog));
    }

    @Test
    void addPosts(){
        Post post1 = new Post(user.getId(),"Header1","Body1");
        Post post2 = new Post(user.getId(),"Header2","Body2");
        Post post3 = new Post(user.getId(),"Header3","Body3");

        List<Post> posts = new ArrayList<>(Arrays.asList(post1,post2,post3));
        blog.setPosts(posts);
        blogDAO.save(blog);

        List<Post> postListFromDB = new ArrayList<>(((Blog)blogDAO.findByNameLike(blog.getName()).toArray()[0]).getPosts());
        assertEquals(postListFromDB,posts);
    }

    @Test
    void orphanRemoval(){
        Post post1 = new Post(user.getId(),"Header1","Body1");
        Post post2 = new Post(user.getId(),"Header2","Body2");
        Post post3 = new Post(user.getId(),"Header3","Body3");

        List<Post> posts = new ArrayList<>(Arrays.asList(post1,post2,post3));
        blog.setPosts(posts);
        blogDAO.save(blog);


        blogDAO.delete(blog);
        assertTrue(postDAO.findByHeaderLike(post1.getHeader(),PageRequest.of(0, 10)).isEmpty());
    }
}