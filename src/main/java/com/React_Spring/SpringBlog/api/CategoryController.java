package com.React_Spring.SpringBlog.api;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.React_Spring.SpringBlog.models.Category;
import com.React_Spring.SpringBlog.services.CategoryService;


@RestController
@RequestMapping("/category")
public class CategoryController {
	
	@Autowired
	private CategoryService categoryService;
	
	public CategoryController(CategoryService categoryService) {
		this.categoryService=categoryService;
	}
	
	@GetMapping("/")
	public List<Category> getAll(){
		return categoryService.getAllCategorys();
	}
	@GetMapping(path="{id}")
	public Optional<Category> getOneById(@PathVariable("id") int id) {
		return categoryService.getOneCategoryById(id);
	}

	@PostMapping
	public void add(@Validated @NonNull @RequestBody Category category) {
		categoryService.addCategory(category);
	}

	@DeleteMapping(path="{id}")
	public void delete(@PathVariable("id") int id) {
		categoryService.deleteCategory(id);
	}
	@PutMapping(path="{id}")
	public void updateCategory(@Validated @NonNull @RequestBody Category category, @PathVariable("id") int id) {
		categoryService.updateCategory(category,id);
	}
	
}