package com.ecommerceweb.service.impl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ecommerceweb.constants.ConstantMsg;
import com.ecommerceweb.dto.CategoryDto;
import com.ecommerceweb.dto.UserDto;
import com.ecommerceweb.entity.Category;
import com.ecommerceweb.exception.BadInputException;
import com.ecommerceweb.exception.DataNotFoundException;
import com.ecommerceweb.exception.UnprocessableEntity;
import com.ecommerceweb.repository.CategoryRepository;
import com.ecommerceweb.repository.UserRepository;
import com.ecommerceweb.service.CategoryService;
import com.ecommerceweb.service.UserService;

@Service
public class CategoryServiceImpl implements CategoryService{

	@Autowired
	private ModelMapper modelMapper;

	@Autowired
	UserRepository userRepo;
	
	@Autowired
	UserService userService;
	
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
		
		Optional<Category> categoryOptional = categoryRepo.findById(id);
		if(!categoryOptional.isPresent())
		{
			throw new DataNotFoundException(ConstantMsg.isInvalid);
		}
		CategoryDto categoryDto =modelMapper.map(categoryOptional.get(), CategoryDto.class);
		return categoryDto;
	}

	@Override
	public CategoryDto addCategory(CategoryDto categoryDto, Long userId) {
		
		UserDto userDto = userService.getUser(userId);
		if (userDto.getRole() != 1) {
			throw new UnprocessableEntity("client cannot access");
		}
		
		Category category = modelMapper.map(categoryDto, Category.class);
		categoryRepo.save(category);
		return modelMapper.map(category, CategoryDto.class);
	}

	@Override
	public void deleteCategory(Long id) {
		if(!categoryRepo.existsById(id))
		{
			throw new BadInputException(ConstantMsg.notFound);
		}
		categoryRepo.deleteById(id);
	}
}
