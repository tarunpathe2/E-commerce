package com.ecommerceweb.service.impl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ecommerceweb.constants.ConstantMsg;
import com.ecommerceweb.controller.UserController;
import com.ecommerceweb.dto.UserDto;
import com.ecommerceweb.entity.User;
import com.ecommerceweb.exception.DataNotFoundException;
import com.ecommerceweb.repository.UserRepository;
import com.ecommerceweb.service.UserService;

@Service
public class UserServiceImpl implements UserService{

	@Autowired
	private UserRepository userRepo;
	
	@Autowired
	private ModelMapper modelMapper;

	Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);
	
	@Override
	public UserDto addUser(UserDto userDto) 
	{
		logger.info("addUser method in UserServiceImpl started");
		User user = modelMapper.map(userDto, User.class);
		userRepo.save(user);
		return modelMapper.map(user, UserDto.class);
	}
	
	@Override
	public List<UserDto> getAllUser()
	{
		logger.info("getAllUser method in UserServiceImpl started");
		List<User> users = userRepo.findAll();
		List<UserDto> userDto = users.stream().map(user -> modelMapper.map(user, UserDto.class))
				.collect(Collectors.toList());
		logger.info("getAllUser method in UserServiceImpl ended");
		return userDto;
	}
	
	@Override
	public UserDto getUser(Long id)
	{
		logger.info("getUser method in UserServiceImpl started");
		Optional<User> userOptional = userRepo.findById(id);
		if(!userOptional.isPresent())
		{
			throw new DataNotFoundException(ConstantMsg.notFound);
		}
		UserDto userDto = modelMapper.map(userOptional.get(), UserDto.class);	
		logger.info("getUser method in UserServiceImpl ended");
		return userDto;		
	}
	
	@Override
	public void deleteUser(Long id)
	{
		logger.info("deleteUser method in UserServiceImpl started");
		userRepo.deleteById(id);
		logger.info("deleteUser method in UserServiceImpl ended");
	}
}
