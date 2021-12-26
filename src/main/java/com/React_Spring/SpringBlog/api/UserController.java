package com.React_Spring.SpringBlog.api;

import java.util.List;
import java.util.Optional;

import com.React_Spring.SpringBlog.models.Blog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import com.React_Spring.SpringBlog.dao.UserDAO;
import com.React_Spring.SpringBlog.models.User;
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
	public Optional<User> getOneById(@PathVariable("id") Long id) {
		return userService.getOneUserById(id);
	}

	@GetMapping(path="{id}/blogs")
	public ResponseEntity<Page<Blog>> getUserBlogs(@PathVariable("id") Long id,
						       @RequestParam(defaultValue = "0") int page,
						       @RequestParam(defaultValue = "5") int size){

		Page<Blog> userBlogs = userService.getUserBlogs(id, page, size);

		return ResponseEntity.ok().body(userBlogs);
	}

	@DeleteMapping(path="/delete/{id}")
	public ResponseEntity<?> delete(@PathVariable("id") Long id) {
		User currentUser=getCurrentUser();

		if(isItCurrentUser(id,currentUser)) {
			userService.deleteUser(id);
			return ok();
		}
		else {
			return unauthorized();
		}
	}
	@PutMapping(path="/edit/{id}")
	public ResponseEntity<?> updateUser(@Validated @NonNull @RequestBody User user,@PathVariable("id") Long id) {
		User currentUser=getCurrentUser();
		
		if(isItCurrentUser(id,currentUser)) {
			return userService.updateUser(user,id);
		}
		else {
			return unauthorized();
		}
		
	}

	@GetMapping
	public ResponseEntity<Page<User>> searchByUsername(@RequestParam(value="username") String username,
													  @RequestParam(defaultValue = "0") int page,
													  @RequestParam(defaultValue = "10") int size){

		Page<User> users = userService.searchByUsername(username,page,size);
		return ResponseEntity.ok().body(users);
	}

	@GetMapping("/byRole")
	public ResponseEntity<Page<User>> searchByRoles(@RequestParam(value="role") String role,
												   @RequestParam(defaultValue = "0") int page,
												   @RequestParam(defaultValue="10") int size){
		Page<User> users = userService.searchByRoles(role,page,size);
		return ResponseEntity.ok().body(users);
	}
	
}