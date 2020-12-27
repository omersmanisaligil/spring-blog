package com.React_Spring.SpringBlog.api;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.React_Spring.SpringBlog.models.Blog;
import com.React_Spring.SpringBlog.security.UserDetailsImpl;
import com.React_Spring.SpringBlog.services.BlogService;

@RestController
@RequestMapping("/blogs")
public class BlogController extends Controller{

	@Autowired
	private BlogService blogService;
	
	@GetMapping("/")
	public List<Blog> getAll(){
		return blogService.getAllBlogs();
	}
	
	@GetMapping(path="{id}")
	public Optional<Blog> getOneById(@PathVariable("id") int id){
		return blogService.getOneBlogById(id);
	}
	
	@PostMapping("/add")
	public ResponseEntity<?> postBlog(@Validated @NonNull @RequestBody Blog blog) {
		UserDetailsImpl currentUser=getCurrentUser();
		
		if(isItCurrentUser(blog.getUserId(),currentUser)) {
			blogService.addBlog(blog);
			return ok();
		}
		else {
			return unauthorized();
		}
	}
	
	@DeleteMapping
	public ResponseEntity<?> deleteBlog(@PathVariable("id") int id) {
		UserDetailsImpl currentUser=getCurrentUser();
		Blog blog=getOneById(id).get();
		
		if(isItCurrentUser(blog.getUserId(), currentUser)) {
			blogService.deleteBlog(id);
			return ok();
		}
		else {
			return unauthorized();
		}
	}
	
	@PutMapping(path="/edit/{id}")
	public ResponseEntity<?> updateBlog(@Validated @NonNull @RequestBody Blog blog,@PathVariable("id") int id) {
		UserDetailsImpl currentUser=getCurrentUser();
		
		if(isItCurrentUser(blog.getUserId(), currentUser)) {
			blogService.updateBlog(blog,id);
			return ok();
		}
		else {
			return unauthorized();
		}
	}
}
