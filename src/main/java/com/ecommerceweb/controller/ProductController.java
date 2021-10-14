package com.ecommerceweb.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
@RequestMapping("/e-commerce/admin")
public class ProductController {

	@Autowired
	private ProductService productService;

	Logger logger = LoggerFactory.getLogger(ProductController.class);

	@PostMapping("/addProducts/{userId}/{categoryId}")
	public ResponseEntity<ProductDto> saveProduct(@RequestBody ProductDto productDto, @PathVariable Long userId,
			@PathVariable Long categoryId) {
		logger.info("saveProduct method started");
		return new ResponseEntity<ProductDto>(productService.addProduct(productDto, userId, categoryId), HttpStatus.OK);
	}

	@GetMapping("/getProduct/{id}")
	public ResponseEntity<ProductDto> getProduct(@PathVariable Long id) {
		logger.info("getProduct method started");
		return new ResponseEntity<ProductDto>(productService.getProduct(id), HttpStatus.OK);
	}

	@GetMapping("/getAllProducts")
	public ResponseEntity<List<ProductDto>> getAllProducts() {
		logger.info("getAllProducts method started");
		return new ResponseEntity<List<ProductDto>>(productService.getAllProduct(), HttpStatus.OK);
	}

	@PutMapping("/updateProduct/{userId}/{categoryId}")
	public ResponseEntity<ProductDto> updateProduct(@RequestBody ProductDto productDto, @PathVariable Long userId,
			@PathVariable Long categoryId) {
		logger.info("updateProduct method started");
		return new ResponseEntity<ProductDto>(productService.updateProduct(productDto, userId, categoryId),
				HttpStatus.OK);
	}

	@DeleteMapping("/deleteProduct{id}")
	public ResponseEntity<ProductDto> deleteProduct(@PathVariable Long id) {
		logger.info("deleteProduct method started");
		productService.deleteProduct(id);
		return new ResponseEntity<ProductDto>(HttpStatus.OK);
	}
}
