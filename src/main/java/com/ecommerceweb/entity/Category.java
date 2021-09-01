package com.ecommerceweb.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class Category {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String name;
	private String desciption;
	
	@ManyToOne
	private User user;
	
	public Category() {
		super();
	}
	public Category(Long id, String name, String desciption) {
		super();
		this.id = id;
		this.name = name;
		this.desciption = desciption;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDesciption() {
		return desciption;
	}
	public void setDesciption(String desciption) {
		this.desciption = desciption;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public Category(Long id, String name, String desciption, User user) {
		super();
		this.id = id;
		this.name = name;
		this.desciption = desciption;
		this.user = user;
	}
	@Override
	public String toString() {
		return "Category [id=" + id + ", name=" + name + ", desciption=" + desciption + ", user=" + user + "]";
	}
	
}
