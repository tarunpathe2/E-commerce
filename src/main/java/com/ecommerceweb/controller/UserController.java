package com.ecommerceweb.controller;

import java.util.List;

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
@RequestMapping("/e-commerce/users")
public class UserController {

	@Autowired
	private UserService userService;

	@GetMapping("{id}")
	public ResponseEntity<UserDto> getUser(@PathVariable Long id) {
		return new ResponseEntity<UserDto>(userService.getUser(id), HttpStatus.OK);
	}

	@PostMapping
	public ResponseEntity<UserDto> addUser(@RequestBody UserDto userDto) {
		return new ResponseEntity<UserDto>(userService.addUser(userDto), HttpStatus.OK);
	}

	@GetMapping
	public ResponseEntity<List<UserDto>> getAllUser() {
		return new ResponseEntity<List<UserDto>>(userService.getAllUser(), HttpStatus.OK);
	}

	@DeleteMapping("{id}")
	public ResponseEntity<UserDto> deleteUser(Long id) {
		userService.deleteUser(id);
		return new ResponseEntity<UserDto>(HttpStatus.OK);
	}

}
