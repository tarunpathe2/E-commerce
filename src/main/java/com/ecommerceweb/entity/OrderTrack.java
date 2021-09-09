package com.ecommerceweb.entity;

import java.sql.Date;

import javax.persistence.ManyToOne;

public class OrderTrack {

	private Long id;
	private String status;
	private String details;
	private Date date;
	
	@ManyToOne
	private Orders orders;
	
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
