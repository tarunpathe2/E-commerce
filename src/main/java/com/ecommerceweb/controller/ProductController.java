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

import com.ecommerceweb.dto.ProductDto;
import com.ecommerceweb.service.ProductService;

@RestController
@RequestMapping("/e-commerce/products/")
public class ProductController {

	@Autowired
	private ProductService productService;
	
	@PostMapping("{userId}/{categoryId}")
	public ResponseEntity<ProductDto> saveProduct(@RequestBody ProductDto productDto, @PathVariable Long userId, @PathVariable Long categoryId)
	{
		return new ResponseEntity<ProductDto> (productService.addProduct(productDto,userId,categoryId),HttpStatus.OK);
	}
	
	@GetMapping("{id}")
	public ResponseEntity<ProductDto> getProduct(@PathVariable Long id)
	{
		return new ResponseEntity<ProductDto> (productService.getProduct(id),HttpStatus.OK);
	}
	
	@GetMapping
	public ResponseEntity<List<ProductDto>> getAllProducts()
	{
		return new ResponseEntity<List<ProductDto>> (productService.getAllProduct(),HttpStatus.OK);
	}
	
	@PutMapping
	public ResponseEntity<ProductDto> updateProduct(@RequestBody ProductDto productDto)
	{
		return new ResponseEntity<ProductDto> (productService.updateProduct(productDto),HttpStatus.OK);
	}
	
	@DeleteMapping("{id}")
	public ResponseEntity<ProductDto> deleteProduct(@PathVariable Long id)
	{
		productService.deleteProduct(id);
		return new ResponseEntity<ProductDto> (HttpStatus.OK);
	}
}
