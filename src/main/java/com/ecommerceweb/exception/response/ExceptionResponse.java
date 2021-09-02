package com.ecommerceweb.exception.response;

import java.util.Date;

public class ExceptionResponse {
	private Date timestamp;
	private String message;
	private String Details;

	public Date getTimestamp() {
		return timestamp;
	}

	public ExceptionResponse(Date timestamp, String message, String details) {
		super();
		this.timestamp = timestamp;
		this.message = message;
		Details = details;
	}

	public String getMessage() {
		return message;
	}

	public String getDetails() {
		return Details;
	}
}
