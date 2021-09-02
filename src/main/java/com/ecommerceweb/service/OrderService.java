package com.ecommerceweb.service;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ecommerceweb.dto.OrdersDto;
import com.ecommerceweb.entity.Orders;
import com.ecommerceweb.repository.OrdersRepository;

@Service
public class OrderService {

	@Autowired
	OrdersRepository orderRepo;
	
	@Autowired
	private ModelMapper modelMapper;
	
	public List<OrdersDto> getAllOrders()
	{
		List<Orders> orders = orderRepo.findAll();
		List<OrdersDto> ordersDto = orders.stream().
				map(user -> modelMapper.map(user, OrdersDto.class)).collect(Collectors.toList());
		return ordersDto;
	}
	
	public OrdersDto getOrder(Long id)
	{
		return modelMapper.map(orderRepo.findById(id).get(), OrdersDto.class);
	}
	
	public OrdersDto addOrders(OrdersDto ordersDto)
	{
		Orders orders = modelMapper.map(ordersDto, Orders.class);
		orderRepo.save(orders);
		return modelMapper.map(orders, OrdersDto.class);
	}
	
	public OrdersDto updateOrders(OrdersDto ordersDto)
	{
		Orders orders = modelMapper.map(ordersDto, Orders.class);
		orderRepo.save(orders);
		return modelMapper.map(orders, OrdersDto.class);
	}
	
	public void deleteOrders(Long id)
	{
		orderRepo.deleteById(id);
	}
}
