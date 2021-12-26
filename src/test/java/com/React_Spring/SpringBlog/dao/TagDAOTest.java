package com.React_Spring.SpringBlog.dao;

import com.React_Spring.SpringBlog.models.Post;
import com.React_Spring.SpringBlog.models.Tag;
import com.React_Spring.SpringBlog.models.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class TagDAOTest {
    User user = new User("testUser","1234","testUser@email.com");

    Pageable pageable = PageRequest.of(0, 10);

    Tag tag1 = new Tag("tag1");
    Tag tag2 = new Tag("tag2");
    Tag tag3 = new Tag("tag3");

    List<Tag> tags = new ArrayList<>(Arrays.asList(tag1,tag2,tag3));

    List<Post> posts;

    Post post1 = new Post(user.getId(),"Header1","Body1");
    Post post2 = new Post(user.getId(),"Header2","Body2");
    Post post3 = new Post(user.getId(),"Header3","Body3");

    @Autowired
    TagDAO tagDAO;

    @Autowired
    PostDAO postDAO;

    @Autowired
    UserDAO userDAO;

    @BeforeEach
    void setUp() {
        userDAO.save(user);

        posts = new ArrayList<>(Arrays.asList(post1,post2,post3));

        postDAO.saveAll(posts);
        tagDAO.saveAll(tags);
    }

    @AfterEach
    void tearDown() {
        tagDAO.deleteAll();
        postDAO.deleteAll();
        userDAO.deleteAll();
    }

    @Test
    void testFindPostsWithTag() {
        post1.getPostTags().add(tag1);
        post2.getPostTags().add(tag1);

        List<Post> postsWithTag = new ArrayList<>(Arrays.asList(post1,post2));

        postDAO.saveAll(postsWithTag);

        List<Post> postsFromDB =  new ArrayList(tagDAO.findPostsWithTag(tag1.getTagName(),pageable).stream()
                                                                                    .collect(Collectors.toList()));

        assertEquals(postsWithTag,postsFromDB);
    }

    @Test
    void findByTagName() {
        assertEquals(tag1, tagDAO.findByTagName("tag1").orElse(null));
    }
}