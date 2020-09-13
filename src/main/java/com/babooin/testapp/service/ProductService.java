package com.babooin.testapp.service;

import java.util.Optional;

import com.babooin.testapp.entity.Product;

public interface ProductService extends CrudService<Product, String> {
	
	Optional<Product> findBySerial(String serial);
	void deleteBySerial(String serial);
	Product findBySerialOrThrow(String serial);

}
