package com.ecommerceweb.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ecommerceweb.dto.CategoryDto;
import com.ecommerceweb.service.CategoryService;


@RestController
@RequestMapping("/admin")
public class CategoryController {
	
	Logger logger = LoggerFactory.getLogger(CategoryController.class);
	
	@Autowired
	private CategoryService categoryService;
	
	@PostMapping("/addCategory/{userId}")
	public ResponseEntity<CategoryDto> saveCategory(@RequestBody CategoryDto categoryDto, @PathVariable Long userId)
	{
		logger.info("saveCategory method started");
		return new ResponseEntity<CategoryDto> (categoryService.addCategory(categoryDto,userId),HttpStatus.OK);
	}

	@GetMapping("/getAllCategory")
	public ResponseEntity<List<CategoryDto>> getAllCategory()
	{
		logger.info("getAllCategory method started");
		return new ResponseEntity<List<CategoryDto>> (categoryService.getAllCategory(),HttpStatus.OK);
	}
	
	@GetMapping("/getCategory/{id}")
	public ResponseEntity<CategoryDto> getCategory(@PathVariable Long id)
	{
		logger.info("getCategory method started");
		return new ResponseEntity<CategoryDto> (categoryService.getCategory(id),HttpStatus.OK);
	}
	
}
