package com.React_Spring.SpringBlog.models;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import org.springframework.lang.NonNull;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name="TAG_TABLE")
public class Tag {
	
	@Id @NonNull @GeneratedValue(strategy=GenerationType.AUTO) @Column(name="TAG_ID")
	private Long id;
	
	@NonNull @Column(name="TAG_NAME")
	private String tagName;

	@ManyToMany(fetch=FetchType.LAZY, mappedBy="postTags")
	@JsonIgnore
	private Set<Post> posts=new HashSet<Post>();
		
	public Tag() {}

	public Tag(Long id, String tagName, Set<Post> posts) {
	    super();
	    this.id = id;
	    this.tagName = tagName;
	    this.posts = posts;
	};

	public Tag(String tagName){
	    this.tagName = tagName;
	}

	public Tag(String tagName, Set<Post> posts){
	    this.tagName = tagName;
	    this.posts = posts;
	}
	
	public String toString() {
		return tagName;
	}
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTagName() {
		return tagName;
	}

	public void setTagName(String tagName) {
		this.tagName = tagName;
	}

	public Set<Post> getPosts() {
		return posts;
	}

	public void setPosts(Set<Post> posts) {
		this.posts = posts;
	}

	@Override
    	public boolean equals(Object tag){
	    return ((Tag)tag).getTagName() == this.tagName;
	}
}
