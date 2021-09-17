package com.ecommerceweb.service;

import java.util.List;

import com.ecommerceweb.dto.ProductDto;

public interface ProductService {

	public List<ProductDto> getAllProduct();
	
	public ProductDto getProduct(Long id);
	
	public ProductDto addProduct(ProductDto productDto,Long userId,Long categoryId);
	
	public ProductDto updateProduct(ProductDto productDto,Long userId,Long categoryId);
	
	public void deleteProduct(Long id);
}
