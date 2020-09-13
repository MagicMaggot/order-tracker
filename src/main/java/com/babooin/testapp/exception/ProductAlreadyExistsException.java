package com.babooin.testapp.exception;

public class ProductAlreadyExistsException extends ProductException {
	
	private static String msg = "Product already exists. Serial No.: ";
	
	public ProductAlreadyExistsException(String serial) {
		super(msg + serial);
	}
	
	
}
