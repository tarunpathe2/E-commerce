package com.ecommerceweb.controller;

import java.sql.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ecommerceweb.dto.OrderTrackDto;
import com.ecommerceweb.dto.OrdersDto;
import com.ecommerceweb.service.OrderService;
import com.ecommerceweb.service.OrderTrackService;

@RestController
@RequestMapping("/e-commerce/orders/")
public class OrderController {

	@Autowired
	private OrderService orderService;
	
	@Autowired
	private OrderTrackService orderTrackService;

	Logger logger = LoggerFactory.getLogger(OrderController.class);

	//Admin API

	@GetMapping
	public ResponseEntity<List<OrdersDto>> getAllOrder() {
		logger.info("getAllOrder method started");
		return new ResponseEntity<List<OrdersDto>>(orderService.getAllOrders(), HttpStatus.OK);
	}

	@GetMapping("date/{start}/{end}")
	public ResponseEntity<List<OrdersDto>> getAllOrderByDates(@PathVariable Date start, @PathVariable Date end) {
		logger.info("getAllOrderByDates method started");
		return new ResponseEntity<List<OrdersDto>>(orderService.getOrdersBetween(start, end), HttpStatus.OK);
	}


	@PutMapping("{orderId}/{userId}/{status}")
	public ResponseEntity<OrdersDto> updateOrderStatus( @PathVariable Long orderId, @PathVariable Long userId,
			@PathVariable String status) {
		logger.info("updateOrderStatus method started");
		return new ResponseEntity<OrdersDto>(orderService.updateOrderStatus( orderId, userId,status), HttpStatus.OK);

	}

	@GetMapping("totalAmountBetween/{start}/{end}")
	public ResponseEntity<Integer> getAllAmountsByDates(@PathVariable Date start, @PathVariable Date end) {
		logger.info("getAllAmountsByDates method started");
		return new ResponseEntity<Integer>(orderService.totalProductSold(start, end), HttpStatus.OK);
	}

	//User API 
	
	@PostMapping("{userId}/{productId}")
	public ResponseEntity<OrdersDto> createOrder(@RequestBody OrdersDto orderDto, @PathVariable Long userId,
			@PathVariable Long productId) {
		logger.info("crreateOrder method started");
		return new ResponseEntity<OrdersDto>(orderService.addOrders(orderDto, productId, userId), HttpStatus.OK);
	}

	@PutMapping("updateOrder/{productId}/{userId}")
	public ResponseEntity<OrdersDto> updateOrder(@RequestBody OrdersDto orderDto, @PathVariable Long userId, @PathVariable Long productId) {
		logger.info("UpdateOrder method started");
		return new ResponseEntity<OrdersDto>(orderService.updateOrders(productId,userId,orderDto), HttpStatus.OK);
	}

	@GetMapping("{id}")
	public ResponseEntity<OrdersDto> getOrder(@PathVariable Long id) {
		logger.info("getOrder method started");
		return new ResponseEntity<OrdersDto>(orderService.getOrder(id), HttpStatus.OK);
	}
	
	@GetMapping("users/{userId}")
	public ResponseEntity<List<OrdersDto>> getAllOrdersByUserId(@PathVariable Long userId) {
		logger.info("getAllOrdersByUserId method started");
		return new ResponseEntity<List<OrdersDto>>(orderService.getUserOrders(userId), HttpStatus.OK);
	}
	
	@GetMapping("orderTrack/{id}")
	public ResponseEntity<OrderTrackDto> getorderTrack(@PathVariable Long id)
	{
		logger.info("getorderTrack method started");
		return new ResponseEntity<OrderTrackDto> (orderTrackService.getOrderTrack(id),HttpStatus.OK);	
	}
	
	

}
