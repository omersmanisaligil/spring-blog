package com.React_Spring.SpringBlog.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.support.PagedListHolder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.React_Spring.SpringBlog.dao.BlogDAO;
import com.React_Spring.SpringBlog.models.Blog;
import com.React_Spring.SpringBlog.models.Post;

@Service
public class BlogService {
	@Autowired
	private BlogDAO blogDAO;
	
	public BlogService(BlogDAO blogDAO) {
		this.blogDAO=blogDAO;
	}
	
	public List<Blog> getAllBlogs(){
		return (List<Blog>) blogDAO.findAll();
	}
	
	public Optional<Blog> getOneBlogById(int id) {
		return blogDAO.findById(id);
	}
	
	public void deleteBlog(int id) {		
		blogDAO.deleteById(id);
	}
	
	public void addBlog(Blog blog) {
		blogDAO.save(blog);
	}
	
	public void updateBlog(Blog blog, int id) {
		Blog toUpdate=blogDAO.findById(id).get();
		
		toUpdate=blog;
		toUpdate.setId(id);
		
		blogDAO.save(toUpdate);
	}
	public Set<Blog> searchBlogs(String name){
		return blogDAO.findByNameLike(name);
	}

	public Page<Post> getBlogPosts(int id, int page, int size){
		Blog blog = blogDAO.findById(id).get();
		List<Post> posts = blog.getPosts();

		PagedListHolder postPage = new PagedListHolder(posts);
		postPage.setPageSize(size);
		postPage.setPage(page);

		Page<Post> blogPosts = new PageImpl<Post>(postPage.getPageList());

		return blogPosts;
	}
}
