package com.React_Spring.SpringBlog.services;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.React_Spring.SpringBlog.dao.PostDAO;
import com.React_Spring.SpringBlog.dao.TagDAO;
import com.React_Spring.SpringBlog.models.Post;
import com.React_Spring.SpringBlog.models.Tag;

@Service
public class PostService {
	@Autowired
	private PostDAO postDAO;
	
	@Autowired
	private TagService tagService;

	
	public PostService(PostDAO postDAO) {
		this.postDAO=postDAO;
	}
	
	public List<Post> getAllPosts(){
		return (List<Post>) postDAO.findAll();
	}
	
	public Optional<Post> getOnePostById(int id) {
		return postDAO.findById(id);
	}
	
	public void deletePost(int id) {
		Post post=postDAO.findById(id).get();
		
		post.getBlog().getPosts().remove(post);
		
		post.getPostTags().forEach(tag->{
			tag.getPosts().remove(post);
		});
		
		postDAO.deleteById(id);
	}

	public void addPost(Post post) {		
		Set<Tag> postTagList=post.getPostTags();
		String[] tags=post.getTags();
		
		if( tags!=null && tags.length>0 ) {
			//converting string[] to string set
			Set<String> tagStringSet=Arrays.stream(tags).collect(Collectors.toSet());
				
			//converting string set to tag set
			Set<Tag> tagSet=tagStringSet.stream().map(tagString->{
				Tag tagToSave=new Tag();
				tagToSave.setTagName(tagString);
				return tagToSave;
			}).collect(Collectors.toSet());
				
				
			//saving tags in tagSet
			tagSet.forEach(tag->{
					tagService.addTag(tag);
						
					System.out.println(tagSet);
					tag.getPosts().add(post);
						
					postTagList.add(tag);
			});
		}
		postDAO.save(post);
	}
	
	public void updatePost(Post post, int id) {
		Post toUpdate=postDAO.findById(id).get();
	
		toUpdate=post;
		post.setId(id);
		
		postDAO.save(toUpdate);
	}
}
