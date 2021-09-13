package com.ecommerceweb.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ecommerceweb.dto.ProductDto;
import com.ecommerceweb.entity.Products;
import com.ecommerceweb.exception.BadInputException;
import com.ecommerceweb.exception.UnprocessableEntity;
import com.ecommerceweb.repository.CategoryRepository;
import com.ecommerceweb.repository.ProductRepository;
import com.ecommerceweb.repository.UserRepository;
import com.ecommerceweb.service.ProductService;

@Service
public class ProductServiceImpl implements ProductService{

	@Autowired
	ProductRepository productRepository;
	
	@Autowired
	CategoryRepository categoryRepository;
	
	@Autowired
	UserRepository userRepository;
	
	@Autowired
	private ModelMapper modelMapper;
	
	public void ifExist(Long categoryId)
	{
		if(!categoryRepository.existsById(categoryId))
		{
			throw new UnprocessableEntity("Category doesn't exist");
		}
		
		if(!userRepository.existsById(categoryRepository.findById(categoryId).get().getUser().getId()))
		{
			throw new BadInputException("User not exist");
		}
	}
	
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
		ifExist(productDto.getCategoryId());
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
