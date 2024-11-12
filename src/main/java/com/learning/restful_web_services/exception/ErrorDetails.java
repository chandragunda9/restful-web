package com.learning.restful_web_services.exception;

import java.time.LocalDateTime;

public class ErrorDetails {

	private LocalDateTime timestamp;
	private String message;
	private String description;

	public ErrorDetails(LocalDateTime now, String message, String description) {
		this.timestamp = now;
		this.message = message;
		this.description = description;
	}

	public LocalDateTime getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(LocalDateTime timestamp) {
		this.timestamp = timestamp;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
	
}
