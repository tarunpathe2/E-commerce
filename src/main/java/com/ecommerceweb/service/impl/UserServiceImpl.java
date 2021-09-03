package com.ecommerceweb.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ecommerceweb.dto.UserDto;
import com.ecommerceweb.entity.User;
import com.ecommerceweb.repository.UserRepository;
import com.ecommerceweb.service.UserService;

@Service
public class UserServiceImpl implements UserService{

	@Autowired
	private UserRepository userRepo;
	
	@Autowired
	private ModelMapper modelMapper;

	@Override
	public UserDto addUser(UserDto userDto) 
	{
		User user = modelMapper.map(userDto, User.class);
		userRepo.save(user);
		return modelMapper.map(user, UserDto.class);
	}
	
	@Override
	public List<UserDto> getAllUser()
	{
		List<User> users = userRepo.findAll();
		List<UserDto> userDto = users.stream().map(user -> modelMapper.map(user, UserDto.class))
				.collect(Collectors.toList());
		return userDto;
	}
	
	@Override
	public UserDto getUser(Long id)
	{
		return modelMapper.map(userRepo.findById(id).get(), UserDto.class);		
	}
	
	@Override
	public void deleteUser(Long id)
	{
		userRepo.deleteById(id);
	}
}
