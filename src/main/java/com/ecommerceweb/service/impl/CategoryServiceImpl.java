package com.ecommerceweb.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ecommerceweb.dto.CategoryDto;
import com.ecommerceweb.entity.Category;
import com.ecommerceweb.entity.User;
import com.ecommerceweb.exception.BadInputException;
import com.ecommerceweb.exception.UnprocessableEntity;
import com.ecommerceweb.repository.CategoryRepository;
import com.ecommerceweb.repository.UserRepository;
import com.ecommerceweb.service.CategoryService;

@Service
public class CategoryServiceImpl implements CategoryService{

	@Autowired
	private ModelMapper modelMapper;

	@Autowired
	UserRepository userRepo;
	
	@Autowired
	CategoryRepository categoryRepo;
	
	public boolean isExist(Long id)
	{
		return userRepo.existsById(id);
	}

	@Override
	public List<CategoryDto> getAllCategory() {
		List<Category> category = categoryRepo.findAll();
		List<CategoryDto> categoryDto = category.stream().map(user -> modelMapper.map(user, CategoryDto.class))
				.collect(Collectors.toList());

		return categoryDto;

	}

	@Override
	public CategoryDto getCategory(Long id) {

		return modelMapper.map(categoryRepo.findById(id).get(), CategoryDto.class);
	}

	@Override
	public CategoryDto addCategory(CategoryDto categoryDto) {
		
		if(!isExist(categoryDto.getUserId()))
		{
			throw new BadInputException("User not exist");
		}
		
		User user = userRepo.findById(categoryDto.getUserId()).get();
		if (user.getRole() == 0) {
			throw new UnprocessableEntity("User cannot process");
		}
		
		Category category = modelMapper.map(categoryDto, Category.class);
		categoryRepo.save(category);
		return modelMapper.map(category, CategoryDto.class);
	}

	@Override
	public CategoryDto updateCategory(CategoryDto categoryDto) {
		
		User user = userRepo.findById(categoryDto.getUserId()).get();
		if (user.getRole() == 0) {
			throw new UnprocessableEntity("User cannot process");
		}
		
		Category category = modelMapper.map(categoryDto, Category.class);
		categoryRepo.save(category);
		return modelMapper.map(category, CategoryDto.class);
	}

	@Override
	public void deleteCategory(Long id) {
		if(!categoryRepo.existsById(id))
		{
			throw new BadInputException("Category Id doesn't exist");
		}
		categoryRepo.deleteById(id);
	}
}
