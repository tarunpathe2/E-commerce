package com.ecommerceweb.service;

import java.util.List;

import com.ecommerceweb.dto.OrdersDto;

public interface OrderService {

	public List<OrdersDto> getAllOrders();
	
	public OrdersDto getOrder(Long id);
	
	public OrdersDto addOrders(OrdersDto ordersDto, Long productId);
	
	public OrdersDto updateOrders(OrdersDto ordersDto);
	
	public void deleteOrders(Long id);
}
