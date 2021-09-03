package com.ecommerceweb.service.impl;

import java.sql.Date;
import java.util.Calendar;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ecommerceweb.dto.OrdersDto;
import com.ecommerceweb.entity.Orders;
import com.ecommerceweb.entity.User;
import com.ecommerceweb.exception.BadInputException;
import com.ecommerceweb.exception.UnprocessableEntity;
import com.ecommerceweb.repository.OrdersRepository;
import com.ecommerceweb.repository.UserRepository;
import com.ecommerceweb.service.OrderService;

@Service
public class OrderServiceImpl implements OrderService {

	@Autowired
	OrdersRepository orderRepo;
	
	@Autowired
	UserRepository userRepo;
	
	@Autowired
	private ModelMapper modelMapper;
	
	public boolean isExist(Long id)
	{
		return userRepo.existsById(id);
	}
	
	private Date date = new Date(Calendar.getInstance().getTime().getTime());
	
	@Override
	public List<OrdersDto> getAllOrders()
	{
		List<Orders> orders = orderRepo.findAll();
		List<OrdersDto> ordersDto = orders.stream().
				map(od -> modelMapper.map(od , OrdersDto.class)).collect(Collectors.toList());
		return ordersDto;
	}
	
	@Override
	public OrdersDto getOrder(Long id)
	{
		return modelMapper.map(orderRepo.findById(id).get(), OrdersDto.class);
	}
	
	@Override
	public OrdersDto addOrders(OrdersDto ordersDto)
	{
		if(!isExist(ordersDto.getUserId()))
		{
			throw new BadInputException("");
		}
		Orders orders = modelMapper.map(ordersDto, Orders.class);
		orders.setDate(date);
		orderRepo.save(orders);
		return modelMapper.map(orders, OrdersDto.class);
	}
	
	@Override
	public OrdersDto updateOrders(OrdersDto ordersDto)
	{
		if(!isExist(ordersDto.getUserId()))
		{
			throw new BadInputException("User not exist");
		}
		
		User user = userRepo.findById(ordersDto.getUserId()).get();
		if (user.getRole() == 0) {
			throw new UnprocessableEntity("User cannot process");
		}
		
		Orders orders = modelMapper.map(ordersDto, Orders.class);
		orderRepo.save(orders);
		return modelMapper.map(orders, OrdersDto.class);
	}
	
	@Override
	public void deleteOrders(Long id)
	{
		orderRepo.deleteById(id);
	}
}
