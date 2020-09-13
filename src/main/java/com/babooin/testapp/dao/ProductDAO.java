package com.babooin.testapp.dao;

import java.util.Optional;

import com.babooin.testapp.entity.Product;
import com.babooin.testapp.exception.ProductNotFoundException;

public interface ProductDAO extends CrudRepository<Product, String> {

	Optional<Product> findBySerial(String serial);
	void deleteBySerial(String serial);
	Product findBySerialOrThrow(String serial) throws ProductNotFoundException;

}
