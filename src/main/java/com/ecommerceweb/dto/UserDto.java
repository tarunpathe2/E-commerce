package com.ecommerceweb.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.ecommerceweb.entity.UserProfile;

public class UserDto {

	private Long id;

	@NotBlank
	private String email;

	@NotNull
	private UserProfile userProfile;

	@NotNull
	private String role;

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public UserProfile getUserProfile() {
		return userProfile;
	}

	public void setUserProfile(UserProfile userProfile) {
		this.userProfile = userProfile;
	}

}
