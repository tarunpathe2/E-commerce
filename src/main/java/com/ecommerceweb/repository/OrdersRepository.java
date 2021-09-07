package com.ecommerceweb.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ecommerceweb.entity.Orders;

public interface OrdersRepository extends JpaRepository<Orders, Long>{
	

}
