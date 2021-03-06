package com.ecommerceweb.repository;

import java.sql.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ecommerceweb.entity.Orders;

public interface OrdersRepository extends JpaRepository<Orders, Long>{
	public List<Orders> findAllByUserId(Long id);
	public List<Orders> findByOrderDateBetween(Date start,Date end); 
}