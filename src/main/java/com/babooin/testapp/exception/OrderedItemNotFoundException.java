package com.babooin.testapp.exception;

public class OrderedItemNotFoundException extends RuntimeException {
	
	private static String msg = "Ordered item does not exist or belongs to another order. Item id: ";
	
	public OrderedItemNotFoundException(long id) {
		super(msg + id);
	}
}
