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
import com.ecommerceweb.entity.Orders;
import com.ecommerceweb.entity.Products;
import com.ecommerceweb.entity.User;
import com.ecommerceweb.exception.BadInputException;
import com.ecommerceweb.exception.UnprocessableEntity;
import com.ecommerceweb.repository.OrdersRepository;
import com.ecommerceweb.repository.ProductRepository;
import com.ecommerceweb.repository.UserRepository;
import com.ecommerceweb.service.OrderService;
import com.ecommerceweb.service.OrderTrackService;

@Service
public class OrderServiceImpl implements OrderService {

	@Autowired
	OrdersRepository orderRepo;
	
	@Autowired
	UserRepository userRepo;
	
	@Autowired
	ProductRepository productRepository;
	
	@Autowired
	OrderTrackService orderTrackService;
	
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
	public OrdersDto addOrders(OrdersDto ordersDto,Long productId,Long UserId)
	{
		if(!isExist(ordersDto.getUserId()))
		{
			throw new BadInputException(ConstantMsg.isInvalid);
		}
		Products products = productRepository.getById(productId);
		User user = userRepo.getById(UserId);
		Orders orders = modelMapper.map(ordersDto, Orders.class);
		orders.setOrderDate(date);
		orders.setUser(user);
		orders.setAmount(products.getPrice()*ordersDto.getQuantity());
		orders.setStatus(ConstantMsg.placed);
		Orders savedOrder = orderRepo.save(orders);
		OrdersDto savedOrderDto = modelMapper.map(savedOrder, OrdersDto.class);
		
		if(savedOrder!=null)
		{
			products.setStock(products.getStock() - orders.getQuantity());
			productRepository.save(products);
			OrderTrackDto orderTrackDto = new OrderTrackDto();
			orderTrackDto.setStatus(ConstantMsg.placed);
			orderTrackDto.setDate(date);
			orderTrackService.addOrderTrack(savedOrderDto.getId(), orderTrackDto);
		}
		
		return savedOrderDto;
	}
	
	@Override
	public List<OrdersDto> getOrdersBetween(Date start, Date end )
	{
		List<Orders> orders = orderRepo.findByOrderDateBetween(start,end);
		List<OrdersDto> ordersDto = orders.stream().
				map(od -> modelMapper.map(od , OrdersDto.class)).collect(Collectors.toList());
		return ordersDto;
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
