package com.React_Spring.SpringBlog.api;

import java.util.List;
import java.util.Optional;


import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.React_Spring.SpringBlog.dao.UserDAO;
import com.React_Spring.SpringBlog.models.User;
import com.React_Spring.SpringBlog.payload.request.LoginRequest;
import com.React_Spring.SpringBlog.payload.request.SignUpRequest;
import com.React_Spring.SpringBlog.security.UserDetailsImpl;
import com.React_Spring.SpringBlog.services.UserService;


@RestController
@RequestMapping("/users")
public class UserController extends Controller{

	@Autowired
	private UserService userService;
	
	public UserController(UserService userService) {
		this.userService=userService;
	}
	
	@Autowired
	private UserDAO userDAO;
	
	@Autowired
	BCryptPasswordEncoder bCryptPasswordEncoder;

	
	@GetMapping("/")
	public List<User> getAll(){
		return userService.getAllUsers();
	}
	@GetMapping(path="{id}")
	public Optional<User> getOneById(@PathVariable("id") int id) {
		return userService.getOneUserById(id);
	}

	@DeleteMapping(path="/delete/{id}")
	public ResponseEntity<?> delete(@PathVariable("id") int id) {
		UserDetailsImpl currentUser=getCurrentUser();

		if(isItCurrentUser(id,currentUser)) {
			userService.deleteUser(id);
			return ok();
		}
		else {
			return unauthorized();
		}
	}
	@PutMapping(path="/edit/{id}")
	public ResponseEntity<?> updateUser(@Validated @NonNull @RequestBody User user,@PathVariable("id") int id) {		
		UserDetailsImpl currentUser=getCurrentUser();
		
		if(isItCurrentUser(id,currentUser)) {
			return userService.updateUser(user,id);
		}
		else {
			return unauthorized();
		}
		
	}
	
	@PostMapping("/login") 
	public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest){
		return userService.authenticateUser(loginRequest);
	}
	
	@PostMapping("/signup")
	public ResponseEntity<?> registerUser(@Valid @RequestBody SignUpRequest signUpRequest){
		return userService.registerUser(signUpRequest);
	}
	
}