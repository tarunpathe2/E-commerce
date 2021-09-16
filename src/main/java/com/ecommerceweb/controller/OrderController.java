package com.ecommerceweb.controller;

import java.sql.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ecommerceweb.dto.OrdersDto;
import com.ecommerceweb.service.OrderService;

@RestController
@RequestMapping("/e-commerce/orders/")
public class OrderController {

	@Autowired
	private OrderService orderService;

	Logger logger = LoggerFactory.getLogger(OrderController.class);

	@PostMapping("{userId}/{productId}")
	public ResponseEntity<OrdersDto> createOrder(@RequestBody OrdersDto orderDto, @PathVariable Long userId,
			@PathVariable Long productId) {
		System.out.println(productId + ", userId =" + userId);
		logger.info(productId + ", userId =" + userId);
		return new ResponseEntity<OrdersDto>(orderService.addOrders(orderDto, productId, userId), HttpStatus.OK);
	}

	@PutMapping("user/{productId}/{userId}")
	public ResponseEntity<OrdersDto> updateOrderUser(@RequestBody OrdersDto orderDto, @PathVariable Long userId, @PathVariable Long productId) {
		return new ResponseEntity<OrdersDto>(orderService.updateOrders(productId,userId,orderDto), HttpStatus.OK);
	}

	@GetMapping("{id}")
	public ResponseEntity<OrdersDto> getOrder(@PathVariable Long id) {
		return new ResponseEntity<OrdersDto>(orderService.getOrder(id), HttpStatus.OK);
	}

	@GetMapping
	public ResponseEntity<List<OrdersDto>> getAllOrder() {
		return new ResponseEntity<List<OrdersDto>>(orderService.getAllOrders(), HttpStatus.OK);
	}

	@GetMapping("date/{start}/{end}")
	public ResponseEntity<List<OrdersDto>> getAllOrderByDates(@PathVariable Date start, @PathVariable Date end) {
		return new ResponseEntity<List<OrdersDto>>(orderService.getOrdersBetween(start, end), HttpStatus.OK);
	}

	@GetMapping("totalAmountBetween/{start}/{end}")
	public ResponseEntity getAllAmountsByDates(@PathVariable Date start, @PathVariable Date end) {
		return new ResponseEntity(orderService.totalProductSold(start, end), HttpStatus.OK);
	}

	@PutMapping("{orderId}/{userId}/{status}")
	public ResponseEntity<OrdersDto> updateOrder( @PathVariable Long orderId, @PathVariable Long userId,
			@PathVariable String status) {

		return new ResponseEntity<OrdersDto>(orderService.updateOrderStatus( orderId, userId,status), HttpStatus.OK);

	}

	@DeleteMapping("{id}")
	public ResponseEntity<OrdersDto> cancelOrder(@PathVariable Long id) {
		orderService.deleteOrders(id);
		return new ResponseEntity<OrdersDto>(HttpStatus.OK);
	}

}
