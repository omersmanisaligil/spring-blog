package com.React_Spring.SpringBlog.api;

import com.React_Spring.SpringBlog.models.User;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;

public class Controller {
	public ResponseEntity<?> ok(){
		return ResponseEntity.status(HttpStatus.OK).build();
	}
	
	
	public ResponseEntity<?> unauthorized(){
		return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
	}
	
	public User getCurrentUser() {
		return (User)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
	}
	
	public boolean isItCurrentUser(Long id, User currentUser) {
		return currentUser.getId()==id;
	}
}
