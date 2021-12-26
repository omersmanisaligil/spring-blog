package com.React_Spring.SpringBlog.api;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import com.React_Spring.SpringBlog.models.Post;
import com.React_Spring.SpringBlog.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import com.React_Spring.SpringBlog.models.Blog;
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
	public Optional<Blog> getOneById(@PathVariable("id") Long id){
		return blogService.getOneBlogById(id);
	}

	@GetMapping(path="{id}/posts")
	public ResponseEntity<Page<Post>> getBlogPosts(@PathVariable("id") Long id,
						       @RequestParam(defaultValue = "0") int page,
						       @RequestParam(defaultValue = "5") int size){
		Page<Post> blogPosts = blogService.getBlogPosts(id, page, size);
		return ResponseEntity.ok().body(blogPosts);
	}
	
	@PostMapping("/add")
	public ResponseEntity<?> postBlog(@Validated @NonNull @RequestBody Blog blog) {
		User currentUser=getCurrentUser();
		
		if(isItCurrentUser(blog.getUser().getId(),currentUser)) {
			blogService.addBlog(blog);
			return ok();
		}
		else {
			return unauthorized();
		}
	}
	
	@DeleteMapping
	public ResponseEntity<?> deleteBlog(@PathVariable("id") Long id) {
		User currentUser=getCurrentUser();
		Blog blog=getOneById(id).get();
		
		if(isItCurrentUser(blog.getUser().getId(), currentUser)) {
			blogService.deleteBlog(id);
			return ok();
		}
		else {
			return unauthorized();
		}
	}
	
	@PutMapping(path="/edit/{id}")
	public ResponseEntity<?> updateBlog(@Validated @NonNull @RequestBody Blog blog,@PathVariable("id") Long id) {
		User currentUser=getCurrentUser();
		
		if(isItCurrentUser(blog.getUser().getId(), currentUser)) {
			blogService.updateBlog(blog,id);
			return ok();
		}
		else {
			return unauthorized();
		}
	}

	@GetMapping
	public ResponseEntity<Set<Blog>> searchBlogs(@RequestParam(value="name", required = false) String name){
		Set<Blog> blogs=blogService.searchBlogs(name);
		return ResponseEntity.ok().body(blogs);
	}
}
