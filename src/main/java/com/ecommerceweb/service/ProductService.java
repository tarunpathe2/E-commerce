package com.ecommerceweb.service;

import java.util.List;

import com.ecommerceweb.dto.ProductDto;

public interface ProductService {

	public void ifExist(Long categoryId);
	
	public List<ProductDto> getAllProduct();
	
	public ProductDto getProduct(Long id);
	
	public ProductDto addProduct(ProductDto productDto);
	
	public ProductDto updateProduct(ProductDto productDto);
	
	public void deleteProduct(Long id);
}
