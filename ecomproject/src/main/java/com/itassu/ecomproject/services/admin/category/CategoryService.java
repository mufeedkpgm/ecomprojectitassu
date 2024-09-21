 package com.itassu.ecomproject.services.admin.category;

import java.util.List;

import com.itassu.ecomproject.dto.CategoryDto;
import com.itassu.ecomproject.entity.Category;

public interface CategoryService {
	
	Category createCategory(CategoryDto categoryDto);
	
	List<Category> getAllCategories();

}
