package com.React_Spring.SpringBlog.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.lang.NonNull;


@Entity
@Table(name="CATEGORY_TABLE")
public class Category {
	
	@Id @NonNull @GeneratedValue(strategy=GenerationType.IDENTITY) @Column(name="TAG_ID")
	private int id;
	@NonNull @Column(name="CATEGORY_NAME")
	private String categoryName;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getCategoryName() {
		return categoryName;
	}
	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}
	public Category(int id, String categoryName) {
		super();
		this.id = id;
		this.categoryName = categoryName;
	}
	public Category() {}
}
