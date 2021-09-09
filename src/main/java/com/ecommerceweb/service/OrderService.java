package com.ecommerceweb.service;

import java.sql.Date;
import java.util.List;

import com.ecommerceweb.dto.OrdersDto;

public interface OrderService {

	public List<OrdersDto> getAllOrders();
	
	public OrdersDto getOrder(Long id);
	
	public OrdersDto addOrders(OrdersDto ordersDto, Long productId,Long UserId);
	
	public List<OrdersDto> getOrdersBetween(Date startDate, Date endDate );
	
	public OrdersDto updateOrders(OrdersDto ordersDto);
	
	public void deleteOrders(Long id);
}
