package com.React_Spring.SpringBlog.api;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;

import com.React_Spring.SpringBlog.security.UserDetailsImpl;

public class Controller {
	public ResponseEntity<?> ok(){
		return ResponseEntity.status(HttpStatus.OK).build();
	}
	
	
	public ResponseEntity<?> unauthorized(){
		return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
	}
	
	public UserDetailsImpl getCurrentUser() {
		return (UserDetailsImpl)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
	}
	
	public boolean isItCurrentUser(int id,UserDetailsImpl currentUser) {
		return currentUser.getId()==id;
	}
}
