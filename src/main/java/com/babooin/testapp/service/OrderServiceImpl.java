package com.babooin.testapp.service;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.babooin.testapp.dao.OrderDAO;
import com.babooin.testapp.entity.Order;

@Service
public class OrderServiceImpl implements OrderService {
	
	@Autowired
	private OrderDAO dao;

	@Override
	@Transactional
	public Optional<Order> findById(Long id) {
		return dao.findById(id);
	}

	@Override
	@Transactional
	public List<Order> findAll() {
		return dao.findAll();
	}

	@Override
	@Transactional
	public void save(Order order) {
		dao.save(order);
	}

	@Override
	@Transactional
	public void deleteById(Long id) {
		dao.deleteById(id);
	}

	@Override
	@Transactional
	public void saveOrUpdate(Order entity) {
		dao.saveOrUpdate(entity);
		
	}

	@Override
	@Transactional
	public void delete(Order entity) {
		dao.delete(entity);
		
	}


}
