package com.React_Spring.SpringBlog.dao;

import com.React_Spring.SpringBlog.models.ERole;
import com.React_Spring.SpringBlog.models.Role;
import com.React_Spring.SpringBlog.models.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class RoleDAOTest {

    @Autowired
    RoleDAO roleDAO;

    @Autowired
    UserDAO userDAO;

    User user = new User("testUser","1234","testUser@email.com");

    Role roleAdmin = new Role(ERole.ROLE_ADMIN);
    Role roleAuthor = new Role(ERole.ROLE_AUTHOR);
    Role roleUser = new Role(ERole.ROLE_USER);

    Pageable pageable = PageRequest.of(0, 10);

    @BeforeEach
    void setUp() {
        roleDAO.save(roleAdmin);
        roleDAO.save(roleAuthor);
        roleDAO.save(roleUser);

        user.setUserRoles(new HashSet<>(Arrays.asList(roleAuthor,roleUser)));

        userDAO.save(user);
    }

    @AfterEach
    void tearDown() {
        roleDAO.deleteAll();
        userDAO.deleteAll();
    }

    @Test
    void testFindByName() {
        Role res = roleDAO.findByName(ERole.ROLE_ADMIN).orElse(null);

        assertEquals(res, roleAdmin);
    }

    @Test
    void testFindUsersByRole() {
        List<User> authors = roleDAO
                        .findUsersByRole(ERole.ROLE_AUTHOR,pageable).stream().collect(Collectors.toList());

        List<User> admins = roleDAO
                        .findUsersByRole(ERole.ROLE_ADMIN,pageable).stream().collect(Collectors.toList());

        assertAll(
            ()->{
                assertTrue(authors.contains(user));
                assertFalse(admins.contains(user));
            }
        );
    }
}