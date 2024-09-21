package com.itassu.ecomproject.services.admin.category;

import java.util.List;

import org.springframework.stereotype.Service;

import com.itassu.ecomproject.dto.CategoryDto;
import com.itassu.ecomproject.entity.Category;
import com.itassu.ecomproject.repository.CategoryRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService{
	
	private final CategoryRepository categoryRepository;
	
	public Category createCategory(CategoryDto categoryDto){
		Category category = new Category();
		category.setName(categoryDto.getName());
		category.setDiscription(categoryDto.getDescription());
		
		return categoryRepository.save(category);
	}
	
	public List<Category> getAllCategories(){
		return categoryRepository.findAll();
	}
}


