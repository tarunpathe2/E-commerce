package com.ecommerceweb.service;

import java.util.List;

import com.ecommerceweb.dto.CategoryDto;

public interface CategoryService {

	public List<CategoryDto> getAllCategory();
	
	public CategoryDto getCategory(Long id);
	
	public CategoryDto addCategory(CategoryDto categoryDto);
	
	public CategoryDto updateCategory(CategoryDto categoryDto);
	
	public void deleteCategory(Long id);
	
}
