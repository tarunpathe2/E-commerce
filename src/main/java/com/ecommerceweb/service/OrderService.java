package com.ecommerceweb.service;

import java.sql.Date;
import java.util.List;

import com.ecommerceweb.dto.OrdersDto;

public interface OrderService {

	public List<OrdersDto> getAllOrders();
	
	public OrdersDto getOrder(Long id);
	
	public OrdersDto addOrders(OrdersDto ordersDto, Long productId,Long UserId);
	
	public OrdersDto updateOrderStatus(Long orderId, Long userId,String status);
	
	public List<OrdersDto> getOrdersBetween(Date startDate, Date endDate );
	
	public int totalProductSold(Date start, Date end);
	
	public OrdersDto updateOrders(Long productId, Long userId, OrdersDto ordersDto);
	
	public void deleteOrders(Long id);
}
