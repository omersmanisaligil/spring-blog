package com.React_Spring.SpringBlog.services;

import java.util.List;
import java.util.Optional;

import com.React_Spring.SpringBlog.models.Blog;
import com.React_Spring.SpringBlog.models.ERole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.support.PagedListHolder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.React_Spring.SpringBlog.dao.RoleDAO;
import com.React_Spring.SpringBlog.dao.UserDAO;
import com.React_Spring.SpringBlog.models.User;
import com.React_Spring.SpringBlog.payload.response.MessageResponse;

@Service
public class UserService{
	@Autowired
	private UserDAO userDAO;

    	@Autowired
    	private RoleDAO roleDAO;
	
	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;
	
	public UserService(UserDAO userDAO,BCryptPasswordEncoder bCryptPasswordEncoder) {
		this.userDAO=userDAO;
		this.bCryptPasswordEncoder=bCryptPasswordEncoder;
	}
	
	public List<User> getAllUsers(){
		return (List<User>) userDAO.findAll();
	}
	
	public Optional<User> getOneUserById(Long id) {
		return userDAO.findById(id);
	}
	
	public void deleteUser(Long id) {
		userDAO.deleteById(id);
	}
	

	public ResponseEntity<?> updateUser(User user,Long id) {
		User toUpdate=userDAO.findById(id).get();
		StringBuilder resp=new StringBuilder("");
		
		if(!toUpdate.getUsername().equals(user.getUsername())) {
			resp.append("Sorry, username cannot be changed\n");
			user.setUsername(toUpdate.getUsername());
		}
		
		String newEmail=user.getEmail();
		if(userDAO.existsByEmail(newEmail) && !toUpdate.getEmail().equals(newEmail)) {
			resp.append("This email is already in use\n");
			user.setEmail(toUpdate.getEmail());
		}
		
		toUpdate=user;
		toUpdate.setId(id);
		
		toUpdate.setPassword(bCryptPasswordEncoder.encode(toUpdate.getPassword()));
			
		System.out.println(toUpdate.getId());
		userDAO.save(toUpdate);
		return ResponseEntity.ok(new MessageResponse(resp+"User updated"));
		
	}
	
	public User findUserByEmail(String email) {
		return userDAO.findByEmail(email).get();
	}
	
	public User findUserByUsername(String username) {
		return userDAO.findByUsername(username).get();
	}

	public Page<User> searchByUsername(String username, int page, int size){
		Pageable pageable = PageRequest.of(page, size);
		return userDAO.searchByUsername(username,pageable);
	}

	public Page<User> searchByRoles(String role, int page, int size){
		Pageable pageable = PageRequest.of(page, size);
	    	ERole eRole = ERole.findERole(role);
		return roleDAO.findUsersByRole(eRole,pageable);
	}

	public Page<Blog> getUserBlogs(Long id, int page, int size){
		User user = userDAO.findById(id).get();
		List<Blog> blogs = user.getBlogs();

		PagedListHolder<Blog> blogPage = new PagedListHolder(blogs);
		blogPage.setPage(page);
		blogPage.setPageSize(size);

		Page<Blog> userBlogs = new PageImpl<>(blogPage.getPageList());

		return userBlogs;
	}

}
