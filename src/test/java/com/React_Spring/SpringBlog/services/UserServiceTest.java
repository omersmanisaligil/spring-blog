package com.React_Spring.SpringBlog.services;

import com.React_Spring.SpringBlog.dao.UserDAO;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserDAO userDAO;
    private UserService userTestService;
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @BeforeEach
    void setUp(){
        userTestService = new UserService(userDAO, bCryptPasswordEncoder);
    }

    @Test
    void getAllUsers() {
        userTestService.getAllUsers();

        Mockito.verify(userDAO).findAll();
    }

    @Test
    void getOneUserById() {
    }

    @Test
    void deleteUser() {
    }

    @Test
    void updateUser() {
    }

    @Test
    void findUserByEmail() {
    }

    @Test
    void findUserByUsername() {
    }

    @Test
    void searchByUsername() {
    }

    @Test
    void searchByRoles() {
    }

    @Test
    void getUserBlogs() {
    }
}