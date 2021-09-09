package com.ecommerceweb.service.impl;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ecommerceweb.dto.OrderTrackDto;
import com.ecommerceweb.entity.OrderTrack;
import com.ecommerceweb.entity.Orders;
import com.ecommerceweb.repository.OrderTrackRepository;
import com.ecommerceweb.repository.OrdersRepository;

@Service
public class OrderTrackServiceImpl {

	@Autowired
	OrderTrackRepository orderTrackRepo;
	
	@Autowired
	OrdersRepository orderRepo;
	
	@Autowired
	private ModelMapper modelMapper;
	
	public boolean isExist(Long id)
	{
		return orderTrackRepo.existsById(id);
	}
	
	public OrderTrackDto addOrderTrack(Long orderId, OrderTrackDto orderTrackDto)
	{
		Orders orders = orderRepo.getById(orderId);
		orderTrackDto.setOrders(orders); 
		OrderTrack orderTrack = modelMapper.map(orderTrackDto, OrderTrack.class);
		orderTrackRepo.save(orderTrack);
		return modelMapper.map(orderTrack, OrderTrackDto.class);
	}
	
	public OrderTrackDto getOrderTrack(Long id)
	{
		return modelMapper.map(orderTrackRepo.findById(id).get(), OrderTrackDto.class);	
	}
	
}
