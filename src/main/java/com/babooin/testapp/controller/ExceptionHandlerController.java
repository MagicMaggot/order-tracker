package com.babooin.testapp.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import com.babooin.testapp.exception.ErrorResponseBody;
import com.babooin.testapp.exception.OrderNotFoundException;
import com.babooin.testapp.exception.OrderedItemNotFoundException;
import com.babooin.testapp.exception.ProductAlreadyExistsException;
import com.babooin.testapp.exception.ProductNotFoundException;

@ControllerAdvice
public class ExceptionHandlerController {
	
	@ExceptionHandler
	@ResponseBody
	public ErrorResponseBody handleProductAlreadyExists(ProductAlreadyExistsException e) {
		
		return new ErrorResponseBody(e, HttpStatus.NOT_ACCEPTABLE);
		
	}
	
	@ExceptionHandler
	@ResponseBody
	public ErrorResponseBody handleProductNotFound(ProductNotFoundException e) {
		
		return new ErrorResponseBody(e, HttpStatus.NOT_FOUND);
		
	}
	
	@ExceptionHandler
	@ResponseBody
	public ErrorResponseBody handleOrderNotFound(OrderNotFoundException e) {
	
		return new ErrorResponseBody(e, HttpStatus.NOT_FOUND);
		
	}
	
	@ExceptionHandler
	@ResponseBody
	public ErrorResponseBody handleOrderedItemNotFound(OrderedItemNotFoundException e) {
	
		return new ErrorResponseBody(e, HttpStatus.NOT_FOUND);
		
	}
	
	@ExceptionHandler
	@ResponseBody
	public ErrorResponseBody handleUnexpectedException(Exception e) {
		e.printStackTrace();
		return new ErrorResponseBody(e, HttpStatus.BAD_REQUEST);
		
	}
}
