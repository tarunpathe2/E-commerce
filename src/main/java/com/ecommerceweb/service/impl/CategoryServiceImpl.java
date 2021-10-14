package com.ecommerceweb.service.impl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ecommerceweb.constants.ConstantMsg;
import com.ecommerceweb.dto.CategoryDto;
import com.ecommerceweb.entity.Category;
import com.ecommerceweb.enums.Role;
import com.ecommerceweb.exception.DataNotFoundException;
import com.ecommerceweb.exception.UnprocessableEntity;
import com.ecommerceweb.repository.CategoryRepository;
import com.ecommerceweb.repository.UserRepository;
import com.ecommerceweb.service.CategoryService;
import com.ecommerceweb.service.UserService;

@Service
public class CategoryServiceImpl implements CategoryService {

	@Autowired
	private ModelMapper modelMapper;

	@Autowired
	UserRepository userRepo;

	@Autowired
	UserService userService;

	@Autowired
	CategoryRepository categoryRepo;
	
	Logger logger = LoggerFactory.getLogger(CategoryServiceImpl.class);

	@Override
	public List<CategoryDto> getAllCategory() {
		logger.info("getAllCategory method in CategoryServiceImpl started");
		List<Category> category = categoryRepo.findAll();
		List<CategoryDto> categoryDto = category.stream().map(user -> modelMapper.map(user, CategoryDto.class))
				.collect(Collectors.toList());
		logger.info("getAllCategory method in CategoryServiceImpl ended");
		return categoryDto;

	}

	@Override
	public CategoryDto getCategory(Long id) {
		logger.info("getCategory method in CategoryServiceImpl started");
		Optional<Category> categoryOptional = categoryRepo.findById(id);
		if (!categoryOptional.isPresent()) {
			throw new DataNotFoundException(ConstantMsg.isInvalid);
		}
		CategoryDto categoryDto = modelMapper.map(categoryOptional.get(), CategoryDto.class);
		logger.info("getCategory method in CategoryServiceImpl ended");
		return categoryDto;
	}

	@Override
	public CategoryDto addCategory(CategoryDto categoryDto, Long userId) {

		logger.info("addCategory method in CategoryServiceImpl started");
		
		if (!userRepo.findUserById(userId).getRole().equalsIgnoreCase(Role.ADMIN.toString()) ) {
			throw new UnprocessableEntity("client cannot access");
		}

		Category category = modelMapper.map(categoryDto, Category.class);
		categoryRepo.save(category);
		logger.info("addCategory method in CategoryServiceImpl ended");
		return modelMapper.map(category, CategoryDto.class);
	}

}
