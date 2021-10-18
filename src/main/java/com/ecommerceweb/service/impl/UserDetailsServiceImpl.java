package com.ecommerceweb.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.ecommerceweb.entity.CustomUserDetail;
import com.ecommerceweb.entity.User;
import com.ecommerceweb.repository.UserRepository;

public class UserDetailsServiceImpl implements UserDetailsService{

	@Autowired
	private UserRepository userRepository;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		User user = userRepository.getUserByEmail(username);
		
		if(user==null)
		{
			throw new UsernameNotFoundException("Could not found user");
		}
		
		CustomUserDetail customUserDetail = new CustomUserDetail(user);
			
		return customUserDetail;
	}

	
}
