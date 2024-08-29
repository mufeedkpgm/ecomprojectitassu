 package com.itassu.ecomproject.services.admin.category;

import com.itassu.ecomproject.dto.CategoryDto;
import com.itassu.ecomproject.entity.Category;

public interface CategoryService {
	
	Category createCategory(CategoryDto categoryDto);

}
