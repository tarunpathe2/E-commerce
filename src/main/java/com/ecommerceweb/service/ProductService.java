package com.ecommerceweb.service;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ecommerceweb.dto.ProductDto;
import com.ecommerceweb.entity.Products;
import com.ecommerceweb.repository.ProductRepository;

@Service
public class ProductService {

	@Autowired
	ProductRepository productRepository;
	
	@Autowired
	private ModelMapper modelMapper;
	
	public List<ProductDto> getAllProduct()
	{
		List<Products> product = productRepository.findAll();
		List<ProductDto> productDto = product.stream().
				map(user -> modelMapper.map(user, ProductDto.class)).collect(Collectors.toList());
		
		return productDto;		
	}
	
	public ProductDto getProduct(Long id)
	{
		return modelMapper.map(productRepository.findById(id).get(), ProductDto.class);
	}
	
	public ProductDto addProduct(ProductDto productDto)
	{
		Products products = modelMapper.map(productDto, Products.class);
		productRepository.save(products);
		return modelMapper.map(products, ProductDto.class);
	}
	
	public ProductDto updateProduct(ProductDto productDto)
	{
		Products products = modelMapper.map(productDto, Products.class);
		productRepository.save(products);
		return modelMapper.map(products, ProductDto.class);
	}
	
	public void deleteProduct(Long id)
	{
		productRepository.deleteById(id);
	}
}
