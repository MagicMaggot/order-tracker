package com.babooin.testapp.service;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.babooin.testapp.dao.ProductDAO;
import com.babooin.testapp.entity.Product;

@Service
public class ProductServiceImpl implements ProductService {
	
	@Autowired
	private ProductDAO dao;

	@Override
	@Transactional
	public Optional<Product> findBySerial(String serial) {
		return dao.findBySerial(serial);
	}

	@Override
	@Transactional
	public List<Product> findAll() {
		return dao.findAll();
	}

	@Override
	@Transactional
	public void save(Product product) {
		dao.save(product);

	}

	@Override
	@Transactional
	public void deleteBySerial(String serial) {
		dao.deleteBySerial(serial);
	}

	@Override
	@Transactional
	public Optional<Product> findById(String id) {
		return dao.findById(id);
	}

	@Override
	@Transactional
	public void deleteById(String id) {
		dao.deleteById(id);
		
	}

	@Override
	@Transactional
	public void saveOrUpdate(Product entity) {
		dao.saveOrUpdate(entity);
		
	}

	@Override
	@Transactional
	public void delete(Product entity) {
		dao.delete(entity);
		
	}

	@Override
	@Transactional
	public Product findBySerialOrThrow(String serial) {
		return dao.findBySerialOrThrow(serial);
	}


}
