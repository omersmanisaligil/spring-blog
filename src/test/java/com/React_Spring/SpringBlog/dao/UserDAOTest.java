package com.React_Spring.SpringBlog.dao;

import com.React_Spring.SpringBlog.models.Blog;
import com.React_Spring.SpringBlog.models.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.Set;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class UserDAOTest {

    User user = new User("testUser","1234","testUser@email.com");

    @Autowired
    private UserDAO userDAO;

    @Autowired
    private BlogDAO blogDAO;

    @BeforeEach
    void setUp() {
        userDAO.save(user);
    }

    @AfterEach
    void tearDown(){
        userDAO.deleteAll();
    }

    @Test
    void itShouldFindByEmail() {
        String email = "testUser@email.com";
        User emailUser = userDAO.findByEmail(email).orElse(null);

        assertEquals(emailUser, user);
    }

    @Test
    void itShouldNotFindByEmail() {
        String email = "theWrongEmail@email.com";
        User emailUser = userDAO.findByEmail(email).orElse(null);

        assertNotEquals(emailUser, user);
    }

    @Test
    void itShouldFindByUsername() {
        String username = "testUser";
        User unameUser = userDAO.findByUsername(username).orElse(null);

        assertEquals(unameUser,user);
    }

    @Test
    void itShouldNotFindByUsername(){
        String username = "wrongUsername";
        User uNameUser = userDAO.findByUsername(username).orElse(null);

        assertNotEquals(uNameUser,user);
    }

    @Test
    void itShouldSearchByUsername() {
        String searchKeyPass = "test";
        String searchKeyFail = "xx";

        //def values from controller
        Pageable pageable = PageRequest.of(0, 10);

        Page<User> searchUserPagePass = userDAO.searchByUsername(searchKeyPass,pageable);
        Page<User> searchUserPageFail = userDAO.searchByUsername(searchKeyFail,pageable);

        User searchUserPass = searchUserPagePass.get().collect(Collectors.toList()).get(0);
        User searchUserFail = null;
        try {
            searchUserFail = searchUserPageFail.get().collect(Collectors.toList()).get(0);
        }catch(IndexOutOfBoundsException e){
        }

        User finalSearchUserFail = searchUserFail;

        assertAll("user search",
                  ()->{
                    assertEquals(searchUserPass,user);
                    assertNotEquals(finalSearchUserFail,user);
                  }
                  );
    }

    @Test
    void itShouldCheckExistsByUsername() {
        String usernamePass = "testUser";
        String usernameFail = "wrongUsername";

        boolean pass = userDAO.existsByUsername(usernamePass);
        boolean fail = userDAO.existsByUsername(usernameFail);

        assertAll("bothConditions",
                  ()->{
                        assertTrue(pass);
                        assertFalse(fail);
                  });
    }

    @Test
    void itShouldCheckExistsByEmail() {
        String emailPass = "testUser@email.com";
        String emailFail = "wrongEil@email.com";

        boolean pass = userDAO.existsByEmail(emailPass);
        boolean fail = userDAO.existsByEmail(emailFail);

        assertAll("bothConditions",
                 ()->{
                    assertTrue(pass);
                    assertFalse(fail);
                 }
        );
    }

    @Test
    void itShouldAssignBlog(){
        Blog blog = new Blog("testBlog",user);
        user.getBlogs().add(blog);
        userDAO.save(user);
        Blog userBlog = user.getBlogs().get(0);
        assertEquals(userBlog, blog);
    }

    @Test
    void itShouldDeleteOrphan(){
        Blog blog = new Blog("testBlog",user);
        user.getBlogs().add(blog);

        userDAO.save(user);

        userDAO.deleteById(user.getId());

        Set<Blog> blogByName = blogDAO.findByNameLike(blog.getName());
        assertTrue(blogByName.isEmpty() || blogByName == null);
    }
}