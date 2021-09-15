package com.ecommerceweb.dto;

import java.sql.Date;

public class OrderTrackDto {

	private Long id;
	private String status;
	private String details;
	private Date date;
	
	private OrdersDto ordersDto;

	public OrdersDto getOrdersDto() {
		return ordersDto;
	}

	public void setOrdersDto(OrdersDto ordersDto) {
		this.ordersDto = ordersDto;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getDetails() {
		return details;
	}

	public void setDetails(String details) {
		this.details = details;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}
	
}
