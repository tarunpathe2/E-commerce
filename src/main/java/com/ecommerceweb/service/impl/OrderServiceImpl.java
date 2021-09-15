package com.ecommerceweb.service.impl;

import java.sql.Date;
import java.util.Calendar;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ecommerceweb.constants.ConstantMsg;
import com.ecommerceweb.dto.OrderTrackDto;
import com.ecommerceweb.dto.OrdersDto;
import com.ecommerceweb.dto.ProductDto;
import com.ecommerceweb.dto.UserDto;
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

	public boolean isExist(User user) {
		return userRepo.existsById(user.getId());
	}

	private Date date = new Date(Calendar.getInstance().getTime().getTime());

	@Override
	public List<OrdersDto> getAllOrders() {
		List<Orders> orders = orderRepo.findAll();
		List<OrdersDto> ordersDto = orders.stream().map(od -> modelMapper.map(od, OrdersDto.class))
				.collect(Collectors.toList());
		return ordersDto;
	}

	@Override
	public OrdersDto getOrder(Long id) {
		return modelMapper.map(orderRepo.findById(id).get(), OrdersDto.class);
	}

	@Override
	public OrdersDto addOrders(OrdersDto ordersDto, Long productId, Long userId) {

		ProductDto productDto = productService.getProduct(productId);
		UserDto userDto = userService.getUser(userId);
		ordersDto.setOrderDate(date);
		ordersDto.setUser(userDto);
		ordersDto.setAmount(productDto.getPrice()*ordersDto.getQuantity());
		ordersDto.setStatus(ConstantMsg.placed);
		Orders orders = modelMapper.map(ordersDto, Orders.class);
		Orders savedOrder = orderRepo.save(orders);
		OrdersDto savedOrderDto = modelMapper.map(savedOrder, OrdersDto.class);
		
		if(savedOrder!=null)
		{
			productDto.setStock(productDto.getStock() - ordersDto.getQuantity());
			productService.updateProduct(productDto);
			OrderTrackDto orderTrackDto = new OrderTrackDto();
			orderTrackDto.setStatus(ConstantMsg.placed);
			orderTrackDto.setDate(date);
			orderTrackService.addOrderTrack(savedOrderDto.getId(), orderTrackDto);
		}
		
		return savedOrderDto;
	}

	@Override
	public List<OrdersDto> getOrdersBetween(Date start, Date end) {
		List<Orders> orders = orderRepo.findByOrderDateBetween(start, end);
		List<OrdersDto> ordersDto = orders.stream().map(od -> modelMapper.map(od, OrdersDto.class))
				.collect(Collectors.toList());
		return ordersDto;
	}

	@Override
	public OrdersDto updateOrders(OrdersDto ordersDto) {
		User user = userRepo.findById(ordersDto.getId()).get();
		if (user.getRole() == 0) {
			throw new UnprocessableEntity(ConstantMsg.isInvalid);
		}

		Orders orders = modelMapper.map(ordersDto, Orders.class);
		orderRepo.save(orders);
		return modelMapper.map(orders, OrdersDto.class);
	}

	@Override
	public void deleteOrders(Long id) {
		orderRepo.deleteById(id);
	}

}
