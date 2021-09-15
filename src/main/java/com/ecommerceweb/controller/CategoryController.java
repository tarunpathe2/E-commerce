package com.ecommerceweb.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ecommerceweb.dto.CategoryDto;
import com.ecommerceweb.service.CategoryService;


@RestController
@RequestMapping("/e-commerce/category/")
public class CategoryController {
	
	@Autowired
	private CategoryService categoryService;
	
	@PostMapping
	public ResponseEntity<CategoryDto> saveCategory(@RequestBody CategoryDto categoryDto, @PathVariable Long userId)
	{
		return new ResponseEntity<CategoryDto> (categoryService.addCategory(categoryDto,userId),HttpStatus.OK);
	}

	@GetMapping
	public ResponseEntity<List<CategoryDto>> getAllCategory()
	{
		return new ResponseEntity<List<CategoryDto>> (categoryService.getAllCategory(),HttpStatus.OK);
	}
	
	@GetMapping("{id}")
	public ResponseEntity<CategoryDto> getCategory(@PathVariable Long id)
	{
		return new ResponseEntity<CategoryDto> (categoryService.getCategory(id),HttpStatus.OK);
	}
	
	@DeleteMapping("{id}")
	public ResponseEntity<CategoryDto> deleteCategory(@PathVariable Long id)
	{
		categoryService.deleteCategory(id);
		return new ResponseEntity<CategoryDto> (HttpStatus.OK);
	}
	
}
