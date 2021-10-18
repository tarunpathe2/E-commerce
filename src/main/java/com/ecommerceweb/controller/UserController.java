package com.ecommerceweb.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ecommerceweb.dto.UserDto;
import com.ecommerceweb.service.UserService;

@RestController
@RequestMapping("/user")
public class UserController {

	Logger logger = LoggerFactory.getLogger(UserController.class);

	@Autowired
	private UserService userService;
	
	@GetMapping("{id}")
	public ResponseEntity<UserDto> getUser(@PathVariable Long id) {
		logger.info("getUser method started");
		return new ResponseEntity<UserDto>(userService.getUser(id), HttpStatus.OK);
	}

	@PostMapping
	public ResponseEntity<UserDto> addUser(@RequestBody UserDto userDto) {
		logger.info("addUser method started");
		return new ResponseEntity<UserDto>(userService.addUser(userDto), HttpStatus.OK);
	}

	@GetMapping
	public ResponseEntity<List<UserDto>> getAllUser() {
		logger.info("getAllUser method started");
		return new ResponseEntity<List<UserDto>>(userService.getAllUser(), HttpStatus.OK);
	}

	@DeleteMapping("{id}")
	public ResponseEntity<UserDto> deleteUser(Long id) {
		logger.info("deleteUser method started");
		userService.deleteUser(id);
		return new ResponseEntity<UserDto>(HttpStatus.OK);
	}

}
