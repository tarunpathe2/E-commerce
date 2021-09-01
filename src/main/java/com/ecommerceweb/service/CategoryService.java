package com.ecommerceweb.service;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ecommerceweb.dto.CategoryDto;
import com.ecommerceweb.entity.Category;
import com.ecommerceweb.repository.CategoryRepository;

@Service
public class CategoryService {

	@Autowired
	private ModelMapper modelMapper;

	@Autowired
	CategoryRepository categoryRepo;

	public List<CategoryDto> getAllCategory() {
		List<Category> category = categoryRepo.findAll();
		List<CategoryDto> categoryDto = category.stream().map(user -> modelMapper.map(user, CategoryDto.class))
				.collect(Collectors.toList());

		return categoryDto;

	}

	public CategoryDto getCategory(Long id) {

		return modelMapper.map(categoryRepo.findById(id).get(), CategoryDto.class);
	}

	public CategoryDto addCategory(CategoryDto categoryDto) {
		Category category = modelMapper.map(categoryDto, Category.class);
		categoryRepo.save(category);
		return modelMapper.map(category, CategoryDto.class);
	}

	public CategoryDto updateCategory(CategoryDto categoryDto) {
		Category category = modelMapper.map(categoryDto, Category.class);
		categoryRepo.save(category);
		return modelMapper.map(category, CategoryDto.class);
	}

	public void deleteCategory(Long id) {
		categoryRepo.deleteById(id);
	}
}
