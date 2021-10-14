package com.ecommerceweb.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ecommerceweb.constants.ConstantMsg;
import com.ecommerceweb.dto.CategoryDto;
import com.ecommerceweb.dto.ProductDto;
import com.ecommerceweb.dto.UserDto;
import com.ecommerceweb.entity.Products;
import com.ecommerceweb.enums.Role;
import com.ecommerceweb.exception.UnprocessableEntity;
import com.ecommerceweb.repository.ProductRepository;
import com.ecommerceweb.repository.UserRepository;
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
	UserRepository userRepository;
	
	@Autowired
	CategoryService categoryService;
	
	@Autowired
	private ModelMapper modelMapper;
	
	Logger logger = LoggerFactory.getLogger(ProductServiceImpl.class);
	
	@Override
	public List<ProductDto> getAllProduct()
	{
		logger.info("getAllProduct  method in ProductServiceImpl started");
		List<Products> product = productRepository.findAll();
		List<ProductDto> productDto = product.stream().
				map(user -> modelMapper.map(user, ProductDto.class)).collect(Collectors.toList());
		logger.info("getAllProduct  method in ProductServiceImpl ended");
		return productDto;		
	}
	
	@Override
	public ProductDto getProduct(Long id)
	{
		logger.info("getProduct  method in ProductServiceImpl started");
		ProductDto productDto = modelMapper.map(productRepository.findById(id).get(), ProductDto.class);
		logger.info("getProduct  method in ProductServiceImpl ended");
		return productDto;
	}
	
	@Override
	public ProductDto addProduct(ProductDto productDto,Long userId,Long categoryId)
	{
		logger.info("addProduct  method in ProductServiceImpl started");
		UserDto userDto = userService.getUser(userId);
		if(!userRepository.findUserById(userId).getRole().equalsIgnoreCase(Role.ADMIN.toString()))
		{
			throw new UnprocessableEntity(ConstantMsg.isInvalid);
		}
		CategoryDto categoryDto = categoryService.getCategory(categoryId);
		productDto.setUser(userDto);
		productDto.setCategory(categoryDto);
		Products products = modelMapper.map(productDto, Products.class);
		Products savedProduct =  productRepository.save(products);
		ProductDto savedProductDto = modelMapper.map(savedProduct, ProductDto.class);
		logger.info("addProduct  method in ProductServiceImpl ended");
		return savedProductDto;
	}
	
	@Override
	public ProductDto updateProduct(ProductDto productDto,Long userId,Long categoryId)
	{
		logger.info("updateProduct  method in ProductServiceImpl started");
		UserDto userDto = userService.getUser(userId);
		CategoryDto categoryDto = categoryService.getCategory(categoryId);
		productDto.setUser(userDto);
		productDto.setCategory(categoryDto);
		Products products = modelMapper.map(productDto, Products.class);
		Products update = productRepository.save(products);
		ProductDto updatedProduct = modelMapper.map(update, ProductDto.class);
		logger.info("updateProduct  method in ProductServiceImpl ended");
		return updatedProduct;
	}
	
	@Override
	public void deleteProduct(Long id)
	{
		logger.info("deleteProduct  method in ProductServiceImpl started");
		productRepository.deleteById(id);
		logger.info("deleteProduct  method in ProductServiceImpl ended");
	}
}
