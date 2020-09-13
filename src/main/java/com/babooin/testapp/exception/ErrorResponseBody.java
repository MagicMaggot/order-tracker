package com.babooin.testapp.exception;

import org.springframework.http.HttpStatus;

public class ErrorResponseBody {
	
	private int response;
	private String message;
	private long timestamp;
	
	public ErrorResponseBody() {
	}
	
	public ErrorResponseBody(Exception e, HttpStatus status) {
		response = status.value();
		message = e.getMessage();
		timestamp = System.currentTimeMillis();
	}

	public ErrorResponseBody(int response, String message, long timestamp) {
		this.response = response;
		this.message = message;
		this.timestamp = timestamp;
	}

	public int getResponse() {
		return response;
	}

	public void setResponse(int response) {
		this.response = response;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public long getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(long timestamp) {
		this.timestamp = timestamp;
	}

}
