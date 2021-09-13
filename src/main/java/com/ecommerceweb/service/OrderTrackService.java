package com.ecommerceweb.service;

import com.ecommerceweb.dto.OrderTrackDto;

public interface OrderTrackService {

	public OrderTrackDto addOrderTrack(Long orderId, OrderTrackDto orderTrackDto);
	public OrderTrackDto getOrderTrack(Long id);
}
