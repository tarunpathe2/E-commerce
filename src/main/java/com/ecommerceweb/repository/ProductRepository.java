package com.ecommerceweb.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ecommerceweb.entity.Products;

@Repository
public interface ProductRepository extends JpaRepository<Products, Long>{

	public List<Products> findAll();
	public void deleteById(Long id);
}
