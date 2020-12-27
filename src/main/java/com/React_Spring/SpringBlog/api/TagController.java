package com.React_Spring.SpringBlog.api;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
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
import com.React_Spring.SpringBlog.models.Tag;
import com.React_Spring.SpringBlog.services.TagService;


@RestController
@RequestMapping("/tags")
public class TagController extends Controller{
	
	@Autowired
	private TagService tagService;
	
	public TagController(TagService tagService) {
		this.tagService=tagService;
	}
	
	@GetMapping("/")
	public List<Tag> getAll(){
		return tagService.getAllTags();
	}
	@GetMapping(path="{id}")
	public Optional<Tag> getOneById(@PathVariable("id") int id) {
		return tagService.getOneTagById(id);
	}
	
	@GetMapping(path="{id}/posts")
	public Set<Post> getPostsWithTag(@PathVariable("id") int id){
		return tagService.getPostsWithtag(id);
	}

	@PostMapping
	public void add(@Validated @NonNull @RequestBody Tag tag) {
		tagService.addTag(tag);
	}

	@DeleteMapping(path="{id}")
	public void delete(@PathVariable("id") int id) {
		tagService.deleteTag(id);
	}
	@PutMapping(path="{id}")
	public void updateTag(@Validated @NonNull @RequestBody Tag tag,@PathVariable("id") int id) {
		tagService.updateTag(tag,id);
	}
	
}