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
	
	public Optional<Category> getOneCategoryById(int id) {
		return categoryDAO.findById(id);
	}
	
	public void deleteCategory(int id) {
		categoryDAO.deleteById(id);
	}
	
	public void addCategory(Category category) {
		categoryDAO.save(category);
	}
	
	public void updateCategory(Category category,int id) {
		Category toUpdate=categoryDAO.findById(id).get();		
		/*TODO
		I don't want to set id by hand
		to the correct value
		I don't want to set all the other values one
		by one here, so as a healthier solution
		seperate partial update methods are needed*/ 
		toUpdate=category;
		toUpdate.setId(id);
		
		categoryDAO.save(toUpdate);
	}

}
