package com.ecommerceweb.service.impl;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ecommerceweb.dto.OrderTrackDto;
import com.ecommerceweb.dto.OrdersDto;
import com.ecommerceweb.entity.OrderTrack;
import com.ecommerceweb.entity.Orders;
import com.ecommerceweb.repository.OrderTrackRepository;
import com.ecommerceweb.repository.OrdersRepository;
import com.ecommerceweb.service.OrderService;
import com.ecommerceweb.service.OrderTrackService;

@Service
public class OrderTrackServiceImpl implements OrderTrackService{

	@Autowired
	OrderTrackRepository orderTrackRepo;
	
	@Autowired
	OrderService orderService;
	
	@Autowired
	OrdersRepository orderRepo;
	
	@Autowired
	private ModelMapper modelMapper;
	
	public boolean isExist(Long id)
	{
		return orderTrackRepo.existsById(id);
	}
	
	@Override
	public OrderTrackDto addOrderTrack(Long orderId, OrderTrackDto orderTrackDto)
	{
		OrdersDto ordersDto = orderService.getOrder(orderId);
		orderTrackDto.setOrdersDto(ordersDto); 
		OrderTrack orderTrack = modelMapper.map(orderTrackDto, OrderTrack.class);
		OrderTrack savedOrderTrack = orderTrackRepo.save(orderTrack);
		OrderTrackDto savedOrderTrackDto = modelMapper.map(savedOrderTrack, OrderTrackDto.class);
		return savedOrderTrackDto;
	}
	
	@Override
	public OrderTrackDto getOrderTrack(Long id)
	{
		return modelMapper.map(orderTrackRepo.findById(id).get(), OrderTrackDto.class);	
	}
	
}
