package com.React_Spring.SpringBlog.api;

import java.util.List;
import java.util.Optional;

import com.React_Spring.SpringBlog.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import com.React_Spring.SpringBlog.models.Post;
import com.React_Spring.SpringBlog.services.PostService;


@RestController
@RequestMapping("/posts")
public class PostController extends Controller {
	
	@Autowired
	private PostService postService;
	
	@GetMapping(path="/")
	public List<Post> getAll(){
		return postService.getAllPosts();
	}
	
	@GetMapping(path="{id}")
	public Optional<Post> getOneById(@PathVariable("id") Long id){
		return postService.getOnePostById(id);
	}
	
	@PostMapping("/add")
	public ResponseEntity<?> addPost(@Validated @NonNull @RequestBody Post post) {
		User currentUser=getCurrentUser();
		
		if(isItCurrentUser(post.getUserId(), currentUser)) {
			postService.addPost(post);
			return ok();
		}
		else {
			return unauthorized();
		}
	}
	
	@DeleteMapping(path="{id}")
	public ResponseEntity<?> deletePost(@PathVariable("id") Long id) {
		User currentUser=getCurrentUser();
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
	public ResponseEntity<?> updatePost(@Validated @NonNull @RequestBody Post post, @PathVariable("id") Long id) {
		User currentUser=getCurrentUser();
		
		if(isItCurrentUser(post.getUserId(),currentUser)){
			postService.updatePost(post,id);
			return ok();
		}
		else {
			return unauthorized();
		}
	}

	@GetMapping
	public ResponseEntity<Page<Post>> searchByHeader(@RequestParam("header") String header,
							 @RequestParam(defaultValue = "0") int page,
							 @RequestParam(defaultValue = "10") int size){
		Page<Post> posts=postService.searchByHeader(header, page, size);
		return ResponseEntity.ok().body(posts);
	}
	
}
