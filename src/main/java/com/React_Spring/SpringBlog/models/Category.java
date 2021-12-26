package com.React_Spring.SpringBlog.models;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.lang.NonNull;

import java.util.HashSet;
import java.util.Set;


@Entity
@Table(name="CATEGORY_TABLE")
public class Category {
	
	@Id @NonNull @GeneratedValue(strategy=GenerationType.AUTO) @Column(name="TAG_ID")
	private Long id;
	@NonNull @Column(name="CATEGORY_NAME")
	private String categoryName;

	@ManyToMany(fetch=FetchType.LAZY, mappedBy="blogCategories")
	@JsonIgnore
	private Set<Blog> blogs=new HashSet<Blog>();

	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getCategoryName() {
		return categoryName;
	}
	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}
	public Category(Long id, String categoryName) {
		super();
		this.id = id;
		this.categoryName = categoryName;
	}
	public Category() {}
	public Category(String categoryName){
	    this.categoryName = categoryName;
	}

	@Override
        public boolean equals(Object category){
	    return ((Category)category).getCategoryName() == this.categoryName;
	}
}
