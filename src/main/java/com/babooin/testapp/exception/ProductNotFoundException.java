package com.babooin.testapp.exception;

public class ProductNotFoundException extends ProductException {
	
	private static String msg = "Product not found. Serial No.: ";
	
	public ProductNotFoundException(String serial) {
		super(msg + serial);
	}

}
