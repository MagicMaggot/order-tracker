package com.babooin.testapp.exception;

public class OrderNotFoundException extends OrderException {
	
	private static String msg = "Order not found. Id: ";

	public OrderNotFoundException(long id) {
		super(msg + id);
	}

}
