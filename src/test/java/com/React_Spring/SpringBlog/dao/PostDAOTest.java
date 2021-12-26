package com.React_Spring.SpringBlog.dao;

import com.React_Spring.SpringBlog.models.Blog;
import com.React_Spring.SpringBlog.models.Post;
import com.React_Spring.SpringBlog.models.Tag;
import com.React_Spring.SpringBlog.models.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class PostDAOTest {
    User user = new User("testUser","1234","testUser@email.com");

    List<Post> posts;

    Pageable pageable = PageRequest.of(0, 10);

    Post post1 = new Post(user.getId(),"Header1","Body1");
    Post post2 = new Post(user.getId(),"Header2","Body2");
    Post post3 = new Post(user.getId(),"Header3","Body3");

    Tag tag1 = new Tag("tag1");
    Tag tag2 = new Tag("tag2");
    Tag tag3 = new Tag("tag3");

    @Autowired
    UserDAO userDAO;

    @Autowired
    BlogDAO blogDAO;

    @Autowired
    PostDAO postDAO;

    @Autowired
    TagDAO tagDAO;

    @BeforeEach
    void setUp() {
        userDAO.save(user);

        posts = new ArrayList<>(Arrays.asList(post1,post2,post3));

        postDAO.saveAll(posts);

        post1.getPostTags().add(tag1);
        postDAO.save(post1);
    }

    @AfterEach
    void tearDown(){
        postDAO.deleteAll();
        tagDAO.deleteAll();
        userDAO.deleteAll();
    }

    @Test
    void findAll() {
        Page<Post> postPage = postDAO.findAll(pageable);
        assertEquals(posts, postPage.stream().collect(Collectors.toList()));
    }

    @Test
    void findByHeaderLike() {
        String headerSpecific = "Header1";
        String header = "Header";
        Post postByHeader = (Post)postDAO.findByHeaderLike(headerSpecific,pageable).stream().toArray()[0];
        List<Post> postsByHeaderList = postDAO.findByHeaderLike(header, pageable).stream().collect(Collectors.toList());

        assertAll(()->{
            assertTrue(postByHeader.equals(post1));
            assertTrue(postsByHeaderList.equals(posts));
        });
    }

    @Test
    void addTags(){
        assertEquals(tag1, post1.getPostTags().toArray()[0]);
    }
}