package com.React_Spring.SpringBlog.models;


import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.*;

import com.React_Spring.SpringBlog.models.User;
import com.fasterxml.jackson.annotation.JsonIgnore;

import org.springframework.lang.NonNull;
@Entity
@Table(name="BLOG_TABLE")
public class Blog {

	@Id @NonNull @GeneratedValue(strategy=GenerationType.AUTO) @Column(name="BLOG_ID")
	private Long id;
	@NonNull @Column(name="BLOG_NAME")
	private String name;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="USER_ID", referencedColumnName="USER_ID", insertable=true, updatable=false)
	@JsonIgnore
	private User user;
	
	@OneToMany(mappedBy="blog", orphanRemoval=true, cascade=CascadeType.ALL, fetch=FetchType.LAZY)
	private List<Post> posts=new ArrayList<Post>();

	@ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinTable(name="CATEGORYMAP_TABLE",
	joinColumns = {@JoinColumn(name="BLOG_ID")},
	inverseJoinColumns = {@JoinColumn(name="CATEGORY_ID")})
	private Set<Category> blogCategories=new HashSet<Category>();
	
	public Blog(Long id, String name, User user, List<Post> posts) {
	    super();
	    this.id = id;
	    this.name = name;
	    this.user = user;
	    this.posts = posts;
	}
	
	

	public Blog(Long id, String name, User user) {
	    super();
	    this.id = id;
	    this.name = name;
	    this.user = user;
	}

	public Blog(String name,User user) {
	    this.name = name;
	    this.user = user;
	}

	public Blog() {}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public List<Post> getPosts() {
		return posts;
	}

	public void setPosts(List<Post> posts) {
		this.posts = posts;
	}

	public Set<Category> getBlogCategories() {
	    return blogCategories;
    	}

    	public void setBlogCategories(Set<Category> blogCategories) {
	    this.blogCategories = blogCategories;
    	}

    	@Override
	public boolean equals(Object blog) {
	    Blog blogT = (Blog)blog;
	    return blogT.getName() == this.name;
	}
}
