package com.React_Spring.SpringBlog.models;


import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.React_Spring.SpringBlog.models.User;
import com.fasterxml.jackson.annotation.JsonIgnore;

import org.springframework.lang.NonNull;
@Entity
@Table(name="BLOG_TABLE")
public class Blog {

	@Id @NonNull @GeneratedValue(strategy=GenerationType.IDENTITY) @Column(name="BLOG_ID")
	private int id;
	@NonNull @Column(name="BLOG_NAME")
	private String name;
	
	@Column(name="USER_ID")
	private int userId;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="USER_ID", referencedColumnName="USER_ID", insertable=false, updatable=false)
	@JsonIgnore
	private User user;
	
	@OneToMany(mappedBy="blog", orphanRemoval=true, cascade=CascadeType.PERSIST)
	private List<Post> posts=new ArrayList<Post>();

	
	
	public Blog(int id, String name, int userId, User user, List<Post> posts) {
		super();
		this.id = id;
		this.name = name;
		this.userId = userId;
		this.user = user;
		this.posts = posts;
	}
	
	

	public Blog(int id, String name, int userId, User user) {
		super();
		this.id = id;
		this.name = name;
		this.userId = userId;
		this.user = user;
	}

	public Blog() {}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
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
	

}
