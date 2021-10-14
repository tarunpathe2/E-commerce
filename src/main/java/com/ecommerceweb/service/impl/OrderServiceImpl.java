package com.ecommerceweb.service.impl;

import java.sql.Date;
import java.util.Calendar;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ecommerceweb.constants.ConstantMsg;
import com.ecommerceweb.dto.OrderTrackDto;
import com.ecommerceweb.dto.OrdersDto;
import com.ecommerceweb.dto.ProductDto;
import com.ecommerceweb.dto.UserDto;
import com.ecommerceweb.entity.OrderTrack;
import com.ecommerceweb.entity.Orders;
import com.ecommerceweb.entity.User;
import com.ecommerceweb.exception.UnprocessableEntity;
import com.ecommerceweb.repository.OrderTrackRepository;
import com.ecommerceweb.repository.OrdersRepository;
import com.ecommerceweb.repository.ProductRepository;
import com.ecommerceweb.repository.UserRepository;
import com.ecommerceweb.service.OrderService;
import com.ecommerceweb.service.OrderTrackService;
import com.ecommerceweb.service.ProductService;
import com.ecommerceweb.service.UserService;

@Service
public class OrderServiceImpl implements OrderService {

	@Autowired
	OrdersRepository orderRepo;

	@Autowired
	UserRepository userRepo;

	@Autowired
	UserService userService;

	@Autowired
	ProductRepository productRepository;

	@Autowired
	OrderTrackService orderTrackService;

	@Autowired
	OrderTrackRepository orderTrackRepository;

	@Autowired
	ProductService productService;

	@Autowired
	private ModelMapper modelMapper;

	Logger logger = LoggerFactory.getLogger(OrderServiceImpl.class);
	
	private Date date = new Date(Calendar.getInstance().getTime().getTime());
	
	@Override
	public List<OrdersDto> getAllOrders() {
		logger.info("getAllOrders  method in OrderServiceImpl started");
		List<Orders> orders = orderRepo.findAll();
		List<OrdersDto> ordersDto = orders.stream().map(od -> modelMapper.map(od, OrdersDto.class))
				.collect(Collectors.toList());
		logger.info("getAllOrders  method in OrderServiceImpl ended");
		return ordersDto;
	}

	@Override
	public OrdersDto getOrder(Long id) {
		logger.info("getOrders  method in OrderServiceImpl started");
		OrdersDto ordersDto = modelMapper.map(orderRepo.findById(id).get(), OrdersDto.class);
		logger.info("getOrders method in OrderServiceImpl ended");
		return ordersDto;
	}
	
	@Override
	public OrdersDto addOrders(OrdersDto ordersDto, Long productId, Long userId) {

		logger.info("addOrders  method in OrderServiceImpl started");
		ProductDto productDto = productService.getProduct(productId);
		UserDto userDto = userService.getUser(userId);
		ordersDto.setOrderDate(date);
		ordersDto.setUser(userDto);
		ordersDto.setProduct(productDto);
		ordersDto.setAmount(productDto.getPrice() * ordersDto.getQuantity());
		ordersDto.setStatus(ConstantMsg.placed);
		Orders orders = modelMapper.map(ordersDto, Orders.class);
		Orders savedOrder = orderRepo.save(orders);
		OrdersDto savedOrderDto = modelMapper.map(savedOrder, OrdersDto.class);
		if (savedOrder != null) {
			OrderTrackDto orderTrackDto = new OrderTrackDto();
			orderTrackDto.setStatus(ConstantMsg.placed);
			orderTrackDto.setDate(date);
			orderTrackService.addOrderTrack(savedOrderDto.getId(), orderTrackDto);
		}
		logger.info("addOrders  method in OrderServiceImpl ended");
		return savedOrderDto;
	}

	@Override
	public List<OrdersDto> getOrdersBetween(Date start, Date end) {
		logger.info("getOrdersBetween  method in OrderServiceImpl started");
		List<Orders> orders = orderRepo.findByOrderDateBetween(start, end);
		List<OrdersDto> ordersDto = orders.stream().map(od -> modelMapper.map(od, OrdersDto.class))
				.collect(Collectors.toList());
		logger.info("getOrdersBetween  method in OrderServiceImpl ended");
		return ordersDto;
	}

	@Override
	public int totalProductSold(Date start, Date end) {
		logger.info("totalProductSold  method in OrderServiceImpl started");
		int amount = 0;
		List<OrderTrack> orderTrack = (List<OrderTrack>) orderTrackRepository.findByDateBetween(start, end);
		int size = orderTrack.size();
		for (int i = 0; i < size; i++) {
			if (orderTrack.get(i).getStatus().equals(ConstantMsg.placed)) {
				amount = (int) (amount + (orderTrack.get(i).getOrders().getAmount()));
			}
		}
		logger.info("totalProductSold  method in OrderServiceImpl started");
		return amount;
	}
	
	@Override
	public List<OrdersDto> getUserOrders(Long id) {
		logger.info("getUserOrders  method in OrderServiceImpl started");
		List<Orders> order = (List<Orders>) orderRepo.findAllByUserId(id);
		List<OrdersDto> orderDto = order.stream().map(user -> modelMapper.map(user, OrdersDto.class))
				.collect(Collectors.toList());
		logger.info("getUserOrders  method in OrderServiceImpl ended");
		return orderDto;
	}


	@Override
	public OrdersDto updateOrders(Long productId, Long userId, OrdersDto ordersDto) {
		logger.info("updateOrders  method in OrderServiceImpl started");
		if (ordersDto.getStatus().equals(ConstantMsg.confirm) || ordersDto.getStatus().equals(ConstantMsg.delivered)
				|| ordersDto.getStatus().equals(ConstantMsg.cancel)) {
			throw new UnprocessableEntity(ConstantMsg.isInvalid);
		}	
		ProductDto productDto = productService.getProduct(productId);
		UserDto userDto = userService.getUser(userId);
		ordersDto.setOrderDate(date);
		ordersDto.setUser(userDto);
		ordersDto.setProduct(productDto);
		ordersDto.setAmount(productDto.getPrice() * ordersDto.getQuantity());
		ordersDto.setStatus(ConstantMsg.placed);
		Orders orders = modelMapper.map(ordersDto, Orders.class);
		Orders savedOrder = orderRepo.save(orders);
		OrdersDto savedOrderDto = modelMapper.map(savedOrder, OrdersDto.class);
		logger.info("updateOrders  method in OrderServiceImpl ended");
		return savedOrderDto;
	}

	@Override
	public OrdersDto updateOrderStatus(Long orderId, Long userId, String status) {
		logger.info("updateOrderStatus  method in OrderServiceImpl started");
		OrdersDto orderDto = getOrder(orderId);
		if (status.equalsIgnoreCase(ConstantMsg.confirm)) {
			orderDto.setStatus(ConstantMsg.confirm);
		}
		if (status.equalsIgnoreCase(ConstantMsg.delivered)) {
			orderDto.setStatus(ConstantMsg.delivered);
		}
		if (status.equalsIgnoreCase(ConstantMsg.cancel)) {
			orderDto.setStatus(ConstantMsg.cancel);
		}
		Orders order = modelMapper.map(orderDto, Orders.class);
		Orders savedOrder = orderRepo.save(order);
		OrdersDto savedOrdersDto = modelMapper.map(savedOrder, OrdersDto.class);
		if (savedOrder != null) {
			OrderTrackDto orderTrackDto = new OrderTrackDto();
			orderTrackDto.setStatus(savedOrdersDto.getStatus());
			orderTrackDto.setDate(date);
			orderTrackService.addOrderTrack(savedOrdersDto.getId(), orderTrackDto);
		}
		logger.info("updateOrderStatus  method in OrderServiceImpl ended");
		return savedOrdersDto;
	}

}
