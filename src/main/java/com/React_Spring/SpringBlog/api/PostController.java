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

import com.React_Spring.SpringBlog.models.Post;
import com.React_Spring.SpringBlog.security.UserDetailsImpl;
import com.React_Spring.SpringBlog.services.PostService;


@RestController
@RequestMapping("/posts")
public class PostController extends Controller {
	
	@Autowired
	private PostService postService;
	
	public PostController(PostService postService) {
		this.postService=postService;
	}
	
	@GetMapping(path="/")
	public List<Post> getAll(){
		return postService.getAllPosts();
	}
	
	@GetMapping(path="{id}")
	public Optional<Post> getOneById(@PathVariable("id") int id){		
		return postService.getOnePostById(id);
	}
	
	@PostMapping("/add")
	public ResponseEntity<?> addPost(@Validated @NonNull @RequestBody Post post) {
		UserDetailsImpl currentUser=getCurrentUser();
		
		if(isItCurrentUser(post.getUserId(), currentUser)) {
			postService.addPost(post);
			return ok();
		}
		else {
			return unauthorized();
		}
	}
	
	@DeleteMapping(path="{id}")
	public ResponseEntity<?> deletePost(@PathVariable("id") int id) {
		UserDetailsImpl currentUser=getCurrentUser();
		Post post=getOneById(id).get();
		
		if(isItCurrentUser(post.getUserId(),currentUser)) {
			postService.deletePost(id);
			return ok();
		}
		else {
			return unauthorized();
		}
			
	}
	@PutMapping(path="/edit/{id}")
	public ResponseEntity<?> updatePost(@Validated @NonNull @RequestBody Post post, @PathVariable("id") int id) {
		UserDetailsImpl currentUser=getCurrentUser();
		
		if(isItCurrentUser(post.getUserId(),currentUser)){
			postService.updatePost(post,id);
			return ok();
		}
		else {
			return unauthorized();
		}
	}
	
}
