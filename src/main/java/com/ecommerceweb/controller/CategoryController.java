package com.ecommerceweb.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ecommerceweb.dto.CategoryDto;
import com.ecommerceweb.service.CategoryService;


@RestController
@RequestMapping("/e-commerce/user/")
public class CategoryController {
	
	@Autowired
	private CategoryService categoryService;
	
	@PostMapping("add-category")
	public ResponseEntity<CategoryDto> saveCategory(@RequestBody CategoryDto categoryDto)
	{
		return new ResponseEntity<CategoryDto> (categoryService.addCategory(categoryDto),HttpStatus.OK);
	}

	@GetMapping("getAll-category")
	public ResponseEntity<List<CategoryDto>> getAllCategory()
	{
		return new ResponseEntity<List<CategoryDto>> (categoryService.getAllCategory(),HttpStatus.OK);
	}
	
	@GetMapping("get-category/{id}")
	public ResponseEntity<CategoryDto> getCategory(@PathVariable Long id)
	{
		return new ResponseEntity<CategoryDto> (categoryService.getCategory(id),HttpStatus.OK);
	}
	
	@PutMapping("update-category")
	public ResponseEntity<CategoryDto> updateCategory(@RequestBody CategoryDto categoryDto)
	{
		return new ResponseEntity<CategoryDto> (categoryService.updateCategory(categoryDto),HttpStatus.OK);
	}
	
	@DeleteMapping("delete-category/{id}")
	public ResponseEntity<CategoryDto> deleteCategory(@PathVariable Long id)
	{
		categoryService.deleteCategory(id);
		return new ResponseEntity<CategoryDto> (HttpStatus.OK);
	}
	
}
