package com.ecommerceweb.service;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ecommerceweb.dto.UserDto;
import com.ecommerceweb.entity.User;
import com.ecommerceweb.repository.UserRepository;

@Service
public class UserService {

	@Autowired
	private UserRepository userRepo;
	
	@Autowired
	private ModelMapper modelMapper;

	public UserDto addUser(UserDto userDto) {
		User user = modelMapper.map(userDto, User.class);
		userRepo.save(user);
		return modelMapper.map(user, UserDto.class);
	}
	
	public List<UserDto> getAllUser()
	{
		List<User> users = userRepo.findAll();
		List<UserDto> userDto = users.stream().map(user -> modelMapper.map(user, UserDto.class))
				.collect(Collectors.toList());
		return userDto;
	}
	
	public UserDto getUser(Long id)
	{
		return modelMapper.map(userRepo.findById(id).get(), UserDto.class);		
	}
	
	public void deleteUser(Long id)
	{
		userRepo.deleteById(id);
	}
}
