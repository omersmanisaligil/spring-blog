package com.React_Spring.SpringBlog.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.React_Spring.SpringBlog.dao.TagDAO;
import com.React_Spring.SpringBlog.models.Post;
import com.React_Spring.SpringBlog.models.Tag;

@Service
public class TagService {
	@Autowired
	private TagDAO tagDAO;
	
	
	public TagService(TagDAO tagDAO) {
		this.tagDAO=tagDAO;
	}
	
	public List<Tag> getAllTags(){
		return (List<Tag>) tagDAO.findAll();
	}
	
	public Optional<Tag> getOneTagById(Long id) {
		return tagDAO.findById(id);
	}
	public Set<Post> getPostsWithTag(Long id){
		return tagDAO.findById(id).get().getPosts();
	}
	
	public void deleteTag(Long id) {
		
		Tag tag=tagDAO.findById(id).get();
		
		Set<Post> postsOfTag=tag.getPosts();
		
		postsOfTag.forEach(post->{
			post.getPostTags().remove(tag);
			postsOfTag.remove(post);
		});
		
		tagDAO.deleteById(id);
	}
	
	public void addTag(Tag tag) {
		if(!doesTagExist(tag)) {
			tagDAO.save(tag);
		}
	}

	public void updateTag(Tag tag,Long id) {
		Tag toUpdate=tag;
		toUpdate.setId(id);
		
		tagDAO.save(toUpdate);
	}
	
	private boolean doesTagExist(Tag tag) {
		ArrayList<Tag> tags=(ArrayList<Tag>)tagDAO.findAll();
		if(tags.contains(tag))
			return true;
		else
			return false;
	}
	public Page<Post> findPostsByTag(String tagName, int page, int size){
		Pageable pageable = PageRequest.of(page, size);
		return tagDAO.findPostsWithTag(tagName, pageable);
	}
}
