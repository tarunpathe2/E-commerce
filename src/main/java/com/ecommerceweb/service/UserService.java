package com.ecommerceweb.service;

import java.util.List;

import com.ecommerceweb.dto.UserDto;

public interface UserService{

	public UserDto addUser(UserDto userDto);
	
	public List<UserDto> getAllUser();
	
	public UserDto getUser(Long id);
	
	public void deleteUser(Long id);
}
