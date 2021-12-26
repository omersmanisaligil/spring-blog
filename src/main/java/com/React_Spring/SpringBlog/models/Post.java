package com.React_Spring.SpringBlog.models;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.springframework.lang.NonNull;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name="POST_TABLE")
public class Post {

	@Id @NonNull @GeneratedValue(strategy=GenerationType.AUTO) @Column(name="POST_ID")
	private Long id;
	@NonNull @Column(name="USER_ID")
	private Long userId;
	@NonNull @Column(name="HEADER")
	private String header;
	@NonNull @Column(name="BODY")
	private String body;
	
	@Transient
	private String[] tags;

	@Column(name="BLOG_ID")
	private Long blogId;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="BLOG_ID", referencedColumnName = "BLOG_ID", insertable=false, updatable=false)
	@JsonIgnore
	private Blog blog;
	
	
	@ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinTable(name="TAGMAP_TABLE",
	joinColumns = {@JoinColumn(name="POST_ID")},
	inverseJoinColumns = {@JoinColumn(name="TAG_ID")})
	private Set<Tag> postTags=new HashSet<Tag>();
	

	public Post(Long id, Long userId, String header, String body, String[] tags, Long blogId, Blog blog, Set<Tag> postTags) {
	    super();
	    this.id = id;
	    this.userId = userId;
	    this.header = header;
	    this.body = body;
	    this.tags = tags;
	    this.blogId = blogId;
	    this.blog = blog;
	    this.postTags = postTags;
	}

	public Post(Long id, Long userId, String header, String body, String[] tags, Long blogId, Blog blog) {
	    super();
	    this.id = id;
	    this.userId = userId;
	    this.header = header;
	    this.body = body;
	    this.tags = tags;
	    this.blogId = blogId;
	    this.blog = blog;
	}
	public Post(Long userId, String header, String body){
	    this.userId = userId;
	    this.header = header;
	    this.body = body;
	}
	public Post() {}


	public Long getId() {
		return id;
	}


	public void setId(Long id) {
		this.id = id;
	}


	public Long getUserId() {
		return userId;
	}


	public void setUserId(Long userId) {
		this.userId = userId;
	}


	public String getHeader() {
		return header;
	}


	public void setHeader(String header) {
		this.header = header;
	}


	public String getBody() {
		return body;
	}


	public void setBody(String body) {
		this.body = body;
	}


	public Long getBlogId() {
		return blogId;
	}


	public void setBlogId(Long blogId) {
		this.blogId = blogId;
	}


	public Blog getBlog() {
		return blog;
	}


	public void setBlog(Blog blog) {
		this.blog = blog;
	}


	public Set<Tag> getPostTags() {
		return postTags;
	}


	public void setPostTags(Set<Tag> postTags) {
		this.postTags = postTags;
	}
	public String[] getTags() {
		return tags;
	}


	public void setTags(String[] tags) {
		this.tags = tags;
	}

	@Override
    	public boolean equals(Object post){
	    Post postk = (Post)post;

	    return postk.getHeader().equals(this.header)
	          && postk.getBody().equals(this.body);
	}

	@Override
    	public String toString() {
	return header+"/n"+body;
    }
}
