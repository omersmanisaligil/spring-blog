package com.React_Spring.SpringBlog.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.React_Spring.SpringBlog.dao.CategoryDAO;
import com.React_Spring.SpringBlog.models.Category;

@Service
public class CategoryService {
	@Autowired
	private CategoryDAO categoryDAO;
	
	
	public CategoryService(CategoryDAO categoryDAO) {
		this.categoryDAO=categoryDAO;
	}
	
	public List<Category> getAllCategorys(){
		return (List<Category>) categoryDAO.findAll();
	}
	
	public Optional<Category> getOneCategoryById(Long id) {
		return categoryDAO.findById(id);
	}
	
	public void deleteCategory(Long id) {
		categoryDAO.deleteById(id);
	}
	
	public void addCategory(Category category) {
		categoryDAO.save(category);
	}
	
	public void updateCategory(Category category,Long id) {
		Category toUpdate=category;
		toUpdate.setId(id);
		
		categoryDAO.save(toUpdate);
	}

}
