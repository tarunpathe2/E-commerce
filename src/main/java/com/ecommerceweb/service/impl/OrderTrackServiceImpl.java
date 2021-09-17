package com.ecommerceweb.service.impl;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ecommerceweb.dto.OrderTrackDto;
import com.ecommerceweb.dto.OrdersDto;
import com.ecommerceweb.entity.OrderTrack;
import com.ecommerceweb.repository.OrderTrackRepository;
import com.ecommerceweb.service.OrderService;
import com.ecommerceweb.service.OrderTrackService;

@Service
public class OrderTrackServiceImpl implements OrderTrackService {

	Logger logger = LoggerFactory.getLogger(OrderTrackServiceImpl.class);
	
	@Autowired
	OrderTrackRepository orderTrackRepo;

	@Autowired
	OrderService orderService;

	@Autowired
	private ModelMapper modelMapper;

	@Override
	public OrderTrackDto addOrderTrack(Long orderId, OrderTrackDto orderTrackDto) {
		logger.info("addOrderTrack method in OrderTrackServiceImpl started");
		OrdersDto ordersDto = orderService.getOrder(orderId);
		orderTrackDto.setOrdersDto(ordersDto);
		OrderTrack orderTrack = modelMapper.map(orderTrackDto, OrderTrack.class);
		OrderTrack savedOrderTrack = orderTrackRepo.save(orderTrack);
		OrderTrackDto savedOrderTrackDto = modelMapper.map(savedOrderTrack, OrderTrackDto.class);
		logger.info("addOrderTrack method in OrderTrackServiceImpl ended");
		return savedOrderTrackDto;
	}

	@Override
	public OrderTrackDto getOrderTrack(Long id) {
		logger.info("getOrderTrack method in OrderTrackServiceImpl started");
		OrderTrackDto orderTrackDto = modelMapper.map(orderTrackRepo.findById(id).get(), OrderTrackDto.class);
		logger.info("getOrderTrack method in OrderTrackServiceImpl ended");
		return orderTrackDto;

	}

}
