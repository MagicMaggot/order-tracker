package com.babooin.testapp.exception;

public class OrderedItemNotFoundException extends RuntimeException {
	
	private static String msg = "Item does not exist or belongs to another order. Item id: ";
	
	public OrderedItemNotFoundException(long id) {
		super(msg + id);
	}
	
	public OrderedItemNotFoundException() {
		super("Can not locate item within this order. Wrong item id or product serial");
	}
}
