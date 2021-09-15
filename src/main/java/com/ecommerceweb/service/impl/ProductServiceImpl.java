package com.ecommerceweb.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ecommerceweb.constants.ConstantMsg;
import com.ecommerceweb.dto.CategoryDto;
import com.ecommerceweb.dto.ProductDto;
import com.ecommerceweb.dto.UserDto;
import com.ecommerceweb.entity.Products;
import com.ecommerceweb.exception.UnprocessableEntity;
import com.ecommerceweb.repository.ProductRepository;
import com.ecommerceweb.service.CategoryService;
import com.ecommerceweb.service.ProductService;
import com.ecommerceweb.service.UserService;

@Service
public class ProductServiceImpl implements ProductService{

	@Autowired
	ProductRepository productRepository;
	
	@Autowired
	UserService userService;
	
	@Autowired
	CategoryService categoryService;
	
	@Autowired
	private ModelMapper modelMapper;
	
	@Override
	public List<ProductDto> getAllProduct()
	{
		List<Products> product = productRepository.findAll();
		List<ProductDto> productDto = product.stream().
				map(user -> modelMapper.map(user, ProductDto.class)).collect(Collectors.toList());
		
		return productDto;		
	}
	
	@Override
	public ProductDto getProduct(Long id)
	{
		return modelMapper.map(productRepository.findById(id).get(), ProductDto.class);
	}
	
	@Override
	public ProductDto addProduct(ProductDto productDto,Long userId,Long categoryId)
	{
		UserDto userDto = userService.getUser(userId);
		if(userDto.getRole()!=0)
		{
			throw new UnprocessableEntity(ConstantMsg.isInvalid);
		}
		CategoryDto categoryDto = categoryService.getCategory(categoryId);
		productDto.setUser(userDto);
		productDto.setCategory(categoryDto);
		Products products = modelMapper.map(productDto, Products.class);
		Products savedProduct =  productRepository.save(products);
		ProductDto savedProductDto = modelMapper.map(savedProduct, ProductDto.class);
		return savedProductDto;
	}
	
	@Override
	public ProductDto updateProduct(ProductDto productDto)
	{
		Products products = modelMapper.map(productDto, Products.class);
		Products update = productRepository.save(products);
		ProductDto updatedProduct = modelMapper.map(update, ProductDto.class);
		return updatedProduct;
	}
	
	@Override
	public void deleteProduct(Long id)
	{
		productRepository.deleteById(id);
	}
}
